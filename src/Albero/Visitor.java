package Albero;

import Albero.Nodi.Proc;
import Albero.Nodi.Program;
import Albero.Nodi.VarDecl;
import TabSym.Symtable;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Visitor {

    private String content = "<ProgramOp>";
    private String cLanguage = "";

    public void saveCFile(Program radice){
        toCLang(radice);
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("main.c"), "utf-8"));
            writer.write(this.cLanguage);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Errore nella scrittura del file");
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                System.out.println("Errore durante la chiusura del file");
            }
        }
    }

    private void toCLang(Program radice){
        this.cLanguage = radice.toCLang();
    }

    public void saveFileXML(Program radice) {
        creaXml(radice);
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("ProgramTree.xml"), "utf-8"));
            writer.write(this.content);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Errore nella scrittura del file");
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                System.out.println("Errore durante la chiusura del file");
            }
        }
    }

    private void creaXml(Program radice) {
        String result = "";
        result = result + "<Program_Op>";
        ArrayList<VarDecl> figli = radice.getVarDeclList();

        if (figli.size() != 0) {
            result = result + "<VarDecList>";
            for (int i = 0; i < figli.size(); i++) {
                result = result + figli.get(i).toString();
            }
            result = result + "</VarDecList>";
        }
        ArrayList<Proc> figli2 = radice.getProcOpList();

        if (figli2.size() != 0) {
            Collections.reverse(figli2);
            result = result + "<ProcList>";
            for (int i = 0; i < figli2.size(); i++) {
                result = result + figli2.get(i).toString();
            }
            result = result + "</ProcList>";
        }

        this.content = result + "</Program_Op>";
    }

    public static void stampaTabella(Symtable s) {

        ArrayList<Symtable> tmp = s.getChildren();
    }
}
