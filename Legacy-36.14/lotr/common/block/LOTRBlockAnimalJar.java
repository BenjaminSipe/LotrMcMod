package lotr.common.block;

import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.item.LOTRItemAnimalJar;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class LOTRBlockAnimalJar extends BlockContainer {
   public LOTRBlockAnimalJar(Material material) {
      super(material);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
   }

   public abstract boolean canCapture(Entity var1);

   public abstract float getJarEntityHeight();

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityAnimalJar();
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return this.func_149718_j(world, i, j, k);
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

   public Item func_149650_a(int i, Random random, int j) {
      return null;
   }

   public int func_149692_a(int i) {
      return i;
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      if ((meta & 8) == 0) {
         ItemStack itemstack = this.getJarDrop(world, i, j, k, meta);
         LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)world.func_147438_o(i, j, k);
         if (jar != null) {
            drops.add(itemstack);
         }
      }

      return drops;
   }

   public ItemStack getJarDrop(World world, int i, int j, int k, int metadata) {
      ItemStack itemstack = new ItemStack(Item.func_150898_a(this), 1, this.func_149692_a(metadata));
      LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)world.func_147438_o(i, j, k);
      if (jar != null) {
         LOTRItemAnimalJar.setEntityData(itemstack, jar.getEntityData());
      }

      return itemstack;
   }

   public int getLightValue(IBlockAccess world, int i, int j, int k) {
      TileEntity te = world.func_147438_o(i, j, k);
      if (te instanceof LOTRTileEntityAnimalJar) {
         LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)te;
         int light = jar.getLightValue();
         if (light > 0) {
            return light;
         }
      }

      return super.getLightValue(world, i, j, k);
   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
      world.func_147471_g(i, j, k);
      return this.getJarDrop(world, i, j, k, world.func_72805_g(i, j, k));
   }
}
