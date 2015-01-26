package smart.dungeon.tasks;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;
import smart.dungeon.util.GameConstants;

public class EndDungeon implements Condition, Task {

	@Override
	public boolean validate() {
		return SceneEntities.getNearest(GameConstants.FINISH_LADDERS) != null
				&& Dungeon.isInBossRoom();
	}

	@Override
	public void run() {
		Dungeon.setStatus("Ending dungeon");
		SceneObject finish = SceneEntities
				.getNearest(GameConstants.FINISH_LADDERS);
		if (Widgets.get(1188).getChild(3).isOnScreen()) {
			Widgets.get(1188).getChild(3).interact("Continue");
			int timeout = 0;
			while (!Widgets.get(933).getChild(13).isOnScreen()
					&& ++timeout <= 15) {
				Time.sleep(100);
			}
		} else if (Widgets.get(933).getChild(13).isOnScreen()
				&& Integer.parseInt(Widgets.get(933).getChild(41).getText()
						.replace("%", "")) == 0) {
			Widgets.get(933).getChild(13).interact("Skip");
		} else if (Widgets.get(933).getChild(322).isOnScreen()) {
			Dungeon.addTokens(Integer.parseInt(Widgets.get(933).getChild(41)
					.getText().replace("%", "")));
			Widgets.get(933).getChild(322).interact("Ready");
			Time.sleep(Random.nextInt(1000, 1200));
			Dungeon.clearAll();
		} else {
			if (!finish.isOnScreen()) {
				if (!Players.getLocal().isMoving()) {
					WalkingApi.walkTileMM(finish.getLocation(), false);
					Time.sleep(Random.nextInt(500, 600));
				}
			} else {
				finish.interact("End-dungeon", "Ladder");
				int timeout = 0;
				while (Calculations.distanceTo(finish.getLocation()) != 1.0
						&& ++timeout <= 15) {
					Time.sleep(100);
					if (Players.getLocal().isMoving()) {
						timeout = 0;
					}
				}
			}
		}
		Time.sleep(Random.nextInt(1000, 1200));
	}
}