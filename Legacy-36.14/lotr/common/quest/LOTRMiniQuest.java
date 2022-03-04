package lotr.common.quest;

import cpw.mods.fml.common.FMLLog;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRDate;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLore;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.entity.npc.LOTRSpeech;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemCoin;
import lotr.common.item.LOTRItemModifierTemplate;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;
import org.apache.commons.lang3.tuple.Pair;

public abstract class LOTRMiniQuest {
   private static Map nameToQuestMapping = new HashMap();
   private static Map questToNameMapping = new HashMap();
   public static int MAX_MINIQUESTS_PER_FACTION;
   public static double RENDER_HEAD_DISTANCE;
   public LOTRMiniQuestFactory questGroup;
   private LOTRPlayerData playerData;
   public UUID questUUID;
   public UUID entityUUID;
   public String entityName;
   public String entityNameFull;
   public LOTRFaction entityFaction;
   private int questColor;
   public int dateGiven;
   public LOTRBiome biomeGiven;
   public float rewardFactor = 1.0F;
   public static final float defaultRewardFactor = 1.0F;
   public boolean willHire = false;
   public float hiringAlignment;
   public List rewardItemTable = new ArrayList();
   private boolean completed;
   public int dateCompleted;
   public int coinsRewarded;
   public float alignmentRewarded;
   public boolean wasHired;
   public List itemsRewarded = new ArrayList();
   private boolean entityDead;
   private Pair lastLocation;
   public String speechBankStart;
   public String speechBankProgress;
   public String speechBankComplete;
   public String speechBankTooMany;
   public String quoteStart;
   public String quoteComplete;
   public List quotesStages = new ArrayList();

   private static void registerQuestType(String name, Class questType) {
      nameToQuestMapping.put(name, questType);
      questToNameMapping.put(questType, name);
   }

   public LOTRMiniQuest(LOTRPlayerData pd) {
      this.playerData = pd;
      this.questUUID = UUID.randomUUID();
   }

   public void setPlayerData(LOTRPlayerData pd) {
      this.playerData = pd;
   }

   public LOTRPlayerData getPlayerData() {
      return this.playerData;
   }

   public void setNPCInfo(LOTREntityNPC npc) {
      this.entityUUID = npc.func_110124_au();
      this.entityName = npc.getNPCName();
      this.entityNameFull = npc.func_70005_c_();
      this.entityFaction = npc.getFaction();
      this.questColor = npc.getMiniquestColor();
   }

   public void writeToNBT(NBTTagCompound nbt) {
      nbt.func_74778_a("QuestType", (String)questToNameMapping.get(this.getClass()));
      if (this.questGroup != null) {
         nbt.func_74778_a("QuestGroup", this.questGroup.getBaseName());
      }

      nbt.func_74778_a("QuestUUID", this.questUUID.toString());
      nbt.func_74778_a("EntityUUID", this.entityUUID.toString());
      nbt.func_74778_a("Owner", this.entityName);
      nbt.func_74778_a("OwnerFull", this.entityNameFull);
      nbt.func_74778_a("Faction", this.entityFaction.codeName());
      nbt.func_74768_a("Color", this.questColor);
      nbt.func_74768_a("DateGiven", this.dateGiven);
      if (this.biomeGiven != null) {
         nbt.func_74774_a("BiomeID", (byte)this.biomeGiven.field_76756_M);
         nbt.func_74778_a("BiomeDim", this.biomeGiven.biomeDimension.dimensionName);
      }

      nbt.func_74776_a("RewardFactor", this.rewardFactor);
      nbt.func_74757_a("WillHire", this.willHire);
      nbt.func_74776_a("HiringAlignF", this.hiringAlignment);
      NBTTagList stageTags;
      Iterator var3;
      ItemStack item;
      NBTTagCompound itemData;
      if (!this.rewardItemTable.isEmpty()) {
         stageTags = new NBTTagList();
         var3 = this.rewardItemTable.iterator();

         while(var3.hasNext()) {
            item = (ItemStack)var3.next();
            itemData = new NBTTagCompound();
            item.func_77955_b(itemData);
            stageTags.func_74742_a(itemData);
         }

         nbt.func_74782_a("RewardItemTable", stageTags);
      }

      nbt.func_74757_a("Completed", this.completed);
      nbt.func_74768_a("DateCompleted", this.dateCompleted);
      nbt.func_74777_a("CoinReward", (short)this.coinsRewarded);
      nbt.func_74776_a("AlignRewardF", this.alignmentRewarded);
      nbt.func_74757_a("WasHired", this.wasHired);
      if (!this.itemsRewarded.isEmpty()) {
         stageTags = new NBTTagList();
         var3 = this.itemsRewarded.iterator();

         while(var3.hasNext()) {
            item = (ItemStack)var3.next();
            itemData = new NBTTagCompound();
            item.func_77955_b(itemData);
            stageTags.func_74742_a(itemData);
         }

         nbt.func_74782_a("ItemRewards", stageTags);
      }

      nbt.func_74757_a("OwnerDead", this.entityDead);
      if (this.lastLocation != null) {
         ChunkCoordinates coords = (ChunkCoordinates)this.lastLocation.getLeft();
         nbt.func_74768_a("XPos", coords.field_71574_a);
         nbt.func_74768_a("YPos", coords.field_71572_b);
         nbt.func_74768_a("ZPos", coords.field_71573_c);
         nbt.func_74768_a("Dimension", (Integer)this.lastLocation.getRight());
      }

      nbt.func_74778_a("SpeechStart", this.speechBankStart);
      nbt.func_74778_a("SpeechProgress", this.speechBankProgress);
      nbt.func_74778_a("SpeechComplete", this.speechBankComplete);
      nbt.func_74778_a("SpeechTooMany", this.speechBankTooMany);
      nbt.func_74778_a("QuoteStart", this.quoteStart);
      nbt.func_74778_a("QuoteComplete", this.quoteComplete);
      if (!this.quotesStages.isEmpty()) {
         stageTags = new NBTTagList();
         var3 = this.quotesStages.iterator();

         while(var3.hasNext()) {
            String s = (String)var3.next();
            stageTags.func_74742_a(new NBTTagString(s));
         }

         nbt.func_74782_a("QuotesStages", stageTags);
      }

   }

