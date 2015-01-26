package smart.dungeon.doors;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.SceneObject;

import smart.api.WalkingApi;
import smart.dungeon.Dungeon;
import smart.dungeon.TileGrabber;
import smart.dungeon.rooms.PuzzleRoom;
import smart.dungeon.rooms.Room;
import smart.dungeon.util.GameConstants;

public class NormalDoor extends Door {

	public NormalDoor(SceneObject door) {
		super(door, NORMAL);
	}

	@Override
	public boolean canOpen() {
		if (isOpen() && connector > -1) {
			return false;
		}
		if (getParent().getType() == Room.PUZZLE) {
			PuzzleRoom room = (PuzzleRoom) getParent();
			return room.isSolved();
		}
		if (getParent().getDoorAt(
				TileGrabber.getNearestNonWallTile(getLocation())) != null) {
			return false;
		}
		return true;
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
			SceneObject object = getTopAt(getLocation(),
					GameConstants.BASIC_DOORS);
			object.interact("Enter");
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
			if (!Dungeon.getCurrentRoom().contains(
					Players.getLocal().getLocation())) {
				Dungeon.setLastDoorOpended(this);
			}
		}
	}
}