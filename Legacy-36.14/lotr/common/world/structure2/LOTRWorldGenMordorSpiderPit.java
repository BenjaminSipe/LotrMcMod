package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityMordorOrcSpiderKeeper;
import lotr.common.entity.npc.LOTREntityMordorSpider;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.world.World;

public class LOTRWorldGenMordorSpiderPit extends LOTRWorldGenMordorWargPit {
   public LOTRWorldGenMordorSpiderPit(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      if (super.generateWithSetRotation(world, random, i, j, k, rotation)) {
         LOTREntityOrc spiderKeeper = new LOTREntityMordorOrcSpiderKeeper(world);
         this.spawnNPCAndSetHome(spiderKeeper, world, 0, 1, 0, 8);
         return true;
      } else {
         return false;
      }
   }

   protected LOTREntityNPC getWarg(World world) {
      return new LOTREntityMordorSpider(world);
   }

   protected void setWargSpawner(LOTREntityNPCRespawner spawner) {
      spawner.setSpawnClass(LOTREntityMordorSpider.class);
   }

   protected void associateGroundBlocks() {
      super.associateGroundBlocks();
      this.clearScanAlias("GROUND_COVER");
      this.addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.webUngoliant, 0);
      this.setBlockAliasChance("GROUND_COVER", 0.04F);
   }
}
