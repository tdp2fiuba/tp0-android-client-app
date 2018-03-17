package com.ar.tdp2fiuba.tp0.service;

import com.ar.tdp2fiuba.tp0.model.City;

import java.util.LinkedList;
import java.util.List;

// TODO: 17/03/18 Perform actual requests to API service.
public class CitiesService {
    public static List<City> getAllCities() {
        // TODO: 17/03/18 Implement!
        List<City> cities = new LinkedList<>();
        cities.add(new City("1", "Nueva York", "28", "10800"));
        cities.add(new City("2", "Buenos Aires", "20", "10400"));
        return cities;
    }

    public static City getCity(String id) {
        // TODO: 17/03/18 Implement!
        return new City("1", "Nueva York", "28", "10800");
    }
}
