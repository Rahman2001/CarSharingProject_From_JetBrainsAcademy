package carsharing.DAO;

import carsharing.Domain.Car;
import carsharing.Domain.Company;
import carsharing.Domain.Customer;

import java.util.List;

public interface DaoInterface {
    List<Company> getAllCompany ();
    boolean createCompany(String name); // adds the company to db
    Company getCompanyByID(int id);
    Company getCompanyByCar(Car car);
    boolean createCar(int companyID, String carName, List<Car> companyCar); // adds the car to the list in company's db
    List<Car> getCarsByCompanyID(int companyID); // retrieves the list of cars respective to the company's id in db
    Car getCarByID(int carID);
    List<Customer> getAllCustomers();
    boolean createCustomer(String name);
    Customer getCustomerByID(int customerID);
    boolean rentCar(int customerID, Car car);
    boolean didCustomerRentCar(int customerID);
    String returnRentedCarOfCustomer(int customerID);
    Car getCarOfCustomerByCustomerID(int customerID);

}
