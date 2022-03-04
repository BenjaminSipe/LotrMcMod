package lotr.common.entity.npc.ai;

import java.util.Comparator;
import java.util.List;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;

public class NPCTargetSorter implements Comparator {
   private final NPCEntity theNPC;

   public NPCTargetSorter(NPCEntity npc) {
      this.theNPC = npc;
   }

   public int compare(Entity e1, Entity e2) {
      double d1 = this.distanceMetricSq(e1);
      double d2 = this.distanceMetricSq(e2);
      if (d1 < d2) {
         return -1;
      } else {
         return d1 > d2 ? 1 : 0;
      }
   }

   private double distanceMetricSq(Entity target) {
      double dSq = this.theNPC.func_70068_e(target);
      double avg = 12.0D;
      double avgSq = avg * avg;
      dSq /= avgSq;
      double nearRange = 8.0D;
      List nearbyWithSameTarget = this.theNPC.field_70170_p.func_225316_b(NPCEntity.class, this.theNPC.func_174813_aQ().func_186662_g(nearRange), (npc) -> {
         return npc != this.theNPC && npc.func_70089_S() && npc.func_70638_az() == target;
      });
      int dupes = nearbyWithSameTarget.size();
      int dupesSq = dupes * dupes;
      return dSq + (double)dupesSq;
   }
}
