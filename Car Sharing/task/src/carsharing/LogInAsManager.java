package carsharing;

import carsharing.DAO.DaoImplementation;
import carsharing.Domain.Car;
import carsharing.Domain.Company;

import java.util.List;
import java.util.Scanner;

public class LogInAsManager {
    private final DaoImplementation daoImplementation;
    private final MultipleMenu multipleMenu ;

    public LogInAsManager(DaoImplementation daoImplementation, MultipleMenu multipleMenu) {
        this.daoImplementation = daoImplementation;
        this.multipleMenu = multipleMenu;
    }

    public String chooseManagerMenu() {
        Scanner input = new Scanner(System.in);
        String answer = multipleMenu.managerMenu();

        while (!answer.equals("0. Back")) {
            if ("1. Company list".equals(answer)) {
                List<Company> companyList = daoImplementation.getAllCompany();

                if (companyList.isEmpty()) {
                    System.out.println("The company list is empty!\n");
                } else {
                    System.out.println("Choose the company:");
                    companyList.forEach(System.out::println);
                    System.out.println("0. Back");
                    int companyID = input.nextInt(); // requests a user to input the index of a company he/she wants to choose
                    String answer2 = multipleMenu.chooseCompany(companyID); //displays menu to choose a company

                    if (!answer2.equals("0. Back")) { // if a user chose particular company
                        System.out.println(daoImplementation.getCompanyByID(companyID).getName() + ":"); // displays the name of chosen company and
                        answer2 = multipleMenu.companyCarMenu(); // displays car selection menu

                        while (!answer2.equals("0. Back")) { //inner loop which promotes a user to make a choice in company's car selection
                            List<Car> carList;
                            if (answer2.equals("1. Car list")) {
                                carList = daoImplementation.getCarsByCompanyID(companyID); //gets list of cars according company's id
                                if (!carList.isEmpty()) {
                                    carList.forEach(System.out::println); //prints all cars in the list
                                    System.out.println();
                                } else {
                                    System.out.println("The car list is empty!\n");
                                }
                            }

                            if (answer2.equals("2. Create a car")) {
                                carList = daoImplementation.getCarsByCompanyID(companyID); // request a list of that company's cars
                                System.out.println("\nEnter the car name:");
                                System.out.println(multipleMenu.createdCar(companyID, carList)); //add the car according to respective company's id and order of a car
                            }
                            answer2 = multipleMenu.companyCarMenu();
                        }
                    }
                }
            }else if ("2. Create a company".equals(answer)) {
                System.out.println("Enter the company name:");
                System.out.println(multipleMenu.createdCompany());
            }
            answer = multipleMenu.managerMenu();
        }
        return String.copyValueOf(answer.toCharArray());
    }
}
