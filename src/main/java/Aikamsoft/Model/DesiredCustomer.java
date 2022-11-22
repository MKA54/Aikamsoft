package Aikamsoft.Model;

public class DesiredCustomer {
    private String paramName;
    private String lastName;

    public DesiredCustomer(String paramName, String lastName) {
        this.paramName = paramName;
        this.lastName = lastName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}