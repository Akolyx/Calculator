package token;

public class Subtract implements Operation {
    @Override
    public int priority() {
        return 2;
    }

    @Override
    public int calc(int a, int b) {
        return a - b;
    }

    @Override
    public String toString() {
        return "-";
    }
}
