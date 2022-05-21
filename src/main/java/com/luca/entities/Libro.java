package com.luca.entities;

import java.util.Objects;

public class Libro {
    private String titolo;
    private String autore;
    private String casa_edi;
    private int genere;
    private String codLib; //ID
    private int codice;

    public Libro(){
        this.titolo="";
        this.autore="";
        this.casa_edi="";
        this.genere=0;
        this.codLib="";
        this.codice=0;
    }
    public Libro(String titolo, String autore, String casa_edi, String codLib,int genere,int codice) {
        this.titolo = titolo;
        this.autore = autore;
        this.casa_edi = casa_edi;
        this.genere = genere;
        this.codLib=codLib;
        this.codice=codice;
    }




    public String getTitolo() {
        return titolo;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice){this.codice=codice;}

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getCasa_edi() {
        return casa_edi;
    }

    public void setCasa_edi(String casa_edi) {
        this.casa_edi = casa_edi;
    }

    public int getGenere() {
        return genere;
    }

    public void setGenere(int genere) {
        this.genere = genere;
    }

    public String getCodLib() {
        return codLib;
    }

    public void setCodLib(String codLib) {
        this.codLib = codLib;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return  titolo.equals(libro.titolo) && autore.equals(libro.autore) && casa_edi.equals(libro.casa_edi);
    }

    @Override
    public int hashCode() {
        return Objects.hash( titolo, autore, casa_edi, genere);
    }

    @Override
    public String toString() {
        return  "" + titolo + "          " +
                "" + autore + "          " +
                "" + casa_edi + "        " +
                "" +"     " + codLib;
    }
}
