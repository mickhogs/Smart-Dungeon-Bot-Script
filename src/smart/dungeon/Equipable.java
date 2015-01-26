package smart.dungeon;

import java.util.Arrays;

import org.powerbot.game.api.methods.tab.Skills;

import smart.api.EquipmentApi;

public class Equipable {

	public static enum Type {
		WEAPON, ARMOUR, NONEQUIPABLE
	}

	public static enum Location {
		HELM, CHEST, HANDS, WEAPON, OFFHAND, LEGS, FEET, AMMO, NOVALUE;

		public static Location toEnum(String str) {
			try {
				return valueOf(str);
			} catch (Exception ex) {
				return NOVALUE;
			}
		}
	}

	private static enum Equipment {
		LONGSWORD(Location.WEAPON, ItemHandler.Style.MELEE,
				new int[] { Skills.ATTACK }, false, Type.WEAPON), RAPIER(
				Location.WEAPON, ItemHandler.Style.MELEE,
				new int[] { Skills.ATTACK }, false, Type.WEAPON), WARHAMMER(
				Location.WEAPON, ItemHandler.Style.MELEE,
				new int[] { Skills.ATTACK }, false, Type.WEAPON), SPEAR(
				Location.WEAPON, ItemHandler.Style.MELEE,
				new int[] { Skills.ATTACK }, true, Type.WEAPON), BATTLEAXE(
				Location.WEAPON, ItemHandler.Style.MELEE,
				new int[] { Skills.ATTACK }, false, Type.WEAPON), H_SWORD(
				Location.WEAPON, ItemHandler.Style.MELEE,
				new int[] { Skills.ATTACK }, true, Type.WEAPON), MAUL(
				Location.WEAPON, ItemHandler.Style.MELEE,
				new int[] { Skills.STRENGTH }, true, Type.WEAPON), SHORTBOW(
				Location.WEAPON, ItemHandler.Style.RANGED,
				new int[] { Skills.RANGE }, true, Type.WEAPON), LONGBOW(
				Location.WEAPON, ItemHandler.Style.RANGED,
				new int[] { Skills.RANGE }, true, Type.WEAPON), ARROWS(
				Location.AMMO, ItemHandler.Style.RANGED,
				new int[] { Skills.RANGE }, false, Type.WEAPON), PLATELEGS(
				Location.LEGS, ItemHandler.Style.MELEE,
				new int[] { Skills.DEFENSE }, false, Type.ARMOUR), PLATESKIRT(
				Location.LEGS, ItemHandler.Style.MELEE,
				new int[] { Skills.DEFENSE }, false, Type.ARMOUR), CHAPS(
				Location.LEGS, ItemHandler.Style.RANGED, new int[] {
						Skills.DEFENSE, Skills.RANGE }, false, Type.ARMOUR), BOTTOM(
				Location.LEGS, ItemHandler.Style.MAGIC, new int[] {
						Skills.DEFENSE, Skills.MAGIC }, false, Type.ARMOUR), KITESHIELD(
				Location.OFFHAND, ItemHandler.Style.MELEE,
				new int[] { Skills.DEFENSE }, false, Type.ARMOUR), GAUNTLETS(
				Location.HANDS, ItemHandler.Style.MELEE,
				new int[] { Skills.DEFENSE }, false, Type.ARMOUR), VAMBRACES(
				Location.HANDS, ItemHandler.Style.RANGED, new int[] {
						Skills.DEFENSE, Skills.RANGE }, false, Type.ARMOUR), GLOVES(
				Location.HANDS, ItemHandler.Style.MELEE, new int[] {
						Skills.DEFENSE, Skills.MAGIC }, false, Type.ARMOUR), BOOTS(
				Location.FEET, ItemHandler.Style.MELEE, new int[] {
						Skills.DEFENSE, Skills.MAGIC }, false, Type.ARMOUR), PLATEBODY(
				Location.CHEST, ItemHandler.Style.MELEE,
				new int[] { Skills.DEFENSE }, false, Type.ARMOUR), CHAINBODY(
				Location.CHEST, ItemHandler.Style.MELEE,
				new int[] { Skills.DEFENSE }, false, Type.ARMOUR), BODY(
				Location.CHEST, ItemHandler.Style.RANGED, new int[] {
						Skills.DEFENSE, Skills.RANGE }, false, Type.ARMOUR), TOP(
				Location.CHEST, ItemHandler.Style.MAGIC, new int[] {
						Skills.DEFENSE, Skills.MAGIC }, false, Type.ARMOUR), FULL_HELM(
				Location.HELM, ItemHandler.Style.MELEE,
				new int[] { Skills.DEFENSE }, false, Type.ARMOUR), COIF(
				Location.HELM, ItemHandler.Style.RANGED, new int[] {
						Skills.DEFENSE, Skills.RANGE }, false, Type.ARMOUR), HOOD(
				Location.HELM, ItemHandler.Style.MAGIC, new int[] {
						Skills.DEFENSE, Skills.MAGIC }, false, Type.ARMOUR), DAGGER(
				Location.WEAPON, ItemHandler.Style.MELEE,
				new int[] { Skills.ATTACK }, false, Type.WEAPON), STAFF(
				Location.WEAPON, ItemHandler.Style.MAGIC, new int[] {
						Skills.ATTACK, Skills.MAGIC }, false, Type.WEAPON), NOVALUE(
				Location.NOVALUE, ItemHandler.Style.UNKNOWN, new int[] { -1 },
				false, Type.NONEQUIPABLE);

