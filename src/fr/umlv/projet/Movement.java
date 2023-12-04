package fr.umlv.projet;

public class Movement{
  
  
  
  
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
  
