package plus.misterplus.cindertally.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.World;
import plus.misterplus.cindertally.helper.NBTHelper;

/**
 * The tally that records life of all living creatures.<br>
 * Function: players can retrieve life out of themselves in forms of lifespan items.
 * @see plus.misterplus.cindertally.common.item.ItemLifeSpan
 */
public class ItemCinderTally extends Item {
    public ItemCinderTally(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide)
            player.sendMessage(new StringTextComponent(String.valueOf(NBTHelper.getLifespan(player))), Util.NIL_UUID);
        return ActionResult.success(player.getItemInHand(hand));
    }
}
