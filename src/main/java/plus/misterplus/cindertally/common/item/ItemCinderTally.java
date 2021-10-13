package plus.misterplus.cindertally.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import plus.misterplus.cindertally.common.inventory.CinderTallyInventory;
import plus.misterplus.cindertally.helper.EffectHelper;
import plus.misterplus.cindertally.helper.LifespanHelper;
import plus.misterplus.cindertally.helper.NBTHelper;

import javax.annotation.Nullable;

/**
 * The tally that records life of all living creatures.<br>
 * Function: players can retrieve life out of themselves in forms of lifespan items.
 * @see plus.misterplus.cindertally.common.item.ItemLifeSpan
 */
public class ItemCinderTally extends Item {
    public ItemCinderTally(Properties properties) {
        super(properties);
    }

    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.cindertally.cindertally");

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (EffectHelper.isInStasis(player)) {
            CinderTallyInventory cinderTallyInventory = LifespanHelper.getCinderTallyInventory(player);
            player.openMenu(new SimpleNamedContainerProvider(new IContainerProvider() {
                @Nullable
                @Override
                public Container createMenu(int containerId, PlayerInventory playerInventory, PlayerEntity player) {
                    return ChestContainer.threeRows(containerId, playerInventory, cinderTallyInventory);
                }
            }, CONTAINER_TITLE));
            return ActionResult.sidedSuccess(item, world.isClientSide());
        }
        else {
            // not in stasis, send msg to player
            return ActionResult.fail(item);
        }
//        if (!world.isClientSide) {
//            player.sendMessage(new StringTextComponent(String.valueOf(NBTHelper.getLifespan(player))), Util.NIL_UUID);
//        }

    }
}
