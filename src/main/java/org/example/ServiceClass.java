package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ServiceClass {

    Map<Customer, HashSet<Account>> customerListHashMap;
    Map<Account, HashSet<Customer>> accountListHashMap;

    public ServiceClass() {
        this.customerListHashMap = new HashMap<>();
        this.accountListHashMap = new HashMap<>();
    }



}
