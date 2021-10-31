package plus.misterplus.cinderedtally.common.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
import plus.misterplus.cinderedtally.common.inventory.CrucibleCraftingInventory;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

import javax.annotation.Nullable;

public class CrucibleRecipe implements IRecipe<CrucibleCraftingInventory> {

    private final NonNullList<Ingredient> ingredients;
    private final FluidStack base;
    private final ItemStack result;
    private final ResourceLocation id;

    // TODO: add heat requirement
    public CrucibleRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, FluidStack base, ItemStack result) {
        this.ingredients = ingredients;
        this.base = base;
        this.result = result;
        this.id = id;
    }

    @Override
    public boolean matches(CrucibleCraftingInventory inventory, World world) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (!ingredients.get(i).test(inventory.getItem(i)))
                return false;
        }
        return inventory.getFluidStack().containsFluid(base);
    }

    @Override
    public ItemStack assemble(CrucibleCraftingInventory inventory) {
        inventory.clearContent();
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
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
        public CrucibleRecipe fromJson(ResourceLocation rl, JsonObject json) {
            // TODO: add exception throws
            JsonArray ingredientArray = JSONUtils.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (JsonElement entry : ingredientArray) {
                ingredients.add(Ingredient.fromJson(entry));
            }
            // TODO: rework this, make this prettier
            try {
                FluidStack base = FluidStack.loadFluidStackFromNBT(JsonToNBT.parseTag(JSONUtils.getAsJsonObject(json, "base").toString()));
                ItemStack result = ItemStack.of(JsonToNBT.parseTag(JSONUtils.getAsJsonObject(json, "result").toString()));
                return new CrucibleRecipe(rl, ingredients, base, result);
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Nullable
        @Override
        public CrucibleRecipe fromNetwork(ResourceLocation rl, PacketBuffer packet) {
            int size = packet.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (int i = 0; i < size; i++)
                ingredients.set(i, Ingredient.fromNetwork(packet));
            FluidStack base = FluidStack.readFromPacket(packet);
            ItemStack result = packet.readItem();
            return new CrucibleRecipe(rl, ingredients, base, result);
        }

        @Override
        public void toNetwork(PacketBuffer packet, CrucibleRecipe recipe) {
            packet.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients)
                ingredient.toNetwork(packet);
            recipe.base.writeToPacket(packet);
            packet.writeItem(recipe.result);
        }
    }
}
