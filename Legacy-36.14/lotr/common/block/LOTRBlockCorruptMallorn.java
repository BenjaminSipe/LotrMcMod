package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import lotr.common.tileentity.LOTRTileEntityCorruptMallorn;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRBlockCorruptMallorn extends LOTRBlockFlower {
   public static int ENT_KILLS = 3;

   public LOTRBlockCorruptMallorn() {
      float f = 0.4F;
      this.func_149676_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.func_149715_a(0.625F);
   }

   public boolean hasTileEntity(int meta) {
      return true;
   }

   public TileEntity createTileEntity(World world, int meta) {
      return new LOTRTileEntityCorruptMallorn();
   }

   public static void summonEntBoss(World world, int i, int j, int k) {
      world.func_147468_f(i, j, k);
      LOTREntityMallornEnt ent = new LOTREntityMallornEnt(world);
      ent.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
      ent.func_110161_a((IEntityLivingData)null);
      world.func_72838_d(ent);
      ent.sendEntBossSpeech("summon");
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      if (!world.field_72995_K) {
         super.func_149674_a(world, i, j, k, random);
      }

   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      drops.add(new ItemStack(LOTRMod.sapling, 1, 1));
      return drops;
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      for(int l = 0; l < 2; ++l) {
         double d = (double)i + 0.25D + (double)(random.nextFloat() * 0.5F);
         double d1 = (double)j + 0.5D;
         double d2 = (double)k + 0.25D + (double)(random.nextFloat() * 0.5F);
         double d3 = -0.05D + (double)random.nextFloat() * 0.1D;
         double d4 = 0.1D + (double)random.nextFloat() * 0.1D;
         double d5 = -0.05D + (double)random.nextFloat() * 0.1D;
         LOTRMod.proxy.spawnParticle("morgulPortal", d, d1, d2, d3, d4, d5);
      }

   }

   public int func_149645_b() {
      return 1;
   }
}
