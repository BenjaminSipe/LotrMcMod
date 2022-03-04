package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIPanic;

public class LOTREntityAIGollumPanic extends EntityAIPanic {
   private LOTREntityGollum theGollum;

   public LOTREntityAIGollumPanic(LOTREntityGollum gollum, double d) {
      super(gollum, d);
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
