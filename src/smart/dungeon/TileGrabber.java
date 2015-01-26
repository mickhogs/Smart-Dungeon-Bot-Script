package smart.dungeon;

import java.util.ArrayList;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.wrappers.Tile;

import smart.dungeon.util.GameConstants;

public class TileGrabber {

	private final ArrayList<Tile> tiles = new ArrayList<Tile>();
	private int z = 0;

	public Tile[] fill(Tile location) {
		Tile[] tiles = getRoomTiles(location);
		for (Tile tile : tiles) {
			Tile[] checkTiles = getSurroundingTiles(tile, true);
			for (Tile checkTile : checkTiles) {
				if (!this.tiles.contains(checkTile))
					this.tiles.add(checkTile);
			}
		}
		Tile[] returnTiles = new Tile[this.tiles.size()];
		for (int i = 0; i < this.tiles.size(); i++) {
			returnTiles[i] = this.tiles.get(i);
		}
		return returnTiles;
	}

	private Tile[] getRoomTiles(Tile location) {
		z++;
		if ((getCollisionFlagAtTile(location) & GameConstants.WALL) != 0) {
			Tile[] tempTiles = new Tile[tiles.size()];
			for (int i = 0; i < tiles.size(); i++) {
				tempTiles[i] = tiles.get(i);
			}
			return tempTiles;
		}
		Tile[] checkTiles = getSurroundingTiles(location, true);
		int[] flagTiles = getSurroundingCollisionFlags(location, true);
		for (int i = 0; i < checkTiles.length; i++) {
			if ((flagTiles[i] & GameConstants.WALL) == 0 && z <= 1100) {
				if (!tiles.contains(checkTiles[i])) {
					tiles.add(checkTiles[i]);
					getRoomTiles(checkTiles[i]);
				}
			}
		}
		Tile[] tempTiles = new Tile[tiles.size()];
		for (int i = 0; i < tiles.size(); i++) {
			tempTiles[i] = tiles.get(i);
		}
		return tempTiles;
	}

	private static Tile[] getSurroundingTiles(Tile tile, boolean eightTiles) {
		int x = tile.getX();
		int y = tile.getY();
		Tile north = new Tile(x, y + 1, Game.getPlane());
		Tile east = new Tile(x + 1, y, Game.getPlane());
		Tile south = new Tile(x, y - 1, Game.getPlane());
		Tile west = new Tile(x - 1, y, Game.getPlane());
		Tile northEast;
		Tile southEast;
		Tile southWest;
		Tile northWest;
		if (eightTiles) {
			northEast = new Tile(x + 1, y + 1, Game.getPlane());
			southEast = new Tile(x + 1, y - 1, Game.getPlane());
			southWest = new Tile(x - 1, y - 1, Game.getPlane());
			northWest = new Tile(x - 1, y + 1, Game.getPlane());
			return new Tile[] { north, northEast, east, southEast, south,
					southWest, west, northWest };
		}
		return new Tile[] { north, east, south, west };
	}

	private static int[] getSurroundingCollisionFlags(Tile tile,
			boolean eightTiles) {
		int[][] flags = Walking.getCollisionFlags(Game.getPlane());
		int x = tile.getX();
		int y = tile.getY();
		int xOff = x - Game.getBaseX()
				- Walking.getCollisionOffset(Game.getPlane()).getX();
		int yOff = y - Game.getBaseY()
				- Walking.getCollisionOffset(Game.getPlane()).getY();
		int fNorth = flags[xOff][yOff + 1];
		int fEast = flags[xOff + 1][yOff];
		int fSouth = flags[xOff][yOff - 1];
		int fWest = flags[xOff - 1][yOff];
		int fNorthEast;
		int fSouthEast;
		int fSouthWest;
		int fNorthWest;
		if (eightTiles) {
			fNorthEast = flags[xOff + 1][yOff + 1];
			fSouthEast = flags[xOff + 1][yOff - 1];
			fSouthWest = flags[xOff - 1][yOff - 1];
			fNorthWest = flags[xOff - 1][yOff + 1];
			return new int[] { fNorth, fNorthEast, fEast, fSouthEast, fSouth,
					fSouthWest, fWest, fNorthWest };
		}
		return new int[] { fNorth, fEast, fSouth, fWest };
	}

	private int getCollisionFlagAtTile(Tile tile) {
		int[][] flags = Walking.getCollisionFlags(Game.getPlane());
		int x = tile.getX();
		int y = tile.getY();
		int xOff = x - Game.getBaseX()
				- Walking.getCollisionOffset(Game.getPlane()).getX();
		int yOff = y - Game.getBaseY()
				- Walking.getCollisionOffset(Game.getPlane()).getY();
		return flags[xOff][yOff];
	}

	public static Tile getNearestNonWallTile(Tile tile) {
		return getNearestNonWallTile(tile, false);
	}

	public static Tile getNearestNonWallTile(Tile tile, boolean eightTiles) {
		Tile[] checkTiles = getSurroundingTiles(tile, eightTiles);
		int[] flags = getSurroundingCollisionFlags(tile, eightTiles);
		for (int i = 0; i < checkTiles.length; i++) {
			if ((flags[i] & GameConstants.WALL) == 0)
				return checkTiles[i];
		}
		return null;
	}
}
