package com.example.mapapi.models;

import java.util.ArrayList;

public class MultiPolygon {
    private String type;
    private ArrayList<Polygons> coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Polygons> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Polygons> coordinates) {
        this.coordinates = coordinates;
    }

    public MultiPolygon() {
    }

    public MultiPolygon(String type, ArrayList<Polygons> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }
}
