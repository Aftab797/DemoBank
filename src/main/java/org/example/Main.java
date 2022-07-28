package org.example;

import java.util.*;

public class Main {

    static Map<Customer, HashSet<Account>> customerListHashMap;
    static Map<Account, HashSet<Customer>> accountListHashMap;
    static double savingsInterest = 0.03f;
    static double lessThan1interest = 0.05f;
    static double lessThan5interest = 0.06f;
    static double greaterThan5interest = 0.065f;
    static ServiceClass serviceClass;

    public static void main(String[] args) {

        customerListHashMap = new HashMap<>();
        accountListHashMap = new HashMap<>();
        serviceClass = new ServiceClass();
        Scanner sc = new Scanner(System.in);

//        Customer aftab = createCustomer("Aftaab Shaikh", "9167689584", "aftabshaikh2014@gmail.com");
//
//        Account a1 = createAccount("Savings", 100.00, 0.03, aftab);
//
//        Account a2 = createAccount("Term Deposit", 00.00, 0.03, aftab);
//
//        Customer shubha = createCustomer("Shubha", "9167689584", "014@gmail.com");

//        linkAccountToUser("123456","8907" );


//        for (Customer c : customerListHashMap.keySet()){
//            System.out.println(c.getCustomerDetails());
//            System.out.println(customerListHashMap.get(c)+"\n");
//        }

//        for (Account c : accountListHashMap.keySet()){
//            System.out.println(c.getAccountNo());
//            System.out.println(accountListHashMap.get(c)+"\n");
//        }

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


        String customerId =  generateUniqueCustomerID(customerListHashMap.keySet());
        Customer c1 = new Customer(name, phoneNum, email, customerId);
        customerListHashMap.putIfAbsent(c1, new HashSet<>());

        System.out.printf("\n************ CUSTOMER CREATED with name: %s ID %s ************\n\n", name, customerId );

        return c1;
    }

    private static Account createAccount(Scanner sc) {
        String choice;
        String accountType =null;
        double balance;
        double interest = savingsInterest;
        Customer customer;
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
            customer = getCustomerObject(sc.nextLine());

            if (customer == null){
                System.out.println("Customer do not exits. Please enter Customer id again");
            }

        }while (customer == null);

        String accountNo = generateUniqueAccountID(accountListHashMap.keySet());

        if (accountType.compareTo("Savings") == 0){

            Savings savings = new Savings(accountNo, balance, interest, accountType);
            HashSet<Customer> c = new HashSet<>();
            c.add(customer);
            accountListHashMap.putIfAbsent(savings,c);


            HashSet<Account> a = customerListHashMap.get(customer);

            a.add(savings);

            customerListHashMap.put(customer, a);

            System.out.printf("\n************ Savings Account created for %s, Account No: %s ************\n\n", customer.getCustomerId(), accountNo);

            return savings;

        }else if(accountType.compareTo("Term Deposit") == 0){

            TermDeposit termDeposit = new TermDeposit(accountNo, balance, interest, term, accountType);

            HashSet<Customer> c = new HashSet<>();
            c.add(customer);
            accountListHashMap.putIfAbsent(termDeposit,c);
            HashSet<Account> a = customerListHashMap.get(customer);
            a.add(termDeposit);
            customerListHashMap.put(customer, a);

            System.out.printf("\n************ Term Deposit created for %s, Account No: %s for a period of %d years ************\n\n", customer.getCustomerId(), accountNo, term);

            return termDeposit;
        }
        return null;
    }

    public static void deposit(Scanner sc){

        double amount;
        Account account = null;
        do{
            System.out.print("Enter account number where you want to deposit: ");
            account = getAccountObject(sc.nextLine());

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
            account = getAccountObject(sc.nextLine());

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

        Customer customer;
        do{
            System.out.print("Enter customer id for which you want details: ");
            customer = getCustomerObject(sc.nextLine());

            if (customer == null){
                System.out.println("Customer do not exits. Please enter Customer id again");
            }

        }while (customer == null);

        System.out.println("\n"+customer);
        Set<Account> accounts = customerListHashMap.get(customer);
        for (Account account: accounts){
            System.out.println(account);
        }
    }

    private static void readAccountDetails(Scanner sc) {
        Account account;
        do{
            System.out.print("Enter Account number for which you want details: ");
            account = getAccountObject(sc.nextLine());

            if (account == null){
                System.out.println("Account do not exits. Please enter Account number again");
            }

        }while (account == null);

        System.out.println(account);
        Set<Customer> customers = accountListHashMap.get(account);
        for (Customer customer: customers){
            System.out.println(customer);
        }
    }

    private static void linkAccountToUser(Scanner sc ) {

        Account account;
        Customer customer;

        do{
            System.out.printf("Enter Account Number: ");
            account = getAccountObject(sc.nextLine());
            if (account == null) {
                System.out.println("******* This account does not exists *******");
                return;
            }
        }while (account == null);

        do{
            System.out.printf("Enter Customer Id: ");
            customer = getCustomerObject(sc.nextLine());

            Set<Account> allAccounts = customerListHashMap.get(customer);
            if (allAccounts.contains(account)){
                System.out.println("******** User is already linked with that account ********");
                return;
            }

            if (customer == null) {
                System.out.println("******* User does not exits *******");
                return;
            }
        }while (customer == null);


        HashSet<Account> accountList = customerListHashMap.get(customer);
        accountList.add(account);

        HashSet<Customer> customerList = accountListHashMap.get(account);
        customerList.add(customer);

        System.out.println("\n Account Linked Successfully \n");

    }

    private static String generateUniqueCustomerID(Set<Customer> customers) {

        String customerID;

        Random rng = new Random();
        int len = 6;
        boolean unique;

        //Loop till we get unique id
        do{
            customerID = "";
            for (int c=0; c<len; c++){
                customerID += ((Integer) rng.nextInt(10)).toString();
            }

            unique = false;

            for( Customer u : customers){
                if (customerID.compareTo(u.getCustomerId()) == 0){
                    unique = true;
                    break;
                }
            }

        }while (unique);

        return customerID;

    }

    private static String generateUniqueAccountID(Set<Account> accounts) {

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

            for( Account u : accounts){
                if (accountNo.compareTo(u.getAccountNo()) == 0){
                    unique = true;
                    break;
                }
            }

        }while (unique);

        return accountNo;

    }



    private static void addInterest(){

        for(Account a: accountListHashMap.keySet()){
            a.addInterest();
        }

    }

    private static void updateDetails(Scanner sc){
        Customer customer;
        do{

            System.out.print("Enter customer id for which you have to update details: ");
            String customerId = sc.nextLine();
            customer = getCustomerObject(customerId);
        }while (customer == null);
        String choice;
        do{
            System.out.println("\nChoose what you have to update\n");

            System.out.println("1) Name");
            System.out.println("2) Email\n");
            System.out.println("3) Phone Number");
            System.out.println("4) Quit");
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

    public static Customer getCustomerObject(String customerId){

        for (Customer customer: customerListHashMap.keySet()){
            if (customer.getCustomerId().compareTo(customerId) == 0){
                return customer;
            }
        }
        return null;
    }

    public static Account getAccountObject(String accountNo){

        for (Account account: accountListHashMap.keySet()){
            if (account.getAccountNo().compareTo(accountNo) == 0){
                return account;
            }
        }
        return null;
    }
}