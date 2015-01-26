package smart.dungeon;

import org.powerbot.game.api.wrappers.interactive.NPC;

import smart.dungeon.rooms.Room;

public class NpcDefinitions {
	private static enum Monsters {

		HYRDA(new String[] { "slash", "ranged" }, new int[] { 1 }), ICEFIEND(
				new String[] { "stab", "fire spells" }, new int[] { 1, 2 }), ICE_WARRIOR(
				new String[] { "crush", "fire spells" }, new int[] { 0 }), ICE_SPIDER(
				new String[] { "crush", "fire spells" }, new int[] { 0 }, 0,
				0.9), THROWER_TROLL(new String[] { "slash", "fire spells" },
				new int[] { 0 }), ICE_GIANT(new String[] { "crush",
				"fire spells" }, new int[] { 0 }), ICE_TROLL(new String[] {
				"stab", "fire spells" }, new int[] { 0 }), ICE_ELEMENTAL(
				new String[] { "crush", "fire spells" }, new int[] { 0, 1, 2 }), FROST_DRAGON(
				new String[] { "stab", "fire spells" }, new int[] { 0, 2 }, 2,
				1.25),

		EARTH_WARRIOR(new String[] { "crush", "magic" }, new int[] { 0 }), GREEN_DRAGON(
				new String[] { "stab" }, new int[] { 0, 2 }, 2, 1.3), HILL_GIANT(
				new String[] { "stab" }, new int[] { 0 }), CRAWLING_HAND(
				new String[] { "stab", "crush" }, new int[] { 0 }),

		BAT(new String[] { "stab", "crush", "range" }, new int[] { 0 }), GIANT_BAT(
				new String[] { "stab", "crush", "range" }, new int[] { 0 }), IRON_DRAGON(
				new String[] { "stab", "fire spells" }, new int[] { 0, 2 }, 2,
				1.5),

		ANIMATED_BOOKS(new String[] { "slash" }, new int[] { 2 }), FIRE_GIANT(
				new String[] { "stab", "crush" }, new int[] { 0 }), HELLHOUND(
				new String[] { "stab", "crush" }, new int[] { 0 }), LESSER_DEMON(
				new String[] { "slash", "magic" }, new int[] { 0, 2 }, 0), GREATER_DEMON(
				new String[] { "slash", "magic" }, new int[] { 0, 2 }, 0), BLACK_DEMON(
				new String[] { "slash", "magic" }, new int[] { 0, 2 }, 0, 1.3), NECROMANCER(
				new String[] { "slash", "stab", "range" }, new int[] { 2 }), RED_DRAGON(
				new String[] { "stab", "range" }, new int[] { 0, 2 }, 2, 1.4), GHOST(
				new String[] { "crush" }, new int[] { 0, 2 }, 2, 1.2),

		FORGOTTEN_WARRIOR(new String[] { "magic", "stab", "crush", "slash" },
				new int[] { 0 }, 0, 1.15), FORGOTTEN_RANGER(new String[] {
				"slash", "stab" }, new int[] { 1 }), FORGOTTEN_MAGE(
				new String[] { "range", "slash", "stab" }, new int[] { 2 }), HOBGOBLIN(
				new String[] { "stab", "crush" }, new int[] { 0 }), GIANT_RAT(
				new String[] { "slash" }, new int[] { 0 }), MYSTERIOUS_SHADE(
				new String[] { "slash" }, new int[] { 2, 1 }, -1, 1.35), DUNGEON_SPIDER(
				new String[] { "crush" }, new int[] { 0 }, -1, 9001), CAVE_SLIME(
				new String[] { "magic" }, new int[] { 0 }), SKELETON(
				new String[] { "crush" }, new int[] { 0 }), SKELETON_WARRIOR(
				new String[] { "crush" }, new int[] { 0 }), SKELETON_RANGER(
				new String[] { "crush", "magic" }, new int[] { 1 }), SKELETON_MAGE(
				new String[] { "crush", "range" }, new int[] { 2 }), ZOMBIE(
				new String[] { "slash", "magic" }, new int[] { 0, 1 }), PYREFIEND(
				new String[] { "stab", "water spells" }, new int[] { 2 }), MERCENARY_LEADER(
				new String[] { "slash", "range" }, new int[] { 2 }), GUARD_DOG(
				new String[] { "stab" }, new int[] { 0 }), BRUTE(new String[] {
				"stab", "crush", "magic" }, new int[] { 0 }),

		SPIRITUAL_GUARDIAN(new String[] { "magic" }, new int[] { 0 }), NIGHT_SPIDER(
				new String[] { "crush" }, new int[] { 0 }, -1, 9001), SEEKER(
				new String[] { "stab" }, new int[] { 2 }), EDIMMU(
				new String[] { "stab" }, new int[] { 0, 2 }), SOULGAZER(
				new String[] { "range", "stab" }, new int[] { 2 }), JELLY(
				new String[] { "slash", "crush" }, new int[] { 2 }),

