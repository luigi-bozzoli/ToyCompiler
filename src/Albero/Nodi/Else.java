package Albero.Nodi;

import TabSym.Symtable;

import java.util.ArrayList;
import java.util.Collections;

public class Else extends Stat {

    private ArrayList<Stat> statList;

    public Else() {
    }

    public Else(ArrayList<Stat> statList) {
        this.statList = statList;
    }

    public ArrayList<Stat> getStatList() {
        return statList;
    }

    public void setStatList(ArrayList<Stat> statList) {
        this.statList = statList;
    }


    public String toString() {
        String temp = "";

        if (statList != null) {
            temp = temp + "<Else>";
            temp = temp + "<StatList>";
            for (int i = 0; i < statList.size(); i++) {
                temp = temp + statList.get(i);
            }
            temp = temp + "</StatList>";
            temp = temp + "</Else>";
        }

        return temp;
    }

    @Override
    public void checkIdDeclaration(Symtable symtable) {
        if (statList != null)
            for (Stat s : statList) {
                s.checkIdDeclaration(symtable);
            }
    }

    @Override
    public void checkType(Symtable symtable) {
        if (statList != null)
            for (Stat s : statList)
                s.checkType(symtable);
    }

    @Override
    public String toCLang(Symtable symtable) {
        StringBuilder result= new StringBuilder();
        if(statList!=null) {
            Collections.reverse(statList);
            for (Stat stat : statList) {
                result.append(stat.toCLang(symtable));
            }
        }
        return result.toString();
    }
}
