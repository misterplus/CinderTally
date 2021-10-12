package plus.misterplus.cindertally.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.network.NetworkEvent;
import plus.misterplus.cindertally.client.gui.screen.OutOfTimeScreen;
import plus.misterplus.cindertally.network.packet.LocalePacket;
import plus.misterplus.cindertally.network.packet.ReckoningPacket;
import plus.misterplus.cindertally.network.packet.SReckoningPacket;

import java.util.function.Supplier;

public class CinderTallyClientPacketHandler {
    public static void handleReckoningPacket(ReckoningPacket packet, Supplier<NetworkEvent.Context> ctx) {
        //  client sends back the locale packet
        CinderTallyPacketHandler.INSTANCE.sendToServer(new LocalePacket());
    }

    public static void handleSReckoningPacket(SReckoningPacket packet, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().setScreen(new OutOfTimeScreen());
    }
}
