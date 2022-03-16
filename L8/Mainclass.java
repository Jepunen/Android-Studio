import java.util.ArrayList;
import java.util.Scanner;

public class Mainclass {
    public static void main(String[] args) {

        int choice;

        BottleDispenser bottle = BottleDispenser.getInstance();

        Scanner sc = new Scanner(System.in);

        do {
            System.out.printf(
                    "\n*** BOTTLE DISPENSER ***\n"
                + "1) Add money to the machine\n"
                + "2) Buy a bottle\n"
                + "3) Take money out\n"
                + "4) List bottles in the dispenser\n"
                + "0) End\n"
                + "Your choice: "
            );
            choice = sc.nextInt();

            switch (choice) {
                case 0:
                    break;
                case 1:
                    bottle.addMoney();
                    break;
                case 2:
                    ArrayList<Bottle> arrayBottles = bottle.getArray();

                    for ( int i = 1; i <= bottle.getBottles(); i++ ) {
                        System.out.println(i + ". Name: " + arrayBottles.get(i - 1).getName());
                        System.out.println("\tSize: " + arrayBottles.get(i - 1).getBottle_size() + "\tPrice: " + arrayBottles.get(i - 1).getBottle_price());
                    }
                    System.out.printf("Your choice: ");
                    int buy = sc.nextInt();

                    bottle.buyBottle(buy);

                    break;
                case 3:
                    bottle.returnMoney();
                    break;
                case 4:
                    bottle.listBottles();
                    break;
                default:
                    break;
            }

        } while ( choice != 0 );
    }
}
