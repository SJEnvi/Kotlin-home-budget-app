package com.example.projektfinalny;

import  java.sql.Connection;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Hikari {
    Connection con;
    @SuppressLint("NewApi")
    public Connection conclass(){

        StrictMode.ThreadPolicy a= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String ConnectURL=null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectURL = "jdbc:sqlserver://new-server-sqo.database.windows.net:1433;database=tested;user=sjenvi@new-server-sqo;password=LOLlolLOL98;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            con = DriverManager.getConnection(ConnectURL);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return con;
    }
}

