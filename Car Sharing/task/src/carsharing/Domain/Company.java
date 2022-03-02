package carsharing.Domain;

public class Company {
    private String name;
    private int ID;

    public Company(){}
    public Company(int id, String name){
        setID(id);
        setName(name);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public String toString(){
        return getID() + ". " + getName();
    }
}
