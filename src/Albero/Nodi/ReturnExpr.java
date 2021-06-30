package Albero.Nodi;

import Albero.Constant;

import java.util.ArrayList;
import java.util.Collections;

public class ReturnExpr {

    private ArrayList<Expr> exprList;

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    public void setExprList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    public ReturnExpr() {

    }

    public ReturnExpr(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    public String toString() {
        String temp = "";
        temp = temp + "<ReturnExpr>";
        for (int i = 0; i < exprList.size(); i++) {
            temp = temp + exprList.get(i);
        }
        temp = temp + "</ReturnExpr>";
        return temp;
    }

    public String toCLang(ArrayList<String> returnVariables, Boolean main) {
        StringBuilder result = new StringBuilder();

        if (main) {
            result.append("return 1;");
            return result.toString();
        }

        Collections.reverse(exprList);

        int i = 0;

        if (returnVariables.size() != 0) {
            for (String s : returnVariables) {
                result.append("if( " + s + " != NULL ){\n");
                result.append("*" + s + " = " + exprList.get(i).toCLang() + ";\n");
                if (result.lastIndexOf(";;") != -1) {
                    result.deleteCharAt(result.lastIndexOf(";"));
                }
                result.append("}\n");
                i++;
            }
            result.append("return;");

        } else {
            String exprToCLang = exprList.get(0).toCLang();
            if (exprToCLang.equals("null")) {
                exprToCLang = "";
            }else if (exprToCLang.equals("true")) {
                exprToCLang = "1";
            }else if(exprToCLang.equals("false")){
                exprToCLang = "0";
            }


            result.append("return " + exprToCLang + ";");
        }


        return result.toString();
    }
}
