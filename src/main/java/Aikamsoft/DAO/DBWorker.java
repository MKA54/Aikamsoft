package Aikamsoft.DAO;

import Aikamsoft.Model.WriteException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.*;

public class DBWorker {
    private Statement statement;

    public DBWorker(String defaultPath, ObjectMapper objectMapper) throws IOException {
        try {
            String USER_NAME = "postgres";
            String PASSWORD = "1234";
            String HOST = "jdbc:postgresql://127.0.0.1:5432/aikamsoft";

            Connection connection = DriverManager.getConnection(HOST, USER_NAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            WriteException writeException = new WriteException();
            writeException.writeException(objectMapper, "Не удалось подключиться к БД", defaultPath);
        }
    }

    public ResultSet getResultSet(String criterion) throws SQLException, NullPointerException {
        return statement.executeQuery(criterion);
    }
}