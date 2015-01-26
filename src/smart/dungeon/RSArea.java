package smart.dungeon;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.wrappers.Tile;

public class RSArea {
	private final Tile[] tiles;

	public RSArea(Tile[] tiles) {
		this.tiles = tiles;
	}

	public boolean contains(Tile tiles) {
		Tile[] areaTiles = this.getTileArray();
		for (Tile space : areaTiles) {
			if (tiles.equals(space)) {
				return true;
			}
		}
		return false;
	}

	public Tile getCentralTile() {
		if (tiles.length < 1)
			return null;
		int totalX = 0, totalY = 0;
		for (Tile tile : tiles) {
			totalX += tile.getX();
			totalY += tile.getY();
		}
		return new Tile(Math.round(totalX / tiles.length), Math.round(totalY
				/ tiles.length), Game.getPlane());
	}

	public Tile[] getTileArray() {
		return tiles;
	}
}