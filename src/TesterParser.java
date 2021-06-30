import java.io.FileReader;
//java -jar C:\CUP\java-cup-11b.jar -destdir src srcjflexcup/toy.cup
//C:\JFLEX\bin\jflex -d src srcjflexcup\toy.flex
//java -jar C:\CUP\java-cup-11b.jar -dump -destdir src srcjflexcup/toy.cup 2>dump.txt
public class TesterParser {

        public static void main(String[] args) throws Exception {
            FileReader inFile = new FileReader(args[0]);
            Yylex test = new Yylex(inFile);
            parser p = new parser(test);
            p.parse();
            /*AnalizzatoreSemantico analizzatoreSemantico = new AnalizzatoreSemantico(p.rootSymTable, p.albero);
            analizzatoreSemantico.checkDeclaration();
            analizzatoreSemantico.checkType();
            Visitor visitor = new Visitor();
            visitor.saveCFile(p.albero);*/
        }


}
