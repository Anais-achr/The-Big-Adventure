//
//
//	private static Boolean isElement(String elem) {
//		for(Elem e : Elem.values()) {
//			if(e.toString().equals(elem)) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	public static int[] getPosition(Lexer lexer, Result result){
//		int[] position = new int[2];
//		
//		while((result = lexer.nextResult()) != null && !result.token().equals(Token.RIGHT_PARENS)) {
//			if(result.token().equals(Token.NUMBER)) {
//				if(position[0] == 0) {
//					position[0] = Integer.parseInt(result.content());
//				}
//				else {
//					position[1] = Integer.parseInt(result.content());
//					break;
//				}
//			}
//		
//			//System.out.println(result);
//		}
//		System.out.println("\n");
//		return position;
//	}
//	
//	public static HashMap<String, Object> validFile(String filePath) throws IOException {
//		HashMap<String, Object> data = new HashMap<>();
//		
//	    var path = Path.of(filePath);
//	    var text = Files.readString(path);
//	    var lexer = new Lexer(text);
//
//	    String firstIdentifier = null;
//        String secondIdentifier = null;
//        
//        String dataString = null;
//        HashMap<String, Object> size = new HashMap<String, Object>();
//	    HashMap<String, Object> encodings = new HashMap<String, Object>();
//	    HashMap<String, Object> elements = new HashMap<String, Object>();
//	    HashMap<String, Object> element = new HashMap<String, Object>();
//	    int[] positions = new int[2];
//
//
//	    int onSize = 0;
//	    int onEncodings = 0;
//	    int onData = 0;
//	    int onElement = 0;
//	    int onPosition = 0;
//	    
//	    
//	    Result result = lexer.nextResult();
//
//	    while (result != null) {
//	        switch (result.content()) {
//	            case "size":
//	            	onSize = 1;
//	            	onEncodings = 0;
//	            	onData = 0;
//	            	onElement = 0;
//	                break;
//	            case "encodings":
//	            	onEncodings = 1;
//	            	onSize = 0;
//	            	onData = 0;
//	            	onElement = 0;
//	                break;
//	            case "data":
//	            	onData = 1;
//	            	onSize = 0;
//	            	onEncodings = 0;
//	            	onElement = 0;
//	                break;
//	            case "element":
//	            	onSize = 0;
//	            	onEncodings = 0;
//	            	onData = 0;
//	            	onElement = 1;
//	                break;
//	        }
//	        if(onSize == 1) {
//	        	if(result.token().equals(Token.NUMBER)) {
//	        		if(!size.containsKey("height")) {
//	        			size.put("height",  Integer.parseInt(result.content()));
//	        		}else {
//	        			size.put("width",  Integer.parseInt(result.content()));
//	        			onSize = 0;
//	        		}
//	        	}
//	        	
//	        }
//	        
//	       
//	        
//	        if(onEncodings == 1) {
//	        	if(result.token().equals(Token.IDENTIFIER)
//	        			&& !result.content().equals("data") 
//	        			&& !result.content().equals("size") 
//	        			&& !result.content().equals("grid")
//	        			&& !result.content().equals("encodings")
//	        			&& !result.content().equals("element")) 
//	        	{
//	        		
//	        		if(firstIdentifier == null && isElement(result.content())) {
//	        			firstIdentifier = result.content();
//	        		}
//	        		else if(firstIdentifier != null) {
//	        			secondIdentifier = result.content();
//	        		
//	        				encodings.put(firstIdentifier, secondIdentifier);
//		        			firstIdentifier = null;
//		        			secondIdentifier = null;
//	        		}
//	        	}
//	        }
//	        
//	        if(onData == 1) {
//	        	if(result.token().equals(Token.QUOTE)
//	        			&& !result.content().equals("data") 
//	        			&& !result.content().equals("size") 
//	        			&& !result.content().equals("grid")
//	        			&& !result.content().equals("encodings")
//	        			&& !result.content().equals("element"))
//	        		
//	        	{
//	        		
//	        		dataString = result.content();
//	        	}
//	        }
//	        System.out.println(result);
//	        result = lexer.nextResult();
//	        
//	        //System.out.println(result);
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.HashMap;
//
//import fr.uge.lexer.Lexer;
//import fr.uge.lexer.Result;
//import fr.uge.lexer.Token;
//import fr.uge.map.Element.Elem;
//
//}
//	    
//	    data.put("size", size);
//	    data.put("encodings", encodings);
//	    data.put("data", dataString);
//	    data.put("element", elements);
//
//
//	    return data;
//	}
//
//
//    public static void main(String[] args) throws IOException {
//    	var res = validFile("map.map");
//    	System.out.println(res);
//
//    }