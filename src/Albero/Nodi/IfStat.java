package Albero.Nodi;

import TabSym.Symtable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class IfStat extends Stat {

    private Else el;

    private Expr expr;

    private ArrayList<Elif> elifList;

    private ArrayList<Stat> statList;

    public IfStat(Else el, Expr expr, ArrayList<Stat> statList, ArrayList<Elif> elifList) {
        this.el = el;
        this.expr = expr;
        this.statList = statList;
        this.elifList = elifList;
    }

    public IfStat() {

    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }


    public ArrayList<Stat> getStatList() {
        return statList;
    }

    public void setStatList(ArrayList<Stat> statList) {
        this.statList = statList;
    }


    public ArrayList<Elif> getElifList() {
        return elifList;
    }

    public void setElifList(ArrayList<Elif> elifList) {
        this.elifList = elifList;
    }


    public Else getEl() {
        return el;
    }

    public void setEl(Else el) {
        this.el = el;
    }


    public String toString() {
        String temp = "";
        temp = temp + "<IfStat>";
        temp = temp + el + expr;
        if (statList.size() != 0) {
            temp = temp + "<StatList>";
            for (int i = 0; i < statList.size(); i++) {
                temp = temp + statList.get(i);
            }
            temp = temp + "</StatList>";
        }
        if (elifList.size() != 0) {
            temp = temp + "<ElifList>";
            for (int i = 0; i < elifList.size(); i++) {
                temp = temp + elifList.get(i);
            }
            temp = temp + "</ElifList>";
        }
        temp = temp + "</IfStat>";
        return temp;
    }

    @Override
    public void checkIdDeclaration(Symtable symtable) {

        expr.checkIdDeclaration(symtable);
        ArrayList<Id> idArrayList = expr.getAllId();
        symtable.checkIdDeclaration(idArrayList);
        if (el != null)
            el.checkIdDeclaration(symtable);

        for (Elif elif : elifList) {
            elif.checkIdDeclaration(symtable);
        }

        for (Stat stat : statList) {
            stat.checkIdDeclaration(symtable);
        }

    }

    @Override
    public void checkType(Symtable symtable) {
        if (!expr.checkType(symtable).equals("bool"))
            throw new RuntimeException("I tipi non corrispondono: " + this.toError());
        if (el != null)
            el.checkType(symtable);

        for (Elif elif : elifList)
            elif.checkType(symtable);

        for (Stat s : statList)
            s.checkType(symtable);
    }

    public String toError() {
        return "IFSTAT:" + expr.toError();
    }

    public String toCLang(Symtable symtable) {
        Collections.reverse(statList);
        Collections.reverse(elifList);
        StringBuilder result = new StringBuilder();
        result.append("if" + " ");
        result.append("(" + expr.toCLang() + ")" + "{");
        for (Stat stat : statList) {
            result.append(stat.toCLang(symtable));
        }
        result.append("}");
        if (elifList.size() != 0) {
            for (Elif elif : elifList) {
                result.append(elif.toCLang(symtable));
            } if(el!=null){
                result.append("else"+"{" + el.toCLang(symtable)+"}");
            }
        } else if (el != null) {
            result.append("else"+"{" + el.toCLang(symtable)+"}");
        }
        return result.toString();
    }
}
