package com.abnamro.entity;

import java.util.Objects;

public class Transaction {
    Client client;
    Product product;
    long qtyLong;
    long qtyShort;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getQtyLong() {
        return qtyLong;
    }

    public void setQtyLong(long qtyLong) {
        this.qtyLong = qtyLong;
    }

    public long getQtyShort() {
        return qtyShort;
    }

    public void setQtyShort(long qtyShort) {
        this.qtyShort = qtyShort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(client, that.client) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {

        return Objects.hash(client, product);
    }
}
