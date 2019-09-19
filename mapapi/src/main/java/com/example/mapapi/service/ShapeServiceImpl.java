package com.example.mapapi.service;

import com.example.mapapi.models.Location;
import com.example.mapapi.models.Polygon;
import com.example.mapapi.models.Polygons;
import com.example.mapapi.models.Shape;
import com.example.mapapi.models.enumerations.ShapeType;
import com.example.mapapi.repository.base.ShapeRepository;
import com.example.mapapi.service.base.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ShapeServiceImpl implements ShapeService {

    private ShapeRepository repository;

    @Autowired
    public ShapeServiceImpl(ShapeRepository repository) {
        this.repository = repository;
    }

    public Shape findByLatLng(Location location, ShapeType shapeType){

        Shape polygon = repository.findOneByLatLng(location, shapeType);

        return polygon;
    }
}
