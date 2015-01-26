package smart.dungeon.tasks;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;

public class AttackNpcTask implements Condition, Task {

	@Override
	public boolean validate() {
		return Dungeon.getCurrentRoom() != null
				&& Dungeon.getCurrentRoom().getHighestPriorityEnemy() != null
				&& Dungeon.getCurrentRoom().hasUnopenedDoors()
				&& !Dungeon.isInBossRoom()
				&& Players.getLocal().getInteracting() == null;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Killing monsters");
		NPC npc = Dungeon.getCurrentRoom().getHighestPriorityEnemy();
		if (!npc.isOnScreen()) {
			WalkingApi.walkTileMM(npc.getLocation(), true);
			int timeout = 0;
			while (!npc.isOnScreen() && ++timeout <= 15) {
				Time.sleep(100);
				if (Players.getLocal().isMoving()) {
					timeout = 0;
				}
			}
		} else {
			npc.interact("Attack");
			Time.sleep(Random.nextInt(1000, 1200));
		}
	}
}
