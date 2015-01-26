package smart.dungeon.listener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.net.URL;

import javax.imageio.ImageIO;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;

import smart.dungeon.Dungeon;
import smart.dungeon.listener.paint.PaintButton;
import smart.dungeon.listener.paint.PaintColumnLayout;
import smart.dungeon.listener.paint.PaintController;
import smart.dungeon.listener.paint.PaintFancyButton;
import smart.dungeon.listener.paint.PaintFrame;
import smart.dungeon.listener.paint.PaintSkill;
import smart.dungeon.util.Misc;
import smart.dungeon.util.Skill;

public class PaintListenerHandler {

	private Graphics graphics;

	public PaintListenerHandler(Graphics graphics) {
		this.graphics = graphics;
	}

	private static Image backGround = null;
	public static boolean showPaint = true;
	private final RenderingHints antialiasing = new RenderingHints(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	private final Color color = new Color(0, 0, 0);
	public static int menuIndex = 0;

	private static long startTime;
	private static Skill attSkill;
	private static Skill strSkill;
	private static Skill defSkill;
	private static Skill rangeSkill;
	private static Skill magicSkill;
	private static Skill dungSkill;
	private static Skill conSkill;

	public static void load() {
		startTime = System.currentTimeMillis();
		attSkill = new Skill(Skills.ATTACK);
		strSkill = new Skill(Skills.STRENGTH);
		defSkill = new Skill(Skills.DEFENSE);
		rangeSkill = new Skill(Skills.RANGE);
		magicSkill = new Skill(Skills.MAGIC);
		dungSkill = new Skill(Skills.DUNGEONEERING);
		conSkill = new Skill(Skills.CONSTITUTION);
		PaintController.reset();
		PaintController.addComponent(new PaintFancyButton(10, 348, 67, -1,
				"Overview", PaintFancyButton.ColorScheme.GRAPHITE) {
			public void onPress() {
				menuIndex = 0;
			}
		});
		PaintController.addComponent(overviewFrame);
		PaintController.addComponent(new PaintFancyButton(80, 348, 67, -1,
				"Options", PaintFancyButton.ColorScheme.GRAPHITE) {
			public void onPress() {
				menuIndex = 1;
			}
		});
		optionsFrame.addComponent(new PaintButton(15, 376, "Bury Bones: ",
				new String[] { "Yes", "No" }) {
			public void onStart() {
				startIndex = 0;
			}

			@Override
			public void onPress() {
			}
		});
		optionsFrame.addComponent(new PaintButton(15, 391, "Pure Mode: ",
				new String[] { "Yes", "No" }) {
			public void onStart() {
				startIndex = 0;
			}

			public void onPress() {
			}
		});
		optionsFrame.addComponent(new PaintButton(15, 406, "Switch Styles: ",
				new String[] { "Yes", "No" }) {
			public void onStart() {
				startIndex = 0;
			}

			public void onPress() {
			}
		});
		optionsFrame.addComponent(new PaintButton(15, 421, "Use Prayers: ",
				new String[] { "Yes", "No" }) {
			public void onStart() {
				startIndex = 0;
			}

			public void onPress() {
			}
		});
		PaintController.addComponent(optionsFrame);
		PaintController.addComponent(new PaintFancyButton(150, 348, 67, -1,
				"Dungeon", PaintFancyButton.ColorScheme.GRAPHITE) {
			public void onPress() {
				menuIndex = 2;
			}
		});
		PaintController.addComponent(dungeonFrame);
		PaintController.addComponent(new PaintFancyButton(220, 348, 67, -1,
				"Skills", PaintFancyButton.ColorScheme.GRAPHITE) {
			public void onPress() {
				menuIndex = 3;
			}
		});
		PaintController.addComponent(skillsFrame);
		PaintController.addComponent(new PaintFancyButton(290, 348, 67, -1,
				"About", PaintFancyButton.ColorScheme.GRAPHITE) {
			public void onPress() {
				menuIndex = 4;
			}
		});
		PaintController.addComponent(aboutFrame);
		PaintController.addComponent(new PaintFancyButton(442, 348, 67, -1,
				"Hide", PaintFancyButton.ColorScheme.GRAPHITE) {
			public void onStart() {
				forceMouse = true;
				forcePaint = true;
			}

			public void onPress() {
				text = (text.equals("Hide")) ? "Show" : "Hide";
				PaintListenerHandler.showPaint = !showPaint;
			}
		});
		try {
			backGround = ImageIO.read(new URL(
					"http://dl.dropbox.com/u/16998689/background.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute() {
		Graphics2D g = (Graphics2D) getGraphics();
		if (showPaint) {
			g.setRenderingHints(antialiasing);
			g.drawImage(backGround, 7, 345, null);
			String timeRan = Misc.parseTime(System.currentTimeMillis()
					- startTime);
			PaintColumnLayout mainLayoutColOne = null;
			PaintColumnLayout mainLayoutColTwo = null;
			PaintColumnLayout dungeonLayout = null;
			mainLayoutColOne = new PaintColumnLayout(15, 376, new String[] {
					"Status:", "Total running:", "Dungeons completed:",
					"Dungeons aborted:", "Dungeons per hour:", "Times died:",
					"Prestige count: " }, new String[] {
					Dungeon.getStatus(),
					timeRan,
					String.valueOf(Dungeon.getDungeonsCompleted()),
					String.valueOf(Dungeon.getDungeonsAborted()),
					String.valueOf(Misc.calculatePerHour(
							Dungeon.getDungeonsCompleted(), startTime)),
					String.valueOf(Dungeon.getTotalDeaths()), "N/A" });
			mainLayoutColTwo = new PaintColumnLayout(300, 376, 5,
					new String[] { "Current level:", "Experience gained:",
							"Experience per hour:", "Time to level:",
							"Tokens gained:" }, new String[] {
							Skills.getRealLevel(Skills.DUNGEONEERING) + " ("
									+ dungSkill.getLevelsGained() + ")",
							Misc.format(dungSkill.getExperienceGained()),
							Misc.format(dungSkill.getExperiencePerHour()),
							dungSkill.getTimeToLevel(),
							Misc.format(Dungeon.getTokens()) });
			dungeonLayout = new PaintColumnLayout(15, 376, new String[] {
					"Current dungeon time:", "Current deaths:",
					"Fastest dungeon:", "Slowest dungeon:", "Current floor:" },
					new String[] { "N/A", "N/A", "N/A", "N/A", "N/A" });
			overviewFrame.addComponent(mainLayoutColOne);
			overviewFrame.addComponent(mainLayoutColTwo);
			dungeonFrame.addComponent(dungeonLayout);
			PaintController.onRepaint(getGraphics());
			overviewFrame.removeComponent(mainLayoutColOne);
			overviewFrame.removeComponent(mainLayoutColTwo);
			dungeonFrame.removeComponent(dungeonLayout);
			Skill[] skillIndex = { attSkill, strSkill, defSkill, rangeSkill,
					magicSkill, conSkill };
			int y = 370;
			for (Skill skill : skillIndex) {
				if (skill.getExperienceGained() > 0) {
					PaintSkill skillComp = new PaintSkill(15, y,
							skill.getSkill(), PaintSkill.ColorScheme.GRAPHITE);
					if (!skillsFrame.containsComponent(skillComp)) {
						skillsFrame.addComponent(skillComp);
					}
					y += 20;
				}
			}
		}
		final Point loc = Mouse.getLocation();
		g.setColor(color);
		g.drawLine(loc.getLocation().x - 6, loc.getLocation().y,
				loc.getLocation().x + 6, loc.getLocation().y);
		g.drawLine(loc.getLocation().x, loc.getLocation().y - 6,
				loc.getLocation().x, loc.getLocation().y + 6);
	}

	private static PaintFrame overviewFrame = new PaintFrame("overview") {
		public boolean shouldPaint() {
			return menuIndex == 0;
		}

		public boolean shouldHandleMouse() {
			return shouldPaint();
		}
	};

	private static PaintFrame optionsFrame = new PaintFrame("options") {
		public boolean shouldPaint() {
			return menuIndex == 1;
		}

		public boolean shouldHandleMouse() {
			return shouldPaint();
		}
	};

	private static PaintFrame dungeonFrame = new PaintFrame("dungeon") {
		public boolean shouldPaint() {
			return menuIndex == 2;
		}

		public boolean shouldHandleMouse() {
			return shouldPaint();
		}
	};

	private static PaintFrame skillsFrame = new PaintFrame("skills") {
		public boolean shouldPaint() {
			return menuIndex == 3;
		}

		public boolean shouldHandleMouse() {
			return shouldPaint();
		}
	};

	private static PaintFrame aboutFrame = new PaintFrame("about") {
		public boolean shouldPaint() {
			return menuIndex == 4;
		}

		public boolean shouldHandleMouse() {
			return shouldPaint();
		}
	};

	private Graphics getGraphics() {
		return graphics;
	}

}
