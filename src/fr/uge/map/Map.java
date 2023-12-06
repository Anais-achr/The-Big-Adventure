package fr.uge.map;

import java.io.IOException;

public class Map {
	public static void main(String[] args) throws IOException {
		Parser parser = new Parser();
		var allData = parser.isValidFile("monster_house.map");
		if(allData != null) {
			System.out.println(allData);
		}
	}
}
