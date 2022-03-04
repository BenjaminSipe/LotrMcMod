package lotr.common.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import lotr.common.fac.EntityFactionHelper;
import lotr.common.fac.Faction;
import lotr.common.item.PouchItem;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootFunction.Builder;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;

public class SetPouchColorFromEntityFaction extends LootFunction {
   private final float chance;

   private SetPouchColorFromEntityFaction(ILootCondition[] conditions, float chance) {
      super(conditions);
      this.chance = chance;
   }

   public LootFunctionType func_230425_b_() {
      return LOTRLootFunctions.SET_POUCH_COLOR_FROM_ENTITY_FACTION;
   }

   public ItemStack func_215859_a(ItemStack stack, LootContext context) {
      if (context.func_216032_b().nextFloat() < this.chance && stack.func_77973_b() instanceof PouchItem) {
         Entity entity = (Entity)context.func_216031_c(LootParameters.field_216281_a);
         if (entity != null) {
            Faction faction = EntityFactionHelper.getFaction(entity);
            if (faction.isPlayableAlignmentFaction()) {
               PouchItem.setPouchDyedByFaction(stack, faction);
            }
         }
      }

      return stack;
   }

   public static Builder setPouchColorFromEntityFactionBuilder(float chance) {
      return func_215860_a((conditions) -> {
         return new SetPouchColorFromEntityFaction(conditions, chance);
      });
   }

   // $FF: synthetic method
   SetPouchColorFromEntityFaction(ILootCondition[] x0, float x1, Object x2) {
      this(x0, x1);
   }

   public static class Serializer extends net.minecraft.loot.LootFunction.Serializer {
      public void serialize(JsonObject object, SetPouchColorFromEntityFaction function, JsonSerializationContext context) {
         super.func_230424_a_(object, function, context);
         object.addProperty("chance", function.chance);
      }

      public SetPouchColorFromEntityFaction deserialize(JsonObject object, JsonDeserializationContext context, ILootCondition[] conditions) {
         float chance = JSONUtils.func_151221_a(object, "chance", 1.0F);
         return new SetPouchColorFromEntityFaction(conditions, chance);
      }
   }
}
