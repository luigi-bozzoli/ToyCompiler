package Albero.Nodi;

import TabSym.Symtable;

import java.util.ArrayList;

public class Elif extends Stat {

    private Expr expr;

    private ArrayList<Stat> staList;

    public Elif() {
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public Elif(Expr expr, ArrayList<Stat> staList) {
        this.expr = expr;
        this.staList = staList;
    }


    public ArrayList<Stat> getStaList() {
        return staList;
    }

    public void setStaList(ArrayList<Stat> staList) {
        this.staList = staList;
    }

    public String toString() {
        String temp = "";
        temp = temp + "<Elif>" + expr;
        if (staList.size() != 0) {
            temp = temp + "<StatList>";
            for (int i = 0; i < staList.size(); i++) {
                temp = temp + staList.get(i);
            }
            temp = temp + "</StatList>";
        }
        temp = temp + "</Elif>";
        return temp;

    }

    @Override
    public void checkIdDeclaration(Symtable symtable) {

        expr.checkIdDeclaration(symtable);
        symtable.checkIdDeclaration(expr.getAllId());

        for (Stat s : staList) {
            s.checkIdDeclaration(symtable);
        }

    }

    @Override
    public void checkType(Symtable symtable) {
        if (!expr.checkType(symtable).equals("bool"))
            throw new RuntimeException("I tipi non corrispondono: ELIF"+this.toError());

        if (staList != null)
            for (Stat s : staList) {
                s.checkType(symtable);
            }
    }

    @Override
    public String toCLang(Symtable symtable) {
        StringBuilder result=new StringBuilder();
        result.append(" "+"else if"+"(");
        result.append(expr.toCLang()+")"+"{");
        for(Stat stat: staList){
            result.append(stat.toCLang(symtable));
        }
        result.append("}");
        return result.toString();
    }

    public String toError(){
        return "ELIF:"+expr.toError();
    }
}
