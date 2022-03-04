package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class LOTRBlockReed extends Block implements IPlantable {
   private static int MAX_GROW_HEIGHT = 3;
   private static int META_GROW_END = 15;
   @SideOnly(Side.CLIENT)
   private IIcon iconUpper;
   @SideOnly(Side.CLIENT)
   private IIcon iconLower;

   public LOTRBlockReed() {
      super(Material.field_151585_k);
      float f = 0.375F;
      this.func_149676_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
      this.func_149675_a(true);
      this.func_149711_c(0.0F);
      this.func_149672_a(field_149779_h);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      Block below = world.func_147439_a(i, j - 1, k);
      int belowMeta = world.func_72805_g(i, j - 1, k);
      if (below == this) {
         return true;
      } else {
         return below.func_149688_o() == Material.field_151586_h && belowMeta == 0;
      }
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return this.func_149742_c(world, i, j, k);
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      this.checkCanStay(world, i, j, k);
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      if (this.checkCanStay(world, i, j, k) && this.canReedGrow(world, i, j, k) && world.func_147437_c(i, j + 1, k)) {
         int belowReeds;
         for(belowReeds = 1; world.func_147439_a(i, j - belowReeds, k) == this; ++belowReeds) {
         }

         if (belowReeds < MAX_GROW_HEIGHT) {
            int meta = world.func_72805_g(i, j, k);
            if (meta == META_GROW_END) {
               world.func_147465_d(i, j + 1, k, this, 0, 3);
               world.func_72921_c(i, j, k, 0, 4);
            } else {
               world.func_72921_c(i, j, k, meta + 1, 4);
            }
         }
      }

   }

   protected boolean canReedGrow(World world, int i, int j, int k) {
      return true;
   }

   private boolean checkCanStay(World world, int i, int j, int k) {
      if (!this.func_149718_j(world, i, j, k)) {
         int meta = world.func_72805_g(i, j, k);
         this.func_149697_b(world, i, j, k, meta, 0);
         world.func_147468_f(i, j, k);
         return false;
      } else {
         return true;
      }
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(this);
   }

   public int func_149692_a(int i) {
      return 0;
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      return null;
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getReedsRenderID();
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      if (side == -2) {
         return this.iconLower;
      } else if (side == -1) {
         return this.field_149761_L;
      } else {
         world.func_147439_a(i, j - 1, k);
         Block above = world.func_147439_a(i, j + 1, k);
         return above != this ? this.iconUpper : this.field_149761_L;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == -2) {
         return this.iconLower;
      } else {
         return i == -1 ? this.field_149761_L : this.field_149761_L;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.field_149761_L = iconregister.func_94245_a(this.func_149641_N() + "_mid");
      this.iconUpper = iconregister.func_94245_a(this.func_149641_N() + "_upper");
      this.iconLower = iconregister.func_94245_a(this.func_149641_N() + "_lower");
   }

   @SideOnly(Side.CLIENT)
   public String func_149702_O() {
      return this.func_149641_N();
   }

   public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
      return EnumPlantType.Water;
   }

   public Block getPlant(IBlockAccess world, int i, int j, int k) {
      return this;
   }

   public int getPlantMetadata(IBlockAccess world, int i, int j, int k) {
      return world.func_72805_g(i, j, k);
   }
}
