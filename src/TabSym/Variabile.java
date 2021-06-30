package TabSym;

public class Variabile extends Simbolo {
    private String type;

        public Variabile(Object name, Object type) {
            super((String) name);
            this.type= (String) type;
        }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
