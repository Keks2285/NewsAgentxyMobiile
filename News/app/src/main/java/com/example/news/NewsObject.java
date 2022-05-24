package com.example.news;

public class NewsObject {
    private int Id;
    private String Name; // название
    private String Description;  // описание

    public NewsObject(int id, String name, String description){
        Id=id;
        Name=name;
        Description=description;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name= name;
    }

    public String getDesription() {
        return Description;
    }

    public void setDesription(String description) {
        Description=description;
    }
    public int getId() {
        return Id;
    }

}
