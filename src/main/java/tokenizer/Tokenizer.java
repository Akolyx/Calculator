package tokenizer;

import token.*;
import token.Number;

import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

public class Tokenizer {
    private final InputUtils input;
    private State state;

    public Tokenizer(final InputStream input) {
        this.input = new InputUtils(input);
        state = State.SYMBOL;
    }

    private Token nextToken() {
        char symbol;

        switch (state) {
            case SYMBOL:
                symbol = input.getNext();

                switch (symbol) {
                    case '(':
                        return new LeftBrace();
                    case ')':
                        return new RightBrace();
                    case '+':
                        return new Add();
                    case '-':
                        return new Subtract();
                    case '*':
                        return new Multiply();
                    case '/':
                        return new Divide();
                    default:
                        if (symbol == (char) -1) {
                            state = State.END;

                            return nextToken();
                        }
                        if (Character.isWhitespace(symbol)) {
                            return nextToken();
                        }
                        if (Character.isDigit(symbol)) {
                            state = State.NUMBER;

                            return nextToken();
                        }

                        state = State.ERROR;

                        return nextToken();
                }

            case NUMBER:
                int number = input.getCurrent() - '0';

                while (Character.isDigit(input.getNext())) {
                    number = number * 10 + (input.getCurrent() - '0');
                }

                input.back();
                state = State.SYMBOL;

                return new Number(number);

            case ERROR:
                throw new RuntimeException("TOKENIZING ERROR: unknown token");

            case END:
                return null;

            default:
                throw new RuntimeException("TOKENIZING ERROR: state unknown");
        }
    }

    public List<Token> tokenize() {
        List<Token> res = new ArrayList<>();

        while (true) {
            Token token = nextToken();

            if (token == null) {
                return res;
            } else {
                res.add(token);
            }
        }
    }

}