		GLUTTONOUS_BEHEMOTH(new String[] { "crush", "magic" }, new int[] { 0,
				1, 2 }, 2), ASTEA_FROSTWEB(new String[] { "range", "slash",
				"fire spells" }, new int[] { 0, 2 }, 2), ICY_BONES(
				new String[] { "stab" }, new int[] { 0, 1, 2 }, 0), LUMINESCENT_ICEFIEND(
				new String[] { "stab", "fire spells" }, new int[] { 0, 1 }, 0), PLANEFREEZER_LAKHRAHNAZ(
				new String[] { "stab", "range" }, new int[] { 0, 1, 2 }, 2), TOKASH_THE_BLOODCHILLER(
				new String[] { "fire spells", "magic", "crush" }, new int[] {
						0, 2 }, 2),

		BULWARK_BEAST(new String[] { "stab" }, new int[] { 0, 1, 2 }, 2), UNHOLY_CURSEBEARER(
				new String[] { "crush", "range" }, new int[] { 0, 2 }, 2),

		NOVALUE(new String[] { "" }, new int[] { 0 });

		private String[] weaknesses;
		private int[] attackStyles;
		private int recomendedPray = -1;
		private double priorityMultiplier = 1;

		Monsters(String[] weaknesses, int[] attackStyles) {
			this.weaknesses = weaknesses;
			this.attackStyles = attackStyles;
		}

		Monsters(String[] weaknesses, int[] attackStyles, int recomendedPray) {
			this.weaknesses = weaknesses;
			this.attackStyles = attackStyles;
			this.recomendedPray = recomendedPray;
		}

		Monsters(String[] weaknesses, int[] attackStyles, int recomendedPray,
				double priorityMultiplier) {
			this.weaknesses = weaknesses;
			this.attackStyles = attackStyles;
			this.recomendedPray = recomendedPray;
			this.priorityMultiplier = priorityMultiplier;
		}

		public double priorityMultiplier() {
			return priorityMultiplier;
		}

		public int recomendedPray() {
			return recomendedPray;
		}

		public int[] attackStyles() {
			return attackStyles;
		}

		public String[] weaknesses() {
			return weaknesses;
		}
	}

	private final int cbLvl;
	private final String name;
	private final Room parent;
	private final int id;
	private Monsters monster;
	private static final double OPPOSITE_STYLE = 1.25;
	private static final double SAME_STYLE = 1.0;
	private static final double LESSER_STYLE = 0.75;

	public NpcDefinitions(NPC NPC, Room parent) {
		cbLvl = NPC.getLevel();
		name = NPC.getName();
		this.parent = parent;
		id = NPC.getId();
		try {
			monster = Monsters.valueOf(name.replaceAll(" ", "_")
					.replaceAll("[-']", "").toUpperCase());
		} catch (Exception ignored) {
			monster = Monsters.NOVALUE;
		}
	}

	public NpcDefinitions(NPC NPC) {
		this(NPC, null);
	}

	public int cbLvl() {
		return cbLvl;
	}

	public String name() {
		return name;
	}

	public Room parent() {
		return parent;
	}

	public int id() {
		return id;
	}

	public int getPriority() {
		double combatOffset = 1;
		switch (Dungeon.getCombatStyle()) {
		case MELEE:
			switch (monster.attackStyles()[0]) {
			case 0:
				combatOffset = SAME_STYLE;
				break;
			case 1:
				combatOffset = LESSER_STYLE;
				break;
			case 2:
				combatOffset = OPPOSITE_STYLE;
				break;
			}
			break;
		case RANGED:
			switch (monster.attackStyles()[0]) {
			case 0:
				combatOffset = OPPOSITE_STYLE;
				break;
			case 1:
				combatOffset = SAME_STYLE;
				break;
			case 2:
				combatOffset = LESSER_STYLE;
				break;
			}
			break;
		case MAGIC:
			switch (monster.attackStyles()[0]) {
			case 0:
				combatOffset = LESSER_STYLE;
				break;
			case 1:
				combatOffset = OPPOSITE_STYLE;
				break;
			case 2:
				combatOffset = SAME_STYLE;
				break;
			}

		}
		return (int) ((cbLvl * monster.priorityMultiplier()) * combatOffset);
	}

	public int recommendedPrayer() {
		return monster.recomendedPray();
	}

	public int[] attackStyles() {
		return monster.attackStyles();
	}

	public String[] weaknesses() {
		return monster.weaknesses();
	}
}