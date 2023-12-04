package fr.umlv.projet;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.ScreenInfo;

public class Main {
  final static int tileOriginalSize = 24;
  final static int scale = 2;
  final static int tileSize = tileOriginalSize * scale;
  final static int gridWidth = 6;
  final static int gridHeight = 6;
  final int maxScreenColumn = 32;
  final int maxScreenRow = 16;  
  public static Tile[][] tiles; // ne doit pas être public et doit être changer
  
  static class Area {    
    void draw(ApplicationContext context, Player player, Tile[][] tile, float  width, float height) {
      context.renderFrame(graphics -> {
        // hide the previous rectangle
        graphics.setColor(Color.BLACK);
        graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
        
        // show a new ellipse at the position of the pointer
        for(var i= 0; i < gridWidth; i++) {
          for(var j =0; j < gridHeight; j++) {
            if(tile[i][j] == null) continue;
            graphics.drawImage(tile[i][j].image(),i * tileSize,j*tileSize,tileSize,tileSize, null);
          }
        }
        BufferedImage image2 = null;        
        String tileName2 = "baba";
        try(var input = Main.class.getResourceAsStream("/"+tileName2+".png")) {
          image2 = ImageIO.read(input);
        } catch (IOException e) {          
          e.printStackTrace();
        }        
        graphics.drawImage(image2,player.getX(),player.getY(),tileSize,tileSize, null);     
      });
    }
  }  
  
  public static void main(String[] args) {   
    
    Application.run(Color.BLACK, context -> {
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float height = screenInfo.getHeight();
      Player player = new Player(100, 100);
      
      
      
      tiles = new Tile[gridWidth][gridHeight];
      for(var i= 0; i < gridWidth; i++) {
        for(var j =0; j < gridHeight; j++) {
          if((i==0||i==5)||(j==0||j==5)) {
            String tileName = "brick";
            BufferedImage image = null;
            try(var input = Main.class.getResourceAsStream("/"+tileName+".png")) {
              image = ImageIO.read(input);
            } catch (IOException e) {          
              e.printStackTrace();
            }           
            tiles[i][j] = new Tile(image, true);
          }          
        }
      }
  
      System.out.println("size of the screen (" + width + " x " + height + ")");
            
      // get the size of the screen    
      context.renderFrame(graphics -> {
        graphics.setColor(Color.BLACK);
        graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
      });
      
      
      Area area = new Area();
      for(;;) {
        
        Event event = context.pollOrWaitEvent(16);
        if(event == null) {
          continue;
        }
        Action action = event.getAction();
        
        
        if (action == Action.KEY_PRESSED) {         
          var pressedKey = event.getKey();
          switch(pressedKey) {
          case UP -> Movement.playerMovement(player, "up");
          case DOWN -> Movement.playerMovement(player, "down");
          case LEFT -> Movement.playerMovement(player, "left");
          case RIGHT -> Movement.playerMovement(player, "right");
          case Q -> {
            context.exit(0);
            return;
          }
          default ->{
            //Si une autre touche est pressée on ne fait rien.
          }
          }
        }             
        area.draw(context,player,tiles, width, height);   
       
     
        }
      
    });
  }
}
