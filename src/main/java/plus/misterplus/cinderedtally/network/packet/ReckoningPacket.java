package plus.misterplus.cinderedtally.network.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import plus.misterplus.cinderedtally.network.CinderedTallyClientPacketHandler;

import java.util.function.Supplier;

/**
 * Packet responsible for informing the client that's out of time.<br>
 * No additional info is needed.
 */
public class ReckoningPacket {

    public static void encode(ReckoningPacket packet, PacketBuffer packetBuffer) {
    }

    public static ReckoningPacket decode(PacketBuffer packetBuffer) {
        return new ReckoningPacket();
    }

    public static void handle(ReckoningPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CinderedTallyClientPacketHandler.handleReckoningPacket(packet, ctx));
        });
        ctx.get().setPacketHandled(true);
    }
}
