package smart.dungeon;

import java.util.LinkedList;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.SmartDungeon;
import smart.dungeon.doors.Door;
import smart.dungeon.doors.KeyDoor;
import smart.dungeon.doors.NormalDoor;
import smart.dungeon.doors.SkillDoor;
import smart.dungeon.rooms.BossRoom;
import smart.dungeon.rooms.NormalRoom;
import smart.dungeon.rooms.PuzzleRoom;
import smart.dungeon.rooms.Room;
import smart.dungeon.util.GameConstants;
import smart.dungeon.util.Misc;

public class AreaExplorer {

	private static LinkedList<Room> rooms = new LinkedList<Room>();
	private static LinkedList<Door> doors = new LinkedList<Door>();
	private static Room startRoom;
	private static Room bossRoom;

	public static LinkedList<Room> getRooms() {
		return rooms;
	}

	public static LinkedList<Door> getDoors() {
		return doors;
	}

	public static Room getStartRoom() {
		return startRoom;
	}

	public static void setStartRoom(Room startRoom) {
		AreaExplorer.startRoom = startRoom;
	}

	public static Room getBossRoom() {
		return bossRoom;
	}

	public static void setBossRoom(Room bossRoom) {
		AreaExplorer.bossRoom = bossRoom;
	}

	public static Room newRoom() {
		TileGrabber floodFill = new TileGrabber();
		final RSArea roomArea = new RSArea(floodFill.fill(Players.getLocal()
				.getLocation()));
		GroundItem[] groundItems = GroundItems
				.getLoaded(new Filter<GroundItem>() {
					public boolean accept(GroundItem groundItem) {
						return roomArea.contains(groundItem.getLocation());
					}
				});
		if (bossRoom == null
				&& SceneEntities.getNearest(GameConstants.BOSS_DOORS) != null) {
			BossRoom room = new BossRoom(roomArea, newDoors(roomArea),
					groundItems);
			getRooms().add(room);
			return room;
		}
		for (Object object : SmartDungeon.getPuzzles()) {
			if ((Boolean) Misc.callMethod(object, "validate")) {
				PuzzleRoom room = new PuzzleRoom(roomArea, newDoors(roomArea),
						groundItems);
				getRooms().add(room);
				return room;
			}
		}
		NormalRoom room = new NormalRoom(roomArea, newDoors(roomArea),
				groundItems);
		getRooms().add(room);
		return room;
	}

	private static LinkedList<Door> newDoors(RSArea roomArea) {
		LinkedList<Door> doors = new LinkedList<Door>();
		SceneObject[] allObject = SceneEntities.getLoaded();
		for (SceneObject obj : allObject) {
			if (!obj.getType().name().equals("INTERACTIVE")
					|| !roomArea.contains(obj.getLocation())) {
				continue;
			}
			/*
			 * Temp fix to duplicates???
			 */
			boolean stop = false;
			for (Door door : doors) {
				if (door.getLocation().equals(obj.getLocation())) {
					stop = true;
				}
			}
			if (stop) {
				continue;
			}
			int getId = obj.getId();
			if (Misc.arrayContains(GameConstants.KEY_DOORS, getId)) {
				doors.add(new KeyDoor(obj));
				continue;
			} else if (Misc.arrayContains(GameConstants.SKILL_DOORS, getId)) {
				doors.add(new SkillDoor(obj));
				continue;
			} else if (Misc.arrayContains(GameConstants.BASIC_DOORS, getId)) {
				doors.add(new NormalDoor(obj));
				continue;
			}
		}
		AreaExplorer.doors.addAll(doors);
		return doors;
	}
}
