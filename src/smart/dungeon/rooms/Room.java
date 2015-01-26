package smart.dungeon.rooms;

import java.util.ArrayList;
import java.util.LinkedList;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.dungeon.AreaExplorer;
import smart.dungeon.Dungeon;
import smart.dungeon.Equipable;
import smart.dungeon.ItemHandler;
import smart.dungeon.NpcDefinitions;
import smart.dungeon.RSArea;
import smart.dungeon.doors.Door;
import smart.dungeon.util.GameConstants;
import smart.dungeon.util.GameVariables;
import smart.dungeon.util.Misc;

public abstract class Room {
	protected final RSArea area;
	protected LinkedList<Door> doors = new LinkedList<Door>();
	protected final Type type;
	protected Room parent;

	protected static enum Type {
		NORMAL, BOSS, PUZZLE
	}

	public static final Type NORMAL = Type.NORMAL;
	public static final Type BOSS = Type.BOSS;
	public static final Type PUZZLE = Type.PUZZLE;

	public Room(RSArea area, LinkedList<Door> doors, Type type) {
		this.area = area;
		this.doors = doors;
		this.type = type;
	}

	public RSArea getArea() {
		return area;
	}

	public LinkedList<Door> getDoors() {
		return doors;
	}

	public Type getType() {
		return type;
	}

	public boolean contains(SceneObject object) {
		return area.contains(object.getLocation());
	}

	public boolean contains(NPC npc) {
		return area.contains(npc.getLocation());
	}

	boolean contains(GroundItem item) {
		return area.contains(item.getLocation());
	}

	public boolean contains(Tile tile) {
		return area.contains(tile);
	}

	public boolean contains(Door door) {
		return area.contains(door.getLocation());
	}

	public Tile getRandomTile() {
		Tile[] tiles = getArea().getTileArray();
		Tile randTile = tiles[Random.nextInt(0, tiles.length)];
		return randTile;
	}

	public void updateDoor(Door door) {
		doors.remove(door);
		doors.add(door);
	}

	public Tile getCentralTile() {
		return getArea().getCentralTile();
	}

	public Door getNearestDoor() {
		Door closest = null;
		for (Door door : doors) {
			if (contains(door)) {
				if (door.canOpen() && !door.isOpen()) {
					if (closest == null)
						closest = door;
					else {
						if (Calculations.distanceTo(door.getLocation()) < Calculations
								.distanceTo(closest.getLocation()))
							closest = door;
					}
				}
			}
		}
		return closest;
	}

	public Door getDoorAt(Tile tile) {
		for (Door door : doors) {
			if (door.getLocation().equals(tile)) {
				return door;
			}
		}
		return null;
	}

	public Door getClosestDoorTo(Door door) {
		if (door == null) {
			return null;
		}
		Door returnDoor = null;
		double closest = Integer.MAX_VALUE;
		for (Door doorCheck : AreaExplorer.getDoors()) {
			if (door.getLocation().equals(doorCheck.getLocation())) {
				continue;
			}
			if (Calculations.distance(door.getLocation(),
					doorCheck.getLocation()) < closest) {
				closest = Calculations.distance(door.getLocation(),
						doorCheck.getLocation());
				returnDoor = doorCheck;
			}
		}
		return returnDoor;
	}

	public Door getDoorForConnector(Room connector) {
		for (Door door : doors) {
			if (door != null && door.getConnector() != null) {
				if (door.getConnector().equals(connector)) {
					return door;
				}
			}
		}
		return null;
	}

	public NPC getHighestPriorityEnemy() {
		if (!hasUnopenedDoors()) {
			return null;
		}
		NPC[] allNpcsInRoom = NPCs.getLoaded(new Filter<NPC>() {
			public boolean accept(NPC npc) {
				return !Misc.arrayContains(GameConstants.NONARGRESSIVE_NPCS,
						npc.getId())
						&& !npc.getName().contains("mastyx")
						&& contains(npc) && npc.getHpPercent() > 0;
			}
		});
		int highestPriority = -1;
		NPC highestPriorityNpc = null;
		for (NPC npc : allNpcsInRoom) {
			NpcDefinitions npcDef = new NpcDefinitions(npc, this);
			if (npcDef.getPriority() > highestPriority) {
				highestPriority = npcDef.getPriority();
				highestPriorityNpc = npc;
			}
		}
		return highestPriorityNpc;
	}

	public boolean lastNpc() {
		NPC[] allNpcsInRoom = NPCs.getLoaded(new Filter<NPC>() {
			public boolean accept(NPC npc) {
				return !Misc.arrayContains(GameConstants.NONARGRESSIVE_NPCS,
						npc.getId())
						&& !npc.getName().contains("mastyx")
						&& contains(npc);
			}
		});
		return allNpcsInRoom.length == 1;
	}

	public NPC getNearestNPC(final String name) {
		return NPCs.getNearest(new Filter<NPC>() {
			public boolean accept(NPC npc) {
				try {
					return npc != null
							&& npc.getName().toLowerCase()
									.matches(name.toLowerCase())
							&& contains(npc) && npc.getHpPercent() > 0;
				} catch (Exception ignored) {
					return false;
				}
			}
		});
	}

	public GroundItem getItem() {
		GroundItem item = GroundItems.getNearest(new Filter<GroundItem>() {
			public boolean accept(GroundItem t) {
				try {
					if (t == null) {
						return false;
					}
					if (!area.contains(t.getLocation())) {
						return false;
					}
					for (int[] j : GameConstants.LOOT_ITEMS) {
						for (int i : j) {
							if (i == t.getGroundItem().getId()) {
								return true;
							}
						}
					}
					if (Skills.getRealLevel(Skills.CONSTITUTION) < 30
							&& Misc.arrayContains(GameConstants.FOODS, t
									.getGroundItem().getId())) {
						return true;
					}
					if (GameVariables.getComplexity() > 4
							&& Misc.arrayContains(
									GameConstants.COMPLEXITY_LOOT, t
											.getGroundItem().getId())) {
						return true;
					}
					if (t.getGroundItem() != null
							&& t.getGroundItem().getName() != null
							&& t.getGroundItem().getName().contains("(b)")) {
						return true;
					}
					Equipable equip = new Equipable(t.getGroundItem().getName());
					return ItemHandler.shouldEquip(equip,
							equip.getEquipmentIndex());
				} catch (Exception ignored) {
					return false;
				}
			}
		});
		if (item != null)
			return item;
		else
			return null;
	}

	public boolean hasUnopenedDoors() {
		for (Door doorCheck : Dungeon.getCurrentRoom().getDoors()) {
			if (!doorCheck.isOpen()) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Room> getConnectors() {
		ArrayList<Room> connectors = new ArrayList<Room>();
		for (Door door : doors) {
			if (door == null)
				continue;
			if (door.getConnector() != null) {
				connectors.add(door.getConnector());
			}
		}
		return connectors;
	}

	public void setParent(Room room) {
		parent = room;
	}

	public Room getParent() {
		return parent;
	}
}