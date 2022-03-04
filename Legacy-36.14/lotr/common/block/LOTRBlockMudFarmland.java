package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockMudFarmland extends BlockFarmland {
   public LOTRBlockMudFarmland() {
      this.func_149711_c(0.6F);
      this.func_149672_a(Block.field_149767_g);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return i == 1 ? super.func_149691_a(i, j) : LOTRMod.mud.func_149733_h(i);
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      super.func_149674_a(world, i, j, k, random);
      if (world.func_147439_a(i, j, k) == Blocks.field_150346_d) {
         world.func_147449_b(i, j, k, LOTRMod.mud);
      }

   }

   public boolean canSustainPlant(IBlockAccess world, int i, int j, int k, ForgeDirection direction, IPlantable plantable) {
      return Blocks.field_150458_ak.canSustainPlant(world, i, j, k, direction, plantable);
   }

   public void onPlantGrow(World world, int i, int j, int k, int sourceX, int sourceY, int sourceZ) {
      world.func_147465_d(i, j, k, LOTRMod.mud, 0, 2);
   }

   public boolean isFertile(World world, int i, int j, int k) {
      return Blocks.field_150458_ak.isFertile(world, i, j, k);
   }

   public void func_149746_a(World world, int i, int j, int k, Entity entity, float f) {
      super.func_149746_a(world, i, j, k, entity, f);
      if (world.func_147439_a(i, j, k) == Blocks.field_150346_d) {
         world.func_147449_b(i, j, k, LOTRMod.mud);
      }

   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      super.func_149695_a(world, i, j, k, block);
      if (world.func_147439_a(i, j, k) == Blocks.field_150346_d) {
         world.func_147449_b(i, j, k, LOTRMod.mud);
      }

   }

   public Item func_149650_a(int i, Random random, int j) {
      return LOTRMod.mud.func_149650_a(0, random, j);
   }

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return Item.func_150898_a(LOTRMod.mud);
   }
}
