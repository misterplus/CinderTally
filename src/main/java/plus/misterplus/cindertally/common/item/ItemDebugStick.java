package plus.misterplus.cindertally.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import plus.misterplus.cindertally.helper.NBTHelper;

public class ItemDebugStick extends Item {

    public ItemDebugStick(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        NBTHelper.setLifespan(player, 200);
        return ActionResult.success(player.getItemInHand(hand));
    }
}
