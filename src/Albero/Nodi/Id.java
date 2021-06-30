package Albero.Nodi;

public class Id {
    public Id(String name) {
        this.name = name;
    }


    private String name;

    public Id(){    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "(ID, "+ this.name+")";
    }
}
