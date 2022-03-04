package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.entity.npc.LOTREntityMordorOrcArcher;
import lotr.common.entity.npc.LOTREntityMordorOrcTrader;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;

public class LOTRWorldGenMordorCamp extends LOTRWorldGenCampBase {
   public LOTRWorldGenMordorCamp(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.tableBlock = LOTRMod.commandTable;
      this.brickBlock = LOTRMod.brick;
      this.brickMeta = 0;
      this.brickSlabBlock = LOTRMod.slabSingle;
      this.brickSlabMeta = 1;
      this.fenceBlock = LOTRMod.fence;
      this.fenceMeta = 3;
      this.fenceGateBlock = LOTRMod.fenceGateCharred;
      this.farmBaseBlock = LOTRMod.rock;
      this.farmBaseMeta = 0;
      this.farmCropBlock = LOTRMod.morgulShroom;
      this.farmCropMeta = 0;
      this.hasOrcTorches = true;
      this.hasSkulls = true;
   }

   protected LOTRWorldGenStructureBase2 createTent(boolean flag, Random random) {
      return (LOTRWorldGenStructureBase2)(random.nextInt(6) == 0 ? new LOTRWorldGenMordorForgeTent(false) : new LOTRWorldGenMordorTent(false));
   }

   protected LOTREntityNPC getCampCaptain(World world, Random random) {
      return random.nextBoolean() ? new LOTREntityMordorOrcTrader(world) : null;
   }

   protected void placeNPCRespawner(World world, Random random, int i, int j, int k) {
      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityMordorOrc.class, LOTREntityMordorOrcArcher.class);
      respawner.setCheckRanges(24, -12, 12, 12);
      respawner.setSpawnRanges(8, -4, 4, 16);
      this.placeNPCRespawner(respawner, world, i, j, k);
   }
}
