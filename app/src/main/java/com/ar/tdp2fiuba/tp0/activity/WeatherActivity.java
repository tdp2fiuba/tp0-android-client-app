package com.ar.tdp2fiuba.tp0.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private City city = new City("3435910", "Buenos Aires", "0", "0", "AR"); //Default Buenos Aires
    private List<InfoWeather> daysInfo =  new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(city.getCityName());

        /*
        findViewById(R.id.action_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCitiesActivity();
            }
        });
        */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.reload);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openCitiesActivity();
                findWeatherInfo();
            }
        });

        findWeatherInfo();
        showDaysInfo();//daysLayout.setVisibility(View.GONE);
    }

    private void openCitiesActivity() {
        Intent intent = new Intent(this, CitiesActivity.class);
        startActivity(intent);
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

            //set day weather
            ImageView weatherDay = (ImageView) findViewById(getResources().getIdentifier("day_"+day+"_ico_day", "id", getPackageName()));
            weatherDay.setImageResource(getWeatherIconId(dayWeather.weatherDayIcon));

            //set temp night
            TextView tempNight = (TextView) findViewById(getResources().getIdentifier("day_"+day+"_temp_night", "id", getPackageName()));
            tempNight.setText(String.valueOf(dayWeather.getTempNight()));

            //set day weather
            ImageView weatherNight = (ImageView) findViewById(getResources().getIdentifier("day_"+day+"_ico_night", "id", getPackageName()));
            weatherNight.setImageResource(getWeatherIconId(dayWeather.weatherNightIcon));

        }

        showDaysInfo();
    }

    private int getWeatherIconId(String iconName) {
        int id = 0;
        if (iconName == null) {
            return id;
        }
        switch (iconName) {
            case "01d":
                id = R.drawable.icon_01d;
                break;
            case "01n":
                id = R.drawable.icon_01n;
                break;
            case "02d":
                id = R.drawable.icon_02d;
                break;
            case "02n":
                id = R.drawable.icon_02n;
                break;
            case "03d":
                id = R.drawable.icon_03d;
                break;
            case "03n":
                id = R.drawable.icon_03n;
                break;
            case "04d":
                id = R.drawable.icon_04d;
                break;
            case "04n":
                id = R.drawable.icon_04n;
                break;
            case "09d":
                id = R.drawable.icon_09d;
                break;
            case "09n":
                id = R.drawable.icon_09n;
                break;
            case "10d":
                id = R.drawable.icon_10d;
                break;
            case "10n":
                id = R.drawable.icon_10n;
                break;
            case "11d":
                id = R.drawable.icon_11d;
                break;
            case "11n":
                id = R.drawable.icon_11n;
                break;
            case "13d":
                id = R.drawable.icon_13d;
                break;
            case "13n":
                id = R.drawable.icon_13n;
                break;
            case "50d":
                id = R.drawable.icon_50d;
                break;
            case "50n":
                id = R.drawable.icon_50n;
                break;
        }
        return id;
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
        CitiesService.getWeather(city.id,successListener,errorListener);
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
