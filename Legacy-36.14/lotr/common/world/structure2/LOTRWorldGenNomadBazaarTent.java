package lotr.common.world.structure2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.npc.LOTREntityNomadArmourer;
import lotr.common.entity.npc.LOTREntityNomadBrewer;
import lotr.common.entity.npc.LOTREntityNomadMason;
import lotr.common.entity.npc.LOTREntityNomadMiner;
import lotr.common.entity.npc.LOTREntityNomadTrader;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenNomadBazaarTent extends LOTRWorldGenNomadStructure {
   private static Class[] stalls = new Class[]{LOTRWorldGenNomadBazaarTent.Mason.class, LOTRWorldGenNomadBazaarTent.Brewer.class, LOTRWorldGenNomadBazaarTent.Miner.class, LOTRWorldGenNomadBazaarTent.Armourer.class};

   public LOTRWorldGenNomadBazaarTent(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 7);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(i1 = -14; i1 <= 14; ++i1) {
            for(k1 = -6; k1 <= 8; ++k1) {
               j1 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, j1, k1)) {
                  return false;
               }

               if (j1 < minHeight) {
                  minHeight = j1;
               }

               if (j1 > maxHeight) {
                  maxHeight = j1;
               }

               if (maxHeight - minHeight > 8) {
                  return false;
               }
            }
         }
      }

      for(minHeight = -14; minHeight <= 14; ++minHeight) {
         for(maxHeight = -6; maxHeight <= 8; ++maxHeight) {
            i1 = Math.abs(minHeight);
            k1 = Math.abs(maxHeight);
            if (!this.isSurface(world, minHeight, 0, maxHeight)) {
               this.laySandBase(world, minHeight, 0, maxHeight);
            }

            for(j1 = 1; j1 <= 8; ++j1) {
               this.setAir(world, minHeight, j1, maxHeight);
            }
         }
      }

      this.loadStrScan("nomad_bazaar");
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
      this.associateBlockMetaAlias("TENT", this.tentBlock, this.tentMeta);
      this.associateBlockMetaAlias("TENT2", this.tent2Block, this.tent2Meta);
      this.associateBlockMetaAlias("CARPET", this.carpetBlock, this.carpetMeta);
      this.associateBlockMetaAlias("CARPET2", this.carpet2Block, this.carpet2Meta);
      this.generateStrScan(world, random, 0, 1, 0);
      this.placeSkull(world, random, -8, 2, -4);
      this.placeBarrel(world, random, 7, 2, -4, 3, LOTRFoods.NOMAD_DRINK);
      this.placeBarrel(world, random, 8, 2, -4, 3, LOTRFoods.NOMAD_DRINK);
      this.placeAnimalJar(world, -7, 2, -4, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
      this.placeAnimalJar(world, 9, 1, 5, LOTRMod.birdCageWood, 0, (EntityLiving)null);
      this.placeAnimalJar(world, 4, 3, 2, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
      this.placeAnimalJar(world, -4, 4, 5, LOTRMod.birdCage, 2, new LOTREntityBird(world));
      this.placeAnimalJar(world, -4, 5, -1, LOTRMod.birdCage, 0, new LOTREntityBird(world));
      this.placeAnimalJar(world, 0, 5, 5, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
      List stallClasses = Arrays.asList(Arrays.copyOf(stalls, stalls.length));
      Collections.shuffle(stallClasses, random);

      try {
         LOTRWorldGenStructureBase2 stall0 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(0)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall1 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(1)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall2 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(2)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         this.generateSubstructure(stall0, world, random, -4, 1, 6, 0);
         this.generateSubstructure(stall1, world, random, 0, 1, 6, 0);
         this.generateSubstructure(stall2, world, random, 4, 1, 6, 0);
      } catch (Exception var12) {
         var12.printStackTrace();
      }

      return true;
   }

   private static class Armourer extends LOTRWorldGenStructureBase2 {
      public Armourer(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, 1, 1, 1, Blocks.field_150467_bQ, 1);
         this.placeArmorStand(world, 0, 1, 1, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetMoredainLion), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsNomad), new ItemStack(LOTRMod.bootsNomad)});
         this.placeWeaponRack(world, -1, 2, -2, 2, (new LOTRWorldGenNomadBazaarTent(false)).getRandomNomadWeapon(random));
         LOTREntityNomadArmourer trader = new LOTREntityNomadArmourer(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Miner extends LOTRWorldGenStructureBase2 {
      public Miner(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.oreCopper, 0);
         this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.oreTin, 0);
         this.setBlockAndMetadata(world, 0, 1, 1, LOTRMod.oreCopper, 0);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.oreTin, 0);
         this.setBlockAndMetadata(world, 1, 2, 1, Blocks.field_150369_x, 0);
         this.setBlockAndMetadata(world, 1, 1, 0, Blocks.field_150369_x, 0);
         this.placeWeaponRack(world, 0, 2, 1, 6, new ItemStack(LOTRMod.pickaxeBronze));
         LOTREntityNomadTrader trader = new LOTREntityNomadMiner(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Brewer extends LOTRWorldGenStructureBase2 {
      public Brewer(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.stairsCedar, 6);
         this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.barrel, 2);
         this.setBlockAndMetadata(world, 0, 1, 1, Blocks.field_150383_bp, 3);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.stairsCedar, 6);
         this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.barrel, 2);
         this.placeMug(world, random, -1, 2, -2, 0, LOTRFoods.NOMAD_DRINK);
         this.placeMug(world, random, 1, 2, -2, 0, LOTRFoods.NOMAD_DRINK);
         LOTREntityNomadTrader trader = new LOTREntityNomadBrewer(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Mason extends LOTRWorldGenStructureBase2 {
      public Mason(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.redSandstone, 0);
         this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.redSandstone, 0);
         this.setBlockAndMetadata(world, -1, 3, 1, LOTRMod.redSandstone, 0);
         this.setBlockAndMetadata(world, -1, 1, 0, Blocks.field_150322_A, 0);
         this.setBlockAndMetadata(world, -1, 2, 0, Blocks.field_150322_A, 0);
         this.setBlockAndMetadata(world, 0, 1, 1, LOTRMod.brick, 15);
         this.setBlockAndMetadata(world, 0, 2, 1, LOTRMod.slabSingle4, 0);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.brick, 15);
         this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.slabSingle4, 0);
         this.placeWeaponRack(world, 1, 3, 1, 6, new ItemStack(LOTRMod.pickaxeBronze));
         LOTREntityNomadTrader trader = new LOTREntityNomadMason(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }
}
