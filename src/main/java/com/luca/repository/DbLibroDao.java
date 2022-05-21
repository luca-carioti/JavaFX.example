package com.luca.repository;

import com.luca.connector.Connector;
import com.luca.entities.Libro;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbLibroDao implements LibroDao{
    private Connection connection;
    private PreparedStatement pstm;
    private ResultSet rs;

    public DbLibroDao(){}

    private Connection getConnection() throws SQLException{
        Connector connector=Connector.getInstance();
        return connector.getConnection();
    }

    @Override
    public ArrayList<Libro> getAll() throws SQLException, Exception {
        ArrayList<Libro> libri=new ArrayList<>();
        try{
            connection=getConnection();
            pstm=connection.prepareStatement("select * from LIBRO;");
            rs=pstm.executeQuery();
            while(rs.next()){
                Libro l=new Libro(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6));
                libri.add(l);
            }
        }catch(SQLException e ){e.printStackTrace();}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e ){System.out.println("La tabella potrebbe essere vuota");e.printStackTrace();}
            catch(Exception e){System.out.println("Probabile problema con la connessione");e.printStackTrace();}
        }
        return libri;
    }

    @Override
    public boolean add(Libro libro) throws SQLException, Exception {
        boolean ret=true;
        try{
            String query="insert into libro(titolo, autore, casa_edi, cod_lib, genere) values(?,?,?,?,?);";
            connection=getConnection();
            PreparedStatement pstm=connection.prepareStatement(query);
            pstm.setString(1, libro.getTitolo());
            pstm.setString(2, libro.getAutore());
            pstm.setString(3, libro.getCasa_edi());
            pstm.setInt(5, libro.getGenere());
            pstm.setString(4, libro.getCodLib());
            pstm.executeUpdate();
        }catch(SQLException e){e.printStackTrace(); ret=false;}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
            }catch(SQLException e){System.out.println("Problema con add libro");e.printStackTrace();}
            catch(Exception e){System.out.println("Problema di connessione");e.printStackTrace();}
        }
        return ret;
    }

    @Override
    public boolean delete(Libro libro) throws SQLException, Exception {
        boolean ret=true;
        try{
            String query="delete from libro where CODICE=?;";
            connection=getConnection();
            pstm= connection.prepareStatement(query);
            pstm.setInt(1,libro.getCodice());
            pstm.executeUpdate();
        }catch(SQLException e ){e.printStackTrace();}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
            }catch(SQLException e ){System.out.println("Problemi con delete libro");e.printStackTrace(); ret=false;}
        }
        return ret;
    }

    @Override
    public boolean update(Libro libro) throws SQLException, Exception {
        boolean ret=true;
        try{
            String query="update libro set TITOLO=?,AUTORE=?,CASA_EDI=?,COD_LIB=?,GENERE=? where CODICE=?";
            connection=getConnection();
            pstm=connection.prepareStatement(query);
            pstm.setString(1, libro.getTitolo());
            pstm.setString(2, libro.getAutore());
            pstm.setString(3,libro.getCasa_edi());
            pstm.setString(4,libro.getCodLib());
            pstm.setInt(5, libro.getGenere());
            pstm.setInt(6,libro.getCodice());
            pstm.executeUpdate();
        }catch(SQLException e ){e.printStackTrace();}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
            }catch(SQLException e ){System.out.println("Problemi con update libro");e.printStackTrace();ret=false;}
            catch(Exception  e){System.out.println("Problemi di connessione");e.printStackTrace();}
        }
        return ret;
    }

    @Override
    public Libro getById(String codice) throws SQLException, Exception {
        Libro p=new Libro();
        try{
            connection=getConnection();
            pstm=connection.prepareStatement("select * from LIBRO where COD_LIB=?;");
            pstm.setString(1,codice);
            rs=pstm.executeQuery();
            if(rs.next()){
            rs.next();
            p.setCodLib(codice);
            p.setTitolo(rs.getString(2));
            p.setAutore(rs.getString(3));
            p.setCasa_edi(rs.getString(4));
            p.setGenere(rs.getInt(5));
            p.setCodice(rs.getInt(6));}
            else{
                p=null;
            }

        }catch(SQLException e ){System.out.println("Sicuramente non è stato trova nessun libro");}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e ){e.printStackTrace();}
            catch(Exception e){e.printStackTrace();}
        }
        return p;
    }

    @Override
    public ArrayList<Libro> getByGenere(int genere) throws SQLException, Exception {
        ArrayList<Libro> ret=new ArrayList<>();
        try{
            connection=getConnection();
            pstm=connection.prepareStatement("select * from LIBRO where GENERE=?;");
            pstm.setInt(1,genere);
            rs=pstm.executeQuery();
            while (rs.next()){
                Libro libro=new Libro();
                libro.setGenere(rs.getInt(5));
                libro.setTitolo(rs.getString(1));
                libro.setAutore(rs.getString(2));
                libro.setCasa_edi(rs.getString(3));
                libro.setCodLib(rs.getString(4));
                libro.setCodice(rs.getInt(6));
                ret.add(libro);
            }

        }catch(SQLException e ){System.out.println("Sicuramente non è stato trova nessun libro"); e.printStackTrace();}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e ){e.printStackTrace();}
            catch(Exception e){e.printStackTrace();}
        }
        return ret;
    }

    @Override
    public ArrayList<Libro> getByTitolo(String titolo) throws SQLException, Exception {
        ArrayList<Libro> ret=new ArrayList<>();
        try{
            connection=getConnection();
            pstm=connection.prepareStatement("select * from LIBRO where TITOLO =?;");
            pstm.setString(1,titolo);
            rs=pstm.executeQuery();
            while (rs.next()){
                Libro libro=new Libro();
                libro.setGenere(rs.getInt(5));
                libro.setTitolo(rs.getString(1));
                libro.setAutore(rs.getString(2));
                libro.setCasa_edi(rs.getString(3));
                libro.setCodLib(rs.getString(4));
                libro.setCodice(rs.getInt(6));
                ret.add(libro);
            }

        }catch(SQLException e ){System.out.println("Sicuramente non è stato trova nessun libro");}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e ){e.printStackTrace();}
            catch(Exception e){e.printStackTrace();}
        }
        return ret;
    }

    @Override
    public ArrayList<Libro> getByAutore(String autore) throws SQLException, Exception {
        ArrayList<Libro> ret=new ArrayList<>();
        try{
            connection=getConnection();
            pstm=connection.prepareStatement("select * from LIBRO where AUTORE= ?;");
            pstm.setString(1,autore);
            rs=pstm.executeQuery();
            while (rs.next()){
                Libro libro=new Libro();
                libro.setCodLib(rs.getString(4));
                libro.setTitolo(rs.getString(1));
                libro.setAutore(rs.getString(2));
                libro.setCasa_edi(rs.getString(3));
                libro.setGenere(rs.getInt(5));
                libro.setCodice(rs.getInt(6));
                ret.add(libro);
            }

        }catch(SQLException e ){System.out.println("Sicuramente non è stato trova nessun libro");e.printStackTrace();}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e ){e.printStackTrace();}
            catch(Exception e){e.printStackTrace();}
        }
        return ret;

    }

    @Override
    public ArrayList<Libro> getByCasa(String casa) throws SQLException, Exception {
        ArrayList<Libro> ret=new ArrayList<>();
        try{
            connection=getConnection();
            pstm=connection.prepareStatement("select * from LIBRO where CASA_EDI like ?;");
            pstm.setString(1,casa);
            rs=pstm.executeQuery();
            while (rs.next()){
                Libro libro=new Libro();
                libro.setCodLib(rs.getString(4));
                libro.setTitolo(rs.getString(1));
                libro.setAutore(rs.getString(2));
                libro.setCasa_edi(rs.getString(3));
                libro.setGenere(rs.getInt(5));
                libro.setCodice(rs.getInt(6));
                ret.add(libro);
            }

        }catch(SQLException e ){System.out.println("Sicuramente non è stato trova nessun libro");}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e ){e.printStackTrace();}
            catch(Exception e){e.printStackTrace();}
        }
        return ret;

    }


    public static void main(String...args) throws Exception {
        DbLibroDao dbLibroDao=new DbLibroDao();
        Libro libro=new Libro("modificato","bello","ssjsj","sjsj",3,2);
        ArrayList<Libro> libri=dbLibroDao.getAll();
        System.out.println(libri);

    }
}
