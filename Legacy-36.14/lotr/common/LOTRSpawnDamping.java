package lotr.common;

import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

public class LOTRSpawnDamping {
   public static Map spawnDamping = new HashMap();
   public static String TYPE_NPC = "lotr_npc";
   public static boolean needsSave = true;

   public static int getCreatureSpawnCap(EnumCreatureType type, World world) {
      return getSpawnCap(type.name(), type.func_75601_b(), world);
   }

   public static int getNPCSpawnCap(World world) {
      return getSpawnCap(TYPE_NPC, LOTRDimension.getCurrentDimensionWithFallback(world).spawnCap, world);
   }

   public static int getSpawnCap(String type, int baseCap, World world) {
      int players = world.field_73010_i.size();
      return getSpawnCap(type, baseCap, players);
   }

   public static int getSpawnCap(String type, int baseCap, int players) {
      float damp = getSpawnDamping(type);
      float dampFraction = (float)(players - 1) * damp;
      dampFraction = MathHelper.func_76131_a(dampFraction, 0.0F, 1.0F);
      float stationaryPointValue = 0.5F + damp / 2.0F;
      if (dampFraction > stationaryPointValue) {
         dampFraction = stationaryPointValue;
      }

      int capPerPlayer = Math.round((float)baseCap * (1.0F - dampFraction));
      capPerPlayer = Math.max(capPerPlayer, 1);
      return capPerPlayer;
   }

   public static float getSpawnDamping(String type) {
      float f = 0.0F;
      if (spawnDamping.containsKey(type)) {
         f = (Float)spawnDamping.get(type);
      }

      return f;
   }

   public static void setSpawnDamping(EnumCreatureType type, float damping) {
      setSpawnDamping(type.name(), damping);
   }

   public static void setNPCSpawnDamping(float damping) {
      setSpawnDamping(TYPE_NPC, damping);
   }

   public static void setSpawnDamping(String type, float damping) {
      spawnDamping.put(type, damping);
      markDirty();
   }

   public static int getBaseSpawnCapForInfo(String type, World world) {
      if (type.equals(TYPE_NPC)) {
         return LOTRDimension.getCurrentDimensionWithFallback(world).spawnCap;
      } else {
         EnumCreatureType creatureType = EnumCreatureType.valueOf(type);
         return creatureType != null ? creatureType.func_75601_b() : -1;
      }
   }

   private static void markDirty() {
      needsSave = true;
   }

   private static File getDataFile() {
      return new File(LOTRLevelData.getOrCreateLOTRDir(), "spawn_damping.dat");
   }

   public static void saveAll() {
      try {
         File datFile = getDataFile();
         if (!datFile.exists()) {
            CompressedStreamTools.func_74799_a(new NBTTagCompound(), new FileOutputStream(datFile));
         }

         NBTTagCompound spawnData = new NBTTagCompound();
         NBTTagList typeTags = new NBTTagList();
         Iterator var3 = spawnDamping.entrySet().iterator();

         while(var3.hasNext()) {
            Entry e = (Entry)var3.next();
            String type = (String)e.getKey();
            float damping = (Float)e.getValue();
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.func_74778_a("Type", type);
            nbt.func_74776_a("Damp", damping);
            typeTags.func_74742_a(nbt);
         }

         spawnData.func_74782_a("Damping", typeTags);
         LOTRLevelData.saveNBTToFile(datFile, spawnData);
         needsSave = false;
      } catch (Exception var8) {
         FMLLog.severe("Error saving LOTR spawn damping", new Object[0]);
         var8.printStackTrace();
      }

   }

   public static void loadAll() {
      try {
         File datFile = getDataFile();
         NBTTagCompound spawnData = LOTRLevelData.loadNBTFromFile(datFile);
         spawnDamping.clear();
         if (spawnData.func_74764_b("Damping")) {
            NBTTagList typeTags = spawnData.func_150295_c("Damping", 10);

            for(int i = 0; i < typeTags.func_74745_c(); ++i) {
               NBTTagCompound nbt = typeTags.func_150305_b(i);
               String type = nbt.func_74779_i("Type");
               float damping = nbt.func_74760_g("Damp");
               if (!StringUtils.isBlank(type)) {
                  spawnDamping.put(type, damping);
               }
            }
         }

         needsSave = true;
         saveAll();
      } catch (Exception var7) {
         FMLLog.severe("Error loading LOTR spawn damping", new Object[0]);
         var7.printStackTrace();
      }

   }

   public static void resetAll() {
      spawnDamping.clear();
      markDirty();
   }
}
