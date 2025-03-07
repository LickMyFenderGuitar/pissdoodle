import backend.*;
import modules.SystemModule;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <filename.nuxj>");
            return;
        }

        String filename = args[0];
        if (!filename.endsWith(".nuxj")) {
            System.out.println("Error: File must have a .nuxj extension");
            return;
        }

        try {
            String input = new String(Files.readAllBytes(Paths.get(filename)));
            Lexer lexer = new Lexer(input);
            List<Token> tokens = lexer.tokenize();
            System.out.println("Tokens: " + tokens); // Debugging output
            Parser parser = new Parser(tokens);
            List<Node> statements = parser.parse();
            Interpreter interpreter = new Interpreter();
            for (Node statement : statements) {
                interpreter.interpret(statement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
