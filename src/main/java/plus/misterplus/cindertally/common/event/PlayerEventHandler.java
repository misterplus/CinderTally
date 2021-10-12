package plus.misterplus.cindertally.common.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import plus.misterplus.cindertally.CinderTally;
import plus.misterplus.cindertally.helper.NBTHelper;
import plus.misterplus.cindertally.network.CinderTallyPacketHandler;
import plus.misterplus.cindertally.network.packet.ReckoningPacket;

public class PlayerEventHandler {

    @OnlyIn(Dist.DEDICATED_SERVER)
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
        boolean outOfLife = NBTHelper.diminishLifespan(player);
        if (outOfLife && player.isAlive()) {
            if (world.isClientSide()) {
                //TODO: if on singleplayer / lan: kill player and show deathscreen

            }
            else {
                // if on multiplayer: kick player (with special msg) and ban player
                // server sends a packet informing the client out of time
                CinderTallyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new ReckoningPacket());
            }
        }
    }
}
