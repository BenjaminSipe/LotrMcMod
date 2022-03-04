package lotr.common.entity.npc;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import cpw.mods.fml.common.FMLLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMiniquestOffer;
import lotr.common.network.LOTRPacketNPCIsOfferingQuest;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestBounty;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.MiniQuestSelector;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;

public class LOTREntityQuestInfo {
   private LOTREntityNPC theNPC;
   private LOTRMiniQuest miniquestOffer;
   private int offerTime = 0;
   public static final int maxOfferTime = 24000;
   private int offerChance;
   private static final int offerChance_default = 20000;
   private float minAlignment;
   private Map playerSpecificOffers = new HashMap();
   private List openOfferPlayers = new ArrayList();
   private Map playerPacketCache = new HashMap();
   public boolean clientIsOffering;
   public int clientOfferColor;
   private List activeQuestPlayers = new ArrayList();
   private Predicate bountyHelpPredicate;
   private Predicate bountyHelpConsumer;
   private MiniQuestSelector.BountyActiveAnyFaction activeBountySelector;

   public LOTREntityQuestInfo(LOTREntityNPC npc) {
      this.theNPC = npc;
      this.offerChance = 20000;
      this.minAlignment = 0.0F;
      this.bountyHelpPredicate = new Predicate() {
         public boolean apply(EntityPlayer player) {
            return LOTREntityQuestInfo.this.theNPC.func_70681_au().nextInt(3) == 0;
         }
      };
      this.bountyHelpConsumer = new Predicate() {
         public boolean apply(EntityPlayer player) {
            return true;
         }
      };
      this.activeBountySelector = new MiniQuestSelector.BountyActiveFaction(new Supplier() {
         public LOTRFaction get() {
            return LOTREntityQuestInfo.this.theNPC.getFaction();
         }
      });
   }

   public void setOfferChance(int i) {
      this.offerChance = i;
   }

   public void setMinAlignment(float f) {
      this.minAlignment = f;
   }

   public void setBountyHelpPredicate(Predicate predicate) {
      this.bountyHelpPredicate = predicate;
   }

   public void setBountyHelpConsumer(Predicate predicate) {
      this.bountyHelpConsumer = predicate;
   }

   public void setActiveBountySelector(MiniQuestSelector.BountyActiveAnyFaction sel) {
      this.activeBountySelector = sel;
   }

   private boolean canGenerateQuests() {
      if (!LOTRConfig.allowMiniquests) {
         return false;
      } else if (!this.theNPC.func_70631_g_() && !this.theNPC.isDrunkard()) {
         return !this.theNPC.isTrader() && !this.theNPC.isTraderEscort && !this.theNPC.hiredNPCInfo.isActive;
      } else {
         return false;
      }
   }

   public boolean canOfferQuestsTo(EntityPlayer entityplayer) {
      if (this.canGenerateQuests() && this.theNPC.isFriendlyAndAligned(entityplayer) && this.theNPC.func_70638_az() == null) {
         float alignment = LOTRLevelData.getData(entityplayer).getAlignment(this.theNPC.getFaction());
         return alignment >= this.minAlignment;
      } else {
         return false;
      }
   }

   private LOTRMiniQuest generateRandomMiniQuest() {
      int tries = 8;

      for(int l = 0; l < tries; ++l) {
         LOTRMiniQuest quest = this.theNPC.createMiniQuest();
         if (quest != null) {
            if (quest.isValidQuest()) {
               return quest;
            }

            FMLLog.severe("Created an invalid LOTR miniquest " + quest.speechBankStart, new Object[0]);
         }
      }

      return null;
   }

   public LOTRMiniQuest getOfferFor(EntityPlayer entityplayer) {
      return this.getOfferFor(entityplayer, (boolean[])null);
   }

   private LOTRMiniQuest getOfferFor(EntityPlayer entityplayer, boolean[] isSpecific) {
      UUID id = entityplayer.func_110124_au();
      if (this.playerSpecificOffers.containsKey(id)) {
         if (isSpecific != null) {
            isSpecific[0] = true;
         }

         return (LOTRMiniQuest)this.playerSpecificOffers.get(id);
      } else {
         if (isSpecific != null) {
            isSpecific[0] = false;
         }

         return this.miniquestOffer;
      }
   }

   public void clearMiniQuestOffer() {
      this.setMiniQuestOffer((LOTRMiniQuest)null, 0);
   }

   public void setMiniQuestOffer(LOTRMiniQuest quest, int time) {
      this.miniquestOffer = quest;
      this.offerTime = time;
   }

