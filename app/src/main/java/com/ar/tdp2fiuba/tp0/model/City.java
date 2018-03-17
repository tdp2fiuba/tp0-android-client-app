package com.ar.tdp2fiuba.tp0.model;

public class City {
    public final String id;
    public final String name;
    public final String tempCelsius;
    public final String humHpa;

    public City(String id, String name, String tempCelsius, String humHpa) {
        this.id = id;
        this.name = name;
        this.tempCelsius = tempCelsius;
        this.humHpa = humHpa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != null ? !id.equals(city.id) : city.id != null) return false;
        return name != null ? name.equals(city.name) : city.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tempCelsius='" + tempCelsius + '\'' +
                ", humHpa='" + humHpa + '\'' +
                '}';
    }
}
