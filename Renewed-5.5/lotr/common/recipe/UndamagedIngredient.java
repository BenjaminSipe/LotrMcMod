package lotr.common.recipe;

import com.google.gson.JsonObject;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.Ingredient.SingleItemList;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.VanillaIngredientSerializer;

public class UndamagedIngredient extends Ingredient {
   private UndamagedIngredient(Stream itemLists) {
      super(itemLists);
   }

   public boolean test(@Nullable ItemStack stack) {
      return super.test(stack) && !stack.func_77951_h();
   }

   public IIngredientSerializer getSerializer() {
      return LOTRRecipes.UNDAMAGED_INGREDIENT_SERIALIZER;
   }

   // $FF: synthetic method
   UndamagedIngredient(Stream x0, Object x1) {
      this(x0);
   }

   public static class Serializer implements IIngredientSerializer {
      private final IIngredientSerializer internalVanillaSerializer = new VanillaIngredientSerializer();

      public UndamagedIngredient parse(PacketBuffer buffer) {
         return new UndamagedIngredient(Stream.generate(() -> {
            return new SingleItemList(buffer.func_150791_c());
         }).limit((long)buffer.func_150792_a()));
      }

      public UndamagedIngredient parse(JsonObject json) {
         return new UndamagedIngredient(Stream.of(Ingredient.func_199803_a(json)));
      }

      public void write(PacketBuffer buffer, UndamagedIngredient ingredient) {
         this.internalVanillaSerializer.write(buffer, ingredient);
      }
   }
}
