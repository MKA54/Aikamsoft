package Aikamsoft.Model;

public class TargetValueProduct {
    String paramMinExpenses;
    String minExpenses;
    String paramMaxExpenses;
    String maxExpenses;

    public TargetValueProduct(String paramMinExpenses, String minExpenses, String paramMaxExpenses, String maxExpenses) {
        this.paramMinExpenses = paramMinExpenses;
        this.minExpenses = minExpenses;
        this.paramMaxExpenses = paramMaxExpenses;
        this.maxExpenses = maxExpenses;
    }

    public String getParamMinExpenses() {
        return paramMinExpenses;
    }

    public void setParamMinExpenses(String paramMinExpenses) {
        this.paramMinExpenses = paramMinExpenses;
    }

    public String getMinExpenses() {
        return minExpenses;
    }

    public void setMinExpenses(String minExpenses) {
        this.minExpenses = minExpenses;
    }

    public String getParamMaxExpenses() {
        return paramMaxExpenses;
    }

    public void setParamMaxExpenses(String paramMaxExpenses) {
        this.paramMaxExpenses = paramMaxExpenses;
    }

    public String getMaxExpenses() {
        return maxExpenses;
    }

    public void setMaxExpenses(String maxExpenses) {
        this.maxExpenses = maxExpenses;
    }
}