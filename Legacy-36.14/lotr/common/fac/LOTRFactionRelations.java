package lotr.common.fac;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.LOTRLevelData;
import lotr.common.network.LOTRPacketFactionRelations;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;

public class LOTRFactionRelations {
   private static Map defaultMap = new HashMap();
   private static Map overrideMap = new HashMap();
   public static boolean needsLoad = true;
   private static boolean needsSave = false;

   private static LOTRFactionRelations.Relation getFromDefaultMap(LOTRFactionRelations.FactionPair key) {
      return defaultMap.containsKey(key) ? (LOTRFactionRelations.Relation)defaultMap.get(key) : LOTRFactionRelations.Relation.NEUTRAL;
   }

   public static LOTRFactionRelations.Relation getRelations(LOTRFaction f1, LOTRFaction f2) {
      if (f1 != LOTRFaction.UNALIGNED && f2 != LOTRFaction.UNALIGNED) {
         if (f1 != LOTRFaction.HOSTILE && f2 != LOTRFaction.HOSTILE) {
            if (f1 == f2) {
               return LOTRFactionRelations.Relation.ALLY;
            } else {
               LOTRFactionRelations.FactionPair key = new LOTRFactionRelations.FactionPair(f1, f2);
               return overrideMap.containsKey(key) ? (LOTRFactionRelations.Relation)overrideMap.get(key) : getFromDefaultMap(key);
            }
         } else {
            return LOTRFactionRelations.Relation.MORTAL_ENEMY;
         }
      } else {
         return LOTRFactionRelations.Relation.NEUTRAL;
      }
   }

   public static void setDefaultRelations(LOTRFaction f1, LOTRFaction f2, LOTRFactionRelations.Relation relation) {
      setRelations(f1, f2, relation, true);
   }

   public static void overrideRelations(LOTRFaction f1, LOTRFaction f2, LOTRFactionRelations.Relation relation) {
      setRelations(f1, f2, relation, false);
   }

   private static void setRelations(LOTRFaction f1, LOTRFaction f2, LOTRFactionRelations.Relation relation, boolean isDefault) {
      if (f1 != LOTRFaction.UNALIGNED && f2 != LOTRFaction.UNALIGNED) {
         if (f1 != LOTRFaction.HOSTILE && f2 != LOTRFaction.HOSTILE) {
            if (f1 == f2) {
               throw new IllegalArgumentException("Cannot alter a faction's relations with itself!");
            } else {
               LOTRFactionRelations.FactionPair key = new LOTRFactionRelations.FactionPair(f1, f2);
               if (isDefault) {
                  if (relation == LOTRFactionRelations.Relation.NEUTRAL) {
                     defaultMap.remove(key);
                  } else {
                     defaultMap.put(key, relation);
                  }
               } else {
                  LOTRFactionRelations.Relation defaultRelation = getFromDefaultMap(key);
                  if (relation == defaultRelation) {
                     overrideMap.remove(key);
                     markDirty();
                  } else {
                     overrideMap.put(key, relation);
                     markDirty();
                  }

                  LOTRPacketFactionRelations pkt = LOTRPacketFactionRelations.oneEntry(key, relation);
                  sendPacketToAll(pkt);
               }

            }
         } else {
            throw new IllegalArgumentException("Cannot alter HOSTILE!");
         }
      } else {
         throw new IllegalArgumentException("Cannot alter UNALIGNED!");
      }
   }

   public static void resetAllRelations() {
      boolean wasEmpty = overrideMap.isEmpty();
      overrideMap.clear();
      if (!wasEmpty) {
         markDirty();
         LOTRPacketFactionRelations pkt = LOTRPacketFactionRelations.reset();
         sendPacketToAll(pkt);
      }

   }

   public static void markDirty() {
      needsSave = true;
   }

   public static boolean needsSave() {
      return needsSave;
   }

   private static File getRelationsFile() {
      return new File(LOTRLevelData.getOrCreateLOTRDir(), "faction_relations.dat");
   }

   public static void save() {
      try {
         File datFile = getRelationsFile();
         if (!datFile.exists()) {
            LOTRLevelData.saveNBTToFile(datFile, new NBTTagCompound());
         }

         NBTTagCompound facData = new NBTTagCompound();
         NBTTagList relationTags = new NBTTagList();
         Iterator var3 = overrideMap.entrySet().iterator();

         while(var3.hasNext()) {
            Entry e = (Entry)var3.next();
            LOTRFactionRelations.FactionPair pair = (LOTRFactionRelations.FactionPair)e.getKey();
            LOTRFactionRelations.Relation rel = (LOTRFactionRelations.Relation)e.getValue();
            NBTTagCompound nbt = new NBTTagCompound();
            pair.writeToNBT(nbt);
            nbt.func_74778_a("Rel", rel.codeName());
            relationTags.func_74742_a(nbt);
         }

         facData.func_74782_a("Overrides", relationTags);
         LOTRLevelData.saveNBTToFile(datFile, facData);
         needsSave = false;
      } catch (Exception var8) {
         FMLLog.severe("Error saving LOTR faction relations", new Object[0]);
         var8.printStackTrace();
      }

   }

