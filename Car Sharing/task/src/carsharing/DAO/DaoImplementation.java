package carsharing.DAO;

import carsharing.Domain.Car;
import carsharing.Domain.Company;
import carsharing.DatabaseConnection.ConnectionDB;
import carsharing.Domain.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoImplementation implements DaoInterface{
    private final Connection conn = ConnectionDB.getConnection();
    private final Statement stat = conn.createStatement();

    public DaoImplementation() throws ClassNotFoundException, SQLException {


    }

    @Override
    public List<Company> getAllCompany() {
        ResultSet companies;
        List<Company> companyList = new ArrayList<>();
        try {
            companies = stat.executeQuery("SELECT * FROM COMPANY");

            while(companies.next()){
                String name = companies.getString("name");
                int id = companies.getInt("id");
                Company company = new Company(id, name);
                companyList.add(company);
            }
            return companyList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createCompany(String name) {
        try {
            return stat.execute("INSERT INTO COMPANY (name) VALUES ('" + name + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Company getCompanyByID(int id){
        try{
            Company company = new Company();
            ResultSet rs = stat.executeQuery("SELECT * FROM COMPANY WHERE id = " + id);
            while(rs.next()) {
                String name = rs.getString("name");
                company.setID(id);
                company.setName(name);
            }
            return company;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Company getCompanyByCar(Car car){ // returns 'company' object by passing car's 'idCompanyCar' within 'car' object;
        ResultSet rs;
        Company company = new Company();
        try{
            rs = stat.executeQuery("SELECT * FROM COMPANY WHERE id = " +  car.getCompanyId());
            while(rs.next()) {
                company.setName(rs.getString("name"));
                company.setID(rs.getInt("id"));
            }
            return company;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        ResultSet rs;
        List<Customer> customerList = new ArrayList<>();
        try{
            rs = stat.executeQuery("SELECT * FROM CUSTOMER");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int rented_car_id = rs.getInt("rented_car_id");
                Customer customer = new Customer(id, name);
                customer.setRented_car_id(rented_car_id);
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createCustomer(String name) {
        try {
            return stat.execute("INSERT INTO CUSTOMER (name) VALUES ('" + name + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Customer getCustomerByID(int customerID){
        ResultSet rs;
        Customer customer = new Customer();
        try{
            rs = stat.executeQuery("SELECT * FROM CUSTOMER WHERE id = " + customerID);
            while(rs.next()){
                String name = rs.getString("name");
                int rented_car_id = rs.getInt("rented_car_id");
                customer.setId(customerID);
                customer.setName(name);
                customer.setRented_car_id(rented_car_id);
            }
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean rentCar(int customerID, Car car){ // renting a car for a customer
        try{
            int rowOfBoth;
             rowOfBoth = stat.executeUpdate("UPDATE CUSTOMER SET rented_car_id = " + car.getId() +
                    " WHERE id = " + customerID);
             rowOfBoth += stat.executeUpdate("UPDATE CAR SET isRented = TRUE WHERE id = " + car.getId());
             return rowOfBoth > 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean didCustomerRentCar(int customerID){
        ResultSet rs;
        Customer customer = new Customer();
        try{
            rs = stat.executeQuery("SELECT * FROM CUSTOMER WHERE id = " + customerID);
            while(rs.next()){
                int carID = rs.getInt("rented_car_id");
                customer.setRented_car_id(carID);
            }
            customer.setId(customerID);
            return customer.getRented_car_id() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String returnRentedCarOfCustomer(int customerID){
        try{
            stat.executeUpdate("UPDATE CUSTOMER SET rented_car_id = NULL WHERE id = " + customerID);
            return "You've returned a rented car!\n";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public Car getCarByID(int carID){
        ResultSet rs;
        Car car = new Car();
        try{
            rs = stat.executeQuery("SELECT * FROM CAR WHERE id = " + carID);
            while(rs.next()){
                String name = rs.getString("name");
                int idCompanyCar = rs.getInt("idCompanyCar");
                car.setName(name);
                car.setIdCompanyCar(idCompanyCar);
            }
            car.setId(carID);
            return car;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Car getCarOfCustomerByCustomerID(int customerID){
        ResultSet rs;
        Car car = new Car();
        try{
            rs = stat.executeQuery("SELECT * FROM CUSTOMER WHERE id = " + customerID);
            int carID = 0;
            while(rs.next()) {
                carID = rs.getInt("rented_car_id");
            }
            car.setId(carID);
            rs = stat.executeQuery("SELECT * FROM CAR WHERE id = " + carID);
            while(rs.next()) {
                car.setName(rs.getString("name"));
                car.setCompanyId(rs.getInt("company_id"));
            }
            return car;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Car> getCarsByCompanyID(int companyID) {
        ResultSet rs;
        List<Car> carList = new ArrayList<>();
        try{
            rs = stat.executeQuery("SELECT * FROM car WHERE company_id = " + companyID + " AND isRented = FALSE");
            while(rs.next()){
                int carID = rs.getInt("id");
                String carName = rs.getString("name");
                int orderOfCar = rs.getInt("idCompanyCar");
                Car car = new Car(carID, carName);
                car.setIdCompanyCar(orderOfCar); // set the order of car to be able to print according to that instead of car's id;
                car.setName(carName);
                car.setCompanyId(companyID);
                carList.add(car);
            }
            return carList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createCar(int companyID, String carName, List<Car> carOfCompany) {
        int orderOfCar ;
        if(carOfCompany == null){ // order of each car according to their respective company
            orderOfCar = 1; // if no car in the list of company then it is the first car
        }else {
            orderOfCar = carOfCompany.size() + 1; // otherwise, it is the next car in the list of company
        }
        try {
            return stat.execute("INSERT INTO car (name, company_id, idCompanyCar) " +
                    "VALUES('" + carName + "', " + companyID + ", " + orderOfCar+ ")");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
