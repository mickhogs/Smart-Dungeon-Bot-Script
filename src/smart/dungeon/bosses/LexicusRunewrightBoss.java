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

public class LexicusRunewrightBoss implements Condition, Task {

	private boolean avoidBooks;
	private final Timer bookResetTimer = new Timer(3500);

	@Override
	public boolean validate() {
		return Dungeon.getCurrentRoom() != null
				&& Dungeon.getCurrentRoom().getNearestNPC(".*icus Runewright") != null;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Killing boss");
		NPC boss = Dungeon.getCurrentRoom().getNearestNPC(".*icus Runewright");
		// CombatApi.doPrayerFor(new NpcDefinitions(boss));
		if (boss.getMessage() != null && boss.getMessage().contains("barrage")) {
			avoidBooks = true;
			bookResetTimer.reset();
		}
		if (!bookResetTimer.isRunning()) {
			avoidBooks = false;
		}
		Tile tile = Dungeon.getCurrentRoom().getRandomTile();
		while (avoidBooks
				&& Calculations.distanceTo(tile) >= Random.nextInt(0, 2)) {
			tile.clickOnMap();
			Time.sleep(Random.nextInt(500, 600));
		}
		NPC book = Dungeon.getCurrentRoom().getNearestNPC("tome of .*");
		if (book != null) {
			if (!Players.getLocal().getInteracting().equals(book)) {
				if (!book.isOnScreen()) {
					WalkingApi.walkTileMM(book.getLocation(), true);
					int timeout = 0;
					while (!book.isOnScreen() && ++timeout <= 15) {
						Time.sleep(100);
						if (Players.getLocal().isMoving()) {
							timeout = 0;
						}
					}
				} else {
					book.interact("Attack");
					Time.sleep(Random.nextInt(1000, 1200));
				}
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
