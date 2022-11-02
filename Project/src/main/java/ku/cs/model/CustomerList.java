package ku.cs.model;

import java.util.ArrayList;

public class CustomerList {

    private ArrayList<Customer> customers;

    public CustomerList() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public boolean searchByUsername(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username))
                return true;
        }
        return false;
    }
}
