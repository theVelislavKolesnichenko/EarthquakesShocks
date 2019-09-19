package com.example.models.elasticsearch;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Activity {

    private String id;
    private String event;
    private String username;
    private Date date;
    private String ipAddress;
    private String additionalData;


    public Activity() { }

    public Activity(String id, String event, String username, Date date, String ipAddress, String additionalData) {
        this.id = id;
        this.event = event;
        this.username = username;
        this.date = date;
        this.ipAddress = ipAddress;
        this.additionalData = additionalData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }
}
