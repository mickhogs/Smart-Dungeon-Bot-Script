package smart.dungeon.tasks;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;
import smart.dungeon.util.GameConstants;
import smart.dungeon.util.GameVariables;

public class EnterDungeonTask implements Condition, Task {

	@Override
	public boolean validate() {
		return SceneEntities.getNearest(GameConstants.ENTRANCE) != null;
	}

	@Override
	public void run() {
		Dungeon.setStatus("Entering dungeon");
		SceneObject entrance = SceneEntities.getNearest(GameConstants.ENTRANCE);
		if (Widgets.get(1186).getChild(8).isOnScreen()) {
			Widgets.get(1186).getChild(8).interact("Continue");
		} else if (Widgets.get(1188).getChild(3).isOnScreen()
				&& Widgets.get(1188).getChild(20).getText().contains("party")) {
			Widgets.get(1188).getChild(3).interact("Continue");
		} else if (Widgets.get(947).getChild(762).isOnScreen()) {
			int currentFloor = Integer.parseInt(Widgets.get(939, 83).getText());
			int selectFloor = currentFloor + 1;
			WidgetChild scrollBar = Widgets.get(947, 47).getChildren()[1];
			// if (selectFloor >= 12) {

			// } else {
			int random = Random.nextInt(1, 3);
			Mouse.move((int) scrollBar.getCentralPoint().getX() + random,
					(int) scrollBar.getCentralPoint().getY() + random);
			while (Integer.parseInt(Widgets.get(947, 44).getText()) != selectFloor) {
				Mouse.drag(Mouse.getX(), Mouse.getY() + 2);
			}
			Time.sleep(Random.nextInt(400, 600));
			Mouse.move((int) Mouse.getX() - Random.nextInt(50, 100),
					(int) Mouse.getY());
			Mouse.click(true);
			// }
			Time.sleep(Random.nextInt(1000, 1200));
			Widgets.get(947).getChild(762).interact("Confirm");
		} else if (Widgets.get(938).getChild(38).isOnScreen()) {
			int currentComplexity = Integer.parseInt(Widgets.get(938, 42)
					.getText());
			if (GameVariables.getComplexity() != currentComplexity) {
				Widgets.get(938)
						.getChild(51 + (GameVariables.getComplexity() * 5))
						.interact("Select");
				Time.sleep(Random.nextInt(1000, 1200));
			}
			Widgets.get(938).getChild(37).interact("Confirm");
		} else if (Widgets.get(1188).getChild(4).isOnScreen()
				&& Widgets.get(1188).getChild(20).getText().contains("dungeon")) {
			Widgets.get(1188)
					.getChild(
							GameVariables.getDungeonSize().equals("Small") ? 3
									: 13).interact("Continue");
		} else {
			if (!entrance.isOnScreen()) {
				if (!Players.getLocal().isMoving()) {
					WalkingApi.walkTileMM(entrance.getLocation(), true);
					Time.sleep(Random.nextInt(500, 600));
				}
			} else {
				entrance.interact("Climb-down", "Dungeon entrance");
				int timeout = 0;
				while (Calculations.distanceTo(entrance.getLocation()) != 1.0
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
