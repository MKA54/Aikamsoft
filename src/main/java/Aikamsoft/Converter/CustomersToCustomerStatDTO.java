package Aikamsoft.Converter;

import Aikamsoft.DTO.CustomerStatDTO;
import Aikamsoft.Model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomersToCustomerStatDTO {
    public List<CustomerStatDTO> convert(List<Customer> customers) {
        List<CustomerStatDTO> result = new ArrayList<>();

        for (Customer c : customers) {
            CustomerStatDTO customerStatDTO = new CustomerStatDTO();
            customerStatDTO.setName(c.getName());
            customerStatDTO.setPurchases(c.getPurchases());
            customerStatDTO.setTotalExpenses(c.getTotalExpenses());

            result.add(customerStatDTO);
        }

        return result;
    }
}