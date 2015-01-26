package smart.dungeon.tasks;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;

import smart.dungeon.AreaExplorer;
import smart.dungeon.Dungeon;
import smart.dungeon.Pathfinding;
import smart.dungeon.RoomChange;
import smart.dungeon.doors.Door;
import smart.dungeon.rooms.Room;

public class WalkToRoomTask implements Condition, Task {

	@Override
	public boolean validate() {
		return Dungeon.getCurrentRoom() != null
				&& Dungeon.getCurrentRoom().getNearestDoor() == null
				&& Dungeon.getCurrentRoom().getItem() == null
				&& Dungeon.getCurrentRoom().getHighestPriorityEnemy() == null
				&& !Dungeon.isInBossRoom() && StartDungeonTask.init;
	}

	@Override
	public void run() {
		Pathfinding bfs = new Pathfinding();
		Room end = null;
		Door searchDoor = getDoor();
		if (searchDoor != null) {
			end = searchDoor.getParent();
		}
		Room[] path = bfs.findPath(Dungeon.getCurrentRoom(), end);
		if (path == null) {
			Dungeon.setStatus("Aborting dungeon");
			Dungeon.increaseDungeonsAborted();
			return;
		}
		Dungeon.setStatus("Walking to unopened door");
		bfs.walkPath(path);
		RoomChange.adapt();
	}

	private Door getDoor() {
		for (Door door : AreaExplorer.getDoors()) {
			if (door.canOpen() && !door.isOpen())
				return door;
		}
		return null;
	}
}
