package smart.dungeon.tasks;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.api.CombatApi;
import smart.api.EquipmentApi;
import smart.dungeon.AreaExplorer;
import smart.dungeon.Dungeon;
import smart.dungeon.Equipable;
import smart.dungeon.ItemHandler.Style;
import smart.dungeon.util.GameConstants;

public class StartDungeonTask implements Condition, Task {

	public static boolean init;

	@Override
	public boolean validate() {
		return SceneEntities.getNearest(GameConstants.EXIT_LADDERS) != null
				&& !StartDungeonTask.init;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Preparing bot");
		if (!Dungeon.getFirstLaunch()) {
			Tabs.EQUIPMENT.open();
			Time.sleep(Random.nextInt(1000, 1200));
			if (CombatApi.getAutoRelatiateSelected()) {
				Tabs.ATTACK.open();
				Time.sleep(Random.nextInt(1000, 1200));
				Widgets.get(884, 13).interact("Auto Retaliate");
				Time.sleep(Random.nextInt(1000, 1200));
			}
			Tabs.INVENTORY.open();
			if (EquipmentApi.getItem(EquipmentApi.WEAPON) != null) {
				Equipable wep = new Equipable(EquipmentApi.getItem(
						EquipmentApi.WEAPON).getName());
				switch (wep.getStyle()) {
				case MELEE:
					Dungeon.setCombatStyle(Style.MELEE);
					break;
				case MAGIC:
					Dungeon.setCombatStyle(Style.MAGIC);
					break;
				case RANGED:
					Dungeon.setCombatStyle(Style.RANGED);
				}
			}
			Dungeon.setFirstLaunch(true);
		}
		Camera.setPitch(true);
		SceneObject exit = SceneEntities.getNearest(GameConstants.EXIT_LADDERS);
		switch (exit.getId()) {
		case 51156:
			Dungeon.setFloorType("Frozen");
			Dungeon.setDoorDifferential(145);
			break;
		case 50604:
			Dungeon.setFloorType("Abandoned");
			Dungeon.setDoorDifferential(209);
			break;
		case 51704:
			Dungeon.setFloorType("Furnished");
			Dungeon.setDoorDifferential(273);
			break;
		case 54675:
			Dungeon.setFloorType("Occult");
			Dungeon.setDoorDifferential(337);
			break;
		case 56149:
			Dungeon.setFloorType("Warped");
			Dungeon.setDoorDifferential(401);
			break;
		}
		Dungeon.setCurrentRoom(AreaExplorer.newRoom());
		AreaExplorer.setStartRoom(Dungeon.getCurrentRoom());
		StartDungeonTask.init = true;
	}
}
