package com.example.mapapi.models;

import com.example.mapapi.models.enumerations.ShapeColor;

import java.util.ArrayList;

public class PolygonInfo {
    private String title;
    private ArrayList<String> description;
    private ShapeColor color;

    public PolygonInfo(String title, ArrayList<String> description, ShapeColor color) {
        this.title = title;
        this.description = description;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public String getColor() {
        return color.toString();
    }

    public ShapeColor getSoilColor() {
        return color;
    }

    public void setSoilColor(ShapeColor color) {
        this.color = color;
    }
}
