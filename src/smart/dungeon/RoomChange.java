package smart.dungeon;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.dungeon.doors.Door;
import smart.dungeon.doors.NormalDoor;
import smart.dungeon.rooms.Room;
import smart.dungeon.util.GameConstants;

public class RoomChange {

	public static void adapt() {
		if (Dungeon.getCurrentRoom().contains(Players.getLocal().getLocation())) {
			return;
		}
		for (Room room : AreaExplorer.getRooms()) {
			if (room.contains(Players.getLocal().getLocation())) {
				Dungeon.setLastRoom(Dungeon.getCurrentRoom());
				Dungeon.setCurrentRoom(room);
				if (!Dungeon.getLastDoorOpened().isOpen()
						&& Calculations.distanceTo(Dungeon.getLastDoorOpened()
								.getLocation()) < 5) {
					Dungeon.getLastDoorOpened().setOpened(true);
					Dungeon.getLastRoom().updateDoor(
							Dungeon.getLastDoorOpened());
					Dungeon.getLastDoorOpened().setConnector(
							Dungeon.getCurrentRoom());
					AreaExplorer.getDoors().remove(Dungeon.getLastDoorOpened());
					AreaExplorer.getDoors().add(Dungeon.getLastDoorOpened());
				}
				return;
			}
		}
		Dungeon.setLastRoom(Dungeon.getCurrentRoom());
		Dungeon.setCurrentRoom(AreaExplorer.newRoom());
		updateDoors();
		if (SceneEntities.getNearest(GameConstants.BOSS_DOORS) != null
				&& AreaExplorer.getBossRoom() == null) {
			AreaExplorer.setBossRoom(Dungeon.getCurrentRoom());
			SceneObject bossDoor = SceneEntities
					.getNearest(GameConstants.BOSS_DOORS);
			Door tempDoor = new NormalDoor(bossDoor);
			tempDoor.setConnector(Dungeon.getLastRoom());
			tempDoor.setOpened(true);
			AreaExplorer.getDoors().add(tempDoor);
		}
	}

	public static void updateDoors() {
		Door closestDoor = Dungeon.getCurrentRoom().getClosestDoorTo(
				Dungeon.getLastDoorOpened());
		if (closestDoor != null) {
			closestDoor.setOpened(true);
			closestDoor.setConnector(Dungeon.getLastRoom());
		}
		Dungeon.getLastDoorOpened().setOpened(true);
		Dungeon.getLastRoom().updateDoor(Dungeon.getLastDoorOpened());
		Dungeon.getCurrentRoom().updateDoor(closestDoor);
		Dungeon.getLastDoorOpened().setConnector(Dungeon.getCurrentRoom());
		AreaExplorer.getDoors().remove(Dungeon.getLastDoorOpened());
		AreaExplorer.getDoors().add(Dungeon.getLastDoorOpened());
		AreaExplorer.getDoors().remove(closestDoor);
		AreaExplorer.getDoors().add(closestDoor);
	}

}
