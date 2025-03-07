package backend;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos;
    private char currentChar;

    public Lexer(String input) {
        this.input = input;
        this.pos = 0;
        this.currentChar = input.charAt(pos);
    }

    private void advance() {
        pos++;
        if (pos >= input.length()) {
            currentChar = '\0'; // End of input
        } else {
            currentChar = input.charAt(pos);
        }
    }

    private void skipWhitespace() {
        while (currentChar != '\0' && Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    private int integer() {
        StringBuilder result = new StringBuilder();
        while (currentChar != '\0' && Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return Integer.parseInt(result.toString());
    }

    private String identifier() {
        StringBuilder result = new StringBuilder();
        while (currentChar != '\0' && (Character.isLetterOrDigit(currentChar) || currentChar == '_')) {
            result.append(currentChar);
            advance();
        }
        return result.toString();
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
                continue;
            }
            if (Character.isDigit(currentChar)) {
                tokens.add(new Token(TokenType.INTEGER, integer()));
                continue;
            }
            if (Character.isLetter(currentChar)) {
                String id = identifier();
                switch (id) {
                    case "mod":
                        tokens.add(new Token(TokenType.MOD, id));
                        break;
                    case "system":
                        tokens.add(new Token(TokenType.SYSTEM, id));
                        break;
                    case "output":
                        tokens.add(new Token(TokenType.OUTPUT, id));
                        break;
                    case "write":
                        tokens.add(new Token(TokenType.WRITE, id));
                        break;
                    case "input":
                        tokens.add(new Token(TokenType.INPUT, id));
                        break;
                    case "exit":
                        tokens.add(new Token(TokenType.EXIT, id));
                        break;
                    case "fn":
                        tokens.add(new Token(TokenType.FN, id));
                        break;
                    case "main":
                        tokens.add(new Token(TokenType.MAIN, id));
                        break;
                    case "string":
                        tokens.add(new Token(TokenType.STRING, id));
                        break;
                    case "int":
                        tokens.add(new Token(TokenType.INT, id));
                        break;
                    case "float":
                        tokens.add(new Token(TokenType.FLOAT, id));
                        break;
                    case "class":
                        tokens.add(new Token(TokenType.CLASS, id));
                        break;
                    case "public":
                        tokens.add(new Token(TokenType.PUBLIC, id));
                        break;
                    case "private":
                        tokens.add(new Token(TokenType.PRIVATE, id));
                        break;
                    default:
                        tokens.add(new Token(TokenType.IDENTIFIER, id));
                        break;
                }
                continue;
            }
            switch (currentChar) {
                case '=':
                    tokens.add(new Token(TokenType.ASSIGN, '='));
                    advance();
                    break;
                case ';':
                    tokens.add(new Token(TokenType.SEMICOLON, ';'));
                    advance();
                    break;
                case '(':
                    tokens.add(new Token(TokenType.LPAREN, '('));
                    advance();
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RPAREN, ')'));
                    advance();
                    break;
                case '{':
                    tokens.add(new Token(TokenType.LBRACE, '{'));
                    advance();
                    break;
                case '}':
                    tokens.add(new Token(TokenType.RBRACE, '}'));
                    advance();
                    break;
                case ',':
                    tokens.add(new Token(TokenType.COMMA, ','));
                    advance();
                    break;
                case '"':
                    tokens.add(new Token(TokenType.QUOTE, '"'));
                    advance();
                    break;
                case ':':
                    tokens.add(new Token(TokenType.COLON, ':'));
                    advance();
                    break;
                case '!':
                    tokens.add(new Token(TokenType.EXCLAMATION, '!'));
                    advance();
                    break;
                case '>':
                    tokens.add(new Token(TokenType.GREATER_THAN, '>'));
                    advance();
                    break;
                case '.':
                    tokens.add(new Token(TokenType.DOT, '.'));
                    advance();
                    break;
                default:
                    System.out.println("Unexpected character: " + currentChar); // Debugging output
                    throw new RuntimeException("Unexpected character: " + currentChar);
            }
        }
        return tokens;
    }
}