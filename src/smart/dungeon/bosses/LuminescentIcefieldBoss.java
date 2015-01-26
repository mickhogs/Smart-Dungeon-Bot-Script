package smart.dungeon.bosses;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;

public class LuminescentIcefieldBoss implements Condition, Task {

	private final int SPEC_ATTACK = 13338;

	@Override
	public boolean validate() {
		return Dungeon.getCurrentRoom() != null
				&& Dungeon.getCurrentRoom()
						.getNearestNPC(".*inescent icefiend") != null;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Killing boss");
		NPC boss = Dungeon.getCurrentRoom()
				.getNearestNPC(".*inescent icefiend");
		// CombatApi.doPrayerFor(new NpcDefinitions(boss));
		if (boss.getAnimation() == SPEC_ATTACK) {
			if (Walking.getEnergy() > 10 && !Walking.isRunEnabled()) {
				Walking.setRun(true);
			}
			Tile corner = Dungeon.getCurrentRoom().getRandomTile();
			while (boss.getAnimation() == SPEC_ATTACK
					&& Calculations.distanceTo(corner) >= Random.nextInt(0, 2)) {
				corner.clickOnMap();
				Time.sleep(Random.nextInt(500, 600));
			}
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
