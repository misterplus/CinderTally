package plus.misterplus.cinderedtally.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import plus.misterplus.cinderedtally.CinderedTallyConstants;
import plus.misterplus.cinderedtally.network.packet.LocalePacket;
import plus.misterplus.cinderedtally.network.packet.ReckoningPacket;
import plus.misterplus.cinderedtally.network.packet.SReckoningPacket;

public class CinderedTallyPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(CinderedTallyConstants.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        int packetID = 0;
        INSTANCE.registerMessage(packetID++, LocalePacket.class, LocalePacket::encode, LocalePacket::decode, LocalePacket::handle);
        INSTANCE.registerMessage(packetID++, ReckoningPacket.class, ReckoningPacket::encode, ReckoningPacket::decode, ReckoningPacket::handle);
        INSTANCE.registerMessage(packetID++, SReckoningPacket.class, SReckoningPacket::encode, SReckoningPacket::decode, SReckoningPacket::handle);
    }
}