   public void readFromNBT(NBTTagCompound nbt) {
      String recovery;
      if (nbt.func_74764_b("QuestGroup")) {
         recovery = nbt.func_74779_i("QuestGroup");
         LOTRMiniQuestFactory factory = LOTRMiniQuestFactory.forName(recovery);
         if (factory != null) {
            this.questGroup = factory;
         }
      }

      if (nbt.func_74764_b("QuestUUID")) {
         UUID u = UUID.fromString(nbt.func_74779_i("QuestUUID"));
         if (u != null) {
            this.questUUID = u;
         }
      }

      if (nbt.func_74764_b("UUIDMost") && nbt.func_74764_b("UUIDLeast")) {
         this.entityUUID = new UUID(nbt.func_74763_f("UUIDMost"), nbt.func_74763_f("UUIDLeast"));
      } else {
         this.entityUUID = UUID.fromString(nbt.func_74779_i("EntityUUID"));
      }

      this.entityName = nbt.func_74779_i("Owner");
      if (nbt.func_74764_b("OwnerFull")) {
         this.entityNameFull = nbt.func_74779_i("OwnerFull");
      } else {
         this.entityNameFull = this.entityName;
      }

      this.entityFaction = LOTRFaction.forName(nbt.func_74779_i("Faction"));
      if (nbt.func_74764_b("Color")) {
         this.questColor = nbt.func_74762_e("Color");
      } else {
         this.questColor = this.entityFaction.getFactionColor();
      }

      this.dateGiven = nbt.func_74762_e("DateGiven");
      if (nbt.func_74764_b("BiomeID")) {
         int biomeID = nbt.func_74771_c("BiomeID") & 255;
         String biomeDimName = nbt.func_74779_i("BiomeDim");
         LOTRDimension biomeDim = LOTRDimension.forName(biomeDimName);
         if (biomeDim != null) {
            this.biomeGiven = biomeDim.biomeList[biomeID];
         }
      }

      if (nbt.func_74764_b("RewardFactor")) {
         this.rewardFactor = nbt.func_74760_g("RewardFactor");
      } else {
         this.rewardFactor = 1.0F;
      }

      this.willHire = nbt.func_74767_n("WillHire");
      if (nbt.func_74764_b("HiringAlignment")) {
         this.hiringAlignment = (float)nbt.func_74762_e("HiringAlignment");
      } else {
         this.hiringAlignment = nbt.func_74760_g("HiringAlignF");
      }

      this.rewardItemTable.clear();
      ItemStack item;
      NBTTagList stageTags;
      int i1;
      NBTTagCompound itemData;
      if (nbt.func_74764_b("RewardItemTable")) {
         stageTags = nbt.func_150295_c("RewardItemTable", 10);

         for(i1 = 0; i1 < stageTags.func_74745_c(); ++i1) {
            itemData = stageTags.func_150305_b(i1);
            item = ItemStack.func_77949_a(itemData);
            if (item != null) {
               this.rewardItemTable.add(item);
            }
         }
      }

      this.completed = nbt.func_74767_n("Completed");
      this.dateCompleted = nbt.func_74762_e("DateCompleted");
      this.coinsRewarded = nbt.func_74765_d("CoinReward");
      if (nbt.func_74764_b("AlignmentReward")) {
         this.alignmentRewarded = (float)nbt.func_74765_d("AlignmentReward");
      } else {
         this.alignmentRewarded = nbt.func_74760_g("AlignRewardF");
      }

      this.wasHired = nbt.func_74767_n("WasHired");
      this.itemsRewarded.clear();
      if (nbt.func_74764_b("ItemRewards")) {
         stageTags = nbt.func_150295_c("ItemRewards", 10);

         for(i1 = 0; i1 < stageTags.func_74745_c(); ++i1) {
            itemData = stageTags.func_150305_b(i1);
            item = ItemStack.func_77949_a(itemData);
            if (item != null) {
               this.itemsRewarded.add(item);
            }
         }
      }

      this.entityDead = nbt.func_74767_n("OwnerDead");
      if (nbt.func_74764_b("Dimension")) {
         ChunkCoordinates coords = new ChunkCoordinates(nbt.func_74762_e("XPos"), nbt.func_74762_e("YPos"), nbt.func_74762_e("ZPos"));
         i1 = nbt.func_74762_e("Dimension");
         this.lastLocation = Pair.of(coords, i1);
      }

      this.speechBankStart = nbt.func_74779_i("SpeechStart");
      this.speechBankProgress = nbt.func_74779_i("SpeechProgress");
      this.speechBankComplete = nbt.func_74779_i("SpeechComplete");
      this.speechBankTooMany = nbt.func_74779_i("SpeechTooMany");
      this.quoteStart = nbt.func_74779_i("QuoteStart");
      this.quoteComplete = nbt.func_74779_i("QuoteComplete");
      this.quotesStages.clear();
      if (nbt.func_74764_b("QuotesStages")) {
         stageTags = nbt.func_150295_c("QuotesStages", 8);

         for(i1 = 0; i1 < stageTags.func_74745_c(); ++i1) {
            String s = stageTags.func_150307_f(i1);
            this.quotesStages.add(s);
         }
      }

      if (this.questGroup == null) {
         recovery = this.speechBankStart;
         if (recovery != null) {
            i1 = recovery.indexOf("/", 0);
            int i2 = recovery.indexOf("/", i1 + 1);
            if (i1 >= 0 && i2 >= 0) {
               recovery = recovery.substring(i1 + 1, i2);
               LOTRMiniQuestFactory factory = LOTRMiniQuestFactory.forName(recovery);
               if (factory != null) {
                  this.questGroup = factory;
               }
            }
         }
      }

   }

