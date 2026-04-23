import java.util.*;

public class Game {

    private Scanner scanner = new Scanner(System.in);
    private boolean isRunning;

    private Map map;
    private Player player;

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<J_item> items = new ArrayList<>();

    public void start() {
        map = new Map(30, 20);
        player = new Player();

        int x, y;

        // Player spawn
        do {
            x = (int)(Math.random() * 30);
            y = (int)(Math.random() * 20);
        } while (!map.isWalkable(x, y));

        player.setPosition(x, y);

        // Enemies
        for (int i = 0; i < 5; i++) {
            do {
                x = (int)(Math.random() * 30);
                y = (int)(Math.random() * 20);
            } while (!map.isWalkable(x, y) || isOccupied(x, y));

            enemies.add(new Enemy(x, y));
        }

        // Items (Potion as string)
        for (int i = 0; i < 3; i++) {
            do {
                x = (int)(Math.random() * 30);
                y = (int)(Math.random() * 20);
            } while (!map.isWalkable(x, y) || isOccupied(x, y));

            items.add(new J_item(x, y, "Potion"));
        }

        isRunning = true;
        gameLoop();
    }

    public void gameLoop() {
      while (isRunning) {
    processInput();
    update();

    if (!isRunning) break; // ⭐ stop before render

    render();

        }
    }

    private void processInput() {
        System.out.print("Move (WASD) | Use Item (P): ");
        String input = scanner.nextLine().toLowerCase();

        int newX = player.getX();
        int newY = player.getY();

        switch (input) {
            case "w": newY--; break;
            case "a": newX--; break;
            case "s": newY++; break;
            case "d": newX++; break;
            case "p":
                player.useItem();
                return;
            default:
                return;
        }

        Enemy enemy = getEnemyAt(newX, newY);

        if (enemy != null) {
            enemy.takeDamage(1);
            System.out.println("You hit the enemy!");

            if (enemy.isDead()) {
                enemies.remove(enemy);
                System.out.println("Enemy defeated!");
            }
        } else {
            J_item item = getItemAt(newX, newY);

            if (item != null) {
                player.addItem(item);
                items.remove(item);
                System.out.println("Picked up: " + item.getName());
            }

            if (map.isWalkable(newX, newY)) {
                player.setPosition(newX, newY);
            }
        }
    }

    private void update() {

        if (player.isDead()) {
            System.out.println("You died! Game Over.");
            isRunning = false;
            return;
        }

        for (Enemy e : enemies) {

            int dx = player.getX() - e.getX();
            int dy = player.getY() - e.getY();

            int moveX = (Math.abs(dx) > Math.abs(dy)) ? Integer.signum(dx) : 0;
            int moveY = (moveX == 0) ? Integer.signum(dy) : 0;

            int newX = e.getX() + moveX;
            int newY = e.getY() + moveY;

            if (newX == player.getX() && newY == player.getY()) {
                player.takeDamage(1);
                System.out.println("Enemy hits you! HP: " + player.getHp());
                continue;
            }

            if (map.isWalkable(newX, newY) && !isOccupied(newX, newY)) {
                e.setPosition(newX, newY);
            }
        }
    }

    private void render() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        char[][] grid = map.getGrid();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {

                if (x == player.getX() && y == player.getY()) {
                    System.out.print("@");
                }
                else if (isEnemyAt(x, y)) {
                    System.out.print("E");
                }
                else if (isItemAt(x, y)) {
                    System.out.print("!");
                }
                else {
                    System.out.print(grid[y][x]);
                }
            }
            System.out.println();
        }

        System.out.println("\nHP: " + player.getHp());
        System.out.println("Enemies left: " + enemies.size());
        System.out.println("Inventory: " + player.getInventory().size());
    }

    // ===== Helpers =====

    private boolean isOccupied(int x, int y) {
        if (player.getX() == x && player.getY() == y) return true;

        for (Enemy e : enemies) {
            if (e.getX() == x && e.getY() == y) return true;
        }

        return false;
    }

    private Enemy getEnemyAt(int x, int y) {
        for (Enemy e : enemies) {
            if (e.getX() == x && e.getY() == y) return e;
        }
        return null;
    }

    private boolean isEnemyAt(int x, int y) {
        return getEnemyAt(x, y) != null;
    }

    private J_item getItemAt(int x, int y) {
        for (J_item i : items) {
            if (i.getX() == x && i.getY() == y) return i;
        }
        return null;
    }

    private boolean isItemAt(int x, int y) {
        return getItemAt(x, y) != null;
    }
}