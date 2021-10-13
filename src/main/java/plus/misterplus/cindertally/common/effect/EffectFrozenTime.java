package plus.misterplus.cindertally.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

/**
 * An effect that freezes players' time.
 */
public class EffectFrozenTime extends Effect {

    public EffectFrozenTime() {
        super(EffectType.NEUTRAL, 16777037);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int p_76394_2_) {
    }
}
