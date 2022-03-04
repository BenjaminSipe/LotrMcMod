package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIGollumRemainStill extends EntityAIBase {
   private LOTREntityGollum theGollum;

   public LOTREntityAIGollumRemainStill(LOTREntityGollum entity) {
      this.theGollum = entity;
      this.func_75248_a(5);
   }

   public boolean func_75250_a() {
      if (this.theGollum.getGollumOwner() == null) {
         return false;
      } else if (this.theGollum.func_70090_H()) {
         return false;
      } else {
         return !this.theGollum.field_70122_E ? false : this.theGollum.isGollumSitting();
      }
   }

   public void func_75249_e() {
      this.theGollum.func_70661_as().func_75499_g();
   }
}
