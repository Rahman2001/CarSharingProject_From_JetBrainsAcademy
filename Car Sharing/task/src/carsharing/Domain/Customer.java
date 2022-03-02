package carsharing.Domain;

public class Customer {
    private int id;
    private String name;
    private int rented_car_id = 0;

    public Customer(){}
    public Customer(int id, String name){
        setId(id);
        setName(name);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRented_car_id() {
        return rented_car_id;
    }

    public void setRented_car_id(int rented_car_id) {
        this.rented_car_id = rented_car_id;
    }

    @Override
    public String toString(){
        return getId() + ". " + getName();
    }
}
