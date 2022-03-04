package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntityMordorSpider extends LOTREntitySpiderBase {
   public LOTREntityMordorSpider(World world) {
      super(world);
   }

   protected int getRandomSpiderScale() {
      return 1 + this.field_70146_Z.nextInt(3);
   }

   protected int getRandomSpiderType() {
      return VENOM_POISON;
   }

   public IEntityLivingData initCreatureForHire(IEntityLivingData data) {
      data = super.func_110161_a(data);
      return data;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (!this.field_70170_p.field_72995_K && this.field_70146_Z.nextInt(3) == 0) {
         LOTREntityNPC rider = this.field_70146_Z.nextBoolean() ? new LOTREntityMordorOrcArcher(this.field_70170_p) : new LOTREntityMordorOrc(this.field_70170_p);
         ((LOTREntityNPC)rider).func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
         ((LOTREntityNPC)rider).func_110161_a((IEntityLivingData)null);
         ((LOTREntityNPC)rider).isNPCPersistent = this.isNPCPersistent;
         this.field_70170_p.func_72838_d((Entity)rider);
         ((LOTREntityNPC)rider).func_70078_a(this);
      }

      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.MORDOR;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killMordorSpider;
   }
}
