package com.example.controllers;

import com.example.client.AuthApiClient;
import com.example.models.SearchCriteria;
import com.example.models.SearchRequest;
import com.example.models.elasticsearch.Activity;
import com.example.service.base.ActivityService;
import com.example.util.WriteCsvToResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@CrossOrigin
@RestController
public class FollowController {

    private ActivityService service;

    private AuthApiClient client;

    @Autowired
    public FollowController(ActivityService service, AuthApiClient client) {
        this.service = service;
        this.client = client;
    }

    @PostMapping("/saveactivity")
    public String saveActivity(@RequestBody Activity activity) throws Exception {
        ZonedDateTime localDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Sofia"));
        activity.setDate(Date.from(localDateTime.toInstant()));
        activity.setIpAddress(getClientIp());
        service.addActivity(activity);
        return "saved";
    }

    @PostMapping("/getactivities")
    public ArrayList<Activity> getActivities(@RequestParam(name = "page", defaultValue = "0") int page, @RequestBody SearchRequest searchRequest) throws Exception {

        if(client.isAdmin()) {
            long monthsBetween = ChronoUnit.MONTHS.between(searchRequest.getFrom().withDayOfMonth(1), searchRequest.getTo().withDayOfMonth(1));

            SearchCriteria criteria = new SearchCriteria(searchRequest.getFrom(), searchRequest.getTo(), searchRequest.getSearch(), page);
            if(monthsBetween <= 3) {
                return service.findByCriteria(criteria);
            }
        }

        return null;
    }

    @PostMapping("/getcsvactivities")
    public void getCSVActivities(@RequestParam(name = "page", defaultValue = "0") int page, @RequestBody SearchRequest searchRequest, HttpServletResponse response) throws Exception {

        if(client.isAdmin()) {
            long monthsBetween = ChronoUnit.MONTHS.between(searchRequest.getFrom().withDayOfMonth(1), searchRequest.getTo().withDayOfMonth(1));

            ArrayList<Activity> activities = null;
            if(monthsBetween <= 3) {
                SearchCriteria criteria = new SearchCriteria(searchRequest.getFrom(), searchRequest.getTo(), searchRequest.getSearch(), page);
                activities =service.findByCriteria(criteria);
            }

            WriteCsvToResponse.writeCities(response.getWriter(), activities);
        }
    }

    private String getClientIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        return ip;
    }

}
