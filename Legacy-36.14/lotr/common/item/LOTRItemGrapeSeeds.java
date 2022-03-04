package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockGrapevine;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class LOTRItemGrapeSeeds extends Item implements IPlantable {
   private Block grapevineBlock;

   public LOTRItemGrapeSeeds(Block block) {
      this.grapevineBlock = block;
      this.func_77637_a(LOTRCreativeTabs.tabMaterials);
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (entityplayer.func_82247_a(i, j, k, side, itemstack)) {
         Block block = world.func_147439_a(i, j, k);
         if (block == LOTRMod.grapevine && LOTRBlockGrapevine.canPlantGrapesAt(world, i, j, k, this)) {
            world.func_147465_d(i, j, k, this.grapevineBlock, 0, 3);
            --itemstack.field_77994_a;
            return true;
         }
      }

      return false;
   }

   public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
      return EnumPlantType.Crop;
   }

   public Block getPlant(IBlockAccess world, int i, int j, int k) {
      return this.grapevineBlock;
   }

   public int getPlantMetadata(IBlockAccess world, int i, int j, int k) {
      return 0;
   }
}
