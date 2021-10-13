package plus.misterplus.cindertally.common.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import plus.misterplus.cindertally.helper.EffectHelper;
import plus.misterplus.cindertally.registry.CinderTallyContainerTypes;

public class CinderTallyContainer extends Container {

    public CinderTallyContainer(int containerId, PlayerInventory playerInventory) {
        super(CinderTallyContainerTypes.CONTAINER_CINDERTALLY, containerId);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return EffectHelper.isInStasis(player);
    }


}
