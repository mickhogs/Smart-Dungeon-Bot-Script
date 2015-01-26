package smart.dungeon.util;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;

import smart.api.InventoryApi;

public class Misc {

	public static boolean arrayContains(String[] array, boolean contains,
			String... check) {
		if (array == null || check == null || array.length < 1)
			return false;
		for (String i : array) {
			if (i == null)
				continue;
			for (String l : check) {
				if (l == null)
					continue;
				if (contains) {
					if (l.toLowerCase().contains(i.toLowerCase())) {
						return true;
					}
				} else {
					if (i.toLowerCase().equals(l.toLowerCase()))
						return true;
				}
			}
		}
		return false;
	}

	public static boolean arrayContains(String[] array, String... check) {
		return arrayContains(array, false, check);
	}

	public static boolean arrayContains(int[] array, int... check) {
		if (array == null || check == null || array.length < 1)
			return false;
		for (int i : array) {
			for (int l : check) {
				if (i == l)
					return true;
			}
		}
		return false;
	}

	public static boolean arrayContains(int[][] arrays, int... check) {
		for (int[] array : arrays) {
			if (arrayContains(array, check))
				return true;
		}
		return false;
	}

	public static Object callMethod(Object object, String name) {
		Method[] allMethods = object.getClass().getDeclaredMethods();
		for (Method m : allMethods) {
			String mname = m.getName();
			if (!mname.equals(name)) {
				continue;
			}
			m.setAccessible(true);
			try {
				return m.invoke(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String parseTime(long millis) {
		long time = millis / 1000;
		String seconds = Integer.toString((int) (time % 60));
		String minutes = Integer.toString((int) ((time % 3600) / 60));
		String hours = Integer.toString((int) (time / 3600));
		for (int i = 0; i < 2; i++) {
			if (seconds.length() < 2) {
				seconds = "0" + seconds;
			}
			if (minutes.length() < 2) {
				minutes = "0" + minutes;
			}
			if (hours.length() < 2) {
				hours = "0" + hours;
			}
		}
		return hours + ":" + minutes + ":" + seconds;
	}

	public static int calculatePerHour(int num, long startTime) {
		return (int) ((num) * 3600000D / (System.currentTimeMillis() - startTime));
	}

	public static void eat() {
		if (Tabs.getCurrent() != Tabs.INVENTORY) {
			Tabs.INVENTORY.open();
			Time.sleep(Random.nextInt(500, 600));
		}
		if (InventoryApi.containsOneOf(GameConstants.FOODS)) {
			InventoryApi
					.getItem(GameConstants.FOODS)
					.getWidgetChild()
					.interact("Eat",
							InventoryApi.getItem(GameConstants.FOODS).getName());
		}
	}

	public static String format(int value) {
		DecimalFormat myFormatter = new DecimalFormat("###,###,###");
		return myFormatter.format(value);
	}
}
