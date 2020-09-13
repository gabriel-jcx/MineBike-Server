package edu.ics.uci.minebike.minecraft.client.hud;

import edu.ics.uci.minebike.minecraft.BiGXMain;
import edu.ics.uci.minebike.minecraft.client.HudManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;


@SideOnly(Side.CLIENT)
//
public class HudTexture extends HudShape{
    private int x;
    private int y;
    private int w;
    private int h;
    private int alpha;
    private ResourceLocation resourceLocation;

    public HudTexture(int x, int y, int w, int h, String location, int alpha)
    {
        super();
        this.x = x;
        this.y = y;

        this.w = w;
        this.h = h;

        this.centerX = false;
        this.centerY = false;

        this.resourceLocation = new ResourceLocation(BiGXMain.MOD_ID, location);

        this.alpha = alpha;
        HudManager.getInstance(mc).shapes.add(this);

    }

    public HudTexture(int x, int y, int w, int h, String location, boolean centerX, boolean centerY, int alpha)
    {
        super();
        this.x = x;
        this.y = y;

        this.w = w;
        this.h = h;

        this.centerX = centerX;
        this.centerY = centerY;

        this.resourceLocation = new ResourceLocation(BiGXMain.MOD_ID, location);

        this.alpha = alpha;
        HudManager.getInstance(mc).shapes.add(this);

    }

    @Override
    public void draw(){
        int offX = centerX ? HudManager.mcWidth/2 : 0;
        int offY = centerY ? HudManager.mcHeight/2 : 0;
        drawTexture(this.x + offX,
                this.y + offY,
                this.w,
                this.h,
                this.alpha,
                this.resourceLocation);
    }

    public void drawTexture(int x, int y, int w, int h, int alpha, ResourceLocation location)
    {
        Tessellator tessellator = Tessellator.getInstance();
        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
        GL11.glPushAttrib(GL11.GL_COLOR);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, (alpha * 1.0f / 255.0f));

        BufferBuilder buff = tessellator.getBuffer();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        //this is where it becomes drawn
        Minecraft.getMinecraft().getTextureManager().bindTexture(location);
        buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buff.pos(x,y,0.0D).tex(0.0d,0.0d).endVertex();
        buff.pos(x,y + h,0.0D).tex(0.0d,1.0d).endVertex();
        buff.pos(x + w,y + h,0.0D).tex(1.0d,1.0d).endVertex();
        buff.pos(x + w,y,0.0D).tex(1.0d,0.0d).endVertex();

        tessellator.draw();

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopAttrib();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public int getW(){return this.w;}
    public int getH(){return this.h;}
    public int getAlpha(){return this.alpha;}

    public void setX(int x){
//        lock.lock();
//        try{
            this.x = x;
//        }finally{
//            lock.unlock();
//        }
    }
    public void setY(int y){
//        lock.lock();
//        try{
            this.y = y;
//        }finally{
//            lock.unlock();
//        }
    }
    synchronized void setWidth(int w){
//        lock.lock();
//        try{
            this.w = w;
//        }finally{
//            lock.unlock();
//        }
    }
    synchronized public void setHeight(int h){
//        lock.lock();
//        try{
            this.h = h;
//        }finally{
//            lock.unlock();
//        }
    }
    synchronized public void setAlpha(int alpha){
//        lock.lock();
//        try{
            this.alpha = alpha;
//        }finally{
//            lock.unlock();
//        }
    }
    synchronized public void setScale(int scale){
        lock.lock();

    }
}
