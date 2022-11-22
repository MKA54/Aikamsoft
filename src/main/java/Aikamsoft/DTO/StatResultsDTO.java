package Aikamsoft.DTO;

import java.util.List;
import java.util.OptionalDouble;

public class StatResultsDTO {
    private String type;
    private int totalDays;
    private List<CustomerStatDTO> customers;
    private double totalExpenses;
    private String avgExpenses;

    public StatResultsDTO(String type, int totalDays, List<CustomerStatDTO> customers, double totalExpenses, String avgExpenses) {
        this.type = type;
        this.totalDays = totalDays;
        this.customers = customers;
        this.totalExpenses = totalExpenses;
        this.avgExpenses = avgExpenses;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CustomerStatDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerStatDTO> customers) {
        this.customers = customers;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getAvgExpenses() {
        return avgExpenses;
    }

    public void setAvgExpenses(String avgExpenses) {
        this.avgExpenses = avgExpenses;
    }
}