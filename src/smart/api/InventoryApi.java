package smart.api;

import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

public class InventoryApi extends Inventory {

	public static boolean contains(int id) {
		return getItem(new int[] { id }) != null;
	}

	public static boolean containsOneOf(String name) {
		for (Item i : getItems()) {
			if (i.getName().contains(name)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsOneOf(int[] id) {
		return getItem(id) != null;
	}

	public static boolean isFull() {
		return Inventory.getCount() == 28;
	}

}