package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemMattock extends LOTRItemPickaxe {
   private float efficiencyOnProperMaterial;

   public LOTRItemMattock(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemMattock(ToolMaterial material) {
      super(material);
      this.efficiencyOnProperMaterial = material.func_77998_b();
      this.setHarvestLevel("axe", material.func_77996_d());
   }

   public float func_150893_a(ItemStack itemstack, Block block) {
      float f = super.func_150893_a(itemstack, block);
      return f != 1.0F || block == null || block.func_149688_o() != Material.field_151575_d && block.func_149688_o() != Material.field_151585_k && block.func_149688_o() != Material.field_151582_l ? f : this.efficiencyOnProperMaterial;
   }
}
