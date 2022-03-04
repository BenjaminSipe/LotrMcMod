package lotr.common.world.spawning;

import cpw.mods.fml.common.FMLLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lotr.common.fac.LOTRFaction;
import lotr.common.util.LOTRLog;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRConquestGrid;
import lotr.common.world.map.LOTRConquestZone;
import net.minecraft.world.World;

public class LOTRBiomeSpawnList {
   private final String biomeIdentifier;
   private List factionContainers;
   private List presentFactions;
   public float conquestGainRate;

   public LOTRBiomeSpawnList(LOTRBiome biome) {
      this(biome.getClass().getName());
   }

   public LOTRBiomeSpawnList(String s) {
      this.factionContainers = new ArrayList();
      this.presentFactions = new ArrayList();
      this.conquestGainRate = 1.0F;
      this.biomeIdentifier = s;
   }

   public LOTRBiomeSpawnList.FactionContainer newFactionList(int w) {
      return this.newFactionList(w, 1.0F);
   }

   public LOTRBiomeSpawnList.FactionContainer newFactionList(int w, float conq) {
      LOTRBiomeSpawnList.FactionContainer cont = new LOTRBiomeSpawnList.FactionContainer(this, w);
      cont.conquestSensitivity = conq;
      this.factionContainers.add(cont);
      return cont;
   }

   public static LOTRBiomeSpawnList.SpawnListContainer entry(LOTRSpawnList list) {
      return entry(list, 1);
   }

   public static LOTRBiomeSpawnList.SpawnListContainer entry(LOTRSpawnList list, int weight) {
      LOTRBiomeSpawnList.SpawnListContainer container = new LOTRBiomeSpawnList.SpawnListContainer(list, weight);
      return container;
   }

   public void clear() {
      this.factionContainers.clear();
      this.presentFactions.clear();
      this.conquestGainRate = 1.0F;
   }

   private void determineFactions(World world) {
      if (this.presentFactions.isEmpty() && !this.factionContainers.isEmpty()) {
         Iterator var2 = this.factionContainers.iterator();

         while(var2.hasNext()) {
            LOTRBiomeSpawnList.FactionContainer facContainer = (LOTRBiomeSpawnList.FactionContainer)var2.next();
            facContainer.determineFaction(world);
            LOTRFaction fac = facContainer.theFaction;
            if (!this.presentFactions.contains(fac)) {
               this.presentFactions.add(fac);
            }
         }
      }

   }

   public boolean isFactionPresent(World world, LOTRFaction fac) {
      this.determineFactions(world);
      return this.presentFactions.contains(fac);
   }

   public LOTRSpawnEntry.Instance getRandomSpawnEntry(Random rand, World world, int i, int j, int k) {
      this.determineFactions(world);
      LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(i, k);
      int totalWeight = 0;
      Map cachedFacWeights = new HashMap();
      Map cachedConqStrengths = new HashMap();
      Iterator var10 = this.factionContainers.iterator();

      while(var10.hasNext()) {
         LOTRBiomeSpawnList.FactionContainer cont = (LOTRBiomeSpawnList.FactionContainer)var10.next();
         if (!cont.isEmpty()) {
            float conq = cont.getEffectiveConquestStrength(world, zone);
            int weight = cont.getFactionWeight(conq);
            if (weight > 0) {
               totalWeight += weight;
               cachedFacWeights.put(cont, weight);
               cachedConqStrengths.put(cont, conq);
            }
         }
      }

      if (totalWeight > 0) {
         LOTRBiomeSpawnList.FactionContainer chosenFacContainer = null;
         boolean isConquestSpawn = false;
         int w = rand.nextInt(totalWeight);
         Iterator var20 = this.factionContainers.iterator();

         while(var20.hasNext()) {
            LOTRBiomeSpawnList.FactionContainer cont = (LOTRBiomeSpawnList.FactionContainer)var20.next();
            if (!cont.isEmpty() && cachedFacWeights.containsKey(cont)) {
               int facWeight = (Integer)cachedFacWeights.get(cont);
               w -= facWeight;
               if (w < 0) {
                  chosenFacContainer = cont;
                  if (facWeight > cont.baseWeight) {
                     isConquestSpawn = rand.nextFloat() < (float)(facWeight - cont.baseWeight) / (float)facWeight;
                  }
                  break;
               }
            }
         }

         if (chosenFacContainer != null) {
            float conq = (Float)cachedConqStrengths.get(chosenFacContainer);
            LOTRBiomeSpawnList.SpawnListContainer spawnList = chosenFacContainer.getRandomSpawnList(rand, conq);
            if (spawnList == null || spawnList.spawnList == null) {
               System.out.println("WARNING NPE in " + this.biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName());
               FMLLog.severe("WARNING NPE in " + this.biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName(), new Object[0]);
               LOTRLog.logger.warn("WARNING NPE in " + this.biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName());
            }

            LOTRSpawnEntry entry = spawnList.spawnList.getRandomSpawnEntry(rand);
            int chance = spawnList.spawnChance;
            return new LOTRSpawnEntry.Instance(entry, chance, isConquestSpawn);
         }
      }

      return null;
   }

