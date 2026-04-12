public class Map {
    private int height;
    private int width;
    private char[][] grid;
    
    Map(int width, int height){
        System.out.println("Map Created!");
        this.width = width;
        this.height = height;
        grid = new char[height][width];
        generateMap();
    }

    private void generateMap(){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(y == 0 || y == height - 1 || x == 0 || x == width - 1){
                    grid[y][x] = '#'; // walls
                }else{
                    grid[y][x] = '.'; //floor
                }
            }
        }
    }
    private void getTile(int x, int y){

    }
    public char[][] getGrid(){
        return grid;
    }
    public boolean isWalkable(int x, int y){
        if(x < 0 || y < 0 || y >= height || x >= width){
            return false;
        }
        return grid[y][x] == '.';
    }
}
