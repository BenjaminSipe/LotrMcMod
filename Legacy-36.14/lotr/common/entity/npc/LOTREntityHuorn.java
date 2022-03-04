package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetHuorn;
import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityHuorn extends LOTREntityHuornBase {
   public LOTREntityHuorn(World world) {
      super(world);
      this.addTargetTasks(true, LOTREntityAINearestAttackableTargetHuorn.class);
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.FANGORN;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killHuorn;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }
}
