package Aikamsoft.DTO;

import Aikamsoft.Model.Purchases;

import java.util.ArrayList;

public class CustomerStatDTO {
    private String name;
    private ArrayList<Purchases> purchases;
    private int totalExpenses;

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
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