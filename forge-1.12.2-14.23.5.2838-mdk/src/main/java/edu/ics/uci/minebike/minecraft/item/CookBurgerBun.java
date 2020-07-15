package edu.ics.uci.minebike.minecraft.item;


import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
public class CookBurgerBun extends Item {
    public CookBurgerBun()
    {
        setUnlocalizedName("hamburgerbun");
        setRegistryName("hamburgerbun");
//        ModelLoader.setCustomModelResourceLocation(this, 0, );
    }
    public void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}
