package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.item.LOTRItemKebabStand;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTRBlockKebabStand extends BlockContainer {
   private String standTextureName;

   public LOTRBlockKebabStand(String s) {
      super(Material.field_151594_q);
      this.standTextureName = s;
      this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.func_149711_c(0.0F);
      this.func_149752_b(1.0F);
      this.func_149672_a(Block.field_149766_f);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityKebabStand();
   }

   public String getStandTextureName() {
      return this.standTextureName;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150344_f.func_149691_a(i, 0);
   }

   @SideOnly(Side.CLIENT)
   public String func_149702_O() {
      return this.func_149641_N();
   }

   public boolean func_149686_d() {
      return false;
   }

   public boolean func_149662_c() {
      return false;
   }

   public int func_149645_b() {
      return -1;
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      return null;
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return true;
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         int meta = world.func_72805_g(i, j, k);
         this.func_149697_b(world, i, j, k, meta, 0);
         world.func_147468_f(i, j, k);
      }

   }

   public void func_149681_a(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
      if (entityplayer.field_71075_bZ.field_75098_d) {
         meta |= 8;
         world.func_72921_c(i, j, k, meta, 4);
      }

      this.func_149697_b(world, i, j, k, meta, 0);
      super.func_149681_a(world, i, j, k, meta, entityplayer);
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      if ((meta & 8) == 0) {
         ItemStack itemstack = this.getKebabStandDrop(world, i, j, k, meta);
         LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand)world.func_147438_o(i, j, k);
         if (kebabStand != null) {
            drops.add(itemstack);
         }
      }

      return drops;
   }

   public ItemStack getKebabStandDrop(World world, int i, int j, int k, int metadata) {
      ItemStack itemstack = new ItemStack(Item.func_150898_a(this));
      LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand)world.func_147438_o(i, j, k);
      if (kebabStand != null) {
         LOTRItemKebabStand.setKebabData(itemstack, kebabStand);
      }

      return itemstack;
   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
      world.func_147471_g(i, j, k);
      return this.getKebabStandDrop(world, i, j, k, world.func_72805_g(i, j, k));
   }

   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
      int rotation = MathHelper.func_76128_c((double)(entity.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
      int meta = 0;
      if (rotation == 0) {
         meta = 2;
      } else if (rotation == 1) {
         meta = 5;
      } else if (rotation == 2) {
         meta = 3;
      } else if (rotation == 3) {
         meta = 4;
      }

      world.func_72921_c(i, j, k, meta, 2);
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityKebabStand) {
         LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand)tileentity;
         LOTRItemKebabStand.loadKebabData(itemstack, kebabStand);
         kebabStand.onReplaced();
      }

   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityKebabStand) {
         LOTRTileEntityKebabStand stand = (LOTRTileEntityKebabStand)tileentity;
         ItemStack heldItem = entityplayer.func_70694_bm();
         if (!stand.isCooked() && stand.isMeat(heldItem)) {
            if (stand.hasEmptyMeatSlot()) {
               if (!world.field_72995_K && stand.addMeat(heldItem) && !entityplayer.field_71075_bZ.field_75098_d) {
                  --heldItem.field_77994_a;
               }

               return true;
            }
         } else if (stand.getMeatAmount() > 0) {
            if (!world.field_72995_K) {
               boolean wasCooked = stand.isCooked();
               ItemStack meat = stand.removeFirstMeat();
               if (meat != null) {
                  if (!entityplayer.field_71071_by.func_70441_a(meat)) {
                     this.func_149642_a(world, i, j, k, meat);
                  }

                  entityplayer.field_71069_bz.func_75142_b();
                  world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.pop", 0.5F, 0.5F + world.field_73012_v.nextFloat() * 0.5F);
                  if (wasCooked) {
                     LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.cookKebab);
                  }
               }
            }

            return true;
         }
      }

      return false;
   }
}
