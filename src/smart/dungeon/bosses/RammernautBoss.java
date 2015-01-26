package smart.dungeon.bosses;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;
import smart.dungeon.TileGrabber;

public class RammernautBoss implements Condition, Task {

	private boolean dodge;

	@Override
	public boolean validate() {
		return Dungeon.getCurrentRoom() != null
				&& Dungeon.getCurrentRoom().getNearestNPC("Rammernaut") != null;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Killing boss");
		NPC boss = Dungeon.getCurrentRoom().getNearestNPC("Rammernaut");
		// CombatApi.doPrayerFor(new NpcDefinitions(boss));
		if (boss.getMessage() != null) {
			if (boss.getMessage().toLowerCase().contains("arge")) {
				dodge = true;
			} else if (boss.getMessage().toLowerCase().contains("oof")) {
				dodge = false;
			}
		}
		if (dodge) {
			Tile doorTile = TileGrabber.getNearestNonWallTile(Dungeon
					.getCurrentRoom().getDoors().get(0).getLocation());
			doorTile.clickOnMap();
			if (Calculations.distanceTo(boss.getLocation()) < 5) {
				Dungeon.getCurrentRoom().getRandomTile().clickOnMap();
			}
		} else {
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
}
