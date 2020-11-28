package com.cookandroid.mainui;

public class EventClass implements Cloneable{
    public String location;
    public String sensor;
    public double min;
    public double max;
    public double time;

    public EventClass() {
        location = "";
        sensor = "";
        min=0.0;
        max=0.0;
        time=0.0;
    }
    public EventClass(String _loc, String _sensor, double _min, double _max, double _time){
        location = _loc;
        sensor = _sensor;
        min = _min;
        max = _max;
        time = _time;
    }

    public String getLocation() {
        return this.location;
    }

    public String getSensor() {
        return this.sensor;
    }
}
