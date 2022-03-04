package lotr.common.entity.npc;

import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiomeGenDorwinion;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityDorwinionGuard extends LOTREntityDorwinionMan {
   private static ItemStack[] guardWeapons;
   public int grapeAlert;
   public static final int MAX_GRAPE_ALERT = 3;

   public LOTREntityDorwinionGuard(World world) {
      super(world);
      this.addTargetTasks(true);
      this.npcShield = LOTRShields.ALIGNMENT_DORWINION;
   }

   protected EntityAIBase createDorwinionAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, true);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(guardWeapons.length);
      this.npcItemsInv.setMeleeWeapon(guardWeapons[i].func_77946_l());
      if (this.field_70146_Z.nextInt(8) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearIron));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsDorwinion));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsDorwinion));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyDorwinion));
      if (this.field_70146_Z.nextInt(4) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetDorwinion));
      } else {
         this.func_70062_b(4, (ItemStack)null);
      }

      return data;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74768_a("GrapeAlert", this.grapeAlert);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.grapeAlert = nbt.func_74762_e("GrapeAlert");
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.grapeAlert > 0 && this.field_70173_aa % 600 == 0) {
         --this.grapeAlert;
      }

   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "dorwinion/guard/hired" : "dorwinion/guard/friendly";
      } else {
         return "dorwinion/guard/hostile";
      }
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K && damagesource.func_76346_g() instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)damagesource.func_76346_g();
         if (this.grapeAlert >= 3) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.stealDorwinionGrapes);
         }
      }

   }

   public static void defendGrapevines(EntityPlayer entityplayer, World world, int i, int j, int k) {
      if (!entityplayer.field_71075_bZ.field_75098_d) {
         BiomeGenBase biome = world.func_72807_a(i, k);
         LOTRBiomeVariant variant = world.field_73011_w instanceof LOTRWorldProvider ? ((LOTRWorldChunkManager)world.field_73011_w.field_76578_c).getBiomeVariantAt(i, k) : null;
         if (biome instanceof LOTRBiomeGenDorwinion && variant == LOTRBiomeVariant.VINEYARD) {
            float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.DORWINION);
            boolean evil = alignment < 0.0F;
            float limit = 2000.0F;
            float chance = (limit - alignment) / limit;
            chance = Math.max(chance, 0.0F);
            chance = Math.min(chance, 1.0F);
            chance *= chance;
            Iterator var14;
            Object obj;
            LOTREntityDorwinionGuard guard;
            if ((evil || world.field_73012_v.nextFloat() < chance) && world.field_73012_v.nextInt(4) == 0) {
               int nearbyGuards = 0;
               int spawnRange = 8;
               List guardList = world.func_72872_a(LOTREntityDorwinionGuard.class, entityplayer.field_70121_D.func_72314_b((double)spawnRange, (double)spawnRange, (double)spawnRange));
               var14 = guardList.iterator();

               while(var14.hasNext()) {
                  obj = var14.next();
                  guard = (LOTREntityDorwinionGuard)obj;
                  if (!guard.hiredNPCInfo.isActive) {
                     ++nearbyGuards;
                  }
               }

               if (nearbyGuards < 8) {
                  int guardSpawns = 1 + world.field_73012_v.nextInt(6);

                  for(int l = 0; l < guardSpawns; ++l) {
                     LOTREntityDorwinionGuard guard = new LOTREntityDorwinionGuard(world);
                     if (world.field_73012_v.nextBoolean()) {
                        guard = new LOTREntityDorwinionCrossbower(world);
                     }

                     int attempts = 16;

                     for(int a = 0; a < attempts; ++a) {
                        int i1 = i + MathHelper.func_76136_a(world.field_73012_v, -spawnRange, spawnRange);
                        int j1 = j + MathHelper.func_76136_a(world.field_73012_v, -spawnRange / 2, spawnRange / 2);
                        int k1 = k + MathHelper.func_76136_a(world.field_73012_v, -spawnRange, spawnRange);
                        Block belowBlock = world.func_147439_a(i1, j1 - 1, k1);
                        world.func_72805_g(i1, j1 - 1, k1);
                        boolean belowSolid = belowBlock.isSideSolid(world, i1, j1 - 1, k1, ForgeDirection.UP);
                        if (belowSolid && !world.func_147439_a(i1, j1, k1).func_149721_r() && !world.func_147439_a(i1, j1 + 1, k1).func_149721_r()) {
                           ((LOTREntityDorwinionGuard)guard).func_70012_b((double)i1 + 0.5D, (double)j1, (double)k1 + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
                           ((LOTREntityDorwinionGuard)guard).liftSpawnRestrictions = true;
                           if (((LOTREntityDorwinionGuard)guard).func_70601_bi()) {
                              ((LOTREntityDorwinionGuard)guard).liftSpawnRestrictions = false;
                              world.func_72838_d((Entity)guard);
                              ((LOTREntityDorwinionGuard)guard).spawnRidingHorse = false;
                              ((LOTREntityDorwinionGuard)guard).func_110161_a((IEntityLivingData)null);
                              break;
                           }
                        }
                     }
                  }
               }
            }

            int range = 16;
            List guardList = world.func_72872_a(LOTREntityDorwinionGuard.class, entityplayer.field_70121_D.func_72314_b((double)range, (double)range, (double)range));
            boolean anyAlert = false;
            var14 = guardList.iterator();

            while(var14.hasNext()) {
               obj = var14.next();
               guard = (LOTREntityDorwinionGuard)obj;
               if (!guard.hiredNPCInfo.isActive) {
                  if (evil) {
                     guard.func_70624_b(entityplayer);
                     guard.sendSpeechBank(entityplayer, "dorwinion/guard/grapeAttack");
                     guard.grapeAlert = 3;
                     anyAlert = true;
                  } else if (world.field_73012_v.nextFloat() < chance) {
                     ++guard.grapeAlert;
                     if (guard.grapeAlert >= 3) {
                        guard.func_70624_b(entityplayer);
                        guard.sendSpeechBank(entityplayer, "dorwinion/guard/grapeAttack");
                     } else {
                        guard.sendSpeechBank(entityplayer, "dorwinion/guard/grapeWarn");
                     }

                     anyAlert = true;
                  }
               }
            }

            if (anyAlert && alignment >= 0.0F) {
               LOTRLevelData.getData(entityplayer).addAlignment(entityplayer, LOTRAlignmentValues.VINEYARD_STEAL_PENALTY, LOTRFaction.DORWINION, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D);
            }
         }
      }

   }

   static {
      guardWeapons = new ItemStack[]{new ItemStack(Items.field_151040_l), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron)};
   }
}
