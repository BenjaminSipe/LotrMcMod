package lotr.common.entity.animal;

import net.minecraft.world.World;

public class LOTREntityJungleScorpion extends LOTREntityScorpion {
   public LOTREntityJungleScorpion(World world) {
      super(world);
   }

   public boolean func_70601_bi() {
      return super.func_70601_bi() && (this.spawningFromSpawner || this.field_70163_u < 60.0D || this.field_70146_Z.nextInt(100) == 0);
   }
}
