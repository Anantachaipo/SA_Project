package ku.cs.service;

import ku.cs.controller.LoginControlller;
import ku.cs.model.Customer;
import ku.cs.model.CustomerList;

public class Account {

    public Customer checkLogin(String username, String password) {
        DataSource<CustomerList> dataSource;

        // TODO: ทำตัวอ่าน data
        dataSource = new CustomerDataSource();
        CustomerList customerList = dataSource.readData();
        for (Customer customer : customerList.getCustomers()) {
            if (customer.getUsername().equals(username)) {
                if (customer.getPassword().equals(password)) {
                    LoginControlller.user = customer;
                    return customer;
                }
            }
        }
        return null;
    }
    public boolean checkUsername(String username) {
        DataSource<CustomerList> dataSource;

        // TODO: ทำตัวอ่าน data
        dataSource = new CustomerDataSource();
        CustomerList customerList = dataSource.readData();

        // if found, return true, else return false
        return !customerList.searchByUsername(username);
    }

    public boolean checkPassword(String password, String conPassword) {
        // if password is the same return true, else return false
        return password.equals(conPassword);
    }
}
