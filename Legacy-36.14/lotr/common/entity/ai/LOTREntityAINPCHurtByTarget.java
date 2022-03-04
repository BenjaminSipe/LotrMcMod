package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;

public class LOTREntityAINPCHurtByTarget extends EntityAIHurtByTarget {
   public LOTREntityAINPCHurtByTarget(LOTREntityNPC npc, boolean flag) {
      super(npc, flag);
   }

   protected boolean func_75296_a(EntityLivingBase entity, boolean flag) {
      if (entity != this.field_75299_d.field_70154_o && entity != this.field_75299_d.field_70153_n) {
         int homeX = this.field_75299_d.func_110172_bL().field_71574_a;
         int homeY = this.field_75299_d.func_110172_bL().field_71572_b;
         int homeZ = this.field_75299_d.func_110172_bL().field_71573_c;
         int homeRange = (int)this.field_75299_d.func_110174_bM();
         this.field_75299_d.func_110177_bN();
         boolean superSuitable = super.func_75296_a(entity, flag);
         this.field_75299_d.func_110171_b(homeX, homeY, homeZ, homeRange);
         return superSuitable;
      } else {
         return false;
      }
   }
}
