package smart.dungeon.bosses;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;

public class SagittareBoss implements Condition, Task {

	private boolean dodgeArrows;
	private final Timer arrowReset = new Timer(5000);

	@Override
	public boolean validate() {
		return Dungeon.getCurrentRoom() != null
				&& Dungeon.getCurrentRoom().getNearestNPC("Sagittare") != null;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Killing boss");
		NPC boss = Dungeon.getCurrentRoom().getNearestNPC("Sagittare");
		// CombatApi.doPrayerFor(new NpcDefinitions(boss));

		if (boss.getMessage() != null && boss.getMessage().contains("k off")) {
			dodgeArrows = true;
			arrowReset.reset();
		}
		if (!arrowReset.isRunning()) {
			dodgeArrows = false;
		}
		Tile tile = Dungeon.getCurrentRoom().getRandomTile();
		while (dodgeArrows
				&& Calculations.distanceTo(tile) >= Random.nextInt(0, 2)) {
			tile.clickOnMap();
			Time.sleep(Random.nextInt(500, 600));
		}
		if (!boss.isOnScreen()) {
			WalkingApi.walkTileMM(boss.getLocation(), true);
			int timeout = 0;
			while (!boss.isOnScreen() && ++timeout <= 15) {
				Time.sleep(100);
				if (Players.getLocal().isMoving()) {
					timeout = 0;
				}
			}
		} else if (Players.getLocal().getInteracting() == null) {
			boss.interact("Attack");
			Time.sleep(Random.nextInt(1000, 1200));
		}
	}
}
