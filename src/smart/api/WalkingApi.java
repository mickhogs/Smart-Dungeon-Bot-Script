package smart.api;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Tile;

public class WalkingApi {

	public static boolean walkTileMM(Tile tile, boolean random) {
		float angle = angleTo(tile) - Camera.getAngleTo(0);
		float distance = (float) Calculations.distanceTo(tile);
		if (distance > 18)
			distance = 18;
		angle = (float) (angle * Math.PI / 180);
		int x = 627, y = 85;
		int dx = (int) (4 * (distance + Random.nextGaussian(0, random ? 2 : 0,
				1)) * Math.cos(angle));
		int dy = (int) (4 * (distance + Random.nextGaussian(0, random ? 2 : 0,
				1)) * Math.sin(angle));
		return Mouse.click(x + dx, y - dy, true);
	}

	public static int angleTo(Tile tile) {
		double ydif = tile.getY() - Players.getLocal().getLocation().getY();
		double xdif = tile.getX() - Players.getLocal().getLocation().getX();
		return (int) (Math.atan2(ydif, xdif) * 180 / Math.PI);
	}
}
