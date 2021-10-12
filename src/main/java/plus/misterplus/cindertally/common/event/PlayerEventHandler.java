package plus.misterplus.cindertally.common.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import plus.misterplus.cindertally.CinderTally;
import plus.misterplus.cindertally.helper.NBTHelper;
import plus.misterplus.cindertally.network.CinderTallyPacketHandler;
import plus.misterplus.cindertally.network.packet.ReckoningPacket;
import plus.misterplus.cindertally.network.packet.SReckoningPacket;

public class PlayerEventHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        boolean isFirstLogin = NBTHelper.isFirstLogin(player);
        CinderTally.LOGGER.debug(String.format("%s is first time login: %b", event.getPlayer().getName().getContents(), isFirstLogin));
        if (isFirstLogin)
            NBTHelper.initLifespan(player);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = player.getCommandSenderWorld();
        if (NBTHelper.getLifespan(player) == 0)
            return;
        boolean outOfLife = NBTHelper.diminishLifespan(player);
        if (!world.isClientSide() && outOfLife && player.isAlive()) {
            if (player.getServer().isSingleplayer()) {
                player.setGameMode(GameType.SPECTATOR);
                CinderTallyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SReckoningPacket());
            } else {
                // if on multiplayer: kick player (with special msg) and ban player
                // server sends a packet informing the client out of time
                CinderTallyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new ReckoningPacket());
            }
        }
    }
}
