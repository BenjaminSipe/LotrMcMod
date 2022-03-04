package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.world.feature.LOTRWorldGenWebOfUngoliant;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenMordorSpiderPit;
import net.minecraft.world.World;

public class LOTRBiomeGenNanUngol extends LOTRBiomeGenMordor {
   public LOTRBiomeGenNanUngol(int i, boolean major) {
      super(i, major);
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 30);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 2);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_SPIDERS, 100);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.5F;
      this.clearBiomeVariants();
      this.decorator.generateCobwebs = false;
      this.decorator.clearRandomStructures();
      this.decorator.addRandomStructure(new LOTRWorldGenMordorSpiderPit(false), 40);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterNanUngol;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.NAN_UNGOL;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.MORDOR.getSubregion("nanUngol");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      for(int l = 0; l < 4; ++l) {
         int i1 = i + random.nextInt(16) + 8;
         int j1 = random.nextInt(128);
         int k1 = k + random.nextInt(16) + 8;
         (new LOTRWorldGenWebOfUngoliant(false, 64)).func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.75F;
   }
}
