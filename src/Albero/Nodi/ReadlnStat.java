package Albero.Nodi;

import TabSym.Symtable;

import java.util.ArrayList;

public class ReadlnStat extends Stat {
    private String tipo = " ";

    private ArrayList<Id> identifierList;

    public ArrayList<Id> getIdentifierList() {
        return identifierList;
    }

    public void setIdentifierList(ArrayList<Id> identifierList) {
        this.identifierList = identifierList;
    }

    public ReadlnStat(ArrayList<Id> identifierList) {
        this.identifierList = identifierList;
    }

    public ReadlnStat() {

    }

    public String toString() {
        String temp = "";
        temp = temp + "<ReadLn>";

        for (int i = 0; i < identifierList.size(); i++) {
            temp = temp + identifierList.get(i);
        }

        temp = temp + "</ReadLn>";

        return temp;
    }

    @Override
    public void checkIdDeclaration(Symtable symtable) {
        symtable.checkIdDeclaration(identifierList);
    }

    @Override
    public void checkType(Symtable symtable) {
        for (Id id : identifierList) {
            if (id instanceof AssignId) {
                throw new RuntimeException("Type mismatch READ");
            }
        }
    }

    @Override
    public String toCLang(Symtable symtable) {
        ArrayList<String> temp = new ArrayList<>();

        StringBuilder result = new StringBuilder();
        result.append("scanf(\"");
        for (int i = 0; i < identifierList.size(); i++) {
            tipo = symtable.getTipo(identifierList.get(i));
            if (tipo.equalsIgnoreCase("int")) {
                result.append("%d");
                temp.add(identifierList.get(i).getName());
            } else if (tipo.equalsIgnoreCase("bool")) {
                result.append("%d");
                temp.add(identifierList.get(i).getName());
            } else if (tipo.equalsIgnoreCase("String")) {
                result.append("%s");
                temp.add(identifierList.get(i).getName());
            } else if (tipo.equalsIgnoreCase("float")) {
                result.append("%f");
                temp.add(identifierList.get(i).getName());
            }
        }
        result.append("\",");
        if (temp.size() == 1) {
            result.append("&" + temp.get(0));
            result.append(")");
        } else {
            for (int i = 0; i < temp.size(); i++) {
                result.append("&" + temp.get(i) + ",");
            }
            int lastIndexOfComma = result.lastIndexOf(",");
            result.setCharAt(lastIndexOfComma, ')');
        }
        result.append(";");
        return result.toString();
    }

}
