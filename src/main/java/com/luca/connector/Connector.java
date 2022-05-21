package com.luca.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    //implemento il pattern singleton
    private static Connector instance;
    private Connection connection;
    private final String DRIVER="org.h2.Driver";
    private final String URL="jdbc:h2:~/Biblioteca";
    private final String USER="";
    private final String PASSWORD="";

    private Connector() {
        try{
            Class.forName(DRIVER);
        }catch(ClassNotFoundException e){e.printStackTrace();}
    }

    public Connection getConnection() throws SQLException{

        connection= DriverManager.getConnection(URL,USER,PASSWORD);
        return connection;
    }

    public static Connector getInstance() throws SQLException {
        if(instance==null){
            instance=new Connector();
        }else if(instance.getConnection().isClosed()){
            instance=new Connector();
        }
        return instance;
    }

}
