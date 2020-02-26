package org.ngs.bigx.server;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import noppes.npcs.api.entity.IEntity;

import javax.inject.Inject;

@Plugin(id = "minbikeservermainplugin",
        name = "Mine Bike Server Plugin",
        version = "0.1",
        description= "This is the sponge plugin for MineBike server for multiplayer")
public class MainPlugin {
    @Inject
    private org.slf4j.Logger Logger;


    @Listener
    public void onServerConstruct(GameConstructionEvent event){
        Logger.info("Server Constructing");
    }

    @Listener
    public void onServerInit(GameInitializationEvent event){
        Logger.info("Server Initializing");
    }
    @Listener
    public void onServerStart(GameStartedServerEvent event){
        Logger.info("Server Started");
    }
}
