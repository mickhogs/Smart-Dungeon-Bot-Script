package smart.dungeon.doors;

import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.dungeon.AreaExplorer;
import smart.dungeon.rooms.Room;

public abstract class Door {
	protected SceneObject door;
	protected final int id;
	protected final Tile location;
	protected final Type type;
	protected boolean locked;
	protected boolean open;
	protected int connector = -1;

	public static enum Type {
		KEY, SKILL, NORMAL
	}

	public static final Type KEY = Type.KEY;
	public static final Type SKILL = Type.SKILL;
	public static final Type NORMAL = Type.NORMAL;

	public Door(SceneObject door, Type type) {
		this.door = door;
		this.id = door.getId();
		this.location = door.getLocation();
		this.type = type;
	}

	public SceneObject getDoor() {
		return door;
	}

	public int getId() {
		return id;
	}

	public Tile getLocation() {
		return location;
	}

	public Type getType() {
		return type;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpened(boolean open) {
		this.open = open;
	}

	public Room getConnector() {
		if (connector == -1) {
			return null;
		} else {
			return AreaExplorer.getRooms().get(connector);
		}
	}

	public void setConnector(Room connector) {
		this.connector = AreaExplorer.getRooms().indexOf(connector);
	}

	public Room getParent() {
		for (Room room : AreaExplorer.getRooms()) {
			if (room.contains(this)) {
				return room;
			}
		}
		return null;
	}

	public static SceneObject getTopAt(Tile tile, int... id) {
		SceneObject[] objects = SceneEntities.getLoaded(tile);
		for (SceneObject object : objects) {
			for (int doorID : id)
				if (object.getId() == doorID)
					return object;
		}
		return null;
	}

	public static SceneObject getTopAt(Tile tile, int[][] ids) {
		for (int[] subIds : ids)
			if (getTopAt(tile, subIds) != null)
				return getTopAt(tile, subIds);
		return null;
	}

	public abstract boolean canOpen();

	public abstract void open();
}