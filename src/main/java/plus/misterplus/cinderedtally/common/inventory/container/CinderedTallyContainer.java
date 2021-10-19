package plus.misterplus.cinderedtally.common.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import plus.misterplus.cinderedtally.helper.EffectHelper;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

public class CinderedTallyContainer extends Container {

    public CinderedTallyContainer(int windowId, PlayerInventory playerInventory, IInventory container) {
        super(CinderedTallyRegistry.CONTAINER_CINDER_TALLY, windowId);
        int containerRows = 1;
        int i = (containerRows - 4) * 18;

        for (int j = 0; j < containerRows; ++j) {
            for (int k = 0; k < 8; ++k) {
                this.addSlot(new Slot(container, k + j * 9, 8 + k * 18, 18 + j * 18) {
                    @Override
                    public boolean mayPickup(PlayerEntity p_82869_1_) {
                        return false;
                    }
                });
            }
        }

        // Player Inventory
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return EffectHelper.isEffectivelyInStasis(player);
    }


}
