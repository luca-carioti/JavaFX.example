package com.luca.repository;

import com.luca.entities.Libro;


import java.sql.SQLException;
import java.util.ArrayList;

public interface LibroDao {

    public ArrayList<Libro> getAll() throws SQLException,Exception;
    public boolean add(Libro libro) throws SQLException,Exception;
    public boolean delete(Libro libro) throws SQLException,Exception;
    public boolean update(Libro libro) throws SQLException,Exception;
    public Libro getById(String codice) throws SQLException,Exception;
    public ArrayList<Libro> getByGenere(int genere) throws SQLException,Exception;
    public ArrayList<Libro> getByTitolo(String titolo) throws SQLException,Exception;
    public ArrayList<Libro> getByAutore(String autore) throws SQLException,Exception;
    public ArrayList<Libro> getByCasa(String casa) throws SQLException,Exception;

}