		private Location location;
		private ItemHandler.Style style;
		private int[] skills;
		private boolean twoHanded;
		private Type type;

		Equipment(Location location, ItemHandler.Style style, int[] skills,
				boolean twoHanded, Type type) {
			this.location = location;
			this.style = style;
			this.skills = skills;
			this.twoHanded = twoHanded;
			this.type = type;
		}

		public static Equipment toEnum(String str) {
			try {
				return valueOf(str);
			} catch (Exception ex) {
				return NOVALUE;
			}
		}

		public Location location() {
			return location;
		}

		public ItemHandler.Style style() {
			return style;
		}

		public int[] skills() {
			return skills;
		}

		public boolean isTwoHanded() {
			return twoHanded;
		}

		public Type type() {
			return type;
		}
	}

	private final int tier;
	private final String name;
	private final Equipment item;

	public Equipable(String itemName) {
		if (itemName == null) {
			tier = -1;
			name = "Unknown";
			item = Equipment.NOVALUE;
			return;
		}
		name = itemName;
		tier = ItemHandler.getTier(name);
		item = Equipment.toEnum(ItemHandler.getItemName(name));
	}

	public int getTier() {
		return tier;
	}

	public ItemHandler.Style getStyle() {
		return item.style();
	}

	public Type getType() {
		return item.type();
	}

	public Location getLocation() {
		return item.location();
	}

	public String getName() {
		return name;
	}

	public Equipment getItem() {
		return item;
	}

	public String getItemName() {
		String item = name.replace(" (b)", "");
		String[] itemSplit = item.split(" ");
		itemSplit = Arrays.copyOfRange(itemSplit, 1, itemSplit.length);
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

	public int getEquipmentIndex() {
		switch (item.location()) {
		case HELM:
			return EquipmentApi.HEAD;
		case CHEST:
			return EquipmentApi.BODY;
		case LEGS:
			return EquipmentApi.LEGS;
		case HANDS:
			return EquipmentApi.HANDS;
		case FEET:
			return EquipmentApi.FEET;
		case OFFHAND:
			return EquipmentApi.SHIELD;
		case WEAPON:
			return EquipmentApi.WEAPON;
		case AMMO:
			return EquipmentApi.AMMO;
		default:
			return -1;
		}
	}

	public boolean twoHanded() {
		return item.isTwoHanded();
	}

	public boolean canWield() {
		int tierReq = tier * 10 - 10;
		if (tierReq < 1)
			tierReq = 1;
		if (tierReq > 99)
			tierReq = 99;
		for (int skill : item.skills())
			if (Skills.getRealLevel(skill) < tierReq)
				return false;
		return true;
	}
}