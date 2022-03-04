package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockRock extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon[] rockIcons;
   @SideOnly(Side.CLIENT)
   private IIcon iconMordorSide;
   @SideOnly(Side.CLIENT)
   private IIcon iconMordorMoss;
   private String[] rockNames = new String[]{"mordor", "gondor", "rohan", "blue", "red", "chalk"};

   public LOTRBlockRock() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(1.5F);
      this.func_149752_b(10.0F);
      this.func_149672_a(Block.field_149769_e);
   }

   public boolean isReplaceableOreGen(World world, int i, int j, int k, Block target) {
      if (target == this) {
         return world.func_72805_g(i, j, k) == 0;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.rockIcons = new IIcon[this.rockNames.length];

      for(int i = 0; i < this.rockNames.length; ++i) {
         String subName = this.func_149641_N() + "_" + this.rockNames[i];
         this.rockIcons[i] = iconregister.func_94245_a(subName);
         if (i == 0) {
            this.iconMordorSide = iconregister.func_94245_a(subName + "_side");
            this.iconMordorMoss = iconregister.func_94245_a(subName + "_moss");
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      int meta = world.func_72805_g(i, j, k);
      if (meta == 0 && side != 1 && side != 0) {
         Block above = world.func_147439_a(i, j + 1, k);
         if (above == LOTRMod.mordorMoss) {
            return this.iconMordorMoss;
         }
      }

      return super.func_149673_e(world, i, j, k, side);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int side, int meta) {
      if (meta >= this.rockNames.length) {
         meta = 0;
      }

      return meta == 0 && side != 1 && side != 0 ? this.iconMordorSide : this.rockIcons[meta];
   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.rockNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (world.func_147439_a(i, j, k) == this && world.func_72805_g(i, j, k) == 0 && random.nextInt(10) == 0) {
         world.func_72869_a("smoke", (double)((float)i + random.nextFloat()), (double)((float)j + 1.1F), (double)((float)k + random.nextFloat()), 0.0D, 0.0D, 0.0D);
      }

   }
}
