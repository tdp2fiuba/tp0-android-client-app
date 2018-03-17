package com.ar.tdp2fiuba.tp0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ar.tdp2fiuba.tp0.model.City;

public class CitiesActivity extends AppCompatActivity implements CitiesFragment.OnCitiesFragmentTapListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
    }

    @Override
    public void onCitySelected(City city) {
        // TODO: 17/03/18 Implement!
    }
}