   public static LOTRMiniQuest loadQuestFromNBT(NBTTagCompound nbt, LOTRPlayerData playerData) {
      String questTypeName = nbt.func_74779_i("QuestType");
      Class questType = (Class)nameToQuestMapping.get(questTypeName);
      if (questType == null) {
         FMLLog.severe("Could not instantiate miniquest of type " + questTypeName, new Object[0]);
         return null;
      } else {
         LOTRMiniQuest quest = newQuestInstance(questType, playerData);
         quest.readFromNBT(nbt);
         if (quest.isValidQuest()) {
            return quest;
         } else {
            FMLLog.severe("Loaded an invalid LOTR miniquest " + quest.speechBankStart, new Object[0]);
            return null;
         }
      }
   }

   private static LOTRMiniQuest newQuestInstance(Class questType, LOTRPlayerData playerData) {
      try {
         LOTRMiniQuest quest = (LOTRMiniQuest)questType.getConstructor(LOTRPlayerData.class).newInstance(playerData);
         return quest;
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public boolean isValidQuest() {
      return this.entityUUID != null && this.entityFaction != null;
   }

   public boolean canPlayerAccept(EntityPlayer entityplayer) {
      return true;
   }

   public String getFactionSubtitle() {
      return this.entityFaction.isPlayableAlignmentFaction() ? this.entityFaction.factionName() : "";
   }

   public final int getQuestColor() {
      return this.questColor;
   }

   public final float[] getQuestColorComponents() {
      return (new Color(this.getQuestColor())).getColorComponents((float[])null);
   }

   public abstract String getQuestObjective();

   public abstract String getObjectiveInSpeech();

   public abstract String getProgressedObjectiveInSpeech();

   public abstract String getQuestProgress();

   public abstract String getQuestProgressShorthand();

   public abstract float getCompletionFactor();

   public abstract ItemStack getQuestIcon();

   public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
   }

   public boolean onInteractOther(EntityPlayer entityplayer, LOTREntityNPC npc) {
      return false;
   }

   protected void sendProgressSpeechbank(EntityPlayer entityplayer, LOTREntityNPC npc) {
      npc.sendSpeechBank(entityplayer, this.speechBankProgress, this);
   }

   protected void sendCompletedSpeech(EntityPlayer entityplayer, LOTREntityNPC npc) {
      this.sendQuoteSpeech(entityplayer, npc, this.quoteComplete);
   }

   protected void sendQuoteSpeech(EntityPlayer entityplayer, LOTREntityNPC npc, String quote) {
      LOTRSpeech.sendSpeech(entityplayer, npc, LOTRSpeech.formatSpeech(quote, entityplayer, (String)null, this.getObjectiveInSpeech()));
      npc.markNPCSpoken();
   }

   public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
   }

