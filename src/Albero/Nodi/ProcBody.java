package Albero.Nodi;

import TabSym.Symtable;

import java.util.ArrayList;
import java.util.Collections;

public class ProcBody {
    private ArrayList<VarDecl> VarDeclOpList;
    private ArrayList<Stat> statList;
    private ReturnExpr returnExpr;

    public ReturnExpr getReturnExpr() {
        return returnExpr;
    }

    public void setReturnExpr(ReturnExpr returnExpr) {
        this.returnExpr = returnExpr;
    }

    public ProcBody(ArrayList<VarDecl> varDeclOpList, ArrayList<Stat> statList, ReturnExpr returnExpr) {
        this.VarDeclOpList = varDeclOpList;
        this.statList = statList;
        this.returnExpr = returnExpr;
    }

    public ProcBody() {

    }

    public ArrayList<VarDecl> getVarDeclOpList() {
        return VarDeclOpList;
    }

    public void setVarDeclOpList(ArrayList<VarDecl> varDeclOpList) {
        VarDeclOpList = varDeclOpList;
    }

    public ArrayList<Stat> getStatList() {
        return statList;
    }

    public void setStatList(ArrayList<Stat> statList) {
        this.statList = statList;
    }

    public String toString() {
        String temp = "";
        if (VarDeclOpList.size() != 0) {

            for (int i = 0; i < VarDeclOpList.size(); i++) {
                temp = temp + "<VarDeclOp>";
                temp = temp + VarDeclOpList.get(i).toString();
                temp = temp + "</VarDeclOp>";
            }

        }
        if (statList != null) {
            temp = temp + "<StatList>";
            for (int i = 0; i < statList.size(); i++) {
                temp = temp + statList.get(i);
            }
            temp = temp + "</StatList>";
        }
        temp = temp + returnExpr;
        return temp;
    }

    public void checkIdDeclaration(Symtable symtable) {
        for (VarDecl varDecl : VarDeclOpList) {
            varDecl.checkIdDeclaration(symtable);
        }

        if (statList != null)
            for (Stat stat : statList) {
                stat.checkIdDeclaration(symtable);
            }
    }

    public void checkTypes(Symtable symtable, ArrayList<String> returnTypes, String nomeFunzione) {

        if(VarDeclOpList != null)
            for(VarDecl v : VarDeclOpList){
                v.checkType(symtable);
            }

        checkReturnTypes(symtable, returnTypes, nomeFunzione);

        if (statList != null)
            for (Stat stat : statList) {
                stat.checkType(symtable);
            }
    }

    private void checkReturnTypes(Symtable symtable, ArrayList<String> returnTypes, String nomeFunzione) {
        ArrayList<Expr> exprArrayList = returnExpr.getExprList();
        ArrayList<String> exprListResults = new ArrayList<>();

        for (Expr e : exprArrayList) {
            exprListResults.add(e.checkType(symtable));
        }


        //controllo void
        if (controlloVoid(returnTypes, exprListResults))
            return;

        if (!exprListResults.equals(returnTypes))
            throw new RuntimeException("I tipi di ritorno non corrispondono: " + nomeFunzione);
    }

    public boolean controlloVoid(ArrayList<String> returnTypes, ArrayList<String> exprListResults) {

        if (returnTypes.size() != 1 && returnTypes.contains("void"))
            return false;

        return returnTypes.get(0).equals("void") && exprListResults.get(0).equals("Null");
    }


    public String toCLang(ArrayList<String> returnVariables, Symtable symtable, Boolean main) {
        StringBuilder result = new StringBuilder();
        Collections.reverse(VarDeclOpList);

        for (VarDecl var : VarDeclOpList) {
            result.append(var.toCLang());
        }

        if (statList != null) {
            Collections.reverse(statList);
            for (Stat stat : statList) {
                result.append(stat.toCLang(symtable) + "\n");
            }
        }

        result.append(returnExpr.toCLang(returnVariables, main)+ "\n");

        return result.toString();
    }
}
