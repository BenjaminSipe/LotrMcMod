package lotr.common.entity.ai;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAIHiringPlayerHurtByTarget extends EntityAITarget {
   private LOTREntityNPC theNPC;
   private EntityLivingBase theTarget;
   private int playerRevengeTimer;

   public LOTREntityAIHiringPlayerHurtByTarget(LOTREntityNPC entity) {
      super(entity, false);
      this.theNPC = entity;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (this.theNPC.hiredNPCInfo.isActive && !this.theNPC.hiredNPCInfo.isHalted()) {
         EntityPlayer entityplayer = this.theNPC.hiredNPCInfo.getHiringPlayer();
         if (entityplayer == null) {
            return false;
         } else {
            this.theTarget = entityplayer.func_70643_av();
            int i = entityplayer.func_142015_aE();
            if (i == this.playerRevengeTimer) {
               return false;
            } else {
               return LOTRMod.canNPCAttackEntity(this.theNPC, this.theTarget, true) && this.func_75296_a(this.theTarget, false);
            }
         }
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.theNPC.func_70624_b(this.theTarget);
      EntityPlayer entityplayer = this.theNPC.hiredNPCInfo.getHiringPlayer();
      if (entityplayer != null) {
         this.playerRevengeTimer = entityplayer.func_142015_aE();
      }

      super.func_75249_e();
   }
}
