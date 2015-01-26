package smart.dungeon.tasks;

import java.awt.Point;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.api.CalculationsApi;
import smart.api.InventoryApi;
import smart.api.WalkingApi;
import smart.dungeon.Dungeon;
import smart.dungeon.util.Misc;

public class PickupItemsTask implements Condition, Task {

	public static int lastPickup;
	public static boolean key;

	@Override
	public boolean validate() {
		return Dungeon.getCurrentRoom() != null
				&& Dungeon.getCurrentRoom().getItem() != null
				&& Dungeon.getCurrentRoom().getHighestPriorityEnemy() == null
				&& StartDungeonTask.init;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Looting items");
		GroundItem pickup = Dungeon.getCurrentRoom().getItem();
		if (InventoryApi.isFull()) {
			Misc.eat();
		}
		if (pickup != null) {
			if (!pickup.isOnScreen()) {
				WalkingApi.walkTileMM(pickup.getLocation(), true);
				int timeout = 0;
				while (!pickup.isOnScreen() && ++timeout <= 15) {
					Time.sleep(100);
					if (Players.getLocal().isMoving()) {
						timeout = 0;
					}
				}
			} else {
				if (isGroundItemOnObject(pickup)) {
					interact(pickup);
				} else {
					pickup.interact("Take", pickup.getGroundItem().getName());
				}
				setLastPickup(pickup.getGroundItem().getId());
				int count = Inventory.getCount();
				int timeout = 0;
				while (count == Inventory.getCount() && ++timeout <= 15) {
					if (getKey()) {
						setKey(false);
						break;
					}
					Time.sleep(100);
					if (Players.getLocal().isMoving()) {
						timeout = 0;
					}
				}
			}
		}
	}

	public boolean isGroundItemOnObject(GroundItem gi) {
		SceneObject[] objects = SceneEntities.getLoaded(gi.getLocation());
		for (SceneObject o : objects) {
			if (o.getType().name().equals("INTERACTIVE")) {
				return true;
			}
		}
		return false;
	}

	public void interact(GroundItem gi) {
		Point p = CalculationsApi.tileToScreen(gi.getLocation(), .5, .5, 512);
		Mouse.move(p);
		Menu.select("Take", gi.getGroundItem().getName());
	}

	public static int getLastPickup() {
		return lastPickup;
	}

	public static void setLastPickup(int lastPickup) {
		PickupItemsTask.lastPickup = lastPickup;
	}

	public static boolean getKey() {
		return key;
	}

	public static void setKey(boolean key) {
		PickupItemsTask.key = key;
	}

}