package smart.dungeon;

import java.util.Arrays;

import smart.api.EquipmentApi;

public class ItemHandler {

	private static enum MELEE_TIERS {
		NOVITE, BATHUS, MARMAROS, KRATONITE, FRACTITE, ZEPHYRIUM, ARGONITE, KATAGON, GORGONITE, PROMETHIUM, PRIMAL, NOVALUE;

		public static MELEE_TIERS toTier(String str) {
			try {
				return valueOf(str);
			} catch (Exception ex) {
				return NOVALUE;
			}
		}
	}

	private static enum RANGED_TIERS_ARMOUR {
		PROTOLEATHER, SUBLEATHER, PARALEATHER, ARCHLEATHER, DROMOLEATHER, SPINOLEATHER, GALILEATHER, STEGOLEATHER, MEGALEATHER, TYRANOLEATHER, SAGITTARIAN, NOVALUE;

		public static RANGED_TIERS_ARMOUR toTier(String str) {
			try {
				return valueOf(str);
			} catch (Exception ex) {
				return NOVALUE;
			}
		}
	}

	private static enum RANGED_TIERS_WEAPON {
		TANGLE_GUM, SEEPING_ELM, BLOOD_SPINDLE, UTUKU, SPINEBEAM, BOVISTRANGLR, THIGAT, CORPSETHORN, ENTGALLOW, GRAVE_CREEPER, SAGITTARIAN, NOVALUE;

		public static RANGED_TIERS_WEAPON toTier(String str) {
			try {
				return valueOf(str);
			} catch (Exception ex) {
				return NOVALUE;
			}
		}
	}

	private static enum MAGE_TIERS {
		SALVE, WILDERCRESS, BLIGHTLEAF, ROSEBLOOD, BRYLL, DUSKWEED, SOULBELL, ECTO, RUNIC, SPIRITBLOOM, CELESTIAL, NOVALUE;

		public static MAGE_TIERS toTier(String str) {
			try {
				return valueOf(str);
			} catch (Exception ex) {
				return NOVALUE;
			}
		}
	}

	public static String getItemName(String item) {
		if (item == null || item.length() < 1)
			return "";
		item = item.replace(" (b)", "");
		String[] itemSplit = item.split(" ");
		itemSplit = Arrays.copyOfRange(itemSplit,
				(item.toLowerCase().matches(".*(shortbow|longbow).*") && item
						.split(" ").length == 3) ? 2 : 1, itemSplit.length);
		item = "";
		for (String str : itemSplit)
			item += "_" + str.trim().toUpperCase().replaceAll("[0-9]", "");
		if (item.length() < 1)
			return "";
		if (item.contains(">"))
			item = item.split(">")[1];
		item = item.substring(1);
		return item;
	}

	public static boolean shouldEquip(Equipable toEquip, int equipmentIndex) {
		Equipable toCheck = null;
		if (toEquip.getLocation() == Equipable.Location.NOVALUE
				|| toEquip.getTier() == -1)
			return false;
		if (toEquip.getName().contains("(b)"))
			return true;
		else if (toEquip.getEquipmentIndex() == EquipmentApi.AMMO)
			return false;
		if (equipmentIndex == -1) {
			return false;
		}
		if (EquipmentApi.getItem(equipmentIndex).getId() != -1) {
			toCheck = new Equipable(EquipmentApi.getItem(equipmentIndex)
					.getName());
			if (toEquip.getItem() != toCheck.getItem()
					|| toEquip.getTier() <= toCheck.getTier()) {
				return false;
			}
		}
		if (toEquip.getStyle() == Dungeon.getCombatStyle()
				&& toEquip.canWield()
				&& toEquip.getEquipmentIndex() == equipmentIndex) {
			if (EquipmentApi.getItem(EquipmentApi.WEAPON).getId() != -1) {
				if ((new Equipable(EquipmentApi.getItem(EquipmentApi.WEAPON)
						.getName()).twoHanded() && toEquip.getEquipmentIndex() == EquipmentApi.SHIELD))
					return false;
				else if (EquipmentApi.getItem(EquipmentApi.SHIELD).getId() != -1
						&& toEquip.twoHanded())
					return false;
			}
			return true;

		}
		return false;
	}

	public static int getTier(String name) {
		name = name.replace(" (b)", "");
		String[] split = Arrays.copyOfRange(name.split(" "), 0, (name
				.toLowerCase().matches(".*(shortbow|longbow).*") && name
				.split(" ").length == 3) ? 2 : 1);
		String tier = "";
		for (String string : split)
			tier += string + "_";
		tier = tier.substring(0, tier.length() - 1);
		if (tier.contains(">"))
			tier = tier.split(">")[1];
		tier = tier.toUpperCase();
		if (MELEE_TIERS.toTier(tier) != MELEE_TIERS.NOVALUE)
			return MELEE_TIERS.valueOf(tier).ordinal() + 1;
		if (RANGED_TIERS_ARMOUR.toTier(tier) != RANGED_TIERS_ARMOUR.NOVALUE)
			return RANGED_TIERS_ARMOUR.valueOf(tier).ordinal() + 1;
		if (RANGED_TIERS_WEAPON.toTier(tier) != RANGED_TIERS_WEAPON.NOVALUE)
			return RANGED_TIERS_WEAPON.valueOf(tier).ordinal() + 1;
		if (MAGE_TIERS.toTier(tier) != MAGE_TIERS.NOVALUE)
			return MAGE_TIERS.valueOf(tier).ordinal() + 1;
		return -1;
	}

	public static enum Style {
		MELEE, RANGED, MAGIC, UNKNOWN
	}

	public static Style MELEE = ItemHandler.Style.MELEE;
	public static Style RANGED = ItemHandler.Style.RANGED;
	public static Style MAGIC = ItemHandler.Style.MAGIC;
	public static Style UNKNOWN = ItemHandler.Style.UNKNOWN;
}