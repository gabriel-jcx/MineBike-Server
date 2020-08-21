package edu.ics.uci.minebike.minecraft.client;

import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudShape;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.client.hud.HudTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReentrantLock;

@SideOnly(Side.CLIENT)
public class HudManager extends GuiScreen {
    private static HudManager instance = null;
    public ArrayList<HudRectangle> rectangles = new ArrayList<>();
    public ArrayList<HudString> strings = new ArrayList<>();
    public ArrayList<HudShape> shapes = new ArrayList<>();
    public ArrayList<HudTexture> textures = new ArrayList<>();
//    public Minecraft mc;
    public static int mcWidth;
    public static int mcHeight;
    public ReentrantLock shape_lock = new ReentrantLock();
    private HudManager(Minecraft mc){
        this.mc = mc;
    }

    @Deprecated
    public void unregisterRectangle(HudRectangle rect){
        if(rectangles.contains(rect))
            rectangles.remove(rect);
        else
            System.err.println("Error: Removing a rectangle thats not registered");
    }
    @Deprecated
    public void unregisterString(HudString string){
        if(strings.contains(string))
            strings.remove(string);
        else
            System.err.println("Error: Removing a rectangle thats not registered");
    }

    public static HudManager getInstance(Minecraft mc){
        if(instance == null)
            instance = new HudManager(mc);
        return instance;
    }
    @SubscribeEvent
    synchronized void onGameOverlayChange(RenderGameOverlayEvent event){
        // return on Non-text change
        if(event.isCancelable() || event.getType() != event.getType().TEXT) return;
        updateResolution();
//        this.shape_lock.lock();
//        try {
            for (HudShape shape : shapes) {
                shape.draw();
            }
//        }finally{
//            this.shape_lock.unlock();
//        }

    }
    private void updateResolution(){
        ScaledResolution sr = new ScaledResolution(this.mc);
        mcWidth = sr.getScaledWidth();
        mcHeight = sr.getScaledHeight();
    }

}
