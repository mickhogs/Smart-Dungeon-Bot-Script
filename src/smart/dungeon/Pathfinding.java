package smart.dungeon;

import java.util.ArrayList;
import java.util.Arrays;

import smart.dungeon.doors.Door;
import smart.dungeon.rooms.Room;

public class Pathfinding {

	public Room[] findPath(Room start, Room end) {
		ArrayList<Room> open = new ArrayList<Room>();
		ArrayList<Room> closed = new ArrayList<Room>();
		open.add(start);
		while (open.size() > 0) {
			Room curr = open.get(0);
			open.remove(curr);
			closed.add(curr);
			for (Room room : curr.getConnectors()) {
				if (!closed.contains(room)) {
					if (!open.contains(room)) {
						room.setParent(curr);
						open.add(room);
					}
				}
			}
			if (curr.equals(end)) {
				Room[] path = new Room[0];
				do {
					path = Arrays.copyOf(path, path.length + 1);
					path[path.length - 1] = curr;
					curr = curr.getParent();
					if (curr == null || start == null)
						return null;
				} while (!curr.equals(start));
				path = Arrays.copyOf(path, path.length + 1);
				path[path.length - 1] = start;
				Room[] pathReverse = new Room[path.length];
				for (int i = 0; i < path.length; i++)
					pathReverse[i] = path[path.length - i - 1];
				return pathReverse;
			}
		}
		return null;
	}

	public void walkPath(Room[] path) {
		if (path.length < 2)
			return;
		Door toOpen = path[0].getDoorForConnector(path[1]);
		toOpen.open();
	}

	public int distanceToRoom(Room from, Room to) {
		Room[] path = findPath(from, to);
		if (path != null)
			return path.length;
		return -1;
	}
}