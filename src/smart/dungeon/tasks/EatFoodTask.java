package smart.dungeon.tasks;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;

import smart.dungeon.util.Misc;

public class EatFoodTask extends Strategy implements Condition, Task {

	public EatFoodTask() {
		setLock(false);
	}

	@Override
	public boolean validate() {
		return Players.getLocal().getHpPercent() <= 50;
	}

	@Override
	public void run() {
		Misc.eat();
		Time.sleep(Random.nextInt(1000, 1200));
	}
}
