package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityMirkwoodSpider extends LOTREntitySpiderBase {
   public LOTREntityMirkwoodSpider(World world) {
      super(world);
   }

   protected int getRandomSpiderScale() {
      return this.field_70146_Z.nextInt(3);
   }

   protected int getRandomSpiderType() {
      return this.field_70146_Z.nextBoolean() ? 0 : 1 + this.field_70146_Z.nextInt(2);
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.DOL_GULDUR;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      if (flag && this.field_70146_Z.nextInt(4) == 0) {
         this.func_145779_a(LOTRMod.mysteryWeb, 1);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killMirkwoodSpider;
   }
}
