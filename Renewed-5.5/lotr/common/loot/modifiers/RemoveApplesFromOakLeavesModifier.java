package lotr.common.loot.modifiers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockState;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class RemoveApplesFromOakLeavesModifier extends LootModifier {
   private final List blockNames;

   public RemoveApplesFromOakLeavesModifier(ILootCondition[] conds, List names) {
      super(conds);
      this.blockNames = names;
   }

   @Nonnull
   public List doApply(List generatedLoot, LootContext context) {
      BlockState state = (BlockState)context.func_216031_c(LootParameters.field_216287_g);
      if (state != null && this.blockNames.contains(state.func_177230_c().getRegistryName())) {
         generatedLoot.removeIf((item) -> {
            return item.func_77973_b() == Items.field_151034_e;
         });
      }

      return generatedLoot;
   }

   public static class Serializer extends GlobalLootModifierSerializer {
      public RemoveApplesFromOakLeavesModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditions) {
         List blockNames = new ArrayList();
         JsonArray list = object.get("target_blocks").getAsJsonArray();
         Iterator var6 = list.iterator();

         while(var6.hasNext()) {
            JsonElement elem = (JsonElement)var6.next();
            String s = elem.getAsString();
            ResourceLocation blockName = new ResourceLocation(s);
            blockNames.add(blockName);
         }

         return new RemoveApplesFromOakLeavesModifier(conditions, blockNames);
      }

      public JsonObject write(RemoveApplesFromOakLeavesModifier instance) {
         JsonObject obj = this.makeConditions(instance.conditions);
         JsonArray list = new JsonArray();
         Iterator var4 = instance.blockNames.iterator();

         while(var4.hasNext()) {
            ResourceLocation blockName = (ResourceLocation)var4.next();
            list.add(blockName.toString());
         }

         obj.add("target_blocks", list);
         return obj;
      }
   }
}
