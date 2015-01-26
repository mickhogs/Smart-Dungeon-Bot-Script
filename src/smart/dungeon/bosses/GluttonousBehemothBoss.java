package smart.dungeon.bosses;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;

public class GluttonousBehemothBoss implements Condition, Task {

	@Override
	public boolean validate() {
		return Dungeon.getCurrentRoom() != null
				&& Dungeon.getCurrentRoom().getNearestNPC(".*tonous behemoth") != null
				&& Players.getLocal().getInteracting() == null;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Killing boss");
		NPC boss = Dungeon.getCurrentRoom().getNearestNPC(".*tonous behemoth");
		// CombatApi.doPrayerFor(new NpcDefinitions(boss));
		SceneObject carcass = SceneEntities.getNearest(49283);
		if (carcass == null) {
			return;
		}
		Tile carcassTile = carcass.getLocation();
		Tile north = new Tile(carcassTile.getX(), carcassTile.getY() + 5,
				carcassTile.getPlane());
		Tile south = new Tile(carcassTile.getX(), carcassTile.getY() - 5,
				carcassTile.getPlane());
		Tile west = new Tile(carcassTile.getX() - 5, carcassTile.getY(),
				carcassTile.getPlane());
		Tile east = new Tile(carcassTile.getX() + 5, carcassTile.getY(),
				carcassTile.getPlane());
		Tile[] array = { north, south, west, east };
		int count = 0;
		for (Tile tile : array) {
			if (Dungeon.getCurrentRoom().getNearestNPC(".*tonous behemoth")
					.getLocation().equals(tile)) {
				Tile walkTile = null;
				switch (count) {
				case 0:
					walkTile = new Tile(carcassTile.getX(),
							carcassTile.getY() + 2, carcassTile.getPlane());
					break;
				case 1:
					walkTile = new Tile(carcassTile.getX(),
							carcassTile.getY() - 2, carcassTile.getPlane());
					break;
				case 2:
					walkTile = new Tile(carcassTile.getX() - 2,
							carcassTile.getY(), carcassTile.getPlane());
					break;
				case 3:
					walkTile = new Tile(carcassTile.getX() + 2,
							carcassTile.getY(), carcassTile.getPlane());
					break;
				}
				if (!walkTile.equals(Players.getLocal().getLocation())
						&& !Players.getLocal().isMoving()) {
					if (walkTile.isOnScreen()) {
						walkTile.interact("Walk");
					} else {
						WalkingApi.walkTileMM(walkTile, false);
					}
					int timeout = 0;
					while (!walkTile.equals(Players.getLocal().getLocation())
							&& ++timeout <= 15) {
						Time.sleep(100);
						if (Players.getLocal().isMoving()) {
							timeout = 0;
						}
					}
				}
			}
			count++;
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
		} else {
			boss.interact("Attack");
			Time.sleep(Random.nextInt(1000, 1200));
		}
	}
}
