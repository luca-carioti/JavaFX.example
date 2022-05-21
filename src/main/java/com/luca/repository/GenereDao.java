package com.luca.repository;

import com.luca.entities.Genere;


import java.sql.SQLException;
import java.util.ArrayList;

public interface GenereDao {
    public ArrayList<Genere> getAll() throws SQLException,Exception;
    public boolean add(Genere genere) throws SQLException,Exception;
    public boolean delete(Genere genere) throws SQLException,Exception;
    public boolean update(Genere genere) throws SQLException,Exception;
}
