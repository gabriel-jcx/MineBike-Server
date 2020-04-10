package edu.ics.uci.minebike.minecraft.client.hud;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class HudShape extends GuiScreen {
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
