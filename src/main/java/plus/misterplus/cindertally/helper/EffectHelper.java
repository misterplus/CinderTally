package plus.misterplus.cindertally.helper;

import net.minecraft.entity.player.PlayerEntity;
import plus.misterplus.cindertally.registry.CinderTallyRegistry;

public class EffectHelper {

    public static boolean isInStasis(PlayerEntity player) {
        return player.hasEffect(CinderTallyRegistry.STASIS);
    }

    public static boolean isEffectivelyInStasis(PlayerEntity player) {
        int test = 255 << 24;
        return isInStasis(player) || player.isCreative() || player.isSpectator();
    }
}
