package com.cookandroid.mainui;

public class Node {
    public String Region;

    public double Temper;
    public int Dust;
    public int Ddust;
    public int Humid;
    public String Time;

    public Node(String region, double temper,int dust,int ddust,int humid,String time){
        Region=region;
        Temper = temper;
        Dust = dust;
        Ddust = ddust;
        Humid = humid;
        Time = time;
    }
}
