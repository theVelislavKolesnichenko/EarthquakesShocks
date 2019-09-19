package com.example.models;

import com.example.models.enumerations.SearchType;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchCriteria {
    private int page;
    private int pageSize;
    private String search;
    private String searchFields;
    private SearchType searchType;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    private Pattern quotes = Pattern.compile("(?:^|\\s)\"([^\"]*?)\"(?:$|\\s)", Pattern.MULTILINE);
    private Pattern colon = Pattern.compile(".*:.*", Pattern.MULTILINE);
    private Pattern equal = Pattern.compile(".*=.*", Pattern.MULTILINE);

    public SearchCriteria(LocalDate dateFrom, LocalDate dateTo, String search) {
        this(dateFrom, dateTo, search, 0, 10000);
    }

    public SearchCriteria(LocalDate dateFrom, LocalDate dateTo, String search, int page) {
        this(dateFrom, dateTo, search, page, 10000);
    }

    public SearchCriteria(LocalDate dateFrom, LocalDate dateTo, String search, int page, int size) {
        this.page = page;
        this.pageSize = size;
        this.search = search;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

        Matcher matcherQuotes = quotes.matcher(search);
        boolean first = matcherQuotes.find() && search.length() == matcherQuotes.group().toString().length();

        Matcher matcherColon = colon.matcher(search);
        boolean second = matcherColon.find();

        Matcher matcherEqual = equal.matcher(search);
        boolean third = matcherEqual.find();

        if (second) {
            String[] text = search.split(":");
            searchFields = text[0];
            this.search = text[1];

            Matcher matcherSearchText = quotes.matcher(this.search);
            if(matcherSearchText.find()) {
                searchType = SearchType.PerfectKeyValue;
            } else {
                searchType = SearchType.FullKeyValue;
            }
        } else if(third) {
            String[] text = search.split("=");
            searchFields = text[0];
            this.search = text[1];
            searchType = SearchType.PerfectKeyValue;
        } else if(first) {
            this.search = search;
            searchType = SearchType.Perfect;
        } else {
            search.replace(" ", "+");
            searchType = SearchType.Full;
        }
    }

    public int getFrom() {
        return page * pageSize;
    }

    public int getSize() {
        return pageSize;
    }

    public String getSearch() {
        return search;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getSearchFields() {
        return searchFields;
    }

    public SearchType getSearchType() { return searchType;}
}
