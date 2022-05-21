package com.luca.entities;

import java.util.Objects;

public class Genere {
    private int codice;
    private String nome;
    private String lettera;

    public Genere(String nome,String lettera){
        this.nome=nome;
        this.lettera=lettera;
    }

    public Genere(int codice,String nome, String lettera) {
        this.codice=codice;
        this.nome = nome;
        this.lettera=lettera;
    }

    @Override
    public String toString() {
        return "" + nome ;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodice() {
        return codice;
    }

    public String getLettera() {
        return lettera;
    }

    public void setLettera(String lettera) {
        this.lettera = lettera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genere genere = (Genere) o;
        return nome.equals(genere.nome) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
