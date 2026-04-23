import java.util.ArrayList;
import java.util.Random;

public class Map {

    private int width;
    private int height;
    private char[][] grid;

    private Random random = new Random();
    private ArrayList<Room> rooms = new ArrayList<>();

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new char[height][width];

        generateMap();
    }

    public char[][] getGrid() {
        return grid;
    }

    public boolean isWalkable(int x, int y) {
        return grid[y][x] == '.';
    }

    private void generateMap() {

        // Fill everything with walls
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = '#';
            }
        }

        int maxRooms = 6;

        for (int i = 0; i < maxRooms; i++) {

            int w = random.nextInt(5) + 4; // width 4–8
            int h = random.nextInt(5) + 4; // height 4–8

            int x = random.nextInt(width - w - 1);
            int y = random.nextInt(height - h - 1);

            Room newRoom = new Room(x, y, w, h);

            boolean overlaps = false;
            for (Room r : rooms) {
                if (newRoom.intersects(r)) {
                    overlaps = true;
                    break;
                }
            }

            if (!overlaps) {
                createRoom(newRoom);

                if (!rooms.isEmpty()) {
                    Room prev = rooms.get(rooms.size() - 1);
                    connectRooms(prev, newRoom);
                }

                rooms.add(newRoom);
            }
        }
    }

    // 🏠 Create room
    private void createRoom(Room room) {
        for (int y = room.y; y < room.y + room.height; y++) {
            for (int x = room.x; x < room.x + room.width; x++) {
                grid[y][x] = '.';
            }
        }
    }

    // 🚪 Connect rooms with corridors
    private void connectRooms(Room r1, Room r2) {

        int x1 = r1.centerX();
        int y1 = r1.centerY();
        int x2 = r2.centerX();
        int y2 = r2.centerY();

        // Horizontal tunnel
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            grid[y1][x] = '.';
        }

        // Vertical tunnel
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            grid[y][x2] = '.';
        }
    }
}