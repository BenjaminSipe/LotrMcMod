package lotr.common.entity.ai;

import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTREntityAIFarmhandAttackRabbit extends LOTREntityAINearestAttackableTargetBasic {
   private LOTREntityNPC theNPC;

   public LOTREntityAIFarmhandAttackRabbit(LOTREntityNPC npc) {
      super(npc, LOTREntityRabbit.class, 0, false);
      this.theNPC = npc;
   }

   public boolean func_75250_a() {
      return this.theNPC.hiredNPCInfo.isActive && !this.theNPC.hiredNPCInfo.isGuardMode() ? false : super.func_75250_a();
   }

   protected double func_111175_f() {
      return 8.0D;
   }
}
