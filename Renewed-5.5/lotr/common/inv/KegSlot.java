package lotr.common.inv;

import lotr.common.recipe.DrinkBrewingRecipe;
import lotr.common.tileentity.KegTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class KegSlot extends Slot {
   public static final ResourceLocation EMPTY_BUCKET_TEXTURE = new ResourceLocation("lotr", "item/empty_keg_slot_bucket");
   private KegContainer theKegContainer;
   private boolean isWater;

   public KegSlot(KegContainer keg, IInventory inv, int index, int x, int y) {
      super(inv, index, x, y);
      this.theKegContainer = keg;
   }

   public KegSlot setWaterSource() {
      this.isWater = true;
      this.setBackground(PlayerContainer.field_226615_c_, EMPTY_BUCKET_TEXTURE);
      return this;
   }

   public boolean func_75214_a(ItemStack stack) {
      if (this.theKegContainer.getKegMode() == KegTileEntity.KegMode.EMPTY) {
         return this.isWater ? DrinkBrewingRecipe.isWaterSource(stack) : true;
      } else {
         return false;
      }
   }
}
