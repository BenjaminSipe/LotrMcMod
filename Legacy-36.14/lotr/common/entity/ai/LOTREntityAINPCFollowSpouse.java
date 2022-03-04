package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAINPCFollowSpouse extends EntityAIBase {
   private LOTREntityNPC theNPC;
   private LOTREntityNPC theSpouse;
   private double moveSpeed;
   private int followTick;

   public LOTREntityAINPCFollowSpouse(LOTREntityNPC npc, double d) {
      this.theNPC = npc;
      this.moveSpeed = d;
   }

   public boolean func_75250_a() {
      LOTREntityNPC spouse = this.theNPC.familyInfo.getSpouse();
      if (spouse == null) {
         return false;
      } else if (spouse.func_70089_S() && this.theNPC.func_70068_e(spouse) >= 36.0D && this.theNPC.func_70068_e(spouse) < 256.0D) {
         this.theSpouse = spouse;
         return true;
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      if (!this.theSpouse.func_70089_S()) {
         return false;
      } else {
         double d = this.theNPC.func_70068_e(this.theSpouse);
         return d >= 36.0D && d <= 256.0D;
      }
   }

   public void func_75249_e() {
      this.followTick = 200;
   }

   public void func_75251_c() {
      this.theSpouse = null;
   }

   public void func_75246_d() {
      --this.followTick;
      if (this.theNPC.func_70068_e(this.theSpouse) > 144.0D || this.followTick <= 0) {
         this.followTick = 200;
         this.theNPC.func_70661_as().func_75497_a(this.theSpouse, this.moveSpeed);
      }

   }
}
