package com.danko.rest.beans;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Objects;

@RegisterForReflection
public class Account {
    private String clientName;
    private String clientPhoneNumber;
    private String clientPassportNumber;
    private int clientMoneyNumber;


    public Account(String clientName, String clientPhoneNumber, String clientPassportNumber, int clientMoneyNumber) {
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientPassportNumber = clientPassportNumber;
        this.clientMoneyNumber = clientMoneyNumber;
    }

    public Account(String clientName, String clientPhoneNumber, String clientPassportNumber) {
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientPassportNumber = clientPassportNumber;
        this.clientMoneyNumber = 0;
    }

    public Account() {

    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientPassportNumber() {
        return clientPassportNumber;
    }

    public void setClientPassportNumber(String clientPassportNumber) {
        this.clientPassportNumber = clientPassportNumber;
    }

    public int getClientMoneyNumber() {
        return clientMoneyNumber;
    }

    public void setClientMoneyNumber(int clientMoneyNumber) {
        this.clientMoneyNumber = clientMoneyNumber;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Account)){
            return false;
        }

        Account otherAccount = (Account)obj;
        return (Objects.equals(this.clientName, otherAccount.clientName) &&
                Objects.equals(this.clientPassportNumber, otherAccount.clientPassportNumber) &&
                Objects.equals(this.clientPhoneNumber, otherAccount.clientPhoneNumber));
    }

    @Override
    public int hashCode(){
        return  Objects.hash(this.clientName,this.clientMoneyNumber,this.clientPassportNumber,this.clientPhoneNumber);
    }
}
