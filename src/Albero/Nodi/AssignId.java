package Albero.Nodi;

import TabSym.Symtable;

public class AssignId extends Id {
    private Id id;
    private Expr expr;

    public AssignId(Id id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public String toString() {
        return "<AssignID>" + id + expr + "</AssignID>";
    }

    public void checkType(String type, Symtable symtable) {
        String typeExpr = expr.checkType(symtable);
        if (!type.equals(typeExpr)) {
            throw new RuntimeException("I tipi non corrispondono: " + expr.toError());
        }
    }

    public String toCLang() {
        StringBuilder result = new StringBuilder();
        String bool;

        //controllo bool
        if (expr.toCLang().equals("true"))
            bool = "1";
        else {
            if (expr.toCLang().equals("false"))
                bool = "0";
            else {
                StringBuilder callProc = new StringBuilder(expr.toCLang());
                int index = callProc.lastIndexOf(";");
                if (index != -1)
                    callProc.deleteCharAt(index);
                bool = callProc.toString();

            }
        }

        result.append(id.getName() + " = " + bool);

        return result.toString();
    }
}
