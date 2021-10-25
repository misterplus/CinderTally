package plus.misterplus.cinderedtally.data;

import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

import java.util.function.Consumer;

public class CinderedTallyRecipeProvider extends RecipeProvider {
    public CinderedTallyRecipeProvider(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        CustomRecipeBuilder.special(CinderedTallyRegistry.REPAIR_CINDERED_TALLY).save(consumer, "cinderedtally:repair_cindered_tally");
    }
}
