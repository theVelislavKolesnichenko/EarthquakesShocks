package com.example.mapapi.service.base;

import com.example.mapapi.models.Location;
import com.example.mapapi.models.Polygon;
import com.example.mapapi.models.Polygons;
import com.example.mapapi.models.Shape;
import com.example.mapapi.models.enumerations.ShapeType;

public interface ShapeService {
    Shape findByLatLng(Location location, ShapeType shapeType);
}
