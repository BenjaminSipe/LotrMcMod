package lotr.common.fac;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.UsernameCache;
import org.apache.commons.lang3.StringUtils;

public class LOTRFactionBounties {
   public static Map factionBountyMap = new HashMap();
   public static boolean needsLoad = true;
   public static final int KILL_RECORD_TIME = 3456000;
   public static final int BOUNTY_KILLED_TIME = 864000;
   public final LOTRFaction theFaction;
   private Map playerList = new HashMap();
   private boolean needsSave = false;

   public static LOTRFactionBounties forFaction(LOTRFaction fac) {
      LOTRFactionBounties bounties = (LOTRFactionBounties)factionBountyMap.get(fac);
      if (bounties == null) {
         bounties = loadFaction(fac);
         if (bounties == null) {
            bounties = new LOTRFactionBounties(fac);
         }

         factionBountyMap.put(fac, bounties);
      }

      return bounties;
   }

   public static void updateAll() {
      Iterator var0 = factionBountyMap.values().iterator();

      while(var0.hasNext()) {
         LOTRFactionBounties fb = (LOTRFactionBounties)var0.next();
         fb.update();
      }

   }

   public LOTRFactionBounties(LOTRFaction f) {
      this.theFaction = f;
   }

   public LOTRFactionBounties.PlayerData forPlayer(EntityPlayer entityplayer) {
      return this.forPlayer(entityplayer.func_110124_au());
   }

   public LOTRFactionBounties.PlayerData forPlayer(UUID id) {
      LOTRFactionBounties.PlayerData pd = (LOTRFactionBounties.PlayerData)this.playerList.get(id);
      if (pd == null) {
         pd = new LOTRFactionBounties.PlayerData(this, id);
         this.playerList.put(id, pd);
      }

      return pd;
   }

   public void update() {
      Iterator var1 = this.playerList.values().iterator();

      while(var1.hasNext()) {
         LOTRFactionBounties.PlayerData pd = (LOTRFactionBounties.PlayerData)var1.next();
         pd.update();
      }

   }

   public List findBountyTargets(int killAmount) {
      List players = new ArrayList();
      Iterator var3 = this.playerList.values().iterator();

      while(var3.hasNext()) {
         LOTRFactionBounties.PlayerData pd = (LOTRFactionBounties.PlayerData)var3.next();
         if (!pd.recentlyBountyKilled() && pd.getNumKills() >= killAmount) {
            players.add(pd);
         }
      }

      return players;
   }

   public void markDirty() {
      this.needsSave = true;
   }

   public void writeToNBT(NBTTagCompound nbt) {
      NBTTagList playerTags = new NBTTagList();
      Iterator var3 = this.playerList.entrySet().iterator();

      while(var3.hasNext()) {
         Entry e = (Entry)var3.next();
         UUID id = (UUID)e.getKey();
         LOTRFactionBounties.PlayerData pd = (LOTRFactionBounties.PlayerData)e.getValue();
         if (pd.shouldSave()) {
            NBTTagCompound playerData = new NBTTagCompound();
            playerData.func_74778_a("UUID", id.toString());
            pd.writeToNBT(playerData);
            playerTags.func_74742_a(playerData);
         }
      }

      nbt.func_74782_a("PlayerList", playerTags);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      this.playerList.clear();
      if (nbt.func_74764_b("PlayerList")) {
         NBTTagList playerTags = nbt.func_150295_c("PlayerList", 10);

         for(int i = 0; i < playerTags.func_74745_c(); ++i) {
            NBTTagCompound playerData = playerTags.func_150305_b(i);
            UUID id = UUID.fromString(playerData.func_74779_i("UUID"));
            if (id != null) {
               LOTRFactionBounties.PlayerData pd = new LOTRFactionBounties.PlayerData(this, id);
               pd.readFromNBT(playerData);
               this.playerList.put(id, pd);
            }
         }
      }

   }

   public static boolean anyDataNeedsSave() {
      Iterator var0 = factionBountyMap.values().iterator();

      LOTRFactionBounties fb;
      do {
         if (!var0.hasNext()) {
            return false;
         }

         fb = (LOTRFactionBounties)var0.next();
      } while(!fb.needsSave);

      return true;
   }

   private static File getBountiesDir() {
      File dir = new File(LOTRLevelData.getOrCreateLOTRDir(), "factionbounties");
      if (!dir.exists()) {
         dir.mkdirs();
      }

      return dir;
   }

   private static File getFactionFile(LOTRFaction f, boolean findLegacy) {
      File defaultFile = new File(getBountiesDir(), f.codeName() + ".dat");
      if (!findLegacy) {
         return defaultFile;
      } else if (defaultFile.exists()) {
         return defaultFile;
      } else {
         Iterator var3 = f.listAliases().iterator();

         File aliasFile;
         do {
            if (!var3.hasNext()) {
               return defaultFile;
            }

            String alias = (String)var3.next();
            aliasFile = new File(getBountiesDir(), alias + ".dat");
         } while(!aliasFile.exists());

         return aliasFile;
      }
   }

   public static void saveAll() {
      try {
         Iterator var0 = factionBountyMap.values().iterator();

         while(var0.hasNext()) {
            LOTRFactionBounties fb = (LOTRFactionBounties)var0.next();
            if (fb.needsSave) {
               saveFaction(fb);
               fb.needsSave = false;
            }
         }
      } catch (Exception var2) {
         FMLLog.severe("Error saving LOTR faction bounty data", new Object[0]);
         var2.printStackTrace();
      }

   }

