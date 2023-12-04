package fr.umlv.projet;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.zen5.ApplicationContext;

public class Graphic {
  void draw(ApplicationContext context, Player player, Tile[][] tile, float  width, float height) {
    context.renderFrame(graphics -> {
      // hide the previous rectangle
      graphics.setColor(Color.BLACK);
      graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
      
      // show a new ellipse at the position of the pointer
      for(var i= 0; i < Main.gridWidth; i++) {
        for(var j =0; j < Main.gridHeight; j++) {
          if(tile[i][j] == null) continue;
          graphics.drawImage(tile[i][j].image(),i * Main.tileSize,j*Main.tileSize,Main.tileSize,Main.tileSize, null);
        }
      }
      BufferedImage image2 = null;        
      String tileName2 = "baba";
      try(var input = Main.class.getResourceAsStream("/"+tileName2+".png")) {
        image2 = ImageIO.read(input);
      } catch (IOException e) {          
        e.printStackTrace();
      }        
      graphics.drawImage(image2,player.getX(),player.getY(),Main.tileSize,Main.tileSize, null);     
    });
  }
}  
  
   
 

