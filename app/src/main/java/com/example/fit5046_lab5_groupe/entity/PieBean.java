package com.example.fit5046_lab5_groupe.entity;



public class PieBean {
    private float Numner;
    private String Name;

    public PieBean() {
    }

    public PieBean(float Numner, String Name) {
        this.Numner = Numner;
        this.Name = Name;
    }

    public float getNumner() {
        return Numner;
    }

    public void setNumner(float numner) {
        Numner = numner;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
