package plus.misterplus.cinderedtally.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;
import plus.misterplus.cinderedtally.client.gui.screen.OutOfTimeScreen;
import plus.misterplus.cinderedtally.network.packet.LocalePacket;
import plus.misterplus.cinderedtally.network.packet.ReckoningPacket;
import plus.misterplus.cinderedtally.network.packet.SReckoningPacket;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class CinderedTallyClientPacketHandler {
    public static void handleReckoningPacket(ReckoningPacket packet, Supplier<NetworkEvent.Context> ctx) {
        //  client sends back the locale packet
        CinderedTallyPacketHandler.INSTANCE.sendToServer(new LocalePacket());
    }

    public static void handleSReckoningPacket(SReckoningPacket packet, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().setScreen(new OutOfTimeScreen());
    }
}
