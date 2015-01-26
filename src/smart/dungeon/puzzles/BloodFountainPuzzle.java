package smart.dungeon.puzzles;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class BloodFountainPuzzle implements Condition, Task {

	private static final int SKATER = 12089;

	@Override
	public boolean validate() {
		return NPCs.getNearest(SKATER) != null;
	}

	@Override
	public void run() {
		NPC npc = NPCs.getNearest(SKATER);
		if (Camera.getPitch() != 20) {
			Camera.setPitch(20);
		}
		if (!npc.isOnScreen()) {
			Camera.turnTo(npc);
		}
		npc.interact("Catch");
		Time.sleep(Random.nextInt(5000, 5100));
	}
}
