package fr.uge.projet;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import fr.uge.map.*;

import javax.imageio.ImageIO;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.ScreenInfo;

public class Main {

    final static double TILE_SIZE_WIDTH = 1;
    final static double TILE_SIZE_HEIGHT = 1;

    public static Tile[][] tiles;

    public static void main(String[] args) {
        Parser parser = new Parser();
        var allData = parser.isValidFile("monster_house.map");

        HashMap<String, ElementValue> size = allData.get("size").getHashMapValue();
        HashMap<String, ElementValue> encodings = allData.get("encodings").getHashMapValue();
        int widthGrid = size.get("width").getIntValue();
        int heightGrid = size.get("height").getIntValue();
        String data = allData.get("data").getStringValue();

        Application.run(Color.BLACK, context -> {
            ScreenInfo screenInfo = context.getScreenInfo();
            float screenWidth = screenInfo.getWidth();
            float screenHeight = screenInfo.getHeight();
            
            float scaleWidth = screenWidth / widthGrid;
            float scaleHeight = screenHeight / heightGrid;
            
            int adjustedTileSizeWidth = (int) (scaleWidth);
            int adjustedTileSizeHeight = (int) (scaleHeight);

            Player player = new Player(15 * adjustedTileSizeWidth, 5 * adjustedTileSizeHeight);

            tiles = new Tile[widthGrid][heightGrid];
            for (var i = 0; i < widthGrid; i++) {
                for (var j = 0; j < heightGrid; j++) {
                    char el = data.charAt(i * j);
                    String nameEncoding = parser.findEncodingName(el, encodings);
                    BufferedImage image = loadImage(nameEncoding);
                    tiles[i][j] = new Tile(image, nameEncoding != null);
                }
            }

            Graphic area = new Graphic();

            for (;;) {
                area.draw(context, player, tiles, screenWidth, screenHeight, widthGrid, heightGrid, adjustedTileSizeWidth, adjustedTileSizeHeight);
                Event event = context.pollOrWaitEvent(16);

                if (event != null && event.getAction() == Action.KEY_PRESSED) {
                    var pressedKey = event.getKey();
                    try {
                        Movement.playerInput(player, pressedKey, context, adjustedTileSizeWidth, adjustedTileSizeHeight);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Pressed Key has no usage");
                    }
                }
            }
        });
    }

    private static BufferedImage loadImage(String name) {
        String imageName = (name != null) ? name : "tile";
        try (var input = Main.class.getResourceAsStream("/Images/" + imageName + ".png")) {
            return ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
