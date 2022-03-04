package lotr.common.world.spawning;

import cpw.mods.fml.common.FMLLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lotr.common.world.biome.LOTRBiome;

public class LOTRBiomeInvasionSpawns {
   private LOTRBiome theBiome;
   private Map invasionsByChance = new HashMap();
   private List registeredInvasions = new ArrayList();

   public LOTRBiomeInvasionSpawns(LOTRBiome biome) {
      this.theBiome = biome;
   }

   public void addInvasion(LOTRInvasions invasion, LOTREventSpawner.EventChance chance) {
      List chanceList = this.getInvasionsForChance(chance);
      if (!chanceList.contains(invasion) && !this.registeredInvasions.contains(invasion)) {
         chanceList.add(invasion);
         this.registeredInvasions.add(invasion);
      } else {
         FMLLog.warning("LOTR biome %s already has invasion %s registered", new Object[]{this.theBiome.field_76791_y, invasion.codeName()});
      }

   }

   public void clearInvasions() {
      this.invasionsByChance.clear();
      this.registeredInvasions.clear();
   }

   public List getInvasionsForChance(LOTREventSpawner.EventChance chance) {
      List chanceList = (List)this.invasionsByChance.get(chance);
      if (chanceList == null) {
         chanceList = new ArrayList();
      }

      this.invasionsByChance.put(chance, chanceList);
      return (List)chanceList;
   }
}
