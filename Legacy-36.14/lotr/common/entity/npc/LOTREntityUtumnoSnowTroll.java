package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityUtumnoSnowTroll extends LOTREntitySnowTroll {
   public LOTREntityUtumnoSnowTroll(World world) {
      super(world);
      this.isChilly = true;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.UTUMNO;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killUtumnoTroll;
   }
}
