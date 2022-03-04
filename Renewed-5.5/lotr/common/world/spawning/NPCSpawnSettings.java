package lotr.common.world.spawning;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import net.minecraft.util.ResourceLocation;

public class NPCSpawnSettings {
   private final List spawnLists;
   private final Map spawnListsByName;
   private Map biomeSpawnLists;

   public NPCSpawnSettings(List spawnLists) {
      this.spawnLists = spawnLists;
      this.spawnListsByName = (Map)spawnLists.stream().collect(Collectors.toMap(NPCSpawnList::getName, UnaryOperator.identity()));
   }

   public List getSpawnLists() {
      return this.spawnLists;
   }

   public NPCSpawnList getSpawnListByName(ResourceLocation name) {
      return (NPCSpawnList)this.spawnListsByName.get(name);
   }

   public Map getBiomeSpawnLists() {
      return this.biomeSpawnLists;
   }

   public void setBiomeSpawnLists(Map spawns) {
      if (this.biomeSpawnLists != null) {
         throw new IllegalArgumentException("Cannot set biomeSpawnLists - already set!");
      } else {
         this.biomeSpawnLists = spawns;
      }
   }

   public BiomeNPCSpawnList getSpawnsForBiomeOrFallbackEmpty(ResourceLocation biomeName) {
      return (BiomeNPCSpawnList)this.biomeSpawnLists.computeIfAbsent(biomeName, BiomeNPCSpawnList::createDefaultEmptyList);
   }
}
