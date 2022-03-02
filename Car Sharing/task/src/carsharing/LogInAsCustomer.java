package carsharing;

import carsharing.DAO.DaoImplementation;
import carsharing.Domain.Company;
import carsharing.Domain.Customer;

import java.util.List;
import java.util.Scanner;

public class LogInAsCustomer {
    private final DaoImplementation daoImplementation;
    private final MultipleMenu multipleMenu;

    public LogInAsCustomer(DaoImplementation daoImplementation, MultipleMenu multipleMenu) {
        this.daoImplementation = daoImplementation;
        this.multipleMenu = multipleMenu;
    }

    public String chooseCustomerMenu(String answer) {

        Scanner input = new Scanner(System.in);
        int customerID;

        if(answer.equals("2. Log in as a customer")){
            List<Customer> customerList = daoImplementation.getAllCustomers();
            if(customerList.isEmpty()){
                answer = "The customer list is empty!\n";
            }else{
                customerList.forEach(System.out::println);
                System.out.println("0. Back");
                customerID = input.nextInt();
                if(customerID != 0){
                    int temp = customerID - 1;
                    answer = multipleMenu.rentMenu();
                    while(!answer.equals("0. Back")) {
                        if (answer.equals("1. Rent a car")) {
                            List<Company> companyList = daoImplementation.getAllCompany();
                            if(!companyList.isEmpty()){
                                answer = multipleMenu.choosingCompany(customerID, companyList);
                                while(!answer.startsWith("show cars by index")) {
                                    if (answer.startsWith("No available cars in the")) {
                                        System.out.println(answer);
                                        answer = multipleMenu.choosingCompany(customerID, companyList);
                                    }else if(answer.equals("0. Back") || answer.equals("You've already rented a car!\n")){
                                        break;
                                    }
                                }
                            }else{
                                System.out.println("The company list is empty!");
                            }

                            if(answer.equals("You've already rented a car!\n")) {
                                System.out.println(answer);
                            }else if(answer.startsWith("show cars by index")){
                                answer = multipleMenu.rentCarForCustomer(customerID, answer);

                                if(answer.startsWith("You rented")){
                                    System.out.println(answer);
                                }
                            }
                            answer = multipleMenu.rentMenu();
                        }
                        if (answer.equals("2. Return a rented car") || answer.equals("3. My rented car")) {
                            answer = multipleMenu.rentedCarOfCustomer(customerList.get(temp).getId(), answer);
                            System.out.println(answer);
                            answer = multipleMenu.rentMenu();
                        }
                    }
                    return answer;
                }else{
                    return "0. Back";
                }
            }
        }
        if(answer.equals("3. Create a customer")){
            answer = multipleMenu.createCustomer();
            return answer;
        }
        return answer;
    }
}
