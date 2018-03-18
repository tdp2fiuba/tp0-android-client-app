package com.ar.tdp2fiuba.tp0.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ar.tdp2fiuba.tp0.R;
import com.ar.tdp2fiuba.tp0.model.City;
import com.ar.tdp2fiuba.tp0.model.InfoWeather;
import com.ar.tdp2fiuba.tp0.service.CitiesService;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    private static final int REQ_CODE_CITIES = 1;

    private static final String SHP_KEY_CURRENT_CITY = "current_city";
    private City currentCity;

    private List<InfoWeather> daysInfo =  new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCurrentCityFromPreferences();

        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.reload);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findWeatherInfo();
            }
        });

        findWeatherInfo();
        showDaysInfo();
    }

    private void openCitiesActivity() {
        Intent intent = new Intent(this, CitiesActivity.class);
        startActivityForResult(intent, REQ_CODE_CITIES);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openCitiesActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_CITIES && resultCode == RESULT_OK) {
            String serializedCity = data.getStringExtra(CitiesActivity.CITY_SERIALIZED);
            currentCity = new Gson().fromJson(serializedCity, City.class);
            saveCurrentCityOnPreferences();
            findWeatherInfo();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loadCurrentCityFromPreferences() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultSerializedCity = gson.toJson(new City("3435910", "Buenos Aires", "AR"));
        String currentSerializedCity = sharedPreferences.getString(SHP_KEY_CURRENT_CITY, defaultSerializedCity);
        this.currentCity = gson.fromJson(currentSerializedCity, City.class);
    }

    private void saveCurrentCityOnPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(SHP_KEY_CURRENT_CITY, new Gson().toJson(currentCity)).apply();
    }

    private void errorOnLoadWeather(){
        if (daysInfo.isEmpty()){
            hideDaysInfo();
        }

        //TODO toast error message
        //Toast.makeText(getActivity(), "Error al cargar la información.",Toast.LENGTH_LONG).show();
    }

    private void loadInfo(){
        for (int i = 0; i < daysInfo.size() && i < 5; i++) {
            String day = String.valueOf(i + 1);
            InfoWeather dayWeather = daysInfo.get(i);

            //set name day
            TextView nameDay = (TextView) findViewById(getResources().getIdentifier("day_"+day+"_text", "id", getPackageName()));
            nameDay.setText(dayWeather.date);

            //set temp day
            TextView tempDay = (TextView) findViewById(getResources().getIdentifier("day_"+day+"_temp_day", "id", getPackageName()));
            tempDay.setText(String.valueOf(dayWeather.getTempDay()));

            //TODO set icon day

            //set temp night
            TextView tempNight = (TextView) findViewById(getResources().getIdentifier("day_"+day+"_temp_night", "id", getPackageName()));
            tempNight.setText(String.valueOf(dayWeather.getTempNight()));

            // TODO set icon night
        }

        showDaysInfo();
    }

    private void findWeatherInfo() {
        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray days = null;
                try {
                    days = response.getJSONArray("days");
                } catch (JSONException e) {
                    errorOnLoadWeather();
                    return;
                }
                for (int i = 0; i < days.length(); i++) {
                    try {
                        daysInfo.add(new Gson().fromJson(days.getJSONObject(i).toString(), InfoWeather.class));
                    } catch (JSONException e) {
                        errorOnLoadWeather();
                        return;
                    }
                }
                loadInfo();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorOnLoadWeather();
            }
        };
        CitiesService.getWeather(currentCity.id, successListener,errorListener);
    }

    private void hideDaysInfo(){
        final LinearLayout daysLayout = (LinearLayout) findViewById(R.id.days_layout);
        daysLayout.setVisibility(View.GONE);
    }

    private void showDaysInfo(){
        final LinearLayout daysLayout = (LinearLayout) findViewById(R.id.days_layout);
        daysLayout.setVisibility(View.VISIBLE);
    }
}
