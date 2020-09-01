package edu.ics.uci.minebike.minecraft.client.AI;
//import  org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
//import  org.ngs.bigx.dictionary.objects.clinical.BiGXPatientInfo;

import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;

//Abstract class for the AI
//import  org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
//import  org.ngs.bigx.dictionary.objects.clinical.BiGXPatientInfo;

//Abstract class for the AI
public class QuestHeartRate  {
    //Contain the prescription information, load from the Bigx
    //TODO:need input from prescription
    /**Target_time_left contains the remaining time that kid needs to play.
     * The time will decrease only if the kid's heart rate is in the target heart rate range and persisting it.
     * The function decrease_target_time modify this variable and check whether the kid is remaining heart rate.**/
    //if the target_time_left==0, the goal has been reached
    private float target_time_left;
    //Average heart rate
    protected Integer avg=0;
    //Prescription heart rate min & max
    protected Integer target_min=0;
    protected Integer target_max=0;
    //Total section time
    protected Integer time=0;
    public Integer current_heart_rate=0;
    //use to calculate the avg
    public Integer quest_heart_rate_sum=0; //
    //how many heart rate data has been loaded
    public Integer num_heart_rate=0; //
    //The avg heart rate return from mini quest, use to receive packet
    public static Integer quest_avg=0;
    private Integer check_remain=0;
    private Integer check_fail=0;

    protected BiGXPatientPrescription p= new BiGXPatientPrescription();
    public QuestHeartRate() {

    }
    //Only use this for outer AI

    protected void get_avg(){

    }

    @Deprecated
    public void setCurrent_heart_rate( Integer load){
        current_heart_rate=load;
        quest_heart_rate_sum+=current_heart_rate;
        num_heart_rate+=1;
        check_remaining();

    }
    //    public Integer getAvg() {
//
//        return avg;
//    }
    protected Integer calc_avg(){
        return (int)quest_heart_rate_sum/num_heart_rate;
    }
    protected  boolean reach_target(){
        Integer avg= calc_avg();
        if( avg<=target_max&&avg>=target_min){
            return true;
        }
        else {
            return false;
        }
    }
    //if remaining the heart rate for 5 count start to decrease the heart rate time
    protected void check_remaining(){
        if (current_heart_rate<=target_max && current_heart_rate>= target_min){
            check_remain+=1;
            check_fail=0;
        }
        else{
            check_fail+=1;
        }
        if (check_fail>=3){
            check_remain=0;
        }
        decrease_target_time();
    }
    protected void decrease_target_time(){
        if (check_remain>=5 && target_time_left>=1){
            target_time_left -=1;
        }
        if (target_time_left==0){
            System.out.println("GOAL REACHED!");
        }
    }





}
