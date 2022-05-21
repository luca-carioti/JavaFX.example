package com.luca.repository;

import com.luca.connector.Connector;
import com.luca.entities.Genere;
import com.luca.exceptions.GenereAlreadyExistException;
import com.luca.exceptions.LetteraAlreadyExistException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbGenereDao implements GenereDao{
    private Connection connection;
    private PreparedStatement pstm;
    private ResultSet rs;

    public DbGenereDao(){}

    private Connection getConnection() throws SQLException{
        Connector connector=Connector.getInstance();
        return connector.getConnection();
    }


    @Override
    public ArrayList<Genere> getAll() throws SQLException, Exception {
        ArrayList<Genere> generi=new ArrayList<>();
        try{

            connection=getConnection();
            pstm=connection.prepareStatement("select * from genere;");
            rs=pstm.executeQuery();
            while(rs.next()){
                Genere g=new Genere(rs.getInt(1),rs.getString(2), rs.getString(3));
                generi.add(g);
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
            }catch(SQLException e ){e.printStackTrace();}
            catch(Exception e){e.printStackTrace();}
        }
        return generi;
    }


    @Override
    public boolean add(Genere genere) throws SQLException, Exception {
        boolean ret=true;
        try{
            String query="insert into genere (nome, lettera) values(?,?);";
            connection=getConnection();
            PreparedStatement pstm=connection.prepareStatement(query);
            pstm.setString(1,genere.getNome());
            pstm.setString(2, genere.getLettera());
            pstm.executeUpdate();
        }catch(SQLException e){e.printStackTrace(); ret=false;}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
            }catch(SQLException e){e.printStackTrace();}
            catch(Exception e){System.out.println("Problema nell'aggiunta di un genere");e.printStackTrace();}
        }
        return ret;
    }

    @Override
    public boolean delete(Genere genere) throws SQLException, Exception {
        boolean ret=true;
        try{
            String query="delete from genere where CODICE=?;";
            connection=getConnection();
            pstm= connection.prepareStatement(query);
            pstm.setInt(1,genere.getCodice());
            pstm.executeUpdate();
        }catch(SQLException e ){e.printStackTrace();}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
            }catch(SQLException e ){e.printStackTrace(); ret=false;}
        }
        return ret;
    }

    @Override
    public boolean update(Genere genere) throws SQLException, Exception {
        boolean ret=true;
        try{
            String query="update genere set nome=?,LETTERA=? where CODICE=?";
            connection=getConnection();
            pstm=connection.prepareStatement(query);
            pstm.setString(1,genere.getNome());
            pstm.setString(2, genere.getLettera());
            pstm.setInt(3, genere.getCodice());
            pstm.executeUpdate();
        }catch(SQLException e ){e.printStackTrace();}
        finally{
            try{
                if(connection!=null)
                    connection.close();
                if(pstm!=null)
                    pstm.close();
            }catch(SQLException e ){e.printStackTrace();ret=false;}
            catch(Exception  e){System.out.println("Problema nell'aggiornamento del genere");e.printStackTrace();}
        }
        return ret;
    }

    public int getCodiceByName(String name) throws IOException, SQLException{
        int ret=0;
        try{

            connection=getConnection();
            pstm=connection.prepareStatement("select CODICE from genere where NOME=?;");
            pstm.setString(1,name);
            rs=pstm.executeQuery();
            while(rs.next()){
                ret=rs.getInt(1);
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
            }catch(SQLException e ){e.printStackTrace();}
            catch(Exception e){e.printStackTrace();}
        }
        return ret;
    }

    public boolean existByLettera(String Lettera) throws LetteraAlreadyExistException{
        boolean ret=false;
        try{
            connection=getConnection();
            pstm=connection.prepareStatement("select * from genere where LETTERA=?;");
            pstm.setString(1,Lettera);
            rs=pstm.executeQuery();
            if (rs.next()){
                ret=true;
                throw new LetteraAlreadyExistException();
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
            }catch(SQLException e ){e.printStackTrace();}
            catch(Exception e){e.printStackTrace();}
        }
        return ret;
    }

    public boolean existByName(String nome) throws GenereAlreadyExistException {
        boolean ret=false;
        try{
            connection=getConnection();
            pstm=connection.prepareStatement("select * from genere where NOME=?;");
            pstm.setString(1,nome);
            rs=pstm.executeQuery();
           if (rs.next()){
               ret=true;
               throw new GenereAlreadyExistException();
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
            }catch(SQLException e ){e.printStackTrace();}
            catch(Exception e){e.printStackTrace();}
        }
        return ret;
    }

    public static void main(String...args) throws Exception {
        ArrayList<Genere> generi=new ArrayList<>();
        DbGenereDao dbGenereDao=new DbGenereDao();
        generi=dbGenereDao.getAll();
        System.out.println(generi);
        Genere genere=new Genere(3,"cianoe","c");
        //dbGenereDao.delete(genere);
        //dbGenereDao.add(genere);
        //Genere mod=new Genere("Maiali","m");
        int cod=dbGenereDao.getCodiceByName("cazzo");
        System.out.println(cod);

    }
}
