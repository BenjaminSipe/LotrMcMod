package lotr.common.fellowship;

import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

public class LOTRFellowshipData {
   private static Map fellowshipMap = new HashMap();
   public static boolean needsLoad = true;
   private static boolean doFullClearing = false;

   public static boolean anyDataNeedsSave() {
      Iterator var0 = fellowshipMap.values().iterator();

      LOTRFellowship fs;
      do {
         if (!var0.hasNext()) {
            return false;
         }

         fs = (LOTRFellowship)var0.next();
      } while(!fs.needsSave());

      return true;
   }

   private static File getFellowshipDir() {
      File fsDir = new File(LOTRLevelData.getOrCreateLOTRDir(), "fellowships");
      if (!fsDir.exists()) {
         fsDir.mkdirs();
      }

      return fsDir;
   }

   private static File getFellowshipDat(UUID fsID) {
      return new File(getFellowshipDir(), fsID.toString() + ".dat");
   }

   public static void saveAll() {
      try {
         Iterator var0 = fellowshipMap.values().iterator();

         while(var0.hasNext()) {
            LOTRFellowship fs = (LOTRFellowship)var0.next();
            if (fs.needsSave()) {
               saveFellowship(fs);
            }
         }
      } catch (Exception var2) {
         FMLLog.severe("Error saving LOTR fellowship data", new Object[0]);
         var2.printStackTrace();
      }

   }

   public static void loadAll() {
      try {
         destroyAllFellowshipData();
         needsLoad = false;
         saveAll();
      } catch (Exception var1) {
         FMLLog.severe("Error loading LOTR fellowship data", new Object[0]);
         var1.printStackTrace();
      }

   }

   public static void addFellowship(LOTRFellowship fs) {
      if (!fellowshipMap.containsKey(fs.getFellowshipID())) {
         fellowshipMap.put(fs.getFellowshipID(), fs);
      }

   }

   public static LOTRFellowship getFellowship(UUID fsID) {
      LOTRFellowship fs = (LOTRFellowship)fellowshipMap.get(fsID);
      if (fs == null) {
         fs = loadFellowship(fsID);
         if (fs != null) {
            fellowshipMap.put(fsID, fs);
         }
      }

      return fs;
   }

   public static LOTRFellowship getActiveFellowship(UUID fsID) {
      LOTRFellowship fs = getFellowship(fsID);
      return fs != null && fs.isDisbanded() ? null : fs;
   }

   private static LOTRFellowship loadFellowship(UUID fsID) {
      File fsDat = getFellowshipDat(fsID);

      try {
         NBTTagCompound nbt = LOTRLevelData.loadNBTFromFile(fsDat);
         if (nbt.func_82582_d()) {
            return null;
         } else {
            LOTRFellowship fs = new LOTRFellowship(fsID);
            fs.load(nbt);
            return fs;
         }
      } catch (Exception var4) {
         FMLLog.severe("Error loading LOTR fellowship data for %s", new Object[]{fsDat.getName()});
         var4.printStackTrace();
         return null;
      }
   }

   public static void saveFellowship(LOTRFellowship fs) {
      try {
         NBTTagCompound nbt = new NBTTagCompound();
         fs.save(nbt);
         LOTRLevelData.saveNBTToFile(getFellowshipDat(fs.getFellowshipID()), nbt);
      } catch (Exception var2) {
         FMLLog.severe("Error saving LOTR fellowship data for %s", new Object[]{fs.getFellowshipID()});
         var2.printStackTrace();
      }

   }

   private static void saveAndClearFellowship(LOTRFellowship fs) {
      if (fellowshipMap.containsValue(fs)) {
         saveFellowship(fs);
         fellowshipMap.remove(fs.getFellowshipID());
      } else {
         FMLLog.severe("Attempted to clear LOTR fellowship data for %s; no data found", new Object[]{fs.getFellowshipID()});
      }

   }

   public static void saveAndClearUnusedFellowships() {
      if (doFullClearing) {
         List clearing = new ArrayList();
         Iterator var1 = fellowshipMap.values().iterator();

         LOTRFellowship fs;
         while(var1.hasNext()) {
            fs = (LOTRFellowship)var1.next();
            boolean foundMember = false;
            Iterator var4 = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b.iterator();

            while(var4.hasNext()) {
               Object player = var4.next();
               EntityPlayer entityplayer = (EntityPlayer)player;
               if (fs.containsPlayer(entityplayer.func_110124_au())) {
                  foundMember = true;
                  break;
               }
            }

            if (!foundMember) {
               clearing.add(fs);
            }
         }

         var1 = clearing.iterator();

         while(var1.hasNext()) {
            fs = (LOTRFellowship)var1.next();
            saveAndClearFellowship(fs);
         }
      }

   }

   public static void destroyAllFellowshipData() {
      fellowshipMap.clear();
   }
}
