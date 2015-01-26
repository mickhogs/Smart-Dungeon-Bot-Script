package smart.dungeon.tasks;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;

import smart.api.CombatApi;
import smart.dungeon.NpcDefinitions;
import smart.dungeon.util.GameVariables;

public class ChangeAttackStyleTask implements Condition, Task {

	@Override
	public boolean validate() {
		if ((CombatApi.getActionIndex("Block") - 7) == CombatApi.getFightMode()
				&& GameVariables.getPureMode()) {
			return true;
		}
		if (Players.getLocal().getInteracting() == null
				|| !GameVariables.getSwitchStyles()) {
			return false;
		}
		NpcDefinitions enemyDef = new NpcDefinitions((NPC) Players.getLocal()
				.getInteracting());
		for (String weakness : enemyDef.weaknesses()) {
			if (CombatApi.haveStyle(weakness)
					&& CombatApi.getStyleIndex(weakness) != CombatApi
							.getActionIndex("Block")) {
				if (CombatApi.getStyleIndex(weakness) != CombatApi
						.getStyleIndex(CombatApi.currentAttackStyle())) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void run() {
		if ((CombatApi.getActionIndex("Block") - 7) == CombatApi.getFightMode()
				&& GameVariables.getPureMode()) {
			CombatApi.setFightMode(1);
		} else {
			NpcDefinitions enemyDef = new NpcDefinitions((NPC) Players
					.getLocal().getInteracting());
			for (String weakness : enemyDef.weaknesses()) {
				if (CombatApi.haveStyle(weakness)
						&& !CombatApi.currentAttackStyle().equals(weakness)) {
					CombatApi.setAttackStyle(weakness);
					Time.sleep(Random.nextInt(400, 600));
					return;
				} else if (CombatApi.currentAttackStyle().equals(weakness)) {
					Time.sleep(Random.nextInt(400, 600));
					return;
				}

			}
		}
		Time.sleep(Random.nextInt(400, 600));
	}
}
