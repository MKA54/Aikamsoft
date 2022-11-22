package Aikamsoft.Service;

import Aikamsoft.Converter.CustomersToCustomerStatDTO;
import Aikamsoft.Converter.StatDateDTOToStatDate;
import Aikamsoft.DAO.RequestCustomerAndPurchases;
import Aikamsoft.DAO.RequestCustomersByDate;
import Aikamsoft.DAO.RequestWorkingDaysCount;
import Aikamsoft.DTO.StatResultsDTO;
import Aikamsoft.Model.Customer;
import Aikamsoft.Model.WriteException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Stat {
    private List<Long> idCustomers = null;
    private ArrayList<Customer> customersAndPurchases;
    private int dayCount = 0;
    private final ObjectMapper objectMapper;
    private final String defaultPath;
    private double totalExpenses = 0;
    private boolean isSuccessfully = true;

    public Stat(String path, String defaultPath, ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        this.defaultPath = defaultPath;
        StatDateDTOToStatDate statDateDTOToStatDate = new StatDateDTOToStatDate(path, defaultPath, objectMapper);

        if (statDateDTOToStatDate.isException()) {
            isSuccessfully = false;
            return;
        }

        WriteException writeException = new WriteException();
        try {
            idCustomers = new RequestCustomersByDate(
                    statDateDTOToStatDate.getStatisticsDates().getStartDate(),
                    statDateDTOToStatDate.getStatisticsDates().getEndDate(),
                    defaultPath,
                    objectMapper
            ).getCustomersByDate();

            dayCount = new RequestWorkingDaysCount(
                    statDateDTOToStatDate.getStatisticsDates().getStartDate(),
                    statDateDTOToStatDate.getStatisticsDates().getEndDate(),
                    defaultPath,
                    objectMapper
            ).getWorkingDaysCount();

            initCustomersAndPurchases();
        } catch (SQLException e) {
            isSuccessfully = false;
            writeException.writeException(objectMapper, "Не верный запрос к БД", defaultPath);
        } catch (NullPointerException e) {
            isSuccessfully = false;
            writeException.writeException(objectMapper, "Не удалось подключиться к БД", defaultPath);
        }
    }

    private void initCustomersAndPurchases() throws IOException {
        customersAndPurchases = new ArrayList<>();

        for (Long idCustomer : idCustomers) {
            RequestCustomerAndPurchases requestCustomerAndPurchases = new RequestCustomerAndPurchases(idCustomer, defaultPath, objectMapper);

            customersAndPurchases.add(requestCustomerAndPurchases.getCustomersAndPurchases());
            totalExpenses += requestCustomerAndPurchases.getCustomersAndPurchases().getTotalExpenses();
        }
    }

    public StatResultsDTO getStatResults() {
        CustomersToCustomerStatDTO customersToCustomerStatDTO = new CustomersToCustomerStatDTO();

        return new StatResultsDTO(
                "stat",
                dayCount,
                customersToCustomerStatDTO.convert(customersAndPurchases),
                totalExpenses,
                String.format("%.2f", totalExpenses / dayCount)
        );
    }

    public boolean isSuccessfully() {
        return isSuccessfully;
    }

    public void setSuccessfully(boolean successfully) {
        isSuccessfully = successfully;
    }
}