package Albero.Nodi;

import TabSym.Simbolo;
import TabSym.Symtable;
import TabSym.Variabile;

import java.util.ArrayList;

public class VarDecl {
    private String type;

    private ArrayList<Id> identifierList;

    public VarDecl(String type, ArrayList<Id> identifierList) {
        this.type = type;
        this.identifierList = identifierList;
    }

    public String toString() {
        String result = "";
        result = result + type;
        String temp = "";
        for (int i = 0; i < identifierList.size(); i++) {
            temp = temp + identifierList.get(i);
        }
        result = result + temp;
        return result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Id> getIdentifierList() {
        return identifierList;
    }

    public void setIdentifierList(ArrayList<Id> identifierList) {
        this.identifierList = identifierList;
    }

    public ArrayList<Simbolo> toVariables() {
        ArrayList<Simbolo> result = new ArrayList<>();

        for (Id id : identifierList) {
            if (!(id instanceof AssignId)) {
                Variabile tmp = new Variabile(id.getName(), type);
                result.add(tmp);
            } else {
                AssignId assignId = (AssignId) id;
                Variabile tmp = new Variabile(assignId.getId().getName(), type);
                result.add(tmp);
            }
        }

        return result;
    }

    public void checkIdDeclaration(Symtable symtable) {

        ArrayList<Id> idArrayList = new ArrayList<>();

        for (Id id : identifierList) {
            if (id instanceof AssignId) {
                Expr e = ((AssignId) id).getExpr();
                e.checkIdDeclaration(symtable);
                idArrayList.addAll(e.getAllId());
            }
        }

        symtable.checkIdDeclaration(idArrayList);
    }

    public void checkType(Symtable symtable) {
        for (Id id : identifierList) {

            if (id instanceof AssignId) {
                ((AssignId) id).checkType(type, symtable);

            }
        }

    }

    public String toCLang() {
        StringBuilder result = new StringBuilder();

        if (type.equals("string"))
            result.append("char");
        else {
            if (type.equals("bool"))
                result.append("int ");
            else
                result.append(type + " ");
        }

        for (Id id : identifierList) {
            if(type.equals("string")){
                result.append(" *");
            }
            if (id instanceof AssignId) {
                result.append(((AssignId) id).toCLang() + ",");
            } else {
                result.append(id.getName() + ",");
            }
        }

        int lastIndexOfComma = result.lastIndexOf(",");
        result.setCharAt(lastIndexOfComma, ';');
        result.append("\n");

        return result.toString();
    }
}
