package smart.dungeon.rooms;

import java.util.LinkedList;

import org.powerbot.game.api.wrappers.node.GroundItem;

import smart.dungeon.RSArea;
import smart.dungeon.doors.Door;

public class NormalRoom extends Room {
	public NormalRoom(RSArea area, LinkedList<Door> doors,
			GroundItem[] groundItems) {
		super(area, doors, NORMAL);
	}
}
