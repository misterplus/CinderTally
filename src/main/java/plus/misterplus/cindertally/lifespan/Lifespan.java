package plus.misterplus.cindertally.lifespan;

import com.ibm.icu.impl.duration.PeriodBuilder;

import java.time.Period;

public class Lifespan {
    /**
     * The value of a lifespan, in real life seconds.
     */
    private final int value;

    /**
     *
     * @param value The value of a lifespan, in in-game ticks.
     */
    public Lifespan(int value) {
        this.value = value / 20;
    }
}
