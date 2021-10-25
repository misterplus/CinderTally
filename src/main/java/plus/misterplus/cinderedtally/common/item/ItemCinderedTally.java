package plus.misterplus.cinderedtally.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import plus.misterplus.cinderedtally.CinderedTallyConstants;
import plus.misterplus.cinderedtally.common.inventory.container.CinderedTallyContainer;
import plus.misterplus.cinderedtally.helper.EffectHelper;
import plus.misterplus.cinderedtally.helper.LifespanHelper;

import javax.annotation.Nullable;

/**
 * The tally that records life of all living creatures.<br>
 * Players can check their own remaining lifespan with this item while in stasis.
 *
 * @see ItemLifespan
 */
public class ItemCinderedTally extends Item {

    private static final int MAX_PAGE_DEFAULT = 10;
    private static final int MAX_PAGE_INCREMENT = 10;
    private static final String NBT_KEY_UPGRADE = CinderedTallyConstants.MOD_ID + "_cindered_tally_upgrade";
    private static final String NBT_KEY_PAGE = CinderedTallyConstants.MOD_ID + "_cindered_tally_page";

    public ItemCinderedTally(Properties properties) {
        super(properties);
    }

    // adds an empty Cindered Tally to the creative inventory
    @Override
    public void fillItemCategory(ItemGroup itemGroup, NonNullList<ItemStack> itemsAdded) {
        if (this.allowdedIn(itemGroup)) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt(NBT_KEY_UPGRADE, 0);
            nbt.putInt(NBT_KEY_PAGE, 0);
            ItemStack itemStack = new ItemStack(this);
            itemStack.setTag(nbt);
            itemsAdded.add(itemStack);
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    public static ItemStack repair(ItemStack stack, int pages) {
        int repairableCount = getRepairableCount(stack);
        int toRepair = Math.min(pages, repairableCount);
        stack.getTag().putInt(NBT_KEY_PAGE, stack.getTag().getInt(NBT_KEY_PAGE) + toRepair);
        return stack;
    }

    private static int getRepairableCount(ItemStack stack) {
        return (int) (ItemCinderedTally.getMaxPages(stack) - ItemCinderedTally.getPages(stack));
    }

    public static double getPages(ItemStack stack) {
        return stack.getTag().getInt(NBT_KEY_PAGE);
    }

    public static double getMaxPages(ItemStack stack) {
        return MAX_PAGE_DEFAULT + MAX_PAGE_INCREMENT * stack.getTag().getInt(NBT_KEY_UPGRADE);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0D - getPages(stack) / getMaxPages(stack);
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
                        return new CinderedTallyContainer(containerId, playerInventory, LifespanHelper.getCinderedTallyInventory(player));
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
