package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAIHorseFollowHiringPlayer extends EntityAIBase {
   private LOTRNPCMount theHorse;
   private EntityCreature livingHorse;
   private EntityPlayer theHiringPlayer;
   private double moveSpeed;
   private int followTick;
   private float maxNearDist;
   private float minFollowDist;
   private boolean avoidsWater;
   private boolean initSpeed;

   public LOTREntityAIHorseFollowHiringPlayer(LOTRNPCMount entity) {
      this.theHorse = entity;
      this.livingHorse = (EntityCreature)this.theHorse;
      this.minFollowDist = 8.0F;
      this.maxNearDist = 6.0F;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (!this.theHorse.getBelongsToNPC()) {
         return false;
      } else {
         Entity rider = this.livingHorse.field_70153_n;
         if (rider != null && rider.func_70089_S() && rider instanceof LOTREntityNPC) {
            LOTREntityNPC ridingNPC = (LOTREntityNPC)rider;
            if (!ridingNPC.hiredNPCInfo.isActive) {
               return false;
            } else {
               EntityPlayer entityplayer = ridingNPC.hiredNPCInfo.getHiringPlayer();
               if (entityplayer == null) {
                  return false;
               } else if (!ridingNPC.hiredNPCInfo.shouldFollowPlayer()) {
                  return false;
               } else if (this.livingHorse.func_70068_e(entityplayer) < (double)(this.minFollowDist * this.minFollowDist)) {
                  return false;
               } else {
                  this.theHiringPlayer = entityplayer;
                  return true;
               }
            }
         } else {
            return false;
         }
      }
   }

   public boolean func_75253_b() {
      if (this.livingHorse.field_70153_n != null && this.livingHorse.field_70153_n.func_70089_S() && this.livingHorse.field_70153_n instanceof LOTREntityNPC) {
         LOTREntityNPC ridingNPC = (LOTREntityNPC)this.livingHorse.field_70153_n;
         return ridingNPC.hiredNPCInfo.isActive && ridingNPC.hiredNPCInfo.getHiringPlayer() != null && ridingNPC.hiredNPCInfo.shouldFollowPlayer() && !this.livingHorse.func_70661_as().func_75500_f() && this.livingHorse.func_70068_e(this.theHiringPlayer) > (double)(this.maxNearDist * this.maxNearDist);
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.followTick = 0;
      this.avoidsWater = this.livingHorse.func_70661_as().func_75486_a();
      this.livingHorse.func_70661_as().func_75491_a(false);
      if (!this.initSpeed) {
         double d = this.livingHorse.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
         this.moveSpeed = 1.0D / d * 0.37D;
         this.initSpeed = true;
      }

   }

   public void func_75251_c() {
      this.theHiringPlayer = null;
      this.livingHorse.func_70661_as().func_75499_g();
      this.livingHorse.func_70661_as().func_75491_a(this.avoidsWater);
   }

   public void func_75246_d() {
      LOTREntityNPC ridingNPC = (LOTREntityNPC)this.livingHorse.field_70153_n;
      this.livingHorse.func_70671_ap().func_75651_a(this.theHiringPlayer, 10.0F, (float)this.livingHorse.func_70646_bf());
      ridingNPC.field_70177_z = this.livingHorse.field_70177_z;
      ridingNPC.field_70759_as = this.livingHorse.field_70759_as;
      if (ridingNPC.hiredNPCInfo.shouldFollowPlayer() && --this.followTick <= 0) {
         this.followTick = 10;
         if (!this.livingHorse.func_70661_as().func_75497_a(this.theHiringPlayer, this.moveSpeed) && ridingNPC.hiredNPCInfo.teleportAutomatically) {
            double d = ridingNPC.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e();
            d = Math.max(d, 24.0D);
            if (ridingNPC.func_70068_e(this.theHiringPlayer) > d * d) {
               ridingNPC.hiredNPCInfo.tryTeleportToHiringPlayer(false);
            }
         }
      }

   }
}
