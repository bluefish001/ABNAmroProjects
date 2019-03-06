package com.abnamro.entity;

import java.util.Objects;

public class Client {
    String clientType;
    int clientNumber;
    int accountNumber;
    int subAccountNumber;

    public Client(){}

    public Client( String clientType, int clientNumber, int accountNumber,
            int subAccountNumber){
        this.clientType = clientType;
        this.clientNumber = clientNumber;
        this.accountNumber = accountNumber;
        this.subAccountNumber = subAccountNumber;
    }


    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getSubAccountNumber() {
        return subAccountNumber;
    }

    public void setSubAccountNumber(int subAccountNumber) {
        this.subAccountNumber = subAccountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientNumber == client.clientNumber &&
                accountNumber == client.accountNumber &&
                subAccountNumber == client.subAccountNumber &&
                Objects.equals(clientType, client.clientType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(clientType, clientNumber, accountNumber, subAccountNumber);
    }
}
