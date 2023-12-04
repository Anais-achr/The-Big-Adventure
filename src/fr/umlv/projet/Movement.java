package fr.umlv.projet;

import fr.umlv.zen5.KeyboardKey;

public class Movement{
  static void playerInput(Player player, KeyboardKey pressedKey) {
    switch(pressedKey) {
      case UP -> Movement.playerMovement(player, "up");
      case DOWN -> Movement.playerMovement(player, "down");
      case LEFT -> Movement.playerMovement(player, "left");
      case RIGHT -> Movement.playerMovement(player, "right");
      default -> throw new IllegalArgumentException("Unexpected value: " + pressedKey);
    }
  }
  
  static void playerMovement(Player player, String direction) {   
    switch(direction) {
      case "up" ->player.setDirection("up");   
      case "down" ->player.setDirection("down");  
      case "left" ->player.setDirection("left");       
      case "right" ->player.setDirection("right");
    }
    
    player.setCollision(false);
    CollisionChecker.checkTile(player);
    System.out.println(player.isCollision());
    if(player.isCollision() == false){     
      switch(direction) {
        case "up" ->player.setY(player.getY() - player.speed()); 
        case "down" ->player.setY(player.getY() + player.speed());  
        case "left" ->player.setX(player.getX() - player.speed());         
        case "right" ->player.setX(player.getX() + player.speed());
      }
    }
  }
}
  
