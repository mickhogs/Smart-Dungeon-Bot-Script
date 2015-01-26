package smart.dungeon.listener.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class PaintColumnLayout extends PaintComponent {
	private final int offsetIncrement;
	private final String[] leftColumn;
	private final String[] rightColumn;
	private final ColorScheme scheme;
	private Font font;

	public PaintColumnLayout(int x, int y, int offsetIncrement,
			String[] leftColumn, String[] rightColumn, Font font) {
		this(x, y, offsetIncrement, leftColumn, rightColumn, font, null);
	}

	public PaintColumnLayout(int x, int y, int offsetIncrement,
			String[] leftColumn, String[] rightColumn) {
		this(x, y, offsetIncrement, leftColumn, rightColumn, null, null);
	}

	public PaintColumnLayout(int x, int y, String[] leftColumn,
			String[] rightColumn) {
		this(x, y, 5, leftColumn, rightColumn, null, null);
	}

	public PaintColumnLayout(int x, int y, String[] leftColumn,
			String[] rightColumn, Font font) {
		this(x, y, 5, leftColumn, rightColumn, font);
	}

	public PaintColumnLayout(int x, int y, int offsetIncrement,
			String[] leftColumn, String[] rightColumn, Font font,
			ColorScheme scheme) {
		this.x = x;
		this.y = y;
		this.offsetIncrement = offsetIncrement;
		this.leftColumn = leftColumn;
		this.rightColumn = rightColumn;
		this.font = font;
		this.scheme = scheme;
	}

	public PaintColumnLayout(int x, int y, int offsetIncrement,
			String[] leftColumn, String[] rightColumn, ColorScheme scheme) {
		this(x, y, offsetIncrement, leftColumn, rightColumn, null, scheme);
	}

	public PaintColumnLayout(int x, int y, String[] leftColumn,
			String[] rightColumn, ColorScheme scheme) {
		this(x, y, 5, leftColumn, rightColumn, null, scheme);
	}

	public PaintColumnLayout(int x, int y, String[] leftColumn,
			String[] rightColumn, Font font, ColorScheme scheme) {
		this(x, y, 5, leftColumn, rightColumn, font, scheme);
	}

	@Override
	public void repaint(Graphics2D g) {
		if (font == null)
			font = g.getFont();
		if (scheme != null)
			g.setColor(scheme.text);
		g.setFont(font);
		String largestString = "";
		int offset = 0;
		for (String str : leftColumn) {
			if (str == null)
				continue;
			if (str.length() > largestString.length())
				largestString = str;
			g.drawString(str, x, y + offset);
			offset += g.getFont()
					.getStringBounds(str, g.getFontRenderContext()).getHeight();
		}
		offset = 0;
		Rectangle2D bounds = g.getFont().getStringBounds(largestString,
				g.getFontRenderContext());
		for (String str : rightColumn) {
			if (str != null) {
				g.drawString(str,
						(int) (x + bounds.getWidth() + offsetIncrement), y
								+ offset);
				offset += g.getFont()
						.getStringBounds(str, g.getFontRenderContext())
						.getHeight();
			}
		}
	}

	public static enum ColorScheme {
		GRAPHITE(new Color(200, 200, 200));
		private Color text;

		ColorScheme(Color text) {
			this.text = text;
		}
	}
}
