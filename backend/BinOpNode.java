package backend;

public class BinOpNode extends Node {
    public final Node left;
    public final Token op;
    public final Node right;

    public BinOpNode(Node left, Token op, Node right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
}