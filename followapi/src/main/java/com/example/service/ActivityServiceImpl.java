package com.example.service;

import com.example.models.SearchCriteria;
import com.example.models.elasticsearch.Activity;
import com.example.repository.base.ActivityRepository;
import com.example.service.base.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class ActivityServiceImpl implements ActivityService {

    private ActivityRepository repository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArrayList<Activity> findByCriteria(SearchCriteria criteria) throws IOException {
        switch (criteria.getSearchType()) {
            case Full:
                return repository.findFull(criteria.getDateFrom(), criteria.getDateTo(), criteria.getSearch(), criteria.getFrom(), criteria.getSize());
            case Perfect:
                return repository.findPerfect(criteria.getDateFrom(), criteria.getDateTo(), criteria.getSearch(), criteria.getFrom(), criteria.getSize());
            case FullKeyValue:
                return repository.findFullKeyValue(criteria.getDateFrom(), criteria.getDateTo(), criteria.getSearchFields(), criteria.getSearch(), criteria.getFrom(), criteria.getSize());
            case PerfectKeyValue:
                return repository.findPerfectKeyValue(criteria.getDateFrom(), criteria.getDateTo(), criteria.getSearchFields(), criteria.getSearch(), criteria.getFrom(), criteria.getSize());
        }
        return repository.findAll();
    }

    @Override
    public Boolean addActivity(Activity activity) {
        return repository.add(activity);
    }

    @Override
    public ArrayList<Activity> findAll() {
        ArrayList<Activity> result = new ArrayList<>();
        try {
            result = repository.findAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<Activity> findByPage(int page, int size) {
        ArrayList<Activity> result = new ArrayList<>();
        try {
            result = repository.findByPage(page*size, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<Activity> find(int page, int size) {
        ArrayList<Activity> result = new ArrayList<>();
        try {
            result = repository.find(page*size, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