   public void onKilledByPlayer(EntityPlayer entityplayer, EntityPlayer killer) {
   }

   public void onPlayerTick(EntityPlayer entityplayer) {
   }

   public void handleEvent(LOTRMiniQuestEvent event) {
   }

   public final boolean isActive() {
      return !this.isCompleted() && !this.isFailed();
   }

   public boolean isFailed() {
      return this.entityDead;
   }

   public String getQuestFailure() {
      return StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.dead", new Object[]{this.entityName});
   }

   public String getQuestFailureShorthand() {
      return StatCollector.func_74838_a("lotr.gui.redBook.mq.dead");
   }

   public void setEntityDead() {
      this.entityDead = true;
      this.updateQuest();
   }

   public void start(EntityPlayer entityplayer, LOTREntityNPC npc) {
      this.setNPCInfo(npc);
      this.dateGiven = LOTRDate.ShireReckoning.currentDay;
      int i = MathHelper.func_76128_c(entityplayer.field_70165_t);
      int k = MathHelper.func_76128_c(entityplayer.field_70161_v);
      BiomeGenBase biome = entityplayer.field_70170_p.func_72807_a(i, k);
      if (biome instanceof LOTRBiome) {
         this.biomeGiven = (LOTRBiome)biome;
      }

      this.playerData.addMiniQuest(this);
      npc.questInfo.addActiveQuestPlayer(entityplayer);
      this.playerData.setTrackingMiniQuest(this);
   }

   public boolean isCompleted() {
      return this.completed;
   }

