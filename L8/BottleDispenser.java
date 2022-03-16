import java.util.ArrayList;

public class BottleDispenser {

    private static BottleDispenser bd = new BottleDispenser();

    private int bottles;
    private double money;

    private ArrayList<Bottle> arrayBottles = new ArrayList<Bottle>();

    public BottleDispenser() {
        bottles = 5;
        money = 0;

        Bottle small_pepsi = new Bottle();
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

    public static BottleDispenser getInstance() {
        return bd;
    }

    public void addMoney() {
        money += 1;
        System.out.println("Klink! Added more money!");
    }

    public void buyBottle(Integer index) {

        double price = arrayBottles.get(index - 1).getBottle_price();

        if ( money < price ) {
            System.out.println("Add money first!");
        } else if ( bottles <= 0 ) {
            System.out.println("Machine out of bottles!");
        } else {
            money -= price;
            System.out.println("KACHUNK! " + arrayBottles.get(index - 1).getName() + " came out of the dispenser!");
            removeBottle(index - 1);
            bottles -= 1;
        }
    }

    public void returnMoney() {
        System.out.printf("Klink klink. Money came out! You got %.2f€ back\n", money);
        money = 0;
    }
    public void listBottles() {

        for ( int i = 1; i <= bottles; i++ ) {
            System.out.println(i + ". Name: " + arrayBottles.get(i - 1).getName());
            System.out.println("\tSize: " + arrayBottles.get(i - 1).getBottle_size() + "\tPrice: " + arrayBottles.get(i - 1).getBottle_price());
        }
    }
    public void removeBottle(int index) {
        arrayBottles.remove(index);
    }

    public int getBottles() {return bottles;}
    public ArrayList getArray() {return arrayBottles;}
}