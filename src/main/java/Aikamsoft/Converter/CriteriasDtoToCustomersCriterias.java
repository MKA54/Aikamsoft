package Aikamsoft.Converter;

import Aikamsoft.Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CriteriasDtoToCustomersCriterias {
    private final String path;
    private final String defaultPath;
    private boolean isException = false;
    private final ObjectMapper objectMapper;

    private List<DesiredCustomer> desiredCustomers = new ArrayList<>();
    private List<ProductNameAndCount> productsAndCount = new ArrayList<>();
    private List<TargetValueProduct> shoppingInterval = new ArrayList<>();
    private List<DesiredBadCustomers> badCustomers = new ArrayList<>();

    public CriteriasDtoToCustomersCriterias(String path, String defaultPath, ObjectMapper objectMapper) throws IOException {
        this.path = path;
        this.defaultPath = defaultPath;
        this.objectMapper = objectMapper;

        loadCriterias();
    }

    public List<DesiredCustomer> getDesiredCustomers() {
        return desiredCustomers;
    }

    public void setDesiredCustomers(List<DesiredCustomer> desiredCustomers) {
        this.desiredCustomers = desiredCustomers;
    }

    public List<ProductNameAndCount> getProductsAndCount() {
        return productsAndCount;
    }

    public void setProductsAndCount(List<ProductNameAndCount> productsAndCount) {
        this.productsAndCount = productsAndCount;
    }

    public List<TargetValueProduct> getShoppingInterval() {
        return shoppingInterval;
    }

    public void setShoppingInterval(List<TargetValueProduct> shoppingInterval) {
        this.shoppingInterval = shoppingInterval;
    }

    public List<DesiredBadCustomers> getBadCustomers() {
        return badCustomers;
    }

    public void setBadCustomers(List<DesiredBadCustomers> badCustomers) {
        this.badCustomers = badCustomers;
    }

    public boolean isException() {
        return isException;
    }

    public void setException(boolean exception) {
        isException = exception;
    }

    private void loadCriterias() throws IOException {
        try {
            Criterias criterias = objectMapper.readValue(new File
                    (path), Criterias.class);

            int parsingCount = 0;
            List<Integer> notFixedParameter = new ArrayList<>();

            for (int i = 0; i < criterias.getCriterias().size(); i++) {
                String criterion = criterias.getCriterias().get(i).toString();
                criterion = criterion.substring(1, criterion.length() - 1);

                if (criterion.contains("lastName")) {
                    String[] paramAndValues = getArrayParameters(2, criterion);
                    desiredCustomers.add(new DesiredCustomer(paramAndValues[0], paramAndValues[1]));

                    parsingCount++;
                    continue;
                }

                if (criterion.contains("productName")) {
                    String[] paramAndValues = getArrayParameters(4, criterion);

                    int count = Integer.parseInt(paramAndValues[3]);

                    if (count < 1) {
                        throw new MyException("Не корректное значение: minTimes" + ", index параметра " + i);
                    }

                    ProductNameAndCount productAndCount = new ProductNameAndCount(
                            paramAndValues[0],
                            paramAndValues[1],
                            paramAndValues[2],
                            paramAndValues[3]);

                    productsAndCount.add(productAndCount);

                    parsingCount++;
                    continue;
                }

                if (criterion.contains("minExpenses")) {
                    String[] paramAndValues = getArrayParameters(4, criterion);

                    int minExpenses = Integer.parseInt(paramAndValues[1]);
                    int maxExpenses = Integer.parseInt(paramAndValues[3]);

                    if (maxExpenses < minExpenses || minExpenses < 0) {
                        throw new MyException("Не корректные значения: minTimes or maxExpenses" + ", index параметра " + i);
                    }

                    TargetValueProduct targetValueProduct = new TargetValueProduct(
                            paramAndValues[0],
                            paramAndValues[1],
                            paramAndValues[2],
                            paramAndValues[3]);

                    shoppingInterval.add(targetValueProduct);

                    parsingCount++;
                    continue;
                }

                if (criterion.contains("badCustomers")) {
                    String[] paramAndValues = getArrayParameters(2, criterion);

                    int badCustomersCount = Integer.parseInt(paramAndValues[1]);

                    if (badCustomersCount < 1) {
                        throw new MyException("Не корректное значение badCustomers: " + badCustomersCount +
                                ", index параметра " + i);
                    }

                    badCustomers.add(new DesiredBadCustomers(paramAndValues[0], paramAndValues[1]));

                    parsingCount++;
                    continue;
                }

                notFixedParameter.add(i);
            }

            if (parsingCount != criterias.getCriterias().size()) {
                throw new MyException("Не верный формат названия параметра(ов), indexArray параметра(ов): " + notFixedParameter);
            }
        } catch (IOException e) {
            isException = true;
            writeException("Не верный путь входного файла");
        } catch (MyException e) {
            isException = true;
            writeException(e.getMessage());
        } catch (NumberFormatException e) {
            isException = true;
            writeException("Не верный формат числовых данных параметра(ов): badCustomers or minExpenses or" +
                    "maxExpenses or minTimes");
        }
    }

    private String[] splitString(String s, String param) {
        return s.split(param);
    }

    private String[] getArrayParameters(int parametersCount, String parameters) {
        String[] arrayParameters = splitString(parameters, ",");
        String[] paramAndValues = new String[parametersCount];
        int j = 0;
        int k = 1;

        for (String param : arrayParameters) {
            String[] entry = splitString(param, "=");

            paramAndValues[j] = entry[0].trim();
            paramAndValues[k] = entry[1].trim();

            j += 2;
            k += 2;
        }

        return paramAndValues;
    }

    private void writeException(String message) throws IOException {
        WriteException writeException = new WriteException();
        writeException.writeException(objectMapper, message, defaultPath);
    }
}