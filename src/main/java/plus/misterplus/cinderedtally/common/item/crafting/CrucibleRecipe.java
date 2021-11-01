package plus.misterplus.cinderedtally.common.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import plus.misterplus.cinderedtally.common.item.wrapper.CrucibleRecipeWrapper;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

import javax.annotation.Nullable;

public class CrucibleRecipe extends InteractiveRecipe<CrucibleRecipeWrapper> {

    private final NonNullList<Ingredient> ingredients;
    private final FluidStack base;
    private final ItemStack result;
    private final ResourceLocation id;
    private final boolean heat;

    public CrucibleRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, FluidStack base, ItemStack result, boolean heat) {
        this.ingredients = ingredients;
        this.base = base;
        this.result = result;
        this.id = id;
        this.heat = heat;
    }

    @Override
    public boolean matches(CrucibleRecipeWrapper inv, World world) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (!ingredients.get(i).test(inv.getItem(i)))
                return false;
        }
        if (heat && world.getBlockState(inv.getBlockPos().below()).getBlock() != Blocks.FIRE)
            return false;
        return inv.getFluidStack().containsFluid(base);
    }

    @Override
    public ItemStack assemble(CrucibleRecipeWrapper inv) {
        inv.clearContent();
        return result.copy();
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return CinderedTallyRegistry.RECIPE_SERIALIZER_CRUCIBLE;
    }

    @Override
    public IRecipeType<?> getType() {
        return CinderedTallyRegistry.RECIPE_CRUCIBLE;
    }

    public int getToDrain() {
        return base.getAmount();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrucibleRecipe> {

        @Override
        public CrucibleRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonArray ingredientArray = JSONUtils.getAsJsonArray(json, "ingredients");
            if (ingredientArray.size() > 4)
                throw new JsonSyntaxException("Ingredient count is greater than 4");
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (JsonElement entry : ingredientArray) {
                ingredients.add(Ingredient.fromJson(entry));
            }
            boolean heat = JSONUtils.getAsBoolean(json, "heat");
            try {
                FluidStack base = FluidStack.loadFluidStackFromNBT(JsonToNBT.parseTag(JSONUtils.getAsJsonObject(json, "base").toString()));
                ItemStack result = ItemStack.of(JsonToNBT.parseTag(JSONUtils.getAsJsonObject(json, "result").toString()));
                return new CrucibleRecipe(recipeId, ingredients, base, result, heat);
            } catch (CommandSyntaxException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Nullable
        @Override
        public CrucibleRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            int size = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (int i = 0; i < size; i++)
                ingredients.set(i, Ingredient.fromNetwork(buffer));
            FluidStack base = FluidStack.readFromPacket(buffer);
            ItemStack result = buffer.readItem();
            boolean heat = buffer.readBoolean();
            return new CrucibleRecipe(recipeId, ingredients, base, result, heat);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, CrucibleRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients)
                ingredient.toNetwork(buffer);
            recipe.base.writeToPacket(buffer);
            buffer.writeItem(recipe.result);
            buffer.writeBoolean(recipe.heat);
        }
    }
}
