package Aikamsoft.Model;

public class DesiredBadCustomers {
    String paramBadCustomers;
    String count;

    public DesiredBadCustomers(String paramBadCustomers, String count) {
        this.paramBadCustomers = paramBadCustomers;
        this.count = count;
    }

    public String getParamBadCustomers() {
        return paramBadCustomers;
    }

    public void setParamBadCustomers(String paramBadCustomers) {
        this.paramBadCustomers = paramBadCustomers;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}