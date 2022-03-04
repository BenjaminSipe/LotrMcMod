package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityUtumnoWarg extends LOTREntityWarg {
   public LOTREntityUtumnoWarg(World world) {
      super(world);
   }

   public EntityAIBase getWargAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.7D, true);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(40.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
   }

   public LOTREntityNPC createWargRider() {
      return (LOTREntityNPC)(this.field_70170_p.field_73012_v.nextBoolean() ? new LOTREntityUtumnoOrcArcher(this.field_70170_p) : new LOTREntityUtumnoOrc(this.field_70170_p));
   }

   public boolean canWargBeRidden() {
      return false;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.UTUMNO;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killUtumnoWarg;
   }
}
