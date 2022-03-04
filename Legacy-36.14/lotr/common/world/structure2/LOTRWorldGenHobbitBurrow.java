package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenHobbitBurrow extends LOTRWorldGenHobbitStructure {
   protected LOTRChestContents burrowLoot;
   protected LOTRFoods foodPool;

   public LOTRWorldGenHobbitBurrow(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.bedBlock = LOTRMod.strawBed;
      this.burrowLoot = LOTRChestContents.HOBBIT_HOLE_LARDER;
      this.foodPool = LOTRFoods.HOBBIT;
   }

   protected boolean makeWealthy(Random random) {
      return false;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 8);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         for(i1 = -9; i1 <= 9; ++i1) {
            for(k1 = -7; k1 <= 8; ++k1) {
               j1 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, j1, k1)) {
                  return false;
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -7; k1 <= 3; ++k1) {
            for(j1 = 1; j1 <= 3; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      this.loadStrScan("hobbit_burrow");
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("FLOOR", this.floorBlock, this.floorMeta);
      this.associateBlockMetaAlias("COBBLE_WALL", Blocks.field_150463_bK, 0);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockAlias("DOOR", this.doorBlock);
      this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
      this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 4);
      this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 8);
      this.associateBlockMetaAlias("TABLE", this.tableBlock, 0);
      this.associateBlockMetaAlias("CARPET", this.carpetBlock, this.carpetMeta);
      this.addBlockMetaAliasOption("THATCH_FLOOR", 1, LOTRMod.thatchFloor, 0);
      this.setBlockAliasChance("THATCH_FLOOR", 0.33F);
      this.associateBlockMetaAlias("LEAF", Blocks.field_150362_t, 4);
      this.generateStrScan(world, random, 0, 0, 0);
      this.setBlockAndMetadata(world, -2, 1, -2, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -3, 1, -2, this.bedBlock, 11);
      this.setBlockAndMetadata(world, -2, 1, -1, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -3, 1, -1, this.bedBlock, 11);
      this.placeChest(world, random, -3, 1, 0, 4, this.burrowLoot, MathHelper.func_76136_a(random, 1, 3));
      this.placeChest(world, random, 1, 1, 2, 2, this.burrowLoot, MathHelper.func_76136_a(random, 1, 3));
      this.placeChest(world, random, 0, 1, 2, 2, this.burrowLoot, MathHelper.func_76136_a(random, 1, 3));
      this.placePlateWithCertainty(world, random, 3, 2, -1, this.plateBlock, this.foodPool);
      this.placeRandomFlowerPot(world, random, -3, 2, -5);
      this.placeRandomFlowerPot(world, random, -1, 4, -4);
      this.placeSign(world, 0, 2, -4, Blocks.field_150444_as, 2, new String[]{"", this.homeName1, this.homeName2, ""});

      for(i1 = -8; i1 <= 8; ++i1) {
         for(k1 = -6; k1 <= 8; ++k1) {
            j1 = this.getTopBlock(world, i1, k1);
            if (j1 >= 1 && this.isAir(world, i1, j1, k1) && this.getBlock(world, i1, j1 - 1, k1) == Blocks.field_150349_c) {
               if (random.nextInt(20) == 0) {
                  this.plantFlower(world, random, i1, j1, k1);
               } else if (random.nextInt(7) == 0) {
                  this.plantTallGrass(world, random, i1, j1, k1);
               }
            }
         }
      }

      this.spawnHobbitCouple(world, 0, 1, 0, 16);
      this.spawnItemFrame(world, -4, 2, 0, 1, this.getRandomHobbitDecoration(world, random));
      return true;
   }
}
