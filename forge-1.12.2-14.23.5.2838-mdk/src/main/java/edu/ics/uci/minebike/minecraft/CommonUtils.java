package edu.ics.uci.minebike.minecraft;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.village.MerchantRecipeList;
import noppes.npcs.LogWriter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static noppes.npcs.Server.writeString;

public class CommonUtils {
    public static boolean fillBuffer(ByteBuf buffer, Enum enu, Object... obs) throws IOException {
        buffer.writeInt(enu.ordinal());
        Object[] var3 = obs;
        int var4 = obs.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Object ob = var3[var5];
            if (ob != null) {
                Iterator var8;
                String s;
                if (ob instanceof Map) {
                    Map<String, Integer> map = (Map)ob;
                    buffer.writeInt(map.size());
                    var8 = map.keySet().iterator();

                    while(var8.hasNext()) {
                        s = (String)var8.next();
                        int value = (Integer)map.get(s);
                        buffer.writeInt(value);
                        writeString(buffer, s);
                    }
                } else if (ob instanceof MerchantRecipeList) {

                    //((MerchantRecipeList)ob).func_151391_a(new PacketBuffer(buffer));
                } else if (ob instanceof List) {
                    List<String> list = (List)ob;
                    buffer.writeInt(list.size());
                    var8 = list.iterator();

                    while(var8.hasNext()) {
                        s = (String)var8.next();
                        writeString(buffer, s);
                    }
                } else if (ob instanceof UUID) {
                    writeString(buffer, ob.toString());
                } else if (ob instanceof Enum) {
                    buffer.writeInt(((Enum)ob).ordinal());
                } else if (ob instanceof Integer) {
                    buffer.writeInt((Integer)ob);
                } else if (ob instanceof Boolean) {
                    buffer.writeBoolean((Boolean)ob);
                } else if (ob instanceof String) {
                    writeString(buffer, (String)ob);
                } else if (ob instanceof Float) {
                    buffer.writeFloat((Float)ob);
                } else if (ob instanceof Long) {
                    buffer.writeLong((Long)ob);
                } else if (ob instanceof Double) {
                    buffer.writeDouble((Double)ob);
                } else if (ob instanceof NBTTagCompound) {
                    System.err.println("Error this hasn't been implemented yet");
                    //writeNBT(buffer, (NBTTagCompound)ob);
                }
            }
        }

        if (buffer.array().length >= 65534) {
            LogWriter.error("Packet " + enu + " was too big to be send");
            return false;
        } else {
            return true;
        }
    }
}
