package Albero.Nodi;

import TabSym.Symtable;

public abstract class Stat {
    public abstract void checkIdDeclaration(Symtable symtable);
    public abstract void checkType(Symtable symtable);
    public abstract String toCLang(Symtable symtable);
}
