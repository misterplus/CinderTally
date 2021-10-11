package plus.misterplus.cindertally;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import plus.misterplus.cindertally.common.item.ItemLifeSpan;
import plus.misterplus.cindertally.handler.PlayerEventHandler;
import plus.misterplus.cindertally.helper.RegistryHelper;

import java.util.function.Supplier;
import java.util.stream.Collectors;

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

        CinderTally.LOGGER.debug("Registering items...");
        RegistryHelper.registerItems().register(modEventBus);
        CinderTally.LOGGER.debug("Items registered!");

        CinderTally.LOGGER.debug("Registering event buses...");
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        CinderTally.LOGGER.debug("Event buses registered!");
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
