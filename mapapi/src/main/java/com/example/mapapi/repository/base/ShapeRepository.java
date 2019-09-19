package com.example.mapapi.repository.base;

import com.example.mapapi.models.Location;
import com.example.mapapi.models.Polygon;
import com.example.mapapi.models.Polygons;
import com.example.mapapi.models.Shape;
import com.example.mapapi.models.enumerations.ShapeType;

public interface ShapeRepository {
    Shape findOneByLatLng(Location location, ShapeType shapeType);
}
