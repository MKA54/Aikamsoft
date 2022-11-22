package Aikamsoft.DAO;

import Aikamsoft.Model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestShoppingInterval {
    private final DBWorker worker;
    private final String minExpenses;
    private final String maxExpenses;

    public RequestShoppingInterval(String minExpenses, String maxExpenses, String defaultPath, ObjectMapper objectMapper) throws IOException {
        worker = new DBWorker(defaultPath, objectMapper);
        this.minExpenses = minExpenses;
        this.maxExpenses = maxExpenses;
    }

    public List<Customer> getShoppingInterval() throws SQLException {
        ResultSet resultSet = worker.getResultSet(getTargetValueProduct());
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

    private String getTargetValueProduct() {
        return "SELECT first_name, last_name, SUM(products.price)\n" +
                "FROM purchases\n" +
                "INNER JOIN buyers\n" +
                "ON purchases.buyers_id = buyers.id\n" +
                "INNER JOIN products\n" +
                "ON purchases.products_id = products.id\n" +
                "GROUP BY first_name, last_name\n" +
                "HAVING SUM(products.price) >= " + minExpenses + " AND SUM(products.price) <= " + maxExpenses + ";";
    }
}