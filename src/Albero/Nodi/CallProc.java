package Albero.Nodi;

import TabSym.Symtable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class CallProc extends Stat {

    private Id identifier;

    private ArrayList<Expr> exprList;

    //necessario per la traduzione
    private ArrayList<String> inputPointers;

    public Id getIdentifierString() {
        return identifier;
    }

    public void setIdentifierString(Id identifierString) {
        this.identifier = identifierString;
    }

    public CallProc(Id identifierString, ArrayList<Expr> exprList) {
        this.identifier = identifierString;
        if (exprList != null)
            Collections.reverse(exprList);
        this.exprList = exprList;
        this.inputPointers = new ArrayList<>();
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    public void setExprList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    public ArrayList<String> getInputPointers() {
        return inputPointers;
    }

    public void setInputPointers(ArrayList<String> inputPointers) {
        this.inputPointers = inputPointers;
    }

    public String toString() {
        String temp = "";
        temp = temp + "<CallProc>" + identifier;

        if (exprList != null)
            if (exprList.size() != 0) {
                temp += "<ParamOp>";
                for (int i = 0; i < exprList.size(); i++) {
                    temp = temp + exprList.get(i);
                }
                temp += "</ParamOp>";
            }
        temp = temp + "</CallProc>";
        return temp;
    }

    @Override
    public void checkIdDeclaration(Symtable symtable) {
        ArrayList<Id> tmp = new ArrayList<>();
        tmp.add(identifier);
        try {
            symtable.checkIdDeclaration(tmp);
        } catch (RuntimeException r) {
            throw new RuntimeException("Metodo non dichiarato:" + identifier.getName());
        }
        ;

        if (exprList != null)
            for (Expr e : exprList) {
                e.checkIdDeclaration(symtable);
                symtable.checkIdDeclaration(e.getAllId());
            }
    }

    @Override
    public void checkType(Symtable symtable) {
        ArrayList<String> paramListType = symtable.getParamListType(identifier);
        ArrayList<String> exprTypeList = new ArrayList<>();

        if (exprList != null)
            for (Expr e : exprList) {
                exprTypeList.add(e.checkType(symtable));
            }


        if (!paramListType.equals(exprTypeList))
            throw new RuntimeException("I tipi non corrispondono " + identifier);
    }

    @Override
    public String toCLang(Symtable symtable) {
        StringBuilder result = new StringBuilder();

        result.append(identifier.getName() + "(");

        //input
        if (exprList != null) {
            for (Expr e : exprList) {
                String eToClang = e.toCLang();
                if (eToClang.equals("true"))
                    eToClang = "1";
                else if (eToClang.equals("false"))
                    eToClang = "0";

                result.append(eToClang + ", ");
            }
        }

        if (symtable != null) {
            int returnValuesSize = symtable.getReturnValues(identifier).size();
            if (returnValuesSize > 1 && inputPointers.isEmpty()) {
                for (int i = 0; i < returnValuesSize; i++) {
                    result.append("NULL, ");
                }

            }

        }

        Collections.reverse(inputPointers);
        for (String p : inputPointers) {
            result.append("&" + p + ", ");
        }

        int lastIndex = result.lastIndexOf(",");
        if (lastIndex != -1)
            result.deleteCharAt(lastIndex);

        result.deleteCharAt(result.lastIndexOf(" "));

        result.append(");");
        return result.toString();
    }
}
