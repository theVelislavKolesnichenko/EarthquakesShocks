package com.example.mapapi.repository;

import com.example.mapapi.helpers.LocationJsonDeserializer;
import com.example.mapapi.helpers.SoilColorSerializer;
import com.example.mapapi.models.*;
import com.example.mapapi.models.enumerations.ShapeType;
import com.example.mapapi.models.enumerations.ShapeColor;
import com.example.mapapi.repository.base.ShapeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class ShapeRepositoryImpl implements ShapeRepository {
    private static final String NO_RESULT_FOUND = "No data found for this location...";

    private String url;
    private String username;
    private String password;
    private ObjectMapper jsonMapper;

    @Autowired
    ShapeRepositoryImpl(Environment environment, ObjectMapper jsonMapper) {
        this.url = environment.getProperty("spring.datasource.url");
        this.username = environment.getProperty("spring.datasource.username");
        this.password = environment.getProperty("spring.datasource.password");
        this.jsonMapper = jsonMapper;
    }

    @Override
    public Shape findOneByLatLng(Location location, ShapeType shapeType) {

        Shape polygons = new Shape();

        String format = null;
        switch (shapeType)  {
            case Soil:
            {
                format = "SELECT json_build_object('polygons', ST_ASGEOJSON(dsmw.geom)::json, 'polygonInfo', json_build_object('title', 'Soil type', 'description', concat('[\"faosoil ', dsmw.faosoil, ', domsoi ', dsmw.domsoi, '\",\"Country ', dsmw.country, '\",\"', ROUND(dsmw.sqkm,2)::text, ' sq. km.', '\"]')::json,'color', dsmw.domsoi)) AS json FROM dsmw WHERE ST_CONTAINS(dsmw.geom, ST_MAKEPOINT(%.14f, %.14f));";
            }
                break;
            case Earthquake:
            {
                format = "SELECT json_build_object('polygons', ST_ASGEOJSON(gdeqk.geom)::json, 'polygonInfo', json_build_object('title', 'Magnitude', 'description', concat('[\"', gdeqk.dn::text, '\"]')::json, 'color', concat('E',gdeqk.dn)::text)) AS json FROM gdeqk WHERE ST_CONTAINS(gdeqk.geom, ST_MAKEPOINT(%.14f, %.14f));";

            }
                break;
        }

        String sql = String.format(Locale.US, format, location.getLongitude(), location.getLatitude());
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println(NO_RESULT_FOUND);
                throw new EmptyResultDataAccessException(NO_RESULT_FOUND, 1);
            }

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {

                        String json = resultSet.getString("json");

                        Object gson = new Gson().fromJson(json, Object.class);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(Location.class, new LocationJsonDeserializer());
                        gsonBuilder.registerTypeAdapter(ShapeColor.class, new SoilColorSerializer());

                        Gson gson1 = gsonBuilder.create();
                        polygons = gson1.fromJson(json, Shape.class);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

         return polygons;
    }
}
