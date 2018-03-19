package com.ar.tdp2fiuba.tp0.service;

import com.android.volley.Response;
import com.ar.tdp2fiuba.tp0.service.network.HttpRequestHelper;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public static void getWeather(String cityId, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        final String url = BASE_URL + "/forecast?id=" + cityId;
        HttpRequestHelper.get(
                url,
                null,
                successListener,
                errorListener,
                "GetCityWeather"
        );
    }
}
