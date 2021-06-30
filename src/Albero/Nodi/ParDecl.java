package Albero.Nodi;

import TabSym.Simbolo;
import TabSym.Variabile;

import java.util.ArrayList;

public class ParDecl {
    private String type;

    private ArrayList<Id> identifierList;


    public ParDecl() {

    }

    public ParDecl(String type, ArrayList<Id> identifierList) {
        this.type = type;
        this.identifierList = identifierList;
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

    public ArrayList<String> getIdListNames() {
        ArrayList<String> result = new ArrayList<>();
        for (Id id : identifierList) {
            result.add(id.getName());
        }
        return result;
    }

    public ArrayList<Variabile> toVariabili() {
        ArrayList<Variabile> simbolos = new ArrayList<>();

        for (Id id : identifierList) {
            Variabile s = new Variabile(id.getName(), type);
            simbolos.add(s);
        }

        return simbolos;
    }

    public ArrayList<Simbolo> toVariables() {
        ArrayList<Simbolo> simbolos = new ArrayList<>();

        for (Id id : identifierList) {
            Simbolo s = new Variabile(id.getName(), type);
            simbolos.add(s);
        }

        return simbolos;
    }

    //int num1, int num2 ....
    public String toCLang() {
        StringBuilder result = new StringBuilder();
        String tipo = "";

        if (type.equals("string"))
            tipo = "char* ";
        else {
            if (type.equals("bool"))
                tipo = "int ";
            else
                tipo = type;
        }
        for (Id id : identifierList) {
            result.append(tipo + " " + id.getName() + ", ");
        }

        int lastIndexComma = result.lastIndexOf(",");
        result.deleteCharAt(lastIndexComma);

        return result.toString();
    }
}
