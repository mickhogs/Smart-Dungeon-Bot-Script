package smart.dungeon;

import org.powerbot.game.api.methods.interactive.Players;

import smart.dungeon.doors.Door;
import smart.dungeon.doors.Keys;
import smart.dungeon.rooms.Room;
import smart.dungeon.tasks.StartDungeonTask;

public class Dungeon {

	private static boolean firstLaunch;
	private static String floorType;
	private static int doorDifferential;
	private static Room currentRoom;
	private static Door lastDoorOpended;
	private static Room lastRoom;
	private static ItemHandler.Style combatStyle;
	private static int dungeonsCompleted;
	private static int dungeonsAborted;
	private static String status = "N/A";
	private static int tokens;
	private static int deaths;

	public static boolean getFirstLaunch() {
		return firstLaunch;
	}

	public static void setFirstLaunch(boolean firstLaunch) {
		Dungeon.firstLaunch = firstLaunch;
	}

	public static String floorType() {
		return floorType;
	}

	public static void setFloorType(String floorType) {
		Dungeon.floorType = floorType;
	}

	public static int getDoorDifferential() {
		return doorDifferential;
	}

	public static void setDoorDifferential(int doorDifferential) {
		Dungeon.doorDifferential = doorDifferential;
	}

	public static Room getCurrentRoom() {
		if (AreaExplorer.getRooms().indexOf(currentRoom) != -1) {
			return AreaExplorer.getRooms().get(
					AreaExplorer.getRooms().indexOf(currentRoom));
		} else {
			return null;
		}
	}

	public static void setCurrentRoom(Room currentRoom) {
		Dungeon.currentRoom = currentRoom;
	}

	public static Door getLastDoorOpened() {
		return lastDoorOpended;
	}

	public static void setLastDoorOpended(Door lastDoorOpended) {
		Dungeon.lastDoorOpended = lastDoorOpended;
	}

	public static Room getLastRoom() {
		return lastRoom;
	}

	public static void setLastRoom(Room lastRoom) {
		Dungeon.lastRoom = lastRoom;
	}

	public static ItemHandler.Style getCombatStyle() {
		return combatStyle;
	}

	public static void setCombatStyle(ItemHandler.Style combatStyle) {
		Dungeon.combatStyle = combatStyle;
	}

	public static int getDungeonsCompleted() {
		return dungeonsCompleted;
	}

	public static void increaseDungeonsCompleted() {
		Dungeon.dungeonsCompleted = Dungeon.dungeonsCompleted + 1;
	}

	public static int getDungeonsAborted() {
		return dungeonsAborted;
	}

	public static void increaseDungeonsAborted() {
		Dungeon.dungeonsAborted = Dungeon.dungeonsAborted + 1;
	}

	public static String getStatus() {
		return status;
	}

	public static void setStatus(String status) {
		Dungeon.status = status;
	}

	public static boolean isInBossRoom() {
		if (AreaExplorer.getBossRoom() == null) {
			return false;
		}
		if (AreaExplorer.getBossRoom().contains(
				Players.getLocal().getLocation())) {
			return true;
		}
		return false;
	}

	public static void clearAll() {
		AreaExplorer.getRooms().clear();
		AreaExplorer.getDoors().clear();
		Dungeon.setLastRoom(null);
		Dungeon.setCurrentRoom(null);
		Dungeon.setLastDoorOpended(null);
		AreaExplorer.setBossRoom(null);
		AreaExplorer.setStartRoom(null);
		Dungeon.increaseDungeonsCompleted();
		Keys.clear();
		StartDungeonTask.init = false;
	}

	public static int getTokens() {
		return tokens;
	}

	public static void addTokens(int tokens) {
		Dungeon.tokens = Dungeon.tokens + tokens;
	}

	public static int getTotalDeaths() {
		return deaths;
	}

	public static void increaseTotalDeaths() {
		Dungeon.deaths = Dungeon.deaths + 1;
	}

}
