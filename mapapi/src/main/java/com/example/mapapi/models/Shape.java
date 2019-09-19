package com.example.mapapi.models;

public class Shape {
    private PolygonInfo polygonInfo;
    private MultiPolygon polygons;

    public Shape() {
    }

    public MultiPolygon getPolygons() {
        return polygons;
    }

    public void setPolygons(MultiPolygon polygons) {
        this.polygons = polygons;
    }

    public PolygonInfo getPolygonInfo() {
        return polygonInfo;
    }

    public void setPolygonInfo(PolygonInfo polygonInfo) {
        this.polygonInfo = polygonInfo;
    }


}
