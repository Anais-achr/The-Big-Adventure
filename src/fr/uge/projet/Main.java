package fr.uge.projet;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.ScreenInfo;

public class Main {
 ;
  final static int tileSize = 48;
  final static int gridWidth = 6;
  final static int gridHeight = 6;
  final int maxScreenColumn = 32;
  final int maxScreenRow = 16;  
  public static Tile[][] tiles; // ne doit pas être public et doit être changer
    
  
  public static void main(String[] args) {   
    
    Application.run(Color.BLACK, context -> {
     
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float height = screenInfo.getHeight();
      Player player = new Player(1*tileSize, 1*tileSize);
      
      
      // Initialisation d'une map à la main pour des tests
      tiles = new Tile[gridWidth][gridHeight];
      for(var i= 0; i < gridWidth; i++) {
        for(var j =0; j < gridHeight; j++) {
          if((i==0||i==5)||(j==0||j==5)) {
            String tileName = "brick";
            BufferedImage image = null;
            try(var input = Main.class.getResourceAsStream("/Images/"+tileName+".png")) {
              image = ImageIO.read(input);
            } catch (IOException e) {          
              e.printStackTrace();
            }           
            tiles[i][j] = new Tile(image, true);
          }  else {
          String tileName2 = "tile";
          BufferedImage image2 = null;
          try(var input = Main.class.getResourceAsStream("/Images/"+tileName2+".png")) {
            image2 = ImageIO.read(input);
          } catch (IOException e) {          
            e.printStackTrace();
          }           
          tiles[i][j] = new Tile(image2, false);
          }
        }
      }
  

            
                 
      Graphic area = new Graphic();
      
      
      for(;;) {
        area.draw(context,player,tiles, width, height);  
        Event event = context.pollOrWaitEvent(16);
        
        if(event == null) {
          continue;
        }
        Action action = event.getAction();        
        // La gestion d'évènement doit se passer dans le context
        if (action == Action.KEY_PRESSED) {         
          var pressedKey = event.getKey();        
            try{
              Movement.playerInput( player, pressedKey, context);
            }
            catch(IllegalArgumentException e) {
              System.err.println("Pressed Key has no usage");
            }
          }
        }  
       
  });
 }
}
