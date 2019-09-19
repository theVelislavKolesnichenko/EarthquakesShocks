package com.example.models;

import java.time.LocalDate;

public class SearchRequest {
    private String search;

    private LocalDate from;

    private LocalDate to;

    public SearchRequest() { }

    public SearchRequest(String search, LocalDate from, LocalDate to) {
        this.search = search;
        this.from = from;
        this.to = to;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
