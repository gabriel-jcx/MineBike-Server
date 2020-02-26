package org.ngs.bigx.server;

import org.spongepowered.api.event.world.SaveWorldEvent;
import org.spongepowered.api.event.world.LoadWorldEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

import org.spongepowered.api.event.Listener;

public class CommonEventHandler {

    @Listener
    public void onWorldSave(SaveWorldEvent event){
        System.out.println("SaveWorldEvent Triggered");
    }
    @Listener
    public void onWorldLoad(LoadWorldEvent event){
        System.out.printf("LoadWorldEvent Triggered");
    }
    @Listener
    public void onEntitySpawn(SpawnEntityEvent event){
        System.out.println("SpawnEntityEvenet Triggered");
    }

}
