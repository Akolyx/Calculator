package visitor;

import token.*;
import token.Number;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    private final List<Token> result;
    private final Stack<Token> operations;

    public ParserVisitor() {
        result = new ArrayList<>();
        operations = new Stack<>();
    }

    @Override
    public void visit(Number token) {
        result.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token instanceof LeftBrace) {
            operations.push(token);
        } else {
            while (!operations.isEmpty() && !(operations.peek() instanceof LeftBrace)) {
                result.add(operations.pop());
            }
            if (operations.isEmpty()) {
                throw new RuntimeException("PARSING ERROR: braces mismatch");
            }

            operations.pop();
        }
    }

    @Override
    public void visit(Operation token) {
        while (!operations.isEmpty() && operations.peek() instanceof Operation
                && ((Operation) operations.peek()).priority() <= token.priority()) {
            result.add(operations.pop());
        }

        operations.push(token);
    }

    public List<Token> getResult() {
        while (!operations.isEmpty()) {
            if (operations.peek() instanceof LeftBrace) {
                throw new RuntimeException("PARSING ERROR: braces mismatch");
            }

            result.add(operations.pop());
        }

        return result;
    }
}
