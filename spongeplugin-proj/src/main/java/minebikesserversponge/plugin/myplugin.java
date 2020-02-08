package minebikesserversponge.plugin;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "plugin",
        name = "multiplayergame"
)
public class myplugin {

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("myplugin started");
    }

    @Listener
    public void onGameInit(GameInitializationEvent event){
        logger.info("asdfasdfsdfsfasdfasdfas");
    }
}
