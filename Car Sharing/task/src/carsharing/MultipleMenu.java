package carsharing;

import carsharing.DAO.DaoImplementation;
import carsharing.Domain.Car;
import carsharing.Domain.Company;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MultipleMenu {
    private final DaoImplementation daoImplementation;
    private static final String[] choicesForMainMenu = {"1. Log in as a manager", "2. Log in as a customer",
            "3. Create a customer", "0. Exit"};
    private static final String[] choicesForManagerMenu = {"1. Company list", "2. Create a company", "0. Back"};
    private static final String[] choicesForCompanyCarMenu = {"1. Car list", "2. Create a car", "0. Back"};
    private static final String[] choicesForRentMenu = {"1. Rent a car", "2. Return a rented car",
            "3. My rented car", "0. Back"};

    public MultipleMenu(DaoImplementation daoImplementation) {
        this.daoImplementation = daoImplementation;
    }

    public String mainMenu() {
        Scanner input = new Scanner(System.in);
        Arrays.stream(choicesForMainMenu).forEach(System.out::println);

        int choice = input.nextInt();
        int temp = choice - 1;
        switch (choice) {
            case 1:
            case 2:
            case 3:
                return choicesForMainMenu[temp];
            case 0:
                return choicesForMainMenu[3];
        }
        return null;
    }

    public String managerMenu() {
        Scanner input = new Scanner(System.in);
        Arrays.stream(choicesForManagerMenu).forEach(System.out::println);

        int choice = input.nextInt();
        int temp = choice - 1;

        switch (choice) {
            case 1:
            case 2:
                return choicesForManagerMenu[temp];
            case 0:
                return choicesForManagerMenu[2];
        }
        return null;
    }

    public String createdCompany() {
        Scanner input = new Scanner(System.in);
        String companyName = input.nextLine();
        boolean check = daoImplementation.createCompany(companyName);
        return !check ? "The company was created!\n" : "Unsuccessful!";
    }

    public String chooseCompany(int choice) {
        return choice != 0 ? "Continue" : "0. Back";
    }

    public String createCustomer() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the customer name:");
        String name = input.nextLine();
        boolean check = daoImplementation.createCustomer(name);
        return !check ? "The customer was added!\n" : "Unsuccessful!\n";
    }

    public String companyCarMenu() {
        Scanner input = new Scanner(System.in);
        Arrays.stream(choicesForCompanyCarMenu).forEach(System.out::println);
        int choice = input.nextInt();
        int temp = choice - 1;
        switch (choice) {
            case 1:
            case 2:
                return choicesForCompanyCarMenu[temp];
            case 0:
                return choicesForCompanyCarMenu[2];
        }
        return null;
    }

    public String createdCar(int companyID, List<Car> companyCar) {
        Scanner input = new Scanner(System.in);
        String carName = input.nextLine();
        boolean check = daoImplementation.createCar(companyID, carName, companyCar);
        return !check ? "The car was added!\n" : "Unsuccessful!";
    }

    public String rentMenu() {
        Scanner input = new Scanner(System.in);
        Arrays.stream(choicesForRentMenu).forEach(System.out::println);
        int choice = input.nextInt();
        int temp = choice - 1;
        switch (choice) {
            case 1:
            case 2:
            case 3:
                return choicesForRentMenu[temp];
            case 0:
                return choicesForRentMenu[choicesForRentMenu.length - 1];
        }
        return null;
    }

    public String choosingCompany(int customerID, List<Company> companyList) { // rents a car for a customer in existing list of company list
        Scanner input = new Scanner(System.in);
        boolean didRentCar = daoImplementation.didCustomerRentCar(customerID);

        if (!didRentCar) { // if there is no rented car of his/her

            System.out.println("Choose a company:");
            companyList.forEach(System.out::println);
            System.out.println("0. Back");

            int choice = input.nextInt(); //requests an input from user to select a company by their index
            List<Car> carListByChoice = daoImplementation.getCarsByCompanyID(choice); // retrieves cars respective to their company id

            if (choice != 0) {

                if (carListByChoice.isEmpty()) { // if the car list is empty
                    return "No available cars in the " + daoImplementation.getCompanyByID(choice).getName() + " company\n";
                } else{
                    return "show cars by index of " + choice;
                }
            } else {
                return "0. Back";
            }

        } else {
            return "You've already rented a car!\n";// if rented car found, print this message
        }
    }

    public String rentCarForCustomer(int customerID, String messageFromChoosingCompany){
        Scanner input = new Scanner(System.in);
        int indexOfCarChoice = Integer.parseInt(messageFromChoosingCompany.replaceAll("[^0-9]", ""));
        List<Car> carListChoice = daoImplementation.getCarsByCompanyID(indexOfCarChoice);

        System.out.println("Choose a car:");
        carListChoice.forEach(System.out::println);
        System.out.println("0. Back");

        indexOfCarChoice = input.nextInt(); // requests a customer to select a car by their index
        int temp = indexOfCarChoice - 1;
        if (indexOfCarChoice != 0) {
            String carName = carListChoice.get(temp).getName();
            return daoImplementation.rentCar(customerID, carListChoice.get(temp)) ?
                    "You rented '" + carName + "'\n" : "Unsuccessful!\n";
        }else{
            return "0. Back";
        }
    }

    public String rentedCarOfCustomer(int customerID, String choice){
        boolean check = daoImplementation.didCustomerRentCar(customerID);
        if(choice.equals(choicesForRentMenu[1])){

            if(check){
                return daoImplementation.returnRentedCarOfCustomer(customerID);
            }else{
                return "You didn't rent a car!\n";
            }
        }else if( choice.equals(choicesForRentMenu[2])){

            if(check){
                System.out.println("Your rented car:");
                Car car = daoImplementation.getCarOfCustomerByCustomerID(customerID);
                System.out.println(car.getName());
                System.out.println("Company:");

                return daoImplementation.getCompanyByCar(car).getName() + "\n";

            }else{
                return "You didn't rent a car!\n";
            }
        }
        return null;
    }
}