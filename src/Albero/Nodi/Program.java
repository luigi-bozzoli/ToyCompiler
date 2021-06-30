package Albero.Nodi;

import java.util.ArrayList;

public class Program {

    private ArrayList<VarDecl> varDeclList;
    private ArrayList<Proc> procOpList;


    public ArrayList<VarDecl> getVarDeclList() {
        return varDeclList;
    }

    public void setVarDeclList(ArrayList<VarDecl> varDeclList) {
        this.varDeclList = varDeclList;
    }

    public ArrayList<Proc> getProcOpList() {
        return procOpList;
    }

    public void setProcOpList(ArrayList<Proc> procList) {
        procOpList = procList;
    }

    public Program(ArrayList<VarDecl> varDeclList, ArrayList<Proc> procOpList) {

        this.varDeclList = varDeclList;
        this.procOpList = procOpList;
    }

    public Program() {
    }

    public String toCLang() {
        StringBuilder result = new StringBuilder();
        result.append("#include <stdio.h>\n");

        for (VarDecl var : varDeclList) {
            result.append(var.toCLang() + "\n");
        }

        for (Proc proc : procOpList) {
            result.append(proc.functionSignature() + "\n");
        }

        for (Proc proc : procOpList) {
            result.append(proc.toCLang() + "\n");
        }

        return result.toString();
    }
}
