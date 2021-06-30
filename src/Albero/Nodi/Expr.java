package Albero.Nodi;

import Albero.Constant;
import TabSym.Symtable;
import java.util.ArrayList;


public class Expr extends Id {

    private String OpType;

    private Stat stat;

    private Expr expr1, expr2;

    private Id identifier;

    public String getOpType() {
        return OpType;
    }

    public void setOpType(String opType) {
        OpType = opType;
    }

    public Id getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Id identifier) {
        this.identifier = identifier;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public Expr getExpr1() {
        return expr1;
    }

    public void setExpr1(Expr expr1) {
        this.expr1 = expr1;
    }

    public Expr getExpr2() {
        return expr2;
    }

    public void setExpr2(Expr expr2) {
        this.expr2 = expr2;
    }

    public void checkIdDeclaration(Symtable symtable) {
        if (stat != null)
            stat.checkIdDeclaration(symtable);
    }

    public ArrayList<Id> getAllId() {

        ArrayList<Id> ids = new ArrayList<>();

        if (OpType.equals(Constant.BOOL_CONST) ||
                OpType.equals(Constant.NULL_CONST) ||
                OpType.equals(Constant.INT_CONST) ||
                OpType.equals(Constant.FLOAT_CONST) ||
                OpType.equals(Constant.STRING_CONST))
            return ids;

        if (identifier != null)
            ids.add(identifier);

        if (expr1 != null)
            ids.addAll(expr1.getAllId());

        if (expr2 != null) {
            ids.addAll(expr2.getAllId());
        }
        return ids;
    }

    public Expr() {
    }

    public Expr(Stat stat) {
        OpType = "";
        this.expr1 = null;
        this.expr2 = null;
        this.identifier = null;
        this.stat = stat;
    }

    public Expr(String opType, Expr expr1, Expr expr2, Id identifier, Stat stat) {
        OpType = opType;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.identifier = identifier;
        this.stat = stat;
    }

    public Expr(String opType, Expr expr1, Expr expr2, Id identifier) {
        OpType = opType;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.identifier = identifier;
        stat = null;
    }

    public ArrayList<String> checkTypeCallProc(Symtable symtable){
        CallProc callProc = (CallProc) stat;
        ArrayList<String> returnValues = symtable.getReturnValues(callProc.getIdentifierString());
        //controlla input funzione
        callProc.checkType(symtable);

        return returnValues;
    }

    public String checkType(Symtable symtable) {
        String type1 = "";
        String type2 = "";

        switch (OpType) {
            case Constant.NULL_CONST:
                return "Null";

            case Constant.BOOL_CONST:
                return "bool";

            case Constant.INT_CONST:
                return "int";

            case Constant.FLOAT_CONST:
                return "float";

            case Constant.STRING_CONST:
                return "string";

            case Constant.ID_CONST:
                return symtable.getTipo(identifier);

            case Constant.SUM_OP:

                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("int") && type2.equals("int")) {
                    return "int";
                } else if (type1.equals("int") && type2.equals("float")) {
                    return "float";
                } else if (type1.equals("float") && type2.equals("int")) {
                    return "float";
                } else if (type1.equals("float") && type2.equals("float")) {
                    return "float";
                } else
                    return "error";

            case Constant.DIFF_OP:

                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("int") && type2.equals("int")) {
                    return "int";
                } else if (type1.equals("int") && type2.equals("float")) {
                    return "float";
                } else if (type1.equals("float") && type2.equals("int")) {
                    return "float";
                } else if (type1.equals("float") && type2.equals("float")) {
                    return "float";
                } else
                    return "error";

            case Constant.MUL_OP:
                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("int") && type2.equals("int")) {
                    return "int";
                } else if (type1.equals("int") && type2.equals("float")) {
                    return "float";
                } else if (type1.equals("float") && type2.equals("int")) {
                    return "float";
                } else if (type1.equals("float") && type2.equals("float")) {
                    return "float";
                } else
                    return "error";

            case Constant.DIV_OP:

                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("int") && type2.equals("int")) {
                    return "int";
                } else if (type1.equals("int") && type2.equals("float")) {
                    return "float";
                } else if (type1.equals("float") && type2.equals("int")) {
                    return "float";
                } else if (type1.equals("float") && type2.equals("float")) {
                    return "float";
                } else
                    return "error";

            case Constant.AND_OP:
                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("bool") && type2.equals("bool")) {
                    return "bool";
                } else
                    return "error";

            case Constant.OR_OP:
                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("bool") && type2.equals("bool")) {
                    return "bool";
                } else
                    return "error";

            case Constant.GT_OP:
                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("int") && type2.equals("int")) {
                    return "bool";
                } else if (type1.equals("int") && type2.equals("float")) {
                    return "bool";
                } else if (type1.equals("float") && type2.equals("int")) {
                    return "bool";
                } else if (type1.equals("float") && type2.equals("float")) {
                    return "bool";
                } else
                    return "error";

            case Constant.GE_OP:

                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("int") && type2.equals("int")) {
                    return "bool";
                } else if (type1.equals("int") && type2.equals("float")) {
                    return "bool";
                } else if (type1.equals("float") && type2.equals("int")) {
                    return "bool";
                } else if (type1.equals("float") && type2.equals("float")) {
                    return "bool";
                } else
                    return "error";

            case Constant.LT_OP:

                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("int") && type2.equals("int")) {
                    return "bool";
                } else if (type1.equals("int") && type2.equals("float")) {
                    return "bool";
                } else if (type1.equals("float") && type2.equals("int")) {
                    return "bool";
                } else if (type1.equals("float") && type2.equals("float")) {
                    return "bool";
                } else
                    return "error";

            case Constant.LE_OP:

                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("int") && type2.equals("int")) {
                    return "bool";
                } else if (type1.equals("int") && type2.equals("float")) {
                    return "bool";
                } else if (type1.equals("float") && type2.equals("int")) {
                    return "bool";
                } else if (type1.equals("float") && type2.equals("float")) {
                    return "bool";
                } else
                    return "error";

            case Constant.EQ_OP:

                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("bool") && type2.equals("bool")) {
                    return "bool";
                } else if (type1.equals("int") && type2.equals("int")) {
                    return "bool";
                } else if (type1.equals("float") && type2.equals("float"))
                    return "bool";
                else if (type1.equals("string") && type2.equals("string"))
                    return "bool";
                else
                    return "error";

            case Constant.NE_OP:

                type1 = expr1.checkType(symtable);
                type2 = expr2.checkType(symtable);

                if (type1.equals("bool") && type2.equals("bool")) {
                    return "bool";
                } else if (type1.equals("int") && type2.equals("int")) {
                    return "bool";
                } else if (type1.equals("float") && type2.equals("float"))
                    return "bool";
                else if (type1.equals("string") && type2.equals("string"))
                    return "bool";
                else
                    return "error";

            case Constant.MINUS_OP:

                type1 = expr1.checkType(symtable);

                if (type1.equals("int"))
                    return "int";
                else if (type1.equals("float"))
                    return "float";
                else
                    return "error";

            case Constant.NOT_OP:

                type1 = expr1.checkType(symtable);

                if (type1.equals("bool"))
                    return "bool";
                else
                    return "error";

            case Constant.CALL_PROC_OP:
                ArrayList<String> returnValues = checkTypeCallProc(symtable);

                if (returnValues.size() != 1) {
                    return "error";
                } else {
                    return returnValues.get(0);
                }
            default:
                return "error";
        }
    }

    public String toString() {
        String temp = "";
        switch (OpType) {
            case Constant.NULL_CONST:
                temp = temp + "Null";
                break;
            case Constant.BOOL_CONST:
                temp = temp + "(Bool_Const, " + identifier + ")";
                break;
            case Constant.INT_CONST:
                temp = temp + "(Int_Const," + identifier + ")";
                break;
            case Constant.FLOAT_CONST:
                temp = temp + "(Float_Const," + identifier + ")";
                break;
            case Constant.STRING_CONST:
                temp = temp + "(String_Const," + identifier + ")";
                break;
            case Constant.SUM_OP:
                temp = temp + "<Sum_Op>," + expr1.toString() + expr2.toString() + "</Sum_Op>";
                break;
            case Constant.ID_CONST:
                temp = temp + identifier;
                break;
            case Constant.DIFF_OP:
                temp = temp + "<Diff_Op>" + expr1.toString() + expr2.toString() + "</Diff_Op>";
                break;
            case Constant.MUL_OP:
                temp = temp + "<Mul_Op>" + expr1.toString() + expr2.toString() + "</Mul_Op>";
                break;
            case Constant.DIV_OP:
                temp = temp + "<Div_Op>" + expr1.toString() + expr2.toString() + "</Div_Op>";
                break;
            case Constant.AND_OP:
                temp = temp + "<And_Op>" + expr1.toString() + expr2.toString() + "</And_Op>";
                break;
            case Constant.OR_OP:
                temp = temp + "<Or_Op>" + expr1.toString() + expr2.toString() + "</Or_Op>";
                break;
            case Constant.GT_OP:
                temp = temp + "<Gt_Op>" + expr1.toString() + expr2.toString() + "</Gt_Op>";
                break;
            case Constant.GE_OP:
                temp = temp + "<Ge_Op>" + expr1.toString() + expr2.toString() + "</Ge_Op>";
                break;
            case Constant.LT_OP:
                temp = temp + "<Lt_Op>" + expr1.toString() + expr2.toString() + "</Lt_Op>";
                break;
            case Constant.LE_OP:
                temp = temp + "<Le_Op>" + expr1.toString() + expr2.toString() + "</Le_Op>";
                break;
            case Constant.EQ_OP:
                temp = temp + "<Eq_Op>" + expr1.toString() + expr2.toString() + "</Eq_Op>";
                break;
            case Constant.NE_OP:
                temp = temp + "<Ne_Op>" + expr1.toString() + expr2.toString() + "</Ne_Op>";
                break;
            case Constant.MINUS_OP:
                temp = temp + "-" + expr1.toString();
                break;
            case Constant.NOT_OP:
                temp = temp + "!" + expr1.toString();
                break;
            case Constant.CALL_PROC_OP:
                temp = temp  + stat;
                break;
        }
        return temp;
    }

    public String toError() {
        ArrayList<Id> Allid = getAllId();
        String s = " ";
        for (int i = 0; i < Allid.size(); i++) {
            s = Allid.get(i).toString();
        }
        return s;
    }


    public String toCLang() {
        String lang1 = "";
        String lang2 = "";

        if (expr1 != null)
            lang1 = expr1.toCLang();

        if (expr2 != null)
            lang2 = expr2.toCLang();

        switch (OpType) {
            case Constant.NULL_CONST:
                return "null";
            case Constant.BOOL_CONST:
                return identifier.getName();
            case Constant.INT_CONST:
                return identifier.getName();
            case Constant.FLOAT_CONST:
                return identifier.getName();
            case Constant.STRING_CONST:
                return "\"" + identifier.getName() + "\"";
            case Constant.ID_CONST:
                return identifier.getName();
            case Constant.SUM_OP:
                return lang1 + " + " + lang2;
            case Constant.DIFF_OP:
                return lang1 + " - " + lang2;
            case Constant.MUL_OP:
                return lang1 + " * " + lang2;
            case Constant.DIV_OP:
                return lang1 + " / " + lang2;
            case Constant.AND_OP:
                return lang1 + " && " + lang2;
            case Constant.OR_OP:
                return lang1 + " || " + lang2;
            case Constant.GT_OP:
                return lang1 + " > " + lang2;
            case Constant.GE_OP:
                return lang1 + " >= " + lang2;
            case Constant.LT_OP:
                return lang1 + " < " + lang2;
            case Constant.LE_OP:
                return lang1 + " <= " + lang2;
            case Constant.EQ_OP:
                return lang1 + " == " + lang2;
            case Constant.NE_OP:
                return lang1 + " != " + lang2;
            case Constant.MINUS_OP:
                return "- " + lang1;
            case Constant.NOT_OP:
                return "!" + lang1;
            case Constant.CALL_PROC_OP:
                return stat.toCLang(null);
            default:
                return "";
        }


    }
}
