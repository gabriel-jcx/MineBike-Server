package edu.ics.uci.minebike.minecraft.client.hud;

public abstract class HudShape {
    protected int x;
    protected int y;
    protected boolean centerX;
    protected boolean centerY;
    protected int color;

    public abstract void draw();

    protected void setCenter(int x, int y){
        this.x = x;
        this.y = y;
    }
}
