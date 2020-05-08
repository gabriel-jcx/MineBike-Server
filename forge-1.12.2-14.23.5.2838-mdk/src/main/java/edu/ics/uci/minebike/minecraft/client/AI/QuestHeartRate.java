package edu.ics.uci.minebike.minecraft.client.AI;
import  org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
import  org.ngs.bigx.dictionary.objects.clinical.BiGXPatientInfo;

import java.util.ArrayList;

public class QuestHeartRate {
    //TODO:need input from prescription
    private float target_time_left;

    public ArrayList<Integer> heart_rate= new ArrayList<Integer>();
    public Integer avg=0;
    BiGXPatientPrescription p= new BiGXPatientPrescription();
    public QuestHeartRate(){

    }
    public void update_hr(int h){
        heart_rate.add(h);
    }
    private float calc_avg(){
        float s=0;
        for (int i = 0; i < heart_rate.size(); i++) {
            s+= heart_rate.get(i);
        }

        avg = (int)(s / heart_rate.size());
        return avg ;

    }




}
