package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityBreeHobbit;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeHobbitBurrow extends LOTRWorldGenHobbitBurrow {
   public LOTRWorldGenBreeHobbitBurrow(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      LOTRWorldGenBreeStructure breeBlockProxy = new LOTRWorldGenBreeHouse(false);
      breeBlockProxy.setupRandomBlocks(random);
      this.brickBlock = breeBlockProxy.brickBlock;
      this.brickMeta = breeBlockProxy.brickMeta;
      this.floorBlock = Blocks.field_150347_e;
      this.floorMeta = 0;
      this.plankBlock = breeBlockProxy.plankBlock;
      this.plankMeta = breeBlockProxy.plankMeta;
      this.plankSlabBlock = breeBlockProxy.plankSlabBlock;
      this.plankSlabMeta = breeBlockProxy.plankSlabMeta;
      this.plankStairBlock = breeBlockProxy.plankStairBlock;
      this.fenceBlock = breeBlockProxy.fenceBlock;
      this.fenceMeta = breeBlockProxy.fenceMeta;
      this.fenceGateBlock = breeBlockProxy.fenceGateBlock;
      this.doorBlock = breeBlockProxy.doorBlock;
      this.beamBlock = breeBlockProxy.beamBlock;
      this.beamMeta = breeBlockProxy.beamMeta;
      this.tableBlock = breeBlockProxy.tableBlock;
      this.burrowLoot = LOTRChestContents.BREE_HOUSE;
      this.foodPool = LOTRFoods.BREE;
   }

   protected LOTREntityHobbit createHobbit(World world) {
      return new LOTREntityBreeHobbit(world);
   }

   protected String[] getHobbitCoupleAndHomeNames(Random random) {
      return LOTRNames.getBreeHobbitCoupleAndHomeNames(random);
   }
}
