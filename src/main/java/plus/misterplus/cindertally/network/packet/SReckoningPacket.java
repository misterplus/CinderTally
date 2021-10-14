package plus.misterplus.cindertally.network.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import plus.misterplus.cindertally.network.CinderTallyClientPacketHandler;

import java.util.function.Supplier;

public class SReckoningPacket {
    public static void encode(SReckoningPacket packet, PacketBuffer packetBuffer) {
    }

    public static SReckoningPacket decode(PacketBuffer packetBuffer) {
        return new SReckoningPacket();
    }

    public static void handle(SReckoningPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CinderTallyClientPacketHandler.handleSReckoningPacket(packet, ctx));
        });
        ctx.get().setPacketHandled(true);
    }
}
