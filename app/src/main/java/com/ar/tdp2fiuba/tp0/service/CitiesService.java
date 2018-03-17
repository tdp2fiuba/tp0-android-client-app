package com.ar.tdp2fiuba.tp0.service;

import com.android.volley.Response;
import com.ar.tdp2fiuba.tp0.model.City;
import com.ar.tdp2fiuba.tp0.service.network.HttpRequestHelper;

import org.json.JSONArray;

public class CitiesService {

    private static final String BASE_URL = "https://fiuba-7547-tp0.herokuapp.com/api";

    public static void getCities(int page, int count, Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener) {
        final String url = BASE_URL + "/cities?page=:page&count=:count"
                .replace(":page", String.valueOf(page))
                .replace(":count", String.valueOf(count));
        HttpRequestHelper.getArray(
                url,
                null,
                successListener,
                errorListener,
                "GetCities"
        );
    }

    public static City getCity(String id) {
        // TODO: 17/03/18 Implement!
        return new City("1", "Nueva York", "28.123", "109.12", "USA");
    }
}
