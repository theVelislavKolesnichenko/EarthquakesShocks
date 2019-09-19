package com.example.util;

import com.example.models.elasticsearch.Activity;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.PrintWriter;
import java.util.ArrayList;

public class WriteCsvToResponse {

    public static void writeCities(PrintWriter writer, ArrayList<Activity> data)  {

            ColumnPositionMappingStrategy mapStrategy = new ColumnPositionMappingStrategy();

            mapStrategy.setType(Activity.class);
            mapStrategy.generateHeader();

            String[] columns = new String[]{"event", "username", "date", "ipAddress"};
            mapStrategy.setColumnMapping(columns);

            StatefulBeanToCsv btcsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mapStrategy)
                    .withSeparator(',')
                    .build();

        try {
            btcsv.write(data);
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }

    }
}
