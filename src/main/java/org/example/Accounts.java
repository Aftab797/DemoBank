package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class Accounts {

    private HashMap<String, Account> accounts;
    private static Accounts obj;

    private Accounts() {
        this.accounts = new HashMap<>();
    }

    public static Accounts getInstance(){
        if (obj == null){
            obj = new Accounts();
        }
        return obj;
    }

    public Account getAccount(String accountNumber){

        if (accounts.containsKey(accountNumber)){
            return accounts.get(accountNumber);
        }
        return null;
    }

    public void displayAllAccounts(){
        System.out.println(accounts.values());
    }

    public void addAccount(String accountNo, Account account){

        accounts.put(accountNo, account);

    }
}
