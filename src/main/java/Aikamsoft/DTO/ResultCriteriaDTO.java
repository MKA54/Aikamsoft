package Aikamsoft.DTO;

import java.util.List;
import java.util.Map;

public class ResultCriteriaDTO {
    private Map<String, String> criteria;
    private List<CustomerDTO> results;

    public ResultCriteriaDTO(Map<String, String> criteria, List<CustomerDTO> results) {
        this.criteria = criteria;
        this.results = results;
    }

    public Map<String, String> getCriteria() {
        return criteria;
    }

    public void setCriteria(Map<String, String> criteria) {
        this.criteria = criteria;
    }

    public List<CustomerDTO> getResults() {
        return results;
    }

    public void setResults(List<CustomerDTO> results) {
        this.results = results;
    }
}