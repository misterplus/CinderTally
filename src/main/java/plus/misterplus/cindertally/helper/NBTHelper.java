package plus.misterplus.cindertally.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import plus.misterplus.cindertally.CinderTallyConstants;

public class NBTHelper {

    public static CompoundNBT getPersistedData(PlayerEntity player, boolean createIfMissing) {
        CompoundNBT nbt = player.getPersistentData().getCompound(PlayerEntity.PERSISTED_NBT_TAG);
        if (createIfMissing) {
            player.getPersistentData().put(PlayerEntity.PERSISTED_NBT_TAG, nbt);
        }
        return nbt;
    }

    public static boolean isFirstLogin(PlayerEntity player) {
        if (!getPersistedData(player, false).getBoolean(CinderTallyConstants.NEW_NBT_TAG)) {
            getPersistedData(player, true).putBoolean(CinderTallyConstants.NEW_NBT_TAG, true);
            return true;
        }
        return false;
    }
}
