package net.tepiloxtl.volumebetaextension;


import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.mod.entrypoint.EventBusPolicy;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

@Entrypoint(eventBus = @EventBusPolicy(registerInstance = false))
public class VolumeBetaExtension {
    @SuppressWarnings("UnstableApiUsage")
    public static final Namespace NAMESPACE = Namespace.resolve();

    public static final Logger LOGGER = NAMESPACE.getLogger();

    @EventListener
    public static void init(InitEvent event) {
        LOGGER.info("Volume Beta Menu Music initializing!");

        // Register our custom menu music manager
        MenuMusicManager.init();
    }
}