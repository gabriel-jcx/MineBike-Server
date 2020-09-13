package edu.ics.uci.minebike.minecraft.client.hud;

import edu.ics.uci.minebike.minecraft.client.HudManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class HudString extends HudShape {
    private int x;
    private int y;
    private boolean shadow;
    private float scale;
    private String text;
    private int color;

    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public String getText(){
        return this.text;
    }


    @Override
    public void draw() {
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
        int offset_x = (int)(this.x + (this.centerX ?
                        (HudManager.mcWidth/2-fontRenderer.getStringWidth(this.text)/2 * this.scale)
                        : 0));
        int offset_y = (this.y + (this.centerY ? HudManager.mcHeight/2 : 0));
        this.drawString(fontRenderer,this.text,offset_x,offset_y,this.color);
    }
    /**
     * takes: <br>
     * top left x, y, <br>
     * location - should be located in assets.minebike.textures.gui
     * , and put in constructor like this: textures/GUI/*.png
     */
    public HudString(int x, int y, String text)
    {
        super();
        this.x = x;
        this.y = y;

        this.scale = 1.0f;

        this.centerX = false;
        this.centerY = false;

        this.color = 0xffffffff;

        this.text = text;
        this.shadow = true;
        HudManager.getInstance(mc).shapes.add(this);
    }

    /**
     * takes: <br>
     * top left
     * x, y, <br>
     * text - the text to be drawn<br> <br>
     * centerX and centerY - these turn the starting x and y into relative coordinates from the center
     * of the screen vertically or horizontally
     * <br>
     * i.e. centerX is true and screen resolution is 1920x1080, and x is -20,
     * the rectangle's top left corner's x coordinate will be (1920/2) - 20 = 940
     */
    public HudString(int x, int y, String text, boolean centerX, boolean centerY)
    {
        super();
        this.x = x;
        this.y = y;

        this.scale = 1.0f;

        this.centerX = centerX;
        this.centerY = centerY;

        this.text = text;

        this.color = 0xffffffff;
        this.shadow = true;
        HudManager.getInstance(mc).shapes.add(this);
    }



    /**
     * takes: <br>
     * top left
     * x, y, <br>
     * text - the text to be drawn<br> <br>
     * centerX and centerY - these turn the starting x and y into relative coordinates from the center
     * of the screen vertically or horizontally <br>
     * scale - the scale of the font to be drawn at, larger than 1.0f makes it bigger
     * <br>
     * i.e. centerX is true and screen resolution is 1920x1080, and x is -20,
     * the rectangle's top left corner's x coordinate will be (1920/2) - 20 = 940
     */
    public HudString(int x, int y, String text, float scale, boolean centerX, boolean centerY)
    {
        super();
        this.x = x;
        this.y = y;

        this.scale = scale;

        this.centerX = centerX;
        this.centerY = centerY;

        this.text = text;

        this.color = 0xffffffff;
        this.shadow = true;
        HudManager.getInstance(mc).shapes.add(this);
    }

    /**
     * takes: <br>
     * top left
     * x, y, <br>
     * text - the text to be drawn<br> <br>
     * centerX and centerY - these turn the starting x and y into relative coordinates from the center
     * of the screen vertically or horizontally <br>
     * scale - the scale of the font to be drawn at, larger than 1.0f makes it bigger
     * color - in the format 0xRRGGBB__ the last two hex digits don't matter
     * <br>
     * i.e. centerX is true and screen resolution is 1920x1080, and x is -20,
     * the rectangle's top left corner's x coordinate will be (1920/2) - 20 = 940
     */
    public HudString(int x, int y, String text, float scale, int color, boolean centerX, boolean centerY)
    {
        super();
        this.x = x;
        this.y = y;

        this.scale = scale;

        this.centerX = centerX;
        this.centerY = centerY;

        this.text = text;
        System.out.println("String is " +  text);
        this.color = color;
        this.shadow = false;
        HudManager.getInstance(mc).shapes.add(this);
    }


    synchronized public void setScale(float scale){
//        lock.lock();
//        try {
            this.scale = scale;
//        }finally {
//            lock.unlock();
//        }
    }
    synchronized public void setText(String text){
//        lock.lock();
//        try{
            this.text = text;
//        }finally{
//            lock.unlock();
//        }
    }
    synchronized public void setColor(int color){
//        lock.lock();
//        try{
            this.color = color;
//        }finally{
//            lock.unlock();
//        }
    }
    synchronized public void setX(int x){
//        lock.lock();
//        try{
            this.x = x;
//        }finally{
//            lock.unlock();
//        }
    }
    synchronized public void setY(int y){
//        lock.lock();
//        try{
            this.y = y;
//        }finally{
//            lock.unlock();
//        }
    }

}
