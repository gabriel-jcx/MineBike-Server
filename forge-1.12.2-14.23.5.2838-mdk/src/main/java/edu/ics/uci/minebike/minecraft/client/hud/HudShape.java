package edu.ics.uci.minebike.minecraft.client.hud;

import edu.ics.uci.minebike.minecraft.client.HudManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.collection.parallel.ParIterableLike;

import java.util.concurrent.locks.ReentrantLock;

@SideOnly(Side.CLIENT)
public abstract class HudShape extends GuiScreen {
    public int x;
    public int y;
    protected boolean centerX;
    protected boolean centerY;
    protected int color;
    protected ReentrantLock lock;
    protected HudShape(){
        lock = new ReentrantLock();
    }
    public abstract void draw();

    protected void setCenter(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getColor() {
        return this.color;
    }
//    public void setColor(int color){
//        this.color = color;
//    }

    synchronized public boolean unregister(){
//        ReentrantLock sharedLock = HudManager.getInstance(Minecraft.getMinecraft()).shape_lock;
//        sharedLock.lock();
//        try {
            if (HudManager.getInstance(mc).shapes.contains(this)) {
                HudManager.getInstance(mc).shapes.remove(this);
                return true;
            }
//        }finally {
//        }
        return false;
    }
}
