package Aikamsoft.Converter;

import Aikamsoft.DTO.CustomerDTO;
import Aikamsoft.Model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomersToCustomersDTO {
    public List<CustomerDTO> convert(List<Customer> customers) {
        List<CustomerDTO> result = new ArrayList<>();

        for (Customer c : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setFirstName(c.getFirstName());
            customerDTO.setLastName(c.getLastName());

            result.add(customerDTO);
        }

        return result;
    }
}