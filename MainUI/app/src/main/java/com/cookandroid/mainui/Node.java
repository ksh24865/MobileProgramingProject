package com.cookandroid.mainui;

public class Node {
    Region region;
    double temper;
    double dust;
    double ddust;
    double humid;
    String time;

    public Node(Region Region, double Temper,double Dust,double Ddust,double Humid,String Time){
        region=Region;
        temper = Temper;
        dust = Dust;
        ddust = Ddust;
        humid = Humid;
        time = Time;
    }
    public Region getRegion() {
        return region;
    }
    public double getTemper() {
        return temper;
    }
    public double getDust() {
        return dust;
    }
    public double getDdust() {
        return ddust;
    }
    public double getHumid() {
        return humid;
    }
    public String getTime() {
        return time;
    }
}
