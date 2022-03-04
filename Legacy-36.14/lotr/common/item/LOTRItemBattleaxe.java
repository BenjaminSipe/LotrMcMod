package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class LOTRItemBattleaxe extends LOTRItemSword {
   private float efficiencyOnProperMaterial;

   public LOTRItemBattleaxe(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemBattleaxe(ToolMaterial material) {
      super(material);
      this.efficiencyOnProperMaterial = material.func_77998_b();
      this.setHarvestLevel("axe", material.func_77996_d());
      this.lotrWeaponDamage += 2.0F;
   }

   public float func_150893_a(ItemStack itemstack, Block block) {
      float f = super.func_150893_a(itemstack, block);
      return f != 1.0F || block == null || block.func_149688_o() != Material.field_151575_d && block.func_149688_o() != Material.field_151585_k && block.func_149688_o() != Material.field_151582_l ? f : this.efficiencyOnProperMaterial;
   }

   public boolean func_150894_a(ItemStack itemstack, World world, Block block, int i, int j, int k, EntityLivingBase entity) {
      if ((double)block.func_149712_f(world, i, j, k) != 0.0D) {
         itemstack.func_77972_a(1, entity);
      }

      return true;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.none;
   }
}
