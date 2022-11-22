package Aikamsoft.Model;

import java.util.ArrayList;

public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String name;
    private int purchasedProductsCount;
    private int sumProductsPurchased;
    private ArrayList<Purchases> purchases;
    private int totalExpenses;

    public int getSumProductsPurchased() {
        return sumProductsPurchased;
    }

    public void setSumProductsPurchased(int sumProductsPurchased) {
        this.sumProductsPurchased = sumProductsPurchased;
    }

    public int getPurchasedProductsCount() {
        return purchasedProductsCount;
    }

    public void setPurchasedProductsCount(int purchasedProductsCount) {
        this.purchasedProductsCount = purchasedProductsCount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Purchases> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<Purchases> purchases) {
        this.purchases = purchases;
    }

    public int getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(int totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}