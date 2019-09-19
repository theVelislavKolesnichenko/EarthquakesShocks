package com.example.mapapi.helpers;

import com.example.mapapi.models.Location;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LocationJsonDeserializer implements JsonDeserializer<Location> {

    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Double> arr = new Gson().fromJson(json, ArrayList.class);
        Location location = new Location(arr.get(1), arr.get(0));
        return location;
    }
}