   public static void loadAll() {
      try {
         factionBountyMap.clear();
         needsLoad = false;
         saveAll();
      } catch (Exception var1) {
         FMLLog.severe("Error loading LOTR faction bounty data", new Object[0]);
         var1.printStackTrace();
      }

   }

   private static LOTRFactionBounties loadFaction(LOTRFaction fac) {
      File file = getFactionFile(fac, true);

      try {
         NBTTagCompound nbt = LOTRLevelData.loadNBTFromFile(file);
         if (nbt.func_82582_d()) {
            return null;
         } else {
            LOTRFactionBounties fb = new LOTRFactionBounties(fac);
            fb.readFromNBT(nbt);
            return fb;
         }
      } catch (Exception var4) {
         FMLLog.severe("Error loading LOTR faction bounty data for %s", new Object[]{fac.codeName()});
         var4.printStackTrace();
         return null;
      }
   }

   private static void saveFaction(LOTRFactionBounties fb) {
      try {
         NBTTagCompound nbt = new NBTTagCompound();
         fb.writeToNBT(nbt);
         LOTRLevelData.saveNBTToFile(getFactionFile(fb.theFaction, false), nbt);
      } catch (Exception var2) {
         FMLLog.severe("Error saving LOTR faction bounty data for %s", new Object[]{fb.theFaction.codeName()});
         var2.printStackTrace();
      }

   }

   public static class PlayerData {
      public final LOTRFactionBounties bountyList;
      public final UUID playerID;
      private String username;
      private List killRecords = new ArrayList();
      private int recentBountyKilled;

      public PlayerData(LOTRFactionBounties b, UUID id) {
         this.bountyList = b;
         this.playerID = id;
      }

      public void recordNewKill() {
         this.killRecords.add(new LOTRFactionBounties.PlayerData.KillRecord());
         this.markDirty();
      }

      public int getNumKills() {
         return this.killRecords.size();
      }

      public void recordBountyKilled() {
         this.recentBountyKilled = 864000;
         this.markDirty();
      }

      public boolean recentlyBountyKilled() {
         return this.recentBountyKilled > 0;
      }

      public void update() {
         boolean minorChanges = false;
         if (this.recentBountyKilled > 0) {
            --this.recentBountyKilled;
            minorChanges = true;
         }

         List toRemove = new ArrayList();

         for(Iterator var3 = this.killRecords.iterator(); var3.hasNext(); minorChanges = true) {
            LOTRFactionBounties.PlayerData.KillRecord kr = (LOTRFactionBounties.PlayerData.KillRecord)var3.next();
            kr.timeElapsed--;
            if (kr.timeElapsed <= 0) {
               toRemove.add(kr);
            }
         }

         if (!toRemove.isEmpty()) {
            this.killRecords.removeAll(toRemove);
            minorChanges = true;
         }

         if (minorChanges && MinecraftServer.func_71276_C().func_71259_af() % 600 == 0) {
            this.markDirty();
         }

      }

      private void markDirty() {
         this.bountyList.markDirty();
      }

      public boolean shouldSave() {
         return !this.killRecords.isEmpty() || this.recentBountyKilled > 0;
      }

      public void writeToNBT(NBTTagCompound nbt) {
         NBTTagList recordTags = new NBTTagList();
         Iterator var3 = this.killRecords.iterator();

         while(var3.hasNext()) {
            LOTRFactionBounties.PlayerData.KillRecord kr = (LOTRFactionBounties.PlayerData.KillRecord)var3.next();
            NBTTagCompound killData = new NBTTagCompound();
            kr.writeToNBT(killData);
            recordTags.func_74742_a(killData);
         }

         nbt.func_74782_a("KillRecords", recordTags);
         nbt.func_74768_a("RecentBountyKilled", this.recentBountyKilled);
      }

      public void readFromNBT(NBTTagCompound nbt) {
         this.killRecords.clear();
         if (nbt.func_74764_b("KillRecords")) {
            NBTTagList recordTags = nbt.func_150295_c("KillRecords", 10);

            for(int i = 0; i < recordTags.func_74745_c(); ++i) {
               NBTTagCompound killData = recordTags.func_150305_b(i);
               LOTRFactionBounties.PlayerData.KillRecord kr = new LOTRFactionBounties.PlayerData.KillRecord();
               kr.readFromNBT(killData);
               this.killRecords.add(kr);
            }
         }

         this.recentBountyKilled = nbt.func_74762_e("RecentBountyKilled");
      }

      public String findUsername() {
         if (this.username == null) {
            GameProfile profile = MinecraftServer.func_71276_C().func_152358_ax().func_152652_a(this.playerID);
            if (profile == null || StringUtils.isBlank(profile.getName())) {
               String name = UsernameCache.getLastKnownUsername(this.playerID);
               if (name != null) {
                  profile = new GameProfile(this.playerID, name);
               } else {
                  profile = new GameProfile(this.playerID, "");
                  MinecraftServer.func_71276_C().func_147130_as().fillProfileProperties(profile, true);
               }
            }

            this.username = profile.getName();
         }

         return this.username;
      }

      private static class KillRecord {
         private int timeElapsed = 3456000;

         public KillRecord() {
         }

         public void writeToNBT(NBTTagCompound nbt) {
            nbt.func_74768_a("Time", this.timeElapsed);
         }

         public void readFromNBT(NBTTagCompound nbt) {
            this.timeElapsed = nbt.func_74762_e("Time");
         }
      }
   }
}