   public static void load() {
      try {
         NBTTagCompound facData = LOTRLevelData.loadNBTFromFile(getRelationsFile());
         overrideMap.clear();
         NBTTagList relationTags = facData.func_150295_c("Overrides", 10);

         for(int i = 0; i < relationTags.func_74745_c(); ++i) {
            NBTTagCompound nbt = relationTags.func_150305_b(i);
            LOTRFactionRelations.FactionPair pair = LOTRFactionRelations.FactionPair.readFromNBT(nbt);
            LOTRFactionRelations.Relation rel = LOTRFactionRelations.Relation.forName(nbt.func_74779_i("Rel"));
            if (pair != null && rel != null) {
               overrideMap.put(pair, rel);
            }
         }

         needsLoad = false;
         save();
      } catch (Exception var6) {
         FMLLog.severe("Error loading LOTR faction relations", new Object[0]);
         var6.printStackTrace();
      }

   }

   public static void sendAllRelationsTo(EntityPlayerMP entityplayer) {
      LOTRPacketFactionRelations pkt = LOTRPacketFactionRelations.fullMap(overrideMap);
      LOTRPacketHandler.networkWrapper.sendTo(pkt, entityplayer);
   }

   private static void sendPacketToAll(IMessage packet) {
      MinecraftServer srv = MinecraftServer.func_71276_C();
      if (srv != null) {
         Iterator var2 = srv.func_71203_ab().field_72404_b.iterator();

         while(var2.hasNext()) {
            Object obj = var2.next();
            EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
            LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
         }
      }

   }

   public static class FactionPair {
      private final LOTRFaction fac1;
      private final LOTRFaction fac2;

      public FactionPair(LOTRFaction f1, LOTRFaction f2) {
         this.fac1 = f1;
         this.fac2 = f2;
      }

      public LOTRFaction getLeft() {
         return this.fac1;
      }

      public LOTRFaction getRight() {
         return this.fac2;
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else {
            if (obj instanceof LOTRFactionRelations.FactionPair) {
               LOTRFactionRelations.FactionPair pair = (LOTRFactionRelations.FactionPair)obj;
               if (this.fac1 == pair.fac1 && this.fac2 == pair.fac2) {
                  return true;
               }

               if (this.fac1 == pair.fac2 && this.fac2 == pair.fac1) {
                  return true;
               }
            }

            return false;
         }
      }

      public int hashCode() {
         int f1 = this.fac1.ordinal();
         int f2 = this.fac2.ordinal();
         int lower = Math.min(f1, f2);
         int upper = Math.max(f1, f2);
         return upper << 16 | lower;
      }

      public void writeToNBT(NBTTagCompound nbt) {
         nbt.func_74778_a("FacPair1", this.fac1.codeName());
         nbt.func_74778_a("FacPair2", this.fac2.codeName());
      }

      public static LOTRFactionRelations.FactionPair readFromNBT(NBTTagCompound nbt) {
         LOTRFaction f1 = LOTRFaction.forName(nbt.func_74779_i("FacPair1"));
         LOTRFaction f2 = LOTRFaction.forName(nbt.func_74779_i("FacPair2"));
         return f1 != null && f2 != null ? new LOTRFactionRelations.FactionPair(f1, f2) : null;
      }
   }

   public static enum Relation {
      ALLY,
      FRIEND,
      NEUTRAL,
      ENEMY,
      MORTAL_ENEMY;

      public String codeName() {
         return this.name();
      }

      public String getDisplayName() {
         return StatCollector.func_74838_a("lotr.faction.rel." + this.codeName());
      }

      public static List listRelationNames() {
         List names = new ArrayList();
         LOTRFactionRelations.Relation[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRFactionRelations.Relation rel = var1[var3];
            names.add(rel.codeName());
         }

         return names;
      }

      public static LOTRFactionRelations.Relation forID(int id) {
         LOTRFactionRelations.Relation[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRFactionRelations.Relation rel = var1[var3];
            if (rel.ordinal() == id) {
               return rel;
            }
         }

         return null;
      }

      public static LOTRFactionRelations.Relation forName(String name) {
         LOTRFactionRelations.Relation[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRFactionRelations.Relation rel = var1[var3];
            if (rel.codeName().equals(name)) {
               return rel;
            }
         }

         return null;
      }
   }
}
