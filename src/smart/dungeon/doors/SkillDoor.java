package smart.dungeon.doors;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;
import smart.dungeon.util.GameConstants;
import smart.dungeon.util.GameVariables;
import smart.dungeon.util.Misc;

public class SkillDoor extends Door {

	public static boolean canOpen = true;

	public SkillDoor(SceneObject door) {
		super(door, SKILL);
	}

	@Override
	public boolean canOpen() {
		if (isOpen() && connector > -1) {
			return false;
		}
		return !isOpen()
				&& canOpen
				&& !(Misc.arrayContains(GameConstants.DARK_SPIRIT, getId()) && !GameVariables
						.getPrayerDoor());
	}

	@Override
	public void open() {
		if (!Dungeon.getCurrentRoom().contains(this)) {
			return;
		}
		if (!getDoor().isOnScreen()) {
			WalkingApi.walkTileMM(getLocation(), true);
			int timeout = 0;
			while (!getDoor().isOnScreen() && ++timeout <= 15) {
				Time.sleep(100);
				if (Players.getLocal().isMoving()) {
					timeout = 0;
				}
			}
		} else {
			SceneObject object = getTopAt(location, getId());
			object.click(true);
			int timeout = 0;
			while (Dungeon.getCurrentRoom().contains(
					Players.getLocal().getLocation())
					&& ++timeout <= 15) {
				Time.sleep(100);
				if (Game.getClientState() == 12) {
					break;
				}
				if (Players.getLocal().isMoving()
						|| Players.getLocal().getAnimation() != -1) {
					timeout = 0;
				}
			}
			if (!Dungeon.getCurrentRoom().contains(
					Players.getLocal().getLocation())) {
				Dungeon.setLastDoorOpended(this);
			}
		}
	}
}