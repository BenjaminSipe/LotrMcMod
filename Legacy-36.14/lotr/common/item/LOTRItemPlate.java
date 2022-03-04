package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.block.LOTRBlockPlate;
import lotr.common.dispenser.LOTRDispensePlate;
import lotr.common.entity.projectile.LOTREntityPlate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemPlate extends ItemReed {
   public Block plateBlock;

   public LOTRItemPlate(Block block) {
      super(block);
      this.plateBlock = block;
      ((LOTRBlockPlate)this.plateBlock).setPlateItem(this);
      this.func_77637_a(LOTRCreativeTabs.tabFood);
      BlockDispenser.field_149943_a.func_82595_a(this, new LOTRDispensePlate(this.plateBlock));
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      LOTREntityPlate plate = new LOTREntityPlate(world, this.plateBlock, entityplayer);
      world.func_72956_a(entityplayer, "random.bow", 1.0F, 1.0F / (field_77697_d.nextFloat() * 0.4F + 1.2F) + 0.25F);
      if (!world.field_72995_K) {
         world.func_72838_d(plate);
      }

      if (!entityplayer.field_71075_bZ.field_75098_d) {
         --itemstack.field_77994_a;
      }

      return itemstack;
   }

   public boolean isValidArmor(ItemStack itemstack, int armorType, Entity entity) {
      return armorType == 0;
   }
}
