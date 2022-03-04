package lotr.common.entity.animal;

import lotr.common.item.LOTRItemLionRug;
import net.minecraft.world.World;

public class LOTREntityLion extends LOTREntityLionBase {
   public LOTREntityLion(World world) {
      super(world);
   }

   public boolean isMale() {
      return true;
   }

   protected LOTRItemLionRug.LionRugType getLionRugType() {
      return LOTRItemLionRug.LionRugType.LION;
   }
}
