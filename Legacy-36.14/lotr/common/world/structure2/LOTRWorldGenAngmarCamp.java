package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityAngmarOrc;
import lotr.common.entity.npc.LOTREntityAngmarOrcArcher;
import lotr.common.entity.npc.LOTREntityAngmarOrcTrader;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarCamp extends LOTRWorldGenCampBase {
   public LOTRWorldGenAngmarCamp(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.tableBlock = LOTRMod.commandTable;
      this.brickBlock = LOTRMod.brick2;
      this.brickMeta = 0;
      this.brickSlabBlock = LOTRMod.slabSingle3;
      this.brickSlabMeta = 3;
      this.fenceBlock = LOTRMod.fence;
      this.fenceMeta = 3;
      this.fenceGateBlock = LOTRMod.fenceGateCharred;
      this.hasOrcTorches = true;
      this.hasSkulls = true;
   }

   protected LOTRWorldGenStructureBase2 createTent(boolean flag, Random random) {
      return (LOTRWorldGenStructureBase2)(random.nextInt(6) == 0 ? new LOTRWorldGenAngmarForgeTent(false) : new LOTRWorldGenAngmarTent(false));
   }

   protected LOTREntityNPC getCampCaptain(World world, Random random) {
      return random.nextBoolean() ? new LOTREntityAngmarOrcTrader(world) : null;
   }

   protected void placeNPCRespawner(World world, Random random, int i, int j, int k) {
      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityAngmarOrc.class, LOTREntityAngmarOrcArcher.class);
      respawner.setCheckRanges(24, -12, 12, 12);
      respawner.setSpawnRanges(8, -4, 4, 16);
      this.placeNPCRespawner(respawner, world, i, j, k);
   }
}
