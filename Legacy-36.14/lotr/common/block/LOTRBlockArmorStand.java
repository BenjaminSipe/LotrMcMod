package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockArmorStand extends Block {
   public LOTRBlockArmorStand() {
      super(Material.field_151594_q);
      this.func_149711_c(0.5F);
      this.func_149752_b(1.0F);
      this.func_149672_a(Block.field_149769_e);
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      return this.hasTileEntity(world.func_72805_g(i, j, k)) ? AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)i + 1.0D, (double)j + 0.125D, (double)k + 1.0D) : null;
   }

   public boolean hasTileEntity(int metadata) {
      return (metadata & 4) == 0;
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return this.hasTileEntity(metadata) ? new LOTRTileEntityArmorStand() : null;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150344_f.func_149691_a(i, 0);
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

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if (!this.hasTileEntity(world.func_72805_g(i, j, k))) {
         --j;
      }

      if (this.hasTileEntity(world.func_72805_g(i, j, k))) {
         if (!world.field_72995_K) {
            entityplayer.openGui(LOTRMod.instance, 17, world, i, j, k);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP) && j < 255;
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      if (!this.hasTileEntity(meta)) {
         return world.func_147439_a(i, j - 1, k) == this;
      } else {
         return world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP) && world.func_147439_a(i, j + 1, k) == this;
      }
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      int meta = world.func_72805_g(i, j, k);
      if (this.hasTileEntity(meta)) {
         if (!this.func_149718_j(world, i, j, k)) {
            world.func_147468_f(i, j, k);
            if (!world.field_72995_K) {
               this.func_149697_b(world, i, j, k, meta, 0);
            }
         }
      } else if (!this.func_149718_j(world, i, j, k)) {
         world.func_147468_f(i, j, k);
      }

   }

   public void func_149681_a(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
      if (entityplayer.field_71075_bZ.field_75098_d && !this.hasTileEntity(meta) && world.func_147439_a(i, j - 1, k) == this) {
         world.func_147468_f(i, j - 1, k);
      }

   }

   public Item func_149650_a(int i, Random random, int j) {
      return this.hasTileEntity(i) ? LOTRMod.armorStandItem : null;
   }

   public void func_149749_a(World world, int i, int j, int k, Block block, int meta) {
      LOTRTileEntityArmorStand stand = (LOTRTileEntityArmorStand)world.func_147438_o(i, j, k);
      if (stand != null) {
         LOTRMod.dropContainerItems(stand, world, i, j, k);
      }

      super.func_149749_a(world, i, j, k, block, meta);
   }

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return LOTRMod.armorStandItem;
   }
}
