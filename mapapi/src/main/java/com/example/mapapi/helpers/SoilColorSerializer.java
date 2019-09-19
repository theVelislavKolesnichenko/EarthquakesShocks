package com.example.mapapi.helpers;

import com.example.mapapi.models.enumerations.ShapeColor;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SoilColorSerializer implements JsonSerializer<ShapeColor> {

    @Override
    public JsonElement serialize(ShapeColor src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.toString());
    }
}