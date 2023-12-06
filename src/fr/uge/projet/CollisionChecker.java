package fr.uge.projet;

public class CollisionChecker{
  public static boolean  checkNeighbourTile(Tile tile1, Tile tile2) {
    if(tile1 == null && tile2 == null) return false;
    return((tile1.collision() || tile2.collision()));
  }
  
  public static void checkTile(Player player, int tilesSizeLargeur, int tilesSizeLongueur) {
    System.out.println(Main.tiles);
    int rightHitbox = player.getxHitbox() + player.getHitboxWidth();
    int botHitbox = player.getyHitbox() + player.getHitboxHeight(); 
    
    int rowTopOfPlayer = player.getyHitbox() / tilesSizeLargeur;
    int rowBotOfPlayer = botHitbox / tilesSizeLargeur;
    int colLeftOfPlayer = player.getxHitbox() / tilesSizeLongueur;
    int colRightOfPlayer = rightHitbox / tilesSizeLongueur;   
   
    
    switch(player.getDirection()) {
    case"up" ->{
      rowTopOfPlayer = (player.getyHitbox() - player.speed())/tilesSizeLongueur;
      player.setCollision(checkNeighbourTile(Main.tiles[rowTopOfPlayer][colLeftOfPlayer], Main.tiles[rowTopOfPlayer][colRightOfPlayer])); 
   }
    case"down" ->{
      System.out.println(rowBotOfPlayer + " " + rowTopOfPlayer+ " " +colRightOfPlayer+ " " + colLeftOfPlayer);
      rowBotOfPlayer = (player.getyHitbox() + player.getHitboxHeight() + player.speed())/tilesSizeLongueur;
      player.setCollision(checkNeighbourTile(Main.tiles[rowBotOfPlayer][colLeftOfPlayer], Main.tiles[rowBotOfPlayer][colRightOfPlayer])); 
    }
    case"left" ->{
      colLeftOfPlayer = (player.getxHitbox() - player.speed())/tilesSizeLongueur;
      player.setCollision(checkNeighbourTile(Main.tiles[rowTopOfPlayer][colLeftOfPlayer], Main.tiles[rowBotOfPlayer][colLeftOfPlayer]));
    }
    case"right" ->{
      colRightOfPlayer = (player.getxHitbox()+ player.getHitboxWidth() + player.speed())/tilesSizeLongueur;
      player.setCollision(checkNeighbourTile(Main.tiles[rowTopOfPlayer][colRightOfPlayer], Main.tiles[rowBotOfPlayer][colRightOfPlayer]));
    }
  }
  }
}
