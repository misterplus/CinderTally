package plus.misterplus.cindertally.network;

import net.minecraftforge.fml.network.NetworkEvent;
import plus.misterplus.cindertally.network.packet.LocalePacket;
import plus.misterplus.cindertally.network.packet.ReckoningPacket;

import java.util.function.Supplier;

public class CinderTallyClientPacketHandler {
    public static void handlePacket(ReckoningPacket packet, Supplier<NetworkEvent.Context> ctx) {
        //  client sends back the locale packet
        CinderTallyPacketHandler.INSTANCE.sendToServer(new LocalePacket());
    }
}
