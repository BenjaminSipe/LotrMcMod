package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;

public class LOTREntityAIHiredRemainStill extends EntityAIBase {
   private LOTREntityNPC theNPC;

   public LOTREntityAIHiredRemainStill(LOTREntityNPC entity) {
      this.theNPC = entity;
      this.func_75248_a(6);
   }

   public boolean func_75250_a() {
      if (!this.theNPC.hiredNPCInfo.isActive) {
         return false;
      } else if (this.theNPC.func_70090_H()) {
         return false;
      } else if (!this.theNPC.field_70122_E) {
         return false;
      } else {
         return this.theNPC.hiredNPCInfo.isHalted() && (this.theNPC.func_70638_az() == null || !this.theNPC.func_70638_az().func_70089_S());
      }
   }

   public void func_75249_e() {
      this.theNPC.func_70661_as().func_75499_g();
   }

   public void func_75246_d() {
      this.theNPC.func_70661_as().func_75499_g();
      Vec3 pos = Vec3.func_72443_a(this.theNPC.field_70165_t, this.theNPC.field_70163_u + (double)this.theNPC.func_70047_e(), this.theNPC.field_70161_v);
      Vec3 look = this.theNPC.func_70040_Z().func_72432_b();
      Vec3 lookUp = pos.func_72441_c(look.field_72450_a * 3.0D, pos.field_72448_b + 0.25D, look.field_72449_c * 3.0D);
      this.theNPC.func_70671_ap().func_75650_a(lookUp.field_72450_a, lookUp.field_72448_b, lookUp.field_72449_c, 20.0F, 20.0F);
   }
}
