package smart.dungeon.util;

public class GameVariables {

	private static int complexity;
	private static String dungeonSize;
	private static boolean prayerDoor;
	private static boolean pureMode;
	private static boolean switchStyles;

	public static int getComplexity() {
		return complexity;
	}

	public static String getDungeonSize() {
		return dungeonSize;
	}

	public static void setComplexity(int complexity) {
		GameVariables.complexity = complexity;
	}

	public static void setDungeonSize(String dungeonSize) {
		GameVariables.dungeonSize = dungeonSize;
	}

	public static boolean getPrayerDoor() {
		return prayerDoor;
	}

	public static void setPrayerDoor() {
		GameVariables.prayerDoor = !GameVariables.prayerDoor;
	}

	public static boolean getPureMode() {
		return pureMode;
	}

	public static void setPureMode() {
		GameVariables.pureMode = !GameVariables.pureMode;
	}

	public static boolean getSwitchStyles() {
		return switchStyles;
	}

	public static void setSwitchStyles() {
		GameVariables.switchStyles = !GameVariables.switchStyles;
	}

}
