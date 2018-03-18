package com.ar.tdp2fiuba.tp0.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ar.tdp2fiuba.tp0.fragment.CitiesFragment;
import com.ar.tdp2fiuba.tp0.R;
import com.ar.tdp2fiuba.tp0.model.City;
import com.google.gson.Gson;

public class CitiesActivity extends AppCompatActivity implements CitiesFragment.OnCitiesFragmentTapListener {

    public static final String CITY_SERIALIZED = "city_serialized";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
    }

    @Override
    public void onCitySelected(City city) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(CITY_SERIALIZED, new Gson().toJson(city));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
