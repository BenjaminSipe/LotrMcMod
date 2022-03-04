package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import lotr.common.world.biome.LOTRBiomeGenMorgulVale;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBlockMorgulFlower extends LOTRBlockFlower {
   public LOTRBlockMorgulFlower() {
      float f = 0.125F;
      this.setFlowerBounds(f, 0.0F, f, 1.0F - f, 0.8F, 1.0F - f);
      this.func_149675_a(true);
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return super.func_149718_j(world, i, j, k) || LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k);
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      super.func_149674_a(world, i, j, k, random);
      if (!world.field_72995_K) {
         BiomeGenBase biome = world.func_72807_a(i, k);
         if (biome instanceof LOTRBiomeGenMorgulVale) {
            double range = 5.0D;
            AxisAlignedBB aabb = AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).func_72314_b(range, range, range);
            List entities = world.func_72872_a(EntityLivingBase.class, aabb);
            Iterator var11 = entities.iterator();

            while(var11.hasNext()) {
               Object obj = var11.next();
               EntityLivingBase entity = (EntityLivingBase)obj;
               if (this.isEntityVulnerable(entity)) {
                  int dur = 200;
                  entity.func_70690_d(new PotionEffect(Potion.field_76431_k.field_76415_H, dur));
               }
            }
         }
      }

   }

   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
      super.func_149670_a(world, i, j, k, entity);
      if (!world.field_72995_K && entity instanceof EntityLivingBase) {
         EntityLivingBase living = (EntityLivingBase)entity;
         if (this.isEntityVulnerable(living)) {
            int dur = 100;
            living.func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, dur));
            living.func_70690_d(new PotionEffect(Potion.field_76440_q.field_76415_H, dur * 2));
         }
      }

   }

   private boolean isEntityVulnerable(EntityLivingBase entity) {
      if (LOTRMod.getNPCFaction(entity) == LOTRFaction.MORDOR) {
         return false;
      } else if (entity instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)entity;
         if (entityplayer.field_71075_bZ.field_75098_d) {
            return false;
         } else {
            float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR);
            float max = 250.0F;
            if (alignment >= max) {
               return false;
            } else if (alignment > 0.0F) {
               float f = alignment / max;
               f = 1.0F - f;
               return entity.func_70681_au().nextFloat() < f;
            } else {
               return true;
            }
         }
      } else {
         return true;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (random.nextInt(4) == 0) {
         double d = (double)((float)i + MathHelper.func_151240_a(random, 0.1F, 0.9F));
         double d1 = (double)((float)j + MathHelper.func_151240_a(random, 0.5F, 0.75F));
         double d2 = (double)((float)k + MathHelper.func_151240_a(random, 0.1F, 0.9F));
         if (random.nextBoolean()) {
            LOTRMod.proxy.spawnParticle("morgulWater", d, d1, d2, 0.0D, 0.0D, 0.0D);
         } else {
            LOTRMod.proxy.spawnParticle("whiteSmoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
         }
      }

   }
}
