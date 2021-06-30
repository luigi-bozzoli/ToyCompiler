package TabSym;

import java.util.ArrayList;

public class Funzione extends Simbolo {
    private ArrayList<Variabile> parameters;
    private ArrayList<String> returnValues;

    public Funzione(Object name, ArrayList<Variabile>parameters, ArrayList<String> returnValues) {
        super((String) name);
        this.parameters=parameters;
        this.returnValues=returnValues;
    }

    public void addParameter(Variabile var) {
        this.parameters.add(var);
    }
    public void addReturnValue(Variabile var){
        this.addReturnValue(var);
    }

    public ArrayList<Variabile> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Variabile> parameters) {
        this.parameters = parameters;
    }

    public ArrayList<String> getReturnValues() {
        return returnValues;
    }

    public void setReturnValues(ArrayList<String> returnValues) {
        this.returnValues = returnValues;
    }
}
