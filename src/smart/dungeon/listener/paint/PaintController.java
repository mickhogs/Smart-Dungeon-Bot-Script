package smart.dungeon.listener.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import smart.dungeon.util.Misc;

public class PaintController {
	private static ArrayList<PaintComponent> components = new ArrayList<PaintComponent>();
	private static long timeRunning = -1;
	private static boolean shouldDraw = true;
	private static boolean shouldHandleMouse = true;
	private static boolean shouldHandleKeys = true;

	public static void addComponent(PaintComponent comp) {
		components.add(comp);
	}

	public static void removeComponent(PaintComponent comp) {
		components.remove(comp);
	}

	public static void startTimer() {
		timeRunning = System.currentTimeMillis();
	}

	public static String timeRunning() {
		return (timeRunning != -1) ? Misc.parseTime(System.currentTimeMillis()
				- timeRunning) : "Not started";
	}

	public static long runTime() {
		return timeRunning;
	}

	public static void toggleEvents() {
		shouldDraw = !shouldDraw;
		shouldHandleKeys = !shouldHandleKeys;
		shouldHandleMouse = !shouldHandleMouse;
		for (PaintComponent component : components) {
			if (component instanceof PaintFrame) {
				((PaintFrame) component).toggleEvents();
			}
		}
	}

	public static void toggleDrawing() {
		shouldDraw = !shouldDraw;
		for (PaintComponent component : components) {
			if (component instanceof PaintFrame) {
				((PaintFrame) component).toggleDrawing();
			}
		}
	}

	public static void toggleMouseHandling() {
		shouldHandleKeys = !shouldHandleKeys;
		for (PaintComponent component : components) {
			if (component instanceof PaintFrame) {
				((PaintFrame) component).toggleMouseHandling();
			}
		}
	}

	public static void toggleKeyHandling() {
		for (PaintComponent component : components) {
			if (component instanceof PaintFrame) {
				((PaintFrame) component).toggleKeyHandling();
			}
		}
		shouldHandleMouse = !shouldHandleMouse;
	}

	public static void setDrawing(boolean draw) {
		shouldDraw = draw;
	}

	public static void setMouseHandling(boolean handle) {
		shouldHandleMouse = handle;
	}

	public static void setKeyHandling(boolean handle) {
		shouldHandleKeys = handle;
	}

