package edu.ics.uci.minebike.minecraft.quests.customQuests;

public class GoalRectangle {
    public int x1;
    public int x2;
    public int y1;
    public int y2;

    // NOTE: swap the x,y values for the rectangle for the checking logic to work
    public GoalRectangle(int x1, int y1, int x2, int y2){
        if(y1 > y2){
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        if(x1 > x2){
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

}
