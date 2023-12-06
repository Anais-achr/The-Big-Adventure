package fr.uge.map;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.uge.lexer.Lexer;
import fr.uge.lexer.Result;
import fr.uge.lexer.Token;
import fr.uge.map.Element.Elem;

public record Parser() {

    public static Lexer createLexer(String filePath) {
        try {
            var path = Path.of(filePath);
            var text = Files.readString(path);
            var lexer = new Lexer(text);
            return lexer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private HashMap<String, ElementValue> getElement(Lexer lexer, Result result, HashMap<String, ElementValue> elements) throws IllegalArgumentException {
        Objects.requireNonNull(result);
        HashMap<String, ElementValue> elem = new HashMap<String, ElementValue>();
        int size = elements.size() + 1;
        ElementValue suivant;
        while ((result = lexer.nextResult()) != null && !result.token().equals(Token.LEFT_BRACKET)) {
            if (result.token().equals(Token.ATTRIBUT)) {
                var attribut = result.content();
                var tmp = lexer.nextResult();

                if (attribut.equals("zone") || attribut.equals("position")) {
                    suivant = new ElementValue(formateCoordonate(attribut, tmp.content()));
                } else {
                    suivant = new ElementValue(tmp.content());
                }

                elem.put(attribut, suivant);
            }
        }
        elements.put("element" + size, new ElementValue(elem));
        return elements;
    }

    private HashMap<String, ElementValue> getEncoding(Lexer lexer, Result result) {
        Objects.requireNonNull(result);
        HashMap<String, ElementValue> encodings = new HashMap<>();
        String[] parts = null;

        while (result != null && result.token().equals(Token.ENCODING)) {
            parts = result.content().split("\\(\\s*|\\s*\\)");
            if (!isTile(parts[0].trim())) {
                throw new IllegalArgumentException("Tile inconnue : " + parts[0]);
            }

            final String[] finalParts = parts;
            
            encodings.compute(parts[0].trim(), (k, existingValue) -> {
                if (existingValue != null) {
                    throw new IllegalArgumentException("Encoding " + finalParts[0] + " déjà présent. ");
                }
                if (finalParts[1].length() >= 2) {
                    throw new IllegalArgumentException("Encoding " + finalParts[1] + " ne peut pas contenir plusieurs lettres. ");
                }
                return new ElementValue(finalParts[1].trim());
            });

            result = lexer.nextResult();
        }

        return encodings;
    }


    private boolean isTile(String tile) {
	    Objects.requireNonNull(tile);
    	return Stream.of(Elem.values()).map(Enum::toString).anyMatch(tile::equals);
	}

	private HashMap<String, ElementValue> getSize(Result result) {
        Objects.requireNonNull(result);
        HashMap<String, ElementValue> size = new HashMap<String, ElementValue>();
        String[] numbers = result.content().replaceAll("[^0-9]+", " ").trim().split(" ");

        size.put("width", new ElementValue(Integer.parseInt(numbers[0])));
        size.put("height", new ElementValue(Integer.parseInt(numbers[1])));

        return size;
    }

    private HashMap<String, ElementValue> formateCoordonate(String attribute, String coordonate) {
        HashMap<String, ElementValue> fomatedCoordonate = new HashMap<String, ElementValue>();
        String format = null;
        String[] keys = null;
        
        if (attribute.equals("position")) {
            format = "\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)\\s*";
            keys = new String[]{"x", "y"};
        } else if (attribute.equals("zone")) {
            format = "\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)\\s*\\(\\s*(\\d+)\\s*x\\s*(\\d+)\\s*\\)";
            keys = new String[]{"x", "y", "width", "height"};
        }

        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(coordonate);

        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
            	fomatedCoordonate.put(keys[i - 1], new ElementValue(Integer.parseInt(matcher.group(i))));
            }
        } else {
            throw new IllegalArgumentException("Format de coordonées non valide : " + coordonate);
        }

        return fomatedCoordonate;
    }

    private String getData(Result result) {
        Objects.requireNonNull(result);

        return Stream.of(result.content().replaceAll("\"\"\"", "").split("\n"))
                .map(String::trim)
                .collect(Collectors.joining("\n"))
                .trim();
    }

    private boolean checkWidthHeight(int dimGrid, String data) {
    	return Arrays.stream(data.split("\n")).map(String::trim).mapToInt(String::length).sum() == dimGrid;
    }
    
    public String findEncodingName(char el, HashMap<String, ElementValue> encodings) {
    	
        for (Entry<String, ElementValue> entry : encodings.entrySet()) {
            ElementValue value = entry.getValue();
            if (value != null && value.getStringValue() != null && value.getStringValue().equals(String.valueOf(el))) {
                return entry.getKey().toLowerCase();
            }
        }
        return null;
    }


    public HashMap<String, ElementValue> getAllData(String filePath) throws IOException, IllegalArgumentException {
        Lexer lexer = createLexer(filePath);
        HashMap<String, ElementValue> allData = new HashMap<>();
        HashMap<String, ElementValue> elements = new HashMap<>();

        Result result = lexer.nextResult();

        while (result != null) {
            if (result.content().equals("element")) {
                allData.put("element", new ElementValue(getElement(lexer, result, elements)));
            } else if (result.token().equals(Token.ENCODING)) {
                allData.put("encodings", new ElementValue(getEncoding(lexer, result)));
            } else if (result.token().equals(Token.SIZE)) {
                allData.put("size", new ElementValue(getSize(result)));
            } else if (result.token().equals(Token.DATA)) {
                allData.put("data", new ElementValue(getData(result)));
            }
            result = lexer.nextResult();
        }
        return allData;
    }
    
    public HashMap<String, ElementValue> isValidFile(String filePath) {
        try {
            var allData = new Parser().getAllData(filePath);

            ElementValue sizeTab = allData.get("size");
            if (sizeTab == null) {
                throw new IllegalArgumentException("Dimensions manquantes");
            }

            int height = sizeTab.get("height").getIntValue();
            int width = sizeTab.get("width").getIntValue();

            if (allData.get("data") == null) {
                throw new IllegalArgumentException("Grille manquante");
            }

            if (!checkWidthHeight(height * width, allData.get("data").getStringValue())) {
                throw new IllegalArgumentException("Erreur : La grille n'a pas les bonnes dimensions spécifiées.");
            }

            return allData;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
            return null;
        }
    }

}