	public static void onRepaint(Graphics g) {
		try {
			Iterator<PaintComponent> it = components.iterator();
			while (it.hasNext()) {
				PaintComponent comp = it.next();
				if (comp == null)
					continue;
				if (shouldDraw) {
					if (comp.shouldPaint()) {
						Font oldFont = g.getFont();
						Color oldColor = g.getColor();
						comp.repaint((Graphics2D) g);
						g.setFont(oldFont);
						g.setColor(oldColor);
					}
				} else {
					if (comp instanceof PaintFrame) {
						for (PaintComponent component : ((PaintFrame) comp)
								.getComponents())
							if (component.forcePaint()) {
								Font oldFont = g.getFont();
								Color oldColor = g.getColor();
								component.repaint((Graphics2D) g);
								g.setFont(oldFont);
								g.setColor(oldColor);
							}
					} else if (comp.forcePaint()) {
						Font oldFont = g.getFont();
						Color oldColor = g.getColor();
						comp.repaint((Graphics2D) g);
						g.setFont(oldFont);
						g.setColor(oldColor);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keyTyped(KeyEvent keyEvent) {
		Iterator<PaintComponent> it = components.iterator();
		while (it.hasNext()) {
			PaintComponent comp = it.next();
			if (comp == null)
				continue;
			if (shouldHandleKeys) {
				if (comp.shouldHandleKeys())
					comp.keyTyped(keyEvent);
			} else {
				if (comp instanceof PaintFrame) {
					for (PaintComponent component : ((PaintFrame) comp)
							.getComponents())
						if (component.forceKeys())
							component.keyTyped(keyEvent);
				} else if (comp.forceKeys())
					comp.keyTyped(keyEvent);
			}
		}
	}

	public static void keyPressed(KeyEvent keyEvent) {
		Iterator<PaintComponent> it = components.iterator();
		while (it.hasNext()) {
			PaintComponent comp = it.next();
			if (comp == null)
				continue;
			if (shouldHandleKeys) {
				if (comp.shouldHandleKeys())
					comp.keyPressed(keyEvent);
			} else {
				if (comp instanceof PaintFrame) {
					for (PaintComponent component : ((PaintFrame) comp)
							.getComponents())
						if (component.forceKeys())
							component.keyPressed(keyEvent);
				} else if (comp.forceKeys())
					comp.keyPressed(keyEvent);
			}
		}
	}

	public static void keyReleased(KeyEvent keyEvent) {
		Iterator<PaintComponent> it = components.iterator();
		while (it.hasNext()) {
			PaintComponent comp = it.next();
			if (comp == null)
				continue;
			if (shouldHandleKeys) {
				if (comp.shouldHandleKeys())
					comp.keyReleased(keyEvent);
			} else {
				if (comp instanceof PaintFrame) {
					for (PaintComponent component : ((PaintFrame) comp)
							.getComponents())
						if (component.forceKeys())
							component.keyReleased(keyEvent);
				} else if (comp.forceKeys())
					comp.keyReleased(keyEvent);
			}
		}
	}

	public static void mouseClicked(MouseEvent mouseEvent) {
		Iterator<PaintComponent> it = components.iterator();
		while (it.hasNext()) {
			PaintComponent comp = it.next();
			if (comp == null)
				continue;
			if (shouldHandleMouse) {
				if (comp.shouldHandleMouse())
					comp.mouseClicked(mouseEvent);
			} else {
				if (comp instanceof PaintFrame) {
					for (PaintComponent component : ((PaintFrame) comp)
							.getComponents())
						if (component.forceMouse())
							component.mouseClicked(mouseEvent);
				} else if (comp.forceMouse())
					comp.mouseClicked(mouseEvent);
			}
		}
	}

	public static void mousePressed(MouseEvent mouseEvent) {
		Iterator<PaintComponent> it = components.iterator();
		while (it.hasNext()) {
			PaintComponent comp = it.next();
			if (comp == null)
				continue;
			if (shouldHandleMouse) {
				if (comp.shouldHandleMouse())
					comp.mousePressed(mouseEvent);
			} else {
				if (comp instanceof PaintFrame) {
					for (PaintComponent component : ((PaintFrame) comp)
							.getComponents())
						if (component.forceMouse())
							component.mousePressed(mouseEvent);
				} else if (comp.forceMouse())
					comp.mousePressed(mouseEvent);
			}
		}
	}

	public static void mouseReleased(MouseEvent mouseEvent) {
		Iterator<PaintComponent> it = components.iterator();
		while (it.hasNext()) {
			PaintComponent comp = it.next();
			if (comp == null)
				continue;
			if (shouldHandleMouse) {
				if (comp.shouldHandleMouse())
					comp.mouseReleased(mouseEvent);
			} else {
				if (comp instanceof PaintFrame) {
					for (PaintComponent component : ((PaintFrame) comp)
							.getComponents())
						if (component.forceMouse())
							component.mouseReleased(mouseEvent);
				} else if (comp.forceMouse())
					comp.mouseReleased(mouseEvent);
			}
		}
	}

	public static void mouseEntered(MouseEvent mouseEvent) {
		Iterator<PaintComponent> it = components.iterator();
		while (it.hasNext()) {
			PaintComponent comp = it.next();
			if (comp == null)
				continue;
			if (shouldHandleMouse) {
				if (comp.shouldHandleMouse())
					comp.mouseEntered(mouseEvent);
			} else {
				if (comp instanceof PaintFrame) {
					for (PaintComponent component : ((PaintFrame) comp)
							.getComponents())
						if (component.forceMouse())
							component.mouseEntered(mouseEvent);
				} else if (comp.forceMouse())
					comp.mouseEntered(mouseEvent);
			}
		}
	}

	public static void mouseExited(MouseEvent mouseEvent) {
		Iterator<PaintComponent> it = components.iterator();
		while (it.hasNext()) {
			PaintComponent comp = it.next();
			if (comp == null)
				continue;
			if (shouldHandleMouse) {
				if (comp.shouldHandleMouse())
					comp.mouseExited(mouseEvent);
			} else {
				if (comp instanceof PaintFrame) {
					for (PaintComponent component : ((PaintFrame) comp)
							.getComponents())
						if (component.forceMouse())
							component.mouseExited(mouseEvent);
				} else if (comp.forceMouse())
					comp.mouseExited(mouseEvent);
			}
		}
	}

	public static void mouseDragged(MouseEvent mouseEvent) {
		Iterator<PaintComponent> it = components.iterator();
		while (it.hasNext()) {
			PaintComponent comp = it.next();
			if (comp == null)
				continue;
			if (shouldHandleMouse) {
				if (comp.shouldHandleMouse())
					comp.mouseDragged(mouseEvent);
			} else {
				if (comp instanceof PaintFrame) {
					for (PaintComponent component : ((PaintFrame) comp)
							.getComponents())
						if (component.forceMouse())
							component.mouseDragged(mouseEvent);
				} else if (comp.forceMouse())
					comp.mouseDragged(mouseEvent);
			}
		}
	}

	public static void mouseMoved(MouseEvent mouseEvent) {
		Iterator<PaintComponent> it = components.iterator();
		while (it.hasNext()) {
			PaintComponent comp = it.next();
			if (comp == null)
				continue;
			if (shouldHandleMouse) {
				if (comp.shouldHandleMouse())
					comp.mouseMoved(mouseEvent);
			} else {
				if (comp instanceof PaintFrame) {
					for (PaintComponent component : ((PaintFrame) comp)
							.getComponents())
						if (component.forceMouse())
							component.mouseMoved(mouseEvent);
				} else if (comp.forceMouse())
					comp.mouseMoved(mouseEvent);
			}
		}
	}

	public static void clearComps() {
		components.clear();
	}

	public static void reset() {
		setDrawing(true);
		setKeyHandling(true);
		setMouseHandling(true);
		clearComps();
	}
}