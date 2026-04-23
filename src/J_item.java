public class J_item {

    // 📍 Position in the world
    private int x;
    private int y;

    // 🏷️ Item type/name
    private String name;

    // ⚙️ Constructor
    public J_item(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    // 📍 Position methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // 🏷️ Name method
    public String getName() {
        return name;
    }
}