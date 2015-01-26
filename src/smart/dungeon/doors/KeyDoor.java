package smart.dungeon.doors;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;
import smart.dungeon.util.GameConstants;

public class KeyDoor extends Door {

	private int key;

	public KeyDoor(SceneObject door) {
		super(door, KEY);
	}

	@Override
	public boolean canOpen() {
		if (isLocked() && isOpen()) {
			setOpened(false);
		}
		if (isOpen() && connector > -1) {
			return false;
		}
		if (Dungeon.getCurrentRoom().contains(location)
				&& getTopAt(location, getId()) != null) {
			setLocked(true);
		}
		if (!isLocked()) {
			return true;
		}
		for (int i = 0; i < GameConstants.KEY_DOORS.length; i++) {
			for (int l = 0; l < GameConstants.KEY_DOORS[i].length; l++) {
				if (id == GameConstants.KEY_DOORS[i][l]
						&& Keys.hasKey(GameConstants.KEYS[i][l])) {
					key = GameConstants.KEYS[i][l];
					return true;
				}
			}
		}
		return false;
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
			if (!isLocked()) {
				SceneObject object = SceneEntities.getNearest(getId()
						+ Dungeon.getDoorDifferential());
				object.interact("Open");
				int timeout = 0;
				while (Dungeon.getCurrentRoom().contains(
						Players.getLocal().getLocation())
						&& ++timeout <= 15) {
					Time.sleep(100);
					if (Game.getClientState() == 12) {
						break;
					}
					if (Players.getLocal().isMoving()) {
						timeout = 0;
					}
				}
			} else {
				SceneObject object = getTopAt(location, getId());
				object.interact("Unlock");
				int timeout = 0;
				while (getTopAt(location, getId()) != null && ++timeout <= 15) {
					Time.sleep(100);
					if (Players.getLocal().isMoving()
							|| Players.getLocal().getAnimation() != -1) {
						timeout = 0;
					}
				}
				if (getTopAt(location, getId()) == null) {
					setLocked(false);
					Keys.removeKey(key);
					Time.sleep(Random.nextInt(400, 600));
				}
			}
		}
		if (!Dungeon.getCurrentRoom()
				.contains(Players.getLocal().getLocation())) {
			Dungeon.setLastDoorOpended(this);
		}
	}
}