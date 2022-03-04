package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class LOTRBlockMarshLights extends Block {
   public LOTRBlockMarshLights() {
      super(Material.field_151594_q);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      return null;
   }

   public boolean func_149703_v() {
      return false;
   }

   public int func_149645_b() {
      return -1;
   }

   public Item func_149650_a(int i, Random random, int j) {
      return null;
   }

   public int func_149745_a(Random random) {
      return 0;
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return this.func_149718_j(world, i, j, k);
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return world.func_147439_a(i, j - 1, k).func_149688_o() == Material.field_151586_h;
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         world.func_147465_d(i, j, k, Blocks.field_150350_a, 0, 2);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (random.nextInt(3) > 0) {
         if (random.nextInt(3) == 0) {
            LOTRMod.proxy.spawnParticle("marshFlame", (double)((float)i + random.nextFloat()), (double)j - 0.5D, (double)((float)k + random.nextFloat()), 0.0D, (double)(0.05F + random.nextFloat() * 0.1F), 0.0D);
         } else {
            LOTRMod.proxy.spawnParticle("marshLight", (double)((float)i + random.nextFloat()), (double)j - 0.5D, (double)((float)k + random.nextFloat()), 0.0D, (double)(0.05F + random.nextFloat() * 0.1F), 0.0D);
         }
      }

   }
}
