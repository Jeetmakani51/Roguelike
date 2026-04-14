public class Enemy {
    private int x;
    private int y;
    private int hp;

    public Enemy(int x, int y){
        this.x = x;
        this.y = y;
        this.hp = 3;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
}
