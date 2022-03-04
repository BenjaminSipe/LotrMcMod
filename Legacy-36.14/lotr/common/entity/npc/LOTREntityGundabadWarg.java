package lotr.common.entity.npc;

import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityGundabadWarg extends LOTREntityWarg {
   public LOTREntityGundabadWarg(World world) {
      super(world);
   }

   public EntityAIBase getWargAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.75D, false);
   }

   public LOTREntityNPC createWargRider() {
      return (LOTREntityNPC)(this.field_70170_p.field_73012_v.nextBoolean() ? new LOTREntityGundabadOrcArcher(this.field_70170_p) : new LOTREntityGundabadOrc(this.field_70170_p));
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.GUNDABAD;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }
}
