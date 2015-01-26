package smart.dungeon.util;

import org.powerbot.game.api.methods.tab.Skills;

public class Skill {
	private final long startime;
	private final int startxp;
	private final int startLevel;
	private final int skillint;

	public Skill(int skillInt) {
		startime = System.currentTimeMillis();
		startxp = Skills.getExperience(skillInt);
		startLevel = Skills.getRealLevel(skillInt);
		skillint = skillInt;
	}

	public int getExperienceGained() {
		return Skills.getExperience(skillint) - startxp;
	}

	public int getLevelsGained() {
		return Skills.getRealLevel(skillint) - startLevel;
	}

	public int getExperienceToLevel() {
		return getExperienceToNextLevel(skillint);
	}

	public static int getPercentToNextLevel(int index) {
		int lvl = Skills.getRealLevel(index);
		if (lvl == 99) {
			return 0;
		}
		int xpTotal = Skills.XP_TABLE[lvl + 1] - Skills.XP_TABLE[lvl];
		if (xpTotal == 0) {
			return 0;
		}
		int xpDone = Skills.getExperience(index) - Skills.XP_TABLE[lvl];
		return 100 * xpDone / xpTotal;
	}

	public static int getExperienceToNextLevel(int index) {
		int lvl = Skills.getRealLevel(index);
		if (lvl == 99) {
			return 0;
		}
		int xpTotal = Skills.XP_TABLE[lvl + 1] - Skills.getExperience(index);
		if (xpTotal == 0) {
			return 0;
		}
		return xpTotal;
	}

	public int getExperiencePerHour() {
		return (int) ((getExperienceGained()) * 3600000D / (System
				.currentTimeMillis() - startime));
	}

	public String getTimeToLevel() {
		String ttl = "N/A";
		long ttlCalculations;
		if (getExperiencePerHour() != 0) {
			ttlCalculations = (long) (getExperienceToLevel() * 3600000D)
					/ getExperiencePerHour();
			ttl = Misc.parseTime(ttlCalculations);
		}
		return ttl;
	}

	public int getSkill() {
		return skillint;
	}
}
