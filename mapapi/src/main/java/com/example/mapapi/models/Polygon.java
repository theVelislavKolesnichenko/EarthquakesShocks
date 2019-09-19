package com.example.mapapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Polygon extends ArrayList<Location> {
    public Polygon() {
    }
}
