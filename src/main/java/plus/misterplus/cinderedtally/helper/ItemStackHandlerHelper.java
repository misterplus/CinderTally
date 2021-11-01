package plus.misterplus.cinderedtally.helper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerHelper {
    public static ItemStack insertToFirstEmptySlot(ItemStackHandler handler, ItemStack stack, boolean simulate) {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (handler.getStackInSlot(i).isEmpty())
                return handler.insertItem(i, stack, simulate);
        }
        return stack;
    }

    public static ItemStack extractAllFromLastFilledSlot(ItemStackHandler handler, boolean simulate) {
        for (int i = handler.getSlots() - 1; i >= 0; i--) {
            if (!handler.getStackInSlot(i).isEmpty())
                return handler.extractItem(i, 64, simulate);
        }
        return ItemStack.EMPTY;
    }
}
