package plus.misterplus.cindertally.helper;

import net.minecraft.entity.player.PlayerEntity;
import plus.misterplus.cindertally.registry.CinderTallyEffects;

public class EffectHelper {
    public static boolean isInStasis(PlayerEntity player) {
        return player.hasEffect(CinderTallyEffects.STASIS);
    }
}
