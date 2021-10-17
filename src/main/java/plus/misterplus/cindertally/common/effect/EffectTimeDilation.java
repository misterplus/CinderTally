package plus.misterplus.cindertally.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;
import plus.misterplus.cindertally.helper.LifespanHelper;
import plus.misterplus.cindertally.registry.CinderTallyRegistry;

/**
 * An effect that slows players' time.<br>
 * For each level of this effect, the affected player's time goes 20% slower.<br>
 * When a player reaches Time Dilation V, that player enters stasis.
 *
 * @see plus.misterplus.cindertally.common.effect.EffectStasis
 */
public class EffectTimeDilation extends Effect {
    public EffectTimeDilation() {
        super(EffectType.NEUTRAL, 16777088);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        World world = entity.getCommandSenderWorld();
        if (!world.isClientSide() && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            LifespanHelper.extendLifespan(player, 1);
            if (amplifier > 3)
                player.addEffect(new EffectInstance(CinderTallyRegistry.STASIS, player.getEffect(CinderTallyRegistry.TIME_DILATION).getDuration(), 0));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 < amplifier * 4 + 4;
    }
}
