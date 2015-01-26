package smart.dungeon.doors;

import java.util.ArrayList;

public class Keys {

	private static ArrayList<Integer> keys = new ArrayList<Integer>();

	public static boolean hasKey(int key) {
		return keys.contains(key);
	}

	public static void addKey(int key) {
		keys.add(key);
	}

	public static void removeKey(int key) {
		keys.remove(key);
	}

	public static void clear() {
		keys.clear();
	}
}
