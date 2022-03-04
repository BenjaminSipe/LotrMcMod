package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

public class LOTREntityAIGollumAvoidEntity extends EntityAIAvoidEntity {
   private LOTREntityGollum theGollum;

   public LOTREntityAIGollumAvoidEntity(LOTREntityGollum gollum, Class entityClass, float f, double d, double d1) {
      super(gollum, entityClass, f, d, d1);
      this.theGollum = gollum;
   }

   public void func_75249_e() {
      super.func_75249_e();
      this.theGollum.setGollumFleeing(true);
   }

   public void func_75251_c() {
      super.func_75251_c();
      this.theGollum.setGollumFleeing(false);
   }
}
