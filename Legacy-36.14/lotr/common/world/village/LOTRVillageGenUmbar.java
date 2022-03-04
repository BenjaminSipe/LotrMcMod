package lotr.common.world.village;

import java.util.Random;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityUmbarArcher;
import lotr.common.entity.npc.LOTREntityUmbarWarrior;
import lotr.common.entity.npc.LOTREntityUmbarian;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.structure2.LOTRWorldGenSouthronTownGate;
import lotr.common.world.structure2.LOTRWorldGenSouthronVillageSign;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.world.structure2.LOTRWorldGenUmbarBarracks;
import lotr.common.world.structure2.LOTRWorldGenUmbarBazaar;
import lotr.common.world.structure2.LOTRWorldGenUmbarFarm;
import lotr.common.world.structure2.LOTRWorldGenUmbarFortCorner;
import lotr.common.world.structure2.LOTRWorldGenUmbarFortGate;
import lotr.common.world.structure2.LOTRWorldGenUmbarFortWall;
import lotr.common.world.structure2.LOTRWorldGenUmbarFortress;
import lotr.common.world.structure2.LOTRWorldGenUmbarHouse;
import lotr.common.world.structure2.LOTRWorldGenUmbarLamp;
import lotr.common.world.structure2.LOTRWorldGenUmbarMansion;
import lotr.common.world.structure2.LOTRWorldGenUmbarPasture;
import lotr.common.world.structure2.LOTRWorldGenUmbarSmithy;
import lotr.common.world.structure2.LOTRWorldGenUmbarStables;
import lotr.common.world.structure2.LOTRWorldGenUmbarStatue;
import lotr.common.world.structure2.LOTRWorldGenUmbarTavern;
import lotr.common.world.structure2.LOTRWorldGenUmbarTower;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownCorner;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownFlowers;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownGate;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownTree;
import lotr.common.world.structure2.LOTRWorldGenUmbarTownWall;
import lotr.common.world.structure2.LOTRWorldGenUmbarTraining;
import lotr.common.world.structure2.LOTRWorldGenUmbarVillageSign;
import lotr.common.world.structure2.LOTRWorldGenUmbarWell;
import net.minecraft.world.World;

public class LOTRVillageGenUmbar extends LOTRVillageGenSouthron {
   public LOTRVillageGenUmbar(LOTRBiome biome, float f) {
      super(biome, f);
   }

   protected LOTRVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
      return new LOTRVillageGenUmbar.InstanceUmbar(this, world, i, k, random, loc);
   }

   public static class InstanceUmbar extends LOTRVillageGenSouthron.Instance {
      public InstanceUmbar(LOTRVillageGenUmbar village, World world, int i, int k, Random random, LocationInfo loc) {
         super(village, world, i, k, random, loc);
      }

      protected void setCivilianSpawnClass(LOTREntityNPCRespawner spawner) {
         spawner.setSpawnClass(LOTREntityUmbarian.class);
      }

      protected void setWarriorSpawnClasses(LOTREntityNPCRespawner spawner) {
         spawner.setSpawnClasses(LOTREntityUmbarWarrior.class, LOTREntityUmbarArcher.class);
      }

      protected LOTRWorldGenStructureBase2 getWell(Random random) {
         return new LOTRWorldGenUmbarWell(false);
      }

      protected LOTRWorldGenSouthronVillageSign getSignpost(Random random) {
         return new LOTRWorldGenUmbarVillageSign(false);
      }

      protected LOTRWorldGenStructureBase2 getMansion(Random random) {
         return new LOTRWorldGenUmbarMansion(false);
      }

      protected LOTRWorldGenStructureBase2 getTavern(Random random) {
         return new LOTRWorldGenUmbarTavern(false);
      }

      protected LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
         if (random.nextInt(6) == 0) {
            return new LOTRWorldGenUmbarSmithy(false);
         } else {
            return (LOTRWorldGenStructureBase2)(random.nextInt(6) == 0 ? new LOTRWorldGenUmbarStables(false) : new LOTRWorldGenUmbarHouse(false));
         }
      }

      protected LOTRWorldGenStructureBase2 getRandomFarm(Random random) {
         return (LOTRWorldGenStructureBase2)(random.nextBoolean() ? new LOTRWorldGenUmbarFarm(false) : new LOTRWorldGenUmbarPasture(false));
      }

      protected LOTRWorldGenStructureBase2 getHouse(Random random) {
         return new LOTRWorldGenUmbarHouse(false);
      }

      protected LOTRWorldGenStructureBase2 getSmithy(Random random) {
         return new LOTRWorldGenUmbarSmithy(false);
      }

      protected LOTRWorldGenStructureBase2 getBazaar(Random random) {
         return new LOTRWorldGenUmbarBazaar(false);
      }

      protected LOTRWorldGenStructureBase2 getLamp(Random random) {
         return new LOTRWorldGenUmbarLamp(false);
      }

      protected LOTRWorldGenStructureBase2 getFlowers(Random random) {
         return new LOTRWorldGenUmbarTownFlowers(false);
      }

      protected LOTRWorldGenStructureBase2 getTree(Random random) {
         return new LOTRWorldGenUmbarTownTree(false);
      }

      protected LOTRWorldGenStructureBase2 getStatue(Random random) {
         return new LOTRWorldGenUmbarStatue(false);
      }

      protected LOTRWorldGenSouthronTownGate getTownGate(Random random) {
         return new LOTRWorldGenUmbarTownGate(false);
      }

      protected LOTRWorldGenStructureBase2 getTownWallShort(Random random) {
         return new LOTRWorldGenUmbarTownWall.Short(false);
      }

      protected LOTRWorldGenStructureBase2 getTownWallLong(Random random) {
         return new LOTRWorldGenUmbarTownWall.Long(false);
      }

      protected LOTRWorldGenStructureBase2 getTownWallSideMid(Random random) {
         return new LOTRWorldGenUmbarTownWall.SideMid(false);
      }

      protected LOTRWorldGenStructureBase2 getTownWallExtra(Random random) {
         return new LOTRWorldGenUmbarTownWall.Extra(false);
      }

      protected LOTRWorldGenStructureBase2 getTownWallCorner(Random random) {
         return new LOTRWorldGenUmbarTownCorner(false);
      }

      protected void placeChampionRespawner() {
      }

      protected LOTRWorldGenStructureBase2 getFortress(Random random) {
         return new LOTRWorldGenUmbarFortress(false);
      }

      protected LOTRWorldGenStructureBase2 getTower(Random random) {
         return new LOTRWorldGenUmbarTower(false);
      }

      protected LOTRWorldGenStructureBase2 getStables(Random random) {
         return new LOTRWorldGenUmbarStables(false);
      }

      protected LOTRWorldGenStructureBase2 getBarracks(Random random) {
         return new LOTRWorldGenUmbarBarracks(false);
      }

      protected LOTRWorldGenStructureBase2 getTraining(Random random) {
         return new LOTRWorldGenUmbarTraining(false);
      }

      protected LOTRWorldGenStructureBase2 getFortGate(Random random) {
         return new LOTRWorldGenUmbarFortGate(false);
      }

      protected LOTRWorldGenStructureBase2 getFortWallShort(Random random) {
         return new LOTRWorldGenUmbarFortWall.Short(false);
      }

      protected LOTRWorldGenStructureBase2 getFortWallLong(Random random) {
         return new LOTRWorldGenUmbarFortWall.Long(false);
      }

      protected LOTRWorldGenStructureBase2 getFortCorner(Random random) {
         return new LOTRWorldGenUmbarFortCorner(false);
      }
   }
}
