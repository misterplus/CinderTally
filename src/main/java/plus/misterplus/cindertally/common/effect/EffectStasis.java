package plus.misterplus.cindertally.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;
import plus.misterplus.cindertally.registry.CinderTallyRegistry;

/**
 * An effect that freezes players' time.<br>
 * Achievable by getting a Time Dilation effect with a amplifier greater than 3 (Level V and above).
 */
public class EffectStasis extends Effect {

    public EffectStasis() {
        super(EffectType.NEUTRAL, 16777037);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        World world = entity.getCommandSenderWorld();
        if (!world.isClientSide() && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            // stasis & time dilation should not co-exist
            if (player.hasEffect(CinderTallyRegistry.TIME_DILATION))
                player.removeEffect(CinderTallyRegistry.TIME_DILATION);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
