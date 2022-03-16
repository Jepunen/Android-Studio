public class Bottle {
    private String name;
    private String manufacturer;
    private double bottle_size;
    private double bottle_price;
    private double total_energy;

    public Bottle(){
        name = "Pepsi Max";
        manufacturer = "Pepsi";
        bottle_size = 0.5;
        bottle_price = 1.80;
        total_energy = 0.3;
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
