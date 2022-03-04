package lotr.common.world.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.network.LOTRPacketCWPSharedHideClient;
import lotr.common.network.LOTRPacketCWPSharedUnlockClient;
import lotr.common.network.LOTRPacketCreateCWPClient;
import lotr.common.network.LOTRPacketDeleteCWPClient;
import lotr.common.network.LOTRPacketFellowship;
import lotr.common.network.LOTRPacketRenameCWPClient;
import lotr.common.network.LOTRPacketShareCWPClient;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import org.apache.commons.lang3.StringUtils;

public class LOTRCustomWaypoint implements LOTRAbstractWaypoint {
   private String customName;
   private double mapX;
   private double mapY;
   private int xCoord;
   private int yCoord;
   private int zCoord;
   private int ID;
   private List sharedFellowshipIDs = new ArrayList();
   private UUID sharingPlayer;
   private String sharingPlayerName;
   private boolean sharedUnlocked;
   private static final int SHARED_UNLOCK_RANGE = 1000;
   private boolean sharedHidden;

   public static LOTRCustomWaypoint createForPlayer(String name, EntityPlayer entityplayer) {
      LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
      int cwpID = playerData.getNextCwpID();
      int i = MathHelper.func_76128_c(entityplayer.field_70165_t);
      int j = MathHelper.func_76128_c(entityplayer.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(entityplayer.field_70161_v);
      double mapX = (double)LOTRWaypoint.worldToMapX((double)i);
      double mapY = (double)LOTRWaypoint.worldToMapZ((double)k);
      LOTRCustomWaypoint cwp = new LOTRCustomWaypoint(name, mapX, mapY, i, j, k, cwpID);
      playerData.addCustomWaypoint(cwp);
      playerData.incrementNextCwpID();
      return cwp;
   }

   public LOTRCustomWaypoint(String name, double i, double j, int posX, int posY, int posZ, int id) {
      this.customName = name;
      this.mapX = i;
      this.mapY = j;
      this.xCoord = posX;
      this.yCoord = posY;
      this.zCoord = posZ;
      this.ID = id;
   }

   public double getX() {
      return this.mapX;
   }

   public double getY() {
      return this.mapY;
   }

   public int getXCoord() {
      return this.xCoord;
   }

   public int getYCoord(World world, int i, int k) {
      int j = this.yCoord;
      if (j < 0) {
         this.yCoord = LOTRMod.getTrueTopBlock(world, i, k);
      } else if (!this.isSafeBlock(world, i, j, k)) {
         Block below = world.func_147439_a(i, j - 1, k);
         Block block = world.func_147439_a(i, j, k);
         Block above = world.func_147439_a(i, j + 1, k);
         boolean belowSafe = below.func_149688_o().func_76230_c();
         boolean blockSafe = !block.isNormalCube(world, i, j, k);
         boolean aboveSafe = !above.isNormalCube(world, i, j + 1, k);
         boolean foundSafe = false;
         int start;
         int j1;
         if (!belowSafe) {
            start = j - 1;

            for(j1 = start; j1 >= 1; --j1) {
               if (this.isSafeBlock(world, i, j1, k)) {
                  this.yCoord = j1;
                  foundSafe = true;
                  break;
               }
            }
         }

         if (!foundSafe && (!blockSafe || !aboveSafe)) {
            start = aboveSafe ? j + 1 : j + 2;

            for(j1 = start; j1 <= world.func_72800_K() - 1; ++j1) {
               if (this.isSafeBlock(world, i, j1, k)) {
                  this.yCoord = j1;
                  foundSafe = true;
                  break;
               }
            }
         }

         if (!foundSafe) {
            this.yCoord = LOTRMod.getTrueTopBlock(world, i, k);
         }
      }

      return this.yCoord;
   }

   private boolean isSafeBlock(World world, int i, int j, int k) {
      Block below = world.func_147439_a(i, j - 1, k);
      Block block = world.func_147439_a(i, j, k);
      Block above = world.func_147439_a(i, j + 1, k);
      if (below.func_149688_o().func_76230_c() && !block.isNormalCube(world, i, j, k) && !above.isNormalCube(world, i, j + 1, k)) {
         if (!block.func_149688_o().func_76224_d() && block.func_149688_o() != Material.field_151581_o) {
            return !above.func_149688_o().func_76224_d() && above.func_149688_o() != Material.field_151581_o;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public int getYCoordSaved() {
      return this.yCoord;
   }

   public int getZCoord() {
      return this.zCoord;
   }

   public String getCodeName() {
      return this.customName;
   }

   public String getDisplayName() {
      return this.isShared() ? StatCollector.func_74837_a("lotr.waypoint.shared", new Object[]{this.customName}) : StatCollector.func_74837_a("lotr.waypoint.custom", new Object[]{this.customName});
   }

   public String getLoreText(EntityPlayer entityplayer) {
      boolean ownShared = !this.isShared() && !this.sharedFellowshipIDs.isEmpty();
      boolean shared = this.isShared() && this.sharingPlayerName != null;
      if (ownShared || shared) {
         int numShared = this.sharedFellowshipIDs.size();
         int maxShow = true;
         int numShown = 0;
         List fsNames = new ArrayList();

         for(int i = 0; i < 3 && i < this.sharedFellowshipIDs.size(); ++i) {
            UUID fsID = (UUID)this.sharedFellowshipIDs.get(i);
            LOTRFellowshipClient fs = LOTRLevelData.getData(entityplayer).getClientFellowshipByID(fsID);
            if (fs != null) {
               fsNames.add(fs.getName());
               ++numShown;
            }
         }

         String sharedFsNames = "";

         String s;
         for(Iterator var12 = fsNames.iterator(); var12.hasNext(); sharedFsNames = sharedFsNames + "\n" + s) {
            s = (String)var12.next();
         }

         if (numShared > numShown) {
            int numMore = numShared - numShown;
            sharedFsNames = sharedFsNames + "\n" + StatCollector.func_74837_a("lotr.waypoint.custom.andMore", new Object[]{numMore});
         }

         if (ownShared) {
            return StatCollector.func_74837_a("lotr.waypoint.custom.info", new Object[]{sharedFsNames});
         }

         if (shared) {
            return StatCollector.func_74837_a("lotr.waypoint.shared.info", new Object[]{this.sharingPlayerName, sharedFsNames});
         }
      }

      return null;
   }

   public boolean hasPlayerUnlocked(EntityPlayer entityplayer) {
      return this.isShared() ? this.isSharedUnlocked() : true;
   }

   public LOTRAbstractWaypoint.WaypointLockState getLockState(EntityPlayer entityplayer) {
      boolean unlocked = this.hasPlayerUnlocked(entityplayer);
      if (this.isShared()) {
         return unlocked ? LOTRAbstractWaypoint.WaypointLockState.SHARED_CUSTOM_UNLOCKED : LOTRAbstractWaypoint.WaypointLockState.SHARED_CUSTOM_LOCKED;
      } else {
         return unlocked ? LOTRAbstractWaypoint.WaypointLockState.CUSTOM_UNLOCKED : LOTRAbstractWaypoint.WaypointLockState.CUSTOM_LOCKED;
      }
   }

   public boolean isHidden() {
      return false;
   }

   public int getID() {
      return this.ID;
   }

   private LOTRWaypoint.Region getWorldPosRegion(World world) {
      BiomeGenBase biome = world.func_72807_a(this.xCoord, this.zCoord);
      return biome instanceof LOTRBiome ? ((LOTRBiome)biome).getBiomeWaypoints() : null;
   }

   public void rename(String newName) {
      this.customName = newName;
   }

   public static String validateCustomName(String name) {
      name = StringUtils.trim(name);
      return !StringUtils.isBlank(name) ? name : null;
   }

   public List getSharedFellowshipIDs() {
      return this.sharedFellowshipIDs;
   }

   public void addSharedFellowship(UUID fsID) {
      if (!this.sharedFellowshipIDs.contains(fsID)) {
         this.sharedFellowshipIDs.add(fsID);
      }

   }

   public void removeSharedFellowship(UUID fsID) {
      if (this.sharedFellowshipIDs.contains(fsID)) {
         this.sharedFellowshipIDs.remove(fsID);
      }

   }

   public boolean hasSharedFellowship(LOTRFellowship fs) {
      return this.hasSharedFellowship(fs.getFellowshipID());
   }

   public boolean hasSharedFellowship(UUID fsID) {
      return this.sharedFellowshipIDs.contains(fsID);
   }

   public void validateFellowshipIDs(LOTRPlayerData ownerData) {
      UUID ownerUUID = ownerData.getPlayerUUID();
      Set removeIDs = new HashSet();
      Iterator var4 = this.sharedFellowshipIDs.iterator();

      while(true) {
         UUID fsID;
         LOTRFellowship fs;
         do {
            if (!var4.hasNext()) {
               this.sharedFellowshipIDs.removeAll(removeIDs);
               return;
            }

            fsID = (UUID)var4.next();
            fs = LOTRFellowshipData.getActiveFellowship(fsID);
         } while(fs != null && fs.containsPlayer(ownerUUID));

         removeIDs.add(fsID);
      }
   }

   public void setSharedFellowshipIDs(List fsIDs) {
      this.sharedFellowshipIDs = fsIDs;
   }

   public void setSharingPlayerID(UUID id) {
      UUID prev = this.sharingPlayer;
      this.sharingPlayer = id;
      if (MinecraftServer.func_71276_C() != null && (prev == null || !prev.equals(this.sharingPlayer))) {
         this.sharingPlayerName = LOTRPacketFellowship.getPlayerProfileWithUsername(this.sharingPlayer).getName();
      }

   }

   public UUID getSharingPlayerID() {
      return this.sharingPlayer;
   }

   public boolean isShared() {
      return this.sharingPlayer != null;
   }

   public void setSharingPlayerName(String s) {
      this.sharingPlayerName = s;
   }

   public String getSharingPlayerName() {
      return this.sharingPlayerName;
   }

   public LOTRCustomWaypoint createCopyOfShared(UUID sharer) {
      LOTRCustomWaypoint copy = new LOTRCustomWaypoint(this.customName, this.mapX, this.mapY, this.xCoord, this.yCoord, this.zCoord, this.ID);
      copy.setSharingPlayerID(sharer);
      copy.setSharedFellowshipIDs(new ArrayList(this.sharedFellowshipIDs));
      return copy;
   }

   public boolean isSharedUnlocked() {
      return this.sharedUnlocked;
   }

   public void setSharedUnlocked() {
      this.sharedUnlocked = true;
   }

   public boolean canUnlockShared(EntityPlayer entityplayer) {
      if (this.yCoord >= 0) {
         double distSq = entityplayer.func_70092_e((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D);
         double unlockRangeSq = 1000000.0D;
         return distSq <= unlockRangeSq;
      } else {
         return false;
      }
   }

   public boolean isSharedHidden() {
      return this.sharedHidden;
   }

   public void setSharedHidden(boolean flag) {
      this.sharedHidden = flag;
   }

   public List getPlayersInAllSharedFellowships() {
      List allPlayers = new ArrayList();
      Iterator var2 = this.sharedFellowshipIDs.iterator();

      while(true) {
         LOTRFellowship fs;
         do {
            if (!var2.hasNext()) {
               return allPlayers;
            }

            UUID fsID = (UUID)var2.next();
            fs = LOTRFellowshipData.getActiveFellowship(fsID);
         } while(fs == null);

         List fsPlayers = fs.getAllPlayerUUIDs();
         Iterator var6 = fsPlayers.iterator();

         while(var6.hasNext()) {
            UUID player = (UUID)var6.next();
            if (!player.equals(this.sharingPlayer) && !allPlayers.contains(player)) {
               allPlayers.add(player);
            }
         }
      }
   }

   public LOTRPacketCreateCWPClient getClientPacket() {
      return new LOTRPacketCreateCWPClient(this.mapX, this.mapY, this.xCoord, this.yCoord, this.zCoord, this.ID, this.customName, this.sharedFellowshipIDs);
   }

   public LOTRPacketDeleteCWPClient getClientDeletePacket() {
      return new LOTRPacketDeleteCWPClient(this.ID);
   }

   public LOTRPacketRenameCWPClient getClientRenamePacket() {
      return new LOTRPacketRenameCWPClient(this.ID, this.customName);
   }

   public LOTRPacketShareCWPClient getClientAddFellowshipPacket(UUID fsID) {
      return new LOTRPacketShareCWPClient(this.ID, fsID, true);
   }

   public LOTRPacketShareCWPClient getClientRemoveFellowshipPacket(UUID fsID) {
      return new LOTRPacketShareCWPClient(this.ID, fsID, false);
   }

   public LOTRPacketCreateCWPClient getClientPacketShared() {
      return (new LOTRPacketCreateCWPClient(this.mapX, this.mapY, this.xCoord, this.yCoord, this.zCoord, this.ID, this.customName, this.sharedFellowshipIDs)).setSharingPlayer(this.sharingPlayer, this.sharingPlayerName, this.sharedUnlocked, this.sharedHidden);
   }

   public LOTRPacketDeleteCWPClient getClientDeletePacketShared() {
      return (new LOTRPacketDeleteCWPClient(this.ID)).setSharingPlayer(this.sharingPlayer);
   }

   public LOTRPacketRenameCWPClient getClientRenamePacketShared() {
      return (new LOTRPacketRenameCWPClient(this.ID, this.customName)).setSharingPlayer(this.sharingPlayer);
   }

   public LOTRPacketCWPSharedUnlockClient getClientSharedUnlockPacket() {
      return new LOTRPacketCWPSharedUnlockClient(this.ID, this.sharingPlayer);
   }

   public LOTRPacketCWPSharedHideClient getClientSharedHidePacket(boolean hide) {
      return new LOTRPacketCWPSharedHideClient(this.ID, this.sharingPlayer, hide);
   }

   public void writeToNBT(NBTTagCompound nbt, LOTRPlayerData pd) {
      nbt.func_74778_a("Name", this.customName);
      nbt.func_74780_a("XMap", this.mapX);
      nbt.func_74780_a("YMap", this.mapY);
      nbt.func_74768_a("XCoord", this.xCoord);
      nbt.func_74768_a("YCoord", this.yCoord);
      nbt.func_74768_a("ZCoord", this.zCoord);
      nbt.func_74768_a("ID", this.ID);
      this.validateFellowshipIDs(pd);
      if (!this.sharedFellowshipIDs.isEmpty()) {
         NBTTagList sharedFellowshipTags = new NBTTagList();
         Iterator var4 = this.sharedFellowshipIDs.iterator();

         while(var4.hasNext()) {
            UUID fsID = (UUID)var4.next();
            NBTTagString tag = new NBTTagString(fsID.toString());
            sharedFellowshipTags.func_74742_a(tag);
         }

         nbt.func_74782_a("SharedFellowships", sharedFellowshipTags);
      }

   }

   public static LOTRCustomWaypoint readFromNBT(NBTTagCompound nbt, LOTRPlayerData pd) {
      String name = nbt.func_74779_i("Name");
      double x = 0.0D;
      double y = 0.0D;
      if (nbt.func_74764_b("XMap")) {
         x = nbt.func_74769_h("XMap");
      } else if (nbt.func_74764_b("X")) {
         x = (double)nbt.func_74762_e("X");
      }

      if (nbt.func_74764_b("YMap")) {
         y = nbt.func_74769_h("YMap");
      } else if (nbt.func_74764_b("Y")) {
         y = (double)nbt.func_74762_e("Y");
      }

      int xCoord = nbt.func_74762_e("XCoord");
      int zCoord = nbt.func_74762_e("ZCoord");
      int yCoord;
      if (nbt.func_74764_b("YCoord")) {
         yCoord = nbt.func_74762_e("YCoord");
      } else {
         yCoord = -1;
      }

      int ID = nbt.func_74762_e("ID");
      LOTRCustomWaypoint cwp = new LOTRCustomWaypoint(name, x, y, xCoord, yCoord, zCoord, ID);
      cwp.sharedFellowshipIDs.clear();
      if (nbt.func_74764_b("SharedFellowships")) {
         NBTTagList sharedFellowshipTags = nbt.func_150295_c("SharedFellowships", 8);

         for(int i = 0; i < sharedFellowshipTags.func_74745_c(); ++i) {
            UUID fsID = UUID.fromString(sharedFellowshipTags.func_150307_f(i));
            if (fsID != null) {
               cwp.sharedFellowshipIDs.add(fsID);
            }
         }
      }

      cwp.validateFellowshipIDs(pd);
      return cwp;
   }
}
