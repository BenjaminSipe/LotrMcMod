package lotr.common.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import lotr.common.tileentity.VesselDrinkTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootFunction.Builder;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tileentity.TileEntity;

public class VesselDrink extends LootFunction {
   private VesselDrink(ILootCondition[] conditions) {
      super(conditions);
   }

   public LootFunctionType func_230425_b_() {
      return LOTRLootFunctions.VESSEL_DRINK;
   }

   public ItemStack func_215859_a(ItemStack stack, LootContext context) {
      TileEntity te = (TileEntity)context.func_216031_c(LootParameters.field_216288_h);
      if (te instanceof VesselDrinkTileEntity) {
         VesselDrinkTileEntity vessel = (VesselDrinkTileEntity)te;
         return vessel.getVesselItem();
      } else {
         return stack;
      }
   }

   public static Builder vesselDrinkBuilder() {
      return func_215860_a(VesselDrink::new);
   }

   // $FF: synthetic method
   VesselDrink(ILootCondition[] x0, Object x1) {
      this(x0);
   }

   public static class Serializer extends net.minecraft.loot.LootFunction.Serializer {
      public VesselDrink deserialize(JsonObject object, JsonDeserializationContext context, ILootCondition[] conditions) {
         return new VesselDrink(conditions);
      }
   }
}
