package edu.ics.uci.minebike.minecraft.client.hud;

import edu.ics.uci.minebike.minecraft.client.HudManager;

public class HudRectangle extends HudShape {
    private int w;  // width
    private int h;  // height
    private int color;
    public HudRectangle(int x, int y, int w, int h, int color){
        this.setCenter(x,y);
        this.w = w;
        this.h = h;
        this.color = color;
        HudManager.getInstance().rectangles.add(this);
    }
    @Override
    public void draw(){

    }
}
