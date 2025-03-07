package backend;

import java.util.List;

public abstract class Node {
}

class ModNode extends Node {
    // Represents "mod system;"
}

class OutputNode extends Node {
    public final Node expr;

    public OutputNode(Node expr) {
        this.expr = expr;
    }
}

class InputNode extends Node {
    public final Node expr;

    public InputNode(Node expr) {
        this.expr = expr;
    }
}

class ExitNode extends Node {
    // Represents "output:exit();"
}

class FunctionNode extends Node {
    public final Token visibility;
    public final Token name;
    public final List<Token> parameters;
    public final List<Node> body;

    public FunctionNode(Token visibility, Token name, List<Token> parameters, List<Node> body) {
        this.visibility = visibility;
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }
}

class ClassNode extends Node {
    public final Token name;
    public final List<Node> body;

    public ClassNode(Token name, List<Node> body) {
        this.name = name;
        this.body = body;
    }
}

class VarDeclNode extends Node {
    public final Token type;
    public final Token varName;
    public final Node value;

    public VarDeclNode(Token type, Token varName, Node value) {
        this.type = type;
        this.varName = varName;
        this.value = value;
    }
}

class StringNode extends Node {
    public final String value;

    public StringNode(String value) {
        this.value = value;
    }
}

class FloatNode extends Node {
    public final float value;

    public FloatNode(float value) {
        this.value = value;
    }
}