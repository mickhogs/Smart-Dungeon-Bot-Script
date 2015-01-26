package smart.api;

import java.awt.Point;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.wrappers.Tile;

public class CalculationsApi {

	public static Point tileToScreen(final Tile tile, final double dX,
			final double dY, final int height) {
		return Calculations.groundToScreen(
				(int) ((tile.getX() - Game.getBaseX() + dX) * 512),
				(int) ((tile.getY() - Game.getBaseY() + dY) * 512),
				Game.getPlane(), height);
	}

}
