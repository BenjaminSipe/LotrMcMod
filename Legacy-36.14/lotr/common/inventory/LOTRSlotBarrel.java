package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRSlotBarrel extends Slot {
   private LOTRTileEntityBarrel theBarrel;
   private boolean isWater;

   public LOTRSlotBarrel(LOTRTileEntityBarrel inv, int i, int j, int k) {
      super(inv, i, j, k);
      this.theBarrel = inv;
   }

   public LOTRSlotBarrel setWaterSource() {
      this.isWater = true;
      return this;
   }

   public boolean func_75214_a(ItemStack itemstack) {
      if (this.theBarrel.barrelMode == 0) {
         return this.isWater ? LOTRBrewingRecipes.isWaterSource(itemstack) : true;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_75212_b() {
      IIcon var1;
      if (this.getSlotIndex() > 5) {
         LOTRItemMug var10000 = (LOTRItemMug)LOTRMod.mugAle;
         var1 = LOTRItemMug.barrelGui_emptyBucketSlotIcon;
      } else {
         var1 = null;
      }

      return var1;
   }
}
