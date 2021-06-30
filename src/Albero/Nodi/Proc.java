package Albero.Nodi;

import TabSym.Funzione;
import TabSym.Simbolo;
import TabSym.Symtable;
import TabSym.Variabile;

import java.util.ArrayList;

public class Proc {
    private String identifier;
    private ArrayList<ParDecl> parDeclList;
    private ArrayList<String> returnTypes;
    private ProcBody body;
    private Symtable symtable;

    public Proc(String identifier, ArrayList<ParDecl> parDeclList, ArrayList<String> returnTypes, ProcBody body) {
        this.identifier = identifier;
        this.parDeclList = parDeclList;
        this.returnTypes = returnTypes;
        this.body = body;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ArrayList<ParDecl> getParDeclList() {
        return parDeclList;
    }

    public void setParDeclList(ArrayList<ParDecl> parDeclList) {
        this.parDeclList = parDeclList;
    }

    public ProcBody getBody() {
        return body;
    }

    public void setBody(ProcBody body) {
        this.body = body;
    }


    public ArrayList<String> getReturnTypes() {
        return returnTypes;
    }

    public void setReturnTypes(ArrayList<String> returnTypes) {
        returnTypes = returnTypes;
    }

    public Symtable getSymtable() {
        return symtable;
    }

    public void setSymtable(Symtable symtable) {
        this.symtable = symtable;
    }

    public String toString() {
        String result = "<ProcOp>";
        result = result + "(ID, " + identifier + ")";

        if (parDeclList != null) {
            result = result + "<ParDecList>";
            for (int i = 0; i < parDeclList.size(); i++) {
                result = result + parDeclList.get(i).toString();
            }
            result = result + "</ParDecList>";
        }
        result = result + "<ResultTypeList>";

        if (returnTypes != null)
            for (int i = 0; i < returnTypes.size(); i++) {
                result = result + "(" + returnTypes.get(i) + ")";
            }
        result = result + "</ResultTypeList>";
        result = result + "<ProcBodyOp>" + body + "</ProcBodyOp>";
        result += "</ProcOp>";
        return result;
    }

    public Simbolo toFunzione() {
        Simbolo simbolo;
        ArrayList<Variabile> variableArrayList = new ArrayList<>();

        if (parDeclList != null) {
            for (ParDecl par : parDeclList) {
                variableArrayList.addAll(par.toVariabili());
            }
        }

        return new Funzione(identifier, variableArrayList, returnTypes);
    }

    public void checkIdDeclaration() {
        body.checkIdDeclaration(symtable);
    }

    public void checkTypes() {
        body.checkTypes(symtable, returnTypes, identifier);
    }

    public String functionSignature() {
        StringBuilder result = new StringBuilder();

        if (identifier.equals("main"))
            return "";

        if (returnTypes.size() > 1) {
            result.append("void " + identifier + "( ");
        } else {
            String returnType = returnTypes.get(0);
            if (returnType.equals("string")) {
                returnType = "char*";
            } else if (returnType.equals("bool"))
                returnType = "int";

            result.append(returnType + " " + identifier + "(");
        }

        if (parDeclList != null) {
            for (ParDecl par : parDeclList) {
                result.append(par.toCLang());
            }
            result.append(",");
        }

        if (returnTypes.size() > 1) {


            int i = 0;
            for (String type : returnTypes) {
                i++;
                String var = "traduzione" + i;
                result.append(type + "* " + var + ", ");
            }


        }
        int index = result.lastIndexOf(",");
        if (index != -1)
            result.deleteCharAt(index);

        result.append(");");
        return result.toString();
    }

    public String toCLang() {
        ArrayList<String> returnVariable = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        Boolean main;

        if (identifier.equals("main")) {
            main = true;
            result.append("int " + identifier + "( ");
        } else {
            main = false;
            if (returnTypes.size() > 1) {
                result.append("void " + identifier + "( ");
            } else {
                String returnType = returnTypes.get(0);
                if (returnType.equals("string")) {
                    returnType = "char*";
                } else if (returnType.equals("bool"))
                    returnType = "int";

                result.append(returnType + " " + identifier + "(");
            }
        }

        if (parDeclList != null) {
            for (ParDecl par : parDeclList) {
                result.append(par.toCLang());
            }
            result.append(",");
        }

        if (returnTypes.size() > 1) {

            int i = 0;
            for (String type : returnTypes) {
                i++;
                String var = "traduzione" + i;
                returnVariable.add(var);
                result.append(type + "* " + var + ", ");
            }


        }
        int index = result.lastIndexOf(",");
        if (index != -1)
            result.deleteCharAt(index);

        result.append("){\n");

        result.append(body.toCLang(returnVariable, symtable, main) + "\n");

        result.append("}\n");

        return result.toString();
    }
}
