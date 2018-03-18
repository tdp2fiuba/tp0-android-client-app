package com.ar.tdp2fiuba.tp0.model;

public class InfoWeather {
    /* info weather for a day */
    public final double tempDay;// temperatura promedio durante el día,
    public final double tempNight;// temperatura promedio durante la noche,
    public final String weatherDayMain;// descripción general del tiempo durante el día,
    public final String weatherDayDesc;// descripción particular del tiempo durante el día,
    public final String weatherDayIcon;// ícono representativo del tiempo durante el día,
    public final String weatherNightMain;// descripción general del tiempo durante la noche,
    public final String weatherNightDesc;// descripción particular del tiempo durante la noche,
    public final String weatherNightIcon;// ícono representativo del tiempo durante noche,
    public final double humidityDay;// porcentaje de humedad durante el día,
    public final double humidityNight;// porcentaje de humedad durante la noche,
    public final String date;// fecha del día

    public InfoWeather (double tempDay ,double tempNight, String weatherDayMain, String weatherDayDesc,
                        String weatherDayIcon, String weatherNightMain, String weatherNightDesc,
                        String weatherNightIcon, double humidityDay, double humidityNight,
                        String date){
        this.tempDay = tempDay;
        this.tempNight = tempNight;
        this.weatherDayMain = weatherDayMain;
        this.weatherDayDesc = weatherDayDesc;
        this.weatherDayIcon = weatherDayIcon;
        this.weatherNightMain = weatherNightMain;
        this.weatherNightDesc = weatherNightDesc;
        this.weatherNightIcon = weatherNightIcon;
        this.humidityDay = humidityDay;
        this.humidityNight = humidityNight;
        this.date = date;
    }
}

