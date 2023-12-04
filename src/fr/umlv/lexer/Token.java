package fr.umlv.lexer;

  public enum Token {
	SECTION("grid|element|data|size|encodings"),
	ATTRIBUT("name|skin|player|position|health|kind|zone|behavior|damage|text|steal|trade"),
    IDENTIFIER("[A-Za-z]+"),
    AFFECTATION("[A-Za-z]+\\s*:\\s*[A-Za-z0-9]+"),
    ZONE("\\(\\s*\\d+\\s*,\\s*\\d+\\s*\\)\\s*\\(\\s*\\d+\\s* x \\s*\\d+\\s*\\)"),
    POSITION("\\(\\s*\\d+\\s*,\\s*\\d+\\s*\\)"),
    ENCODING("\\s*[a-zA-Z]+\\s*\\(\\s*[a-zA-Z]+\\s*\\)"),
    SIZE("\\(\\s*\\d+ x \\d+\\s*\\)"),
    NUMBER("[0-9]+"),
    //LEFT_PARENS("\\("),
    //RIGHT_PARENS("\\)"),
    LEFT_BRACKET("\\["),
    //RIGHT_BRACKET("\\]"),
    //COMMA(","),
    //COLON(":"),
    DATA("\"\"\"[^\"]+\"\"\""),
    
    ;

    final String regex;

    Token(String regex) {
      this.regex = regex;
    }
  }

  