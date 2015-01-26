package smart.dungeon.tasks;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;
import smart.dungeon.util.GameConstants;

public class JumpStairsTask implements Condition, Task {

	@Override
	public boolean validate() {
		return SceneEntities.getNearest(GameConstants.END_STAIRS) != null;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Jumping stairs");
		SceneObject exit = SceneEntities.getNearest(GameConstants.END_STAIRS);
		if (!exit.isOnScreen()) {
			if (!Players.getLocal().isMoving()) {
				WalkingApi.walkTileMM(exit.getLocation(), true);
				Time.sleep(Random.nextInt(500, 600));
			}
		} else {
			exit.interact("Jump-down", "Broken-stairs");
			int timeout = 0;
			while (Players.getLocal().getPlane() != 0 && ++timeout <= 15) {
				Time.sleep(100);
				if (Players.getLocal().isMoving()) {
					timeout = 0;
				}
			}
		}
	}
}
