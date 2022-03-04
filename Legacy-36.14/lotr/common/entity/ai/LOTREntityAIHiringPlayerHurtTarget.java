package lotr.common.entity.ai;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAIHiringPlayerHurtTarget extends EntityAITarget {
   private LOTREntityNPC theNPC;
   private EntityLivingBase theTarget;
   private int playerLastAttackerTime;

   public LOTREntityAIHiringPlayerHurtTarget(LOTREntityNPC entity) {
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
            this.theTarget = entityplayer.func_110144_aD();
            int i = entityplayer.func_142013_aG();
            if (i == this.playerLastAttackerTime) {
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
      this.theNPC.hiredNPCInfo.wasAttackCommanded = true;
      EntityPlayer entityplayer = this.theNPC.hiredNPCInfo.getHiringPlayer();
      if (entityplayer != null) {
         this.playerLastAttackerTime = entityplayer.func_142013_aG();
      }

      super.func_75249_e();
   }
}
