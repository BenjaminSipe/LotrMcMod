package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockMudGrass extends Block implements IGrowable {
   @SideOnly(Side.CLIENT)
   private IIcon iconTop;

   public LOTRBlockMudGrass() {
      super(Material.field_151577_b);
      this.func_149711_c(0.6F);
      this.func_149672_a(Block.field_149779_h);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149675_a(true);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 1) {
         return this.iconTop;
      } else {
         return i == 0 ? LOTRMod.mud.func_149733_h(i) : this.field_149761_L;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.field_149761_L = iconregister.func_94245_a(this.func_149641_N() + "_side");
      this.iconTop = iconregister.func_94245_a(this.func_149641_N() + "_top");
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      Blocks.field_150349_c.func_149674_a(world, i, j, k, random);
   }

   public boolean canSustainPlant(IBlockAccess world, int i, int j, int k, ForgeDirection direction, IPlantable plantable) {
      return Blocks.field_150349_c.canSustainPlant(world, i, j, k, direction, plantable) || plantable instanceof BlockStem;
   }

   public void onPlantGrow(World world, int i, int j, int k, int sourceX, int sourceY, int sourceZ) {
      world.func_147465_d(i, j, k, LOTRMod.mud, 0, 2);
   }

   public Item func_149650_a(int i, Random random, int j) {
      return LOTRMod.mud.func_149650_a(0, random, j);
   }

   @SideOnly(Side.CLIENT)
   public int func_149635_D() {
      return 16777215;
   }

   @SideOnly(Side.CLIENT)
   public int func_149741_i(int i) {
      return 16777215;
   }

   @SideOnly(Side.CLIENT)
   public int func_149720_d(IBlockAccess world, int i, int j, int k) {
      return 16777215;
   }

   public boolean func_149851_a(World world, int i, int j, int k, boolean flag) {
      return Blocks.field_150349_c.func_149851_a(world, i, j, k, flag);
   }

   public boolean func_149852_a(World world, Random random, int i, int j, int k) {
      return Blocks.field_150349_c.func_149852_a(world, random, i, j, k);
   }

   public void func_149853_b(World world, Random random, int i, int j, int k) {
      Blocks.field_150349_c.func_149853_b(world, random, i, j, k);
   }
}
