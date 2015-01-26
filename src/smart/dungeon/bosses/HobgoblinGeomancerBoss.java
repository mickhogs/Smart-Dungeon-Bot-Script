package smart.dungeon.bosses;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;

public class HobgoblinGeomancerBoss implements Condition, Task {

	@Override
	public boolean validate() {
		return Dungeon.getCurrentRoom() != null
				&& Dungeon.getCurrentRoom().getNearestNPC(".*goblin Geomancer") != null
				&& Players.getLocal().getInteracting() == null;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Killing boss");
		NPC boss = Dungeon.getCurrentRoom().getNearestNPC(".*goblin Geomancer");
		// CombatApi.doPrayerFor(new NpcDefinitions(boss));
		if (!boss.isOnScreen()) {
			WalkingApi.walkTileMM(boss.getLocation(), true);
			int timeout = 0;
			while (!boss.isOnScreen() && ++timeout <= 15) {
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
