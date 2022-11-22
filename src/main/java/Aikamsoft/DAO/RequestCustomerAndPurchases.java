package Aikamsoft.DAO;

import Aikamsoft.Model.Customer;
import Aikamsoft.Model.Purchases;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestCustomerAndPurchases {
    private final DBWorker worker;
    private final Long id;

    public RequestCustomerAndPurchases(Long id, String defaultPath, ObjectMapper objectMapper) throws IOException {
        this.worker = new DBWorker(defaultPath, objectMapper);
        this.id = id;
    }

    public Customer getCustomersAndPurchases() {
        try {
            ResultSet customerSet = worker.getResultSet(getCustomers());
            Customer customer = new Customer();

            while (customerSet.next()) {
                customer.setName(customerSet.getString("concat"));
            }

            ResultSet purchasesSet = worker.getResultSet(getPurchases());
            ArrayList<Purchases> purchases = new ArrayList<>();

            while (purchasesSet.next()) {
                Purchases purchase = new Purchases();
                purchase.setName(purchasesSet.getString("name"));
                purchase.setExpenses(purchasesSet.getInt("sum"));

                purchases.add(purchase);
            }

            int totalExpenses = 0;
            for (Purchases p : purchases) {
                totalExpenses += p.getExpenses();
            }

            customer.setPurchases(purchases);
            customer.setTotalExpenses(totalExpenses);

            return customer;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getCustomers() {
        return "SELECT CONCAT(last_name, ' ', first_name)  \n" +
                "FROM buyers\n" +
                "Where id = " + id + ";";
    }

    private String getPurchases() {
        return "SELECT products.name, SUM(price)\n" +
                "FROM purchases\n" +
                "INNER JOIN buyers\n" +
                "ON purchases.buyers_id = buyers.id\n" +
                "INNER JOIN products\n" +
                "ON purchases.products_id = products.id\n" +
                "WHERE purchase_date BETWEEN date '2022-11-01' AND date '2022-11-28'\n" +
                "\tAND purchase_date = (SELECT holiday\n" +
                "\t\tFROM weekend_calendar\n" +
                "\t\tWHERE purchases.purchase_date = weekend_calendar.holiday) AND buyers.id = " + id + "\n" +
                "GROUP BY products.name;";
    }
}