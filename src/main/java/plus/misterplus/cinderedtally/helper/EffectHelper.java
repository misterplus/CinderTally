package plus.misterplus.cinderedtally.helper;

import net.minecraft.entity.player.PlayerEntity;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

public class EffectHelper {

    public static boolean isInStasis(PlayerEntity player) {
        return player != null && player.hasEffect(CinderedTallyRegistry.STASIS);
    }

    public static boolean isEffectivelyInStasis(PlayerEntity player) {
        return isInStasis(player) || player.isCreative() || player.isSpectator();
    }
}
