package org.example;

import java.util.HashMap;

public class Customers {

    private HashMap<String, Customer> customers;
    private static Customers obj;

    private Customers(){
        customers = new HashMap<>();
    }

    public static Customers getInstance(){
        if (obj==null)
            obj = new Customers();
        return obj;
    }

    public Customer getCustomer(String customerID){

        if (customers.containsKey(customerID)){
            return customers.get(customerID);
        }

        return null;
    }

    public void addCustomer(String customerId, Customer customer){
        customers.put(customerId, customer);
    }

    public void displayAllCustomer(){
        System.out.println(customers.values());
    }
}
