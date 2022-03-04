package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityGundabadOrc;
import lotr.common.entity.npc.LOTREntityGundabadOrcArcher;
import lotr.common.entity.npc.LOTREntityGundabadOrcMercenaryCaptain;
import lotr.common.entity.npc.LOTREntityGundabadOrcTrader;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;

public class LOTRWorldGenGundabadCamp extends LOTRWorldGenCampBase {
   public LOTRWorldGenGundabadCamp(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.tableBlock = LOTRMod.commandTable;
      this.fenceBlock = LOTRMod.fence;
      this.fenceMeta = 3;
      this.fenceGateBlock = LOTRMod.fenceGateCharred;
      this.hasOrcTorches = true;
      this.hasSkulls = true;
   }

   protected LOTRWorldGenStructureBase2 createTent(boolean flag, Random random) {
      return (LOTRWorldGenStructureBase2)(random.nextInt(6) == 0 ? new LOTRWorldGenGundabadForgeTent(false) : new LOTRWorldGenGundabadTent(false));
   }

   protected LOTREntityNPC getCampCaptain(World world, Random random) {
      return (LOTREntityNPC)(random.nextBoolean() ? new LOTREntityGundabadOrcTrader(world) : new LOTREntityGundabadOrcMercenaryCaptain(world));
   }

   protected void placeNPCRespawner(World world, Random random, int i, int j, int k) {
      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityGundabadOrc.class, LOTREntityGundabadOrcArcher.class);
      respawner.setCheckRanges(24, -12, 12, 12);
      respawner.setSpawnRanges(8, -4, 4, 16);
      this.placeNPCRespawner(respawner, world, i, j, k);
   }
}
