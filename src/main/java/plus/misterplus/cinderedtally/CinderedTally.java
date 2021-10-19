package plus.misterplus.cinderedtally;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import plus.misterplus.cinderedtally.network.CinderedTallyPacketHandler;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

@Mod(CinderedTallyConstants.MOD_ID)
public class CinderedTally {
    public static final Logger LOGGER = LogManager.getLogger();

    public CinderedTally() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CinderedTally.LOGGER.debug("Registering entries...");
        CinderedTallyRegistry.register(modEventBus);
        CinderedTally.LOGGER.debug("Entries registered!");

        CinderedTally.LOGGER.debug("Registering packets...");
        CinderedTallyPacketHandler.registerPackets();
        CinderedTally.LOGGER.debug("Packets registered!");
    }
}