   public void setPlayerSpecificOffer(EntityPlayer entityplayer, LOTRMiniQuest quest) {
      this.playerSpecificOffers.put(entityplayer.func_110124_au(), quest);
   }

   public void clearPlayerSpecificOffer(EntityPlayer entityplayer) {
      this.playerSpecificOffers.remove(entityplayer.func_110124_au());
   }

   public void addOpenOfferPlayer(EntityPlayer entityplayer) {
      this.openOfferPlayers.add(entityplayer);
   }

   public void removeOpenOfferPlayer(EntityPlayer entityplayer) {
      this.openOfferPlayers.remove(entityplayer);
   }

   public boolean anyOpenOfferPlayers() {
      return !this.openOfferPlayers.isEmpty();
   }

   public void onUpdate() {
      if (!this.theNPC.field_70170_p.field_72995_K) {
         if (this.miniquestOffer == null) {
            if (this.canGenerateQuests() && this.theNPC.func_70681_au().nextInt(this.offerChance) == 0) {
               this.miniquestOffer = this.generateRandomMiniQuest();
               if (this.miniquestOffer != null) {
                  this.offerTime = 24000;
               }
            }
         } else if (this.miniquestOffer.isValidQuest() && this.canGenerateQuests()) {
            if (!this.anyOpenOfferPlayers()) {
               if (this.offerTime > 0) {
                  --this.offerTime;
               } else {
                  this.clearMiniQuestOffer();
               }
            }
         } else {
            this.clearMiniQuestOffer();
         }

         if (this.theNPC.field_70173_aa % 10 == 0) {
            this.pruneActiveQuestPlayers();
         }

         if (this.theNPC.field_70173_aa % 10 == 0) {
            this.sendDataToAllWatchers();
         }
      }

   }

   public boolean anyActiveQuestPlayers() {
      return !this.activeQuestPlayers.isEmpty();
   }

   public void addActiveQuestPlayer(EntityPlayer entityplayer) {
      this.activeQuestPlayers.add(entityplayer.func_110124_au());
   }

   private void removeActiveQuestPlayer(EntityPlayer entityplayer) {
      this.activeQuestPlayers.remove(entityplayer.func_110124_au());
   }

   private void pruneActiveQuestPlayers() {
      if (!this.activeQuestPlayers.isEmpty()) {
         Set removes = new HashSet();
         Iterator var2 = this.activeQuestPlayers.iterator();

         while(true) {
            while(var2.hasNext()) {
               UUID player = (UUID)var2.next();
               List playerQuests = LOTRLevelData.getData(player).getMiniQuestsForEntity(this.theNPC, true);
               if (playerQuests.isEmpty()) {
                  removes.add(player);
               } else {
                  Iterator var5 = playerQuests.iterator();

                  while(var5.hasNext()) {
                     LOTRMiniQuest quest = (LOTRMiniQuest)var5.next();
                     quest.updateLocation(this.theNPC);
                  }
               }
            }

            this.activeQuestPlayers.removeAll(removes);
            break;
         }
      }

   }

