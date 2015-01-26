package smart.api;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.wrappers.node.Item;

public class EquipmentApi {

	public static final int WIDGET = 387;
	public static final int HEAD = 6, CAPE = 9, NECK = 12, WEAPON = 15,
			BODY = 18, SHIELD = 21, LEGS = 24, HANDS = 27, FEET = 30,
			RING = 33, AMMO = 36, AURA = 45;

	public static Item getItem(int equipmentIndex) {
		return new Item(Widgets.get(WIDGET, equipmentIndex));
	}

	public static boolean wearingItem(String name) {
		return new Item(Widgets.get(WIDGET, WEAPON)).getName().contains(name);
	}
}