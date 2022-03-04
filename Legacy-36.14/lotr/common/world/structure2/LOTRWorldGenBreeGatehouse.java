package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityBreeGuard;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenBreeGatehouse extends LOTRWorldGenBreeStructure {
   private String villageName = "Village";

   public LOTRWorldGenBreeGatehouse(boolean flag) {
      super(flag);
   }

   public LOTRWorldGenBreeGatehouse setName(String name) {
      this.villageName = name;
      return this;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 2);
      this.setupRandomBlocks(random);
      int k1;
      int step;
      int stepsDown;
      if (this.restrictions) {
         for(k1 = -10; k1 <= 5; ++k1) {
            for(step = -5; step <= 10; ++step) {
               stepsDown = this.getTopBlock(world, k1, step) - 1;
               if (!this.isSurface(world, k1, stepsDown, step)) {
                  return false;
               }
            }
         }
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         for(step = -1; step <= 1; ++step) {
            for(stepsDown = 1; stepsDown <= 8; ++stepsDown) {
               this.setAir(world, k1, stepsDown, step);
            }
         }
      }

      for(k1 = -9; k1 <= -4; ++k1) {
         for(step = 4; step <= 9; ++step) {
            for(stepsDown = 1; stepsDown <= 8; ++stepsDown) {
               this.setAir(world, k1, stepsDown, step);
            }
         }
      }

      for(k1 = 6; k1 <= 7; ++k1) {
         int i1 = -3;

         for(stepsDown = 1; stepsDown <= 7; ++stepsDown) {
            this.setAir(world, i1, stepsDown, k1);
         }
      }

      this.loadStrScan("bree_gatehouse");
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("COBBLE", Blocks.field_150347_e, 0);
      this.associateBlockAlias("COBBLE_STAIR", Blocks.field_150446_ar);
      this.associateBlockMetaAlias("COBBLE_WALL", Blocks.field_150463_bK, 0);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("DOOR", this.doorBlock);
      this.associateBlockAlias("TRAPDOOR", this.trapdoorBlock);
      this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
      this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 4);
      this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 8);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
      this.addBlockMetaAliasOption("THATCH_FLOOR", 1, LOTRMod.thatchFloor, 0);
      this.setBlockAliasChance("THATCH_FLOOR", 0.4F);
      this.addBlockMetaAliasOption("PATH", 5, LOTRMod.dirtPath, 0);
      this.addBlockMetaAliasOption("PATH", 3, Blocks.field_150347_e, 0);
      this.addBlockMetaAliasOption("PATH", 2, Blocks.field_150351_n, 0);
      this.generateStrScan(world, random, 0, 0, 0);
      int maxSteps = 12;

      for(step = 0; step < maxSteps; ++step) {
         stepsDown = Math.max(0, step - 2);
         int i1 = -3;
         int j1 = 0 - stepsDown;
         int k1 = 6 + step;
         if (this.isOpaque(world, i1, j1, k1)) {
            break;
         }

         if (step < 2) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150347_e, 0);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150446_ar, 3);
         }

         this.setGrassToDirt(world, i1, j1 - 1, k1);

         for(int j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
            this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150347_e, 0);
            this.setGrassToDirt(world, i1, j2 - 1, k1);
         }
      }

      this.placeChest(world, random, -5, 2, 8, 5, LOTRChestContents.BREE_HOUSE);
      this.placeMug(world, random, -7, 3, 5, 2, LOTRFoods.BREE_DRINK);
      this.placePlateWithCertainty(world, random, -8, 3, 5, LOTRMod.plateBlock, LOTRFoods.BREE);
      this.setBlockAndMetadata(world, -7, 2, 8, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -8, 2, 8, this.bedBlock, 11);
      this.spawnItemFrame(world, -7, 4, 4, 0, new ItemStack(Items.field_151113_aN));
      LOTREntityBreeGuard guard = new LOTREntityBreeGuard(world);
      this.spawnNPCAndSetHome(guard, world, -7, 2, 6, 8);
      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClass(LOTREntityBreeGuard.class);
      respawner.setCheckRanges(20, -12, 12, 1);
      respawner.setSpawnRanges(4, -2, 2, 8);
      this.placeNPCRespawner(respawner, world, -7, 2, 6);
      this.placeSign(world, -4, 3, -5, Blocks.field_150444_as, 2, new String[]{"", "Welcome to", this.villageName, ""});
      this.placeWallBanner(world, -4, 6, -1, LOTRItemBanner.BannerType.BREE, 2);
      this.placeWallBanner(world, 4, 6, -1, LOTRItemBanner.BannerType.BREE, 2);
      this.placeWallBanner(world, 4, 6, 1, LOTRItemBanner.BannerType.BREE, 0);
      this.placeWallBanner(world, -4, 6, 6, LOTRItemBanner.BannerType.BREE, 1);
      return true;
   }
}
