package com.example.jdbc.domain;
 
public class Kamera {
 
    public Kamera() {
 
    }
 
    public Kamera(int id, String name, String type, int cena) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.cena = cena;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getName() {
        return this.name;
    }
 
    public void setType(String type) {
        this.type = type;
    }
 
    public String getType() {
        return this.type;
    }
 
    public void setCena(int cena) {
        this.cena = cena;
    }
 
    public int getCena() {
        return this.cena;
    }
     
    public void setId(int id) {
        this.id = id;
    }
 
    public int getId() {
        return this.id;
    }
 
    private String name;
    private String type;
    private int cena;
    private int id;
 
}