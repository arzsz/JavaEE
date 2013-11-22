package com.example.jdbcdemo.domain;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 
import com.example.jdbc.domain.Kamera;
 
public class KameraMenager {
    private Connection connection;
    String url = "jdbc:hsqldb:hsql://localhost/workdb";
    private Statement statement;
    
    
 // wzorce do zapytan sql
    private PreparedStatement addKamera;
    private PreparedStatement delAllKamera;
    private PreparedStatement writeAllKamera;
    private PreparedStatement searchKamera;
    private PreparedStatement updateKamera;
 
    public KameraMenager() {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
 
            // sprawdzanie czy baza instnieje
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
 
            while (rs.next()) {
                if ("Kamera".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }
 
            if (!tableExists)
                statement.executeUpdate("Create Table kamera (id int, name varchar(20), type varchar(20), cena int);");
 
            // Przygotowanie wzorcow do zapytan sql
            addKamera = connection.prepareStatement("INSERT into kamera VALUES (?, ?, ?, ?);");
            delAllKamera = connection.prepareStatement("DELETE FROM kamera where id = (?);");
            writeAllKamera = connection.prepareStatement("SELECT * from kamera;");
            searchKamera = connection.prepareStatement("SELECT * from kamera where id = (?);");
            updateKamera = connection.prepareStatement("UPDATE kamera SET name = (?), type = (?), cena = (?) WHERE id = (?);");
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    // polaczenie z baza
    public Connection getConnection() {
        return connection;
    }
 
    // usuwanie wszystkich osob z bazy danych
    public void clearDB(Kamera kamera) {
        try {
 
            delAllKamera.setInt(1, kamera.getId());
            delAllKamera.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    // dodanie nowej osoby do bazy
    public void AddKamera(Kamera kamera) {
 
        try {
            addKamera.setInt(1, kamera.getId());
            addKamera.setString(2, kamera.getName());
            addKamera.setString(3, kamera.getType());
            addKamera.setInt(4, kamera.getCena());
            addKamera.executeUpdate();
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    // wyspisanie wszystki z bazy danych
    public ArrayList<Kamera> WriteAll() {
        ArrayList<Kamera> Lista = new ArrayList<Kamera>();
 
        try {
            ResultSet rs = writeAllKamera.executeQuery();
            while (rs.next()) {
                Kamera kamera = new Kamera();
                kamera.setId(rs.getInt(1));
                kamera.setName(rs.getString(2));
                kamera.setType(rs.getString(3));
                kamera.setCena(rs.getInt(4));
                Lista.add(kamera);
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return Lista;
    }
 
    public Kamera searchKamera(int id) {
        Kamera kamera = new Kamera();
 
        try {
            searchKamera.setInt(1, id);
            ResultSet rs = searchKamera.executeQuery();
 
            while (rs.next()) {
                kamera.setId(rs.getInt(1));
                kamera.setName(rs.getString(2));
                kamera.setType(rs.getString(3));
                kamera.setCena(rs.getInt(4));
                return kamera;
            }
             
             
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
         
        return null;
    }
     
    public void UpdateKamera(Kamera kamera){
        try {
            updateKamera.setString(1, kamera.getName());
            updateKamera.setString(2, kamera.getType());
            updateKamera.setInt(3, kamera.getCena());
            updateKamera.setInt(4, kamera.getId());
            updateKamera.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
         
    }
}