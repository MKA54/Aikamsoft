package Aikamsoft.DAO;

import Aikamsoft.Model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestBadCustomers {
    private final DBWorker worker;
    private final String count;

    public RequestBadCustomers(String count, String defaultPath, ObjectMapper objectMapper) throws IOException {
        worker = new DBWorker(defaultPath, objectMapper);
        this.count = count;
    }

    public List<Customer> getBadCustomers() throws SQLException {
        ResultSet resultSet = worker.getResultSet(requestBadCustomersCount());
        List<Customer> resultCustomers = new ArrayList<>();

        while (resultSet.next()) {
            Customer customer = new Customer();

            customer.setLastName(resultSet.getString("last_name"));
            customer.setFirstName(resultSet.getString("first_name"));
            customer.setSumProductsPurchased(resultSet.getInt("SUM"));

            resultCustomers.add(customer);
        }

        return resultCustomers;
    }

    private String requestBadCustomersCount() {
        return "SELECT first_name, last_name, SUM(products.price)\n" +
                "FROM purchases\n" +
                "INNER JOIN buyers\n" +
                "ON purchases.buyers_id = buyers.id\n" +
                "INNER JOIN products\n" +
                "ON purchases.products_id = products.id\n" +
                "GROUP BY first_name, last_name\n" +
                "ORDER BY SUM(products.price)\n" +
                "LIMIT " + count + ";";
    }
}