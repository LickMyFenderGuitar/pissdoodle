package backend;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
    }

    private Token currentToken() {
        if (pos >= tokens.size()) {
            return null;
        }
        return tokens.get(pos);
    }

    private void eat(TokenType type) {
        if (currentToken().type == type) {
            pos++;
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken());
        }
    }

    private Node factor() {
        Token token = currentToken();
        if (token.type == TokenType.INTEGER) {
            eat(TokenType.INTEGER);
            if (currentToken().type == TokenType.DOT) {
                eat(TokenType.DOT);
                Token fractionalPart = currentToken();
                eat(TokenType.INTEGER);
                return new FloatNode(Float.parseFloat(token.value + "." + fractionalPart.value));
            }
            return new NumNode((int) token.value);
        } else if (token.type == TokenType.QUOTE) {
            eat(TokenType.QUOTE);
            StringBuilder str = new StringBuilder();
            while (currentToken().type != TokenType.QUOTE) {
                str.append(currentToken().value);
                eat(currentToken().type);
            }
            eat(TokenType.QUOTE);
            return new StringNode(str.toString());
        }
        throw new RuntimeException("Unexpected token: " + token);
    }

    private Node term() {
        Node node = factor();
        while (currentToken() != null && (currentToken().type == TokenType.MUL || currentToken().type == TokenType.DIV)) {
            Token token = currentToken();
            if (token.type == TokenType.MUL) {
                eat(TokenType.MUL);
            } else if (token.type == TokenType.DIV) {
                eat(TokenType.DIV);
            }
            node = new BinOpNode(node, token, factor());
        }
        return node;
    }

    private Node expr() {
        Node node = term();
        while (currentToken() != null && (currentToken().type == TokenType.PLUS || currentToken().type == TokenType.MINUS)) {
            Token token = currentToken();
            if (token.type == TokenType.PLUS) {
                eat(TokenType.PLUS);
            } else if (token.type == TokenType.MINUS) {
                eat(TokenType.MINUS);
            }
            node = new BinOpNode(node, token, term());
        }
        return node;
    }

    private Node statement() {
        Token token = currentToken();
        switch (token.type) {
            case MOD:
                eat(TokenType.MOD);
                eat(TokenType.SYSTEM);
                eat(TokenType.SEMICOLON);
                return new ModNode();
            case OUTPUT:
                eat(TokenType.OUTPUT);
                if (currentToken().type == TokenType.COLON) {
                    eat(TokenType.COLON);
                    if (currentToken().type == TokenType.WRITE) {
                        eat(TokenType.WRITE);
                        eat(TokenType.LPAREN);
                        Node expr = expr();
                        eat(TokenType.RPAREN);
                        eat(TokenType.SEMICOLON);
                        return new OutputNode(expr);
                    } else if (currentToken().type == TokenType.INPUT) {
                        eat(TokenType.INPUT);
                        eat(TokenType.LPAREN);
                        Node expr = expr();
                        eat(TokenType.RPAREN);
                        eat(TokenType.SEMICOLON);
                        return new InputNode(expr);
                    } else if (currentToken().type == TokenType.EXIT) {
                        eat(TokenType.EXIT);
                        eat(TokenType.LPAREN);
                        eat(TokenType.RPAREN);
                        eat(TokenType.SEMICOLON);
                        return new ExitNode();
                    }
                }
                break;
            case FN:
                eat(TokenType.FN);
                eat(TokenType.MAIN);
                eat(TokenType.LPAREN);
                // Handle function parameters
                while (currentToken().type != TokenType.RPAREN) {
                    eat(TokenType.IDENTIFIER);
                    if (currentToken().type == TokenType.COMMA) {
                        eat(TokenType.COMMA);
                    }
                }
                eat(TokenType.RPAREN);
                eat(TokenType.LBRACE);
                // Handle function body
                while (currentToken().type != TokenType.RBRACE) {
                    statement();
                }
                eat(TokenType.RBRACE);
                return new FunctionNode(null, null, null, null); // Update this line with correct arguments
            case STRING:
            case INT:
            case FLOAT:
                Token type = currentToken();
                eat(type.type);
                Token varName = currentToken();
                eat(TokenType.IDENTIFIER);
                eat(TokenType.ASSIGN);
                Node value = expr();
                eat(TokenType.SEMICOLON);
                return new VarDeclNode(type, varName, value);
            case CLASS:
                eat(TokenType.CLASS);
                Token className = currentToken();
                eat(TokenType.IDENTIFIER);
                eat(TokenType.COLON);
                eat(TokenType.LBRACE);
                List<Node> classBody = new ArrayList<>();
                while (currentToken().type != TokenType.RBRACE) {
                    classBody.add(classStatement());
                }
                eat(TokenType.RBRACE);
                return new ClassNode(className, classBody);
            case PUBLIC:
            case PRIVATE:
                Token visibility = currentToken();
                eat(visibility.type);
                eat(TokenType.FN);
                Token functionName = currentToken();
                eat(TokenType.IDENTIFIER);
                eat(TokenType.LPAREN);
                List<Token> parameters = new ArrayList<>();
                while (currentToken().type != TokenType.RPAREN) {
                    parameters.add(currentToken());
                    eat(currentToken().type);
                    if (currentToken().type == TokenType.COMMA) {
                        eat(TokenType.COMMA);
                    }
                }
                eat(TokenType.RPAREN);
                eat(TokenType.LBRACE);
                List<Node> functionBody = new ArrayList<>();
                while (currentToken().type != TokenType.RBRACE) {
                    functionBody.add(statement());
                }
                eat(TokenType.RBRACE);
                return new FunctionNode(visibility, functionName, parameters, functionBody);
            default:
                throw new RuntimeException("Unexpected token: " + token);
        }
        return null;
    }

    private Node classStatement() {
        Token token = currentToken();
        switch (token.type) {
            case FN:
                eat(TokenType.FN);
                Token functionName = currentToken();
                eat(TokenType.IDENTIFIER);
                eat(TokenType.LPAREN);
                List<Token> parameters = new ArrayList<>();
                while (currentToken().type != TokenType.RPAREN) {
                    parameters.add(currentToken());
                    eat(currentToken().type);
                    if (currentToken().type == TokenType.COMMA) {
                        eat(TokenType.COMMA);
                    }
                }
                eat(TokenType.RPAREN);
                eat(TokenType.LBRACE);
                List<Node> functionBody = new ArrayList<>();
                while (currentToken().type != TokenType.RBRACE) {
                    functionBody.add(statement());
                }
                eat(TokenType.RBRACE);
                return new FunctionNode(null, functionName, parameters, functionBody);
            case PUBLIC:
            case PRIVATE:
                Token visibility = currentToken();
                eat(visibility.type);
                eat(TokenType.FN);
                functionName = currentToken();
                eat(TokenType.IDENTIFIER);
                eat(TokenType.LPAREN);
                parameters = new ArrayList<>();
                while (currentToken().type != TokenType.RPAREN) {
                    parameters.add(currentToken());
                    eat(currentToken().type);
                    if (currentToken().type == TokenType.COMMA) {
                        eat(TokenType.COMMA);
                    }
                }
                eat(TokenType.RPAREN);
                eat(TokenType.LBRACE);
                functionBody = new ArrayList<>();
                while (currentToken().type != TokenType.RBRACE) {
                    functionBody.add(statement());
                }
                eat(TokenType.RBRACE);
                return new FunctionNode(visibility, functionName, parameters, functionBody);
            default:
                throw new RuntimeException("Unexpected token in class body: " + token);
        }
    }

    public List<Node> parse() {
        List<Node> statements = new ArrayList<>();
        while (currentToken() != null) {
            statements.add(statement());
        }
        return statements;
    }
}