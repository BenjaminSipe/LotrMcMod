package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetHuorn;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiomeGenOldForest;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDarkHuorn extends LOTREntityHuornBase {
   public LOTREntityDarkHuorn(World world) {
      super(world);
      this.addTargetTasks(true, LOTREntityAINearestAttackableTargetHuorn.class);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.setTreeType(0);
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.DARK_HUORN;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killDarkHuorn;
   }

   protected boolean isTreeHomeBiome(BiomeGenBase biome) {
      return biome instanceof LOTRBiomeGenOldForest;
   }
}
