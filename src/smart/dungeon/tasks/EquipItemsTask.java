package smart.dungeon.tasks;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;

import smart.api.EquipmentApi;
import smart.api.InventoryApi;
import smart.dungeon.Dungeon;
import smart.dungeon.Equipable;
import smart.dungeon.ItemHandler;
import smart.dungeon.util.GameVariables;

public class EquipItemsTask implements Condition, Task {

	@Override
	public boolean validate() {
		if (!StartDungeonTask.init
				|| Players.getLocal().getInteracting() != null
				|| Dungeon.isInBossRoom()) {
			return false;
		}
		for (Item item : InventoryApi.getItems()) {
			if (item != null) {
				Equipable check = new Equipable(item.getName());
				if (check.getLocation() != Equipable.Location.NOVALUE) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run() {
		for (Item item : InventoryApi.getItems()) {
			Equipable equip = new Equipable(item.getName());
			if (equip.getLocation() != Equipable.Location.NOVALUE) {
				switch (equip.getLocation()) {
				case HELM:
					doItem(item, EquipmentApi.HEAD);
					break;
				case CHEST:
					doItem(item, EquipmentApi.BODY);
					break;
				case LEGS:
					doItem(item, EquipmentApi.LEGS);
					break;
				case FEET:
					doItem(item, EquipmentApi.FEET);
					break;
				case HANDS:
					doItem(item, EquipmentApi.HANDS);
					break;
				case WEAPON:
					doItem(item, EquipmentApi.WEAPON);
					break;
				case OFFHAND:
					doItem(item, EquipmentApi.SHIELD);
					break;
				case AMMO:
					doItem(item, EquipmentApi.AMMO);
					break;
				}
			}
		}
		Time.sleep(Random.nextInt(400, 600));
	}

	private void doItem(Item item, int equipmentIndex) {
		Equipable toEquip = new Equipable(item.getName());
		if (ItemHandler.shouldEquip(toEquip, toEquip.getEquipmentIndex())) {
			item.getWidgetChild().click(true);
		} else if (!item.getName().contains("(b)")) {
			if (GameVariables.getComplexity() < 5)
				item.getWidgetChild().interact("Drop", item.getName());
		}
	}

}
