package com.example.finnkino;

public class Theatre {

    private final int id;
    private final String name;

    public Theatre (int i, String s) {
        id = i;
        name = s;
    }

    public String getName() {return name;}
    public int getID() {return id;}
}