   public List getAllSpawnEntries(World world) {
      this.determineFactions(world);
      List spawns = new ArrayList();
      Iterator var3 = this.factionContainers.iterator();

      while(true) {
         LOTRBiomeSpawnList.FactionContainer facCont;
         do {
            if (!var3.hasNext()) {
               return spawns;
            }

            facCont = (LOTRBiomeSpawnList.FactionContainer)var3.next();
         } while(facCont.isEmpty());

         Iterator var5 = facCont.spawnLists.iterator();

         while(var5.hasNext()) {
            LOTRBiomeSpawnList.SpawnListContainer listCont = (LOTRBiomeSpawnList.SpawnListContainer)var5.next();
            LOTRSpawnList list = listCont.spawnList;
            spawns.addAll(list.getReadOnlyList());
         }
      }
   }

   public boolean containsEntityClassByDefault(Class desiredClass, World world) {
      this.determineFactions(world);
      Iterator var3 = this.factionContainers.iterator();

      while(true) {
         LOTRBiomeSpawnList.FactionContainer facCont;
         do {
            do {
               if (!var3.hasNext()) {
                  return false;
               }

               facCont = (LOTRBiomeSpawnList.FactionContainer)var3.next();
            } while(facCont.isEmpty());
         } while(facCont.isConquestFaction());

         Iterator var5 = facCont.spawnLists.iterator();

         while(var5.hasNext()) {
            LOTRBiomeSpawnList.SpawnListContainer listCont = (LOTRBiomeSpawnList.SpawnListContainer)var5.next();
            LOTRSpawnList list = listCont.spawnList;
            Iterator var8 = list.getReadOnlyList().iterator();

            while(var8.hasNext()) {
               LOTRSpawnEntry e = (LOTRSpawnEntry)var8.next();
               Class spawnClass = e.field_76300_b;
               if (desiredClass.isAssignableFrom(spawnClass)) {
                  return true;
               }
            }
         }
      }
   }

   public static class SpawnListContainer {
      private final LOTRSpawnList spawnList;
      private final int weight;
      private int spawnChance = 0;
      private float conquestThreshold = -1.0F;

      public SpawnListContainer(LOTRSpawnList list, int w) {
         this.spawnList = list;
         this.weight = w;
      }

      public LOTRBiomeSpawnList.SpawnListContainer setSpawnChance(int i) {
         this.spawnChance = i;
         return this;
      }

      public LOTRBiomeSpawnList.SpawnListContainer setConquestOnly() {
         return this.setConquestThreshold(0.0F);
      }

      public LOTRBiomeSpawnList.SpawnListContainer setConquestThreshold(float f) {
         this.conquestThreshold = f;
         return this;
      }

