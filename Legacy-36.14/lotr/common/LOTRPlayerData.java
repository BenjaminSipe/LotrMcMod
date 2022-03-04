package lotr.common;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import lotr.common.block.LOTRBlockCraftingTable;
import lotr.common.command.LOTRCommandAdminHideMap;
import lotr.common.entity.npc.LOTREntityGollum;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionData;
import lotr.common.fac.LOTRFactionRank;
import lotr.common.fellowship.FellowshipUpdateType;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.fellowship.LOTRFellowshipInvite;
import lotr.common.item.LOTRItemArmor;
import lotr.common.item.LOTRItemCrossbowBolt;
import lotr.common.item.LOTRMaterial;
import lotr.common.network.LOTRPacketAchievement;
import lotr.common.network.LOTRPacketAchievementRemove;
import lotr.common.network.LOTRPacketAlignDrain;
import lotr.common.network.LOTRPacketAlignmentBonus;
import lotr.common.network.LOTRPacketAlignmentChoiceOffer;
import lotr.common.network.LOTRPacketBrokenPledge;
import lotr.common.network.LOTRPacketCWPSharedHideClient;
import lotr.common.network.LOTRPacketCWPSharedUnlockClient;
import lotr.common.network.LOTRPacketCreateCWPClient;
import lotr.common.network.LOTRPacketDeleteCWPClient;
import lotr.common.network.LOTRPacketFTBounceClient;
import lotr.common.network.LOTRPacketFTScreen;
import lotr.common.network.LOTRPacketFTTimer;
import lotr.common.network.LOTRPacketFactionData;
import lotr.common.network.LOTRPacketFellowship;
import lotr.common.network.LOTRPacketFellowshipAcceptInviteResult;
import lotr.common.network.LOTRPacketFellowshipRemove;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketLoginPlayerData;
import lotr.common.network.LOTRPacketMessage;
import lotr.common.network.LOTRPacketMiniquest;
import lotr.common.network.LOTRPacketMiniquestRemove;
import lotr.common.network.LOTRPacketMiniquestTrackClient;
import lotr.common.network.LOTRPacketOptions;
import lotr.common.network.LOTRPacketPledge;
import lotr.common.network.LOTRPacketRenameCWPClient;
import lotr.common.network.LOTRPacketShareCWPClient;
import lotr.common.network.LOTRPacketTitle;
import lotr.common.network.LOTRPacketUpdateViewingFaction;
import lotr.common.network.LOTRPacketWaypointRegion;
import lotr.common.network.LOTRPacketWaypointUseCount;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestEvent;
import lotr.common.quest.LOTRMiniQuestWelcome;
import lotr.common.quest.MiniQuestSelector;
import lotr.common.util.LOTRLog;
import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenMistyMountains;
import lotr.common.world.map.LOTRAbstractWaypoint;
import lotr.common.world.map.LOTRConquestGrid;
import lotr.common.world.map.LOTRCustomWaypoint;
import lotr.common.world.map.LOTRCustomWaypointLogger;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class LOTRPlayerData {
   private UUID playerUUID;
   private boolean needsSave = false;
   private int pdTick = 0;
   private Map alignments = new HashMap();
   private Map factionDataMap = new HashMap();
   private LOTRFaction viewingFaction;
   private Map prevRegionFactions = new HashMap();
   private boolean hideAlignment = false;
   private Set takenAlignmentRewards = new HashSet();
   private LOTRFaction pledgeFaction;
   private int pledgeKillCooldown = 0;
   public static final int PLEDGE_KILL_COOLDOWN_MAX = 24000;
   private int pledgeBreakCooldown;
   private int pledgeBreakCooldownStart;
   private LOTRFaction brokenPledgeFaction = null;
   private boolean hasPre35Alignments = false;
   private boolean chosenUnwantedAlignments = false;
   private boolean hideOnMap = false;
   private boolean adminHideMap = false;
   private boolean showWaypoints = true;
   private boolean showCustomWaypoints = true;
   private boolean showHiddenSharedWaypoints = true;
   private boolean conquestKills = true;
   private List achievements = new ArrayList();
   private LOTRShields shield;
   private boolean friendlyFire = false;
   private boolean hiredDeathMessages = true;
   private ChunkCoordinates deathPoint;
   private int deathDim;
   private int alcoholTolerance;
   private List miniQuests = new ArrayList();
   private List miniQuestsCompleted = new ArrayList();
   private int completedMiniquestCount;
   private int completedBountyQuests;
   private UUID trackingMiniQuestID;
   private List bountiesPlaced = new ArrayList();
   private LOTRWaypoint lastWaypoint;
   private LOTRBiome lastBiome;
   private Map sentMessageTypes = new HashMap();
   private LOTRTitle.PlayerTitle playerTitle;
   private boolean femRankOverride = false;
   private int ftSinceTick;
   private LOTRAbstractWaypoint targetFTWaypoint;
   private int ticksUntilFT;
   private static int ticksUntilFT_max = 200;
   private UUID uuidToMount;
   private int uuidToMountTime;
   private long lastOnlineTime = -1L;
   private Set unlockedFTRegions = new HashSet();
   private List customWaypoints = new ArrayList();
   private List customWaypointsShared = new ArrayList();
   private Set cwpSharedUnlocked = new HashSet();
   private Set cwpSharedHidden = new HashSet();
   private Map wpUseCounts = new HashMap();
   private Map cwpUseCounts = new HashMap();
   private Map cwpSharedUseCounts = new HashMap();
   private static final int startCwpID = 20000;
   private int nextCwpID = 20000;
   private List fellowshipIDs = new ArrayList();
   private List fellowshipsClient = new ArrayList();
   private List fellowshipInvites = new ArrayList();
   private List fellowshipInvitesClient = new ArrayList();
   private UUID chatBoundFellowshipID;
   private boolean structuresBanned = false;
   private boolean teleportedME = false;
   private LOTRPlayerQuestData questData = new LOTRPlayerQuestData(this);
   private int siegeActiveTime;

   public LOTRPlayerData(UUID uuid) {
      this.playerUUID = uuid;
      this.viewingFaction = LOTRFaction.HOBBIT;
      this.ftSinceTick = LOTRLevelData.getWaypointCooldownMax() * 20;
   }

   public UUID getPlayerUUID() {
      return this.playerUUID;
   }

   private EntityPlayer getPlayer() {
      World[] searchWorlds = null;
      if (LOTRMod.proxy.isClient()) {
         searchWorlds = new World[]{LOTRMod.proxy.getClientWorld()};
      } else {
         searchWorlds = MinecraftServer.func_71276_C().field_71305_c;
      }

      Object var2 = searchWorlds;
      int var3 = ((Object[])searchWorlds).length;

      for(int var4 = 0; var4 < var3; ++var4) {
         World world = ((Object[])var2)[var4];
         EntityPlayer entityplayer = ((World)world).func_152378_a(this.playerUUID);
         if (entityplayer != null) {
            return entityplayer;
         }
      }

      return null;
   }

   private EntityPlayer getOtherPlayer(UUID uuid) {
      WorldServer[] var2 = MinecraftServer.func_71276_C().field_71305_c;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         World world = var2[var4];
         EntityPlayer entityplayer = world.func_152378_a(uuid);
         if (entityplayer != null) {
            return entityplayer;
         }
      }

      return null;
   }

   public void markDirty() {
      this.needsSave = true;
   }

   public boolean needsSave() {
      return this.needsSave;
   }

   public void save(NBTTagCompound playerData) {
      NBTTagList alignmentTags = new NBTTagList();
      Iterator var3 = this.alignments.entrySet().iterator();

      while(var3.hasNext()) {
         Entry entry = (Entry)var3.next();
         LOTRFaction faction = (LOTRFaction)entry.getKey();
         float alignment = (Float)entry.getValue();
         NBTTagCompound nbt = new NBTTagCompound();
         nbt.func_74778_a("Faction", faction.codeName());
         nbt.func_74776_a("AlignF", alignment);
         alignmentTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("AlignmentMap", alignmentTags);
      NBTTagList factionDataTags = new NBTTagList();
      Iterator var24 = this.factionDataMap.entrySet().iterator();

      NBTTagCompound nbt;
      while(var24.hasNext()) {
         Entry entry = (Entry)var24.next();
         LOTRFaction faction = (LOTRFaction)entry.getKey();
         LOTRFactionData data = (LOTRFactionData)entry.getValue();
         nbt = new NBTTagCompound();
         nbt.func_74778_a("Faction", faction.codeName());
         data.save(nbt);
         factionDataTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("FactionData", factionDataTags);
      playerData.func_74778_a("CurrentFaction", this.viewingFaction.codeName());
      NBTTagList prevRegionFactionTags = new NBTTagList();
      Iterator var27 = this.prevRegionFactions.entrySet().iterator();

      NBTTagCompound nbt;
      while(var27.hasNext()) {
         Entry entry = (Entry)var27.next();
         LOTRDimension.DimensionRegion region = (LOTRDimension.DimensionRegion)entry.getKey();
         LOTRFaction faction = (LOTRFaction)entry.getValue();
         nbt = new NBTTagCompound();
         nbt.func_74778_a("Region", region.codeName());
         nbt.func_74778_a("Faction", faction.codeName());
         prevRegionFactionTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("PrevRegionFactions", prevRegionFactionTags);
      playerData.func_74757_a("HideAlignment", this.hideAlignment);
      NBTTagList takenRewardsTags = new NBTTagList();
      Iterator var31 = this.takenAlignmentRewards.iterator();

      while(var31.hasNext()) {
         LOTRFaction faction = (LOTRFaction)var31.next();
         nbt = new NBTTagCompound();
         nbt.func_74778_a("Faction", faction.codeName());
         takenRewardsTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("TakenAlignmentRewards", takenRewardsTags);
      if (this.pledgeFaction != null) {
         playerData.func_74778_a("PledgeFac", this.pledgeFaction.codeName());
      }

      playerData.func_74768_a("PledgeKillCD", this.pledgeKillCooldown);
      playerData.func_74768_a("PledgeBreakCD", this.pledgeBreakCooldown);
      playerData.func_74768_a("PledgeBreakCDStart", this.pledgeBreakCooldownStart);
      if (this.brokenPledgeFaction != null) {
         playerData.func_74778_a("BrokenPledgeFac", this.brokenPledgeFaction.codeName());
      }

      playerData.func_74757_a("Pre35Align", this.hasPre35Alignments);
      playerData.func_74757_a("Chosen35Align", this.chosenUnwantedAlignments);
      playerData.func_74757_a("HideOnMap", this.hideOnMap);
      playerData.func_74757_a("AdminHideMap", this.adminHideMap);
      playerData.func_74757_a("ShowWP", this.showWaypoints);
      playerData.func_74757_a("ShowCWP", this.showCustomWaypoints);
      playerData.func_74757_a("ShowHiddenSWP", this.showHiddenSharedWaypoints);
      playerData.func_74757_a("ConquestKills", this.conquestKills);
      NBTTagList achievementTags = new NBTTagList();
      Iterator var37 = this.achievements.iterator();

      while(var37.hasNext()) {
         LOTRAchievement achievement = (LOTRAchievement)var37.next();
         nbt = new NBTTagCompound();
         nbt.func_74778_a("Category", achievement.category.name());
         nbt.func_74768_a("ID", achievement.ID);
         achievementTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("Achievements", achievementTags);
      if (this.shield != null) {
         playerData.func_74778_a("Shield", this.shield.name());
      }

      playerData.func_74757_a("FriendlyFire", this.friendlyFire);
      playerData.func_74757_a("HiredDeathMessages", this.hiredDeathMessages);
      if (this.deathPoint != null) {
         playerData.func_74768_a("DeathX", this.deathPoint.field_71574_a);
         playerData.func_74768_a("DeathY", this.deathPoint.field_71572_b);
         playerData.func_74768_a("DeathZ", this.deathPoint.field_71573_c);
         playerData.func_74768_a("DeathDim", this.deathDim);
      }

      playerData.func_74768_a("Alcohol", this.alcoholTolerance);
      NBTTagList miniquestTags = new NBTTagList();
      Iterator var40 = this.miniQuests.iterator();

      while(var40.hasNext()) {
         LOTRMiniQuest quest = (LOTRMiniQuest)var40.next();
         NBTTagCompound nbt = new NBTTagCompound();
         quest.writeToNBT(nbt);
         miniquestTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("MiniQuests", miniquestTags);
      NBTTagList miniquestCompletedTags = new NBTTagList();
      Iterator var43 = this.miniQuestsCompleted.iterator();

      while(var43.hasNext()) {
         LOTRMiniQuest quest = (LOTRMiniQuest)var43.next();
         NBTTagCompound nbt = new NBTTagCompound();
         quest.writeToNBT(nbt);
         miniquestCompletedTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("MiniQuestsCompleted", miniquestCompletedTags);
      playerData.func_74768_a("MQCompleteCount", this.completedMiniquestCount);
      playerData.func_74768_a("MQCompletedBounties", this.completedBountyQuests);
      if (this.trackingMiniQuestID != null) {
         playerData.func_74778_a("MiniQuestTrack", this.trackingMiniQuestID.toString());
      }

      NBTTagList bountyTags = new NBTTagList();
      Iterator var46 = this.bountiesPlaced.iterator();

      while(var46.hasNext()) {
         LOTRFaction fac = (LOTRFaction)var46.next();
         String fName = fac.codeName();
         bountyTags.func_74742_a(new NBTTagString(fName));
      }

      playerData.func_74782_a("BountiesPlaced", bountyTags);
      if (this.lastWaypoint != null) {
         String lastWPName = this.lastWaypoint.getCodeName();
         playerData.func_74778_a("LastWP", lastWPName);
      }

      if (this.lastBiome != null) {
         int lastBiomeID = this.lastBiome.field_76756_M;
         playerData.func_74777_a("LastBiome", (short)lastBiomeID);
      }

      NBTTagList sentMessageTags = new NBTTagList();
      Iterator var51 = this.sentMessageTypes.entrySet().iterator();

      NBTTagCompound nbt;
      while(var51.hasNext()) {
         Entry entry = (Entry)var51.next();
         LOTRGuiMessageTypes message = (LOTRGuiMessageTypes)entry.getKey();
         boolean sent = (Boolean)entry.getValue();
         nbt = new NBTTagCompound();
         nbt.func_74778_a("Message", message.getSaveName());
         nbt.func_74757_a("Sent", sent);
         sentMessageTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("SentMessageTypes", sentMessageTags);
      if (this.playerTitle != null) {
         playerData.func_74778_a("PlayerTitle", this.playerTitle.getTitle().getTitleName());
         playerData.func_74768_a("PlayerTitleColor", this.playerTitle.getColor().func_96298_a());
      }

      playerData.func_74757_a("FemRankOverride", this.femRankOverride);
      playerData.func_74768_a("FTSince", this.ftSinceTick);
      if (this.uuidToMount != null) {
         playerData.func_74778_a("MountUUID", this.uuidToMount.toString());
      }

      playerData.func_74768_a("MountUUIDTime", this.uuidToMountTime);
      playerData.func_74772_a("LastOnlineTime", this.lastOnlineTime);
      NBTTagList unlockedFTRegionTags = new NBTTagList();
      Iterator var54 = this.unlockedFTRegions.iterator();

      while(var54.hasNext()) {
         LOTRWaypoint.Region region = (LOTRWaypoint.Region)var54.next();
         NBTTagCompound nbt = new NBTTagCompound();
         nbt.func_74778_a("Name", region.name());
         unlockedFTRegionTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("UnlockedFTRegions", unlockedFTRegionTags);
      NBTTagList customWaypointTags = new NBTTagList();
      Iterator var57 = this.customWaypoints.iterator();

      while(var57.hasNext()) {
         LOTRCustomWaypoint waypoint = (LOTRCustomWaypoint)var57.next();
         nbt = new NBTTagCompound();
         waypoint.writeToNBT(nbt, this);
         customWaypointTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("CustomWaypoints", customWaypointTags);
      NBTTagList cwpSharedUnlockedTags = new NBTTagList();
      Iterator var61 = this.cwpSharedUnlocked.iterator();

      while(var61.hasNext()) {
         LOTRPlayerData.CWPSharedKey key = (LOTRPlayerData.CWPSharedKey)var61.next();
         NBTTagCompound nbt = new NBTTagCompound();
         nbt.func_74778_a("SharingPlayer", key.sharingPlayer.toString());
         nbt.func_74768_a("CustomID", key.waypointID);
         cwpSharedUnlockedTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("CWPSharedUnlocked", cwpSharedUnlockedTags);
      NBTTagList cwpSharedHiddenTags = new NBTTagList();
      Iterator var64 = this.cwpSharedHidden.iterator();

      while(var64.hasNext()) {
         LOTRPlayerData.CWPSharedKey key = (LOTRPlayerData.CWPSharedKey)var64.next();
         NBTTagCompound nbt = new NBTTagCompound();
         nbt.func_74778_a("SharingPlayer", key.sharingPlayer.toString());
         nbt.func_74768_a("CustomID", key.waypointID);
         cwpSharedHiddenTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("CWPSharedHidden", cwpSharedHiddenTags);
      NBTTagList wpUseTags = new NBTTagList();
      Iterator var67 = this.wpUseCounts.entrySet().iterator();

      int ID;
      NBTTagCompound questNBT;
      while(var67.hasNext()) {
         Entry e = (Entry)var67.next();
         LOTRAbstractWaypoint wp = (LOTRAbstractWaypoint)e.getKey();
         ID = (Integer)e.getValue();
         questNBT = new NBTTagCompound();
         questNBT.func_74778_a("WPName", wp.getCodeName());
         questNBT.func_74768_a("Count", ID);
         wpUseTags.func_74742_a(questNBT);
      }

      playerData.func_74782_a("WPUses", wpUseTags);
      NBTTagList cwpUseTags = new NBTTagList();
      Iterator var70 = this.cwpUseCounts.entrySet().iterator();

      NBTTagCompound nbt;
      while(var70.hasNext()) {
         Entry e = (Entry)var70.next();
         ID = (Integer)e.getKey();
         int count = (Integer)e.getValue();
         nbt = new NBTTagCompound();
         nbt.func_74768_a("CustomID", ID);
         nbt.func_74768_a("Count", count);
         cwpUseTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("CWPUses", cwpUseTags);
      NBTTagList cwpSharedUseTags = new NBTTagList();
      Iterator var73 = this.cwpSharedUseCounts.entrySet().iterator();

      NBTTagCompound nbt;
      while(var73.hasNext()) {
         Entry e = (Entry)var73.next();
         LOTRPlayerData.CWPSharedKey key = (LOTRPlayerData.CWPSharedKey)e.getKey();
         int count = (Integer)e.getValue();
         nbt = new NBTTagCompound();
         nbt.func_74778_a("SharingPlayer", key.sharingPlayer.toString());
         nbt.func_74768_a("CustomID", key.waypointID);
         nbt.func_74768_a("Count", count);
         cwpSharedUseTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("CWPSharedUses", cwpSharedUseTags);
      playerData.func_74768_a("NextCWPID", this.nextCwpID);
      NBTTagList fellowshipTags = new NBTTagList();
      Iterator var76 = this.fellowshipIDs.iterator();

      while(var76.hasNext()) {
         UUID fsID = (UUID)var76.next();
         nbt = new NBTTagCompound();
         nbt.func_74778_a("ID", fsID.toString());
         fellowshipTags.func_74742_a(nbt);
      }

      playerData.func_74782_a("Fellowships", fellowshipTags);
      NBTTagList fellowshipInviteTags = new NBTTagList();

      for(Iterator var82 = this.fellowshipInvites.iterator(); var82.hasNext(); fellowshipInviteTags.func_74742_a(nbt)) {
         LOTRFellowshipInvite invite = (LOTRFellowshipInvite)var82.next();
         nbt = new NBTTagCompound();
         nbt.func_74778_a("ID", invite.fellowshipID.toString());
         if (invite.inviterID != null) {
            nbt.func_74778_a("InviterID", invite.inviterID.toString());
         }
      }

      playerData.func_74782_a("FellowshipInvites", fellowshipInviteTags);
      if (this.chatBoundFellowshipID != null) {
         playerData.func_74778_a("ChatBoundFellowship", this.chatBoundFellowshipID.toString());
      }

      playerData.func_74757_a("StructuresBanned", this.structuresBanned);
      playerData.func_74757_a("TeleportedME", this.teleportedME);
      questNBT = new NBTTagCompound();
      this.questData.save(questNBT);
      playerData.func_74782_a("QuestData", questNBT);
      this.needsSave = false;
   }

   public void load(NBTTagCompound playerData) {
      this.alignments.clear();
      NBTTagList alignmentTags = playerData.func_150295_c("AlignmentMap", 10);

      for(int i = 0; i < alignmentTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = alignmentTags.func_150305_b(i);
         LOTRFaction faction = LOTRFaction.forName(nbt.func_74779_i("Faction"));
         if (faction != null) {
            float alignment;
            if (nbt.func_74764_b("Alignment")) {
               alignment = (float)nbt.func_74762_e("Alignment");
               this.hasPre35Alignments = true;
            } else {
               alignment = nbt.func_74760_g("AlignF");
            }

            this.alignments.put(faction, alignment);
         }
      }

      this.factionDataMap.clear();
      NBTTagList factionDataTags = playerData.func_150295_c("FactionData", 10);

      for(int i = 0; i < factionDataTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = factionDataTags.func_150305_b(i);
         LOTRFaction faction = LOTRFaction.forName(nbt.func_74779_i("Faction"));
         if (faction != null) {
            LOTRFactionData data = new LOTRFactionData(this, faction);
            data.load(nbt);
            this.factionDataMap.put(faction, data);
         }
      }

      if (playerData.func_74764_b("CurrentFaction")) {
         LOTRFaction cur = LOTRFaction.forName(playerData.func_74779_i("CurrentFaction"));
         if (cur != null) {
            this.viewingFaction = cur;
         }
      }

      this.prevRegionFactions.clear();
      NBTTagList prevRegionFactionTags = playerData.func_150295_c("PrevRegionFactions", 10);

      LOTRFaction faction;
      for(int i = 0; i < prevRegionFactionTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = prevRegionFactionTags.func_150305_b(i);
         LOTRDimension.DimensionRegion region = LOTRDimension.DimensionRegion.forName(nbt.func_74779_i("Region"));
         faction = LOTRFaction.forName(nbt.func_74779_i("Faction"));
         if (region != null && faction != null) {
            this.prevRegionFactions.put(region, faction);
         }
      }

      this.hideAlignment = playerData.func_74767_n("HideAlignment");
      this.takenAlignmentRewards.clear();
      NBTTagList takenRewardsTags = playerData.func_150295_c("TakenAlignmentRewards", 10);

      for(int i = 0; i < takenRewardsTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = takenRewardsTags.func_150305_b(i);
         faction = LOTRFaction.forName(nbt.func_74779_i("Faction"));
         if (faction != null) {
            this.takenAlignmentRewards.add(faction);
         }
      }

      this.pledgeFaction = null;
      if (playerData.func_74764_b("PledgeFac")) {
         this.pledgeFaction = LOTRFaction.forName(playerData.func_74779_i("PledgeFac"));
      }

      this.pledgeKillCooldown = playerData.func_74762_e("PledgeKillCD");
      this.pledgeBreakCooldown = playerData.func_74762_e("PledgeBreakCD");
      this.pledgeBreakCooldownStart = playerData.func_74762_e("PledgeBreakCDStart");
      this.brokenPledgeFaction = null;
      if (playerData.func_74764_b("BrokenPledgeFac")) {
         this.brokenPledgeFaction = LOTRFaction.forName(playerData.func_74779_i("BrokenPledgeFac"));
      }

      if (!this.hasPre35Alignments && playerData.func_74764_b("Pre35Align")) {
         this.hasPre35Alignments = playerData.func_74767_n("Pre35Align");
      }

      if (playerData.func_74764_b("Chosen35Align")) {
         this.chosenUnwantedAlignments = playerData.func_74767_n("Chosen35Align");
      }

      this.hideOnMap = playerData.func_74767_n("HideOnMap");
      this.adminHideMap = playerData.func_74767_n("AdminHideMap");
      if (playerData.func_74764_b("ShowWP")) {
         this.showWaypoints = playerData.func_74767_n("ShowWP");
      }

      if (playerData.func_74764_b("ShowCWP")) {
         this.showCustomWaypoints = playerData.func_74767_n("ShowCWP");
      }

      if (playerData.func_74764_b("ShowHiddenSWP")) {
         this.showHiddenSharedWaypoints = playerData.func_74767_n("ShowHiddenSWP");
      }

      if (playerData.func_74764_b("ConquestKills")) {
         this.conquestKills = playerData.func_74767_n("ConquestKills");
      }

      this.achievements.clear();
      NBTTagList achievementTags = playerData.func_150295_c("Achievements", 10);

      String s;
      int i;
      for(int i = 0; i < achievementTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = achievementTags.func_150305_b(i);
         s = nbt.func_74779_i("Category");
         i = nbt.func_74762_e("ID");
         LOTRAchievement achievement = LOTRAchievement.achievementForCategoryAndID(LOTRAchievement.categoryForName(s), i);
         if (achievement != null && !this.achievements.contains(achievement)) {
            this.achievements.add(achievement);
         }
      }

      this.shield = null;
      if (playerData.func_74764_b("Shield")) {
         LOTRShields savedShield = LOTRShields.shieldForName(playerData.func_74779_i("Shield"));
         if (savedShield != null) {
            this.shield = savedShield;
         }
      }

      if (playerData.func_74764_b("FriendlyFire")) {
         this.friendlyFire = playerData.func_74767_n("FriendlyFire");
      }

      if (playerData.func_74764_b("HiredDeathMessages")) {
         this.hiredDeathMessages = playerData.func_74767_n("HiredDeathMessages");
      }

      this.deathPoint = null;
      if (playerData.func_74764_b("DeathX") && playerData.func_74764_b("DeathY") && playerData.func_74764_b("DeathZ")) {
         this.deathPoint = new ChunkCoordinates(playerData.func_74762_e("DeathX"), playerData.func_74762_e("DeathY"), playerData.func_74762_e("DeathZ"));
         if (playerData.func_74764_b("DeathDim")) {
            this.deathDim = playerData.func_74762_e("DeathDim");
         } else {
            this.deathDim = LOTRDimension.MIDDLE_EARTH.dimensionID;
         }
      }

      this.alcoholTolerance = playerData.func_74762_e("Alcohol");
      this.miniQuests.clear();
      NBTTagList miniquestTags = playerData.func_150295_c("MiniQuests", 10);

      for(int i = 0; i < miniquestTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = miniquestTags.func_150305_b(i);
         LOTRMiniQuest quest = LOTRMiniQuest.loadQuestFromNBT(nbt, this);
         if (quest != null) {
            this.miniQuests.add(quest);
         }
      }

      this.miniQuestsCompleted.clear();
      NBTTagList miniquestCompletedTags = playerData.func_150295_c("MiniQuestsCompleted", 10);

      for(int i = 0; i < miniquestCompletedTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = miniquestCompletedTags.func_150305_b(i);
         LOTRMiniQuest quest = LOTRMiniQuest.loadQuestFromNBT(nbt, this);
         if (quest != null) {
            this.miniQuestsCompleted.add(quest);
         }
      }

      this.completedMiniquestCount = playerData.func_74762_e("MQCompleteCount");
      this.completedBountyQuests = playerData.func_74762_e("MQCompletedBounties");
      this.trackingMiniQuestID = null;
      if (playerData.func_74764_b("MiniQuestTrack")) {
         s = playerData.func_74779_i("MiniQuestTrack");
         this.trackingMiniQuestID = UUID.fromString(s);
      }

      this.bountiesPlaced.clear();
      NBTTagList bountyTags = playerData.func_150295_c("BountiesPlaced", 8);

      for(i = 0; i < bountyTags.func_74745_c(); ++i) {
         String fName = bountyTags.func_150307_f(i);
         LOTRFaction fac = LOTRFaction.forName(fName);
         if (fac != null) {
            this.bountiesPlaced.add(fac);
         }
      }

      this.lastWaypoint = null;
      if (playerData.func_74764_b("LastWP")) {
         String lastWPName = playerData.func_74779_i("LastWP");
         this.lastWaypoint = LOTRWaypoint.waypointForName(lastWPName);
      }

      this.lastBiome = null;
      if (playerData.func_74764_b("LastBiome")) {
         int lastBiomeID = playerData.func_74765_d("LastBiome");
         LOTRBiome[] biomeList = LOTRDimension.MIDDLE_EARTH.biomeList;
         if (lastBiomeID >= 0 && lastBiomeID < biomeList.length) {
            this.lastBiome = biomeList[lastBiomeID];
         }
      }

      this.sentMessageTypes.clear();
      NBTTagList sentMessageTags = playerData.func_150295_c("SentMessageTypes", 10);

      for(int i = 0; i < sentMessageTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = sentMessageTags.func_150305_b(i);
         LOTRGuiMessageTypes message = LOTRGuiMessageTypes.forSaveName(nbt.func_74779_i("Message"));
         if (message != null) {
            boolean sent = nbt.func_74767_n("Sent");
            this.sentMessageTypes.put(message, sent);
         }
      }

      this.playerTitle = null;
      int i;
      if (playerData.func_74764_b("PlayerTitle")) {
         LOTRTitle title = LOTRTitle.forName(playerData.func_74779_i("PlayerTitle"));
         if (title != null) {
            i = playerData.func_74762_e("PlayerTitleColor");
            EnumChatFormatting color = LOTRTitle.PlayerTitle.colorForID(i);
            this.playerTitle = new LOTRTitle.PlayerTitle(title, color);
         }
      }

      if (playerData.func_74764_b("FemRankOverride")) {
         this.femRankOverride = playerData.func_74767_n("FemRankOverride");
      }

      if (playerData.func_74764_b("FTSince")) {
         this.ftSinceTick = playerData.func_74762_e("FTSince");
      }

      this.targetFTWaypoint = null;
      this.uuidToMount = null;
      if (playerData.func_74764_b("MountUUID")) {
         this.uuidToMount = UUID.fromString(playerData.func_74779_i("MountUUID"));
      }

      this.uuidToMountTime = playerData.func_74762_e("MountUUIDTime");
      if (playerData.func_74764_b("LastOnlineTime")) {
         this.lastOnlineTime = playerData.func_74763_f("LastOnlineTime");
      }

      this.unlockedFTRegions.clear();
      NBTTagList unlockedFTRegionTags = playerData.func_150295_c("UnlockedFTRegions", 10);

      for(i = 0; i < unlockedFTRegionTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = unlockedFTRegionTags.func_150305_b(i);
         String regionName = nbt.func_74779_i("Name");
         LOTRWaypoint.Region region = LOTRWaypoint.regionForName(regionName);
         if (region != null) {
            this.unlockedFTRegions.add(region);
         }
      }

      this.customWaypoints.clear();
      NBTTagList customWaypointTags = playerData.func_150295_c("CustomWaypoints", 10);

      for(int i = 0; i < customWaypointTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = customWaypointTags.func_150305_b(i);
         LOTRCustomWaypoint waypoint = LOTRCustomWaypoint.readFromNBT(nbt, this);
         this.customWaypoints.add(waypoint);
      }

      this.cwpSharedUnlocked.clear();
      NBTTagList cwpSharedUnlockedTags = playerData.func_150295_c("CWPSharedUnlocked", 10);

      int i;
      for(int i = 0; i < cwpSharedUnlockedTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = cwpSharedUnlockedTags.func_150305_b(i);
         UUID sharingPlayer = UUID.fromString(nbt.func_74779_i("SharingPlayer"));
         if (sharingPlayer != null) {
            i = nbt.func_74762_e("CustomID");
            LOTRPlayerData.CWPSharedKey key = LOTRPlayerData.CWPSharedKey.keyFor(sharingPlayer, i);
            this.cwpSharedUnlocked.add(key);
         }
      }

      this.cwpSharedHidden.clear();
      NBTTagList cwpSharedHiddenTags = playerData.func_150295_c("CWPSharedHidden", 10);

      int i;
      for(int i = 0; i < cwpSharedHiddenTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = cwpSharedHiddenTags.func_150305_b(i);
         UUID sharingPlayer = UUID.fromString(nbt.func_74779_i("SharingPlayer"));
         if (sharingPlayer != null) {
            i = nbt.func_74762_e("CustomID");
            LOTRPlayerData.CWPSharedKey key = LOTRPlayerData.CWPSharedKey.keyFor(sharingPlayer, i);
            this.cwpSharedHidden.add(key);
         }
      }

      this.wpUseCounts.clear();
      NBTTagList wpCooldownTags = playerData.func_150295_c("WPUses", 10);

      int i;
      for(int i = 0; i < wpCooldownTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = wpCooldownTags.func_150305_b(i);
         String name = nbt.func_74779_i("WPName");
         i = nbt.func_74762_e("Count");
         LOTRWaypoint wp = LOTRWaypoint.waypointForName(name);
         if (wp != null) {
            this.wpUseCounts.put(wp, i);
         }
      }

      this.cwpUseCounts.clear();
      NBTTagList cwpCooldownTags = playerData.func_150295_c("CWPUses", 10);

      int i;
      for(i = 0; i < cwpCooldownTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = cwpCooldownTags.func_150305_b(i);
         i = nbt.func_74762_e("CustomID");
         i = nbt.func_74762_e("Count");
         this.cwpUseCounts.put(i, i);
      }

      this.cwpSharedUseCounts.clear();
      NBTTagList cwpSharedCooldownTags = playerData.func_150295_c("CWPSharedUses", 10);

      UUID fsID;
      for(i = 0; i < cwpSharedCooldownTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = cwpSharedCooldownTags.func_150305_b(i);
         fsID = UUID.fromString(nbt.func_74779_i("SharingPlayer"));
         if (fsID != null) {
            int ID = nbt.func_74762_e("CustomID");
            LOTRPlayerData.CWPSharedKey key = LOTRPlayerData.CWPSharedKey.keyFor(fsID, ID);
            int count = nbt.func_74762_e("Count");
            this.cwpSharedUseCounts.put(key, count);
         }
      }

      this.nextCwpID = 20000;
      if (playerData.func_74764_b("NextCWPID")) {
         this.nextCwpID = playerData.func_74762_e("NextCWPID");
      }

      this.fellowshipIDs.clear();
      NBTTagList fellowshipTags = playerData.func_150295_c("Fellowships", 10);

      NBTTagCompound questNBT;
      for(i = 0; i < fellowshipTags.func_74745_c(); ++i) {
         questNBT = fellowshipTags.func_150305_b(i);
         UUID fsID = UUID.fromString(questNBT.func_74779_i("ID"));
         if (fsID != null) {
            this.fellowshipIDs.add(fsID);
         }
      }

      this.fellowshipInvites.clear();
      NBTTagList fellowshipInviteTags = playerData.func_150295_c("FellowshipInvites", 10);

      for(i = 0; i < fellowshipInviteTags.func_74745_c(); ++i) {
         NBTTagCompound nbt = fellowshipInviteTags.func_150305_b(i);
         UUID fsID = UUID.fromString(nbt.func_74779_i("ID"));
         if (fsID != null) {
            UUID inviterID = null;
            if (nbt.func_74764_b("InviterID")) {
               inviterID = UUID.fromString(nbt.func_74779_i("InviterID"));
            }

            this.fellowshipInvites.add(new LOTRFellowshipInvite(fsID, inviterID));
         }
      }

      this.chatBoundFellowshipID = null;
      if (playerData.func_74764_b("ChatBoundFellowship")) {
         fsID = UUID.fromString(playerData.func_74779_i("ChatBoundFellowship"));
         if (fsID != null) {
            this.chatBoundFellowshipID = fsID;
         }
      }

      this.structuresBanned = playerData.func_74767_n("StructuresBanned");
      this.teleportedME = playerData.func_74767_n("TeleportedME");
      if (playerData.func_74764_b("QuestData")) {
         questNBT = playerData.func_74775_l("QuestData");
         this.questData.load(questNBT);
      }

   }

   public void sendPlayerData(EntityPlayerMP entityplayer) throws IOException {
      NBTTagCompound nbt = new NBTTagCompound();
      this.save(nbt);
      nbt.func_82580_o("Achievements");
      nbt.func_82580_o("MiniQuests");
      nbt.func_82580_o("MiniQuestsCompleted");
      nbt.func_82580_o("CustomWaypoints");
      nbt.func_82580_o("Fellowships");
      nbt.func_82580_o("FellowshipInvites");
      LOTRPacketLoginPlayerData packet = new LOTRPacketLoginPlayerData(nbt);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
      Iterator var4 = this.achievements.iterator();

      while(var4.hasNext()) {
         LOTRAchievement achievement = (LOTRAchievement)var4.next();
         this.sendAchievementPacket(entityplayer, achievement, false);
      }

      var4 = this.miniQuests.iterator();

      LOTRMiniQuest quest;
      while(var4.hasNext()) {
         quest = (LOTRMiniQuest)var4.next();
         this.sendMiniQuestPacket(entityplayer, quest, false);
      }

      var4 = this.miniQuestsCompleted.iterator();

      while(var4.hasNext()) {
         quest = (LOTRMiniQuest)var4.next();
         this.sendMiniQuestPacket(entityplayer, quest, true);
      }

      var4 = this.customWaypoints.iterator();

      while(var4.hasNext()) {
         LOTRCustomWaypoint waypoint = (LOTRCustomWaypoint)var4.next();
         LOTRPacketCreateCWPClient cwpPacket = waypoint.getClientPacket();
         LOTRPacketHandler.networkWrapper.sendTo(cwpPacket, entityplayer);
      }

      var4 = this.fellowshipIDs.iterator();

      while(var4.hasNext()) {
         UUID fsID = (UUID)var4.next();
         LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(fsID);
         if (fs != null) {
            this.sendFellowshipPacket(fs);
            fs.doRetroactiveWaypointSharerCheckIfNeeded();
            this.checkIfStillWaypointSharerForFellowship(fs);
         }
      }

      Set staleFellowshipInvites = new HashSet();
      Iterator var13 = this.fellowshipInvites.iterator();

      while(var13.hasNext()) {
         LOTRFellowshipInvite invite = (LOTRFellowshipInvite)var13.next();
         LOTRFellowship fs = LOTRFellowshipData.getFellowship(invite.fellowshipID);
         if (fs != null) {
            this.sendFellowshipInvitePacket(fs);
         } else {
            staleFellowshipInvites.add(invite);
         }
      }

      if (!staleFellowshipInvites.isEmpty()) {
         this.fellowshipInvites.removeAll(staleFellowshipInvites);
         this.markDirty();
      }

      this.addSharedCustomWaypointsFromAllFellowships();
   }

   public void send35AlignmentChoice(EntityPlayerMP entityplayer, World world) {
      if (LOTRConfig.alignmentDrain && this.hasPre35Alignments && !this.chosenUnwantedAlignments) {
         LOTRPacketAlignmentChoiceOffer pkt = new LOTRPacketAlignmentChoiceOffer();
         LOTRPacketHandler.networkWrapper.sendTo(pkt, entityplayer);
      }

   }

   public void chooseUnwantedAlignments(EntityPlayerMP entityplayer, Set setZeroFacs) {
      if (LOTRConfig.alignmentDrain && this.hasPre35Alignments && !this.chosenUnwantedAlignments) {
         Set validSelections = new HashSet();
         Iterator var4 = setZeroFacs.iterator();

         LOTRFaction fac;
         while(var4.hasNext()) {
            fac = (LOTRFaction)var4.next();
            boolean valid = false;
            if (this.getAlignment(fac) > 0.0F) {
               Iterator var7 = LOTRFaction.getPlayableAlignmentFactions().iterator();

               while(var7.hasNext()) {
                  LOTRFaction otherFac = (LOTRFaction)var7.next();
                  if (fac != otherFac && this.doFactionsDrain(fac, otherFac) && this.getAlignment(otherFac) > 0.0F) {
                     valid = true;
                     break;
                  }
               }
            }

            if (valid) {
               validSelections.add(fac);
            }
         }

         var4 = validSelections.iterator();

         while(var4.hasNext()) {
            fac = (LOTRFaction)var4.next();
            this.setAlignment(fac, 0.0F);
            entityplayer.func_145747_a(new ChatComponentTranslation("Set %s alignment to zero", new Object[]{fac.factionName()}));
         }

         this.hasPre35Alignments = false;
         this.chosenUnwantedAlignments = true;
         this.markDirty();
      }

   }

   private static boolean isTimerAutosaveTick() {
      return MinecraftServer.func_71276_C() != null && MinecraftServer.func_71276_C().func_71259_af() % 200 == 0;
   }

   public void onUpdate(EntityPlayerMP entityplayer, WorldServer world) {
      ++this.pdTick;
      LOTRDimension.DimensionRegion currentRegion = this.viewingFaction.factionRegion;
      LOTRDimension currentDim = LOTRDimension.getCurrentDimensionWithFallback(world);
      if (currentRegion.getDimension() != currentDim) {
         currentRegion = (LOTRDimension.DimensionRegion)currentDim.dimensionRegions.get(0);
         this.setViewingFaction(this.getRegionLastViewedFaction(currentRegion));
      }

      this.runAlignmentDraining(entityplayer);
      this.questData.onUpdate(entityplayer, world);
      if (!this.isSiegeActive()) {
         this.runAchievementChecks(entityplayer, world);
      }

      if (this.playerTitle != null && !this.playerTitle.getTitle().canPlayerUse(entityplayer)) {
         IChatComponent msg = new ChatComponentTranslation("chat.lotr.loseTitle", new Object[]{this.playerTitle.getFullTitleComponent(entityplayer)});
         entityplayer.func_145747_a(msg);
         this.setPlayerTitle((LOTRTitle.PlayerTitle)null);
      }

      if (this.pledgeKillCooldown > 0) {
         --this.pledgeKillCooldown;
         if (this.pledgeKillCooldown == 0 || isTimerAutosaveTick()) {
            this.markDirty();
         }
      }

      if (this.pledgeBreakCooldown > 0) {
         this.setPledgeBreakCooldown(this.pledgeBreakCooldown - 1);
      }

      if (this.trackingMiniQuestID != null && this.getTrackingMiniQuest() == null) {
         this.setTrackingMiniQuest((LOTRMiniQuest)null);
      }

      List activeMiniquests = this.getActiveMiniQuests();
      Iterator var6 = activeMiniquests.iterator();

      while(var6.hasNext()) {
         LOTRMiniQuest quest = (LOTRMiniQuest)var6.next();
         quest.onPlayerTick(entityplayer);
      }

      if (!this.bountiesPlaced.isEmpty()) {
         var6 = this.bountiesPlaced.iterator();

         while(var6.hasNext()) {
            LOTRFaction fac = (LOTRFaction)var6.next();
            IChatComponent msg = new ChatComponentTranslation("chat.lotr.bountyPlaced", new Object[]{fac.factionName()});
            msg.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
            entityplayer.func_145747_a(msg);
         }

         this.bountiesPlaced.clear();
         this.markDirty();
      }

      this.setTimeSinceFT(this.ftSinceTick + 1);
      int seconds;
      if (this.targetFTWaypoint != null) {
         if (entityplayer.func_70608_bn()) {
            entityplayer.func_145747_a(new ChatComponentTranslation("lotr.fastTravel.inBed", new Object[0]));
            this.setTargetFTWaypoint((LOTRAbstractWaypoint)null);
         } else if (this.ticksUntilFT > 0) {
            seconds = this.ticksUntilFT / 20;
            if (this.ticksUntilFT == ticksUntilFT_max) {
               entityplayer.func_145747_a(new ChatComponentTranslation("lotr.fastTravel.travelTicksStart", new Object[]{seconds}));
            } else if (this.ticksUntilFT % 20 == 0 && seconds <= 5) {
               entityplayer.func_145747_a(new ChatComponentTranslation("lotr.fastTravel.travelTicks", new Object[]{seconds}));
            }

            --this.ticksUntilFT;
            this.setTicksUntilFT(this.ticksUntilFT);
         } else {
            this.sendFTBouncePacket(entityplayer);
         }
      } else {
         this.setTicksUntilFT(0);
      }

      this.lastOnlineTime = this.getCurrentOnlineTime();
      if (this.uuidToMount != null) {
         if (this.uuidToMountTime > 0) {
            --this.uuidToMountTime;
         } else {
            double range = 32.0D;
            List entities = world.func_72872_a(EntityLivingBase.class, entityplayer.field_70121_D.func_72314_b(range, range, range));
            Iterator var9 = entities.iterator();

            while(var9.hasNext()) {
               Object obj = var9.next();
               Entity entity = (Entity)obj;
               if (entity.func_110124_au().equals(this.uuidToMount)) {
                  entityplayer.func_70078_a(entity);
                  break;
               }
            }

            this.setUUIDToMount((UUID)null);
         }
      }

      if (this.pdTick % 24000 == 0 && this.alcoholTolerance > 0) {
         --this.alcoholTolerance;
         this.setAlcoholTolerance(this.alcoholTolerance);
      }

      this.unlockSharedCustomWaypoints(entityplayer);
      if (this.pdTick % 100 == 0 && world.field_73011_w instanceof LOTRWorldProvider) {
         seconds = MathHelper.func_76128_c(entityplayer.field_70165_t);
         int k = MathHelper.func_76128_c(entityplayer.field_70161_v);
         LOTRBiome biome = (LOTRBiome)world.field_73011_w.getBiomeGenForCoords(seconds, k);
         if (biome.biomeDimension == LOTRDimension.MIDDLE_EARTH) {
            LOTRBiome prevLastBiome = this.lastBiome;
            this.lastBiome = biome;
            if (prevLastBiome != biome) {
               this.markDirty();
            }
         }
      }

      if (this.adminHideMap) {
         boolean isOp = MinecraftServer.func_71276_C().func_71203_ab().func_152596_g(entityplayer.func_146103_bH());
         if (!entityplayer.field_71075_bZ.field_75098_d || !isOp) {
            this.setAdminHideMap(false);
            LOTRCommandAdminHideMap.notifyUnhidden(entityplayer);
         }
      }

      if (this.siegeActiveTime > 0) {
         --this.siegeActiveTime;
      }

   }

   private long getCurrentOnlineTime() {
      return MinecraftServer.func_71276_C().func_71218_a(0).func_82737_E();
   }

   public void updateFastTravelClockFromLastOnlineTime(EntityPlayerMP player, World world) {
      if (this.lastOnlineTime > 0L) {
         MinecraftServer server = MinecraftServer.func_71276_C();
         if (!server.func_71264_H()) {
            long currentOnlineTime = this.getCurrentOnlineTime();
            int diff = (int)(currentOnlineTime - this.lastOnlineTime);
            double offlineFactor = 0.1D;
            int ftClockIncrease = (int)((double)diff * offlineFactor);
            if (ftClockIncrease > 0) {
               this.setTimeSinceFTWithUpdate(this.ftSinceTick + ftClockIncrease);
               IChatComponent msg = new ChatComponentTranslation("chat.lotr.ft.offlineTick", new Object[]{LOTRLevelData.getHMSTime_Ticks(diff), LOTRLevelData.getHMSTime_Ticks(ftClockIncrease)});
               player.func_145747_a(msg);
            }
         }

      }
   }

   public float getAlignment(LOTRFaction faction) {
      if (faction.hasFixedAlignment) {
         return (float)faction.fixedAlignment;
      } else {
         Float alignment = (Float)this.alignments.get(faction);
         return alignment != null ? alignment : 0.0F;
      }
   }

   public void setAlignment(LOTRFaction faction, float alignment) {
      EntityPlayer entityplayer = this.getPlayer();
      if (faction.isPlayableAlignmentFaction()) {
         float prevAlignment = this.getAlignment(faction);
         this.alignments.put(faction, alignment);
         this.markDirty();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, entityplayer.field_70170_p);
         }

         this.checkAlignmentAchievements(faction, prevAlignment);
      }

      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K && this.pledgeFaction != null && !this.canPledgeTo(this.pledgeFaction)) {
         this.revokePledgeFaction(entityplayer, false);
      }

   }

   public LOTRAlignmentBonusMap addAlignment(EntityPlayer entityplayer, LOTRAlignmentValues.AlignmentBonus source, LOTRFaction faction, Entity entity) {
      return this.addAlignment(entityplayer, source, faction, (List)null, entity);
   }

   public LOTRAlignmentBonusMap addAlignment(EntityPlayer entityplayer, LOTRAlignmentValues.AlignmentBonus source, LOTRFaction faction, List forcedBonusFactions, Entity entity) {
      return this.addAlignment(entityplayer, source, faction, forcedBonusFactions, entity.field_70165_t, entity.field_70121_D.field_72338_b + (double)entity.field_70131_O * 0.7D, entity.field_70161_v);
   }

   public LOTRAlignmentBonusMap addAlignment(EntityPlayer entityplayer, LOTRAlignmentValues.AlignmentBonus source, LOTRFaction faction, double posX, double posY, double posZ) {
      return this.addAlignment(entityplayer, source, faction, (List)null, posX, posY, posZ);
   }

   public LOTRAlignmentBonusMap addAlignment(EntityPlayer entityplayer, LOTRAlignmentValues.AlignmentBonus source, LOTRFaction faction, List forcedBonusFactions, double posX, double posY, double posZ) {
      float bonus = source.bonus;
      LOTRAlignmentBonusMap factionBonusMap = new LOTRAlignmentBonusMap();
      float prevMainAlignment = this.getAlignment(faction);
      float conquestBonus = 0.0F;
      float notPledgedMultiplier = 0.5F;
      if (source.isKill) {
         List killBonuses = faction.getBonusesForKilling();
         Iterator var17 = killBonuses.iterator();

         label95:
         while(true) {
            LOTRFaction bonusFaction;
            float alignment;
            float factionBonus;
            do {
               do {
                  if (!var17.hasNext()) {
                     List killPenalties = faction.getPenaltiesForKilling();
                     Iterator var26 = killPenalties.iterator();

                     while(var26.hasNext()) {
                        LOTRFaction penaltyFaction = (LOTRFaction)var26.next();
                        if (penaltyFaction.isPlayableAlignmentFaction() && !source.killByHiredUnit) {
                           alignment = 0.0F;
                           if (penaltyFaction == faction) {
                              alignment = 1.0F;
                           } else {
                              alignment = penaltyFaction.getControlZoneAlignmentMultiplier(entityplayer);
                           }

                           if (alignment > 0.0F) {
                              factionBonus = this.getAlignment(penaltyFaction);
                              float factionPenalty = -Math.abs(bonus);
                              factionPenalty *= alignment;
                              factionPenalty = LOTRAlignmentValues.AlignmentBonus.scalePenalty(factionPenalty, factionBonus);
                              factionBonus += factionPenalty;
                              this.setAlignment(penaltyFaction, factionBonus);
                              factionBonusMap.put(penaltyFaction, factionPenalty);
                           }
                        }
                     }
                     break label95;
                  }

                  bonusFaction = (LOTRFaction)var17.next();
               } while(!bonusFaction.isPlayableAlignmentFaction());
            } while(!bonusFaction.approvesWarCrimes && source.isCivilianKill);

            float mplier;
            if (!source.killByHiredUnit) {
               mplier = 0.0F;
               if (forcedBonusFactions != null && forcedBonusFactions.contains(bonusFaction)) {
                  mplier = 1.0F;
               } else {
                  mplier = bonusFaction.getControlZoneAlignmentMultiplier(entityplayer);
               }

               if (mplier > 0.0F) {
                  alignment = this.getAlignment(bonusFaction);
                  factionBonus = Math.abs(bonus);
                  factionBonus *= mplier;
                  if (alignment >= bonusFaction.getPledgeAlignment() && !this.isPledgedTo(bonusFaction)) {
                     factionBonus *= 0.5F;
                  }

                  factionBonus = this.checkBonusForPledgeEnemyLimit(bonusFaction, factionBonus);
                  alignment += factionBonus;
                  this.setAlignment(bonusFaction, alignment);
                  factionBonusMap.put(bonusFaction, factionBonus);
               }
            }

            if (bonusFaction == this.getPledgeFaction()) {
               mplier = bonus;
               if (source.killByHiredUnit) {
                  mplier = bonus * 0.25F;
               }

               conquestBonus = LOTRConquestGrid.onConquestKill(entityplayer, bonusFaction, faction, mplier);
               this.getFactionData(bonusFaction).addConquest(Math.abs(conquestBonus));
            }
         }
      } else if (faction.isPlayableAlignmentFaction()) {
         float alignment = this.getAlignment(faction);
         float factionBonus = bonus;
         if (bonus > 0.0F && alignment >= faction.getPledgeAlignment() && !this.isPledgedTo(faction)) {
            factionBonus = bonus * 0.5F;
         }

         factionBonus = this.checkBonusForPledgeEnemyLimit(faction, factionBonus);
         alignment += factionBonus;
         this.setAlignment(faction, alignment);
         factionBonusMap.put(faction, factionBonus);
      }

      if (!factionBonusMap.isEmpty() || conquestBonus != 0.0F) {
         this.sendAlignmentBonusPacket(source, faction, prevMainAlignment, factionBonusMap, conquestBonus, posX, posY, posZ);
      }

      return factionBonusMap;
   }

   public void givePureConquestBonus(EntityPlayer entityplayer, LOTRFaction bonusFac, LOTRFaction enemyFac, float conq, String title, double posX, double posY, double posZ) {
      conq = LOTRConquestGrid.onConquestKill(entityplayer, bonusFac, enemyFac, conq);
      this.getFactionData(bonusFac).addConquest(Math.abs(conq));
      if (conq != 0.0F) {
         LOTRAlignmentValues.AlignmentBonus source = new LOTRAlignmentValues.AlignmentBonus(0.0F, title);
         LOTRPacketAlignmentBonus packet = new LOTRPacketAlignmentBonus(bonusFac, this.getAlignment(bonusFac), new LOTRAlignmentBonusMap(), conq, posX, posY, posZ, source);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   private void sendAlignmentBonusPacket(LOTRAlignmentValues.AlignmentBonus source, LOTRFaction faction, float prevMainAlignment, LOTRAlignmentBonusMap factionMap, float conqBonus, double posX, double posY, double posZ) {
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null) {
         LOTRPacketAlignmentBonus packet = new LOTRPacketAlignmentBonus(faction, prevMainAlignment, factionMap, conqBonus, posX, posY, posZ, source);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   public void setAlignmentFromCommand(LOTRFaction faction, float set) {
      this.setAlignment(faction, set);
   }

   public void addAlignmentFromCommand(LOTRFaction faction, float add) {
      float alignment = this.getAlignment(faction);
      alignment += add;
      this.setAlignment(faction, alignment);
   }

   private float checkBonusForPledgeEnemyLimit(LOTRFaction fac, float bonus) {
      if (this.isPledgeEnemyAlignmentLimited(fac)) {
         float alignment = this.getAlignment(fac);
         float limit = this.getPledgeEnemyAlignmentLimit(fac);
         if (alignment > limit) {
            bonus = 0.0F;
         } else if (alignment + bonus > limit) {
            bonus = limit - alignment;
         }
      }

      return bonus;
   }

   public boolean isPledgeEnemyAlignmentLimited(LOTRFaction fac) {
      return this.pledgeFaction != null && this.doesFactionPreventPledge(this.pledgeFaction, fac);
   }

   public float getPledgeEnemyAlignmentLimit(LOTRFaction fac) {
      return 0.0F;
   }

   private void checkAlignmentAchievements(LOTRFaction faction, float prevAlignment) {
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         float alignment = this.getAlignment(faction);
         faction.checkAlignmentAchievements(entityplayer, alignment);
      }

   }

   private void runAlignmentDraining(EntityPlayerMP entityplayer) {
      if (LOTRConfig.alignmentDrain && this.pdTick % 1000 == 0) {
         float drainLimit = 0.0F;
         List drainFactions = new ArrayList();
         List allFacs = LOTRFaction.getPlayableAlignmentFactions();
         Iterator var5 = allFacs.iterator();

         LOTRFaction fac1;
         while(var5.hasNext()) {
            fac1 = (LOTRFaction)var5.next();
            Iterator var7 = allFacs.iterator();

            while(var7.hasNext()) {
               LOTRFaction fac2 = (LOTRFaction)var7.next();
               if (this.doFactionsDrain(fac1, fac2)) {
                  float align1 = this.getAlignment(fac1);
                  float align2 = this.getAlignment(fac2);
                  if (align1 > 0.0F && align2 > 0.0F) {
                     if (!drainFactions.contains(fac1)) {
                        drainFactions.add(fac1);
                     }

                     if (!drainFactions.contains(fac2)) {
                        drainFactions.add(fac2);
                     }
                  }
               }
            }
         }

         if (!drainFactions.isEmpty()) {
            var5 = drainFactions.iterator();

            while(var5.hasNext()) {
               fac1 = (LOTRFaction)var5.next();
               float align = this.getAlignment(fac1);
               float alignLoss = 5.0F;
               alignLoss = Math.min(alignLoss, align - 0.0F);
               align -= alignLoss;
               this.setAlignment(fac1, align);
            }

            this.sendMessageIfNotReceived(LOTRGuiMessageTypes.ALIGN_DRAIN);
            LOTRPacketAlignDrain packet = new LOTRPacketAlignDrain(drainFactions.size());
            LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
         }
      }

   }

   public boolean doFactionsDrain(LOTRFaction fac1, LOTRFaction fac2) {
      return fac1.isMortalEnemy(fac2);
   }

   public LOTRFactionData getFactionData(LOTRFaction faction) {
      LOTRFactionData data = (LOTRFactionData)this.factionDataMap.get(faction);
      if (data == null) {
         data = new LOTRFactionData(this, faction);
         this.factionDataMap.put(faction, data);
      }

      return data;
   }

   public void updateFactionData(LOTRFaction faction, LOTRFactionData factionData) {
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         this.markDirty();
         NBTTagCompound nbt = new NBTTagCompound();
         factionData.save(nbt);
         LOTRPacketFactionData packet = new LOTRPacketFactionData(faction, nbt);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   public LOTRFaction getPledgeFaction() {
      return this.pledgeFaction;
   }

   public boolean isPledgedTo(LOTRFaction fac) {
      return this.pledgeFaction == fac;
   }

   public void setPledgeFaction(LOTRFaction fac) {
      this.pledgeFaction = fac;
      this.pledgeKillCooldown = 0;
      this.markDirty();
      if (fac != null) {
         this.checkAlignmentAchievements(fac, this.getAlignment(fac));
         this.addAchievement(LOTRAchievement.pledgeService);
      }

      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         if (fac != null) {
            World world = entityplayer.field_70170_p;
            world.func_72956_a(entityplayer, "lotr:event.pledge", 1.0F, 1.0F);
         }

         LOTRPacketPledge packet = new LOTRPacketPledge(fac);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   public boolean canPledgeTo(LOTRFaction fac) {
      if (!fac.isPlayableAlignmentFaction()) {
         return false;
      } else {
         return this.hasPledgeAlignment(fac) && this.getFactionsPreventingPledgeTo(fac).isEmpty();
      }
   }

   public boolean hasPledgeAlignment(LOTRFaction fac) {
      float alignment = this.getAlignment(fac);
      return alignment >= fac.getPledgeAlignment();
   }

   public List getFactionsPreventingPledgeTo(LOTRFaction fac) {
      List enemies = new ArrayList();
      LOTRFaction[] var3 = LOTRFaction.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LOTRFaction otherFac = var3[var5];
         if (otherFac.isPlayableAlignmentFaction() && this.doesFactionPreventPledge(fac, otherFac)) {
            float enemyAlign = this.getAlignment(otherFac);
            if (enemyAlign > 0.0F) {
               enemies.add(otherFac);
            }
         }
      }

      return enemies;
   }

   private boolean doesFactionPreventPledge(LOTRFaction pledgeFac, LOTRFaction otherFac) {
      return pledgeFac.isMortalEnemy(otherFac);
   }

   public boolean canMakeNewPledge() {
      return this.pledgeBreakCooldown <= 0;
   }

   public void revokePledgeFaction(EntityPlayer entityplayer, boolean intentional) {
      LOTRFaction wasPledge = this.pledgeFaction;
      float pledgeLvl = wasPledge.getPledgeAlignment();
      float prevAlign = this.getAlignment(wasPledge);
      float diff = prevAlign - pledgeLvl;
      float cd = diff / 5000.0F;
      cd = MathHelper.func_76131_a(cd, 0.0F, 1.0F);
      int cdTicks = '負';
      int cdTicks = cdTicks + Math.round(cd * 150.0F * 60.0F * 20.0F);
      this.setPledgeFaction((LOTRFaction)null);
      this.setBrokenPledgeFaction(wasPledge);
      this.setPledgeBreakCooldown(cdTicks);
      World world = entityplayer.field_70170_p;
      if (!world.field_72995_K) {
         LOTRFactionRank rank = wasPledge.getRank(prevAlign);
         LOTRFactionRank rankBelow = wasPledge.getRankBelow(rank);
         LOTRFactionRank rankBelow2 = wasPledge.getRankBelow(rankBelow);
         float newAlign = rankBelow2.alignment;
         newAlign = Math.max(newAlign, pledgeLvl / 2.0F);
         float alignPenalty = newAlign - prevAlign;
         if (alignPenalty < 0.0F) {
            LOTRAlignmentValues.AlignmentBonus penalty = LOTRAlignmentValues.createPledgePenalty(alignPenalty);
            double alignX = 0.0D;
            double alignY = 0.0D;
            double alignZ = 0.0D;
            double lookRange = 2.0D;
            Vec3 posEye = Vec3.func_72443_a(entityplayer.field_70165_t, entityplayer.field_70121_D.field_72338_b + (double)entityplayer.func_70047_e(), entityplayer.field_70161_v);
            Vec3 look = entityplayer.func_70676_i(1.0F);
            Vec3 posSight = posEye.func_72441_c(look.field_72450_a * lookRange, look.field_72448_b * lookRange, look.field_72449_c * lookRange);
            MovingObjectPosition mop = world.func_72933_a(posEye, posSight);
            if (mop != null && mop.field_72313_a == MovingObjectType.BLOCK) {
               alignX = (double)mop.field_72311_b + 0.5D;
               alignY = (double)mop.field_72312_c + 0.5D;
               alignZ = (double)mop.field_72309_d + 0.5D;
            } else {
               alignX = posSight.field_72450_a;
               alignY = posSight.field_72448_b;
               alignZ = posSight.field_72449_c;
            }

            this.addAlignment(entityplayer, penalty, wasPledge, alignX, alignY, alignZ);
         }

         world.func_72956_a(entityplayer, "lotr:event.unpledge", 1.0F, 1.0F);
         ChatComponentTranslation msg;
         if (intentional) {
            msg = new ChatComponentTranslation("chat.lotr.unpledge", new Object[]{wasPledge.factionName()});
            entityplayer.func_145747_a(msg);
         } else {
            msg = new ChatComponentTranslation("chat.lotr.autoUnpledge", new Object[]{wasPledge.factionName()});
            entityplayer.func_145747_a(msg);
         }

         this.checkAlignmentAchievements(wasPledge, prevAlign);
      }

   }

   public void onPledgeKill(EntityPlayer entityplayer) {
      this.pledgeKillCooldown += 24000;
      this.markDirty();
      if (this.pledgeKillCooldown > 24000) {
         this.revokePledgeFaction(entityplayer, false);
      } else if (this.pledgeFaction != null) {
         IChatComponent msg = new ChatComponentTranslation("chat.lotr.pledgeKillWarn", new Object[]{this.pledgeFaction.factionName()});
         entityplayer.func_145747_a(msg);
      }

   }

   public int getPledgeBreakCooldown() {
      return this.pledgeBreakCooldown;
   }

   public void setPledgeBreakCooldown(int i) {
      int preCD = this.pledgeBreakCooldown;
      LOTRFaction preBroken = this.brokenPledgeFaction;
      i = Math.max(0, i);
      this.pledgeBreakCooldown = i;
      boolean bigChange = (this.pledgeBreakCooldown == 0 || preCD == 0) && this.pledgeBreakCooldown != preCD;
      if (this.pledgeBreakCooldown > this.pledgeBreakCooldownStart) {
         this.setPledgeBreakCooldownStart(this.pledgeBreakCooldown);
         bigChange = true;
      }

      if (this.pledgeBreakCooldown <= 0 && preBroken != null) {
         this.setPledgeBreakCooldownStart(0);
         this.setBrokenPledgeFaction((LOTRFaction)null);
         bigChange = true;
      }

      if (bigChange || isTimerAutosaveTick()) {
         this.markDirty();
      }

      EntityPlayer entityplayer;
      if (bigChange || this.pledgeBreakCooldown % 5 == 0) {
         entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRPacketBrokenPledge packet = new LOTRPacketBrokenPledge(this.pledgeBreakCooldown, this.pledgeBreakCooldownStart, this.brokenPledgeFaction);
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }
      }

      if (this.pledgeBreakCooldown == 0 && preCD != this.pledgeBreakCooldown) {
         entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            String brokenName = preBroken == null ? StatCollector.func_74838_a("lotr.gui.factions.pledgeUnknown") : preBroken.factionName();
            IChatComponent msg = new ChatComponentTranslation("chat.lotr.pledgeBreakCooldown", new Object[]{brokenName});
            entityplayer.func_145747_a(msg);
         }
      }

   }

   public int getPledgeBreakCooldownStart() {
      return this.pledgeBreakCooldownStart;
   }

   public void setPledgeBreakCooldownStart(int i) {
      this.pledgeBreakCooldownStart = i;
      this.markDirty();
   }

   public LOTRFaction getBrokenPledgeFaction() {
      return this.brokenPledgeFaction;
   }

   public void setBrokenPledgeFaction(LOTRFaction f) {
      this.brokenPledgeFaction = f;
      this.markDirty();
   }

   public List getAchievements() {
      return this.achievements;
   }

   public List getEarnedAchievements(LOTRDimension dimension) {
      List earnedAchievements = new ArrayList();
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null) {
         Iterator it = this.achievements.iterator();

         while(it.hasNext()) {
            LOTRAchievement achievement = (LOTRAchievement)it.next();
            if (achievement.getDimension() == dimension && achievement.canPlayerEarn(entityplayer)) {
               earnedAchievements.add(achievement);
            }
         }
      }

      return earnedAchievements;
   }

   public boolean hasAchievement(LOTRAchievement achievement) {
      Iterator var2 = this.achievements.iterator();

      LOTRAchievement a;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         a = (LOTRAchievement)var2.next();
      } while(a.category != achievement.category || a.ID != achievement.ID);

      return true;
   }

   public void addAchievement(LOTRAchievement achievement) {
      if (!this.hasAchievement(achievement)) {
         if (!this.isSiegeActive()) {
            this.achievements.add(achievement);
            this.markDirty();
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
               boolean canEarn = achievement.canPlayerEarn(entityplayer);
               this.sendAchievementPacket((EntityPlayerMP)entityplayer, achievement, canEarn);
               if (canEarn) {
                  achievement.broadcastEarning(entityplayer);
                  List earnedAchievements = this.getEarnedAchievements(LOTRDimension.MIDDLE_EARTH);
                  int biomes = 0;

                  for(int i = 0; i < earnedAchievements.size(); ++i) {
                     LOTRAchievement earnedAchievement = (LOTRAchievement)earnedAchievements.get(i);
                     if (earnedAchievement.isBiomeAchievement) {
                        ++biomes;
                     }
                  }

                  if (biomes >= 10) {
                     this.addAchievement(LOTRAchievement.travel10);
                  }

                  if (biomes >= 20) {
                     this.addAchievement(LOTRAchievement.travel20);
                  }

                  if (biomes >= 30) {
                     this.addAchievement(LOTRAchievement.travel30);
                  }

                  if (biomes >= 40) {
                     this.addAchievement(LOTRAchievement.travel40);
                  }

                  if (biomes >= 50) {
                     this.addAchievement(LOTRAchievement.travel50);
                  }
               }
            }

         }
      }
   }

   public void removeAchievement(LOTRAchievement achievement) {
      if (this.hasAchievement(achievement)) {
         if (this.achievements.remove(achievement)) {
            this.markDirty();
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
               this.sendAchievementRemovePacket((EntityPlayerMP)entityplayer, achievement);
            }
         }

      }
   }

   private void runAchievementChecks(EntityPlayer entityplayer, World world) {
      int i = MathHelper.func_76128_c(entityplayer.field_70165_t);
      int j = MathHelper.func_76128_c(entityplayer.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(entityplayer.field_70161_v);
      BiomeGenBase biome = world.func_72807_a(i, k);
      if (biome instanceof LOTRBiome) {
         LOTRBiome lotrbiome = (LOTRBiome)biome;
         LOTRAchievement ach = lotrbiome.getBiomeAchievement();
         if (ach != null) {
            this.addAchievement(ach);
         }

         LOTRWaypoint.Region biomeRegion = lotrbiome.getBiomeWaypoints();
         if (biomeRegion != null) {
            this.unlockFTRegion(biomeRegion);
         }
      }

      if (entityplayer.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) {
         this.addAchievement(LOTRAchievement.enterMiddleEarth);
      }

      if (entityplayer.field_71093_bK == LOTRDimension.UTUMNO.dimensionID) {
         this.addAchievement(LOTRAchievement.enterUtumnoIce);
         int y = MathHelper.func_76128_c(entityplayer.field_70121_D.field_72338_b);
         LOTRUtumnoLevel level = LOTRUtumnoLevel.forY(y);
         if (level == LOTRUtumnoLevel.OBSIDIAN) {
            this.addAchievement(LOTRAchievement.enterUtumnoObsidian);
         } else if (level == LOTRUtumnoLevel.FIRE) {
            this.addAchievement(LOTRAchievement.enterUtumnoFire);
         }
      }

      if (entityplayer.field_71071_by.func_146028_b(LOTRMod.pouch)) {
         this.addAchievement(LOTRAchievement.getPouch);
      }

      Set tables = new HashSet();
      int crossbowBolts = 0;
      ItemStack[] var18 = entityplayer.field_71071_by.field_70462_a;
      int var10 = var18.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         ItemStack item = var18[var11];
         if (item != null && item.func_77973_b() instanceof ItemBlock) {
            Block block = Block.func_149634_a(item.func_77973_b());
            if (block instanceof LOTRBlockCraftingTable) {
               tables.add(block);
            }
         }

         if (item != null && item.func_77973_b() instanceof LOTRItemCrossbowBolt) {
            crossbowBolts += item.field_77994_a;
         }
      }

      if (tables.size() >= 10) {
         this.addAchievement(LOTRAchievement.collectCraftingTables);
      }

      if (crossbowBolts >= 128) {
         this.addAchievement(LOTRAchievement.collectCrossbowBolts);
      }

      if (!this.hasAchievement(LOTRAchievement.hundreds) && this.pdTick % 20 == 0) {
         int hiredUnits = 0;
         List nearbyNPCs = world.func_72872_a(LOTREntityNPC.class, entityplayer.field_70121_D.func_72314_b(64.0D, 64.0D, 64.0D));
         Iterator var22 = nearbyNPCs.iterator();

         while(var22.hasNext()) {
            LOTREntityNPC npc = (LOTREntityNPC)var22.next();
            if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
               ++hiredUnits;
            }
         }

         if (hiredUnits >= 100) {
            this.addAchievement(LOTRAchievement.hundreds);
         }
      }

      if (biome instanceof LOTRBiomeGenMistyMountains && entityplayer.field_70163_u > 192.0D) {
         this.addAchievement(LOTRAchievement.climbMistyMountains);
      }

      LOTRMaterial fullMaterial = this.isPlayerWearingFull(entityplayer);
      if (fullMaterial != null) {
         if (fullMaterial == LOTRMaterial.MITHRIL) {
            this.addAchievement(LOTRAchievement.wearFullMithril);
         } else if (fullMaterial == LOTRMaterial.FUR) {
            this.addAchievement(LOTRAchievement.wearFullFur);
         } else if (fullMaterial == LOTRMaterial.BLUE_DWARVEN) {
            this.addAchievement(LOTRAchievement.wearFullBlueDwarven);
         } else if (fullMaterial == LOTRMaterial.HIGH_ELVEN) {
            this.addAchievement(LOTRAchievement.wearFullHighElven);
         } else if (fullMaterial == LOTRMaterial.GONDOLIN) {
            this.addAchievement(LOTRAchievement.wearFullGondolin);
         } else if (fullMaterial == LOTRMaterial.GALVORN) {
            this.addAchievement(LOTRAchievement.wearFullGalvorn);
         } else if (fullMaterial == LOTRMaterial.RANGER) {
            this.addAchievement(LOTRAchievement.wearFullRanger);
         } else if (fullMaterial == LOTRMaterial.GUNDABAD_URUK) {
            this.addAchievement(LOTRAchievement.wearFullGundabadUruk);
         } else if (fullMaterial == LOTRMaterial.ARNOR) {
            this.addAchievement(LOTRAchievement.wearFullArnor);
         } else if (fullMaterial == LOTRMaterial.RIVENDELL) {
            this.addAchievement(LOTRAchievement.wearFullRivendell);
         } else if (fullMaterial == LOTRMaterial.ANGMAR) {
            this.addAchievement(LOTRAchievement.wearFullAngmar);
         } else if (fullMaterial == LOTRMaterial.WOOD_ELVEN_SCOUT) {
            this.addAchievement(LOTRAchievement.wearFullWoodElvenScout);
         } else if (fullMaterial == LOTRMaterial.WOOD_ELVEN) {
            this.addAchievement(LOTRAchievement.wearFullWoodElven);
         } else if (fullMaterial == LOTRMaterial.DOL_GULDUR) {
            this.addAchievement(LOTRAchievement.wearFullDolGuldur);
         } else if (fullMaterial == LOTRMaterial.DALE) {
            this.addAchievement(LOTRAchievement.wearFullDale);
         } else if (fullMaterial == LOTRMaterial.DWARVEN) {
            this.addAchievement(LOTRAchievement.wearFullDwarven);
         } else if (fullMaterial == LOTRMaterial.GALADHRIM) {
            this.addAchievement(LOTRAchievement.wearFullElven);
         } else if (fullMaterial == LOTRMaterial.HITHLAIN) {
            this.addAchievement(LOTRAchievement.wearFullHithlain);
         } else if (fullMaterial == LOTRMaterial.URUK) {
            this.addAchievement(LOTRAchievement.wearFullUruk);
         } else if (fullMaterial == LOTRMaterial.ROHAN) {
            this.addAchievement(LOTRAchievement.wearFullRohirric);
         } else if (fullMaterial == LOTRMaterial.ROHAN_MARSHAL) {
            this.addAchievement(LOTRAchievement.wearFullRohirricMarshal);
         } else if (fullMaterial == LOTRMaterial.DUNLENDING) {
            this.addAchievement(LOTRAchievement.wearFullDunlending);
         } else if (fullMaterial == LOTRMaterial.GONDOR) {
            this.addAchievement(LOTRAchievement.wearFullGondorian);
         } else if (fullMaterial == LOTRMaterial.DOL_AMROTH) {
            this.addAchievement(LOTRAchievement.wearFullDolAmroth);
         } else if (fullMaterial == LOTRMaterial.RANGER_ITHILIEN) {
            this.addAchievement(LOTRAchievement.wearFullRangerIthilien);
         } else if (fullMaterial == LOTRMaterial.LOSSARNACH) {
            this.addAchievement(LOTRAchievement.wearFullLossarnach);
         } else if (fullMaterial == LOTRMaterial.PELARGIR) {
            this.addAchievement(LOTRAchievement.wearFullPelargir);
         } else if (fullMaterial == LOTRMaterial.PINNATH_GELIN) {
            this.addAchievement(LOTRAchievement.wearFullPinnathGelin);
         } else if (fullMaterial == LOTRMaterial.BLACKROOT) {
            this.addAchievement(LOTRAchievement.wearFullBlackroot);
         } else if (fullMaterial == LOTRMaterial.LAMEDON) {
            this.addAchievement(LOTRAchievement.wearFullLamedon);
         } else if (fullMaterial == LOTRMaterial.MORDOR) {
            this.addAchievement(LOTRAchievement.wearFullOrc);
         } else if (fullMaterial == LOTRMaterial.MORGUL) {
            this.addAchievement(LOTRAchievement.wearFullMorgul);
         } else if (fullMaterial == LOTRMaterial.BLACK_URUK) {
            this.addAchievement(LOTRAchievement.wearFullBlackUruk);
         } else if (fullMaterial == LOTRMaterial.DORWINION) {
            this.addAchievement(LOTRAchievement.wearFullDorwinion);
         } else if (fullMaterial == LOTRMaterial.DORWINION_ELF) {
            this.addAchievement(LOTRAchievement.wearFullDorwinionElf);
         } else if (fullMaterial == LOTRMaterial.RHUN) {
            this.addAchievement(LOTRAchievement.wearFullRhun);
         } else if (fullMaterial == LOTRMaterial.RHUN_GOLD) {
            this.addAchievement(LOTRAchievement.wearFullRhunGold);
         } else if (fullMaterial == LOTRMaterial.NEAR_HARAD) {
            this.addAchievement(LOTRAchievement.wearFullNearHarad);
         } else if (fullMaterial == LOTRMaterial.GULF_HARAD) {
            this.addAchievement(LOTRAchievement.wearFullGulfHarad);
         } else if (fullMaterial == LOTRMaterial.CORSAIR) {
            this.addAchievement(LOTRAchievement.wearFullCorsair);
         } else if (fullMaterial == LOTRMaterial.UMBAR) {
            this.addAchievement(LOTRAchievement.wearFullUmbar);
         } else if (fullMaterial == LOTRMaterial.HARNEDOR) {
            this.addAchievement(LOTRAchievement.wearFullHarnedor);
         } else if (fullMaterial == LOTRMaterial.HARAD_NOMAD) {
            this.addAchievement(LOTRAchievement.wearFullNomad);
         } else if (fullMaterial == LOTRMaterial.BLACK_NUMENOREAN) {
            this.addAchievement(LOTRAchievement.wearFullBlackNumenorean);
         } else if (fullMaterial == LOTRMaterial.MOREDAIN) {
            this.addAchievement(LOTRAchievement.wearFullMoredain);
         } else if (fullMaterial == LOTRMaterial.TAUREDAIN) {
            this.addAchievement(LOTRAchievement.wearFullTauredain);
         } else if (fullMaterial == LOTRMaterial.TAUREDAIN_GOLD) {
            this.addAchievement(LOTRAchievement.wearFullTaurethrimGold);
         } else if (fullMaterial == LOTRMaterial.HALF_TROLL) {
            this.addAchievement(LOTRAchievement.wearFullHalfTroll);
         } else if (fullMaterial == LOTRMaterial.UTUMNO) {
            this.addAchievement(LOTRAchievement.wearFullUtumno);
         }
      }

   }

   public LOTRMaterial isPlayerWearingFull(EntityPlayer entityplayer) {
      LOTRMaterial fullMaterial = null;
      ItemStack[] var3 = entityplayer.field_71071_by.field_70460_b;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ItemStack itemstack = var3[var5];
         if (itemstack == null || !(itemstack.func_77973_b() instanceof LOTRItemArmor)) {
            return null;
         }

         LOTRItemArmor armor = (LOTRItemArmor)itemstack.func_77973_b();
         LOTRMaterial thisMaterial = armor.getLOTRArmorMaterial();
         if (fullMaterial == null) {
            fullMaterial = thisMaterial;
         } else if (fullMaterial != thisMaterial) {
            return null;
         }
      }

      return fullMaterial;
   }

   private void sendAchievementPacket(EntityPlayerMP entityplayer, LOTRAchievement achievement, boolean display) {
      LOTRPacketAchievement packet = new LOTRPacketAchievement(achievement, display);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   private void sendAchievementRemovePacket(EntityPlayerMP entityplayer, LOTRAchievement achievement) {
      LOTRPacketAchievementRemove packet = new LOTRPacketAchievementRemove(achievement);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   public void setShield(LOTRShields lotrshield) {
      this.shield = lotrshield;
      this.markDirty();
   }

   public LOTRShields getShield() {
      return this.shield;
   }

   public void setStructuresBanned(boolean flag) {
      this.structuresBanned = flag;
      this.markDirty();
   }

   public boolean getStructuresBanned() {
      return this.structuresBanned;
   }

   private void sendOptionsPacket(int option, boolean flag) {
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketOptions packet = new LOTRPacketOptions(option, flag);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   public boolean getFriendlyFire() {
      return this.friendlyFire;
   }

   public void setFriendlyFire(boolean flag) {
      this.friendlyFire = flag;
      this.markDirty();
      this.sendOptionsPacket(0, flag);
   }

   public boolean getEnableHiredDeathMessages() {
      return this.hiredDeathMessages;
   }

   public void setEnableHiredDeathMessages(boolean flag) {
      this.hiredDeathMessages = flag;
      this.markDirty();
      this.sendOptionsPacket(1, flag);
   }

   public boolean getHideAlignment() {
      return this.hideAlignment;
   }

   public void setHideAlignment(boolean flag) {
      this.hideAlignment = flag;
      this.markDirty();
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, entityplayer.field_70170_p);
      }

   }

   private void sendFTBouncePacket(EntityPlayerMP entityplayer) {
      LOTRPacketFTBounceClient packet = new LOTRPacketFTBounceClient();
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   public void receiveFTBouncePacket() {
      if (this.targetFTWaypoint != null && this.ticksUntilFT <= 0) {
         this.fastTravelTo(this.targetFTWaypoint);
         this.setTargetFTWaypoint((LOTRAbstractWaypoint)null);
      }

   }

   private void fastTravelTo(LOTRAbstractWaypoint waypoint) {
      EntityPlayer player = this.getPlayer();
      if (player instanceof EntityPlayerMP) {
         EntityPlayerMP entityplayer = (EntityPlayerMP)player;
         WorldServer world = (WorldServer)entityplayer.field_70170_p;
         int startX = MathHelper.func_76128_c(entityplayer.field_70165_t);
         int startZ = MathHelper.func_76128_c(entityplayer.field_70161_v);
         double range = 256.0D;
         List entities = world.func_72872_a(EntityLiving.class, entityplayer.field_70121_D.func_72314_b(range, range, range));
         Set entitiesToTransport = new HashSet();
         Iterator var11 = entities.iterator();

         while(true) {
            while(var11.hasNext()) {
               EntityLiving entity = (EntityLiving)var11.next();
               if (entity instanceof LOTREntityNPC) {
                  LOTREntityNPC npc = (LOTREntityNPC)entity;
                  if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.shouldFollowPlayer()) {
                     entitiesToTransport.add(npc);
                     continue;
                  }

                  if (npc instanceof LOTREntityGollum) {
                     LOTREntityGollum gollum = (LOTREntityGollum)npc;
                     if (gollum.getGollumOwner() == entityplayer && !gollum.isGollumSitting()) {
                        entitiesToTransport.add(gollum);
                        continue;
                     }
                  }
               }

               if (entity instanceof EntityTameable) {
                  EntityTameable pet = (EntityTameable)entity;
                  if (pet.func_70902_q() == entityplayer && !pet.func_70906_o()) {
                     entitiesToTransport.add(pet);
                     continue;
                  }
               }

               if (entity.func_110167_bD() && entity.func_110166_bE() == entityplayer) {
                  entitiesToTransport.add(entity);
               }
            }

            Set removes = new HashSet();
            Iterator var20 = entitiesToTransport.iterator();

            while(var20.hasNext()) {
               EntityLiving entity = (EntityLiving)var20.next();
               Entity rider = entity.field_70153_n;
               if (rider != null && entitiesToTransport.contains(rider)) {
                  removes.add(entity);
               }
            }

            entitiesToTransport.removeAll(removes);
            int i = waypoint.getXCoord();
            int k = waypoint.getZCoord();
            world.field_73059_b.func_73154_d(i >> 4, k >> 4);
            int j = waypoint.getYCoord(world, i, k);
            Entity playerMount = entityplayer.field_70154_o;
            entityplayer.func_70078_a((Entity)null);
            entityplayer.func_70634_a((double)i + 0.5D, (double)j, (double)k + 0.5D);
            entityplayer.field_70143_R = 0.0F;
            if (playerMount instanceof EntityLiving) {
               playerMount = this.fastTravelEntity(world, (EntityLiving)playerMount, i, j, k);
            }

            if (playerMount != null) {
               this.setUUIDToMount(((Entity)playerMount).func_110124_au());
            }

            Iterator var16 = entitiesToTransport.iterator();

            while(var16.hasNext()) {
               EntityLiving entity = (EntityLiving)var16.next();
               Entity mount = entity.field_70154_o;
               entity.func_70078_a((Entity)null);
               entity = this.fastTravelEntity(world, entity, i, j, k);
               if (mount instanceof EntityLiving) {
                  Entity mount = this.fastTravelEntity(world, (EntityLiving)mount, i, j, k);
                  entity.func_70078_a(mount);
               }
            }

            this.sendFTPacket(entityplayer, waypoint, startX, startZ);
            this.setTimeSinceFTWithUpdate(0);
            this.incrementWPUseCount(waypoint);
            if (waypoint instanceof LOTRWaypoint) {
               this.lastWaypoint = (LOTRWaypoint)waypoint;
               this.markDirty();
            }

            if (waypoint instanceof LOTRCustomWaypoint) {
               LOTRCustomWaypointLogger.logTravel(entityplayer, (LOTRCustomWaypoint)waypoint);
            }
            break;
         }
      }

   }

   private void sendFTPacket(EntityPlayerMP entityplayer, LOTRAbstractWaypoint waypoint, int startX, int startZ) {
      LOTRPacketFTScreen packet = new LOTRPacketFTScreen(waypoint, startX, startZ);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   private EntityLiving fastTravelEntity(WorldServer world, EntityLiving entity, int i, int j, int k) {
      String entityID = EntityList.func_75621_b(entity);
      NBTTagCompound nbt = new NBTTagCompound();
      entity.func_70109_d(nbt);
      entity.func_70106_y();
      EntityLiving newEntity = (EntityLiving)EntityList.func_75620_a(entityID, world);
      newEntity.func_70020_e(nbt);
      newEntity.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, newEntity.field_70177_z, newEntity.field_70125_A);
      newEntity.field_70143_R = 0.0F;
      newEntity.func_70661_as().func_75499_g();
      newEntity.func_70624_b((EntityLivingBase)null);
      world.func_72838_d(newEntity);
      world.func_72866_a(newEntity, false);
      return newEntity;
   }

   public boolean canFastTravel() {
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer == null) {
         return false;
      } else {
         World world = entityplayer.field_70170_p;
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            double range = 16.0D;
            List entities = world.func_72872_a(EntityLiving.class, entityplayer.field_70121_D.func_72314_b(range, range, range));

            for(int l = 0; l < entities.size(); ++l) {
               EntityLiving entityliving = (EntityLiving)entities.get(l);
               if (entityliving.func_70638_az() == entityplayer) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public int getTimeSinceFT() {
      return this.ftSinceTick;
   }

   public void setTimeSinceFT(int i) {
      this.setTimeSinceFT(i, false);
   }

   public void setTimeSinceFTWithUpdate(int i) {
      this.setTimeSinceFT(i, true);
   }

   private void setTimeSinceFT(int i, boolean forceUpdate) {
      int preTick = this.ftSinceTick;
      i = Math.max(0, i);
      this.ftSinceTick = i;
      boolean bigChange = (this.ftSinceTick == 0 || preTick == 0) && this.ftSinceTick != preTick || preTick < 0 && this.ftSinceTick >= 0;
      if (bigChange || isTimerAutosaveTick() || forceUpdate) {
         this.markDirty();
      }

      if (bigChange || this.ftSinceTick % 5 == 0 || forceUpdate) {
         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRPacketFTTimer packet = new LOTRPacketFTTimer(this.ftSinceTick);
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }
      }

   }

   private void setUUIDToMount(UUID uuid) {
      this.uuidToMount = uuid;
      if (this.uuidToMount != null) {
         this.uuidToMountTime = 10;
      } else {
         this.uuidToMountTime = 0;
      }

      this.markDirty();
   }

   public LOTRAbstractWaypoint getTargetFTWaypoint() {
      return this.targetFTWaypoint;
   }

   public void setTargetFTWaypoint(LOTRAbstractWaypoint wp) {
      this.targetFTWaypoint = wp;
      this.markDirty();
      if (wp != null) {
         this.setTicksUntilFT(ticksUntilFT_max);
      } else {
         this.setTicksUntilFT(0);
      }

   }

   public int getTicksUntilFT() {
      return this.ticksUntilFT;
   }

   public void setTicksUntilFT(int i) {
      if (this.ticksUntilFT != i) {
         this.ticksUntilFT = i;
         if (this.ticksUntilFT == ticksUntilFT_max || this.ticksUntilFT == 0) {
            this.markDirty();
         }
      }

   }

   public void cancelFastTravel() {
      if (this.targetFTWaypoint != null) {
         this.setTargetFTWaypoint((LOTRAbstractWaypoint)null);
         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            entityplayer.func_145747_a(new ChatComponentTranslation("lotr.fastTravel.motion", new Object[0]));
         }
      }

   }

   public boolean isFTRegionUnlocked(LOTRWaypoint.Region region) {
      return this.unlockedFTRegions.contains(region);
   }

   public void unlockFTRegion(LOTRWaypoint.Region region) {
      if (!this.isSiegeActive()) {
         if (this.unlockedFTRegions.add(region)) {
            this.markDirty();
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
               LOTRPacketWaypointRegion packet = new LOTRPacketWaypointRegion(region, true);
               LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
            }
         }

      }
   }

   public void lockFTRegion(LOTRWaypoint.Region region) {
      if (this.unlockedFTRegions.remove(region)) {
         this.markDirty();
         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRPacketWaypointRegion packet = new LOTRPacketWaypointRegion(region, false);
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }
      }

   }

   public List getAllAvailableWaypoints() {
      List waypoints = new ArrayList();
      waypoints.addAll(LOTRWaypoint.listAllWaypoints());
      waypoints.addAll(this.getCustomWaypoints());
      waypoints.addAll(this.customWaypointsShared);
      return waypoints;
   }

   public List getCustomWaypoints() {
      return this.customWaypoints;
   }

   public LOTRCustomWaypoint getCustomWaypointByID(int ID) {
      Iterator var2 = this.customWaypoints.iterator();

      LOTRCustomWaypoint waypoint;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         waypoint = (LOTRCustomWaypoint)var2.next();
      } while(waypoint.getID() != ID);

      return waypoint;
   }

   public void addCustomWaypoint(LOTRCustomWaypoint waypoint) {
      this.customWaypoints.add(waypoint);
      this.markDirty();
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketCreateCWPClient packet = waypoint.getClientPacket();
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         LOTRCustomWaypointLogger.logCreate(entityplayer, waypoint);
      }

      LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
      List sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
      Iterator var5 = sharedPlayers.iterator();

      while(var5.hasNext()) {
         UUID sharedPlayerUUID = (UUID)var5.next();
         EntityPlayer sharedPlayer = this.getOtherPlayer(sharedPlayerUUID);
         if (sharedPlayer != null && !sharedPlayer.field_70170_p.field_72995_K) {
            LOTRLevelData.getData(sharedPlayerUUID).addOrUpdateSharedCustomWaypoint(shareCopy);
         }
      }

   }

   public void addCustomWaypointClient(LOTRCustomWaypoint waypoint) {
      this.customWaypoints.add(waypoint);
   }

   public void removeCustomWaypoint(LOTRCustomWaypoint waypoint) {
      if (this.customWaypoints.remove(waypoint)) {
         this.markDirty();
         Iterator var2 = waypoint.getSharedFellowshipIDs().iterator();

         while(var2.hasNext()) {
            UUID fsID = (UUID)var2.next();
            LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(fsID);
            if (fs != null) {
               this.checkIfStillWaypointSharerForFellowship(fs);
            }
         }

         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRPacketDeleteCWPClient packet = waypoint.getClientDeletePacket();
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
            LOTRCustomWaypointLogger.logDelete(entityplayer, waypoint);
         }

         LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
         List sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
         Iterator var5 = sharedPlayers.iterator();

         while(var5.hasNext()) {
            UUID sharedPlayerUUID = (UUID)var5.next();
            EntityPlayer sharedPlayer = this.getOtherPlayer(sharedPlayerUUID);
            if (sharedPlayer != null && !sharedPlayer.field_70170_p.field_72995_K) {
               LOTRLevelData.getData(sharedPlayerUUID).removeSharedCustomWaypoint(shareCopy);
            }
         }
      }

   }

   public void removeCustomWaypointClient(LOTRCustomWaypoint waypoint) {
      this.customWaypoints.remove(waypoint);
   }

   public void renameCustomWaypoint(LOTRCustomWaypoint waypoint, String newName) {
      waypoint.rename(newName);
      this.markDirty();
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketRenameCWPClient packet = waypoint.getClientRenamePacket();
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         LOTRCustomWaypointLogger.logRename(entityplayer, waypoint);
      }

      LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
      List sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
      Iterator var6 = sharedPlayers.iterator();

      while(var6.hasNext()) {
         UUID sharedPlayerUUID = (UUID)var6.next();
         EntityPlayer sharedPlayer = this.getOtherPlayer(sharedPlayerUUID);
         if (sharedPlayer != null && !sharedPlayer.field_70170_p.field_72995_K) {
            LOTRLevelData.getData(sharedPlayerUUID).renameSharedCustomWaypoint(shareCopy, newName);
         }
      }

   }

   public void renameCustomWaypointClient(LOTRCustomWaypoint waypoint, String newName) {
      waypoint.rename(newName);
   }

   public void customWaypointAddSharedFellowship(LOTRCustomWaypoint waypoint, LOTRFellowship fs) {
      UUID fsID = fs.getFellowshipID();
      waypoint.addSharedFellowship(fsID);
      this.markDirty();
      fs.markIsWaypointSharer(this.playerUUID, true);
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketShareCWPClient packet = waypoint.getClientAddFellowshipPacket(fsID);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

      LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
      Iterator var6 = fs.getAllPlayerUUIDs().iterator();

      while(var6.hasNext()) {
         UUID player = (UUID)var6.next();
         if (!player.equals(this.playerUUID)) {
            LOTRLevelData.getData(player).addOrUpdateSharedCustomWaypoint(shareCopy);
         }
      }

   }

   public void customWaypointAddSharedFellowshipClient(LOTRCustomWaypoint waypoint, LOTRFellowshipClient fs) {
      waypoint.addSharedFellowship(fs.getFellowshipID());
   }

   public void customWaypointRemoveSharedFellowship(LOTRCustomWaypoint waypoint, LOTRFellowship fs) {
      UUID fsID = fs.getFellowshipID();
      waypoint.removeSharedFellowship(fsID);
      this.markDirty();
      this.checkIfStillWaypointSharerForFellowship(fs);
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketShareCWPClient packet = waypoint.getClientRemoveFellowshipPacket(fsID);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

      LOTRCustomWaypoint shareCopy = waypoint.createCopyOfShared(this.playerUUID);
      Iterator var6 = fs.getAllPlayerUUIDs().iterator();

      while(var6.hasNext()) {
         UUID player = (UUID)var6.next();
         if (!player.equals(this.playerUUID)) {
            LOTRPlayerData pd = LOTRLevelData.getData(player);
            pd.addOrUpdateSharedCustomWaypoint(shareCopy);
            pd.checkCustomWaypointsSharedBy(ImmutableList.of(this.playerUUID));
         }
      }

   }

   public void checkIfStillWaypointSharerForFellowship(LOTRFellowship fs) {
      if (!this.hasAnyWaypointsSharedToFellowship(fs)) {
         fs.markIsWaypointSharer(this.playerUUID, false);
      }

   }

   public boolean hasAnyWaypointsSharedToFellowship(LOTRFellowship fs) {
      Iterator var2 = this.customWaypoints.iterator();

      LOTRCustomWaypoint waypoint;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         waypoint = (LOTRCustomWaypoint)var2.next();
      } while(!waypoint.hasSharedFellowship(fs));

      return true;
   }

   public void customWaypointRemoveSharedFellowshipClient(LOTRCustomWaypoint waypoint, UUID fsID) {
      waypoint.removeSharedFellowship(fsID);
   }

   public int getMaxCustomWaypoints() {
      int achievements = this.getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size();
      return 5 + achievements / 5;
   }

   public LOTRCustomWaypoint getSharedCustomWaypointByID(UUID owner, int id) {
      Iterator var3 = this.customWaypointsShared.iterator();

      LOTRCustomWaypoint waypoint;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         waypoint = (LOTRCustomWaypoint)var3.next();
      } while(!waypoint.getSharingPlayerID().equals(owner) || waypoint.getID() != id);

      return waypoint;
   }

   public void addOrUpdateSharedCustomWaypoint(LOTRCustomWaypoint waypoint) {
      if (!waypoint.isShared()) {
         FMLLog.warning("LOTR: Warning! Tried to cache a shared custom waypoint with no owner!", new Object[0]);
      } else if (waypoint.getSharingPlayerID().equals(this.playerUUID)) {
         FMLLog.warning("LOTR: Warning! Tried to share a custom waypoint to its own player (%s)!", new Object[]{this.playerUUID.toString()});
      } else {
         LOTRPlayerData.CWPSharedKey key = LOTRPlayerData.CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
         if (this.cwpSharedUnlocked.contains(key)) {
            waypoint.setSharedUnlocked();
         }

         waypoint.setSharedHidden(this.cwpSharedHidden.contains(key));
         LOTRCustomWaypoint existing = this.getSharedCustomWaypointByID(waypoint.getSharingPlayerID(), waypoint.getID());
         if (existing == null) {
            this.customWaypointsShared.add(waypoint);
         } else {
            if (existing.isSharedUnlocked()) {
               waypoint.setSharedUnlocked();
            }

            waypoint.setSharedHidden(existing.isSharedHidden());
            int i = this.customWaypointsShared.indexOf(existing);
            this.customWaypointsShared.set(i, waypoint);
         }

         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRPacketCreateCWPClient packet = waypoint.getClientPacketShared();
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }

      }
   }

   public void removeSharedCustomWaypoint(LOTRCustomWaypoint waypoint) {
      if (!waypoint.isShared()) {
         FMLLog.warning("LOTR: Warning! Tried to remove a shared custom waypoint with no owner!", new Object[0]);
      } else {
         LOTRCustomWaypoint existing = null;
         if (this.customWaypointsShared.contains(waypoint)) {
            existing = waypoint;
         } else {
            existing = this.getSharedCustomWaypointByID(waypoint.getSharingPlayerID(), waypoint.getID());
         }

         if (existing != null) {
            this.customWaypointsShared.remove(existing);
            EntityPlayer entityplayer = this.getPlayer();
            if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
               LOTRPacketDeleteCWPClient packet = waypoint.getClientDeletePacketShared();
               LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
            }
         } else {
            FMLLog.warning("LOTR: Warning! Tried to remove a shared custom waypoint that does not exist!", new Object[0]);
         }

      }
   }

   public void renameSharedCustomWaypoint(LOTRCustomWaypoint waypoint, String newName) {
      if (!waypoint.isShared()) {
         FMLLog.warning("LOTR: Warning! Tried to rename a shared custom waypoint with no owner!", new Object[0]);
      } else {
         waypoint.rename(newName);
         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRPacketRenameCWPClient packet = waypoint.getClientRenamePacketShared();
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }

      }
   }

   public void unlockSharedCustomWaypoint(LOTRCustomWaypoint waypoint) {
      if (!waypoint.isShared()) {
         FMLLog.warning("LOTR: Warning! Tried to unlock a shared custom waypoint with no owner!", new Object[0]);
      } else {
         waypoint.setSharedUnlocked();
         LOTRPlayerData.CWPSharedKey key = LOTRPlayerData.CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
         this.cwpSharedUnlocked.add(key);
         this.markDirty();
         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRPacketCWPSharedUnlockClient packet = waypoint.getClientSharedUnlockPacket();
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }

      }
   }

   public void hideOrUnhideSharedCustomWaypoint(LOTRCustomWaypoint waypoint, boolean hide) {
      if (!waypoint.isShared()) {
         FMLLog.warning("LOTR: Warning! Tried to unlock a shared custom waypoint with no owner!", new Object[0]);
      } else {
         waypoint.setSharedHidden(hide);
         LOTRPlayerData.CWPSharedKey key = LOTRPlayerData.CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
         if (hide) {
            this.cwpSharedHidden.add(key);
         } else {
            this.cwpSharedHidden.remove(key);
         }

         this.markDirty();
         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRPacketCWPSharedHideClient packet = waypoint.getClientSharedHidePacket(hide);
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }

      }
   }

   public int getWaypointFTTime(LOTRAbstractWaypoint wp, EntityPlayer entityplayer) {
      int baseMin = LOTRLevelData.getWaypointCooldownMin();
      int baseMax = LOTRLevelData.getWaypointCooldownMax();
      int useCount = this.getWPUseCount(wp);
      double dist = entityplayer.func_70011_f((double)wp.getXCoord() + 0.5D, (double)wp.getYCoordSaved(), (double)wp.getZCoord() + 0.5D);
      double time = (double)baseMin;
      double added = (double)(baseMax - baseMin) * Math.pow(0.9D, (double)useCount);
      time += added;
      time *= Math.max(1.0D, dist * 1.2E-5D);
      int seconds = (int)Math.round(time);
      seconds = Math.max(seconds, 0);
      return seconds * 20;
   }

   public int getWPUseCount(LOTRAbstractWaypoint wp) {
      if (wp instanceof LOTRCustomWaypoint) {
         LOTRCustomWaypoint cwp = (LOTRCustomWaypoint)wp;
         int ID = cwp.getID();
         if (cwp.isShared()) {
            UUID sharingPlayer = cwp.getSharingPlayerID();
            LOTRPlayerData.CWPSharedKey key = LOTRPlayerData.CWPSharedKey.keyFor(sharingPlayer, ID);
            if (this.cwpSharedUseCounts.containsKey(key)) {
               return (Integer)this.cwpSharedUseCounts.get(key);
            }
         } else if (this.cwpUseCounts.containsKey(ID)) {
            return (Integer)this.cwpUseCounts.get(ID);
         }
      } else if (this.wpUseCounts.containsKey(wp)) {
         return (Integer)this.wpUseCounts.get(wp);
      }

      return 0;
   }

   public void setWPUseCount(LOTRAbstractWaypoint wp, int count) {
      if (wp instanceof LOTRCustomWaypoint) {
         LOTRCustomWaypoint cwp = (LOTRCustomWaypoint)wp;
         int ID = cwp.getID();
         if (cwp.isShared()) {
            UUID sharingPlayer = cwp.getSharingPlayerID();
            LOTRPlayerData.CWPSharedKey key = LOTRPlayerData.CWPSharedKey.keyFor(sharingPlayer, ID);
            this.cwpSharedUseCounts.put(key, count);
         } else {
            this.cwpUseCounts.put(ID, count);
         }
      } else {
         this.wpUseCounts.put((LOTRWaypoint)wp, count);
      }

      this.markDirty();
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketWaypointUseCount packet = new LOTRPacketWaypointUseCount(wp, count);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   public void incrementWPUseCount(LOTRAbstractWaypoint wp) {
      this.setWPUseCount(wp, this.getWPUseCount(wp) + 1);
   }

   public int getNextCwpID() {
      return this.nextCwpID;
   }

   public void incrementNextCwpID() {
      ++this.nextCwpID;
      this.markDirty();
   }

   private void addSharedCustomWaypointsFromAllFellowships() {
      int playersChecked = this.addSharedCustomWaypointsFrom((UUID)null, (List)null);
   }

   private void addSharedCustomWaypointsFromAllIn(UUID onlyOneFellowshipID) {
      this.addSharedCustomWaypointsFrom(onlyOneFellowshipID, (List)null);
   }

   private int addSharedCustomWaypointsFrom(UUID onlyOneFellowshipID, List checkSpecificPlayers) {
      Object checkFellowshipIDs;
      if (onlyOneFellowshipID != null) {
         checkFellowshipIDs = new ArrayList();
         ((List)checkFellowshipIDs).add(onlyOneFellowshipID);
      } else {
         checkFellowshipIDs = this.fellowshipIDs;
      }

      List checkFellowPlayerIDs = new ArrayList();
      Iterator var5;
      UUID player;
      Iterator var9;
      if (checkSpecificPlayers != null) {
         var5 = checkSpecificPlayers.iterator();

         while(var5.hasNext()) {
            player = (UUID)var5.next();
            if (!player.equals(this.playerUUID)) {
               checkFellowPlayerIDs.add(player);
            }
         }
      } else {
         var5 = ((List)checkFellowshipIDs).iterator();

         label71:
         while(true) {
            LOTRFellowship fs;
            do {
               if (!var5.hasNext()) {
                  break label71;
               }

               player = (UUID)var5.next();
               fs = LOTRFellowshipData.getActiveFellowship(player);
            } while(fs == null);

            Set playerIDs = fs.getWaypointSharerUUIDs();
            var9 = playerIDs.iterator();

            while(var9.hasNext()) {
               UUID player = (UUID)var9.next();
               if (!player.equals(this.playerUUID) && !checkFellowPlayerIDs.contains(player)) {
                  checkFellowPlayerIDs.add(player);
               }
            }
         }
      }

      var5 = checkFellowPlayerIDs.iterator();

      while(var5.hasNext()) {
         player = (UUID)var5.next();
         LOTRPlayerData pd = LOTRLevelData.getData(player);
         List cwps = pd.getCustomWaypoints();
         var9 = cwps.iterator();

         while(var9.hasNext()) {
            LOTRCustomWaypoint waypoint = (LOTRCustomWaypoint)var9.next();
            boolean inSharedFellowship = false;
            Iterator var12 = ((List)checkFellowshipIDs).iterator();

            while(var12.hasNext()) {
               UUID fsID = (UUID)var12.next();
               if (waypoint.hasSharedFellowship(fsID)) {
                  inSharedFellowship = true;
                  break;
               }
            }

            if (inSharedFellowship) {
               this.addOrUpdateSharedCustomWaypoint(waypoint.createCopyOfShared(player));
            }
         }
      }

      return checkFellowPlayerIDs.size();
   }

   private void checkCustomWaypointsSharedFromFellowships() {
      this.checkCustomWaypointsSharedBy((List)null);
   }

   private void checkCustomWaypointsSharedBy(List checkSpecificPlayers) {
      List removes = new ArrayList();
      Iterator var3 = this.customWaypointsShared.iterator();

      while(true) {
         LOTRCustomWaypoint waypoint;
         UUID waypointSharingPlayer;
         do {
            if (!var3.hasNext()) {
               var3 = removes.iterator();

               while(var3.hasNext()) {
                  waypoint = (LOTRCustomWaypoint)var3.next();
                  this.removeSharedCustomWaypoint(waypoint);
               }

               return;
            }

            waypoint = (LOTRCustomWaypoint)var3.next();
            waypointSharingPlayer = waypoint.getSharingPlayerID();
         } while(checkSpecificPlayers != null && !checkSpecificPlayers.contains(waypointSharingPlayer));

         LOTRCustomWaypoint wpOriginal = LOTRLevelData.getData(waypointSharingPlayer).getCustomWaypointByID(waypoint.getID());
         if (wpOriginal != null) {
            List sharedFellowPlayers = wpOriginal.getPlayersInAllSharedFellowships();
            if (!sharedFellowPlayers.contains(this.playerUUID)) {
               removes.add(waypoint);
            }
         }
      }
   }

   private void unshareFellowshipFromOwnCustomWaypoints(LOTRFellowship fs) {
      Iterator var2 = this.customWaypoints.iterator();

      while(var2.hasNext()) {
         LOTRCustomWaypoint waypoint = (LOTRCustomWaypoint)var2.next();
         if (waypoint.hasSharedFellowship(fs)) {
            this.customWaypointRemoveSharedFellowship(waypoint, fs);
         }
      }

   }

   private void unlockSharedCustomWaypoints(EntityPlayer entityplayer) {
      if (this.pdTick % 20 == 0 && entityplayer.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) {
         List unlockWaypoints = new ArrayList();
         Iterator var3 = this.customWaypointsShared.iterator();

         LOTRCustomWaypoint waypoint;
         while(var3.hasNext()) {
            waypoint = (LOTRCustomWaypoint)var3.next();
            if (waypoint.isShared() && !waypoint.isSharedUnlocked() && waypoint.canUnlockShared(entityplayer)) {
               unlockWaypoints.add(waypoint);
            }
         }

         var3 = unlockWaypoints.iterator();

         while(var3.hasNext()) {
            waypoint = (LOTRCustomWaypoint)var3.next();
            this.unlockSharedCustomWaypoint(waypoint);
         }
      }

   }

   public List getFellowshipIDs() {
      return this.fellowshipIDs;
   }

   public List getFellowships() {
      List fellowships = new ArrayList();
      Iterator var2 = this.fellowshipIDs.iterator();

      while(var2.hasNext()) {
         UUID fsID = (UUID)var2.next();
         LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(fsID);
         if (fs != null) {
            fellowships.add(fs);
         }
      }

      return fellowships;
   }

   public LOTRFellowship getFellowshipByName(String fsName) {
      Iterator var2 = this.fellowshipIDs.iterator();

      LOTRFellowship fs;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         UUID fsID = (UUID)var2.next();
         fs = LOTRFellowshipData.getActiveFellowship(fsID);
      } while(fs == null || !fs.getName().equalsIgnoreCase(fsName));

      return fs;
   }

   public List listAllFellowshipNames() {
      List list = new ArrayList();
      Iterator var2 = this.fellowshipIDs.iterator();

      while(var2.hasNext()) {
         UUID fsID = (UUID)var2.next();
         LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(fsID);
         if (fs != null && fs.containsPlayer(this.playerUUID)) {
            list.add(fs.getName());
         }
      }

      return list;
   }

   public List listAllLeadingFellowshipNames() {
      List list = new ArrayList();
      Iterator var2 = this.fellowshipIDs.iterator();

      while(var2.hasNext()) {
         UUID fsID = (UUID)var2.next();
         LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(fsID);
         if (fs != null && fs.isOwner(this.playerUUID)) {
            list.add(fs.getName());
         }
      }

      return list;
   }

   public void addFellowship(LOTRFellowship fs) {
      if (fs.containsPlayer(this.playerUUID)) {
         UUID fsID = fs.getFellowshipID();
         if (!this.fellowshipIDs.contains(fsID)) {
            this.fellowshipIDs.add(fsID);
            this.markDirty();
            this.sendFellowshipPacket(fs);
            this.addSharedCustomWaypointsFromAllIn(fs.getFellowshipID());
         }
      }

   }

   public void removeFellowship(LOTRFellowship fs) {
      if (fs.isDisbanded() || !fs.containsPlayer(this.playerUUID)) {
         UUID fsID = fs.getFellowshipID();
         if (this.fellowshipIDs.contains(fsID)) {
            this.fellowshipIDs.remove(fsID);
            this.markDirty();
            this.sendFellowshipRemovePacket(fs);
            this.unshareFellowshipFromOwnCustomWaypoints(fs);
            this.checkCustomWaypointsSharedFromFellowships();
         }
      }

   }

   public void updateFellowship(LOTRFellowship fs, FellowshipUpdateType updateType) {
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         IMessage updatePacket = updateType.createUpdatePacket(this, fs);
         if (updatePacket != null) {
            LOTRPacketHandler.networkWrapper.sendTo(updatePacket, (EntityPlayerMP)entityplayer);
         } else {
            LOTRLog.logger.error("No associated packet for fellowship update type " + updateType.getClass().getName());
         }
      }

      List playersToCheckSharedWaypointsFrom = updateType.getPlayersToCheckSharedWaypointsFrom(fs);
      if (playersToCheckSharedWaypointsFrom != null && !playersToCheckSharedWaypointsFrom.isEmpty()) {
         this.addSharedCustomWaypointsFrom(fs.getFellowshipID(), playersToCheckSharedWaypointsFrom);
         this.checkCustomWaypointsSharedBy(playersToCheckSharedWaypointsFrom);
      }

   }

   public void createFellowship(String name, boolean normalCreation) {
      if (!normalCreation || LOTRConfig.enableFellowshipCreation && this.canCreateFellowships(false)) {
         if (!this.anyMatchingFellowshipNames(name, false)) {
            LOTRFellowship fellowship = new LOTRFellowship(this.playerUUID, name);
            fellowship.createAndRegister();
         }

      }
   }

   public boolean canCreateFellowships(boolean client) {
      int max = this.getMaxLeadingFellowships();
      int leading = 0;
      Iterator var4;
      if (client) {
         var4 = this.fellowshipsClient.iterator();

         while(var4.hasNext()) {
            LOTRFellowshipClient fs = (LOTRFellowshipClient)var4.next();
            if (fs.isOwned()) {
               ++leading;
               if (leading >= max) {
                  return false;
               }
            }
         }
      } else {
         var4 = this.fellowshipIDs.iterator();

         while(var4.hasNext()) {
            UUID fsID = (UUID)var4.next();
            LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(fsID);
            if (fs != null && fs.isOwner(this.playerUUID)) {
               ++leading;
               if (leading >= max) {
                  return false;
               }
            }
         }
      }

      return leading < max;
   }

   private int getMaxLeadingFellowships() {
      int achievements = this.getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size();
      return 1 + achievements / 20;
   }

   public void disbandFellowship(LOTRFellowship fs, String disbanderUsername) {
      if (fs.isOwner(this.playerUUID)) {
         List memberUUIDs = new ArrayList(fs.getMemberUUIDs());
         fs.setDisbandedAndRemoveAllMembers();
         this.removeFellowship(fs);
         Iterator var4 = memberUUIDs.iterator();

         while(var4.hasNext()) {
            UUID memberID = (UUID)var4.next();
            EntityPlayer member = this.getOtherPlayer(memberID);
            if (member != null && !member.field_70170_p.field_72995_K) {
               fs.sendNotification(member, "lotr.gui.fellowships.notifyDisband", disbanderUsername);
            }
         }
      }

   }

   public void invitePlayerToFellowship(LOTRFellowship fs, UUID invitedPlayerUUID, String inviterUsername) {
      if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
         LOTRLevelData.getData(invitedPlayerUUID).addFellowshipInvite(fs, this.playerUUID, inviterUsername);
      }

   }

   public void removePlayerFromFellowship(LOTRFellowship fs, UUID player, String removerUsername) {
      if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
         fs.removeMember(player);
         EntityPlayer removed = this.getOtherPlayer(player);
         if (removed != null && !removed.field_70170_p.field_72995_K) {
            fs.sendNotification(removed, "lotr.gui.fellowships.notifyRemove", removerUsername);
         }
      }

   }

   public void transferFellowship(LOTRFellowship fs, UUID player, String prevOwnerUsername) {
      if (fs.isOwner(this.playerUUID)) {
         fs.setOwner(player);
         EntityPlayer newOwner = this.getOtherPlayer(player);
         if (newOwner != null && !newOwner.field_70170_p.field_72995_K) {
            fs.sendNotification(newOwner, "lotr.gui.fellowships.notifyTransfer", prevOwnerUsername);
         }
      }

   }

   public void setFellowshipAdmin(LOTRFellowship fs, UUID player, boolean flag, String granterUsername) {
      if (fs.isOwner(this.playerUUID)) {
         fs.setAdmin(player, flag);
         EntityPlayer subjectPlayer = this.getOtherPlayer(player);
         if (subjectPlayer != null && !subjectPlayer.field_70170_p.field_72995_K) {
            if (flag) {
               fs.sendNotification(subjectPlayer, "lotr.gui.fellowships.notifyOp", granterUsername);
            } else {
               fs.sendNotification(subjectPlayer, "lotr.gui.fellowships.notifyDeop", granterUsername);
            }
         }
      }

   }

   public void renameFellowship(LOTRFellowship fs, String name) {
      if (fs.isOwner(this.playerUUID)) {
         fs.setName(name);
      }

   }

   public void setFellowshipIcon(LOTRFellowship fs, ItemStack itemstack) {
      if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
         fs.setIcon(itemstack);
      }

   }

   public void setFellowshipPreventPVP(LOTRFellowship fs, boolean prevent) {
      if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
         fs.setPreventPVP(prevent);
      }

   }

   public void setFellowshipPreventHiredFF(LOTRFellowship fs, boolean prevent) {
      if (fs.isOwner(this.playerUUID) || fs.isAdmin(this.playerUUID)) {
         fs.setPreventHiredFriendlyFire(prevent);
      }

   }

   public void setFellowshipShowMapLocations(LOTRFellowship fs, boolean show) {
      if (fs.isOwner(this.playerUUID)) {
         fs.setShowMapLocations(show);
      }

   }

   public void leaveFellowship(LOTRFellowship fs) {
      if (!fs.isOwner(this.playerUUID)) {
         fs.removeMember(this.playerUUID);
         if (this.fellowshipIDs.contains(fs.getFellowshipID())) {
            this.removeFellowship(fs);
         }

         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            EntityPlayer owner = this.getOtherPlayer(fs.getOwner());
            if (owner != null) {
               fs.sendNotification(owner, "lotr.gui.fellowships.notifyLeave", entityplayer.func_70005_c_());
            }
         }
      }

   }

   private void sendFellowshipPacket(LOTRFellowship fs) {
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketFellowship packet = new LOTRPacketFellowship(this, fs, false);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   private void sendFellowshipRemovePacket(LOTRFellowship fs) {
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketFellowshipRemove packet = new LOTRPacketFellowshipRemove(fs, false);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   public List getClientFellowships() {
      return this.fellowshipsClient;
   }

   public boolean anyMatchingFellowshipNames(String name, boolean client) {
      name = StringUtils.strip(name).toLowerCase();
      Iterator var3;
      if (client) {
         var3 = this.fellowshipsClient.iterator();

         while(var3.hasNext()) {
            LOTRFellowshipClient fs = (LOTRFellowshipClient)var3.next();
            String otherName = fs.getName();
            otherName = StringUtils.strip(otherName).toLowerCase();
            if (name.equals(otherName)) {
               return true;
            }
         }
      } else {
         var3 = this.fellowshipIDs.iterator();

         while(var3.hasNext()) {
            UUID fsID = (UUID)var3.next();
            LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(fsID);
            if (fs != null) {
               String otherName = fs.getName();
               otherName = StringUtils.strip(otherName).toLowerCase();
               if (name.equals(otherName)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public void addOrUpdateClientFellowship(LOTRFellowshipClient fs) {
      UUID fsID = fs.getFellowshipID();
      LOTRFellowshipClient inList = null;
      Iterator var4 = this.fellowshipsClient.iterator();

      while(var4.hasNext()) {
         LOTRFellowshipClient fsInList = (LOTRFellowshipClient)var4.next();
         if (fsInList.getFellowshipID().equals(fsID)) {
            inList = fsInList;
            break;
         }
      }

      if (inList != null) {
         inList.updateDataFrom(fs);
      } else {
         this.fellowshipsClient.add(fs);
      }

   }

   public void removeClientFellowship(UUID fsID) {
      LOTRFellowshipClient inList = null;
      Iterator var3 = this.fellowshipsClient.iterator();

      while(var3.hasNext()) {
         LOTRFellowshipClient fsInList = (LOTRFellowshipClient)var3.next();
         if (fsInList.getFellowshipID().equals(fsID)) {
            inList = fsInList;
            break;
         }
      }

      if (inList != null) {
         this.fellowshipsClient.remove(inList);
      }

   }

   public LOTRFellowshipClient getClientFellowshipByName(String fsName) {
      Iterator var2 = this.fellowshipsClient.iterator();

      LOTRFellowshipClient fs;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         fs = (LOTRFellowshipClient)var2.next();
      } while(!fs.getName().equalsIgnoreCase(fsName));

      return fs;
   }

   public LOTRFellowshipClient getClientFellowshipByID(UUID fsID) {
      Iterator var2 = this.fellowshipsClient.iterator();

      LOTRFellowshipClient fs;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         fs = (LOTRFellowshipClient)var2.next();
      } while(!fs.getFellowshipID().equals(fsID));

      return fs;
   }

   public void addFellowshipInvite(LOTRFellowship fs, UUID inviterUUID, String inviterUsername) {
      UUID fsID = fs.getFellowshipID();
      boolean hasInviteAlready = false;
      Iterator var6 = this.fellowshipInvites.iterator();

      while(var6.hasNext()) {
         LOTRFellowshipInvite invite = (LOTRFellowshipInvite)var6.next();
         if (invite.fellowshipID.equals(fsID)) {
            hasInviteAlready = true;
            break;
         }
      }

      if (!hasInviteAlready) {
         this.fellowshipInvites.add(new LOTRFellowshipInvite(fsID, inviterUUID));
         this.markDirty();
         this.sendFellowshipInvitePacket(fs);
         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            fs.sendNotification(entityplayer, "lotr.gui.fellowships.notifyInvite", inviterUsername);
         }
      }

   }

   public void acceptFellowshipInvite(LOTRFellowship fs, boolean respectSizeLimit) {
      UUID fsID = fs.getFellowshipID();
      LOTRFellowshipInvite existingInvite = null;
      Iterator var5 = this.fellowshipInvites.iterator();

      while(var5.hasNext()) {
         LOTRFellowshipInvite invite = (LOTRFellowshipInvite)var5.next();
         if (invite.fellowshipID.equals(fsID)) {
            existingInvite = invite;
            break;
         }
      }

      if (existingInvite != null) {
         EntityPlayer entityplayer = this.getPlayer();
         if (fs.isDisbanded()) {
            this.rejectFellowshipInvite(fs);
            if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
               LOTRPacketFellowshipAcceptInviteResult resultPacket = new LOTRPacketFellowshipAcceptInviteResult(fs, LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.DISBANDED);
               LOTRPacketHandler.networkWrapper.sendTo(resultPacket, (EntityPlayerMP)entityplayer);
            }
         } else {
            int limit = LOTRConfig.fellowshipMaxSize;
            LOTRPacketFellowshipAcceptInviteResult resultPacket;
            if (respectSizeLimit && limit >= 0 && fs.getPlayerCount() >= limit) {
               this.rejectFellowshipInvite(fs);
               if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
                  resultPacket = new LOTRPacketFellowshipAcceptInviteResult(fs, LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.TOO_LARGE);
                  LOTRPacketHandler.networkWrapper.sendTo(resultPacket, (EntityPlayerMP)entityplayer);
               }
            } else {
               fs.addMember(this.playerUUID);
               this.fellowshipInvites.remove(existingInvite);
               this.markDirty();
               this.sendFellowshipInviteRemovePacket(fs);
               if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
                  resultPacket = new LOTRPacketFellowshipAcceptInviteResult(fs, LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.JOINED);
                  LOTRPacketHandler.networkWrapper.sendTo(resultPacket, (EntityPlayerMP)entityplayer);
                  UUID inviterID = existingInvite.inviterID;
                  if (inviterID == null) {
                     inviterID = fs.getOwner();
                  }

                  EntityPlayer inviter = this.getOtherPlayer(inviterID);
                  if (inviter != null) {
                     fs.sendNotification(inviter, "lotr.gui.fellowships.notifyAccept", entityplayer.func_70005_c_());
                  }
               }
            }
         }
      }

   }

   public void rejectFellowshipInvite(LOTRFellowship fs) {
      UUID fsID = fs.getFellowshipID();
      LOTRFellowshipInvite existingInvite = null;
      Iterator var4 = this.fellowshipInvites.iterator();

      while(var4.hasNext()) {
         LOTRFellowshipInvite invite = (LOTRFellowshipInvite)var4.next();
         if (invite.fellowshipID.equals(fsID)) {
            existingInvite = invite;
            break;
         }
      }

      if (existingInvite != null) {
         this.fellowshipInvites.remove(existingInvite);
         this.markDirty();
         this.sendFellowshipInviteRemovePacket(fs);
      }

   }

   private void sendFellowshipInvitePacket(LOTRFellowship fs) {
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketFellowship packet = new LOTRPacketFellowship(this, fs, true);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   private void sendFellowshipInviteRemovePacket(LOTRFellowship fs) {
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketFellowshipRemove packet = new LOTRPacketFellowshipRemove(fs, true);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   public List getClientFellowshipInvites() {
      return this.fellowshipInvitesClient;
   }

   public void addOrUpdateClientFellowshipInvite(LOTRFellowshipClient fs) {
      UUID fsID = fs.getFellowshipID();
      LOTRFellowshipClient inList = null;
      Iterator var4 = this.fellowshipInvitesClient.iterator();

      while(var4.hasNext()) {
         LOTRFellowshipClient fsInList = (LOTRFellowshipClient)var4.next();
         if (fsInList.getFellowshipID().equals(fsID)) {
            inList = fsInList;
            break;
         }
      }

      if (inList != null) {
         inList.updateDataFrom(fs);
      } else {
         this.fellowshipInvitesClient.add(fs);
      }

   }

   public void removeClientFellowshipInvite(UUID fsID) {
      LOTRFellowshipClient inList = null;
      Iterator var3 = this.fellowshipInvitesClient.iterator();

      while(var3.hasNext()) {
         LOTRFellowshipClient fsInList = (LOTRFellowshipClient)var3.next();
         if (fsInList.getFellowshipID().equals(fsID)) {
            inList = fsInList;
            break;
         }
      }

      if (inList != null) {
         this.fellowshipInvitesClient.remove(inList);
      }

   }

   public UUID getChatBoundFellowshipID() {
      return this.chatBoundFellowshipID;
   }

   public LOTRFellowship getChatBoundFellowship() {
      if (this.chatBoundFellowshipID != null) {
         LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(this.chatBoundFellowshipID);
         if (fs != null) {
            return fs;
         }
      }

      return null;
   }

   public void setChatBoundFellowshipID(UUID fsID) {
      this.chatBoundFellowshipID = fsID;
      this.markDirty();
   }

   public void setChatBoundFellowship(LOTRFellowship fs) {
      this.setChatBoundFellowshipID(fs.getFellowshipID());
   }

   public void setSiegeActive(int duration) {
      this.siegeActiveTime = Math.max(this.siegeActiveTime, duration);
   }

   public boolean isSiegeActive() {
      return this.siegeActiveTime > 0;
   }

   public LOTRFaction getViewingFaction() {
      return this.viewingFaction;
   }

   public void setViewingFaction(LOTRFaction faction) {
      if (faction != null) {
         this.viewingFaction = faction;
         this.markDirty();
         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRPacketUpdateViewingFaction packet = new LOTRPacketUpdateViewingFaction(this.viewingFaction);
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }
      }

   }

   public LOTRFaction getRegionLastViewedFaction(LOTRDimension.DimensionRegion region) {
      LOTRFaction fac = (LOTRFaction)this.prevRegionFactions.get(region);
      if (fac == null) {
         fac = (LOTRFaction)region.factionList.get(0);
         this.prevRegionFactions.put(region, fac);
      }

      return fac;
   }

   public void setRegionLastViewedFaction(LOTRDimension.DimensionRegion region, LOTRFaction fac) {
      if (region.factionList.contains(fac)) {
         this.prevRegionFactions.put(region, fac);
         this.markDirty();
      }

   }

   public boolean showWaypoints() {
      return this.showWaypoints;
   }

   public void setShowWaypoints(boolean flag) {
      this.showWaypoints = flag;
      this.markDirty();
   }

   public boolean showCustomWaypoints() {
      return this.showCustomWaypoints;
   }

   public void setShowCustomWaypoints(boolean flag) {
      this.showCustomWaypoints = flag;
      this.markDirty();
   }

   public boolean showHiddenSharedWaypoints() {
      return this.showHiddenSharedWaypoints;
   }

   public void setShowHiddenSharedWaypoints(boolean flag) {
      this.showHiddenSharedWaypoints = flag;
      this.markDirty();
   }

   public boolean getHideMapLocation() {
      return this.hideOnMap;
   }

   public void setHideMapLocation(boolean flag) {
      this.hideOnMap = flag;
      this.markDirty();
      this.sendOptionsPacket(3, flag);
   }

   public boolean getAdminHideMap() {
      return this.adminHideMap;
   }

   public void setAdminHideMap(boolean flag) {
      this.adminHideMap = flag;
      this.markDirty();
   }

   public boolean getEnableConquestKills() {
      return this.conquestKills;
   }

   public void setEnableConquestKills(boolean flag) {
      this.conquestKills = flag;
      this.markDirty();
      this.sendOptionsPacket(5, flag);
   }

   public boolean getTeleportedME() {
      return this.teleportedME;
   }

   public void setTeleportedME(boolean flag) {
      this.teleportedME = flag;
      this.markDirty();
   }

   public ChunkCoordinates getDeathPoint() {
      return this.deathPoint;
   }

   public void setDeathPoint(int i, int j, int k) {
      this.deathPoint = new ChunkCoordinates(i, j, k);
      this.markDirty();
   }

   public int getDeathDimension() {
      return this.deathDim;
   }

   public void setDeathDimension(int dim) {
      this.deathDim = dim;
      this.markDirty();
   }

   public int getAlcoholTolerance() {
      return this.alcoholTolerance;
   }

   public void setAlcoholTolerance(int i) {
      this.alcoholTolerance = i;
      this.markDirty();
      if (this.alcoholTolerance >= 250) {
         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            this.addAchievement(LOTRAchievement.gainHighAlcoholTolerance);
         }
      }

   }

   public List getMiniQuests() {
      return this.miniQuests;
   }

   public List getMiniQuestsCompleted() {
      return this.miniQuestsCompleted;
   }

   public void addMiniQuest(LOTRMiniQuest quest) {
      this.miniQuests.add(quest);
      this.updateMiniQuest(quest);
   }

   public void addMiniQuestCompleted(LOTRMiniQuest quest) {
      this.miniQuestsCompleted.add(quest);
      this.markDirty();
   }

   public void removeMiniQuest(LOTRMiniQuest quest, boolean completed) {
      List removeList;
      if (completed) {
         removeList = this.miniQuestsCompleted;
      } else {
         removeList = this.miniQuests;
      }

      if (removeList.remove(quest)) {
         this.markDirty();
         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRPacketMiniquestRemove packet = new LOTRPacketMiniquestRemove(quest, quest.isCompleted(), false);
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }
      } else {
         FMLLog.warning("Warning: Attempted to remove a miniquest which does not belong to the player data", new Object[0]);
      }

   }

   public void updateMiniQuest(LOTRMiniQuest quest) {
      this.markDirty();
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         try {
            this.sendMiniQuestPacket((EntityPlayerMP)entityplayer, quest, false);
         } catch (IOException var4) {
            FMLLog.severe("Error sending miniquest packet to player " + entityplayer.func_70005_c_(), new Object[0]);
            var4.printStackTrace();
         }
      }

   }

   public void completeMiniQuest(LOTRMiniQuest quest) {
      if (this.miniQuests.remove(quest)) {
         this.addMiniQuestCompleted(quest);
         ++this.completedMiniquestCount;
         this.getFactionData(quest.entityFaction).completeMiniQuest();
         this.markDirty();
         LOTRMod.proxy.setTrackedQuest(quest);
         EntityPlayer entityplayer = this.getPlayer();
         if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
            LOTRPacketMiniquestRemove packet = new LOTRPacketMiniquestRemove(quest, false, true);
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }
      } else {
         FMLLog.warning("Warning: Attempted to remove a miniquest which does not belong to the player data", new Object[0]);
      }

   }

   private void sendMiniQuestPacket(EntityPlayerMP entityplayer, LOTRMiniQuest quest, boolean completed) throws IOException {
      NBTTagCompound nbt = new NBTTagCompound();
      quest.writeToNBT(nbt);
      LOTRPacketMiniquest packet = new LOTRPacketMiniquest(nbt, completed);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   public LOTRMiniQuest getMiniQuestForID(UUID id, boolean completed) {
      ArrayList threadSafe;
      if (completed) {
         threadSafe = new ArrayList(this.miniQuestsCompleted);
      } else {
         threadSafe = new ArrayList(this.miniQuests);
      }

      Iterator var4 = threadSafe.iterator();

      LOTRMiniQuest quest;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         quest = (LOTRMiniQuest)var4.next();
      } while(!quest.questUUID.equals(id));

      return quest;
   }

   public List getActiveMiniQuests() {
      return this.selectMiniQuests((new MiniQuestSelector.OptionalActive()).setActiveOnly());
   }

   public List getMiniQuestsForEntity(LOTREntityNPC npc, boolean activeOnly) {
      return this.getMiniQuestsForEntityID(npc.func_110124_au(), activeOnly);
   }

   public List getMiniQuestsForEntityID(UUID npcID, boolean activeOnly) {
      MiniQuestSelector.EntityId sel = new MiniQuestSelector.EntityId(npcID);
      if (activeOnly) {
         sel.setActiveOnly();
      }

      return this.selectMiniQuests(sel);
   }

   public List getMiniQuestsForFaction(final LOTRFaction f, boolean activeOnly) {
      MiniQuestSelector.Faction sel = new MiniQuestSelector.Faction(new Supplier() {
         public LOTRFaction get() {
            return f;
         }
      });
      if (activeOnly) {
         sel.setActiveOnly();
      }

      return this.selectMiniQuests(sel);
   }

   public List selectMiniQuests(MiniQuestSelector selector) {
      List ret = new ArrayList();
      List threadSafe = new ArrayList(this.miniQuests);
      Iterator var4 = threadSafe.iterator();

      while(var4.hasNext()) {
         LOTRMiniQuest quest = (LOTRMiniQuest)var4.next();
         if (selector.include(quest)) {
            ret.add(quest);
         }
      }

      return ret;
   }

   public int getCompletedMiniQuestsTotal() {
      return this.completedMiniquestCount;
   }

   public int getCompletedBountyQuests() {
      return this.completedBountyQuests;
   }

   public void addCompletedBountyQuest() {
      ++this.completedBountyQuests;
      this.markDirty();
   }

   public boolean hasActiveOrCompleteMQType(Class type) {
      List quests = this.getMiniQuests();
      List questsComplete = this.getMiniQuestsCompleted();
      List allQuests = new ArrayList();
      Iterator var5 = quests.iterator();

      LOTRMiniQuest q;
      while(var5.hasNext()) {
         q = (LOTRMiniQuest)var5.next();
         if (q.isActive()) {
            allQuests.add(q);
         }
      }

      allQuests.addAll(questsComplete);
      var5 = allQuests.iterator();

      do {
         if (!var5.hasNext()) {
            return false;
         }

         q = (LOTRMiniQuest)var5.next();
      } while(!type.isAssignableFrom(q.getClass()));

      return true;
   }

   public boolean hasAnyGWQuest() {
      return this.hasActiveOrCompleteMQType(LOTRMiniQuestWelcome.class);
   }

   public void distributeMQEvent(LOTRMiniQuestEvent event) {
      Iterator var2 = this.miniQuests.iterator();

      while(var2.hasNext()) {
         LOTRMiniQuest quest = (LOTRMiniQuest)var2.next();
         if (quest.isActive()) {
            quest.handleEvent(event);
         }
      }

   }

   public LOTRMiniQuest getTrackingMiniQuest() {
      return this.trackingMiniQuestID == null ? null : this.getMiniQuestForID(this.trackingMiniQuestID, false);
   }

   public void setTrackingMiniQuest(LOTRMiniQuest quest) {
      this.setTrackingMiniQuestID(quest == null ? null : quest.questUUID);
   }

   public void setTrackingMiniQuestID(UUID npcID) {
      this.trackingMiniQuestID = npcID;
      this.markDirty();
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketMiniquestTrackClient packet = new LOTRPacketMiniquestTrackClient(this.trackingMiniQuestID);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   public void placeBountyFor(LOTRFaction f) {
      this.bountiesPlaced.add(f);
      this.markDirty();
   }

   public LOTRWaypoint getLastKnownWaypoint() {
      return this.lastWaypoint;
   }

   public LOTRBiome getLastKnownBiome() {
      return this.lastBiome;
   }

   public void sendMessageIfNotReceived(LOTRGuiMessageTypes message) {
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         Boolean sent = (Boolean)this.sentMessageTypes.get(message);
         if (sent == null) {
            sent = false;
            this.sentMessageTypes.put(message, sent);
         }

         if (!sent) {
            this.sentMessageTypes.put(message, true);
            this.markDirty();
            LOTRPacketMessage packet = new LOTRPacketMessage(message);
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }
      }

   }

   public LOTRTitle.PlayerTitle getPlayerTitle() {
      return this.playerTitle;
   }

   public void setPlayerTitle(LOTRTitle.PlayerTitle title) {
      this.playerTitle = title;
      this.markDirty();
      EntityPlayer entityplayer = this.getPlayer();
      if (entityplayer != null && !entityplayer.field_70170_p.field_72995_K) {
         LOTRPacketTitle packet = new LOTRPacketTitle(this.playerTitle);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

      Iterator var6 = this.fellowshipIDs.iterator();

      while(var6.hasNext()) {
         UUID fsID = (UUID)var6.next();
         LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(fsID);
         if (fs != null) {
            fs.updateForAllMembers(new FellowshipUpdateType.UpdatePlayerTitle(this.playerUUID, this.playerTitle));
         }
      }

   }

   public boolean getFemRankOverride() {
      return this.femRankOverride;
   }

   public void setFemRankOverride(boolean flag) {
      this.femRankOverride = flag;
      this.markDirty();
      this.sendOptionsPacket(4, flag);
   }

   public boolean useFeminineRanks() {
      if (this.femRankOverride) {
         return true;
      } else if (this.playerTitle != null) {
         LOTRTitle title = this.playerTitle.getTitle();
         return title.isFeminineRank();
      } else {
         return false;
      }
   }

   public LOTRPlayerQuestData getQuestData() {
      return this.questData;
   }

   private static class CWPSharedKey extends Pair {
      public final UUID sharingPlayer;
      public final int waypointID;

      private CWPSharedKey(UUID player, int id) {
         this.sharingPlayer = player;
         this.waypointID = id;
      }

      public static LOTRPlayerData.CWPSharedKey keyFor(UUID player, int id) {
         return new LOTRPlayerData.CWPSharedKey(player, id);
      }

      public Integer setValue(Integer value) {
         throw new UnsupportedOperationException();
      }

      public UUID getLeft() {
         return this.sharingPlayer;
      }

      public Integer getRight() {
         return this.waypointID;
      }
   }
}
