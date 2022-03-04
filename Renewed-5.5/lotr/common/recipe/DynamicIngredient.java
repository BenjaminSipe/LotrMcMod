package lotr.common.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.Ingredient.SingleItemList;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class DynamicIngredient extends Ingredient {
   private final DynamicIngredient.Type type;

   private DynamicIngredient(DynamicIngredient.Type type) {
      super(type.getItemList());
      this.type = type;
   }

   public DynamicIngredient.Type getType() {
      return this.type;
   }

   public IIngredientSerializer getSerializer() {
      return LOTRRecipes.DYNAMIC_INGREDIENT_SERIALIZER;
   }

   // $FF: synthetic method
   DynamicIngredient(DynamicIngredient.Type x0, Object x1) {
      this(x0);
   }

   public static class Serializer implements IIngredientSerializer {
      public Ingredient parse(JsonObject json) {
         if (!json.has("dynamic_type")) {
            throw new JsonSyntaxException("Missing dynamic_type, expected to find something here");
         } else {
            String typeName = json.get("dynamic_type").getAsString();
            DynamicIngredient.Type type = DynamicIngredient.Type.forCode(typeName);
            if (type != null) {
               return new DynamicIngredient(type);
            } else {
               throw new JsonSyntaxException("No dynamic_type named " + typeName + " exists");
            }
         }
      }

      public Ingredient parse(PacketBuffer buffer) {
         String typeName = buffer.func_218666_n();
         DynamicIngredient.Type type = DynamicIngredient.Type.forCode(typeName);
         if (type != null) {
            return new DynamicIngredient(type);
         } else {
            throw new JsonSyntaxException("No dynamic_type named " + typeName + " exists");
         }
      }

      public void write(PacketBuffer buffer, Ingredient ingredient) {
         DynamicIngredient dynIng = (DynamicIngredient)ingredient;
         buffer.func_180714_a(dynIng.getType().getCode());
      }
   }

   public static enum Type {
      MEATS_EXCEPT_ROTTEN_FLESH("meats_except_rotten_flesh");

      private final String code;
      private ItemStack[] cachedItemStacks;

      private Type(String code) {
         this.code = code;
      }

      public String getCode() {
         return this.code;
      }

      public static DynamicIngredient.Type forCode(String code) {
         DynamicIngredient.Type[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            DynamicIngredient.Type type = var1[var3];
            if (type.code.equals(code)) {
               return type;
            }
         }

         return null;
      }

      public Stream getItemList() {
         if (this.cachedItemStacks == null) {
            if (this != MEATS_EXCEPT_ROTTEN_FLESH) {
               throw new IllegalArgumentException("Type " + this.code + " not yet implemented in code");
            }

            List meats = (List)ForgeRegistries.ITEMS.getValues().stream().filter((item) -> {
               return item.func_219971_r() && item.func_219967_s().func_221467_c() && item != Items.field_151078_bh;
            }).map((item) -> {
               return new ItemStack(item);
            }).collect(Collectors.toList());
            this.cachedItemStacks = (ItemStack[])meats.toArray(new ItemStack[0]);
         }

         return Arrays.stream(this.cachedItemStacks).map((stack) -> {
            return new SingleItemList(stack);
         });
      }
   }
}
