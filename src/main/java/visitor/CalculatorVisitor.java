package visitor;

import token.Brace;
import token.Number;
import token.Operation;

import java.util.Stack;

public class CalculatorVisitor implements TokenVisitor {
    private final Stack<Integer> stack;

    public CalculatorVisitor() {
        stack = new Stack<>();
    }

    @Override
    public void visit(Number token) {
        stack.push(token.getValue());
    }

    @Override
    public void visit(Brace token) {
        throw new RuntimeException("CALCULATION ERROR: unexpected brace");
    }

    @Override
    public void visit(Operation token) {
        if (stack.size() < 2) {
            throw new RuntimeException("CALCULATION ERROR: not enough arguments");
        }

        int left = stack.pop();
        int right = stack.pop();

        stack.push(token.calc(right, left));
    }

    public int getResult() {
        if (stack.size() != 1) {
            throw new RuntimeException("CALCULATION ERROR: invalid result");
        }

        return stack.pop();
    }
}
