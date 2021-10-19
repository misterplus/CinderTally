package plus.misterplus.cinderedtally.common.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import plus.misterplus.cinderedtally.CinderedTally;
import plus.misterplus.cinderedtally.CinderedTallyConstants;
import plus.misterplus.cinderedtally.helper.LifespanHelper;
import plus.misterplus.cinderedtally.helper.NBTHelper;
import plus.misterplus.cinderedtally.network.CinderedTallyPacketHandler;
import plus.misterplus.cinderedtally.network.packet.ReckoningPacket;
import plus.misterplus.cinderedtally.network.packet.SReckoningPacket;

@Mod.EventBusSubscriber(modid = CinderedTallyConstants.MOD_ID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.getCommandSenderWorld().isClientSide())
            return;
        boolean isFirstLogin = NBTHelper.isFirstLogin(player);
        CinderedTally.LOGGER.debug(String.format("%s is first time login: %b", event.getPlayer().getName().getContents(), isFirstLogin));
        if (isFirstLogin)
            LifespanHelper.initLifespan(player);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = player.getCommandSenderWorld();
        if (world.isClientSide())
            return;
        if (LifespanHelper.getLifespan(player) == 0)
            return;
        boolean outOfLife = LifespanHelper.diminishLifespan(player);
        if (outOfLife && player.isAlive()) {
            if (player.getServer().isSingleplayer()) {
                // if on singleplayer: set player to gm3 then shows a death screen
                player.setGameMode(GameType.SPECTATOR);
                CinderedTallyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SReckoningPacket());
            } else {
                // if on multiplayer: kick player (with special msg) and ban player
                // server sends a packet informing the client out of time
                CinderedTallyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new ReckoningPacket());
            }
        }
    }
}
