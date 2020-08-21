package edu.ics.uci.minebike.minecraft.client.hud;

import edu.ics.uci.minebike.minecraft.client.HudManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.util.concurrent.locks.ReentrantLock;

@SideOnly(Side.CLIENT)
public class HudRectangle extends HudShape {
    private int w;  // width
    private int h;  // height
    private int color;
    //private ReentrantLock lock;
    public HudRectangle(int x, int y, int w, int h, int color, boolean centerX, boolean centerY){
        super();
        this.centerX = centerX;
        this.centerY = centerY;
        this.setCenter(x,y);
        this.w = w;
        this.h = h;
        this.color = color;
        HudManager.getInstance(mc).shapes.add(this);
    }

    public int getX(){
        return this.x;
    }

    public void setWidth(int width){
        this.lock.lock();
        try{
            w = width;
        }finally {
            lock.unlock();
        }
    }

    public void setHeight(int height){
        this.lock.lock();
        try{
            h = height;
        }finally {
            lock.unlock();
        }
    }

    //@Override
    @Deprecated
    protected void drawRectangle(int x1, int y1, int x2, int y2, int color)
    {
        int temp;

        //swaps if it's in the wrong orientation
        if (x1 < x2)
        {
            temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 < y2)
        {
            temp = y1;
            y1 = y2;
            y2 = temp;
        }
        //rgba masks
        float r = (float)(color >> 24 & 255) / 255.0F;
        float g = (float)(color >> 16 & 255) / 255.0F;
        float b = (float)(color >> 8  & 255) / 255.0F;
        float a = (float)(color >> 0  & 255) / 255.0F;

        //gl stuff
        Tessellator tessellator = Tessellator.getInstance();
        GL11.glPushAttrib(GL11.GL_COLOR);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glColor4f(r, g, b, a);

        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        buffer.pos(x1, y2, 0.0D).endVertex();

        buffer.pos(x2, y2, 0.0D).endVertex();
        buffer.pos(x2, y1, 0.0D).endVertex();
        buffer.pos(x1, y1, 0.0D).endVertex();

        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }


    @Override
    public void draw(){
        int offsetX = (this.centerX ? HudManager.mcWidth/2 : 0);
        int offsetY = (this.centerY ? HudManager.mcHeight/2 : 0);
        this.drawRect(
                this.x + offsetX,
                this.y + offsetY,
                this.x + this.w + offsetX,
                this.y + this.h + offsetY,
                this.color
        );

    }

}
