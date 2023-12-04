package fr.uge.map;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.uge.lexer.Lexer;
import fr.uge.lexer.Result;
import fr.uge.lexer.Token;
import fr.uge.map.Element.Elem;

public record Parser() {
	
	public static Lexer createLexer(String filePath) throws IOException {
		var path = Path.of(filePath);
	    var text = Files.readString(path);
	    var lexer = new Lexer(text);
	    
	    return lexer;
	}
	
	private HashMap<String, Object> getElement(Lexer lexer, Result result, HashMap<String, Object> elements) throws IllegalArgumentException{
		Objects.requireNonNull(result);
        HashMap<String, Object> elem = new HashMap<String, Object>();
        int size = elements.size() + 1;
        Object suivant;
        while ((result = lexer.nextResult()) != null && !result.token().equals(Token.LEFT_BRACKET)) {
            if (result.token().equals(Token.ATTRIBUT)) {
                var attribut = result.content();
                var tmp = lexer.nextResult();
                
                if(attribut.equals("zone") || attribut.equals("position")){
                	suivant = formateCoordonate(attribut, tmp.content());
                }
                else {
                	suivant = tmp.content();
                }
                elem.put(attribut, suivant);
            }
        }
        elements.put("element" + size, elem);
        return elements;
    }
	
	private boolean isTile(String tile) {
	    Objects.requireNonNull(tile);

	    for (Elem e : Elem.values()) {
	        if(tile.equals(e.toString())) {
	        	return true;
	        }
	    }
	    return false;
	}
	
 	private HashMap<String, Object> getEncoding(Lexer lexer, Result result) {
		Objects.requireNonNull(result);
	    HashMap<String, Object> encodings = new HashMap<String, Object>();
	    String[] parts = null;

	    while (result != null && result.token().equals(Token.ENCODING)) {
	        parts = result.content().split("\\(\\s*|\\s*\\)");
	        if(!isTile(parts[0].trim())) {
	        	throw new IllegalArgumentException("Tile inconnue : " + parts[0]);
	        }
	        if(encodings.containsValue(parts[1])) {
	        	throw new IllegalArgumentException("Encoding " +  parts[1] + " déjà présent. ");
	        }
	        if(parts[1].length() >= 2) {
	        	throw new IllegalArgumentException("Encoding " +  parts[1] + " ne peut pas contenir plusieurs lettres. ");
	        }
	        encodings.put(parts[0].trim(), parts[1].trim());
	        
	        result = lexer.nextResult();
	    }

	    return encodings;
	}

	private HashMap<String, Object> getSize(Result result) {
		Objects.requireNonNull(result);
		HashMap<String, Object> size = new HashMap<String, Object>();
		String[] numbers = result.content().replaceAll("[^0-9]+", " ").trim().split(" ");
		
		size.put("width", Integer.parseInt(numbers[0]));
		size.put("height", Integer.parseInt(numbers[1]));
		
		return size;
	}
	
	private HashMap<String, Integer> formateCoordonate(String attribute, String coordonate){
		HashMap<String, Integer> fomatedCoordonate = new HashMap<String, Integer>();
		String format = null;
		
		if(attribute.equals("position")) {
			format = "\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)\\s*";
		}else if(attribute.equals("zone")) {
			format = "\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)\\s*\\(\\s*(\\d+)\\s*x\\s*(\\d+)\\s*\\)";
		}
		
		Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(coordonate);
        
        if(matcher.matches()) {
        	fomatedCoordonate.put("x", Integer.parseInt(matcher.group(1)));
        	fomatedCoordonate.put("y", Integer.parseInt(matcher.group(2)));
        	if(matcher.groupCount() == 4) {
        		fomatedCoordonate.put("width", Integer.parseInt(matcher.group(3)));
            	fomatedCoordonate.put("height", Integer.parseInt(matcher.group(4)));
            }
        }else {
        	throw new IllegalArgumentException("Format de coordonées non valide : " + coordonate);
        }
        
        
		return fomatedCoordonate;
	}
	
	private String getData(Result result) {
		Objects.requireNonNull(result);
		return result.content().replaceAll("\"\"\"", "");
	}
	
	private boolean checkWidthHeight(int dimGrid, String data) {
    	String[] lines = data.split("\n");

    	int count = 0;
        for (String line : lines) {
            count += line.trim().length();
        }
        return count == dimGrid;
	}
	
	public HashMap<String, Object> getAllData(String filePath) throws IOException, IllegalArgumentException{
	    Lexer lexer = createLexer(filePath);
	    HashMap<String, Object> allData = new HashMap<>();
	    HashMap<String, Object> elements = new HashMap<>();
	    
	    Result result = lexer.nextResult();

	    while (result != null) {
	        if (result.content().equals("element")) {
	            allData.put("element", getElement(lexer, result, elements));
	        } else if (result.token().equals(Token.ENCODING)) {
	            allData.put("encodings", getEncoding(lexer, result));
	        } else if (result.token().equals(Token.SIZE)) {
	            allData.put("size", getSize(result));
	        } else if (result.token().equals(Token.DATA)) {
	            allData.put("data", getData(result));
	        }
	        result = lexer.nextResult();
	    }
	    return allData;
	}
	
	public HashMap<String, Object> isValidFile(String filePath) {
	    try {
	        int data = 1;
	        int size = 1;
	        var allData = new Parser().getAllData(filePath);

	        if (!allData.containsKey("size")) {
	            size = 0;
	            throw new IllegalArgumentException("Dimensions manquantes");
	        } else if (!allData.containsKey("data")) {
	            data = 0;
	            throw new IllegalArgumentException("grille manquante");
	        } else if (!allData.containsKey("encodings")) {
	            throw new IllegalArgumentException("Encodings manquants");
	        }

	        if (size == 1 && data == 1) {
	            HashMap<String, Integer> sizeTab = (HashMap<String, Integer>) allData.get("size");
	            Integer height = sizeTab.get("height");
	            Integer width = sizeTab.get("width");
	            if (!checkWidthHeight(height * width, allData.get("data").toString())) {
	                throw new IllegalArgumentException("Erreur : La grille n'a pas les bonnes dimensions spécifiées.");
	            }
	        }
	        return allData;
	    } catch (IOException | IllegalArgumentException e) {
	        System.out.println("Erreur : " + e.getMessage());
	        // e.printStackTrace();
	        return null;
	    }
	}

}