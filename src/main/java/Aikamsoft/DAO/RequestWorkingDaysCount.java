package Aikamsoft.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestWorkingDaysCount {
    private final DBWorker worker;
    private final String startDate;
    private final String endDate;

    public RequestWorkingDaysCount(String startDate, String endDate, String defaultPath, ObjectMapper objectMapper) throws IOException {
        this.startDate = startDate;
        this.endDate = endDate;

        worker = new DBWorker(defaultPath, objectMapper);
    }

    public int getWorkingDaysCount() {
        try {
            ResultSet resultSet = worker.getResultSet(getCustomers());
            int dayCount = 0;

            while (resultSet.next()) {
                dayCount = (resultSet.getInt("count"));
            }

            return dayCount;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private String getCustomers() {
        return "SELECT COUNT(working_day)\n" +
                "FROM working_calendar\n" +
                "WHERE working_day BETWEEN date '" + startDate + "' AND date '" + endDate + "';";
    }
}