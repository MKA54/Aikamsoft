package Aikamsoft.DTO;

import java.util.List;

public class SearchResults {
    private String type;
    private List<ResultCriteriaDTO> results;

    public SearchResults(String type, List<ResultCriteriaDTO> results) {
        this.type = type;
        this.results = results;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ResultCriteriaDTO> getResults() {
        return results;
    }

    public void setResults(List<ResultCriteriaDTO> results) {
        this.results = results;
    }
}