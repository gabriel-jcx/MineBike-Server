package edu.ics.uci.minebike.minecraft.client;

import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class HudManager extends GuiScreen {
    private static HudManager instance = null;
    public ArrayList<HudRectangle> rectangles;
    public ArrayList<HudString> strings;
    private HudManager(){

    }
    public static HudManager getInstance(){
        if(instance == null)
            instance = new HudManager();
        return instance;
    }

}
