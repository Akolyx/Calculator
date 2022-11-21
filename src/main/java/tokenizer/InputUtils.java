package tokenizer;

import java.io.IOException;
import java.io.InputStream;

public class InputUtils {
    private final InputStream input;
    private char current;
    private boolean getBack;

    public InputUtils(final InputStream input) {
        this.input = input;
        getBack = false;
    }

    public char getCurrent() {
        return current;
    }

    public char getNext() {
        if (getBack) {
            getBack = false;
            return current;
        }

        try {
            current = (char) input.read();
            return current;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back() {
        getBack = true;
    }
}
