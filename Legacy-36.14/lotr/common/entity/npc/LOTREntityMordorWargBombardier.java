package lotr.common.entity.npc;

import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityMordorWargBombardier extends LOTREntityWargBombardier {
   public LOTREntityMordorWargBombardier(World world) {
      super(world);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.setWargType(LOTREntityWarg.WargType.BLACK);
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.MORDOR;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }
}
