package com.example.service.base;

import com.example.models.SearchCriteria;
import com.example.models.elasticsearch.Activity;

import java.io.IOException;
import java.util.ArrayList;

public interface ActivityService {
    ArrayList<Activity> findByCriteria(SearchCriteria criteria) throws IOException;
    Boolean addActivity(Activity activity);
    ArrayList<Activity> findAll();
    ArrayList<Activity> findByPage(int page, int size);
    ArrayList<Activity> find(int page, int size);
}
