package carsharing;

import carsharing.DAO.DaoImplementation;
import carsharing.DatabaseConnection.ConnectionDB;
import org.h2.tools.DeleteDbFiles;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        DaoImplementation daoImplementation = new DaoImplementation();
        MultipleMenu multipleMenu = new MultipleMenu(daoImplementation);
        LogInAsManager logInAsManager = new LogInAsManager(daoImplementation, multipleMenu);
        LogInAsCustomer logInAsCustomer = new LogInAsCustomer(daoImplementation, multipleMenu);

        DeleteDbFiles.execute("C:/Users/Rahma/IdeaProjects/Car Sharing/Car Sharing/" +
                "task/src/carsharing/db/", "carsharing", true); //deletes the existing database file in the path
        Statement stat = ConnectionDB.getConnection().createStatement();

        stat.execute("CREATE TABLE IF NOT EXISTS COMPANY (id INT AUTO_INCREMENT, " +
                "name VARCHAR(25) NOT NULL, CONSTRAINT pk_keyCompany PRIMARY KEY(id), " +
                "CONSTRAINT uk UNIQUE KEY(name)) ");  //create a table 'company'

        stat.execute("CREATE TABLE IF NOT EXISTS CAR (id INT AUTO_INCREMENT, name VARCHAR(25) NOT NULL, idCompanyCar INT," +
                "company_id INT NOT NULL, isRented BOOLEAN DEFAULT FALSE,  CONSTRAINT pk_keyCar PRIMARY KEY(id), CONSTRAINT uk_key UNIQUE KEY(name)," +
                "CONSTRAINT fk_keyOfCar FOREIGN KEY(company_id) REFERENCES COMPANY (id))");  //create a table 'car'

        stat.execute("ALTER TABLE company ALTER COLUMN id RESTART WITH 1"); //when a new test is run, id will begin from 1

        stat.execute("CREATE TABLE IF NOT EXISTS CUSTOMER (id INT AUTO_INCREMENT, name VARCHAR(25) UNIQUE NOT NULL, " +
                "rented_car_id INT NULL, CONSTRAINT pk_keyCustomer PRIMARY KEY(id), " +
                "CONSTRAINT fk_keyOfCustomer FOREIGN KEY(rented_car_id) REFERENCES CAR (id))"); //creates a table 'customer'




        String answer = multipleMenu.mainMenu(); // displays a main menu and requests an input for selection


        while(!answer.equals("0. Exit")) {
            if (answer.equals("1. Log in as a manager")) {
                answer = logInAsManager.chooseManagerMenu();
                if(answer.equals("0. Back")){
                    answer = multipleMenu.mainMenu();
                }
            }else if (answer.equals("2. Log in as a customer") || answer.equals("3. Create a customer")) {
                answer = logInAsCustomer.chooseCustomerMenu(answer);
                if(!answer.equals("0. Back")){ // any answer, besides '0. Back', from other methods must be printed on console
                    System.out.println(answer);
                }
                if (answer.equals("0. Back") || answer.equals("The customer was added!\n")
                        || answer.equals("The customer list is empty!\n")) {
                    answer = multipleMenu.mainMenu();
                }
            }
        }
        System.exit(0);
    }
}
