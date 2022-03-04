package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAINPCFollowParent extends EntityAIBase {
   private LOTREntityNPC theNPC;
   private LOTREntityNPC parentNPC;
   private double moveSpeed;
   private int followTick;

   public LOTREntityAINPCFollowParent(LOTREntityNPC npc, double d) {
      this.theNPC = npc;
      this.moveSpeed = d;
   }

   public boolean func_75250_a() {
      if (this.theNPC.familyInfo.getAge() >= 0) {
         return false;
      } else {
         LOTREntityNPC parent = this.theNPC.familyInfo.getParentToFollow();
         if (parent == null) {
            return false;
         } else if (this.theNPC.func_70068_e(parent) >= 9.0D && this.theNPC.func_70068_e(parent) < 256.0D) {
            this.parentNPC = parent;
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean func_75253_b() {
      if (!this.parentNPC.func_70089_S()) {
         return false;
      } else {
         double d = this.theNPC.func_70068_e(this.parentNPC);
         return d >= 9.0D && d <= 256.0D;
      }
   }

   public void func_75249_e() {
      this.followTick = 0;
   }

   public void func_75251_c() {
      this.parentNPC = null;
   }

   public void func_75246_d() {
      if (this.followTick-- <= 0) {
         this.followTick = 10;
         this.theNPC.func_70661_as().func_75497_a(this.parentNPC, this.moveSpeed);
      }

   }
}
