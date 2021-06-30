//C:\JFLEX\bin\jflex -d src srcjflexcup\toy.flex
//java -jar C:\CUP\java-cup-11b.jar -destdir src srcjflexcup/toy.cup

import java_cup.runtime.Symbol;

import java.io.FileReader;
import java.io.IOException;

public class TesterLexicalAnalyzer {
    public static void main(String[] args) throws IOException {
        FileReader inFile = new FileReader(args[0]);
        Yylex lexer = new Yylex(inFile);

        Symbol token = lexer.next_token();
        while (token.sym != sym.EOF) {
            switch (token.sym) {
                case sym.INT: System.out.println("INT"); break;
                case sym.STRING: System.out.println("String"); break;
                case sym.STRING_CONST: System.out.println("STRING_CONST"); break;
                case sym.FLOAT: System.out.println("Float"); break;
                case sym.BOOL: System.out.println("Bool"); break;
                case sym.INT_CONST: System.out.println("INT_CONST"); break;
                case sym.FLOAT_CONST: System.out.println("FLOAT_CONST"); break;
                case sym.IF: System.out.println("IF"); break;
                case sym.FI: System.out.println("FI"); break;
                case sym.ELSE: System.out.println("ELSE"); break;
                case sym.ELIF: System.out.println("ELIF"); break;
                case sym.THEN: System.out.println("THEN"); break;
                case sym.WHILE: System.out.println("WHILE"); break;
                case sym.LPAR: System.out.println("LPAR"); break;
                case sym.RPAR: System.out.println("RPAR"); break;
                case sym.DO: System.out.println("DO"); break;
                case sym.OD: System.out.println("OD"); break;
                case sym.SEMI: System.out.println("SEMI"); break;
                case sym.COMMA: System.out.println("COMMA"); break;
                case sym.EQ: System.out.println("EQ"); break;
                case sym.GT: System.out.println("GT"); break;
                case sym.LT: System.out.println("LT"); break;
                case sym.ASSIGN: System.out.println("ASSIGN"); break;
                case sym.LE: System.out.println("LE"); break;
                case sym.GE: System.out.println("GE"); break;
                case sym.NOT: System.out.println("NOT"); break;
                case sym.PLUS: System.out.println("PLUS"); break;
                case sym.MINUS: System.out.println("MINUS"); break;
                case sym.TIMES: System.out.println("TIMES"); break;
                case sym.DIV: System.out.println("DIV"); break;
                case sym.VOID: System.out.println("VOID"); break;
                case sym.PROC: System.out.println("PROC"); break;
                case sym.CORP: System.out.println("CORP"); break;
                case sym.NULL: System.out.println("NULL"); break;
                case sym.TRUE: System.out.println("TRUE"); break;
                case sym.FALSE: System.out.println("FALSE"); break;
                case sym.OR: System.out.println("OR"); break;
                case sym.AND: System.out.println("AND"); break;
                case sym.COLON: System.out.println("COLON"); break;
                case sym.ID: System.out.println("ID"); break;
                case sym.READ: System.out.println("READ"); break;
                case sym.WRITE: System.out.println("WRITE"); break;
                case sym.NE: System.out.println("NE"); break;
                case sym.COMMENT: System.out.println("COMMENT"); break;


            }
            try {
                token = lexer.next_token();
            }catch (Error e){
                System.out.println("\u001B[31m"+ "Unexpected character!"+ "\u001B[31m");
                break;
            }
        }
    }
}
