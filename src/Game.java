import java.util.Scanner;

public class Game{
    private Scanner scanner = new Scanner(System.in);
    private boolean isRunning;
    private Map map;
    private Player player;

    public void start(){
        map = new Map(10,10);
        player = new Player();
        player.setPosition(5,5);

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
                }else{
                    System.out.print(grid[y][x]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
