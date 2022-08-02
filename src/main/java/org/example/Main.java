package org.example;

import java.util.*;

public class Main {

    static double savingsInterest = 0.03f;
    static double lessThan1interest = 0.05f;
    static double lessThan5interest = 0.06f;
    static double greaterThan5interest = 0.065f;

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);

        do{

            mainMenuPrompt(sc);

        }while (true);

    }

    private static void mainMenuPrompt(Scanner sc) {

        System.out.println("\n\nWhat would you like to do today\n");

        System.out.println("1) Create Customer");
        System.out.println("2) Create Account");
        System.out.println("3) Deposit Funds");
        System.out.println("4) Withdraw Funds");
        System.out.println("5) Read Customer Details");
        System.out.println("6) Read Account Details");
        System.out.println("7) Link user with different account");
        System.out.println("8) Add Interest in all account");
        System.out.println("9) Update User details\n");

        int choice = 0;

        do {
            System.out.print("Enter your choice: ");
            try {
                choice = sc.nextInt();

                if (choice<1 || choice>9){
                    System.out.println("Please provide a valid Input\n");
                }

            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Please provide a valid Input\n");

            }
        }while (choice<1 || choice>9);

        switchMethods(choice, sc);
    }

    private static void switchMethods(int choice, Scanner sc) {
        sc.nextLine();
        switch (choice){
            case 1:
                createCustomer(sc);
                break;
            case 2:
                createAccount(sc);
                break;
            case 3:
                deposit(sc);
                break;
            case 4:
                withdraw(sc);
                break;
            case 5:
                readCustomerDetails(sc);
                break;
            case 6:
                readAccountDetails(sc);
                break;
            case 7:
                linkAccountToUser(sc);
                break;
            case 8:
                addInterest();
                break;
            case 9:
                updateDetails(sc);
                break;
        }

    }

    private static Customer createCustomer(Scanner sc) {

        String name;
        String phoneNum;
        String email;

        System.out.print("Enter your name: ");
        name = sc.nextLine();

        do{
            System.out.print("Enter your phone number: ");
            phoneNum = sc.nextLine();

            if (phoneNum.length() != 10){
                System.out.println("******* Please Enter a valid Phone Number *******");
            }

        }while (phoneNum.length() != 10);

        System.out.print("Enter your email Id: ");
        email = sc.nextLine();



        Customer c1 = new Customer(name, phoneNum, email);


        return c1;
    }


    private static Account createAccount(Scanner sc) {
        String choice;
        String accountType =null;
        double balance;
        double interest = savingsInterest;
        String customerId;
        int term= 0;

        do {

            System.out.println("\nSelect the type of account you want to create\n");

            System.out.println("1) Savings Account");
            System.out.println("2) Term Deposit Account\n");

            System.out.print("Enter your choice: ");
            choice = sc.nextLine();
            if (choice.compareTo("1") == 0){
                accountType = "Savings";
            }else if (choice.compareTo("2") == 0){
                accountType = "Term Deposit";
            }else {
                System.out.println("Please Enter valid choice!!");
            }

        }while (accountType == null);

        System.out.print("Enter initial deposit in ₹: ");
        balance = sc.nextDouble();
        sc.nextLine();
        do {
            if (accountType.compareTo("Term Deposit") == 0) {
                interest = 0;
                System.out.print("Enter number of years: ");
                term = sc.nextInt();
                sc.nextLine();
                if (0 < term && term <= 1) {
                    interest = lessThan1interest;
                } else if (1 < term && term <= 5) {
                    interest = lessThan5interest;
                } else if (term >= 6) {
                    interest = greaterThan5interest;
                }
            }
        }while (interest == 0 );

        do{
            System.out.print("Enter customer id, who will be the owner of this account: ");
            customerId = sc.nextLine();

            if (!MapperClass.getInstance().ifCustomerExist(customerId)){
                System.out.println("Customer do not exits. Please enter Customer id again");
            }

        }while (!MapperClass.getInstance().ifCustomerExist(customerId));

        String accountNo = generateUniqueAccountID(MapperClass.getInstance().getAllAccountNo());

        if (accountType.compareTo("Savings") == 0){

            Savings savings = new Savings(accountNo, balance, interest, accountType, customerId);

            System.out.printf("\n************ Savings Account created for %s, Account No: %s ************\n\n", customerId, accountNo);

            return savings;

        }else if(accountType.compareTo("Term Deposit") == 0){

            TermDeposit termDeposit = new TermDeposit(accountNo, balance, interest, term, accountType, customerId);

            System.out.printf("\n************ Term Deposit created for %s, Account No: %s for a period of %d years ************\n\n", customerId, accountNo, term);

            return termDeposit;
        }
        return null;
    }

    public static void deposit(Scanner sc){

        double amount;
        Account account = null;
        do{
            System.out.print("Enter account number where you want to deposit: ");
            account = Accounts.getInstance().getAccount(sc.nextLine());

            if (account == null){
                System.out.println("Account do not exits. Please enter valid Account number ");
            }

        }while (account == null);

        if(account.getType().compareTo("Savings") == 0){
            System.out.print("Enter amount in ₹: ");
            amount = sc.nextDouble();
            account.addBalance(amount);
            System.out.printf("\nAmount of ₹%.02f deposited successfully in account no %s\n", amount, account.getAccountNo());
            System.out.println(account.getBalance()+"\n\n");

        }else {
            System.out.println("\n****** You cannot deposit into Term Deposit Account ******\n");
        }

    }

    private static void withdraw(Scanner sc) {

        double amount;
        Account account;
        do{
            System.out.print("Enter account number where you want to withdraw from: ");
            account = Accounts.getInstance().getAccount(sc.nextLine());

            if (account == null){
                System.out.println("Account do not exits. Please enter valid Account number ");
            }

        }while (account == null);

        if(account.getType().compareTo("Savings") == 0){

            do {
                System.out.print("Enter amount in ₹: ");
                amount = sc.nextDouble();
                if (amount < 0 || amount > account.showBalance() ){
                    System.out.println("\nAmount cannot be negative or greater than your balance\n");

                }else {
                    account.withdrawBalance(amount);
                    System.out.printf("\n\nAmount of ₹%.02f withdrawn successfully from account no %s\n\n", amount, account.getAccountNo());
                    System.out.println(account.getBalance());
                }

            }while (amount < 0 || amount > account.showBalance());

        }else {
            System.out.printf("\n\n****** You cannot withdraw from Term Deposit Account before %.02f years from date of creation i.e. %s ******\n\n", account.getTerm(), account.getTimestamp() );
        }

    }

    private static void readCustomerDetails(Scanner sc) {

        String customerId;
        do{
            System.out.print("Enter customer id for which you want details: ");
            customerId = sc.nextLine();

            if (!MapperClass.getInstance().ifCustomerExist(customerId)){
                System.out.println("Customer do not exits. Please enter Customer id again");
            }

        }while (!MapperClass.getInstance().ifCustomerExist(customerId));

        System.out.println(Customers.getInstance().getCustomer(customerId));
        for (String a: MapperClass.getInstance().getAccountList(customerId)){
            System.out.println(Accounts.getInstance().getAccount(a));
        }

    }

    private static void readAccountDetails(Scanner sc) {
        String accountNo;
        do{
            System.out.print("Enter Account number for which you want details: ");
            accountNo = sc.nextLine();

            if (!MapperClass.getInstance().ifAccountExist(accountNo)){
                System.out.println("Account do not exits. Please enter Account number again");
            }

        }while (!MapperClass.getInstance().ifAccountExist(accountNo));

        System.out.println(Accounts.getInstance().getAccount(accountNo));
        for (String a: MapperClass.getInstance().getAllCustomerId()){
            System.out.println(Customers.getInstance().getCustomer(a));
        }
    }

    private static void linkAccountToUser(Scanner sc ) {

        String accountNo;
        String customerId;

        do{
            System.out.printf("Enter Account Number: ");
            accountNo = sc.nextLine();
            if (!MapperClass.getInstance().ifAccountExist(accountNo)) {
                System.out.println("******* This account does not exists *******");
            }
        }while (!MapperClass.getInstance().ifAccountExist(accountNo));

        do{
            System.out.printf("Enter Customer Id: ");
            customerId = sc.nextLine();

            if (!MapperClass.getInstance().ifCustomerExist(customerId)){
                System.out.println("******* User Does not Exist *******");
                continue;
            }

            if (MapperClass.getInstance().getAccountList(customerId).contains(accountNo)){
                System.out.println("******** User is already linked with that account ********");
                return;
            }

            if (!MapperClass.getInstance().ifCustomerExist(customerId)) {
                System.out.println("******* User does not exits *******");
                continue;
            }
        }while (!MapperClass.getInstance().ifCustomerExist(customerId));


        MapperClass.getInstance().addCustomerToAccountNo(accountNo, customerId);
        MapperClass.getInstance().addAccountToCustomerId(customerId, accountNo);

        System.out.println("\n Account Linked Successfully \n");

    }

    private static void addInterest(){

        for(String a: MapperClass.getInstance().getAllAccountNo()){
            Accounts.getInstance().getAccount(a).addInterest();
        }

    }

    private static void updateDetails(Scanner sc){
        Customer customer;
        do{

            System.out.print("Enter customer id for which you have to update details: ");
            String customerId = sc.nextLine();
            customer = Customers.getInstance().getCustomer(customerId);

            if (customer == null){
                System.out.println("User does not exits");
            }

        }while (customer == null);
        String choice;
        do{
            System.out.println("\nChoose what you have to update\n");

            System.out.println("1) Name");
            System.out.println("2) Email");
            System.out.println("3) Phone Number");
            System.out.println("4) Quit\n");
            System.out.print("Enter your choice (1-4): ");
            choice = sc.nextLine();

            if (choice.compareTo("1") == 0){
                System.out.print("Enter name to be updated: ");
                customer.updateName(sc.nextLine());

                System.out.println("******** Name updated Successfully ********");
                System.out.println(customer+"\n");

            }else if (choice.compareTo("2") == 0){
                System.out.print("Enter email to be updated");
                customer.updateEmail(sc.nextLine());

                System.out.println("******** Email updated Successfully ********");
                System.out.println(customer+"\n");
            }else if (choice.compareTo("3") == 0){
                String phoneNum;
                do{
                    System.out.print("Enter your phone number: ");
                    phoneNum = sc.nextLine();

                    if (phoneNum.length() != 10){
                        System.out.println("******* Please Enter a valid Phone Number *******");
                    }

                }while (phoneNum.length() != 10);
                customer.updateNumber(phoneNum);

                System.out.println("******** Phone Number updated Successfully ********");
                System.out.println(customer+"\n");
            }else {
                System.out.println("Please Enter valid choice!!");
            }

        }while (choice.compareTo("4") != 0);

    }

    private static String generateUniqueAccountID(Set<String> accounts) {

        String accountNo;

        Random rng = new Random();
        int len = 10;
        boolean unique;

        //Loop till we get unique id
        do{
            accountNo = "";
            for (int c=0; c<len; c++){
                accountNo += ((Integer) rng.nextInt(10)).toString();
            }

            unique = false;

            for( String a : accounts){
                if (accountNo.compareTo(a) == 0){
                    unique = true;
                    break;
                }
            }

        }while (unique);

        return accountNo;

    }

}