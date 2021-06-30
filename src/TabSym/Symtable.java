package TabSym;

import Albero.Nodi.Id;

import java.util.ArrayList;
import java.util.HashMap;

public class Symtable {
    private Symtable padre;
    private HashMap<String, Simbolo> map = new HashMap<>();
    private int livello;
    private ArrayList<Symtable> children = new ArrayList<>();

    public Symtable() {
        livello = 0;
    }

    public void setPadre(Symtable padre) {
        padre.addChild(this);
        this.padre = padre;
    }

    public Symtable getPadre() {
        return padre;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public void incrementaLivello() {
        this.livello++;
    }

    public HashMap<String, Simbolo> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Simbolo> map) {
        this.map = map;
    }

    public void addChildren(Symtable input) {
        input.incrementaLivello();
        children.add(input);
    }

    public void addSymbol(Simbolo s) {
        if (s == null)
            return;

        if (this.map.containsKey(s.getName())) {
            String message = "";
            if (s instanceof Variabile) {
                message = "Variabile già dichiarata";
            } else {
                message = "Funziona già dichiarata";
            }
            throw new RuntimeException(message);
        } else {
            this.map.put(s.getName(), s);
            // return true;
        }
    }

    public void addSymbols(ArrayList<Simbolo> simbolos) {
        for (Simbolo s : simbolos) {
            addSymbol(s);
        }
    }

    public void checkIdDeclaration(ArrayList<Id> list) {
        for (Id id : list) {
            if (!this.map.containsKey(id.getName())) {
                if (this.livello != 0) {
                    padre.checkIdDeclaration(list);
                } else {
                    throw new RuntimeException("Variabile non dichiarata: " + id.getName());
                }
            }
        }

    }

    public String getTipo(Id id) {
        Simbolo var = this.map.get(id.getName());

        if (var == null) {
            if (this.livello != 0)
                return this.padre.getTipo(id);
            else
                return "";
        } else {
            return ((Variabile) var).getType();
        }

    }

    public ArrayList<String> getTipi(ArrayList<Id> idList){
        ArrayList<String> tipi = new ArrayList<>();

        for (Id id : idList){
            tipi.add(getTipo(id));
        }

        return tipi;
    }


    public ArrayList<String> getReturnValues(Id id) {

        Simbolo var = this.map.get(id.getName());

        if (var == null) {
            ArrayList<String> result = new ArrayList<>();
            if (this.livello != 0) {
               result =  this.padre.getReturnValues(id);
            }
            return result;
        } else {
            Funzione func = (Funzione) var;
            return func.getReturnValues();
        }

    }

    public ArrayList<String> getParamListType (Id id) {
        Simbolo var = this.padre.getMap().get(id.getName());
        ArrayList<String> result = new ArrayList<>();
        Funzione func = (Funzione) var;
        ArrayList<Variabile> variabiles = func.getParameters();

        for(Variabile variabile : variabiles) {
            result.add(variabile.getType());
        }

        return result;
    }

    public void checkMain() {
        if (!this.map.containsKey("main"))
            throw new RuntimeException("Main non dichiarato");
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public ArrayList<Symtable> getChildren() {
        return children;
    }

    public String toString() {
        return map.toString();
    }

    public void addChild(Symtable child) {
        this.children.add(child);
    }

}

