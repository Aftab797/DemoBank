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

    public Account(String accountNo, double balance, double interest, String type) {
        this.accountNo = accountNo;
        this.balance = balance;
        this.interest = interest;
        this.timestamp = new Date();
        this.type = type;
    }

    public Account(String accountNo, double balance, double interest,String type, float term) {
        this.accountNo = accountNo;
        this.balance = balance;
        this.interest = interest;
        this.timestamp = new Date();
        this.term = term;
        this.type = type;
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
        long duration = this.timestamp.getTime() - new Date().getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
        if (diffInDays < 365){
            double curBalance = this.balance;
            this.balance += curBalance * interest;
            System.out.println("Account type "+this.type+" Current Balance " + curBalance + " Interest "+interest+" Updated balance of Account No "+ this.accountNo+ " is ₹"+ this.balance);
        }else {
            System.out.println("Cannot add interest");
        }

    }
}
