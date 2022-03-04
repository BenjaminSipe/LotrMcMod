package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import lotr.common.entity.npc.LOTREntityHaradPyramidWraith;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.tileentity.LOTRTileEntitySpawnerChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockSpawnerChest extends BlockChest {
   private static boolean dropChestItems = true;
   public final Block chestModel;

   public LOTRBlockSpawnerChest(Block block) {
      super(0);
      this.chestModel = block;
      this.func_149672_a(block.field_149762_H);
      this.func_149647_a((CreativeTabs)null);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntitySpawnerChest();
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return this.chestModel.func_149691_a(i, j);
   }

   public int func_149645_b() {
      return this.chestModel.func_149645_b();
   }

   public float func_149712_f(World world, int i, int j, int k) {
      return this.chestModel.func_149712_f(world, i, j, k);
   }

   public float getExplosionResistance(Entity entity, World world, int i, int j, int k, double explosionX, double explosionY, double explosionZ) {
      return this.chestModel.getExplosionResistance(entity, world, i, j, k, explosionX, explosionY, explosionZ);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      this.spawnEntity(world, i, j, k);
      dropChestItems = false;
      if (!world.field_72995_K) {
         ItemStack[] chestInv = new ItemStack[27];
         TileEntity tileentity = world.func_147438_o(i, j, k);
         int l;
         if (tileentity instanceof IInventory) {
            for(l = 0; l < chestInv.length; ++l) {
               chestInv[l] = ((IInventory)tileentity).func_70301_a(l);
            }
         }

         world.func_147465_d(i, j, k, this.chestModel, world.func_72805_g(i, j, k), 3);

         for(l = 0; l < 27; ++l) {
            ((IInventory)world.func_147438_o(i, j, k)).func_70299_a(l, chestInv[l]);
         }
      }

      dropChestItems = true;
      return true;
   }

   public void func_149749_a(World world, int i, int j, int k, Block block, int meta) {
      if (dropChestItems) {
         this.spawnEntity(world, i, j, k);
         super.func_149749_a(world, i, j, k, block, meta);
      } else {
         world.func_147475_p(i, j, k);
      }

   }

   private void spawnEntity(World world, int i, int j, int k) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntitySpawnerChest) {
         Entity entity = ((LOTRTileEntitySpawnerChest)tileentity).createMob();
         if (entity instanceof EntityLiving) {
            EntityLiving entityliving = (EntityLiving)entity;
            entityliving.func_70012_b((double)i + 0.5D, (double)(j + 1), (double)k + 0.5D, 0.0F, 0.0F);
            entityliving.func_70656_aK();
            if (!world.field_72995_K) {
               entityliving.func_110161_a((IEntityLivingData)null);
               if (entityliving instanceof LOTREntityNPC) {
                  ((LOTREntityNPC)entityliving).isNPCPersistent = true;
               }

               world.func_72838_d(entityliving);
               world.func_72956_a(entityliving, "lotr:wraith.spawn", 1.0F, 0.7F + world.field_73012_v.nextFloat() * 0.6F);
               if (entityliving instanceof LOTREntityHaradPyramidWraith) {
                  for(int l = 0; l < 4; ++l) {
                     LOTREntityDesertScorpion desertScorpion = new LOTREntityDesertScorpion(world);
                     desertScorpion.setSpawningFromMobSpawner(true);
                     double d = entityliving.field_70165_t - world.field_73012_v.nextDouble() * 3.0D + world.field_73012_v.nextDouble() * 3.0D;
                     double d1 = entityliving.field_70163_u;
                     double d2 = entityliving.field_70161_v - world.field_73012_v.nextDouble() * 3.0D + world.field_73012_v.nextDouble() * 3.0D;
                     desertScorpion.func_70012_b(d, d1, d2, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
                     if (desertScorpion.func_70601_bi()) {
                        world.func_72838_d(desertScorpion);
                        desertScorpion.setSpawningFromMobSpawner(false);
                     }
                  }

                  world.func_72942_c(new EntityLightningBolt(world, entityliving.field_70165_t, entityliving.field_70163_u, entityliving.field_70161_v));
               }
            }

         }
      }
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(this.chestModel);
   }

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return Item.func_150898_a(this.chestModel);
   }
}
