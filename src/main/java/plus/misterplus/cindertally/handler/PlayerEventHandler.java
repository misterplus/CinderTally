package plus.misterplus.cindertally.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import plus.misterplus.cindertally.CinderTally;
import plus.misterplus.cindertally.helper.NBTHelper;

public class PlayerEventHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        boolean isFirstLogin = NBTHelper.isFirstLogin(player);
        CinderTally.LOGGER.debug(event.getPlayer().getName().getContents() + " is first time login: " + isFirstLogin);
        if (isFirstLogin)
            NBTHelper.initLifespan(player);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        NBTHelper.diminishLifespan(event.player);
    }
}
