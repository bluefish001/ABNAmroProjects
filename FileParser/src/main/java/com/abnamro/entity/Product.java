package com.abnamro.entity;

import java.util.Date;
import java.util.Objects;

public class Product {
    String exchangeCode;
    String productGroup;
    String symbol;
    Date expirationDate;

    public Product(){}

    public Product(String exchangeCode, String productGroup, String symbol,
            Date expirationDate){
        this.exchangeCode = exchangeCode;
        this.productGroup = productGroup;
        this.symbol =symbol;
        this.expirationDate = expirationDate;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(exchangeCode, product.exchangeCode) &&
                Objects.equals(productGroup, product.productGroup) &&
                Objects.equals(symbol, product.symbol) &&
                Objects.equals(expirationDate, product.expirationDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(exchangeCode, productGroup, symbol, expirationDate);
    }
}
