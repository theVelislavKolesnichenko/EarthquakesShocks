package com.example.mapapi.models;

import com.example.mapapi.models.enumerations.ShapeType;

public class SearchRequest {

    private Location location;
    private ShapeType type;

    public SearchRequest() { }

    public SearchRequest(Location location, ShapeType shapeType) {
        this.location = location;
        this.type = shapeType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ShapeType getShapeType() {
        return type;
    }

    public void setShapeType(ShapeType shapeType) {
        this.type = shapeType;
    }
}