      public boolean canSpawnAtConquestLevel(float conq) {
         return conq > this.conquestThreshold;
      }

      public boolean isConquestOnly() {
         return this.conquestThreshold >= 0.0F;
      }
   }

   public static class FactionContainer {
      private final LOTRBiomeSpawnList parent;
      private LOTRFaction theFaction;
      private final List spawnLists = new ArrayList();
      private final int baseWeight;
      private float conquestSensitivity = 1.0F;

      public FactionContainer(LOTRBiomeSpawnList biomeList, int w) {
         this.parent = biomeList;
         this.baseWeight = w;
      }

      public void add(LOTRBiomeSpawnList.SpawnListContainer... lists) {
         LOTRBiomeSpawnList.SpawnListContainer[] var2 = lists;
         int var3 = lists.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            LOTRBiomeSpawnList.SpawnListContainer cont = var2[var4];
            this.spawnLists.add(cont);
         }

      }

      public boolean isEmpty() {
         return this.spawnLists.isEmpty();
      }

      public boolean isConquestFaction() {
         return this.baseWeight <= 0;
      }

      public void determineFaction(World world) {
         if (this.theFaction == null) {
            Iterator var2 = this.spawnLists.iterator();

            while(var2.hasNext()) {
               LOTRBiomeSpawnList.SpawnListContainer cont = (LOTRBiomeSpawnList.SpawnListContainer)var2.next();
               LOTRSpawnList list = cont.spawnList;
               LOTRFaction fac = list.getListCommonFaction(world);
               if (this.theFaction == null) {
                  this.theFaction = fac;
               } else if (fac != this.theFaction) {
                  throw new IllegalArgumentException("Faction containers must include spawn lists of only one faction! Mismatched faction " + fac.codeName() + " in biome " + this.parent.biomeIdentifier);
               }
            }
         }

      }

      public float getEffectiveConquestStrength(World world, LOTRConquestZone zone) {
         if (LOTRConquestGrid.conquestEnabled(world) && !zone.isEmpty()) {
            float conqStr = zone.getConquestStrength(this.theFaction, world);
            Iterator var4 = this.theFaction.getConquestBoostRelations().iterator();

            while(var4.hasNext()) {
               LOTRFaction allyFac = (LOTRFaction)var4.next();
               if (!this.parent.isFactionPresent(world, allyFac)) {
                  conqStr += zone.getConquestStrength(allyFac, world) * 0.333F;
               }
            }

            return conqStr;
         } else {
            return 0.0F;
         }
      }

      public int getFactionWeight(float conq) {
         if (conq > 0.0F) {
            float conqFactor = conq * 0.2F * this.conquestSensitivity;
            return this.baseWeight + Math.round(conqFactor);
         } else {
            return this.baseWeight;
         }
      }

      public LOTRBiomeSpawnList.SpawnListContainer getRandomSpawnList(Random rand, float conq) {
         int totalWeight = 0;
         Iterator var4 = this.spawnLists.iterator();

         while(var4.hasNext()) {
            LOTRBiomeSpawnList.SpawnListContainer cont = (LOTRBiomeSpawnList.SpawnListContainer)var4.next();
            if (cont.canSpawnAtConquestLevel(conq)) {
               totalWeight += cont.weight;
            }
         }

         if (totalWeight <= 0) {
            return null;
         } else {
            LOTRBiomeSpawnList.SpawnListContainer chosenList = null;
            int w = rand.nextInt(totalWeight);
            Iterator var6 = this.spawnLists.iterator();

            while(var6.hasNext()) {
               LOTRBiomeSpawnList.SpawnListContainer cont = (LOTRBiomeSpawnList.SpawnListContainer)var6.next();
               if (cont.canSpawnAtConquestLevel(conq)) {
                  w -= cont.weight;
                  if (w < 0) {
                     chosenList = cont;
                     break;
                  }
               }
            }

            return chosenList;
         }
      }
   }
}
