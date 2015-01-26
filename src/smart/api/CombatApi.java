package smart.api;

import java.util.ArrayList;

import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;

import smart.dungeon.Equipable;
import smart.dungeon.NpcDefinitions;

public class CombatApi {

	public static final int PARENT_IFACE = 884;
	public static final int STYLE_ONE_IFACE = 7;
	public static final int STYLE_TWO_IFACE = 8;
	public static final int STYLE_THREE_IFACE = 9;
	public static final int STYLE_FOUR_IFACE = 10;
	public static final int SETTING_COMBAT_STYLE = 43;

	private static enum Weapons {
		RAPIER(new String[] { "stab", "stab", "slash", "stab" }), WARHAMMER(
				new String[] { "crush", "crush", "crush" }), LONGSWORD(
				new String[] { "slash", "slash", "stab", "slash" }), BATTLEAXE(
				new String[] { "slash", "slash", "slash", "crush" }), SPEAR(
				new String[] { "stab", "slash", "crush", "stab" }), MAUL(
				new String[] { "crush", "crush", "crush" }), H_SWORD(
				new String[] { "slash", "slash", "crush", "slash" }), LONGBOW(
				new String[] { "range" }), SHORTBOW(new String[] { "range" }), NOVALUE(
				new String[] {});
		private String[] ATTACK_STYLES;

		private Weapons(String[] attackStyles) {
			ATTACK_STYLES = attackStyles;
		}

		public static Weapons toValue(String value) {
			try {
				return Weapons.valueOf(value);
			} catch (Exception ignored) {
				return NOVALUE;
			}
		}

		public String[] attackStyles() {
			return ATTACK_STYLES;
		}
	}

	public static boolean getAutoRelatiateSelected() {
		return Settings.get(172) == 0;
	}

	public static void setAttackStyle(String style) {
		if (haveStyle(style)) {
			Tabs.ATTACK.open();
			for (int i = 0; Tabs.getCurrent() != Tabs.ATTACK && i <= 15; i++)
				Time.sleep(100);
			Widgets.get(PARENT_IFACE, getStyleIndex(style)).click(true);
			Time.sleep(Random.nextInt(500, 600));
			Tabs.INVENTORY.open();
		}
	}

	public static void doPrayerFor(NpcDefinitions enemy) {
		PrayerApi.Prayer prayer = styleToPrayer(enemy.recommendedPrayer());
		if (canUsePrayer(prayer) && !prayer.isSelected()) {
			prayer.setActivated(true);
			Time.sleep(Random.nextInt(300, 400));
		}
	}

	public static boolean canUsePrayer(PrayerApi.Prayer prayer) {
		if (prayer == null)
			return false;
		return Skills.getRealLevel(Skills.PRAYER) >= prayer.getRequiredLevel();
	}

	public static PrayerApi.Prayer styleToPrayer(int style) {
		switch (style) {
		case 0:
			return (PrayerApi.isUsingRegularPrayers()) ? PrayerApi.Prayer.PROTECT_FROM_MELEE
					: PrayerApi.Prayer.DEFLECT_MELEE;
		case 1:
			return (PrayerApi.isUsingRegularPrayers()) ? PrayerApi.Prayer.PROTECT_FROM_MISSILES
					: PrayerApi.Prayer.DEFLECT_MISSILE;
		case 2:
			return (PrayerApi.isUsingRegularPrayers()) ? PrayerApi.Prayer.PROTECT_FROM_MAGIC
					: PrayerApi.Prayer.DEFLECT_MAGIC;
		}
		return null;
	}

	public static String[] getAttackActions() {
		ArrayList<String> actions = new ArrayList<String>();
		try {
			actions.add(Widgets.get(PARENT_IFACE, STYLE_ONE_IFACE).getActions()[0]);
			actions.add(Widgets.get(PARENT_IFACE, STYLE_TWO_IFACE).getActions()[0]);
			actions.add(Widgets.get(PARENT_IFACE, STYLE_THREE_IFACE)
					.getActions()[0]);
			actions.add(Widgets.get(PARENT_IFACE, STYLE_FOUR_IFACE)
					.getActions()[0]);
		} catch (NullPointerException ignored) {
		}
		return actions.toArray(new String[0]);
	}

	public static int getActionIndex(String action) {
		String[] actions = getAttackActions();
		for (int i = 0; i < actions.length; i++)
			if (actions[i].equalsIgnoreCase(action))
				return 7 + i;
		return -1;
	}

	public static boolean setFightMode(int fightMode) {
		if (fightMode != getFightMode()) {
			Tabs.ATTACK.open();
			if (fightMode == 0) {
				return Widgets.get(PARENT_IFACE, STYLE_ONE_IFACE).click(true);
			} else if (fightMode == 1) {
				return Widgets.get(PARENT_IFACE, STYLE_TWO_IFACE).click(true);
			} else if (fightMode == 2
					|| (fightMode == 3 && Widgets.get(PARENT_IFACE,
							STYLE_FOUR_IFACE).getActions() == null)) {
				return Widgets.get(PARENT_IFACE, STYLE_THREE_IFACE).click(true);
			} else if (fightMode == 3) {
				return Widgets.get(PARENT_IFACE, STYLE_FOUR_IFACE).click(true);
			}
		}
		return false;
	}

	public static boolean haveStyle(String style) {
		return getStyleIndex(style) != -1;
	}

	public static int getStyleIndex(String style) {
		String[] styles = getAttackStyles();
		for (int i = 0; i < styles.length; i++)
			if (styles[i].equalsIgnoreCase(style))
				return 7 + i;
		return -1;
	}

	public static String[] getAttackStyles() {
		return Weapons.toValue(
				new Equipable(EquipmentApi.getItem(EquipmentApi.WEAPON)
						.getName()).getItemName()).attackStyles();
	}

	public static int getFightMode() {
		return Settings.get(SETTING_COMBAT_STYLE);
	}

	public static String currentAttackStyle() {
		return getAttackStyles()[getFightMode()];
	}

}
