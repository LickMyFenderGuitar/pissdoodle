package backend;

import modules.SystemModule;

public class Interpreter {
    private final SystemModule systemModule = new SystemModule();

    public int visit(Node node) {
        if (node instanceof ModNode) {
            // Handle mod system;
            System.out.println("Module system loaded.");
            return 0;
        } else if (node instanceof OutputNode) {
            OutputNode outputNode = (OutputNode) node;
            if (outputNode.expr instanceof StringNode) {
                systemModule.write(((StringNode) outputNode.expr).value);
            }
            return 0;
        } else if (node instanceof InputNode) {
            InputNode inputNode = (InputNode) node;
            if (inputNode.expr instanceof StringNode) {
                systemModule.input(((StringNode) inputNode.expr).value);
            }
            return 0;
        } else if (node instanceof ExitNode) {
            systemModule.exit();
        } else if (node instanceof FunctionNode) {
            FunctionNode functionNode = (FunctionNode) node;
            System.out.println("Function " + functionNode.name.value + " defined with visibility " + functionNode.visibility.value);
            return 0;
        } else if (node instanceof ClassNode) {
            ClassNode classNode = (ClassNode) node;
            System.out.println("Class " + classNode.name.value + " defined.");
            for (Node classBodyNode : classNode.body) {
                visit(classBodyNode);
            }
            return 0;
        } else if (node instanceof VarDeclNode) {
            VarDeclNode varDeclNode = (VarDeclNode) node;
            System.out.println("Variable declared: " + varDeclNode.varName.value);
            return 0;
        } else if (node instanceof NumNode) {
            return ((NumNode) node).value;
        } else if (node instanceof FloatNode) {
            return (int) ((FloatNode) node).value;
        } else if (node instanceof StringNode) {
            System.out.print(((StringNode) node).value);
            return 0;
        } else if (node instanceof BinOpNode) {
            BinOpNode binOpNode = (BinOpNode) node;
            switch (binOpNode.op.type) {
                case PLUS:
                    return visit(binOpNode.left) + visit(binOpNode.right);
                case MINUS:
                    return visit(binOpNode.left) - visit(binOpNode.right);
                case MUL:
                    return visit(binOpNode.left) * visit(binOpNode.right);
                case DIV:
                    return visit(binOpNode.left) / visit(binOpNode.right);
                case INTEGER:
                    return ((NumNode) binOpNode.left).value;
                case CLASS:
                case PUBLIC:
                case PRIVATE:
                    throw new UnsupportedOperationException("Operation not supported for type: " + binOpNode.op.type);
                case COLON:
                case EXCLAMATION:
                case GREATER_THAN:
                case DOT:
                case WRITE:
                case ASSIGN:
                case OUTPUT:
                case SYSTEM:
                case RPAREN:
                case MOD:
                case LPAREN:
                case FN:
                case MAIN:
                case LBRACE:
                case STRING:
                case IDENTIFIER:
                case SEMICOLON:
                case EXIT:
                case INPUT:
                case COMMA:
                case INT:
                case FLOAT:
                case QUOTE:
                case RBRACE:
                    throw new UnsupportedOperationException("Operation not supported for type: " + binOpNode.op.type);
            }
        }
        throw new RuntimeException("Unexpected node: " + node);
    }

    public void interpret(Node tree) {
        visit(tree);
    }
}