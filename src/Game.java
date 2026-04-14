import java.util.Scanner;
import java.util.ArrayList;

public class Game{
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Enemy> enemies = new ArrayList<>(); // number of enemies is dynamic
    private boolean isRunning;
    private Map map;
    private Player player;

    public void start(){
        map = new Map(20,15);
        player = new Player();
        int x,y;
        do{
            x = (int)(Math.random() * 20); // math.random gives random numbers between 0.0 and 1.0
            y = (int)(Math.random() * 15);//multiply by 20 ex : 0.12*20 = 2.4, then int() gives only 2
        }while(!map.isWalkable(x, y));//so x becomes random number between 0 - 19

        player.setPosition(x,y);

        for(int i = 0; i < 5; i++){
            do{
                x = (int)(Math.random() * 20);
                y = (int)(Math.random() * 15);
            }while(!map.isWalkable(x, y) || isOccupied(x,y));
            enemies.add(new Enemy(x,y));
        }
        isRunning = true;
        gameloop();
    }

    public void gameloop(){
        while(isRunning){
            processInput();
            update();
            render();

            try{
                Thread.sleep(500);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    private void processInput(){
        //render();
        System.out.print("move (WASD) : ");
        String input = scanner.nextLine().toLowerCase();

        int newX = player.getX();
        int newY = player.getY();

        switch(input){
            case "w" : newY--; break;
            case "a" : newX--; break;
            case "s" : newY++; break;
            case "d" : newX++; break;
        }
        if(map.isWalkable(newX,newY)){
            player.setPosition(newX, newY);
        }
    }
    private void update(){

    }
    private void render(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
        // fallback
            for (int i = 0; i < 30; i++) System.out.println();
        }
        char[][] grid = map.getGrid();
        for(int y = 0; y < grid.length; y++){
            for(int x = 0; x < grid[y].length; x++){
                if(x == player.getX() && y == player.getY()){
                    System.out.print("@");
                }else if(isEnemyAt(x,y)){
                    System.out.print("E");
                }
                else{
                    System.out.print(grid[y][x]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isOccupied(int x, int y){
        if(player.getX() == x && player.getY() == y){
            return true;
        }
        for(Enemy e : enemies){
            if(e.getX() == x && e.getY() == y){
                return true;
            }
        }
        return false;
    }

    private boolean isEnemyAt(int x, int y){
        for(Enemy e : enemies){
            if(e.getX() == x && e.getY() == y){
                return true;
            }
        }
        return false;
    }
}
