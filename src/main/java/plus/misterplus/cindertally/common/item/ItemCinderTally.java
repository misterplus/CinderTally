package plus.misterplus.cindertally.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import plus.misterplus.cindertally.common.inventory.container.CinderTallyContainer;
import plus.misterplus.cindertally.helper.EffectHelper;
import plus.misterplus.cindertally.helper.LifespanHelper;

import javax.annotation.Nullable;

/**
 * The tally that records life of all living creatures.<br>
 * Players can check their own remaining lifespan with this item while in stasis.
 *
 * @see ItemLifespan
 */
public class ItemCinderTally extends Item {

    public ItemCinderTally(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (!world.isClientSide()) {
            if (EffectHelper.isEffectivelyInStasis(player)) {
                NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new StringTextComponent("");
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int containerId, PlayerInventory playerInventory, PlayerEntity player) {
                        return new CinderTallyContainer(containerId, playerInventory, LifespanHelper.getCinderTallyInventory(player));
                    }
                }, packetBuffer -> packetBuffer.writeLong(LifespanHelper.getLifespan(player)));
                return ActionResult.success(item);
            } else {
                // not in stasis, send msg to player
                return ActionResult.fail(item);
            }
        }
        return ActionResult.pass(item);
    }
}
