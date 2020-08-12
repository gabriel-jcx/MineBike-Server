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
    public void onGameOverlayChange(RenderGameOverlayEvent event){
        // return on Non-text change
        if(event.isCancelable() || event.getType() != event.getType().TEXT) return;


        updateResolution();
        for(HudShape shape : shapes){
//            if(shape instanceof  HudString){
//                HudString str = (HudString)shape;
//                System.out.println(str.text);
//            }
            shape.draw();
        }
////        System.out.println("not returned");
//        for(HudRectangle rect: rectangles)
//            rect.draw();
//        for(HudString str: strings){
//            str.draw();
//        }
    }
    private void updateResolution(){
        ScaledResolution sr = new ScaledResolution(this.mc);
        mcWidth = sr.getScaledWidth();
        mcHeight = sr.getScaledHeight();
    }

}
