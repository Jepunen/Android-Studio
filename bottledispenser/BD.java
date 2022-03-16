package com.example.bottledispenser;

import java.util.ArrayList;

public class BD {

    private static BD bd = new BD();

    private int bottles;
    private double money;

    private final ArrayList<Bottle> arrayBottles = new ArrayList<Bottle>();

    public BD() {
        bottles = 5;
        money = 0;

        Bottle small_pepsi = new Bottle("Pepsi Max", "Pepsi", 0.5, 1.80, 0.9);
        Bottle big_pepsi   = new Bottle("Pepsi Max", "Pepsi", 1.5, 2.20, 0.9);
        Bottle small_coce  = new Bottle("Coca-Cola Zero", "Coca-Cola", 0.5, 2.00, 0.3);
        Bottle big_coce    = new Bottle("Coca-Cola Zero", "Coca-Cola", 1.5, 2.50, 0.9);
        Bottle small_fanta = new Bottle("Fanta Zero", "Fanta", 0.5, 1.95, 0.3);

        arrayBottles.add(small_pepsi);
        arrayBottles.add(big_pepsi);
        arrayBottles.add(small_coce);
        arrayBottles.add(big_coce);
        arrayBottles.add(small_fanta);

    }

    public static BD getInstance() {return bd;}
    public double getMoney() {return money;}

    public void addMoney(double n) {
        money += n;
    }


    public String buyBottle(Integer index) {

        double price = arrayBottles.get(index).getBottle_price();

        money -= price;
        bottles -= 1;
        return arrayBottles.get(index).getName();
    }

    public void returnMoney() {
        money = 0;
    }

    public void removeBottle(int index) {
        arrayBottles.remove(index);
    }

    public int getBottles() {return bottles;}
    public ArrayList getArray() {return arrayBottles;}
}
