package Albero.Nodi;

import Albero.Constant;
import TabSym.Symtable;

import java.util.ArrayList;
import java.util.Collections;

public class WriteStat extends Stat {

    private ArrayList<Expr> exprList;

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    public WriteStat(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    public void setExprList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    public WriteStat() {

    }

    public String toString() {
        String temp = "";
        if (exprList.size() != 0) {
            temp = temp + "<WriteStat>";
            for (int i = 0; i < exprList.size(); i++) {
                temp = temp + exprList.get(i);
            }
            temp = temp + "</WriteStat>";
        }
        return temp;
    }

    @Override
    public void checkIdDeclaration(Symtable symtable) {
        ArrayList<Id> list = new ArrayList<>();

       for(Expr e : exprList){
           e.checkIdDeclaration(symtable);
           list.addAll(e.getAllId());
       }

        symtable.checkIdDeclaration(list);
    }

    @Override
    public void checkType(Symtable symtable) {
        for(Expr e : exprList){
            if(e.checkType(symtable).equals("error")){
                throw new RuntimeException("I tipi non corrispondono: WRITESTAT");
            }
        }
    }

    @Override
    public String toCLang(Symtable symtable) {
        Collections.reverse(exprList);
        String tipo;
        ArrayList<String>temp= new ArrayList<>();
        StringBuilder result= new StringBuilder();
        result.append("printf(");
        result.append("\"");
        for(Expr expr : exprList){
            tipo = expr.checkType(symtable);
            if(expr.toCLang().contains("\"")){
                result.append("%s");
                temp.add(expr.toCLang());
            }
               else if(tipo.equalsIgnoreCase("float")){
                    result.append("%f");
                if(expr.getOpType().equalsIgnoreCase(Constant.CALL_PROC_OP)){
                    StringBuilder procBuild= new StringBuilder();
                    CallProc proc= (CallProc) expr.getStat();
                    procBuild.append(proc.toCLang(symtable));
                    procBuild.deleteCharAt(procBuild.lastIndexOf(";"));
                    temp.add(procBuild.toString());
                }else {
                    temp.add(expr.getIdentifier().getName());
                }
                }
            else if(tipo.equalsIgnoreCase("bool")){
                result.append("%d");
                if(expr.getOpType().equalsIgnoreCase(Constant.CALL_PROC_OP)){
                    StringBuilder procBuild= new StringBuilder();
                    CallProc proc= (CallProc) expr.getStat();
                    procBuild.append(proc.toCLang(symtable));
                    procBuild.deleteCharAt(procBuild.lastIndexOf(";"));
                    temp.add(procBuild.toString());
                }else {
                    temp.add(expr.getIdentifier().getName());
                }
            }
               else if(tipo.equalsIgnoreCase("int")){
                    result.append("%d");
                    if(expr.getOpType().equalsIgnoreCase(Constant.CALL_PROC_OP)){
                        StringBuilder procBuild= new StringBuilder();
                        CallProc proc= (CallProc) expr.getStat();
                        procBuild.append(proc.toCLang(symtable));
                        procBuild.deleteCharAt(procBuild.lastIndexOf(";"));
                        temp.add(procBuild.toString());
                    }else {
                        temp.add(expr.getIdentifier().getName());
                    }
                }
                else if(tipo.equalsIgnoreCase("string")){
                    result.append("%s");
                    temp.add(expr.getIdentifier().getName());
                }

            }

        result.append("\",");
        if (temp.size() == 1) {
            result.append(temp.get(0));
            result.append(")");
        } else {
            for (int i = 0; i < temp.size(); i++) {
                result.append(temp.get(i) + ",");
            }
            int lastIndexOfComma = result.lastIndexOf(",");
            result.setCharAt(lastIndexOfComma, ')');
        }
        result.append(";");
        return result.toString();
    }
}
