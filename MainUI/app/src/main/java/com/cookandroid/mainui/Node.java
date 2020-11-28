package com.cookandroid.mainui;

import java.util.ArrayList;
import com.cookandroid.mainui.EventClass;

public class Node {
    // 등록된 이벤트 목록
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
