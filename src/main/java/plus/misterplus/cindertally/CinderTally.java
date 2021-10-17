package plus.misterplus.cindertally;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import plus.misterplus.cindertally.network.CinderTallyPacketHandler;
import plus.misterplus.cindertally.registry.CinderTallyRegistry;

@Mod(CinderTallyConstants.MOD_ID)
public class CinderTally {
    public static final Logger LOGGER = LogManager.getLogger();

    public CinderTally() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
//        modEventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
//        modEventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
//        modEventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
//        modEventBus.addListener(this::doClientStuff);

        CinderTally.LOGGER.debug("Registering entries...");
        CinderTallyRegistry.register(modEventBus);
        CinderTally.LOGGER.debug("Entries registered!");

        CinderTally.LOGGER.debug("Registering packets...");
        CinderTallyPacketHandler.registerPackets();
        CinderTally.LOGGER.debug("Packets registered!");
    }

//    private void setup(final FMLCommonSetupEvent event)
//    {
//        // some preinit code
//    }

//    private void doClientStuff(final FMLClientSetupEvent event) {
//        // do something that can only be done on the client
//    }

//    private void enqueueIMC(final InterModEnqueueEvent event)
//    {
//        // some example code to dispatch IMC to another mod
//        InterModComms.sendTo("cindertally", "helloworld", () -> {
//            LOGGER.info("Hello world from the MDK");
//            return "Hello world";
//        });
//    }

//    private void processIMC(final InterModProcessEvent event)
//    {
//        // some example code to receive and process InterModComms from other mods
//        LOGGER.info("Got IMC {}", event.getIMCStream().
//                map(m->m.getMessageSupplier().get()).
//                collect(Collectors.toList()));
//    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(FMLServerStartingEvent event) {
//        // do something when the server starts
//        LOGGER.info("HELLO from server starting");
//    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
//    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
//    public static class RegistryEvents {
//        @SubscribeEvent
//        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
//            // register a new block here
//            LOGGER.info("HELLO from Register Block");
//        }
//    }
}
