package Aikamsoft.Service;

import Aikamsoft.Converter.CriteriasDtoToCustomersCriterias;
import Aikamsoft.Converter.CustomersToCustomersDTO;
import Aikamsoft.DAO.*;
import Aikamsoft.DTO.ResultCriteriaDTO;
import Aikamsoft.DTO.CustomerDTO;
import Aikamsoft.DTO.SearchResults;
import Aikamsoft.Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Search {
    private final CriteriasDtoToCustomersCriterias criteriasDtoToCustomersCriterias;
    private CustomersToCustomersDTO customersToCustomersDTO;
    private final String defaultPath;
    private final ObjectMapper objectMapper;
    private boolean isSuccessfully = true;
    private final WriteException writeException = new WriteException();

    public Search(String path, String defaultPath, ObjectMapper objectMapper) throws IOException {
        this.defaultPath = defaultPath;
        this.objectMapper = objectMapper;
        this.criteriasDtoToCustomersCriterias = new CriteriasDtoToCustomersCriterias(path, defaultPath, objectMapper);

        if (criteriasDtoToCustomersCriterias.isException()) {
            isSuccessfully = false;
            return;
        }

        customersToCustomersDTO = new CustomersToCustomersDTO();
    }

    private List<ResultCriteriaDTO> querySelectLastName() throws IOException {
        List<DesiredCustomer> desiredCustomers = criteriasDtoToCustomersCriterias.getDesiredCustomers();
        List<ResultCriteriaDTO> resultsCriteriaDTO = new ArrayList<>();

        try {
            for (DesiredCustomer desiredCustomer : desiredCustomers) {
                RequestSearchLastName requestSearchLastName = new RequestSearchLastName(desiredCustomer.getLastName(), defaultPath, objectMapper);
                List<Customer> resultCustomers = requestSearchLastName.getLastName();

                List<CustomerDTO> resultCustomersDTO = new ArrayList<>(customersToCustomersDTO.convert(resultCustomers));

                resultsCriteriaDTO.add(new ResultCriteriaDTO(
                        getCriterionSet(
                                desiredCustomer.getParamName(),
                                desiredCustomer.getLastName()),
                        resultCustomersDTO));
            }
        } catch (SQLException | IOException e) {
            isSuccessfully = false;
            writeException.writeException(objectMapper, "Не верный запрос к БД", defaultPath);
        } catch (NullPointerException e) {
            isSuccessfully = false;
            writeException.writeException(objectMapper, "Не удалось подключиться к БД", defaultPath);
        }

        return resultsCriteriaDTO;
    }

    private List<ResultCriteriaDTO> queryProductsCount() throws IOException {
        List<ProductNameAndCount> productsAndCount = criteriasDtoToCustomersCriterias.getProductsAndCount();
        List<ResultCriteriaDTO> resultsCriteriaDTO = new ArrayList<>();

        try {
            for (ProductNameAndCount productNameAndCount : productsAndCount) {
                RequestProductsCount requestProductsCount = new RequestProductsCount(
                        productNameAndCount.getName(),
                        productNameAndCount.getCount(),
                        defaultPath,
                        objectMapper);

                List<CustomerDTO> resultCustomersDTO = new ArrayList<>(customersToCustomersDTO.
                        convert(requestProductsCount.getProductsCount()));

                HashMap<String, String> criteria = new HashMap<>();
                criteria.put(
                        productNameAndCount.getParamCount(),
                        productNameAndCount.getCount());

                criteria.put(
                        productNameAndCount.getParamNameProduct(),
                        productNameAndCount.getName());

                resultsCriteriaDTO.add(new ResultCriteriaDTO(criteria, resultCustomersDTO));
            }
        } catch (SQLException | IOException e) {
            isSuccessfully = false;
            writeException.writeException(objectMapper, "Не верный запрос к БД", defaultPath);
        } catch (NullPointerException e) {
            isSuccessfully = false;
            writeException.writeException(objectMapper, "Не удалось подключиться к БД", defaultPath);
        }

        return resultsCriteriaDTO;
    }

    private List<ResultCriteriaDTO> queryTargetValueProduct() throws IOException {
        List<TargetValueProduct> shoppingInterval = criteriasDtoToCustomersCriterias.getShoppingInterval();
        List<ResultCriteriaDTO> resultsCriteriaDTO = new ArrayList<>();

        try {
            for (TargetValueProduct targetValueProduct : shoppingInterval) {
                RequestShoppingInterval requestShoppingInterval = new RequestShoppingInterval(
                        targetValueProduct.getMinExpenses(),
                        targetValueProduct.getMaxExpenses(),
                        defaultPath,
                        objectMapper);

                List<CustomerDTO> resultCustomersDTO = customersToCustomersDTO.
                        convert(requestShoppingInterval.getShoppingInterval());

                HashMap<String, String> criteria = new HashMap<>();
                criteria.put(
                        targetValueProduct.getParamMinExpenses(),
                        targetValueProduct.getMinExpenses());

                criteria.put(
                        targetValueProduct.getParamMaxExpenses(),
                        targetValueProduct.getMaxExpenses());

                resultsCriteriaDTO.add(new ResultCriteriaDTO(criteria, resultCustomersDTO));
            }
        } catch (SQLException | IOException e) {
            isSuccessfully = false;
            writeException.writeException(objectMapper, "Не верный запрос к БД", defaultPath);
        } catch (NullPointerException e) {
            isSuccessfully = false;
            writeException.writeException(objectMapper, "Не удалось подключиться к БД", defaultPath);
        }

        return resultsCriteriaDTO;
    }

    private List<ResultCriteriaDTO> queryPassiveBuyersCount() throws IOException {
        List<DesiredBadCustomers> passiveBuyersCount = criteriasDtoToCustomersCriterias.getBadCustomers();
        List<ResultCriteriaDTO> resultsCriteriaDTO = new ArrayList<>();

        try {
            for (DesiredBadCustomers desiredBadCustomers : passiveBuyersCount) {
                RequestBadCustomers requestBadCustomers = new RequestBadCustomers(
                        desiredBadCustomers.getCount(), defaultPath, objectMapper);

                List<CustomerDTO> resultCustomersDTO = new ArrayList<>(customersToCustomersDTO.
                        convert(requestBadCustomers.getBadCustomers()));

                resultsCriteriaDTO.add(new ResultCriteriaDTO(
                        getCriterionSet(
                                desiredBadCustomers.getParamBadCustomers(),
                                desiredBadCustomers.getCount()),
                        resultCustomersDTO));
            }
        } catch (SQLException | IOException e) {
            isSuccessfully = false;
            writeException.writeException(objectMapper, "Не верный запрос к БД", defaultPath);
        } catch (NullPointerException e) {
            isSuccessfully = false;
            writeException.writeException(objectMapper, "Не удалось подключиться к БД", defaultPath);
        }

        return resultsCriteriaDTO;
    }

    public SearchResults getResult() throws IOException {
        List<ResultCriteriaDTO> resultCriteriaDTO = new ArrayList<>();
        resultCriteriaDTO.addAll(querySelectLastName());
        resultCriteriaDTO.addAll(queryProductsCount());
        resultCriteriaDTO.addAll(queryTargetValueProduct());
        resultCriteriaDTO.addAll(queryPassiveBuyersCount());

        return new SearchResults("search", resultCriteriaDTO);
    }

    private HashMap<String, String> getCriterionSet(String paramName, String value) {
        HashMap<String, String> set = new HashMap<>();
        set.put(paramName, value);

        return set;
    }

    public boolean isSuccessfully() {
        return isSuccessfully;
    }

    public void setSuccessfully(boolean successfully) {
        isSuccessfully = successfully;
    }
}