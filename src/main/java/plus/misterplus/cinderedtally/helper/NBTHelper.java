package plus.misterplus.cinderedtally.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import plus.misterplus.cinderedtally.CinderedTallyConstants;

public class NBTHelper {

    private static final String NBT_KEY_NEW = CinderedTallyConstants.MOD_ID + "_new";

    public static CompoundNBT getPersistedData(PlayerEntity player, boolean createIfMissing) {
        CompoundNBT nbt = player.getPersistentData().getCompound(PlayerEntity.PERSISTED_NBT_TAG);
        if (createIfMissing) {
            player.getPersistentData().put(PlayerEntity.PERSISTED_NBT_TAG, nbt);
        }
        return nbt;
    }

    public static boolean isFirstLogin(PlayerEntity player) {
        if (!getPersistedData(player, false).getBoolean(NBT_KEY_NEW)) {
            getPersistedData(player, true).putBoolean(NBT_KEY_NEW, true);
            return true;
        }
        return false;
    }
}
