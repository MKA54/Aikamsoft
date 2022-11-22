package Aikamsoft.DAO;

import Aikamsoft.Model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestProductsCount {
    private final DBWorker worker;
    private final String productName;
    private final String minTimes;

    public RequestProductsCount(String productName, String minTimes, String defaultPath, ObjectMapper objectMapper) throws IOException {
        worker = new DBWorker(defaultPath, objectMapper);
        this.productName = productName;
        this.minTimes = minTimes;
    }

    public List<Customer> getProductsCount() throws SQLException {
        ResultSet resultSet = worker.getResultSet(requestProductsCount());
        List<Customer> resultCustomers = new ArrayList<>();

        while (resultSet.next()) {
            Customer customer = new Customer();

            customer.setLastName(resultSet.getString("last_name"));
            customer.setFirstName(resultSet.getString("first_name"));
            customer.setPurchasedProductsCount(resultSet.getInt("COUNT"));

            resultCustomers.add(customer);
        }

        return resultCustomers;
    }

    private String requestProductsCount() {
        return "SELECT first_name, last_name, COUNT(*)\n" +
                "FROM purchases\n" +
                "INNER JOIN buyers\n" +
                "ON purchases.buyers_id = buyers.id\n" +
                "INNER JOIN products\n" +
                "ON purchases.products_id = products.id\n" +
                "Where products.name = '" + productName + "'\n" +
                "GROUP BY first_name, last_name\n" +
                "Having COUNT(*) >= " + minTimes + ";";
    }
}