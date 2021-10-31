package plus.misterplus.cinderedtally.helper;

import java.util.Random;

public class SoundHelper {
    public static float getPitch(Random rand) {
        return (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F;
    }
}
