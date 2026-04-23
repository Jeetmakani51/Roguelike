import java.util.ArrayList;

public class Player {

    private int x, y;
    private int hp;
    private ArrayList<J_item> inventory;

    public Player() {
        hp = 5;
        inventory = new ArrayList<>();
    }

    // Position
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    // Health
    public int getHp() { return hp; }

    public void takeDamage(int dmg) {
        hp -= dmg;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void heal(int amount) {
        hp += amount;
    }

    // Inventory
    public void addItem(J_item item) {
        inventory.add(item);
    }

    public ArrayList<J_item> getInventory() {
        return inventory;
    }

    // 🔁 Use item (string-based)
    public void useItem() {

        for (int i = 0; i < inventory.size(); i++) {
            J_item item = inventory.get(i);

            if (item.getName().equals("Potion")) {
                heal(2);
                System.out.println("Used potion! HP: " + hp);
                inventory.remove(i);
                return;
            }
        }

        System.out.println("No potion found!");
    }
}