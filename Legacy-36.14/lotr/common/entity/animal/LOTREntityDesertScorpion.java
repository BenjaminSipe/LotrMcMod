package lotr.common.entity.animal;

import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import net.minecraft.world.World;

public class LOTREntityDesertScorpion extends LOTREntityScorpion implements LOTRBiomeGenNearHarad.ImmuneToHeat {
   public boolean pyramidSpawned = false;

   public LOTREntityDesertScorpion(World world) {
      super(world);
      this.field_70178_ae = true;
   }

   protected int getRandomScorpionScale() {
      return this.field_70146_Z.nextInt(2);
   }

   public boolean func_70601_bi() {
      return super.func_70601_bi() && (this.spawningFromSpawner || this.pyramidSpawned || this.field_70163_u < 60.0D || this.field_70146_Z.nextInt(500) == 0);
   }

   public boolean func_70814_o() {
      return this.spawningFromSpawner || this.pyramidSpawned || super.func_70814_o();
   }
}
