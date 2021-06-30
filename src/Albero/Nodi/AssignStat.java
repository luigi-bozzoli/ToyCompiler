package Albero.Nodi;

import Albero.Constant;
import TabSym.Symtable;

import java.util.ArrayList;
import java.util.Collections;

public class AssignStat extends Stat {

    private ArrayList<Id> identifierList;

    private ArrayList<Expr> exprList;

    public AssignStat(ArrayList<Id> identifierList, ArrayList<Expr> exprList) {
        this.identifierList = identifierList;
        this.exprList = exprList;
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    public void setExprList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }


    public AssignStat() {

    }

    public ArrayList<Id> getIdentifierList() {
        return identifierList;
    }

    public void setIdentifierList(ArrayList<Id> identifierList) {
        this.identifierList = identifierList;
    }

    public String toString() {
        String temp = "";
        temp = temp + "<AssignStat>";
        if (identifierList.size() != 0) {
            temp = temp + "<IdList>";
            for (int i = 0; i < identifierList.size(); i++) {
                temp = temp + identifierList.get(i);
            }
            temp = temp + "</IdList>";
        }
        if (exprList.size() != 0) {
            temp = temp + "<ExprList>";
            for (int i = 0; i < exprList.size(); i++) {
                temp = temp + exprList.get(i);
            }
            temp = temp + "</ExprList>";
        }
        temp = temp + "</AssignStat>";
        return temp;
    }

    @Override
    public void checkIdDeclaration(Symtable symtable) {
        symtable.checkIdDeclaration(identifierList);

        for (Expr e : exprList) {
            e.checkIdDeclaration(symtable);
            symtable.checkIdDeclaration(e.getAllId());
        }

    }

    @Override
    public void checkType(Symtable symtable) {
        ArrayList<String> tipi = symtable.getTipi(identifierList);
        ArrayList<String> tipiExprList = new ArrayList<>();

        if (exprList != null)
            for (Expr e : exprList) {
                if (e.getOpType().equals(Constant.CALL_PROC_OP))
                    tipiExprList.addAll(e.checkTypeCallProc(symtable));
                else
                    tipiExprList.add(e.checkType(symtable));
            }

        //addall inserisce in testa
        Collections.reverse(tipiExprList);


        if (!tipi.equals(tipiExprList))
            throw new RuntimeException("I tipi non corrispondono: " + identifierList + this.toError());

    }

    @Override
    public String toCLang(Symtable symtable) {
        StringBuilder result = new StringBuilder();

        if (exprList != null)
            for (Expr e : exprList) {
                if (e.getOpType().equals(Constant.CALL_PROC_OP)) {
                    CallProc callProc = (CallProc) e.getStat();
                    ArrayList<String> returnValues = symtable.getReturnValues(callProc.getIdentifierString());

                    if (returnValues.size() == 1) {
                        Id id = identifierList.remove(0);
                        String bool;
                        //controllo bool
                        if (e.toCLang().equals("true"))
                            bool = "1";
                        else {
                            if (e.toCLang().equals("false"))
                                bool = "0";
                            else
                                bool = e.toCLang();
                        }

                        result.append(id.getName() + " = " + bool + ";");
                    } else {
                        ArrayList<String> inputPointers = new ArrayList<>();

                        for (String s : returnValues) {
                            Id id;
                            if (identifierList.size() != 0) {
                                id = identifierList.remove(0);
                                inputPointers.add(id.getName());
                            }
                        }
                        callProc.setInputPointers(inputPointers);
                        result.append(callProc.toCLang(symtable));
                    }
                } else {
                    Id id = identifierList.remove(0);

                    String bool;
                    //controllo bool
                    if (e.toCLang().equals("true"))
                        bool = "1";
                    else {
                        if (e.toCLang().equals("false"))
                            bool = "0";
                        else
                            bool = e.toCLang();
                    }

                    result.append(id.getName() + " = " + bool + ";");
                }
            }

        int indexSemi = result.lastIndexOf(";;");
        if (indexSemi != -1) {
                result.deleteCharAt(result.lastIndexOf(";"));
        }
        return result.toString();
    }

    public String toError() {
        return "ASSIGNSTAT: " + exprList.get(0).toError();
    }
}
