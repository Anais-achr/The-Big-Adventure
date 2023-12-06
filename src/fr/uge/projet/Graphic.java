package fr.uge.projet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.zen5.ApplicationContext;

public class Graphic {
 
  void drawPlayer(Graphics2D graphics, Player player, int tilesSizeLargeur, int tilesSizeLongueur) {
    BufferedImage imagePlayer = null;        
    String playerImageName = "baba";
    try(var input = Main.class.getResourceAsStream("/Images/"+playerImageName+".png")) {
      imagePlayer = ImageIO.read(input);
    } catch (IOException e) {          
      e.printStackTrace();
    }        
    graphics.drawImage(imagePlayer,player.getX(),player.getY(),tilesSizeLargeur, tilesSizeLongueur, null); 
  }
  
  void drawTile(Graphics2D graphics, Tile[][] tiles, int widthGrid, int heightGrid, int tilesSizeLargeur, int tilesSizeLongueur) {
    for(var i= 0; i < widthGrid; i++) {
      for(var j =0; j < heightGrid; j++) {
        if(tiles[i][j] == null) continue;
        graphics.drawImage(tiles[i][j].image(),i * tilesSizeLargeur,j*tilesSizeLongueur, tilesSizeLargeur, tilesSizeLongueur, null);
      }
    } 
  }  
  
  void draw(ApplicationContext context, Player player, Tile[][] tiles, float  width, float height, int widthGrid, int heightGrid, int tilesSizeLargeur, int tilesSizeLongueur) {
    var buffer = new BufferedImage((int)width,(int)height,BufferedImage.TYPE_INT_ARGB);
    Graphics2D displayBuffer = (Graphics2D)buffer.getGraphics();
    context.renderFrame(graphics -> {
      
      // hide the previous rectangle
      
      displayBuffer.setColor(Color.BLACK);
      graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
      
      drawTile(displayBuffer, tiles, widthGrid, heightGrid, tilesSizeLargeur, tilesSizeLongueur);      
      drawPlayer(displayBuffer, player, tilesSizeLargeur, tilesSizeLongueur);
      
      graphics.drawImage((Image)buffer, 0,0, null);
    });
  }
}  
  
   
 