   protected void complete(EntityPlayer entityplayer, LOTREntityNPC npc) {
      this.completed = true;
      this.dateCompleted = LOTRDate.ShireReckoning.currentDay;
      Random rand = npc.func_70681_au();
      List dropItems = new ArrayList();
      float alignment = this.getAlignmentBonus();
      if (alignment != 0.0F) {
         alignment *= MathHelper.func_151240_a(rand, 0.75F, 1.25F);
         alignment = Math.max(alignment, 1.0F);
         LOTRAlignmentValues.AlignmentBonus bonus = LOTRAlignmentValues.createMiniquestBonus(alignment);
         LOTRFaction rewardFaction = this.getAlignmentRewardFaction();
         if (!this.questGroup.isNoAlignRewardForEnemy() || this.playerData.getAlignment(rewardFaction) >= 0.0F) {
            LOTRAlignmentBonusMap alignmentMap = this.playerData.addAlignment(entityplayer, bonus, rewardFaction, npc);
            this.alignmentRewarded = (Float)alignmentMap.get(rewardFaction);
         }
      }

      int coins = this.getCoinBonus();
      if (coins != 0) {
         if (this.shouldRandomiseCoinReward()) {
            coins = Math.round((float)coins * MathHelper.func_151240_a(rand, 0.75F, 1.25F));
            if (rand.nextInt(12) == 0) {
               coins *= MathHelper.func_76136_a(rand, 2, 5);
            }
         }

         coins = Math.max(coins, 1);
         this.coinsRewarded = coins;
         int coinsRemain = coins;

         for(int l = LOTRItemCoin.values.length - 1; l >= 0; --l) {
            int coinValue = LOTRItemCoin.values[l];
            if (coinsRemain >= coinValue) {
               int numCoins = coinsRemain / coinValue;
               coinsRemain -= numCoins * coinValue;

               while(numCoins > 64) {
                  numCoins -= 64;
                  dropItems.add(new ItemStack(LOTRMod.silverCoin, 64, l));
               }

               dropItems.add(new ItemStack(LOTRMod.silverCoin, numCoins, l));
            }
         }
      }

      ItemStack mithrilBook;
      if (!this.rewardItemTable.isEmpty()) {
         mithrilBook = (ItemStack)this.rewardItemTable.get(rand.nextInt(this.rewardItemTable.size()));
         dropItems.add(mithrilBook.func_77946_l());
         this.itemsRewarded.add(mithrilBook.func_77946_l());
      }

      ItemStack pouch;
      if (this.canRewardVariousExtraItems()) {
         if (rand.nextInt(10) == 0 && this.questGroup != null && !this.questGroup.getLoreCategories().isEmpty()) {
            LOTRLore lore = LOTRLore.getMultiRandomLore(this.questGroup.getLoreCategories(), rand, true);
            if (lore != null) {
               pouch = lore.createLoreBook(rand);
               dropItems.add(pouch.func_77946_l());
               this.itemsRewarded.add(pouch.func_77946_l());
            }
         }

         if (rand.nextInt(15) == 0) {
            mithrilBook = LOTRItemModifierTemplate.getRandomCommonTemplate(rand);
            dropItems.add(mithrilBook.func_77946_l());
            this.itemsRewarded.add(mithrilBook.func_77946_l());
         }

         if (npc instanceof LOTREntityDwarf && rand.nextInt(10) == 0) {
            mithrilBook = new ItemStack(LOTRMod.mithrilBook);
            dropItems.add(mithrilBook.func_77946_l());
            this.itemsRewarded.add(mithrilBook.func_77946_l());
         }
      }

      if (!dropItems.isEmpty()) {
         boolean givePouch = this.canRewardVariousExtraItems() && rand.nextInt(10) == 0;
         if (givePouch) {
            pouch = npc.createNPCPouchDrop();
            npc.fillPouchFromListAndRetainUnfilled(pouch, dropItems);
            npc.func_70099_a(pouch, 0.0F);
            ItemStack pouchCopy = pouch.func_77946_l();
            pouchCopy.func_77982_d((NBTTagCompound)null);
            this.itemsRewarded.add(pouchCopy);
         }

         npc.dropItemList(dropItems);
      }

      if (this.willHire) {
         LOTRUnitTradeEntry tradeEntry = new LOTRUnitTradeEntry(npc.getClass(), 0, this.hiringAlignment);
         tradeEntry.setTask(LOTRHiredNPCInfo.Task.WARRIOR);
         npc.hiredNPCInfo.hireUnit(entityplayer, false, this.entityFaction, tradeEntry, (String)null, npc.field_70154_o);
         this.wasHired = true;
      }

      this.updateQuest();
      this.playerData.completeMiniQuest(this);
      this.sendCompletedSpeech(entityplayer, npc);
      if (this.questGroup != null) {
         LOTRAchievement achievement = this.questGroup.getAchievement();
         if (achievement != null) {
            this.playerData.addAchievement(achievement);
         }
      }

   }

   public final LOTRFaction getAlignmentRewardFaction() {
      return this.questGroup.checkAlignmentRewardFaction(this.entityFaction);
   }

   public boolean anyRewardsGiven() {
      return this.alignmentRewarded > 0.0F || this.coinsRewarded > 0 || !this.itemsRewarded.isEmpty();
   }

   public void updateLocation(LOTREntityNPC npc) {
      int i = MathHelper.func_76128_c(npc.field_70165_t);
      int j = MathHelper.func_76128_c(npc.field_70163_u);
      int k = MathHelper.func_76128_c(npc.field_70161_v);
      ChunkCoordinates coords = new ChunkCoordinates(i, j, k);
      int dim = npc.field_71093_bK;
      ChunkCoordinates prevCoords = null;
      if (this.lastLocation != null) {
         prevCoords = (ChunkCoordinates)this.lastLocation.getLeft();
      }

      this.lastLocation = Pair.of(coords, dim);
      boolean sendUpdate = false;
      if (prevCoords == null) {
         sendUpdate = true;
      } else {
         sendUpdate = (double)coords.func_82371_e(prevCoords) > 256.0D;
      }

      if (sendUpdate) {
         this.updateQuest();
      }

   }

