package com.example;

import com.example.models.SearchCriteria;
import com.example.models.elasticsearch.Activity;
import com.example.service.base.ActivityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.elasticsearch.annotations.DateFormat.date;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {

    @Mock
    private ActivityService service;

    @Test
    public void GetActivityFirstSearchType_ReturnCorrectCount() throws IOException {
        Mockito.when(service.findAll()).thenReturn(new ArrayList<Activity>() {{
            add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1", null));
            add(new Activity(UUID.randomUUID().toString(), "click", "username" , new Date(), "121.0.0.1", null));
            add(new Activity(UUID.randomUUID().toString(), "logout", "username" , new Date(), "121.0.0.1", null));
        }});

        SearchCriteria criteria = new SearchCriteria(LocalDate.now(), LocalDate.now(), "login click logout");

        List<Activity> result = service.findByCriteria(criteria);

        Assert.assertEquals(3, result.size());
    }

    @Test
    public void GetActivityBySecondSearchType_ReturnCorrectCount() throws IOException {
        Mockito.when(service.findAll()).thenReturn(new ArrayList<Activity>() {{
            add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1", null));
            add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1", null));
            add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1", null));
        }});

        SearchCriteria criteria = new SearchCriteria(LocalDate.now(), LocalDate.now(), "\"login\"");

        List<Activity> result = service.findByCriteria(criteria);

        Assert.assertEquals(3, result.size());
    }

    @Test
    public void GetActivityByThirdSearchType_ReturnCorrectCount() throws IOException {
        Mockito.when(service.findAll()).thenReturn(new ArrayList<Activity>() {{
            add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1", null));
            add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1", null));
            add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1", null));
        }});

        SearchCriteria criteria = new SearchCriteria(LocalDate.now(), LocalDate.now(), "event: \"login\"");

        List<Activity> result = service.findByCriteria(criteria);

        Assert.assertEquals(3, result.size());
    }

    @Test
    public void GetActivityByFourthSearchType_ReturnCorrectCount() throws IOException {
        Mockito.when(service.findAll()).thenReturn(new ArrayList<Activity>() {{
            add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1", null));
            add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1", null));
            add(new Activity(UUID.randomUUID().toString(), "login", "username" , new Date(), "121.0.0.1", null));
        }});

        SearchCriteria criteria = new SearchCriteria(LocalDate.now(), LocalDate.now(), "event: login");

        List<Activity> result = service.findByCriteria(criteria);

        Assert.assertEquals(3, result.size());
    }
}
