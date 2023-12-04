package fr.uge.projet;

import java.awt.image.BufferedImage;
import java.util.Objects;

public record Tile(BufferedImage image, boolean collision) {

  public Tile{
    Objects.requireNonNull(image);
    Objects.requireNonNull(collision);
    
    
  }

}

