package smart.dungeon.tasks;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;

import smart.dungeon.Dungeon;
import smart.dungeon.RoomChange;
import smart.dungeon.doors.Door;

public class OpenDoorTask implements Condition, Task {

	@Override
	public boolean validate() {
		return Dungeon.getCurrentRoom() != null
				&& Dungeon.getCurrentRoom().getNearestDoor() != null
				&& Dungeon.getCurrentRoom().getHighestPriorityEnemy() == null
				&& Dungeon.getCurrentRoom().getItem() == null
				&& !Dungeon.isInBossRoom();
	}

	@Override
	public void run() {
		Door toOpen = Dungeon.getCurrentRoom().getNearestDoor();
		if (toOpen == null) {
			return;
		}
		Dungeon.setStatus("Opening " + toOpen.getType().name().toLowerCase()
				+ " door");
		toOpen.open();
		RoomChange.adapt();
	}
}
