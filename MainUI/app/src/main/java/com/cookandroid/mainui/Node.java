package com.cookandroid.mainui;

public class Node {
    Region region;
    double temper;
    int dust;
    int ddust;
    int humid;
    String time;

    public Node(Region Region, double Temper,int Dust,int Ddust,int Humid,String Time){
        region=Region;
        temper = Temper;
        dust = Dust;
        ddust = Ddust;
        humid = Humid;
        time = Time;
    }
}
