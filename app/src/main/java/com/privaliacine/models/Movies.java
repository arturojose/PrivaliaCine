package com.privaliacine.models;

import java.util.List;

public class Movies {
    //ponemos el mismo nombre del Array Jon de la API (results)
    public int total_results;
    public List<Results> results;

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
