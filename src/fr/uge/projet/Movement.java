package fr.uge.projet;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.KeyboardKey;

public class Movement{
  
  static void playerInput(Player player, KeyboardKey pressedKey, ApplicationContext context, int tilesSizeLargeur, int tilesSizeLongueur) {
    switch(pressedKey) {
      case UP -> Movement.playerMovement(player, "up", tilesSizeLargeur, tilesSizeLongueur);
      case DOWN -> Movement.playerMovement(player, "down", tilesSizeLargeur, tilesSizeLongueur);
      case LEFT -> Movement.playerMovement(player, "left", tilesSizeLargeur, tilesSizeLongueur);
      case RIGHT -> Movement.playerMovement(player, "right", tilesSizeLargeur, tilesSizeLongueur);
      case Q ->  context.exit(0);
      default -> throw new IllegalArgumentException("Unexpected value: " + pressedKey);
    }
  }
  
  static void playerMovement(Player player, String direction, int tilesSizeLargeur, int tilesSizeLongueur) {   
    switch(direction) {
      case "up" -> player.setDirection("up");   
      case "down" -> player.setDirection("down");  
      case "left" -> player.setDirection("left");       
      case "right" -> player.setDirection("right");
    }
    
    player.setCollision(false);
    CollisionChecker.checkTile(player, tilesSizeLargeur, tilesSizeLongueur);
    System.out.println(player.isCollision());
    if(player.isCollision() == false){     
      switch(direction) {
        case "up" -> player.setY(player.getY() - player.speed()); 
        case "down" -> player.setY(player.getY() + player.speed());  
        case "left" -> player.setX(player.getX() - player.speed());         
        case "right" -> player.setX(player.getX() + player.speed());
      }
    }
  }
}
  
