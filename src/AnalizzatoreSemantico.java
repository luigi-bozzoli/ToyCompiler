import Albero.Nodi.Proc;
import Albero.Nodi.Program;
import Albero.Nodi.VarDecl;
import TabSym.Symtable;
import java.util.ArrayList;

public class AnalizzatoreSemantico {
    private Symtable symtable;
    private Program root;

    public AnalizzatoreSemantico(Symtable symtable, Program root) {
        this.symtable = symtable;
        this.root = root;
    }

    public void checkType(){

        checkVarDeclType();

        for (Proc proc: root.getProcOpList()) {
            proc.checkTypes();
        }
    }

    private void checkVarDeclType(){

        ArrayList<VarDecl> varDecls = root.getVarDeclList();

        for (VarDecl var : varDecls) {
            var.checkType(symtable);
        }
    }


    public void checkDeclaration(){
        symtable.checkMain();

        //controllo variabili radice
       checkVarDeclDeclartion();

        for(Proc proc : root.getProcOpList()){
            proc.checkIdDeclaration();
        }

    }

    private void checkVarDeclDeclartion(){

        ArrayList<VarDecl> varDecls = root.getVarDeclList();

        for (VarDecl var : varDecls) {
            var.checkIdDeclaration(symtable);
        }

    }

    public Symtable getSymtable() {
        return symtable;
    }

    public void setSymtable(Symtable symtable) {
        this.symtable = symtable;
    }

    public Program getRoot() {
        return root;
    }

    public void setRoot(Program root) {
        this.root = root;
    }

}
