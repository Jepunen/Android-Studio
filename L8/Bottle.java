package com.example.bottledispenser;

public class Bottle {
    private final String name;
    private final String manufacturer;
    private final double bottle_size;
    private final double bottle_price;
    private final double total_energy;

    public Bottle(){
        name = "";
        manufacturer = "";
        bottle_size = 0;
        bottle_price = 0;
        total_energy = 0;
    }
    public Bottle(String n, String manuf, double size, double price, double totE){
        name = n;
        manufacturer = manuf;
        bottle_size = size;
        bottle_price = price;
        total_energy = totE;
    }
    public String getName(){
        return name;
    }
    public String getManufacturer(){
        return manufacturer;
    }
    public double getBottle_price() {return bottle_price;}
    public double getBottle_size() {return bottle_size;}
    public double getEnergy(){
        return total_energy;
    }
}
