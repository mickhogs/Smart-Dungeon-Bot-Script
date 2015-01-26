package smart.dungeon.rooms;

import java.util.LinkedList;

import org.powerbot.game.api.wrappers.node.GroundItem;

import smart.dungeon.RSArea;
import smart.dungeon.doors.Door;

public class PuzzleRoom extends Room {
	private boolean solved;

	public PuzzleRoom(RSArea area, LinkedList<Door> doors,
			GroundItem[] groundItems) {
		super(area, doors, PUZZLE);
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}
}