   public boolean interact(EntityPlayer entityplayer) {
      LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
      List thisNPCQuests = playerData.getMiniQuestsForEntity(this.theNPC, true);
      if (thisNPCQuests.isEmpty()) {
         Iterator var4 = playerData.getActiveMiniQuests().iterator();

         while(var4.hasNext()) {
            LOTRMiniQuest quest = (LOTRMiniQuest)var4.next();
            if (!quest.entityUUID.equals(this.theNPC.func_110124_au()) && quest.onInteractOther(entityplayer, this.theNPC)) {
               return true;
            }
         }
      }

      if (this.canOfferQuestsTo(entityplayer)) {
         LOTRMiniQuest offer;
         if (!thisNPCQuests.isEmpty()) {
            offer = (LOTRMiniQuest)thisNPCQuests.get(0);
            offer.onInteract(entityplayer, this.theNPC);
            if (offer.isCompleted()) {
               this.removeActiveQuestPlayer(entityplayer);
            } else {
               playerData.setTrackingMiniQuest(offer);
            }

            return true;
         }

         offer = this.getOfferFor(entityplayer);
         List bountyQuests;
         if (offer != null && offer.isValidQuest() && offer.canPlayerAccept(entityplayer)) {
            bountyQuests = playerData.getMiniQuestsForFaction(this.theNPC.getFaction(), true);
            if (bountyQuests.size() < LOTRMiniQuest.MAX_MINIQUESTS_PER_FACTION) {
               this.sendMiniquestOffer(entityplayer, offer);
               return true;
            }

            this.theNPC.sendSpeechBank(entityplayer, offer.speechBankTooMany, offer);
            return true;
         }

         LOTRMiniQuestFactory bountyHelpSpeechDir = this.theNPC.getBountyHelpSpeechDir();
         if (bountyHelpSpeechDir != null && this.bountyHelpPredicate.apply(entityplayer)) {
            bountyQuests = playerData.selectMiniQuests(this.activeBountySelector);
            if (!bountyQuests.isEmpty()) {
               LOTRMiniQuestBounty bQuest = (LOTRMiniQuestBounty)bountyQuests.get(this.theNPC.func_70681_au().nextInt(bountyQuests.size()));
               UUID targetID = bQuest.targetID;
               String objective = bQuest.targetName;
               LOTRPlayerData targetData = LOTRLevelData.getData(targetID);
               LOTRMiniQuestBounty.BountyHelp helpType = LOTRMiniQuestBounty.BountyHelp.getRandomHelpType(this.theNPC.func_70681_au());
               String location = null;
               if (helpType == LOTRMiniQuestBounty.BountyHelp.BIOME) {
                  LOTRBiome lastBiome = targetData.getLastKnownBiome();
                  if (lastBiome != null) {
                     location = lastBiome.getBiomeDisplayName();
                  }
               } else if (helpType == LOTRMiniQuestBounty.BountyHelp.WAYPOINT) {
                  LOTRWaypoint lastWP = targetData.getLastKnownWaypoint();
                  if (lastWP != null) {
                     location = lastWP.getDisplayName();
                  }
               }

               if (location != null) {
                  String speechBank = "miniquest/" + bountyHelpSpeechDir.getBaseName() + "/_bountyHelp_" + helpType.speechName;
                  this.theNPC.sendSpeechBank(entityplayer, speechBank, location, objective);
                  this.bountyHelpConsumer.apply(entityplayer);
                  return true;
               }
            }
         }
      }

      return false;
   }

   private void sendMiniquestOffer(EntityPlayer entityplayer, LOTRMiniQuest quest) {
      NBTTagCompound nbt = new NBTTagCompound();
      quest.writeToNBT(nbt);
      LOTRPacketMiniquestOffer packet = new LOTRPacketMiniquestOffer(this.theNPC.func_145782_y(), nbt);
      LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      this.addOpenOfferPlayer(entityplayer);
   }

   public void receiveOfferResponse(EntityPlayer entityplayer, boolean accept) {
      this.removeOpenOfferPlayer(entityplayer);
      if (accept) {
         boolean[] container = new boolean[1];
         LOTRMiniQuest quest = this.getOfferFor(entityplayer, container);
         boolean isSpecific = container[0];
         if (quest != null && quest.isValidQuest() && this.canOfferQuestsTo(entityplayer)) {
            quest.setPlayerData(LOTRLevelData.getData(entityplayer));
            quest.start(entityplayer, this.theNPC);
            if (isSpecific) {
               this.clearPlayerSpecificOffer(entityplayer);
            } else {
               this.clearMiniQuestOffer();
            }
         }
      }

   }

