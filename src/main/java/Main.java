import token.Token;
import tokenizer.Tokenizer;
import visitor.CalculatorVisitor;
import visitor.ParserVisitor;
import visitor.PrinterVisitor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Tokenizer tokenizer = new Tokenizer(System.in);

            List<Token> tokens = tokenizer.tokenize();

            ParserVisitor parserVisitor = new ParserVisitor();
            tokens.forEach(t -> t.accept(parserVisitor));
            List<Token> parsed = parserVisitor.getResult();

            PrinterVisitor printerVisitor = new PrinterVisitor(System.out);
            System.out.print("Expression in Polish notation: ");
            parsed.forEach(t -> t.accept(printerVisitor));

            System.out.println();

            CalculatorVisitor calculatorVisitor = new CalculatorVisitor();
            parsed.forEach(t -> t.accept(calculatorVisitor));
            System.out.println("Expression result: " + calculatorVisitor.getResult());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
