package com.danko.rest.Services;

import com.danko.rest.beans.Account;

import javax.enterprise.context.ApplicationScoped;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ApplicationScoped
public class BankService {

    private Map<Integer, Account> accountMap = new LinkedHashMap<>(){{
        put(0, new Account("Danko", "123414", "123", 0));
        put(1, new Account("First","880005553535","first1",100500));

    }};

    public Map<Integer, Account> addAccout(String clientName, String clientPhoneNumber,
                                           String clientPassportNumber, int clientMoneyNumber){
        Integer last = 0;
        for (Map.Entry<Integer, Account> entry : accountMap.entrySet()) {
            last = entry.getKey();
        }
        this.accountMap.put(last.intValue() + 1, new Account(clientName, clientPhoneNumber, clientPassportNumber, clientMoneyNumber));
        return this.accountMap;

    }

    public Map<Integer, Account> getAllAccounts(){
        return this.accountMap;
    }

    public Map<Integer, Account> deleteSelectAccounts(String clientName,
                                                      String clientPassportNumber){
        Integer s = search( clientName, clientPassportNumber);
        if (s != null) {
            this.accountMap.remove(s);
            return this.accountMap;
        } else
            return null;
    }

    public Integer search(String clientName,
                           String clientPassportNumber){
        Integer equals = 0;
        for (Map.Entry<Integer, Account> entry: accountMap.entrySet()){
            if (entry.getValue().getClientName().equals(clientName) &&
                entry.getValue().getClientPassportNumber().equals(clientPassportNumber)){
                equals = entry.getKey();
                return equals;
            }
        }
        return null;

    }

    public Map<Integer, Account> addMoney(Integer s, int money){
        addSelectMoneyToAccount(s, money);
        return this.accountMap;
    }

    public Map<Integer, Account> removeMoney(Integer s, int money){
        if (removeSelectMoneyFromAccount(s, money) == false){
            return null;
        } else
        return this.accountMap;
    }


    public Map<Integer, Account> giveMoneyFromFirstToLast(Integer s1, Integer s2, int money){
        if (removeSelectMoneyFromAccount(s1, money) == false){
            return null;
        } else addSelectMoneyToAccount(s2, money);
        return this.accountMap;
    }


    private void addSelectMoneyToAccount(Integer id, int money){
        for (Map.Entry<Integer, Account> entry: accountMap.entrySet()){
            if (entry.getKey().equals(id)){
                entry.getValue().setClientMoneyNumber(entry.getValue().getClientMoneyNumber() + money);
            }
        }
    }

    private boolean removeSelectMoneyFromAccount(Integer id, int money){
        for (Map.Entry<Integer, Account> entry: accountMap.entrySet()){
            if (entry.getKey().equals(id)) {
                if (entry.getValue().getClientMoneyNumber() < money){
                    return false;
                } else {
                    entry.getValue().setClientMoneyNumber(entry.getValue().getClientMoneyNumber() - money);
                   break;
                }
            }
        }
        return true;
    }



}
