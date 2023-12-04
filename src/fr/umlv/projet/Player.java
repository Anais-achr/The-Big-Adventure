package fr.umlv.projet;

import java.util.Objects;

public class Player{  
    private int y;
    private int x;
    private int xHitbox;
    private int yHitbox;
    private final int hitboxHeight;
    private final int hitboxWidth;
    private final int speed;
    private boolean collision;
    private String direction;

    public Player(int x, int y){
      Objects.requireNonNull(x);
      Objects.requireNonNull(y);
      this.hitboxWidth = 38;
      this.hitboxHeight = 38;
      this.xHitbox = x + 10;
      this.yHitbox = y + 10;
      this.speed = 6;
      this.x = x;
      this.y = y;
      this.setCollision(false);
      this.setDirection("down");
      
    }

    public int getY() {
      return y;
    }

    public void setY(int y) {
      this.y = y;
      this.yHitbox = y + 6;
    }

    public int getX() {
      return x;
    }

    public void setX(int x) {
      this.x = x;
      this.xHitbox = x + 6;
    }

    public boolean isObstacle(){
      return false;
    }

    public int speed() {
      return this.speed;
    }

    public int getxHitbox() {
      return xHitbox;
    }

    public int getHitboxWidth() {
      return hitboxWidth;
    }

    public int getyHitbox() {
      return yHitbox;
    }

    public int getHitboxHeight() {
      return hitboxHeight;
    }

    /**
     * @return the collision
     */
    public boolean isCollision() {
      return collision;
    }

    /**
     * @param collision the collision to set
     */
    public void setCollision(boolean collision) {
      this.collision = collision;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
      return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
      this.direction = direction;
    }
    
 
}

