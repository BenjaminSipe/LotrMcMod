package lotr.common.entity.ai;

import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTRBannerBearer;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAIFollowHiringPlayer extends EntityAIBase {
   private LOTREntityNPC theNPC;
   private final boolean isBannerBearer;
   private EntityPlayer theHiringPlayer;
   private double moveSpeed;
   private int followTick;
   private float maxNearDist;
   private float minFollowDist;
   private boolean avoidsWater;
   private EntityLiving bannerBearerTarget;

   public LOTREntityAIFollowHiringPlayer(LOTREntityNPC entity) {
      this.theNPC = entity;
      this.isBannerBearer = entity instanceof LOTRBannerBearer;
      double entityMoveSpeed = entity.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
      this.moveSpeed = 1.0D / entityMoveSpeed * 0.37D;
      this.minFollowDist = 8.0F;
      this.maxNearDist = 6.0F;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (!this.theNPC.hiredNPCInfo.isActive) {
         return false;
      } else {
         EntityPlayer entityplayer = this.theNPC.hiredNPCInfo.getHiringPlayer();
         if (entityplayer == null) {
            return false;
         } else {
            this.theHiringPlayer = entityplayer;
            if (!this.theNPC.hiredNPCInfo.shouldFollowPlayer()) {
               return false;
            } else {
               if (this.isBannerBearer) {
                  List alliesToFollow = new ArrayList();
                  List nearbyEntities = this.theNPC.field_70170_p.func_72872_a(EntityLiving.class, this.theNPC.field_70121_D.func_72314_b(16.0D, 16.0D, 16.0D));

                  for(int i = 0; i < nearbyEntities.size(); ++i) {
                     EntityLiving entity = (EntityLiving)nearbyEntities.get(i);
                     if (entity != this.theNPC && LOTRMod.getNPCFaction(entity) == this.theNPC.getFaction()) {
                        if (entity instanceof LOTREntityNPC) {
                           LOTREntityNPC npc = (LOTREntityNPC)entity;
                           if (!npc.hiredNPCInfo.isActive || npc.hiredNPCInfo.getHiringPlayer() != entityplayer) {
                              continue;
                           }
                        }

                        alliesToFollow.add(entity);
                     }
                  }

                  EntityLiving entityToFollow = null;
                  double d = Double.MAX_VALUE;

                  for(int i = 0; i < alliesToFollow.size(); ++i) {
                     EntityLiving entity = (EntityLiving)alliesToFollow.get(i);
                     double dist = this.theNPC.func_70068_e(entity);
                     if (dist < d && dist > (double)(this.minFollowDist * this.minFollowDist)) {
                        d = dist;
                        entityToFollow = entity;
                     }
                  }

                  if (entityToFollow != null) {
                     this.bannerBearerTarget = entityToFollow;
                     return true;
                  }
               }

               return this.theNPC.func_70068_e(entityplayer) >= (double)(this.minFollowDist * this.minFollowDist);
            }
         }
      }
   }

   public boolean func_75253_b() {
      if (this.theNPC.hiredNPCInfo.isActive && this.theNPC.hiredNPCInfo.getHiringPlayer() != null && this.theNPC.hiredNPCInfo.shouldFollowPlayer() && !this.theNPC.func_70661_as().func_75500_f()) {
         EntityLivingBase target = this.bannerBearerTarget != null ? this.bannerBearerTarget : this.theHiringPlayer;
         return this.theNPC.func_70068_e((Entity)target) > (double)(this.maxNearDist * this.maxNearDist);
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.followTick = 0;
      this.avoidsWater = this.theNPC.func_70661_as().func_75486_a();
      this.theNPC.func_70661_as().func_75491_a(false);
   }

   public void func_75251_c() {
      this.theHiringPlayer = null;
      this.bannerBearerTarget = null;
      this.theNPC.func_70661_as().func_75499_g();
      this.theNPC.func_70661_as().func_75491_a(this.avoidsWater);
   }

   public void func_75246_d() {
      EntityLivingBase target = this.bannerBearerTarget != null ? this.bannerBearerTarget : this.theHiringPlayer;
      this.theNPC.func_70671_ap().func_75651_a((Entity)target, 10.0F, (float)this.theNPC.func_70646_bf());
      if (this.theNPC.hiredNPCInfo.shouldFollowPlayer() && --this.followTick <= 0) {
         this.followTick = 10;
         if (!this.theNPC.func_70661_as().func_75497_a((Entity)target, this.moveSpeed) && this.theNPC.hiredNPCInfo.teleportAutomatically) {
            double d = this.theNPC.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e();
            d = Math.max(d, 24.0D);
            if (this.theNPC.func_70068_e(this.theHiringPlayer) > d * d) {
               this.theNPC.hiredNPCInfo.tryTeleportToHiringPlayer(false);
            }
         }
      }

   }
}