   public void writeToNBT(NBTTagCompound nbt) {
      if (this.miniquestOffer != null) {
         NBTTagCompound questData = new NBTTagCompound();
         this.miniquestOffer.writeToNBT(questData);
         nbt.func_74782_a("MQOffer", questData);
      }

      nbt.func_74768_a("MQOfferTime", this.offerTime);
      NBTTagList specificTags = new NBTTagList();
      Iterator var3 = this.playerSpecificOffers.entrySet().iterator();

      UUID player;
      while(var3.hasNext()) {
         Entry e = (Entry)var3.next();
         player = (UUID)e.getKey();
         LOTRMiniQuest offer = (LOTRMiniQuest)e.getValue();
         NBTTagCompound offerData = new NBTTagCompound();
         offerData.func_74778_a("OfferPlayerID", player.toString());
         offer.writeToNBT(offerData);
         specificTags.func_74742_a(offerData);
      }

      nbt.func_74782_a("MQSpecificOffers", specificTags);
      NBTTagList activeQuestTags = new NBTTagList();
      Iterator var10 = this.activeQuestPlayers.iterator();

      while(var10.hasNext()) {
         player = (UUID)var10.next();
         String s = player.toString();
         activeQuestTags.func_74742_a(new NBTTagString(s));
      }

      nbt.func_74782_a("ActiveQuestPlayers", activeQuestTags);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      if (nbt.func_150297_b("MQOffer", 10)) {
         NBTTagCompound questData = nbt.func_74775_l("MQOffer");
         this.miniquestOffer = LOTRMiniQuest.loadQuestFromNBT(questData, (LOTRPlayerData)null);
      }

      this.offerTime = nbt.func_74762_e("MQOfferTime");
      this.playerSpecificOffers.clear();
      int i;
      UUID playerID;
      NBTTagList activeQuestTags;
      if (nbt.func_74764_b("MQSpecificOffers")) {
         activeQuestTags = nbt.func_150295_c("MQSpecificOffers", 10);

         for(i = 0; i < activeQuestTags.func_74745_c(); ++i) {
            NBTTagCompound offerData = activeQuestTags.func_150305_b(i);

            try {
               playerID = UUID.fromString(offerData.func_74779_i("OfferPlayerID"));
               LOTRMiniQuest offer = LOTRMiniQuest.loadQuestFromNBT(offerData, (LOTRPlayerData)null);
               if (offer != null && offer.isValidQuest()) {
                  this.playerSpecificOffers.put(playerID, offer);
               }
            } catch (Exception var7) {
               FMLLog.warning("Error loading NPC player-specific miniquest offer", new Object[0]);
               var7.printStackTrace();
            }
         }
      }

      this.activeQuestPlayers.clear();
      activeQuestTags = nbt.func_150295_c("ActiveQuestPlayers", 8);

      for(i = 0; i < activeQuestTags.func_74745_c(); ++i) {
         String s = activeQuestTags.func_150307_f(i);
         playerID = UUID.fromString(s);
         if (playerID != null) {
            this.activeQuestPlayers.add(playerID);
         }
      }

      if (nbt.func_74764_b("NPCMiniQuestPlayer")) {
         UUID player = UUID.fromString(nbt.func_74779_i("NPCMiniQuestPlayer"));
         if (player != null) {
            this.activeQuestPlayers.add(player);
         }
      }

   }

   public void sendData(EntityPlayerMP entityplayer) {
      LOTRMiniQuest questOffer = this.getOfferFor(entityplayer);
      boolean isOffering = questOffer != null && this.canOfferQuestsTo(entityplayer);
      int color = questOffer != null ? questOffer.getQuestColor() : 0;
      boolean prevOffering = false;
      UUID uuid = entityplayer.func_110124_au();
      if (this.playerPacketCache.containsKey(uuid)) {
         prevOffering = (Boolean)this.playerPacketCache.get(uuid);
      }

      this.playerPacketCache.put(uuid, isOffering);
      if (isOffering != prevOffering) {
         LOTRPacketNPCIsOfferingQuest packet = new LOTRPacketNPCIsOfferingQuest(this.theNPC.func_145782_y(), isOffering, color);
         LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
      }

   }

   private void sendDataToAllWatchers() {
      int x = MathHelper.func_76128_c(this.theNPC.field_70165_t) >> 4;
      int z = MathHelper.func_76128_c(this.theNPC.field_70161_v) >> 4;
      PlayerManager playermanager = ((WorldServer)this.theNPC.field_70170_p).func_73040_p();
      List players = this.theNPC.field_70170_p.field_73010_i;
      Iterator var5 = players.iterator();

      while(var5.hasNext()) {
         Object obj = var5.next();
         EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
         if (playermanager.func_72694_a(entityplayer, x, z)) {
            this.sendData(entityplayer);
         }
      }

   }

   public void receiveData(LOTRPacketNPCIsOfferingQuest packet) {
      this.clientIsOffering = packet.offering;
      this.clientOfferColor = packet.offerColor;
   }

   public void onDeath() {
      if (!this.theNPC.field_70170_p.field_72995_K && !this.activeQuestPlayers.isEmpty()) {
         Iterator var1 = this.activeQuestPlayers.iterator();

         while(var1.hasNext()) {
            UUID player = (UUID)var1.next();
            List playerQuests = LOTRLevelData.getData(player).getMiniQuestsForEntity(this.theNPC, true);
            Iterator var4 = playerQuests.iterator();

            while(var4.hasNext()) {
               LOTRMiniQuest quest = (LOTRMiniQuest)var4.next();
               if (quest.isActive()) {
                  quest.setEntityDead();
               }
            }
         }
      }

   }
}
