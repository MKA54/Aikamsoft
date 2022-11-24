package Aikamsoft.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestCustomersByDate {
    private final DBWorker worker;
    private final String startDate;
    private final String endDate;

    public RequestCustomersByDate(String startDate, String endDate, String defaultPath, ObjectMapper objectMapper) throws IOException {
        this.startDate = startDate;
        this.endDate = endDate;

        worker = new DBWorker(defaultPath, objectMapper);
    }

    public List<Long> getCustomersByDate() throws SQLException {
        ResultSet resultSet = worker.getResultSet(getCustomers());
        List<Long> resultCustomers = new ArrayList<>();

        while (resultSet.next()) {
            Long id = (resultSet.getLong("id"));

            resultCustomers.add(id);
        }

        return resultCustomers;
    }

    private String getCustomers() {
        return "SELECT buyers.id\n" +
                "FROM purchases\n" +
                "INNER JOIN buyers\n" +
                "ON purchases.buyers_id = buyers.id\n" +
                "INNER JOIN products\n" +
                "ON purchases.products_id = products.id\n" +
                "WHERE purchase_date BETWEEN date '" + startDate + "' AND date '" + endDate + "'\n" +
                "\tAND purchase_date = (SELECT working_day\n" +
                "\t\tFROM working_calendar\n" +
                "\t\tWHERE purchases.purchase_date = working_calendar.working_day)\n" +
                "GROUP BY buyers.id;";
    }
}