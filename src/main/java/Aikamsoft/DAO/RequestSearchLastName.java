package Aikamsoft.DAO;

import Aikamsoft.Model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestSearchLastName {
    private final DBWorker worker;
    private final String lastName;

    public RequestSearchLastName(String lastName, String defaultPath, ObjectMapper objectMapper) throws IOException {
        worker = new DBWorker(defaultPath, objectMapper);
        this.lastName = lastName;
    }

    public List<Customer> getLastName() throws SQLException {
        ResultSet resultSet = worker.getResultSet(getSelectLastName());
        List<Customer> resultCustomers = new ArrayList<>();

        while (resultSet.next()) {
            Customer customer = new Customer();
            customer.setLastName(resultSet.getString("last_name"));
            customer.setFirstName(resultSet.getString("first_name"));

            resultCustomers.add(customer);
        }

        return resultCustomers;
    }

    private String getSelectLastName() {
        return "SELECT last_name, first_name  \n" +
                "FROM buyers\n" +
                "Where last_name = '" + lastName + "';";
    }
}