package com.example.mapapi.controllers;

import com.example.mapapi.client.AuthApiClient;
import com.example.mapapi.models.*;
import com.example.mapapi.service.base.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class MapController {

    private ShapeService service;

    private AuthApiClient client;

    @Autowired
    public MapController(ShapeService service, AuthApiClient client) {
        this.service = service;
        this.client = client;
    }

    @PostMapping("/getpolygon")
    public Shape getPolygon(@RequestBody SearchRequest searchRequest) throws Exception {
        if(client.isLogged()) {
            Shape polygon = service.findByLatLng(searchRequest.getLocation(), searchRequest.getShapeType());
            return polygon;
        }
        return null;
    }
}
