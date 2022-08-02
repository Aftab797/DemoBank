package org.example;


import java.util.Date;
import java.util.concurrent.TimeUnit;

abstract class Account {

    private String accountNo;
    private double balance;
    private Date timestamp;
    private double interest;
    private float term;
    private String type;

    public Account(String accountNo, double balance, double interest, String type, String customerId) {
        this.accountNo = accountNo;
        this.balance = balance;
        this.interest = interest;
        this.timestamp = new Date();
        this.type = type;

        Accounts.getInstance().addAccount(accountNo, this);
        MapperClass.getInstance().addCustomerToAccountNo(accountNo, customerId);
        MapperClass.getInstance().addAccountToCustomerId(customerId, accountNo);

    }

    public Account(String accountNo, double balance, double interest,String type, float term, String customerId) {
        this.accountNo = accountNo;
        this.balance = balance;
        this.interest = interest;
        this.timestamp = new Date();
        this.term = term;
        this.type = type;

        Accounts.getInstance().addAccount(accountNo, this);
        MapperClass.getInstance().addCustomerToAccountNo(accountNo, customerId);
        MapperClass.getInstance().addAccountToCustomerId(customerId, accountNo);
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String toString(){
        if (this.type.compareTo("Savings") == 0) {
            return String.format("Account No: %s, Balance: %.02f, Type: %s, Created on: %s.", this.accountNo, this.balance,this.type, this.timestamp.toString());
        }else {
            return String.format("Account No: %s, Balance: %.02f, Type: %s, Term: %.01f years, Created on: %s.", this.accountNo, this.balance, this.type, this.term, this.timestamp.toString());
        }
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public float getTerm() {
        return term;
    }

    public String getTimestamp() {
        return timestamp.toString();
    }

    public void withdrawBalance(double amount) {
        this.balance -= amount;
    }

    public String getType() {
        return type;
    }

    public String getBalance() {
        return String.format("Updated balance is ₹%.02f", this.balance);
    }

    public double showBalance() {
        return this.balance;
    }

    public void addInterest() {
        long duration =  new Date().getTime() - this.timestamp.getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
        if (diffInDays > 365){
            double curBalance = this.balance;
            this.balance += curBalance * interest;
            System.out.printf("Account type %s, Current Balance %.02f, Interest %.01f%%, Updated balance of Account No %s is ₹%.02f", this.type, curBalance, interest*100, this.accountNo,this.balance);
        }else {
            System.out.println("Cannot add interest");
        }

    }
}
