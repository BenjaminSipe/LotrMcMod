package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockPlaceableFood extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon iconBottom;
   @SideOnly(Side.CLIENT)
   private IIcon iconTop;
   @SideOnly(Side.CLIENT)
   private IIcon iconSide;
   @SideOnly(Side.CLIENT)
   private IIcon iconEaten;
   public Item foodItem;
   private float foodHalfWidth;
   private float foodHeight;
   private static int MAX_EATS = 6;
   private int healAmount;
   private float saturationAmount;

   public LOTRBlockPlaceableFood() {
      this(0.4375F, 0.5F);
   }

   public LOTRBlockPlaceableFood(float f, float f1) {
      super(Material.field_151568_F);
      this.foodHalfWidth = f;
      this.foodHeight = f1;
      this.func_149711_c(0.5F);
      this.func_149672_a(Block.field_149775_l);
      this.func_149675_a(true);
      this.setFoodStats(2, 0.1F);
   }

   public LOTRBlockPlaceableFood setFoodStats(int i, float f) {
      this.healAmount = i;
      this.saturationAmount = f;
      return this;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 0) {
         return this.iconBottom;
      } else if (i == 1) {
         return this.iconTop;
      } else {
         return j > 0 && i == 4 ? this.iconEaten : this.iconSide;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.iconBottom = iconregister.func_94245_a(this.func_149641_N() + "_bottom");
      this.iconTop = iconregister.func_94245_a(this.func_149641_N() + "_top");
      this.iconSide = iconregister.func_94245_a(this.func_149641_N() + "_side");
      this.iconEaten = iconregister.func_94245_a(this.func_149641_N() + "_inner");
   }

   public void func_149719_a(IBlockAccess world, int i, int j, int k) {
      world.func_72805_g(i, j, k);
      float f = 0.5F - this.foodHalfWidth;
      float f1 = 0.5F + this.foodHalfWidth;
      float f2 = f + (f1 - f) * ((float)world.func_72805_g(i, j, k) / (float)MAX_EATS);
      this.func_149676_a(f2, 0.0F, f, f1, this.foodHeight, f1);
   }

   public void func_149683_g() {
      float f = 0.5F - this.foodHalfWidth;
      float f1 = 0.5F + this.foodHalfWidth;
      this.func_149676_a(f, 0.0F, f, f1, this.foodHeight, f1);
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      float f = 0.5F - this.foodHalfWidth;
      float f1 = 0.5F + this.foodHalfWidth;
      float f2 = f + (f1 - f) * ((float)world.func_72805_g(i, j, k) / (float)MAX_EATS);
      return AxisAlignedBB.func_72330_a((double)((float)i + f2), (double)j, (double)((float)k + f), (double)((float)i + f1), (double)((float)j + this.foodHeight), (double)((float)k + f1));
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB func_149633_g(World world, int i, int j, int k) {
      return this.func_149668_a(world, i, j, k);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      this.eatCake(world, i, j, k, entityplayer);
      return true;
   }

   private void eatCake(World world, int i, int j, int k, EntityPlayer entityplayer) {
      if (!world.field_72995_K && entityplayer.func_71043_e(false)) {
         entityplayer.func_71024_bL().func_75122_a(this.healAmount, this.saturationAmount);
         entityplayer.func_85030_a("random.burp", 0.5F, world.field_73012_v.nextFloat() * 0.1F + 0.9F);
         int meta = world.func_72805_g(i, j, k);
         ++meta;
         if (meta >= MAX_EATS) {
            world.func_147468_f(i, j, k);
         } else {
            world.func_72921_c(i, j, k, meta, 3);
         }
      }

   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return super.func_149742_c(world, i, j, k) && this.func_149718_j(world, i, j, k);
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         int meta = world.func_72805_g(i, j, k);
         this.func_149697_b(world, i, j, k, meta, 0);
         world.func_147468_f(i, j, k);
      }

   }

   public boolean func_149686_d() {
      return false;
   }

   public boolean func_149662_c() {
      return false;
   }

   public int func_149745_a(Random random) {
      return 0;
   }

   public Item func_149650_a(int i, Random random, int j) {
      return null;
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      if (meta == 0) {
         if (this.foodItem != null) {
            drops.add(new ItemStack(this.foodItem));
         } else {
            drops.add(new ItemStack(this, 1, 0));
         }
      }

      return drops;
   }

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return this.foodItem != null ? this.foodItem : Item.func_150898_a(this);
   }
}
