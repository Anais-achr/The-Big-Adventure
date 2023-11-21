package uge.map;




public class Map implements Element{
  
  public static String[][] Grid(int width, int height,String data) {
    String[][] grid = new String[width][height];
    for(var i = 0; i < width; i++) {
      for(var j = 0; j < height; j++) {
        var n = j+(i*j);
        grid[i][j] = data[n];
      }
    }
    return grid;
  }
  

}