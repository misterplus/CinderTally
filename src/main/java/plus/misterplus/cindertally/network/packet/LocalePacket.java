package plus.misterplus.cindertally.network.packet;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.network.NetworkEvent;
import plus.misterplus.cindertally.CinderTally;
import plus.misterplus.cindertally.helper.CommandHelper;
import plus.misterplus.cindertally.helper.LifespanHelper;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

/**
 * Packet responsible for handling localized kick and ban messages from the client
 */
public class LocalePacket {

    private final String msgKick, msgBanned;

    public LocalePacket() {
        this.msgKick = I18n.get("msg.cindertally.kick");
        this.msgBanned = I18n.get("msg.cindertally.banned");
    }

    public LocalePacket(String msgKick, String msgBanned) {
        this.msgKick = msgKick;
        this.msgBanned = msgBanned;
    }

    public static void encode(LocalePacket packet, PacketBuffer packetBuffer) {
        packetBuffer.writeInt(packet.msgKick.length());
        packetBuffer.writeBytes(packet.msgKick.getBytes(StandardCharsets.UTF_8));
        packetBuffer.writeBytes(packet.msgBanned.getBytes(StandardCharsets.UTF_8));
    }

    public static LocalePacket decode(PacketBuffer packetBuffer) {
        byte[] msgKick = new byte[packetBuffer.readInt()];
        packetBuffer.readBytes(msgKick, 0, msgKick.length);
        byte[] msgBanned = new byte[packetBuffer.readableBytes()];
        packetBuffer.readBytes(msgBanned);
        return new LocalePacket(new String(msgKick, StandardCharsets.UTF_8), new String(msgBanned, StandardCharsets.UTF_8));
    }

    public static void handle(LocalePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            String name = player.getName().getContents();
            MinecraftServer server = player.getServer();
            //  server receives the packet, checks if player is actually out of time, then kick & ban them
            if (LifespanHelper.getLifespan(player) == 0) {
                CinderTally.LOGGER.debug(String.format("Player %s out of time, kicking...", name));
                CommandHelper.executeCommand(server, String.format("/kick %s %s", name, packet.msgKick));
                CommandHelper.executeCommand(server, String.format("/ban %s %s", name, packet.msgBanned));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
