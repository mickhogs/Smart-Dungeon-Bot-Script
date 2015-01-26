package smart.dungeon.bosses;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;
import smart.dungeon.ItemHandler.Style;

public class PlaneFreezerBoss implements Condition, Task {

	@Override
	public boolean validate() {
		return Dungeon.getCurrentRoom() != null
				&& Dungeon.getCurrentRoom().getNearestNPC(
						".*freezer Lakhrahnaz") != null
				&& Players.getLocal().getInteracting() == null;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Killing boss");
		NPC boss = Dungeon.getCurrentRoom().getNearestNPC(
				".*freezer Lakhrahnaz");
		// CombatApi.doPrayerFor(new NpcDefinitions(boss));
		if (Calculations.distanceTo(boss.getLocation()) > 2
				&& Dungeon.getCombatStyle() == Style.MELEE) {
			if (!boss.isOnScreen()) {
				WalkingApi.walkTileMM(boss.getLocation(), false);
			} else {
				boss.interact("Walk");
			}
			int timeout = 0;
			while (Calculations.distanceTo(boss.getLocation()) > 2
					&& ++timeout <= 15) {
				Time.sleep(100);
				if (Players.getLocal().isMoving()) {
					timeout = 0;
				}
			}
		} else {
			boss.interact("Attack");
			Time.sleep(Random.nextInt(1000, 1200));
		}
	}

}
