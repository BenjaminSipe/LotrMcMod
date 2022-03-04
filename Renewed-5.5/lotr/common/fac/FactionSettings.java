package lotr.common.fac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotr.common.LOTRLog;
import lotr.common.init.LOTRDimensions;
import lotr.common.world.map.MapSettings;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FactionSettings {
   private final List allRegions;
   private final Map regionsByDimension;
   private final Map regionsByName;
   private final Map regionsById;
   private List allFactions;
   private Map factionsByRegion;
   private Map factionsByName;
   private Map factionsById;
   private FactionRelationsTable relations;

   public FactionSettings(List regions) {
      this.allRegions = sortRegionsByOrder(regions);
      this.regionsByDimension = groupRegionsByDimensionAndSortOrder(this.allRegions);
      this.regionsByName = (Map)this.allRegions.stream().collect(Collectors.toMap(FactionRegion::getName, UnaryOperator.identity()));
      this.regionsById = (Map)this.allRegions.stream().collect(Collectors.toMap(FactionRegion::getAssignedId, UnaryOperator.identity()));
   }

   private static List sortRegionsByOrder(List regions) {
      Collections.sort(regions, Comparator.comparingInt(FactionRegion::getOrdering));
      return regions;
   }

   private static Map groupRegionsByDimensionAndSortOrder(List regions) {
      Map map = (Map)regions.stream().collect(Collectors.groupingBy(FactionRegion::getDimensionName));
      map.values().forEach(FactionSettings::sortRegionsByOrder);
      return map;
   }

   private static List sortFactionsByOrder(List factions) {
      Collections.sort(factions, Comparator.comparingInt(Faction::getNullableRegionOrdering).thenComparingInt(Faction::getOrdering));
      return factions;
   }

   private static Map groupFactionsByRegionAndSortOrder(List factions) {
      Map unsortedMap = (Map)factions.stream().filter((fac) -> {
         return fac.getRegion() != null;
      }).collect(Collectors.groupingBy(Faction::getRegion));
      return (Map)unsortedMap.keySet().stream().collect(Collectors.toMap(UnaryOperator.identity(), (region) -> {
         return sortFactionsByOrder((List)unsortedMap.get(region));
      }));
   }

   public static FactionSettings read(MapSettings mapSettings, PacketBuffer buf) {
      List regions = new ArrayList();
      int numRegions = buf.func_150792_a();

      for(int i = 0; i < numRegions; ++i) {
         try {
            FactionRegion region = FactionRegion.read(buf);
            if (region != null) {
               regions.add(region);
            }
         } catch (Exception var10) {
            LOTRLog.warn("Error loading a faction region from server");
            var10.printStackTrace();
         }
      }

      FactionSettings facSettings = new FactionSettings(regions);
      List factions = new ArrayList();
      int numFactions = buf.func_150792_a();

      for(int i = 0; i < numFactions; ++i) {
         try {
            Faction faction = Faction.read(facSettings, mapSettings, buf);
            if (faction != null) {
               factions.add(faction);
            }
         } catch (Exception var9) {
            LOTRLog.warn("Error loading a faction from server");
            var9.printStackTrace();
         }
      }

      facSettings.setFactions(factions);
      FactionRelationsTable relations = FactionRelationsTable.read(facSettings, buf);
      facSettings.setRelations(relations);
      return facSettings;
   }

   public void write(PacketBuffer buf) {
      buf.func_150787_b(this.allRegions.size());
      this.allRegions.forEach((region) -> {
         region.write(buf);
      });
      buf.func_150787_b(this.allFactions.size());
      this.allFactions.forEach((faction) -> {
         faction.write(buf);
      });
      this.relations.write(buf);
   }

   public List getRegions() {
      return this.allRegions;
   }

   public List getRegionsForDimension(RegistryKey dim) {
      return (List)this.regionsByDimension.get(dim.func_240901_a_());
   }

   public List getRegionsForDimensionOrDefault(RegistryKey dim) {
      if (!this.regionsByDimension.containsKey(dim.func_240901_a_())) {
         dim = LOTRDimensions.MIDDLE_EARTH_WORLD_KEY;
      }

      return this.getRegionsForDimension(dim);
   }

   public FactionRegion getRegionByName(ResourceLocation name) {
      return (FactionRegion)this.regionsByName.get(name);
   }

   public FactionRegion getRegionByID(int id) {
      return (FactionRegion)this.regionsById.get(id);
   }

   public List getFactions() {
      return this.allFactions;
   }

   public void setFactions(List facs) {
      if (this.allFactions != null) {
         throw new IllegalArgumentException("Cannot set faction list - already set!");
      } else {
         this.allFactions = sortFactionsByOrder(facs);
         this.factionsByRegion = groupFactionsByRegionAndSortOrder(this.allFactions);
         this.factionsById = (Map)this.allFactions.stream().collect(Collectors.toMap(Faction::getAssignedId, UnaryOperator.identity()));
         this.factionsByName = (Map)this.allFactions.stream().collect(Collectors.toMap(Faction::getName, UnaryOperator.identity()));
      }
   }

   public Stream streamFactions() {
      return this.allFactions.stream();
   }

   public Stream streamFactionsExcept(Faction except) {
      return this.streamFactions().filter(Predicate.isEqual(except).negate());
   }

   public List getAllPlayableAlignmentFactions() {
      return (List)this.streamFactions().filter(Faction::isPlayableAlignmentFaction).collect(Collectors.toList());
   }

   public List getPlayableFactionNames() {
      return (List)this.getAllPlayableAlignmentFactions().stream().map(Faction::getName).collect(Collectors.toList());
   }

   public List getFactionsForRegion(FactionRegion region) {
      return (List)this.factionsByRegion.get(region);
   }

   public List getFactionsOfTypes(FactionType... types) {
      return (List)this.streamFactions().filter((fac) -> {
         return fac.isOfAnyType(types);
      }).collect(Collectors.toList());
   }

   public Faction getFactionByID(int id) {
      return (Faction)this.factionsById.get(id);
   }

   public Faction getFactionByName(ResourceLocation name) {
      return (Faction)this.factionsByName.get(name);
   }

   public Faction getFactionByPointer(FactionPointer pointer) {
      return this.getFactionByName(pointer.getName());
   }

   public FactionRelationsTable getRelations() {
      return this.relations;
   }

   public void setRelations(FactionRelationsTable rels) {
      if (this.relations != null) {
         throw new IllegalArgumentException("Cannot set faction relations - already set!");
      } else {
         this.relations = rels;
      }
   }

   public void postLoadValidateBiomes(World world) {
      this.allFactions.forEach((fac) -> {
         fac.postLoadValidateBiomes(world);
      });
   }
}
