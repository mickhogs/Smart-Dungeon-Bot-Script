package smart;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.bot.event.MessageEvent;
import org.powerbot.game.bot.event.listener.MessageListener;
import org.powerbot.game.bot.event.listener.PaintListener;

import smart.dungeon.bosses.AsteaFrostwebBoss;
import smart.dungeon.bosses.BloodChillerBoss;
import smart.dungeon.bosses.GluttonousBehemothBoss;
import smart.dungeon.bosses.HobgoblinGeomancerBoss;
import smart.dungeon.bosses.IcyBonesBoss;
import smart.dungeon.bosses.LexicusRunewrightBoss;
import smart.dungeon.bosses.LuminescentIcefieldBoss;
import smart.dungeon.bosses.PlaneFreezerBoss;
import smart.dungeon.bosses.RammernautBoss;
import smart.dungeon.bosses.SagittareBoss;
import smart.dungeon.listener.MessageListenerHandler;
import smart.dungeon.listener.PaintListenerHandler;
import smart.dungeon.listener.paint.PaintController;
import smart.dungeon.puzzles.PondSkaterPuzzle;
import smart.dungeon.tasks.AttackNpcTask;
import smart.dungeon.tasks.ChangeAttackStyleTask;
import smart.dungeon.tasks.EatFoodTask;
import smart.dungeon.tasks.EndDungeon;
import smart.dungeon.tasks.EnterDungeonTask;
import smart.dungeon.tasks.EquipItemsTask;
import smart.dungeon.tasks.JumpStairsTask;
import smart.dungeon.tasks.OpenDoorTask;
import smart.dungeon.tasks.PickupItemsTask;
import smart.dungeon.tasks.StartDungeonTask;
import smart.dungeon.tasks.WalkToRoomTask;
import smart.dungeon.util.GameVariables;

@Manifest(name = "SmartDungeon", authors = "Lil Str Kid", version = 1.0, description = "Runescape dungeoneering bot", premium = true)
public class SmartDungeon extends ActiveScript implements PaintListener,
		MessageListener, MouseMotionListener, MouseListener {

	private final static LinkedList<Object> plugins = new LinkedList<Object>();
	private final static LinkedList<Object> puzzles = new LinkedList<Object>();

	static {
		/*
		 * Dungeon
		 */
		plugins.add(new EnterDungeonTask());
		plugins.add(new StartDungeonTask());
		plugins.add(new EquipItemsTask());
		plugins.add(new PickupItemsTask());
		plugins.add(new OpenDoorTask());
		plugins.add(new AttackNpcTask());
		plugins.add(new ChangeAttackStyleTask());
		plugins.add(new EatFoodTask());
		plugins.add(new WalkToRoomTask());
		plugins.add(new EndDungeon());
		plugins.add(new JumpStairsTask());

		/*
		 * Bosses
		 */
		plugins.add(new AsteaFrostwebBoss());// TODO: Improve
		plugins.add(new BloodChillerBoss());
		plugins.add(new GluttonousBehemothBoss());
		plugins.add(new IcyBonesBoss());
		plugins.add(new LuminescentIcefieldBoss());// TODO: Improve
		plugins.add(new PlaneFreezerBoss());// TODO: Improve

		plugins.add(new HobgoblinGeomancerBoss());
		// plugins.add(new BulwarkBeastBoss());
		// plugins.add(new SkeletalHordeBoss());
		// plugins.add(new UnholyCursebearerBoss());

		plugins.add(new LexicusRunewrightBoss());
		// plugins.add(new StompBoss());
		plugins.add(new SagittareBoss());
		plugins.add(new RammernautBoss());
		// plugins.add(new RiftSplitterBoss());
		// plugins.add(new NightGazerKhighorahk());

		/*
		 * Puzzles
		 */
		puzzles.add(new PondSkaterPuzzle());
	}

	@Override
	protected void setup() {
		PaintListenerHandler.load();

		GameVariables.setComplexity(4);
		GameVariables.setDungeonSize("Small");
		GameVariables.setSwitchStyles();

		for (Object plugins : SmartDungeon.getPlugins()) {
			Strategy plugin = new Strategy((Condition) plugins, (Task) plugins);
			this.provide(plugin);
		}

		for (Object puzzles : SmartDungeon.getPuzzles()) {
			Strategy puzzle = new Strategy((Condition) puzzles, (Task) puzzles);
			this.provide(puzzle);
		}
	}

	@Override
	public void onRepaint(Graphics arg0) {
		final PaintListenerHandler paintListenerHandler = new PaintListenerHandler(
				arg0);
		paintListenerHandler.execute();
	}

	@Override
	public void messageReceived(MessageEvent arg0) {
		final MessageListenerHandler messageListenerHandler = new MessageListenerHandler(
				arg0);
		messageListenerHandler.execute();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		PaintController.mouseClicked(arg0);

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		PaintController.mouseEntered(arg0);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		PaintController.mouseExited(arg0);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		PaintController.mousePressed(arg0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		PaintController.mouseReleased(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		PaintController.mouseMoved(e);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		PaintController.mouseDragged(arg0);
	}

	public static LinkedList<Object> getPlugins() {
		return plugins;
	}

	public static LinkedList<Object> getPuzzles() {
		return puzzles;
	}
}
