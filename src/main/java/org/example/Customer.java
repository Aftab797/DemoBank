package org.example;

public class Customer {

    private String name;
    private String customerId;
    private String phoneNo;
    private String email;


    public Customer(String name, String phoneNo, String email, String customerId) {

        this.name = name;
        this.phoneNo = phoneNo;
        this.email = email;
        this.customerId = customerId;

    }

    public String getCustomerId() {
        return customerId;
    }



    public String toString(){
        return String.format("Customer Id: %s, Account Holder Name: %s, Email: %s, Phone Number: %s\n",this.customerId, this.name, this.email, this.phoneNo);
    }


    public void updateName(String name) {
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateNumber(String phoneNum) {
        this.phoneNo = phoneNum;
    }
}
