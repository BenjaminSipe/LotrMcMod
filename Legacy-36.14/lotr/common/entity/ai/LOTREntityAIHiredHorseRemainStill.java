package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIHiredHorseRemainStill extends EntityAIBase {
   private LOTRNPCMount theHorse;
   private EntityCreature livingHorse;

   public LOTREntityAIHiredHorseRemainStill(LOTRNPCMount entity) {
      this.theHorse = entity;
      this.livingHorse = (EntityCreature)this.theHorse;
      this.func_75248_a(5);
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
            } else if (this.livingHorse.func_70090_H()) {
               return false;
            } else if (!this.livingHorse.field_70122_E) {
               return false;
            } else {
               return ridingNPC.hiredNPCInfo.isHalted() && (ridingNPC.func_70638_az() == null || !ridingNPC.func_70638_az().func_70089_S());
            }
         } else {
            return false;
         }
      }
   }

   public void func_75249_e() {
      this.livingHorse.func_70661_as().func_75499_g();
   }
}
