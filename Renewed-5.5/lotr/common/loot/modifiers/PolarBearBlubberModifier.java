package lotr.common.loot.modifiers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.loot.ILootGenerator;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootSerializers;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.apache.commons.lang3.mutable.MutableInt;

public class PolarBearBlubberModifier extends LootModifier {
   private final LootEntry extraEntry;

   public PolarBearBlubberModifier(ILootCondition[] conds, LootEntry extraEntry) {
      super(conds);
      this.extraEntry = extraEntry;
   }

   @Nonnull
   public List doApply(List generatedLoot, LootContext context) {
      Entity entity = (Entity)context.func_216031_c(LootParameters.field_216281_a);
      if (entity instanceof PolarBearEntity) {
         Consumer stacksOut = (stack) -> {
            generatedLoot.add(stack);
         };
         stacksOut = LootTable.func_216124_a(stacksOut);
         Consumer consumer = ILootFunction.func_215858_a(LootFunctionManager.field_216242_a, stacksOut, context);
         this.generateExtraLootEntry(consumer, context);
      }

      return generatedLoot;
   }

   private void generateExtraLootEntry(Consumer consumer, LootContext context) {
      Random random = context.func_216032_b();
      List lootGens = Lists.newArrayList();
      MutableInt totalWeight = new MutableInt();
      UnmodifiableIterator var6 = ImmutableList.of(this.extraEntry).iterator();

      while(var6.hasNext()) {
         LootEntry lootentry = (LootEntry)var6.next();
         lootentry.expand(context, (gen) -> {
            int weight = gen.func_186361_a(context.func_186491_f());
            if (weight > 0) {
               lootGens.add(gen);
               totalWeight.add(weight);
            }

         });
      }

      int numLootGens = lootGens.size();
      if (totalWeight.intValue() != 0 && numLootGens != 0) {
         if (numLootGens == 1) {
            ((ILootGenerator)lootGens.get(0)).func_216188_a(consumer, context);
         } else {
            int weight = random.nextInt(totalWeight.intValue());
            Iterator var8 = lootGens.iterator();

            while(var8.hasNext()) {
               ILootGenerator ilootgenerator = (ILootGenerator)var8.next();
               weight -= ilootgenerator.func_186361_a(context.func_186491_f());
               if (weight < 0) {
                  ilootgenerator.func_216188_a(consumer, context);
                  return;
               }
            }
         }
      }

   }

   public static class Serializer extends GlobalLootModifierSerializer {
      private static final Gson GSON_WITH_LOOT_ENTRY_ADAPTER = LootSerializers.func_237387_b_().create();

      public PolarBearBlubberModifier read(ResourceLocation name, JsonObject obj, ILootCondition[] conditions) {
         LootEntry extraEntry = (LootEntry)GSON_WITH_LOOT_ENTRY_ADAPTER.fromJson(obj.get("extra_entry"), LootEntry.class);
         return new PolarBearBlubberModifier(conditions, extraEntry);
      }

      public JsonObject write(PolarBearBlubberModifier instance) {
         JsonObject obj = this.makeConditions(instance.conditions);
         obj.add("extra_entry", GSON_WITH_LOOT_ENTRY_ADAPTER.toJsonTree(instance.extraEntry));
         return obj;
      }
   }
}