   public ChunkCoordinates getLastLocation() {
      return this.lastLocation == null ? null : (ChunkCoordinates)this.lastLocation.getLeft();
   }

   protected void updateQuest() {
      this.playerData.updateMiniQuest(this);
   }

   public abstract float getAlignmentBonus();

   public abstract int getCoinBonus();

   protected boolean shouldRandomiseCoinReward() {
      return true;
   }

   protected boolean canRewardVariousExtraItems() {
      return true;
   }

   static {
      registerQuestType("Collect", LOTRMiniQuestCollect.class);
      registerQuestType("KillFaction", LOTRMiniQuestKillFaction.class);
      registerQuestType("KillEntity", LOTRMiniQuestKillEntity.class);
      registerQuestType("Bounty", LOTRMiniQuestBounty.class);
      registerQuestType("Welcome", LOTRMiniQuestWelcome.class);
      registerQuestType("Pickpocket", LOTRMiniQuestPickpocket.class);
      MAX_MINIQUESTS_PER_FACTION = 5;
      RENDER_HEAD_DISTANCE = 12.0D;
   }

   public static class SorterAlphabetical implements Comparator {
      public int compare(LOTRMiniQuest q1, LOTRMiniQuest q2) {
         if (!q2.isActive() && q1.isActive()) {
            return 1;
         } else if (!q1.isActive() && q2.isActive()) {
            return -1;
         } else {
            return q1.entityFaction == q2.entityFaction ? q1.entityName.compareTo(q2.entityName) : Integer.valueOf(q1.entityFaction.ordinal()).compareTo(q2.entityFaction.ordinal());
         }
      }
   }

   public abstract static class QuestFactoryBase {
      private LOTRMiniQuestFactory questFactoryGroup;
      private String questName;
      private float rewardFactor = 1.0F;
      private boolean willHire = false;
      private float hiringAlignment;
      private List rewardItems;

      public QuestFactoryBase(String name) {
         this.questName = name;
      }

      public void setFactoryGroup(LOTRMiniQuestFactory factory) {
         this.questFactoryGroup = factory;
      }

      public LOTRMiniQuestFactory getFactoryGroup() {
         return this.questFactoryGroup;
      }

      public LOTRMiniQuest.QuestFactoryBase setRewardFactor(float f) {
         this.rewardFactor = f;
         return this;
      }

      public LOTRMiniQuest.QuestFactoryBase setHiring(float f) {
         this.willHire = true;
         this.hiringAlignment = f;
         return this;
      }

      public LOTRMiniQuest.QuestFactoryBase setRewardItems(ItemStack[] items) {
         this.rewardItems = Arrays.asList(items);
         return this;
      }

      public abstract Class getQuestClass();

      public LOTRMiniQuest createQuest(LOTREntityNPC npc, Random rand) {
         LOTRMiniQuest quest = LOTRMiniQuest.newQuestInstance(this.getQuestClass(), (LOTRPlayerData)null);
         quest.questGroup = this.getFactoryGroup();
         String pathName = "miniquest/" + this.getFactoryGroup().getBaseName() + "/";
         String pathNameBaseSpeech = "miniquest/" + this.getFactoryGroup().getBaseSpeechGroup().getBaseName() + "/";
         String questPathName = pathName + this.questName + "_";
         quest.speechBankStart = questPathName + "start";
         quest.speechBankProgress = questPathName + "progress";
         quest.speechBankComplete = questPathName + "complete";
         quest.speechBankTooMany = pathNameBaseSpeech + "_tooMany";
         quest.quoteStart = LOTRSpeech.getRandomSpeech(quest.speechBankStart);
         quest.quoteComplete = LOTRSpeech.getRandomSpeech(quest.speechBankComplete);
         quest.setNPCInfo(npc);
         quest.rewardFactor = this.rewardFactor;
         quest.willHire = this.willHire;
         quest.hiringAlignment = this.hiringAlignment;
         if (this.rewardItems != null) {
            quest.rewardItemTable.addAll(this.rewardItems);
         }

         return quest;
      }
   }
}
