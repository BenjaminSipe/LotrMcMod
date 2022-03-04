package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.npc.LOTREntityUmbarArcher;
import lotr.common.entity.npc.LOTREntityUmbarCaptain;
import lotr.common.entity.npc.LOTREntityUmbarWarrior;
import net.minecraft.world.World;

public class LOTRWorldGenUmbarFortress extends LOTRWorldGenSouthronFortress {
   public LOTRWorldGenUmbarFortress(boolean flag) {
      super(flag);
   }

   protected boolean isUmbar() {
      return true;
   }

   protected LOTREntityNearHaradrimBase createWarrior(World world, Random random) {
      return (LOTREntityNearHaradrimBase)(random.nextInt(3) == 0 ? new LOTREntityUmbarArcher(world) : new LOTREntityUmbarWarrior(world));
   }

   protected LOTREntityNearHaradrimBase createCaptain(World world, Random random) {
      return new LOTREntityUmbarCaptain(world);
   }

   protected void setSpawnClasses(LOTREntityNPCRespawner spawner) {
      spawner.setSpawnClasses(LOTREntityUmbarWarrior.class, LOTREntityUmbarArcher.class);
   }
}
