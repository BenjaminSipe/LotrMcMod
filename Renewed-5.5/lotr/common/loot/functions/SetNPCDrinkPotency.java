package lotr.common.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Random;
import lotr.common.item.VesselDrinkItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootFunction.Builder;
import net.minecraft.loot.conditions.ILootCondition;

public class SetNPCDrinkPotency extends LootFunction {
   private SetNPCDrinkPotency(ILootCondition[] conditions) {
      super(conditions);
   }

   public LootFunctionType func_230425_b_() {
      return LOTRLootFunctions.SET_NPC_DRINK_POTENCY;
   }

   public ItemStack func_215859_a(ItemStack stack, LootContext context) {
      if (stack.func_77973_b() instanceof VesselDrinkItem) {
         VesselDrinkItem drink = (VesselDrinkItem)stack.func_77973_b();
         if (drink.hasPotencies) {
            VesselDrinkItem.setPotency(stack, this.getRandomPotency(context.func_216032_b()));
         }
      }

      return stack;
   }

   private VesselDrinkItem.Potency getRandomPotency(Random rand) {
      int i = rand.nextInt(3);
      if (i == 0) {
         return VesselDrinkItem.Potency.LIGHT;
      } else {
         return i == 1 ? VesselDrinkItem.Potency.MODERATE : VesselDrinkItem.Potency.STRONG;
      }
   }

   public static Builder setNPCDrinkPotencyBuilder() {
      return func_215860_a(SetNPCDrinkPotency::new);
   }

   // $FF: synthetic method
   SetNPCDrinkPotency(ILootCondition[] x0, Object x1) {
      this(x0);
   }

   public static class Serializer extends net.minecraft.loot.LootFunction.Serializer {
      public SetNPCDrinkPotency deserialize(JsonObject object, JsonDeserializationContext context, ILootCondition[] conditions) {
         return new SetNPCDrinkPotency(conditions);
      }
   }
}
