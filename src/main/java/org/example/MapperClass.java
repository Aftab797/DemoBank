package org.example;

import java.util.*;

public class MapperClass {

    private Map<String, ArrayList<String>> customerId_to_AccountMap;

    private Map<String, ArrayList<String>> accountNo_to_customerIdMap;

    private static MapperClass obj;

    private MapperClass(){
        customerId_to_AccountMap = new HashMap<>();
        accountNo_to_customerIdMap = new HashMap<>();
    }

    public static MapperClass getInstance(){
        if (obj == null){
            obj = new MapperClass();
        }
        return obj;
    }

    public ArrayList<String> getAccountList(String customerId){

        if(customerId_to_AccountMap.containsKey(customerId)){
            return customerId_to_AccountMap.get(customerId);
        }
        return null;
    }

    public ArrayList<String> getCustomerList(String accountNo){

        if(accountNo_to_customerIdMap.containsKey(accountNo)){
            return accountNo_to_customerIdMap.get(accountNo);
        }
        return null;
    }

    public void addCustomerToAccountNo(String accountNo, String customerId){

        if (accountNo_to_customerIdMap.containsKey(accountNo)) {
            accountNo_to_customerIdMap.get(accountNo).add(customerId);
        }else {
            ArrayList<String> account = new ArrayList<>();
            account.add(customerId);
            accountNo_to_customerIdMap.put(accountNo,account);
        }
    }

    public Set<String> getAllAccountNo(){
        return accountNo_to_customerIdMap.keySet();
    }

    public Set<String> getAllCustomerId(){
        return customerId_to_AccountMap.keySet();
    }

    public void addAccountToCustomerId(String customerId, String accountNo){

        if (customerId_to_AccountMap.containsKey(customerId)){
            customerId_to_AccountMap.get(customerId).add(accountNo);
        }else {
            customerId_to_AccountMap.put(customerId, new ArrayList<>());
        }
    }

    public Boolean ifAccountExist(String accountNo){

        if (accountNo_to_customerIdMap.containsKey(accountNo)){
            return true;
        }else {
            return false;
        }
    }

    public Boolean ifCustomerExist(String customerId){
        if (customerId_to_AccountMap.containsKey(customerId)){
            return true;
        }else {
            return false;
        }
    }

    public String generateUniqueCustomerID() {

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

            if (customerId_to_AccountMap.containsKey(customerID)){
                unique = true;
                break;

            }

        }while (unique);

        return customerID;

    }
}
