package Albero.Nodi;

import TabSym.Symtable;

import java.util.ArrayList;
import java.util.Collections;

public class WhileStat extends Stat {

    private ArrayList<Stat> statList;

    private Expr Expr;

    private ArrayList<Stat> statList2;

    public ArrayList<Stat> getStatList() {
        return statList;
    }

    public WhileStat(ArrayList<Stat> statList, Albero.Nodi.Expr expr, ArrayList<Stat> statList2) {
        this.statList = statList;
        Expr = expr;
        this.statList2 = statList2;
    }

    public void setStatList(ArrayList<Stat> statList) {
        this.statList = statList;
    }


    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr expr) {
        Expr = expr;
    }


    public ArrayList<Stat> getStatList2() {
        return statList2;
    }

    public void setStatList2(ArrayList<Stat> statList2) {
        this.statList2 = statList2;
    }


    public WhileStat() {

    }

    public String toString() {
        if (statList != null)
            Collections.reverse(statList);

        if (statList2 != null)
            Collections.reverse(statList2);

        String temp = "";
        temp = temp + "<WhileStat>";

        if (statList != null) {
            temp = temp + "<StatList>";
            for (int i = 0; i < statList.size(); i++) {
                temp = temp + statList.get(i);
            }
            temp = temp + "</StatList>";
        }

        temp = temp + Expr;

        if (statList2 != null) {
            temp = temp + "<StatList>";
            for (int i = 0; i < statList2.size(); i++) {
                temp = temp + statList2.get(i);
            }
            temp = temp + "</StatList>";
        }

        temp = temp + "</WhileStat>";

        return temp;
    }

    @Override
    public void checkIdDeclaration(Symtable symtable) {

        if (statList != null)
            for (Stat stat : statList) {
                stat.checkIdDeclaration(symtable);
            }

        Expr.checkIdDeclaration(symtable);
        ArrayList<Id> idArrayList = Expr.getAllId();
        symtable.checkIdDeclaration(idArrayList);

        if (statList2 != null)
            for (Stat stat : statList2) {
                stat.checkIdDeclaration(symtable);
            }
    }

    @Override
    public void checkType(Symtable symtable) {
        if (!Expr.checkType(symtable).equals("bool"))
            throw new RuntimeException("I tipi non corrispondono: WHILESTAT" + this.toError());

        if (statList != null)
            for (Stat stat : statList) {
                stat.checkType(symtable);
            }

        if (statList2 != null)
            for (Stat stat : statList2) {
                stat.checkType(symtable);
            }

    }

    public String toError() {
        return "WHILESTAT:" + Expr.toError();
    }

    public String toCLang(Symtable symtable) {
        StringBuilder result = new StringBuilder();

        if (statList != null)
            for (Stat stat : statList) {
                result.append( stat.toCLang(symtable));
            }
        result.append( " "+ "while(");
        result.append(Expr.toCLang()+")"+"{");
        if (statList2 != null)
            for (Stat stat : statList2) {
                result.append(stat.toCLang(symtable));
            }
        result.append("}");

        return result.toString();
    }

}
