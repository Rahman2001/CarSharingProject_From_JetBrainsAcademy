package carsharing.Domain;

public class Car {
    private String name;
    private int id;
    private int companyId;
    private int idCompanyCar = 1;

    public Car(){}
    public Car(int id, String name){
        setId(id);
        setName(name);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId(){return this.companyId;}

    public void setCompanyId(int id){this.companyId = id;}

    public int getIdCompanyCar(){return this.idCompanyCar;}

    public void setIdCompanyCar(int idCompanyCar){this.idCompanyCar = idCompanyCar;}

    public String toString(){
        return getIdCompanyCar() + ". " + getName();
    }
}
