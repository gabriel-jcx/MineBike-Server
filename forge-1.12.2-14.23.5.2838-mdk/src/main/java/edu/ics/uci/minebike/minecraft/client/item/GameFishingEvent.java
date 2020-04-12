package edu.ics.uci.minebike.minecraft.client.item;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.theawesomegem.fishingmadebetter.common.event.FishingEventHandler;
public class GameFishingEvent extends FishingEventHandler {
    public GameFishingEvent(){
        super();
    }

    public void onPlayerFish(ItemFishedEvent e) {
        e.getListenerList().unregister();
    }
}
