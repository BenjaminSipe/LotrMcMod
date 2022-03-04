package lotr.common.quest;

import cpw.mods.fml.common.FMLLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLore;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityCrocodile;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntityLion;
import lotr.common.entity.animal.LOTREntityLioness;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import lotr.common.entity.npc.LOTREntityAngmarOrc;
import lotr.common.entity.npc.LOTREntityAngmarWarg;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import lotr.common.entity.npc.LOTREntityDaleMan;
import lotr.common.entity.npc.LOTREntityDaleSoldier;
import lotr.common.entity.npc.LOTREntityDarkHuorn;
import lotr.common.entity.npc.LOTREntityDolAmrothSoldier;
import lotr.common.entity.npc.LOTREntityDolGuldurOrc;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityGaladhrimElf;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.entity.npc.LOTREntityGondorRenegade;
import lotr.common.entity.npc.LOTREntityGondorSoldier;
import lotr.common.entity.npc.LOTREntityGundabadOrc;
import lotr.common.entity.npc.LOTREntityGundabadWarg;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.entity.npc.LOTREntityHighElf;
import lotr.common.entity.npc.LOTREntityIsengardSnaga;
import lotr.common.entity.npc.LOTREntityLossarnachAxeman;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.entity.npc.LOTREntityMordorWarg;
import lotr.common.entity.npc.LOTREntityMoredainWarrior;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityNearHaradrimWarrior;
import lotr.common.entity.npc.LOTREntityRangerIthilien;
import lotr.common.entity.npc.LOTREntityRangerNorth;
import lotr.common.entity.npc.LOTREntityRivendellElf;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntitySwanKnight;
import lotr.common.entity.npc.LOTREntityTauredainBlowgunner;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.entity.npc.LOTREntityUrukHai;
import lotr.common.entity.npc.LOTREntityUrukWarg;
import lotr.common.entity.npc.LOTREntityWoodElf;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public enum LOTRMiniQuestFactory {
   HOBBIT("hobbit"),
   BREE("bree"),
   RUFFIAN_SPY("ruffianSpy"),
   RUFFIAN_BRUTE("ruffianBrute"),
   RANGER_NORTH("rangerNorth"),
   RANGER_NORTH_ARNOR_RELIC("rangerNorthArnorRelic"),
   BLUE_MOUNTAINS("blueMountains"),
   HIGH_ELF("highElf"),
   RIVENDELL("rivendell"),
   GUNDABAD("gundabad"),
   ANGMAR("angmar"),
   ANGMAR_HILLMAN("angmarHillman"),
   WOOD_ELF("woodElf"),
   DOL_GULDUR("dolGuldur"),
   DALE("dale"),
   DURIN("durin"),
   GALADHRIM("galadhrim"),
   DUNLAND("dunland"),
   ISENGARD("isengard"),
   ENT("ent"),
   ROHAN("rohan"),
   ROHAN_SHIELDMAIDEN("rohanShieldmaiden"),
   GONDOR("gondor"),
   GONDOR_KILL_RENEGADE("gondorKillRenegade"),
   MORDOR("mordor"),
   DORWINION("dorwinion"),
   DORWINION_ELF("dorwinionElf"),
   RHUN("rhun"),
   HARNENNOR("harnennor"),
   NEAR_HARAD("nearHarad"),
   UMBAR("umbar"),
   CORSAIR("corsair"),
   GONDOR_RENEGADE("gondorRenegade"),
   NOMAD("nomad"),
   GULF_HARAD("gulfHarad"),
   MOREDAIN("moredain"),
   TAUREDAIN("tauredain"),
   HALF_TROLL("halfTroll");

   private static Random rand = new Random();
   private static Map questClassWeights = new HashMap();
   private String baseName;
   private LOTRMiniQuestFactory baseSpeechGroup;
   private Map questFactories = new HashMap();
   private LOTRAchievement questAchievement;
   private List loreCategories = new ArrayList();
   private LOTRFaction alignmentRewardOverride;
   private boolean noAlignRewardForEnemy = false;

   private LOTRMiniQuestFactory(String s) {
      this.baseName = s;
   }

   public String getBaseName() {
      return this.baseName;
   }

   public LOTRMiniQuestFactory getBaseSpeechGroup() {
      return this.baseSpeechGroup != null ? this.baseSpeechGroup : this;
   }

   private void setBaseSpeechGroup(LOTRMiniQuestFactory qf) {
      this.baseSpeechGroup = qf;
   }

   private void addQuest(LOTRMiniQuest.QuestFactoryBase factory) {
      Class questClass = factory.getQuestClass();
      Class registryClass = null;
      Iterator var4 = questClassWeights.keySet().iterator();

      Class c;
      while(var4.hasNext()) {
         c = (Class)var4.next();
         if (questClass.equals(c)) {
            registryClass = c;
            break;
         }
      }

      if (registryClass == null) {
         var4 = questClassWeights.keySet().iterator();

         while(var4.hasNext()) {
            c = (Class)var4.next();
            if (c.isAssignableFrom(questClass)) {
               registryClass = c;
               break;
            }
         }
      }

      if (registryClass == null) {
         throw new IllegalArgumentException("Could not find registered quest class for " + questClass.toString());
      } else {
         factory.setFactoryGroup(this);
         List list = (List)this.questFactories.get(registryClass);
         if (list == null) {
            list = new ArrayList();
            this.questFactories.put(registryClass, list);
         }

         ((List)list).add(factory);
      }
   }

   public LOTRMiniQuest createQuest(LOTREntityNPC npc) {
      int totalWeight = getTotalQuestClassWeight(this);
      if (totalWeight <= 0) {
         FMLLog.warning("LOTR: No quests registered for %s!", new Object[]{this.baseName});
         return null;
      } else {
         int randomWeight = rand.nextInt(totalWeight);
         int i = randomWeight;
         Iterator iterator = this.questFactories.entrySet().iterator();
         List chosenFactoryList = null;

         while(iterator.hasNext()) {
            Entry next = (Entry)iterator.next();
            chosenFactoryList = (List)next.getValue();
            i -= getQuestClassWeight((Class)next.getKey());
            if (i < 0) {
               break;
            }
         }

         LOTRMiniQuest.QuestFactoryBase factory = (LOTRMiniQuest.QuestFactoryBase)chosenFactoryList.get(rand.nextInt(chosenFactoryList.size()));
         LOTRMiniQuest quest = factory.createQuest(npc, rand);
         if (quest != null) {
            quest.questGroup = this;
         }

         return quest;
      }
   }

   private void setAchievement(LOTRAchievement a) {
      if (this.questAchievement != null) {
         throw new IllegalArgumentException("Miniquest achievement is already registered");
      } else {
         this.questAchievement = a;
      }
   }

   public LOTRAchievement getAchievement() {
      return this.questAchievement;
   }

   private void setLore(LOTRLore.LoreCategory... categories) {
      this.loreCategories = Arrays.asList(categories);
   }

   public List getLoreCategories() {
      return this.loreCategories;
   }

   private LOTRMiniQuestFactory setAlignmentRewardOverride(LOTRFaction fac) {
      this.alignmentRewardOverride = fac;
      return this;
   }

   private LOTRMiniQuestFactory setNoAlignRewardForEnemy() {
      this.noAlignRewardForEnemy = true;
      return this;
   }

   public LOTRFaction checkAlignmentRewardFaction(LOTRFaction fac) {
      return this.alignmentRewardOverride != null ? this.alignmentRewardOverride : fac;
   }

   public boolean isNoAlignRewardForEnemy() {
      return this.noAlignRewardForEnemy;
   }

   private static void registerQuestClass(Class questClass, int weight) {
      questClassWeights.put(questClass, weight);
   }

   private static int getQuestClassWeight(Class questClass) {
      Integer i = (Integer)questClassWeights.get(questClass);
      if (i == null) {
         throw new RuntimeException("Encountered a registered quest class " + questClass.toString() + " which is not assigned a weight");
      } else {
         return i;
      }
   }

   private static int getTotalQuestClassWeight(LOTRMiniQuestFactory factory) {
      Set registeredQuestTypes = new HashSet();
      Iterator var2 = factory.questFactories.entrySet().iterator();

      Class c;
      while(var2.hasNext()) {
         Entry entry = (Entry)var2.next();
         c = (Class)entry.getKey();
         registeredQuestTypes.add(c);
      }

      int totalWeight = 0;

      for(Iterator var6 = registeredQuestTypes.iterator(); var6.hasNext(); totalWeight += getQuestClassWeight(c)) {
         c = (Class)var6.next();
      }

      return totalWeight;
   }

   public static void createMiniQuests() {
      registerQuestClass(LOTRMiniQuestCollect.class, 10);
      registerQuestClass(LOTRMiniQuestPickpocket.class, 6);
      registerQuestClass(LOTRMiniQuestKill.class, 8);
      registerQuestClass(LOTRMiniQuestBounty.class, 4);
      HOBBIT.setAchievement(LOTRAchievement.doMiniquestHobbit);
      HOBBIT.setLore(LOTRLore.LoreCategory.SHIRE);
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("pipeweed")).setCollectItem(new ItemStack(LOTRMod.pipeweed), 20, 40).setRewardFactor(0.25F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugAle), 1, 6).setRewardFactor(3.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugCider), 1, 6).setRewardFactor(3.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugPerry), 1, 6).setRewardFactor(3.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugMead), 1, 6).setRewardFactor(4.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugCherryLiqueur), 1, 6).setRewardFactor(3.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(Items.field_151034_e), 4, 10).setRewardFactor(2.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.appleGreen), 4, 10).setRewardFactor(2.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.pear), 4, 10).setRewardFactor(2.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.plum), 4, 10).setRewardFactor(2.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.cherry), 4, 10).setRewardFactor(2.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("uncleBirthday")).setCollectItem(new ItemStack(LOTRMod.appleCrumbleItem), 1, 3).setRewardFactor(5.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("uncleBirthday")).setCollectItem(new ItemStack(LOTRMod.cherryPieItem), 1, 3).setRewardFactor(5.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("uncleBirthday")).setCollectItem(new ItemStack(LOTRMod.berryPieItem), 1, 3).setRewardFactor(5.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("uncleBirthday")).setCollectItem(new ItemStack(Items.field_151025_P), 4, 12).setRewardFactor(1.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("uncleBirthday")).setCollectItem(new ItemStack(Items.field_151077_bg), 4, 8).setRewardFactor(2.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("uncleBirthday")).setCollectItem(new ItemStack(Items.field_151157_am), 4, 8).setRewardFactor(2.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("uncleBirthday")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 4, 8).setRewardFactor(2.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("uncleBirthday")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 4, 8).setRewardFactor(2.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("farmingTool")).setCollectItem(new ItemStack(Items.field_151019_K), 1, 3).setRewardFactor(4.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("farmingTool")).setCollectItem(new ItemStack(LOTRMod.hoeBronze), 1, 3).setRewardFactor(4.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("farmingTool")).setCollectItem(new ItemStack(Items.field_151133_ar), 1, 4).setRewardFactor(3.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("firewood")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 10, 30).setRewardFactor(0.5F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("firewood")).setCollectItem(new ItemStack(LOTRMod.wood, 1, 0), 10, 30).setRewardFactor(0.5F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("firewood")).setCollectItem(new ItemStack(LOTRMod.wood4, 1, 0), 10, 30).setRewardFactor(0.5F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("firewood")).setCollectItem(new ItemStack(LOTRMod.wood6, 1, 1), 10, 30).setRewardFactor(0.5F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("kitchenware")).setCollectItem(new ItemStack(LOTRMod.plate), 5, 12).setRewardFactor(2.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("kitchenware")).setCollectItem(new ItemStack(LOTRMod.clayPlate), 5, 12).setRewardFactor(1.5F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("kitchenware")).setCollectItem(new ItemStack(LOTRMod.mug), 5, 15).setRewardFactor(1.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("books")).setCollectItem(new ItemStack(Items.field_151122_aG), 4, 10).setRewardFactor(2.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("pastries")).setCollectItem(new ItemStack(LOTRMod.appleCrumbleItem), 3, 5).setRewardFactor(4.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("pastries")).setCollectItem(new ItemStack(LOTRMod.cherryPieItem), 3, 5).setRewardFactor(4.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("pastries")).setCollectItem(new ItemStack(LOTRMod.berryPieItem), 3, 5).setRewardFactor(4.0F));
      HOBBIT.addQuest((new LOTRMiniQuestCollect.QFCollect("pastries")).setCollectItem(new ItemStack(Items.field_151105_aU), 3, 5).setRewardFactor(4.0F));
      BREE.setAchievement(LOTRAchievement.doMiniquestBree);
      BREE.setLore(LOTRLore.LoreCategory.BREE);
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBucket")).setCollectItem(new ItemStack(Items.field_151133_ar), 1, 4).setRewardFactor(3.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugAle), 1, 6).setRewardFactor(3.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugCider), 1, 6).setRewardFactor(3.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugPerry), 1, 6).setRewardFactor(3.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugMead), 1, 6).setRewardFactor(4.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugCherryLiqueur), 1, 6).setRewardFactor(3.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151025_P), 10, 30).setRewardFactor(0.5F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151083_be), 5, 20).setRewardFactor(0.75F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151157_am), 5, 20).setRewardFactor(0.75F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151077_bg), 5, 20).setRewardFactor(0.75F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 5, 20).setRewardFactor(0.75F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 5, 20).setRewardFactor(0.75F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 3, 15).setRewardFactor(1.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.rabbitStew), 3, 8).setRewardFactor(2.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151168_bH), 10, 30).setRewardFactor(0.5F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151034_e), 3, 12).setRewardFactor(1.5F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.appleGreen), 3, 12).setRewardFactor(1.5F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.pear), 3, 12).setRewardFactor(1.5F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.appleCrumbleItem), 2, 5).setRewardFactor(3.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.cherryPieItem), 2, 5).setRewardFactor(3.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.mugAle), 5, 15).setRewardFactor(1.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.mugCider), 5, 15).setRewardFactor(1.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRangerItem")).setCollectItem(new ItemStack(LOTRMod.helmetRanger), 1, 2).setRewardFactor(8.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRangerItem")).setCollectItem(new ItemStack(LOTRMod.bodyRanger), 1, 2).setRewardFactor(8.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRangerItem")).setCollectItem(new ItemStack(LOTRMod.legsRanger), 1, 2).setRewardFactor(8.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRangerItem")).setCollectItem(new ItemStack(LOTRMod.bootsRanger), 1, 2).setRewardFactor(8.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTool")).setCollectItem(new ItemStack(Items.field_151019_K), 1, 3).setRewardFactor(4.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTool")).setCollectItem(new ItemStack(LOTRMod.hoeBronze), 1, 3).setRewardFactor(4.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTool")).setCollectItem(new ItemStack(Items.field_151036_c), 1, 3).setRewardFactor(4.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTool")).setCollectItem(new ItemStack(LOTRMod.axeBronze), 1, 3).setRewardFactor(4.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTool")).setCollectItem(new ItemStack(Items.field_151037_a), 1, 3).setRewardFactor(4.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTool")).setCollectItem(new ItemStack(LOTRMod.shovelBronze), 1, 3).setRewardFactor(4.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTool")).setCollectItem(new ItemStack(LOTRMod.chisel), 1, 3).setRewardFactor(4.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTool")).setCollectItem(new ItemStack(Items.field_151097_aZ), 1, 3).setRewardFactor(4.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTool")).setCollectItem(new ItemStack(Items.field_151133_ar), 1, 4).setRewardFactor(3.0F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("firewood")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 10, 30).setRewardFactor(0.5F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("firewood")).setCollectItem(new ItemStack(LOTRMod.wood2, 1, 1), 10, 30).setRewardFactor(0.5F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("firewood")).setCollectItem(new ItemStack(LOTRMod.wood3, 1, 0), 10, 30).setRewardFactor(0.5F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("firewood")).setCollectItem(new ItemStack(LOTRMod.wood4, 1, 0), 10, 30).setRewardFactor(0.5F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("firewood")).setCollectItem(new ItemStack(LOTRMod.wood6, 1, 1), 10, 30).setRewardFactor(0.5F));
      BREE.addQuest((new LOTRMiniQuestCollect.QFCollect("pipeweed")).setCollectItem(new ItemStack(LOTRMod.pipeweed), 20, 40).setRewardFactor(0.25F));
      BREE.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killEnemy")).setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
      BREE.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killEnemy")).setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
      BREE.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.GUNDABAD, 10, 30));
      BREE.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killEnemy")).setKillEntity(LOTREntityBandit.class, 1, 3).setRewardFactor(8.0F));
      BREE.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityGundabadOrc.class, 10, 40));
      BREE.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWight")).setKillEntity(LOTREntityBarrowWight.class, 5, 10).setRewardFactor(3.0F));
      BREE.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      RUFFIAN_SPY.setAchievement(LOTRAchievement.doMiniquestRuffianSpy);
      RUFFIAN_SPY.setAlignmentRewardOverride(LOTRFaction.ISENGARD).setNoAlignRewardForEnemy();
      RUFFIAN_SPY.addQuest((new LOTRMiniQuestPickpocket.QFPickpocket("pickpocket")).setPickpocketFaction(LOTRFaction.BREE, 2, 6));
      RUFFIAN_SPY.addQuest((new LOTRMiniQuestPickpocket.QFPickpocket("pickpocketForBoss")).setPickpocketFaction(LOTRFaction.BREE, 2, 8).setRewardFactor(1.5F));
      RUFFIAN_SPY.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPipeweed")).setCollectItem(new ItemStack(LOTRMod.pipeweed), 10, 20).setRewardFactor(0.5F));
      RUFFIAN_SPY.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugAle), 3, 6).setRewardFactor(3.0F));
      RUFFIAN_SPY.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugCider), 3, 6).setRewardFactor(3.0F));
      RUFFIAN_SPY.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugPerry), 3, 6).setRewardFactor(3.0F));
      RUFFIAN_SPY.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugMead), 3, 6).setRewardFactor(4.0F));
      RUFFIAN_SPY.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMathom")).setCollectItem(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.HOBBIT.bannerID), 10, 15).setRewardFactor(1.5F));
      RUFFIAN_SPY.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMathom")).setCollectItem(new ItemStack(LOTRMod.hobbitPipe), 1, 2).setRewardFactor(15.0F));
      RUFFIAN_SPY.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMathom")).setCollectItem(new ItemStack(LOTRMod.pipeweed), 10, 20).setRewardFactor(0.5F));
      RUFFIAN_BRUTE.setAchievement(LOTRAchievement.doMiniquestRuffianBrute);
      RUFFIAN_BRUTE.setAlignmentRewardOverride(LOTRFaction.ISENGARD).setNoAlignRewardForEnemy();
      RUFFIAN_BRUTE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugAle), 3, 6));
      RUFFIAN_BRUTE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugCider), 3, 6));
      RUFFIAN_BRUTE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugPerry), 3, 6));
      RUFFIAN_BRUTE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugMead), 3, 6));
      RUFFIAN_BRUTE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPipeweed")).setCollectItem(new ItemStack(LOTRMod.pipeweed), 20, 40));
      RUFFIAN_BRUTE.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killBreelanders")).setKillFaction(LOTRFaction.BREE, 10, 30));
      Iterator var0 = RUFFIAN_BRUTE.questFactories.values().iterator();

      List qfList;
      Iterator var2;
      LOTRMiniQuest.QuestFactoryBase qf;
      while(var0.hasNext()) {
         qfList = (List)var0.next();
         var2 = qfList.iterator();

         while(var2.hasNext()) {
            qf = (LOTRMiniQuest.QuestFactoryBase)var2.next();
            qf.setRewardFactor(0.0F);
            qf.setHiring(0.0F);
         }
      }

      RANGER_NORTH.setAchievement(LOTRAchievement.doMiniquestRanger);
      RANGER_NORTH.setLore(LOTRLore.LoreCategory.ERIADOR);
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 30, 60).setRewardFactor(0.25F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 1), 30, 60).setRewardFactor(0.25F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(LOTRMod.wood2, 1, 1), 30, 60).setRewardFactor(0.25F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(LOTRMod.wood4, 1, 0), 30, 60).setRewardFactor(0.25F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(LOTRMod.wood6, 1, 1), 30, 60).setRewardFactor(0.25F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBricks")).setCollectItem(new ItemStack(Blocks.field_150417_aV), 40, 100).setRewardFactor(0.2F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBricks")).setCollectItem(new ItemStack(LOTRMod.brick2, 1, 3), 30, 80).setRewardFactor(0.25F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151025_P), 10, 30).setRewardFactor(0.5F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151083_be), 5, 20).setRewardFactor(0.75F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151157_am), 5, 20).setRewardFactor(0.75F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 5, 20).setRewardFactor(0.75F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 5, 20).setRewardFactor(0.75F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 3, 15).setRewardFactor(1.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.mugAle), 5, 15).setRewardFactor(1.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.mugCider), 5, 15).setRewardFactor(1.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectAthelas")).setCollectItem(new ItemStack(LOTRMod.athelas), 2, 6).setRewardFactor(3.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectGondorItem")).setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 1).setRewardFactor(10.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectGondorItem")).setCollectItem(new ItemStack(LOTRMod.helmetGondor), 1, 1).setRewardFactor(10.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectGondorItem")).setCollectItem(new ItemStack(LOTRMod.helmetGondorWinged), 1, 1).setRewardFactor(15.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("craftRangerItem")).setCollectItem(new ItemStack(LOTRMod.helmetRanger), 2, 5).setRewardFactor(3.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("craftRangerItem")).setCollectItem(new ItemStack(LOTRMod.bodyRanger), 2, 5).setRewardFactor(4.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(Items.field_151040_l), 2, 4).setRewardFactor(3.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.daggerIron), 2, 6).setRewardFactor(2.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.rangerBow), 3, 7).setRewardFactor(2.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(Items.field_151032_g), 20, 40).setRewardFactor(0.25F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMaterials")).setCollectItem(new ItemStack(Blocks.field_150325_L, 1, 0), 6, 15).setRewardFactor(1.0F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMaterials")).setCollectItem(new ItemStack(Items.field_151116_aA), 10, 20).setRewardFactor(0.5F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEnemyBones")).setCollectItem(new ItemStack(LOTRMod.orcBone), 10, 40).setRewardFactor(0.5F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEnemyBones")).setCollectItem(new ItemStack(LOTRMod.wargBone), 10, 30).setRewardFactor(0.75F));
      RANGER_NORTH.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad")).setKillFaction(LOTRFaction.GUNDABAD, 10, 40));
      RANGER_NORTH.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killAngmar")).setKillFaction(LOTRFaction.ANGMAR, 10, 30));
      RANGER_NORTH.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killTroll")).setKillEntity(LOTREntityTroll.class, 10, 30));
      RANGER_NORTH.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killMountainTroll")).setKillEntity(LOTREntityMountainTroll.class, 20, 40));
      RANGER_NORTH.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityGundabadWarg.class, 10, 40));
      RANGER_NORTH.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityAngmarWarg.class, 10, 30));
      RANGER_NORTH.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killDarkHuorn")).setKillEntity(LOTREntityDarkHuorn.class, 20, 30));
      RANGER_NORTH.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("avengeBrother")).setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
      RANGER_NORTH.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("avengeBrother")).setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
      RANGER_NORTH.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killHillmen")).setKillEntity(LOTREntityAngmarHillman.class, 10, 30));
      RANGER_NORTH.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      RANGER_NORTH_ARNOR_RELIC.setAchievement(LOTRAchievement.doMiniquestRanger);
      RANGER_NORTH_ARNOR_RELIC.setBaseSpeechGroup(RANGER_NORTH);
      RANGER_NORTH_ARNOR_RELIC.setLore(LOTRLore.LoreCategory.ERIADOR);
      RANGER_NORTH_ARNOR_RELIC.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("arnorRelicKill")).setKillFaction(LOTRFaction.GUNDABAD, 10, 30));
      RANGER_NORTH_ARNOR_RELIC.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("arnorRelicKill")).setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
      RANGER_NORTH_ARNOR_RELIC.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("arnorRelicKill")).setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
      var0 = RANGER_NORTH_ARNOR_RELIC.questFactories.values().iterator();

      while(var0.hasNext()) {
         qfList = (List)var0.next();
         var2 = qfList.iterator();

         while(var2.hasNext()) {
            qf = (LOTRMiniQuest.QuestFactoryBase)var2.next();
            qf.setRewardFactor(0.0F);
            qf.setRewardItems(new ItemStack[]{new ItemStack(LOTRMod.helmetArnor), new ItemStack(LOTRMod.bodyArnor), new ItemStack(LOTRMod.legsArnor), new ItemStack(LOTRMod.bootsArnor), new ItemStack(LOTRMod.swordArnor), new ItemStack(LOTRMod.daggerArnor), new ItemStack(LOTRMod.spearArnor)});
         }
      }

      BLUE_MOUNTAINS.setAchievement(LOTRAchievement.doMiniquestBlueMountains);
      BLUE_MOUNTAINS.setLore(LOTRLore.LoreCategory.BLUE_MOUNTAINS);
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("mineMithril")).setCollectItem(new ItemStack(LOTRMod.mithril), 1, 2).setRewardFactor(50.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 15).setRewardFactor(4.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.silver), 3, 15).setRewardFactor(4.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151114_aO), 5, 15).setRewardFactor(2.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.diamond), 1, 3).setRewardFactor(15.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.sapphire), 1, 3).setRewardFactor(12.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.opal), 1, 3).setRewardFactor(10.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.amethyst), 1, 3).setRewardFactor(10.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon")).setCollectItem(new ItemStack(LOTRMod.hammerBlueDwarven), 1, 3).setRewardFactor(5.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon")).setCollectItem(new ItemStack(LOTRMod.battleaxeBlueDwarven), 1, 3).setRewardFactor(5.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon")).setCollectItem(new ItemStack(LOTRMod.throwingAxeBlueDwarven), 1, 4).setRewardFactor(4.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugDwarvenAle), 2, 5).setRewardFactor(3.0F));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad")).setKillFaction(LOTRFaction.GUNDABAD, 20, 40));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
      BLUE_MOUNTAINS.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
      BLUE_MOUNTAINS.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      HIGH_ELF.setAchievement(LOTRAchievement.doMiniquestHighElf);
      HIGH_ELF.setLore(LOTRLore.LoreCategory.LINDON);
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collect")).setCollectItem(new ItemStack(Blocks.field_150345_g, 1, 2), 5, 20).setRewardFactor(1.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collect")).setCollectItem(new ItemStack(LOTRMod.sapling2, 1, 1), 5, 20).setRewardFactor(1.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBirchWood")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 2), 10, 50).setRewardFactor(0.5F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectGoldenLeaves")).setCollectItem(new ItemStack(LOTRMod.leaves, 1, 1), 10, 20).setRewardFactor(1.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMallornSapling")).setCollectItem(new ItemStack(LOTRMod.sapling, 1, 1), 3, 10).setRewardFactor(2.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMallornNut")).setCollectItem(new ItemStack(LOTRMod.mallornNut), 1, 3).setRewardFactor(5.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectCrystal")).setCollectItem(new ItemStack(LOTRMod.quenditeCrystal), 4, 16).setRewardFactor(1.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.swordHighElven), 1, 4).setRewardFactor(3.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.polearmHighElven), 1, 4).setRewardFactor(3.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.longspearHighElven), 1, 4).setRewardFactor(3.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.spearHighElven), 1, 4).setRewardFactor(2.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.helmetHighElven), 1, 4).setRewardFactor(3.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.bodyHighElven), 1, 4).setRewardFactor(4.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 10).setRewardFactor(4.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.silver), 3, 10).setRewardFactor(4.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.sapphire), 1, 3).setRewardFactor(12.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.opal), 1, 3).setRewardFactor(10.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.pearl), 1, 3).setRewardFactor(15.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDwarven")).setCollectItem(new ItemStack(LOTRMod.brick3, 1, 12), 2, 6).setRewardFactor(4.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 1).setRewardFactor(10.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.helmetGondorWinged), 1, 1).setRewardFactor(15.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.helmetRanger), 1, 1).setRewardFactor(10.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.brick, 1, 1), 10, 20).setRewardFactor(1.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.brick, 1, 5), 3, 5).setRewardFactor(4.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.brick2, 1, 3), 10, 20).setRewardFactor(1.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.brick2, 1, 6), 3, 5).setRewardFactor(3.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150345_g, 1, 0), 2, 5).setRewardFactor(3.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150345_g, 1, 1), 2, 5).setRewardFactor(3.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150345_g, 1, 2), 2, 5).setRewardFactor(2.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150328_O, 1, 0), 2, 8).setRewardFactor(1.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150327_N, 1, 0), 2, 8).setRewardFactor(1.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectString")).setCollectItem(new ItemStack(Items.field_151007_F), 5, 20).setRewardFactor(1.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.helmetOrc), 1, 1).setRewardFactor(10.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.helmetAngmar), 1, 1).setRewardFactor(10.0F));
      HIGH_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityGundabadOrc.class, 10, 40));
      HIGH_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityAngmarOrc.class, 10, 40));
      HIGH_ELF.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad")).setKillFaction(LOTRFaction.GUNDABAD, 10, 40));
      HIGH_ELF.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killAngmar")).setKillFaction(LOTRFaction.ANGMAR, 10, 30));
      HIGH_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
      HIGH_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityAngmarWarg.class, 10, 30));
      HIGH_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killTroll")).setKillEntity(LOTREntityTroll.class, 10, 30));
      HIGH_ELF.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      RIVENDELL.setAchievement(LOTRAchievement.doMiniquestRivendell);
      RIVENDELL.setLore(LOTRLore.LoreCategory.RIVENDELL, LOTRLore.LoreCategory.EREGION);
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collect")).setCollectItem(new ItemStack(Blocks.field_150345_g, 1, 2), 5, 20).setRewardFactor(1.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collect")).setCollectItem(new ItemStack(LOTRMod.sapling2, 1, 1), 5, 20).setRewardFactor(1.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBirchWood")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 2), 10, 50).setRewardFactor(0.5F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectGoldenLeaves")).setCollectItem(new ItemStack(LOTRMod.leaves, 1, 1), 10, 20).setRewardFactor(1.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMallornSapling")).setCollectItem(new ItemStack(LOTRMod.sapling, 1, 1), 3, 10).setRewardFactor(2.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMallornNut")).setCollectItem(new ItemStack(LOTRMod.mallornNut), 1, 3).setRewardFactor(5.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectCrystal")).setCollectItem(new ItemStack(LOTRMod.quenditeCrystal), 4, 16).setRewardFactor(1.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.swordRivendell), 1, 4).setRewardFactor(3.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.polearmRivendell), 1, 4).setRewardFactor(3.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.longspearRivendell), 1, 4).setRewardFactor(3.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.spearRivendell), 1, 4).setRewardFactor(2.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.helmetRivendell), 1, 4).setRewardFactor(3.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.bodyRivendell), 1, 4).setRewardFactor(4.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 10).setRewardFactor(4.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.silver), 3, 10).setRewardFactor(4.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.diamond), 1, 3).setRewardFactor(15.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.emerald), 1, 3).setRewardFactor(12.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.pearl), 1, 3).setRewardFactor(15.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDwarven")).setCollectItem(new ItemStack(LOTRMod.brick3, 1, 12), 2, 6).setRewardFactor(4.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 1).setRewardFactor(10.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.helmetGondorWinged), 1, 1).setRewardFactor(15.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.helmetRanger), 1, 1).setRewardFactor(10.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.brick, 1, 1), 10, 20).setRewardFactor(1.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.brick, 1, 5), 3, 5).setRewardFactor(4.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.brick2, 1, 3), 10, 20).setRewardFactor(1.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectNumenorItem")).setCollectItem(new ItemStack(LOTRMod.brick2, 1, 6), 3, 5).setRewardFactor(3.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150345_g, 1, 0), 2, 5).setRewardFactor(3.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150345_g, 1, 1), 2, 5).setRewardFactor(3.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150345_g, 1, 2), 2, 5).setRewardFactor(2.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150328_O, 1, 0), 2, 8).setRewardFactor(1.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150327_N, 1, 0), 2, 8).setRewardFactor(1.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectString")).setCollectItem(new ItemStack(Items.field_151007_F), 5, 20).setRewardFactor(1.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.helmetOrc), 1, 1).setRewardFactor(10.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.helmetAngmar), 1, 1).setRewardFactor(10.0F));
      RIVENDELL.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityGundabadOrc.class, 10, 40));
      RIVENDELL.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityAngmarOrc.class, 10, 40));
      RIVENDELL.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad")).setKillFaction(LOTRFaction.GUNDABAD, 10, 40));
      RIVENDELL.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killAngmar")).setKillFaction(LOTRFaction.ANGMAR, 10, 30));
      RIVENDELL.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
      RIVENDELL.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityAngmarWarg.class, 10, 30));
      RIVENDELL.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killTroll")).setKillEntity(LOTREntityTroll.class, 10, 30));
      RIVENDELL.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      GUNDABAD.setAchievement(LOTRAchievement.doMiniquestGundabad);
      GUNDABAD.setLore(LOTRLore.LoreCategory.GUNDABAD);
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(Items.field_151040_l), 1, 5).setRewardFactor(3.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.daggerIron), 1, 6).setRewardFactor(2.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151157_am), 2, 8).setRewardFactor(2.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151083_be), 2, 8).setRewardFactor(2.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151103_aS), 2, 8).setRewardFactor(2.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(Items.field_151028_Y), 2, 5).setRewardFactor(3.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(Items.field_151030_Z), 2, 5).setRewardFactor(4.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.helmetBronze), 2, 5).setRewardFactor(3.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bodyBronze), 2, 5).setRewardFactor(4.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151042_j), 3, 10).setRewardFactor(2.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 8).setRewardFactor(4.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.silver), 3, 8).setRewardFactor(4.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.diamond), 1, 3).setRewardFactor(15.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0F));
      GUNDABAD.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.HOBBIT, 10, 30));
      GUNDABAD.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.RANGER_NORTH, 10, 40));
      GUNDABAD.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.HIGH_ELF, 10, 40));
      GUNDABAD.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.LOTHLORIEN, 10, 40));
      GUNDABAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRanger")).setKillEntity(LOTREntityRangerNorth.class, 10, 30));
      GUNDABAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killElf")).setKillEntity(LOTREntityHighElf.class, 10, 30));
      GUNDABAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killElf")).setKillEntity(LOTREntityRivendellElf.class, 10, 30));
      GUNDABAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killElf")).setKillEntity(LOTREntityGaladhrimElf.class, 10, 30));
      GUNDABAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killDwarf")).setKillEntity(LOTREntityDwarf.class, 10, 30));
      GUNDABAD.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      ANGMAR.setAchievement(LOTRAchievement.doMiniquestAngmar);
      ANGMAR.setLore(LOTRLore.LoreCategory.ANGMAR);
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.swordAngmar), 1, 5).setRewardFactor(3.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.battleaxeAngmar), 1, 5).setRewardFactor(3.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.axeAngmar), 1, 5).setRewardFactor(3.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.daggerAngmar), 1, 6).setRewardFactor(2.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.polearmAngmar), 1, 5).setRewardFactor(3.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151157_am), 2, 8).setRewardFactor(2.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151083_be), 2, 8).setRewardFactor(2.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151103_aS), 2, 8).setRewardFactor(2.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.helmetAngmar), 2, 5).setRewardFactor(3.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bodyAngmar), 2, 5).setRewardFactor(4.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.legsAngmar), 2, 5).setRewardFactor(3.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bootsAngmar), 2, 5).setRewardFactor(3.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151042_j), 3, 10).setRewardFactor(2.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 8).setRewardFactor(4.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.silver), 3, 8).setRewardFactor(4.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.guldurilCrystal), 3, 6).setRewardFactor(4.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.emerald), 1, 3).setRewardFactor(12.0F));
      ANGMAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0F));
      ANGMAR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.RANGER_NORTH, 10, 40));
      ANGMAR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.HIGH_ELF, 10, 40));
      ANGMAR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRanger")).setKillEntity(LOTREntityRangerNorth.class, 10, 30));
      ANGMAR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killElf")).setKillEntity(LOTREntityHighElf.class, 10, 30));
      ANGMAR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killElf")).setKillEntity(LOTREntityRivendellElf.class, 10, 30));
      ANGMAR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      ANGMAR_HILLMAN.setAchievement(LOTRAchievement.doMiniquestAngmar);
      ANGMAR_HILLMAN.setLore(LOTRLore.LoreCategory.ANGMAR);
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMeat")).setCollectItem(new ItemStack(Items.field_151157_am), 4, 8).setRewardFactor(2.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMeat")).setCollectItem(new ItemStack(Items.field_151083_be), 4, 8).setRewardFactor(2.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMeat")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 4, 8).setRewardFactor(2.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMeat")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 4, 8).setRewardFactor(2.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMeat")).setCollectItem(new ItemStack(Items.field_151077_bg), 4, 8).setRewardFactor(2.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.helmetAngmar), 2, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bodyAngmar), 2, 5).setRewardFactor(4.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.legsAngmar), 2, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bootsAngmar), 2, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.helmetBronze), 2, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bodyBronze), 2, 5).setRewardFactor(4.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.legsBronze), 2, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bootsBronze), 2, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(Items.field_151028_Y), 2, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(Items.field_151030_Z), 2, 5).setRewardFactor(4.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(Items.field_151165_aa), 2, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(Items.field_151167_ab), 2, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMetal")).setCollectItem(new ItemStack(Items.field_151042_j), 3, 10).setRewardFactor(2.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMetal")).setCollectItem(new ItemStack(LOTRMod.bronze), 3, 10).setRewardFactor(2.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMetal")).setCollectItem(new ItemStack(LOTRMod.copper), 3, 10).setRewardFactor(2.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMetal")).setCollectItem(new ItemStack(LOTRMod.orcSteel), 3, 10).setRewardFactor(2.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcBrew")).setCollectItem(new ItemStack(LOTRMod.mugOrcDraught), 2, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.swordAngmar), 1, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.battleaxeAngmar), 1, 5).setRewardFactor(4.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.polearmAngmar), 1, 5).setRewardFactor(4.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.swordBronze), 1, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.battleaxeBronze), 1, 5).setRewardFactor(4.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(Items.field_151040_l), 1, 5).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.battleaxeIron), 1, 5).setRewardFactor(4.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.pikeIron), 1, 5).setRewardFactor(4.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectThrowingAxes")).setCollectItem(new ItemStack(LOTRMod.throwingAxeIron), 3, 6).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectThrowingAxes")).setCollectItem(new ItemStack(LOTRMod.throwingAxeBronze), 3, 6).setRewardFactor(3.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTreasure")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 6).setRewardFactor(4.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTreasure")).setCollectItem(new ItemStack(LOTRMod.silver), 3, 6).setRewardFactor(4.0F));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killRanger")).setKillFaction(LOTRFaction.RANGER_NORTH, 10, 30));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killRangerMany")).setKillFaction(LOTRFaction.RANGER_NORTH, 40, 60));
      ANGMAR_HILLMAN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killHighElf")).setKillFaction(LOTRFaction.HIGH_ELF, 10, 30));
      ANGMAR_HILLMAN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      WOOD_ELF.setAchievement(LOTRAchievement.doMiniquestWoodElf);
      WOOD_ELF.setLore(LOTRLore.LoreCategory.WOODLAND_REALM);
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectGoldenLeaves")).setCollectItem(new ItemStack(LOTRMod.leaves, 1, 1), 10, 20).setRewardFactor(1.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMallornSapling")).setCollectItem(new ItemStack(LOTRMod.sapling, 1, 1), 3, 10).setRewardFactor(2.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMallornNut")).setCollectItem(new ItemStack(LOTRMod.mallornNut), 1, 3).setRewardFactor(5.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectLorienFlowers")).setCollectItem(new ItemStack(LOTRMod.elanor), 2, 7).setRewardFactor(2.5F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectLorienFlowers")).setCollectItem(new ItemStack(LOTRMod.niphredil), 2, 7).setRewardFactor(2.5F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.swordWoodElven), 1, 4).setRewardFactor(3.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.polearmWoodElven), 1, 4).setRewardFactor(3.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.longspearWoodElven), 1, 4).setRewardFactor(3.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.spearWoodElven), 1, 4).setRewardFactor(2.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.helmetWoodElven), 1, 4).setRewardFactor(3.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.bodyWoodElven), 1, 4).setRewardFactor(4.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(LOTRMod.sapling7, 1, 1), 4, 8).setRewardFactor(2.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(LOTRMod.sapling, 1, 3), 2, 5).setRewardFactor(4.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectString")).setCollectItem(new ItemStack(Items.field_151007_F), 5, 20).setRewardFactor(1.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.helmetOrc), 1, 1).setRewardFactor(10.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.helmetDolGuldur), 1, 1).setRewardFactor(10.0F));
      WOOD_ELF.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killDolGuldur")).setKillFaction(LOTRFaction.DOL_GULDUR, 10, 40));
      WOOD_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
      WOOD_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityDolGuldurOrc.class, 10, 40));
      WOOD_ELF.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad")).setKillFaction(LOTRFaction.GUNDABAD, 10, 30));
      WOOD_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
      WOOD_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killSpider")).setKillEntity(LOTREntityMirkwoodSpider.class, 10, 40));
      WOOD_ELF.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      DOL_GULDUR.setAchievement(LOTRAchievement.doMiniquestDolGuldur);
      DOL_GULDUR.setLore(LOTRLore.LoreCategory.DOL_GULDUR);
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.swordDolGuldur), 1, 5).setRewardFactor(3.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.battleaxeDolGuldur), 1, 5).setRewardFactor(3.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.axeDolGuldur), 1, 5).setRewardFactor(3.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.daggerDolGuldur), 1, 6).setRewardFactor(2.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.pikeDolGuldur), 1, 5).setRewardFactor(3.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151157_am), 2, 8).setRewardFactor(2.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151083_be), 2, 8).setRewardFactor(2.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151103_aS), 2, 8).setRewardFactor(2.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.helmetDolGuldur), 2, 5).setRewardFactor(3.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bodyDolGuldur), 2, 5).setRewardFactor(4.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.legsDolGuldur), 2, 5).setRewardFactor(3.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bootsDolGuldur), 2, 5).setRewardFactor(3.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151042_j), 3, 10).setRewardFactor(2.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 8).setRewardFactor(4.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.silver), 3, 8).setRewardFactor(4.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.guldurilCrystal), 3, 6).setRewardFactor(4.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.emerald), 1, 3).setRewardFactor(12.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.amethyst), 1, 3).setRewardFactor(10.0F));
      DOL_GULDUR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.LOTHLORIEN, 10, 40));
      DOL_GULDUR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.WOOD_ELF, 10, 40));
      DOL_GULDUR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.DALE, 10, 40));
      DOL_GULDUR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killElf")).setKillEntity(LOTREntityGaladhrimElf.class, 10, 30));
      DOL_GULDUR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killElf")).setKillEntity(LOTREntityWoodElf.class, 10, 30));
      DOL_GULDUR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      DALE.setAchievement(LOTRAchievement.doMiniquestDale);
      DALE.setLore(LOTRLore.LoreCategory.DALE);
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("pastries")).setCollectItem(new ItemStack(LOTRMod.dalishPastryItem), 3, 8).setRewardFactor(4.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWine")).setCollectItem(new ItemStack(LOTRMod.mugRedWine), 2, 5).setRewardFactor(5.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWine")).setCollectItem(new ItemStack(LOTRMod.mugWhiteWine), 2, 5).setRewardFactor(5.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArrows")).setCollectItem(new ItemStack(Items.field_151032_g), 10, 30).setRewardFactor(0.5F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("foreignItem")).setCollectItem(new ItemStack(LOTRMod.oliveBread), 2, 5).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("foreignItem")).setCollectItem(new ItemStack(LOTRMod.banana), 3, 6).setRewardFactor(4.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("foreignItem")).setCollectItem(new ItemStack(LOTRMod.mango), 3, 6).setRewardFactor(4.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("foreignItem")).setCollectItem(new ItemStack(LOTRMod.daggerGondor), 1, 1).setRewardFactor(15.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("foreignItem")).setCollectItem(new ItemStack(LOTRMod.daggerRohan), 1, 1).setRewardFactor(15.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("foreignItem")).setCollectItem(new ItemStack(LOTRMod.daggerBlueDwarven), 1, 1).setRewardFactor(15.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("dwarfTrade")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 10).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("dwarfTrade")).setCollectItem(new ItemStack(LOTRMod.silver), 3, 10).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("dwarfTrade")).setCollectItem(new ItemStack(Items.field_151042_j), 5, 12).setRewardFactor(2.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("dwarfTrade")).setCollectItem(new ItemStack(LOTRMod.bronze), 5, 12).setRewardFactor(2.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("dwarfTrade")).setCollectItem(new ItemStack(Items.field_151015_O), 20, 40).setRewardFactor(0.5F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("dwarfTrade")).setCollectItem(new ItemStack(Items.field_151025_P), 3, 10).setRewardFactor(2.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("dwarfTrade")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 20, 60).setRewardFactor(0.25F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("dwarfTrade")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 1), 20, 60).setRewardFactor(0.25F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("smithyItem")).setCollectItem(new ItemStack(Items.field_151044_h), 10, 30).setRewardFactor(0.5F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("smithyItem")).setCollectItem(new ItemStack(Items.field_151042_j), 3, 10).setRewardFactor(1.5F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("smithyItem")).setCollectItem(new ItemStack(LOTRMod.bronze), 3, 10).setRewardFactor(1.5F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("smithyItem")).setCollectItem(new ItemStack(Items.field_151133_ar), 3, 5).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("smithyItem")).setCollectItem(new ItemStack(Items.field_151129_at), 2, 4).setRewardFactor(5.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.swordDale), 1, 4).setRewardFactor(5.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.daggerDale), 2, 6).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.spearDale), 1, 4).setRewardFactor(4.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.pikeDale), 1, 4).setRewardFactor(4.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.helmetDale), 2, 5).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.bodyDale), 2, 5).setRewardFactor(4.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.legsDale), 2, 5).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.bootsDale), 2, 5).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.daleBow), 3, 5).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(Items.field_151032_g), 10, 40).setRewardFactor(0.5F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(Items.field_151116_aA), 10, 30).setRewardFactor(0.5F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151034_e), 3, 8).setRewardFactor(2.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.appleGreen), 3, 8).setRewardFactor(2.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.appleCrumbleItem), 2, 5).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151083_be), 2, 6).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151077_bg), 2, 6).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.cherryPieItem), 2, 5).setRewardFactor(3.0F));
      DALE.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killDolGuldur")).setKillFaction(LOTRFaction.DOL_GULDUR, 10, 40));
      DALE.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killMordor")).setKillFaction(LOTRFaction.MORDOR, 10, 40));
      DALE.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
      DALE.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityDolGuldurOrc.class, 10, 30));
      DALE.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
      DALE.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      DURIN.setAchievement(LOTRAchievement.doMiniquestDwarf);
      DURIN.setLore(LOTRLore.LoreCategory.DURIN);
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("mineMithril")).setCollectItem(new ItemStack(LOTRMod.mithril), 1, 2).setRewardFactor(50.0F));
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 15).setRewardFactor(4.0F));
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.silver), 3, 15).setRewardFactor(4.0F));
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151114_aO), 5, 15).setRewardFactor(2.0F));
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.diamond), 1, 3).setRewardFactor(15.0F));
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.emerald), 1, 3).setRewardFactor(12.0F));
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0F));
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.opal), 1, 3).setRewardFactor(10.0F));
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon")).setCollectItem(new ItemStack(LOTRMod.hammerDwarven), 1, 3).setRewardFactor(5.0F));
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon")).setCollectItem(new ItemStack(LOTRMod.battleaxeDwarven), 1, 3).setRewardFactor(5.0F));
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("forgeDwarfWeapon")).setCollectItem(new ItemStack(LOTRMod.throwingAxeDwarven), 1, 4).setRewardFactor(4.0F));
      DURIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugDwarvenAle), 2, 5).setRewardFactor(3.0F));
      DURIN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad")).setKillFaction(LOTRFaction.GUNDABAD, 20, 40));
      DURIN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
      DURIN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
      DURIN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killSpider")).setKillEntity(LOTREntityMirkwoodSpider.class, 20, 30));
      DURIN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      GALADHRIM.setAchievement(LOTRAchievement.doMiniquestGaladhrim);
      GALADHRIM.setLore(LOTRLore.LoreCategory.LOTHLORIEN);
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collect")).setCollectItem(new ItemStack(LOTRMod.sapling, 1, 1), 5, 20).setRewardFactor(0.5F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collect")).setCollectItem(new ItemStack(LOTRMod.elanor), 5, 30).setRewardFactor(0.25F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collect")).setCollectItem(new ItemStack(LOTRMod.niphredil), 5, 30).setRewardFactor(0.25F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collect")).setCollectItem(new ItemStack(LOTRMod.mallornNut), 5, 10).setRewardFactor(2.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFangorn")).setCollectItem(new ItemStack(LOTRMod.fangornPlant, 1, 0), 4, 10).setRewardFactor(2.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFangorn")).setCollectItem(new ItemStack(LOTRMod.fangornPlant, 1, 2), 4, 10).setRewardFactor(2.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFangorn")).setCollectItem(new ItemStack(LOTRMod.fangornPlant, 1, 5), 4, 10).setRewardFactor(2.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectCrystal")).setCollectItem(new ItemStack(LOTRMod.quenditeCrystal), 4, 16).setRewardFactor(1.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.swordElven), 1, 4).setRewardFactor(3.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.polearmElven), 1, 4).setRewardFactor(3.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.longspearElven), 1, 4).setRewardFactor(3.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.spearElven), 1, 4).setRewardFactor(2.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.helmetElven), 1, 4).setRewardFactor(3.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.bodyElven), 1, 4).setRewardFactor(4.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 10).setRewardFactor(4.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.silver), 3, 10).setRewardFactor(4.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.emerald), 1, 3).setRewardFactor(12.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.opal), 1, 3).setRewardFactor(10.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.amber), 1, 3).setRewardFactor(10.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDwarven")).setCollectItem(new ItemStack(LOTRMod.brick3, 1, 12), 2, 6).setRewardFactor(4.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150345_g, 1, 1), 2, 5).setRewardFactor(3.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150345_g, 1, 2), 2, 5).setRewardFactor(3.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150328_O, 1, 0), 2, 8).setRewardFactor(1.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPlants")).setCollectItem(new ItemStack(Blocks.field_150327_N, 1, 0), 2, 8).setRewardFactor(1.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectString")).setCollectItem(new ItemStack(Items.field_151007_F), 5, 20).setRewardFactor(1.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.helmetOrc), 1, 1).setRewardFactor(10.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.helmetDolGuldur), 1, 1).setRewardFactor(10.0F));
      GALADHRIM.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killDolGuldur")).setKillFaction(LOTRFaction.DOL_GULDUR, 10, 30));
      GALADHRIM.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
      GALADHRIM.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityDolGuldurOrc.class, 10, 30));
      GALADHRIM.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGundabad")).setKillFaction(LOTRFaction.GUNDABAD, 10, 30));
      GALADHRIM.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
      GALADHRIM.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      DUNLAND.setAchievement(LOTRAchievement.doMiniquestDunland);
      DUNLAND.setLore(LOTRLore.LoreCategory.DUNLAND);
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectResources")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 30, 80).setRewardFactor(0.25F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectResources")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 1), 30, 80).setRewardFactor(0.25F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectResources")).setCollectItem(new ItemStack(Items.field_151044_h), 10, 30).setRewardFactor(0.5F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectResources")).setCollectItem(new ItemStack(Blocks.field_150347_e), 30, 80).setRewardFactor(0.25F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectResources")).setCollectItem(new ItemStack(Items.field_151116_aA), 10, 30).setRewardFactor(0.5F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectResources")).setCollectItem(new ItemStack(Items.field_151042_j), 3, 10).setRewardFactor(1.5F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugAle), 3, 10).setRewardFactor(2.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugMead), 3, 10).setRewardFactor(2.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugCider), 3, 10).setRewardFactor(2.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugRum), 3, 10).setRewardFactor(2.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRohanItem")).setCollectItem(new ItemStack(LOTRMod.helmetRohan), 1, 3).setRewardFactor(10.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRohanItem")).setCollectItem(new ItemStack(LOTRMod.bodyRohan), 1, 3).setRewardFactor(10.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRohanItem")).setCollectItem(new ItemStack(LOTRMod.swordRohan), 1, 3).setRewardFactor(10.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151157_am), 3, 8).setRewardFactor(2.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151083_be), 3, 8).setRewardFactor(2.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 3, 8).setRewardFactor(2.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 3, 8).setRewardFactor(2.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151077_bg), 3, 8).setRewardFactor(2.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 3, 12).setRewardFactor(2.0F));
      DUNLAND.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151025_P), 5, 15).setRewardFactor(1.0F));
      DUNLAND.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killRohirrim")).setKillFaction(LOTRFaction.ROHAN, 10, 40));
      DUNLAND.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("avengeKin")).setKillFaction(LOTRFaction.ROHAN, 30, 60));
      DUNLAND.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killHorse")).setKillEntity(LOTREntityHorse.class, 10, 20));
      DUNLAND.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      ISENGARD.setAchievement(LOTRAchievement.doMiniquestIsengard);
      ISENGARD.setLore(LOTRLore.LoreCategory.ISENGARD);
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.scimitarUruk), 1, 5).setRewardFactor(3.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.pikeUruk), 1, 5).setRewardFactor(3.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.battleaxeUruk), 1, 5).setRewardFactor(3.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.axeUruk), 1, 5).setRewardFactor(3.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.daggerUruk), 1, 6).setRewardFactor(2.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151157_am), 2, 8).setRewardFactor(2.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151083_be), 2, 8).setRewardFactor(2.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151103_aS), 2, 8).setRewardFactor(2.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.helmetUruk), 2, 5).setRewardFactor(3.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bodyUruk), 2, 5).setRewardFactor(4.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.legsUruk), 2, 5).setRewardFactor(3.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bootsUruk), 2, 5).setRewardFactor(3.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151042_j), 3, 10).setRewardFactor(2.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 8).setRewardFactor(4.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.silver), 3, 8).setRewardFactor(4.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("forgeSteel")).setCollectItem(new ItemStack(LOTRMod.urukSteel), 3, 10).setRewardFactor(3.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("forgeSteel")).setCollectItem(new ItemStack(LOTRMod.orcSteel), 3, 10).setRewardFactor(3.0F));
      ISENGARD.addQuest((new LOTRMiniQuestCollect.QFCollect("forgeSteel")).setCollectItem(new ItemStack(Items.field_151042_j), 3, 10).setRewardFactor(2.0F));
      ISENGARD.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.ROHAN, 10, 40));
      ISENGARD.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.GONDOR, 10, 40));
      ISENGARD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killMen")).setKillEntity(LOTREntityRohirrimWarrior.class, 10, 30));
      ISENGARD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killMen")).setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
      ISENGARD.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      ROHAN.setAchievement(LOTRAchievement.doMiniquestRohan);
      ROHAN.setLore(LOTRLore.LoreCategory.ROHAN);
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151157_am), 3, 8).setRewardFactor(2.0F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151083_be), 3, 8).setRewardFactor(2.0F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 3, 8).setRewardFactor(2.0F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 3, 8).setRewardFactor(2.0F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151077_bg), 3, 8).setRewardFactor(2.0F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 3, 12).setRewardFactor(2.0F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151025_P), 5, 15).setRewardFactor(1.0F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMead")).setCollectItem(new ItemStack(LOTRMod.mugMead), 3, 20).setRewardFactor(1.0F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("defences")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 20, 60).setRewardFactor(0.25F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("defences")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 1), 20, 60).setRewardFactor(0.25F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("defences")).setCollectItem(new ItemStack(Blocks.field_150344_f, 1, 0), 80, 160).setRewardFactor(0.125F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("defences")).setCollectItem(new ItemStack(Blocks.field_150344_f, 1, 1), 80, 160).setRewardFactor(0.125F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("defences")).setCollectItem(new ItemStack(Blocks.field_150347_e), 30, 80).setRewardFactor(0.25F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringWeapon")).setCollectItem(new ItemStack(LOTRMod.swordRohan), 1, 4).setRewardFactor(3.0F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringWeapon")).setCollectItem(new ItemStack(Items.field_151042_j), 3, 8).setRewardFactor(2.0F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("stealUruk")).setCollectItem(new ItemStack(LOTRMod.urukTable), 1, 2).setRewardFactor(10.0F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBones")).setCollectItem(new ItemStack(LOTRMod.orcBone), 15, 30).setRewardFactor(0.5F));
      ROHAN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBones")).setCollectItem(new ItemStack(LOTRMod.wargBone), 15, 30).setRewardFactor(0.75F));
      ROHAN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityIsengardSnaga.class, 10, 30));
      ROHAN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityUrukHai.class, 10, 30));
      ROHAN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityMordorOrc.class, 10, 30));
      ROHAN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("avengeRiders")).setKillEntity(LOTREntityUrukWarg.class, 10, 20));
      ROHAN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killDunland")).setKillFaction(LOTRFaction.DUNLAND, 10, 40));
      ROHAN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      ROHAN_SHIELDMAIDEN.setAchievement(LOTRAchievement.doMiniquestRohanShieldmaiden);
      ROHAN_SHIELDMAIDEN.setLore(LOTRLore.LoreCategory.ROHAN);
      ROHAN_SHIELDMAIDEN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemies")).setKillFaction(LOTRFaction.DUNLAND, 5, 20));
      ROHAN_SHIELDMAIDEN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemies")).setKillFaction(LOTRFaction.ISENGARD, 5, 20));
      var0 = ROHAN_SHIELDMAIDEN.questFactories.values().iterator();

      while(var0.hasNext()) {
         qfList = (List)var0.next();
         var2 = qfList.iterator();

         while(var2.hasNext()) {
            qf = (LOTRMiniQuest.QuestFactoryBase)var2.next();
            qf.setRewardFactor(0.0F);
            qf.setHiring(150.0F);
         }
      }

      GONDOR.setAchievement(LOTRAchievement.doMiniquestGondor);
      GONDOR.setLore(LOTRLore.LoreCategory.GONDOR);
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("defences")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 20, 60).setRewardFactor(0.25F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("defences")).setCollectItem(new ItemStack(Blocks.field_150344_f, 1, 0), 80, 160).setRewardFactor(0.125F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("defences")).setCollectItem(new ItemStack(LOTRMod.rock, 1, 1), 30, 80).setRewardFactor(0.25F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("defences")).setCollectItem(new ItemStack(LOTRMod.brick, 1, 1), 30, 60).setRewardFactor(0.5F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("defences")).setCollectItem(new ItemStack(LOTRMod.brick5, 1, 8), 30, 60).setRewardFactor(0.5F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("lebethron")).setCollectItem(new ItemStack(LOTRMod.wood2, 1, 0), 10, 30).setRewardFactor(1.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectIron")).setCollectItem(new ItemStack(Blocks.field_150366_p), 10, 20).setRewardFactor(1.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectIron")).setCollectItem(new ItemStack(Items.field_151042_j), 6, 15).setRewardFactor(1.5F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("blackMarble")).setCollectItem(new ItemStack(LOTRMod.rock, 1, 0), 25, 35).setRewardFactor(1.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 4).setRewardFactor(5.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.spearGondor), 1, 4).setRewardFactor(5.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.pikeGondor), 1, 4).setRewardFactor(5.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.helmetGondor), 1, 4).setRewardFactor(5.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.helmetGondorWinged), 1, 4).setRewardFactor(5.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("forge")).setCollectItem(new ItemStack(LOTRMod.bodyGondor), 1, 4).setRewardFactor(5.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRohanItem")).setCollectItem(new ItemStack(LOTRMod.swordRohan), 1, 1).setRewardFactor(15.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRohanItem")).setCollectItem(new ItemStack(LOTRMod.helmetRohan), 1, 1).setRewardFactor(15.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRohanItem")).setCollectItem(new ItemStack(LOTRMod.helmetRohanMarshal), 1, 1).setRewardFactor(20.0F));
      GONDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPipeweed")).setCollectItem(new ItemStack(LOTRMod.pipeweedPlant), 2, 4).setRewardFactor(6.0F));
      GONDOR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killMordor")).setKillFaction(LOTRFaction.MORDOR, 10, 40));
      GONDOR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.MORDOR, 30, 40));
      GONDOR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.DUNLAND, 30, 40));
      GONDOR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.ISENGARD, 30, 40));
      GONDOR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killMordorMany")).setKillFaction(LOTRFaction.MORDOR, 60, 90));
      GONDOR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killHarad")).setKillFaction(LOTRFaction.NEAR_HARAD, 10, 40));
      GONDOR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killHarnennor")).setKillEntity(LOTREntityHarnedorWarrior.class, 20, 30));
      GONDOR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      GONDOR_KILL_RENEGADE.setAchievement(LOTRAchievement.doMiniquestGondorKillRenegade);
      GONDOR_KILL_RENEGADE.setBaseSpeechGroup(GONDOR);
      GONDOR_KILL_RENEGADE.setLore(LOTRLore.LoreCategory.GONDOR);
      GONDOR_KILL_RENEGADE.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRenegades")).setKillEntity(LOTREntityGondorRenegade.class, 2, 6).setRewardFactor(8.0F));
      MORDOR.setAchievement(LOTRAchievement.doMiniquestMordor);
      MORDOR.setLore(LOTRLore.LoreCategory.MORDOR);
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.scimitarOrc), 1, 5).setRewardFactor(3.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.polearmOrc), 1, 5).setRewardFactor(3.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.battleaxeOrc), 1, 5).setRewardFactor(3.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.axeOrc), 1, 5).setRewardFactor(3.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapon")).setCollectItem(new ItemStack(LOTRMod.daggerOrc), 1, 6).setRewardFactor(2.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151157_am), 2, 8).setRewardFactor(2.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151083_be), 2, 8).setRewardFactor(2.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151103_aS), 2, 8).setRewardFactor(2.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.helmetOrc), 2, 5).setRewardFactor(3.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bodyOrc), 2, 5).setRewardFactor(4.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.legsOrc), 2, 5).setRewardFactor(3.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectArmour")).setCollectItem(new ItemStack(LOTRMod.bootsOrc), 2, 5).setRewardFactor(3.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.orcSteel), 3, 10).setRewardFactor(2.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.nauriteGem), 4, 12).setRewardFactor(2.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.guldurilCrystal), 3, 6).setRewardFactor(4.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.diamond), 1, 3).setRewardFactor(15.0F));
      MORDOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMineral")).setCollectItem(new ItemStack(LOTRMod.ruby), 1, 3).setRewardFactor(12.0F));
      MORDOR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.ROHAN, 10, 40));
      MORDOR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killEnemy")).setKillFaction(LOTRFaction.GONDOR, 10, 40));
      MORDOR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killMen")).setKillEntity(LOTREntityRohirrimWarrior.class, 10, 30));
      MORDOR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killMen")).setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
      MORDOR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRanger")).setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
      MORDOR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      DORWINION.setAchievement(LOTRAchievement.doMiniquestDorwinion);
      DORWINION.setLore(LOTRLore.LoreCategory.DORWINION);
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBarrel")).setCollectItem(new ItemStack(LOTRMod.barrel), 3, 6).setRewardFactor(4.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("feast")).setCollectItem(new ItemStack(LOTRMod.grapeRed), 4, 12).setRewardFactor(2.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("feast")).setCollectItem(new ItemStack(LOTRMod.grapeWhite), 4, 12).setRewardFactor(2.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("feast")).setCollectItem(new ItemStack(LOTRMod.raisins), 4, 12).setRewardFactor(2.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("feast")).setCollectItem(new ItemStack(LOTRMod.mugRedWine), 2, 6).setRewardFactor(4.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("feast")).setCollectItem(new ItemStack(LOTRMod.mugWhiteWine), 2, 6).setRewardFactor(4.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("feast")).setCollectItem(new ItemStack(LOTRMod.mugRedGrapeJuice), 2, 6).setRewardFactor(4.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("feast")).setCollectItem(new ItemStack(LOTRMod.mugWhiteGrapeJuice), 2, 6).setRewardFactor(4.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("feast")).setCollectItem(new ItemStack(LOTRMod.olive), 10, 20).setRewardFactor(1.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("feast")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 5, 12).setRewardFactor(1.5F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("feast")).setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 5, 12).setRewardFactor(1.5F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("feast")).setCollectItem(new ItemStack(Items.field_151083_be), 5, 12).setRewardFactor(1.5F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWater")).setCollectItem(new ItemStack(Items.field_151133_ar), 3, 5).setRewardFactor(3.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWater")).setCollectItem(new ItemStack(Items.field_151131_as), 3, 5).setRewardFactor(4.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectHoe")).setCollectItem(new ItemStack(Items.field_151019_K), 1, 3).setRewardFactor(4.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectHoe")).setCollectItem(new ItemStack(LOTRMod.hoeBronze), 1, 3).setRewardFactor(4.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectHoe")).setCollectItem(new ItemStack(Items.field_151018_J), 2, 6).setRewardFactor(2.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectHoe")).setCollectItem(new ItemStack(Items.field_151017_I), 3, 8).setRewardFactor(1.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBonemeal")).setCollectItem(new ItemStack(Items.field_151100_aR, 1, 15), 12, 40).setRewardFactor(0.25F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(Items.field_151040_l), 2, 4).setRewardFactor(3.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.daggerIron), 2, 6).setRewardFactor(2.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.pikeIron), 2, 4).setRewardFactor(3.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.helmetDorwinion), 2, 5).setRewardFactor(3.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.bodyDorwinion), 2, 5).setRewardFactor(4.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.legsDorwinion), 2, 5).setRewardFactor(3.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.bootsDorwinion), 2, 5).setRewardFactor(3.0F));
      DORWINION.addQuest((new LOTRMiniQuestCollect.QFCollect("kineHorn")).setCollectItem(new ItemStack(LOTRMod.kineArawHorn), 1, 3).setRewardFactor(20.0F));
      DORWINION.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRabbits")).setKillEntity(LOTREntityRabbit.class, 10, 20));
      DORWINION.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killBirds")).setKillEntity(LOTREntityBird.class, 10, 20));
      DORWINION.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killBandit")).setKillEntity(LOTREntityBandit.class, 1, 3).setRewardFactor(8.0F));
      DORWINION.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOddmentCollector")).setKillEntity(LOTREntityScrapTrader.class, 1, 2).setRewardFactor(15.0F));
      DORWINION.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      DORWINION_ELF.setAchievement(LOTRAchievement.doMiniquestDorwinion);
      DORWINION_ELF.setLore(LOTRLore.LoreCategory.DORWINION);
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 20, 60).setRewardFactor(0.25F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 2), 20, 60).setRewardFactor(0.25F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(LOTRMod.wood6, 1, 2), 20, 60).setRewardFactor(0.25F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(LOTRMod.wood6, 1, 3), 20, 60).setRewardFactor(0.25F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWine")).setCollectItem(new ItemStack(LOTRMod.grapeRed), 4, 12).setRewardFactor(2.0F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWine")).setCollectItem(new ItemStack(LOTRMod.grapeWhite), 4, 12).setRewardFactor(2.0F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWine")).setCollectItem(new ItemStack(LOTRMod.barrel), 3, 6).setRewardFactor(4.0F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWine")).setCollectItem(new ItemStack(LOTRMod.mugRedWine), 2, 6).setRewardFactor(4.0F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWine")).setCollectItem(new ItemStack(LOTRMod.mugWhiteWine), 2, 6).setRewardFactor(4.0F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBarrel")).setCollectItem(new ItemStack(LOTRMod.barrel), 3, 6).setRewardFactor(4.0F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectGreenOak")).setCollectItem(new ItemStack(LOTRMod.wood7, 1, 1), 20, 40).setRewardFactor(0.5F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectGreenOak")).setCollectItem(new ItemStack(LOTRMod.leaves7, 1, 1), 40, 80).setRewardFactor(0.25F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectGreenOak")).setCollectItem(new ItemStack(LOTRMod.sapling7, 1, 1), 5, 10).setRewardFactor(1.5F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("collectGreenOak")).setCollectItem(new ItemStack(LOTRMod.planks2, 1, 13), 40, 80).setRewardFactor(0.25F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("brewing")).setCollectItem(new ItemStack(LOTRMod.grapeRed), 4, 12).setRewardFactor(2.0F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("brewing")).setCollectItem(new ItemStack(LOTRMod.grapeWhite), 4, 12).setRewardFactor(2.0F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("brewing")).setCollectItem(new ItemStack(LOTRMod.barrel), 3, 6).setRewardFactor(4.0F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("brewing")).setCollectItem(new ItemStack(LOTRMod.mug), 5, 12).setRewardFactor(1.5F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("brewing")).setCollectItem(new ItemStack(Items.field_151133_ar), 3, 5).setRewardFactor(3.0F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestCollect.QFCollect("brewing")).setCollectItem(new ItemStack(Items.field_151131_as), 3, 5).setRewardFactor(4.0F));
      DORWINION_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityGundabadOrc.class, 10, 30));
      DORWINION_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killOrc")).setKillEntity(LOTREntityMordorOrc.class, 10, 30));
      DORWINION_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityGundabadWarg.class, 10, 30));
      DORWINION_ELF.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killWarg")).setKillEntity(LOTREntityMordorWarg.class, 10, 30));
      DORWINION_ELF.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      RHUN.setAchievement(LOTRAchievement.doMiniquestRhun);
      RHUN.setLore(LOTRLore.LoreCategory.RHUN);
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringRhunThing")).setCollectItem(new ItemStack(LOTRMod.pomegranate), 4, 12).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringRhunThing")).setCollectItem(new ItemStack(LOTRMod.date), 4, 12).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringRhunThing")).setCollectItem(new ItemStack(LOTRMod.rhunFlower, 1, 0), 2, 5).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringRhunThing")).setCollectItem(new ItemStack(LOTRMod.rhunFlower, 1, 1), 2, 5).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringRhunThing")).setCollectItem(new ItemStack(LOTRMod.rhunFlower, 1, 2), 2, 5).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringRhunThing")).setCollectItem(new ItemStack(LOTRMod.rhunFlower, 1, 3), 2, 5).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringRhunThing")).setCollectItem(new ItemStack(LOTRMod.rhunFlower, 1, 4), 2, 5).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringRhunThing")).setCollectItem(new ItemStack(LOTRMod.sapling8, 1, 1), 3, 5).setRewardFactor(3.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.scimitarOrc), 3, 5).setRewardFactor(3.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.spearOrc), 3, 5).setRewardFactor(3.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.orcSteel), 5, 10).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.helmetOrc), 2, 4).setRewardFactor(4.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.bodyOrc), 2, 4).setRewardFactor(4.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.legsOrc), 2, 4).setRewardFactor(4.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectOrcItem")).setCollectItem(new ItemStack(LOTRMod.bootsOrc), 2, 4).setRewardFactor(4.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("goHunting")).setCollectItem(new ItemStack(LOTRMod.rabbitRaw), 5, 10).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("goHunting")).setCollectItem(new ItemStack(LOTRMod.deerRaw), 5, 10).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("goHunting")).setCollectItem(new ItemStack(Items.field_151082_bd), 5, 10).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("goHunting")).setCollectItem(new ItemStack(Items.field_151116_aA), 8, 16).setRewardFactor(1.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("goHunting")).setCollectItem(new ItemStack(Items.field_151008_G), 8, 16).setRewardFactor(1.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("goHunting")).setCollectItem(new ItemStack(LOTRMod.kineArawHorn), 1, 2).setRewardFactor(10.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFire")).setCollectItem(new ItemStack(LOTRMod.nauriteGem), 4, 8).setRewardFactor(3.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFire")).setCollectItem(new ItemStack(LOTRMod.sulfur), 4, 8).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFire")).setCollectItem(new ItemStack(LOTRMod.saltpeter), 4, 8).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRations")).setCollectItem(new ItemStack(Items.field_151025_P), 5, 8).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRations")).setCollectItem(new ItemStack(LOTRMod.oliveBread), 5, 8).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRations")).setCollectItem(new ItemStack(Items.field_151083_be), 2, 8).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRations")).setCollectItem(new ItemStack(Items.field_151101_aQ), 2, 8).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRations")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 2, 8).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRations")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 2, 8).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRations")).setCollectItem(new ItemStack(LOTRMod.raisins), 6, 12).setRewardFactor(1.5F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMaterials")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 30, 60).setRewardFactor(0.25F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMaterials")).setCollectItem(new ItemStack(LOTRMod.wood8, 1, 1), 30, 60).setRewardFactor(0.25F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMaterials")).setCollectItem(new ItemStack(LOTRMod.wood6, 1, 2), 30, 60).setRewardFactor(0.25F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMaterials")).setCollectItem(new ItemStack(LOTRMod.brick5, 1, 11), 40, 100).setRewardFactor(0.2F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMaterials")).setCollectItem(new ItemStack(Blocks.field_150347_e, 1, 0), 40, 100).setRewardFactor(0.25F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectKineHorn")).setCollectItem(new ItemStack(LOTRMod.kineArawHorn), 1, 1).setRewardFactor(30.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDorwinionWine")).setCollectItem(new ItemStack(LOTRMod.mugRedWine), 3, 8).setRewardFactor(3.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDorwinionWine")).setCollectItem(new ItemStack(LOTRMod.mugWhiteWine), 3, 8).setRewardFactor(3.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringPoison")).setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0F));
      RHUN.addQuest((new LOTRMiniQuestCollect.QFCollect("bringPoison")).setCollectItem(new ItemStack(LOTRMod.sulfur), 4, 8).setRewardFactor(2.0F));
      RHUN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killThings")).setKillEntity(LOTREntityGondorMan.class, 10, 30));
      RHUN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killThings")).setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
      RHUN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killThings")).setKillEntity(LOTREntityDaleMan.class, 10, 30));
      RHUN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killThings")).setKillEntity(LOTREntityRabbit.class, 10, 30));
      RHUN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killThings")).setKillEntity(LOTREntityDeer.class, 10, 30));
      RHUN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killGondorAllies")).setKillEntity(LOTREntityRohirrimWarrior.class, 10, 30));
      RHUN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killGondorAllies")).setKillEntity(LOTREntityDaleSoldier.class, 10, 30));
      RHUN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killGondorAllies")).setKillEntity(LOTREntityDolAmrothSoldier.class, 10, 30));
      RHUN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killGondorAllies")).setKillEntity(LOTREntityLossarnachAxeman.class, 10, 30));
      RHUN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killDale")).setKillFaction(LOTRFaction.DALE, 20, 40));
      RHUN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killNorthmen")).setKillFaction(LOTRFaction.DALE, 10, 40));
      RHUN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killDwarves")).setKillFaction(LOTRFaction.DURINS_FOLK, 10, 40));
      RHUN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      HARNENNOR.setAchievement(LOTRAchievement.doMiniquestNearHarad);
      HARNENNOR.setLore(LOTRLore.LoreCategory.HARNENNOR);
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("bringWater")).setCollectItem(new ItemStack(Items.field_151131_as), 3, 5).setRewardFactor(5.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBlackRock")).setCollectItem(new ItemStack(LOTRMod.rock, 1, 0), 30, 50).setRewardFactor(0.5F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDates")).setCollectItem(new ItemStack(LOTRMod.date), 8, 15).setRewardFactor(2.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.lemon), 4, 12).setRewardFactor(2.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.orange), 4, 12).setRewardFactor(2.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.lime), 4, 12).setRewardFactor(2.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.plum), 4, 12).setRewardFactor(2.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("orangeJuice")).setCollectItem(new ItemStack(LOTRMod.mugOrangeJuice), 2, 6).setRewardFactor(4.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("lemonLiqueur")).setCollectItem(new ItemStack(LOTRMod.mugLemonLiqueur), 2, 6).setRewardFactor(4.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPoison")).setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRangedWeapon")).setCollectItem(new ItemStack(LOTRMod.nearHaradBow), 1, 3).setRewardFactor(5.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectRangedWeapon")).setCollectItem(new ItemStack(Items.field_151032_g), 20, 40).setRewardFactor(0.5F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTradeGoods")).setCollectItem(new ItemStack(Items.field_151100_aR, 1, 4), 3, 8).setRewardFactor(3.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTradeGoods")).setCollectItem(new ItemStack(LOTRMod.lionFur), 3, 6).setRewardFactor(3.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTradeGoods")).setCollectItem(new ItemStack(LOTRMod.doubleFlower, 1, 3), 5, 15).setRewardFactor(1.5F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectTradeGoods")).setCollectItem(new ItemStack(LOTRMod.olive), 10, 20).setRewardFactor(1.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMarketFood")).setCollectItem(new ItemStack(Items.field_151025_P), 5, 8).setRewardFactor(2.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMarketFood")).setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 5, 12).setRewardFactor(1.5F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMarketFood")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 5, 12).setRewardFactor(1.5F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMarketFood")).setCollectItem(new ItemStack(LOTRMod.orange), 4, 8).setRewardFactor(2.5F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectMarketFood")).setCollectItem(new ItemStack(LOTRMod.lemon), 4, 8).setRewardFactor(2.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("buildMaterials")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 20, 60).setRewardFactor(0.25F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("buildMaterials")).setCollectItem(new ItemStack(LOTRMod.wood4, 1, 2), 20, 60).setRewardFactor(0.25F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("buildMaterials")).setCollectItem(new ItemStack(Blocks.field_150344_f, 1, 0), 80, 160).setRewardFactor(0.125F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("buildMaterials")).setCollectItem(new ItemStack(LOTRMod.planks2, 1, 2), 80, 160).setRewardFactor(0.125F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("buildMaterials")).setCollectItem(new ItemStack(Blocks.field_150322_A, 1, 0), 30, 80).setRewardFactor(0.25F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("buildMaterials")).setCollectItem(new ItemStack(Blocks.field_150322_A, 1, 1), 15, 40).setRewardFactor(0.5F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("buildMaterials")).setCollectItem(new ItemStack(LOTRMod.thatch, 1, 1), 20, 40).setRewardFactor(0.5F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("specialFood")).setCollectItem(new ItemStack(LOTRMod.orange), 4, 8).setRewardFactor(2.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("specialFood")).setCollectItem(new ItemStack(LOTRMod.lemon), 4, 8).setRewardFactor(2.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("specialFood")).setCollectItem(new ItemStack(LOTRMod.mango), 2, 4).setRewardFactor(4.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("specialFood")).setCollectItem(new ItemStack(LOTRMod.banana), 2, 4).setRewardFactor(4.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestCollect.QFCollect("specialFood")).setCollectItem(new ItemStack(LOTRMod.lionCooked), 3, 6).setRewardFactor(3.0F));
      HARNENNOR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("conquerGondor")).setKillFaction(LOTRFaction.GONDOR, 20, 50));
      HARNENNOR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGondor")).setKillFaction(LOTRFaction.GONDOR, 20, 50));
      HARNENNOR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("avengeRangers")).setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
      HARNENNOR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killGondorSoldier")).setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
      HARNENNOR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("reclaimHarondor")).setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
      HARNENNOR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRohirrim")).setKillEntity(LOTREntityRohirrimWarrior.class, 10, 30));
      HARNENNOR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      NEAR_HARAD.setAchievement(LOTRAchievement.doMiniquestNearHarad);
      NEAR_HARAD.setLore(LOTRLore.LoreCategory.SOUTHRON);
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("bringWater")).setCollectItem(new ItemStack(Items.field_151131_as), 3, 5).setRewardFactor(5.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBlackRock")).setCollectItem(new ItemStack(LOTRMod.rock, 1, 0), 30, 50).setRewardFactor(0.5F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDates")).setCollectItem(new ItemStack(LOTRMod.date), 8, 15).setRewardFactor(2.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.lemon), 4, 12).setRewardFactor(2.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.orange), 4, 12).setRewardFactor(2.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.lime), 4, 12).setRewardFactor(2.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.plum), 4, 12).setRewardFactor(2.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("orangeJuice")).setCollectItem(new ItemStack(LOTRMod.mugOrangeJuice), 2, 6).setRewardFactor(4.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("lemonLiqueur")).setCollectItem(new ItemStack(LOTRMod.mugLemonLiqueur), 2, 6).setRewardFactor(4.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPoison")).setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("gulfSword")).setCollectItem(new ItemStack(LOTRMod.swordGulfHarad), 1, 1).setRewardFactor(5.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("ringTax")).setCollectItem(new ItemStack(LOTRMod.goldRing), 2, 5).setRewardFactor(2.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("ringTax")).setCollectItem(new ItemStack(LOTRMod.silverRing), 2, 5).setRewardFactor(2.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("saveFromVenom")).setCollectItem(new ItemStack(LOTRMod.pearl), 1, 1).setRewardFactor(20.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("bringReedsRoof")).setCollectItem(new ItemStack(LOTRMod.driedReeds), 10, 20).setRewardFactor(0.5F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("bringReedsRoof")).setCollectItem(new ItemStack(LOTRMod.thatch, 1, 1), 10, 20).setRewardFactor(0.5F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("bringReedsRoof")).setCollectItem(new ItemStack(LOTRMod.slabSingleThatch, 1, 1), 20, 40).setRewardFactor(0.25F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("cedarWood")).setCollectItem(new ItemStack(LOTRMod.wood4, 1, 2), 30, 60).setRewardFactor(0.25F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("cedarWood")).setCollectItem(new ItemStack(LOTRMod.planks2, 1, 2), 60, 120).setRewardFactor(0.125F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("repairWall")).setCollectItem(new ItemStack(Blocks.field_150322_A, 1, 0), 30, 80).setRewardFactor(0.25F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("repairWall")).setCollectItem(new ItemStack(LOTRMod.brick, 1, 15), 30, 60).setRewardFactor(0.5F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("repairWall")).setCollectItem(new ItemStack(LOTRMod.brick3, 1, 13), 30, 60).setRewardFactor(0.75F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("bringMeat")).setCollectItem(new ItemStack(LOTRMod.rabbitCooked), 5, 10).setRewardFactor(1.5F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("bringMeat")).setCollectItem(new ItemStack(LOTRMod.deerCooked), 5, 10).setRewardFactor(1.5F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("bringMeat")).setCollectItem(new ItemStack(Items.field_151083_be), 4, 8).setRewardFactor(2.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("bringMeat")).setCollectItem(new ItemStack(Items.field_151157_am), 4, 8).setRewardFactor(2.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("bringMeat")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 4, 8).setRewardFactor(2.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("bringMeat")).setCollectItem(new ItemStack(LOTRMod.kebab), 4, 8).setRewardFactor(2.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("lionSteak")).setCollectItem(new ItemStack(LOTRMod.lionCooked), 2, 4).setRewardFactor(4.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarArmor")).setCollectItem(new ItemStack(LOTRMod.helmetUmbar), 1, 1).setRewardFactor(15.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarArmor")).setCollectItem(new ItemStack(LOTRMod.bodyUmbar), 1, 1).setRewardFactor(15.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarArmor")).setCollectItem(new ItemStack(LOTRMod.legsUmbar), 1, 1).setRewardFactor(15.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarArmor")).setCollectItem(new ItemStack(LOTRMod.bootsUmbar), 1, 1).setRewardFactor(15.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("defendCorsair")).setCollectItem(new ItemStack(LOTRMod.scimitarNearHarad), 1, 3).setRewardFactor(5.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("defendCorsair")).setCollectItem(new ItemStack(LOTRMod.spearNearHarad), 1, 3).setRewardFactor(5.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("defendCorsair")).setCollectItem(new ItemStack(LOTRMod.maceNearHarad), 1, 3).setRewardFactor(5.0F));
      NEAR_HARAD.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGondor")).setKillFaction(LOTRFaction.GONDOR, 20, 50));
      NEAR_HARAD.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killForUmbar")).setKillFaction(LOTRFaction.GONDOR, 10, 40));
      NEAR_HARAD.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killRohirrim")).setKillFaction(LOTRFaction.ROHAN, 10, 30));
      NEAR_HARAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRangers")).setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
      NEAR_HARAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("greenDemons")).setKillEntity(LOTREntityRangerIthilien.class, 10, 20));
      NEAR_HARAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killGondorSoldiers")).setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
      NEAR_HARAD.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      UMBAR.setAchievement(LOTRAchievement.doMiniquestNearHarad);
      UMBAR.setLore(LOTRLore.LoreCategory.UMBAR);
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBlackRock")).setCollectItem(new ItemStack(LOTRMod.rock, 1, 0), 30, 50).setRewardFactor(0.5F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDates")).setCollectItem(new ItemStack(LOTRMod.date), 8, 15).setRewardFactor(2.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.lemon), 4, 12).setRewardFactor(2.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.orange), 4, 12).setRewardFactor(2.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.lime), 4, 12).setRewardFactor(2.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.plum), 4, 12).setRewardFactor(2.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("orangeJuice")).setCollectItem(new ItemStack(LOTRMod.mugOrangeJuice), 2, 6).setRewardFactor(4.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("lemonLiqueur")).setCollectItem(new ItemStack(LOTRMod.mugLemonLiqueur), 2, 6).setRewardFactor(4.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPoison")).setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("gulfSword")).setCollectItem(new ItemStack(LOTRMod.swordGulfHarad), 1, 1).setRewardFactor(5.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("ringTax")).setCollectItem(new ItemStack(LOTRMod.goldRing), 2, 5).setRewardFactor(2.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("ringTax")).setCollectItem(new ItemStack(LOTRMod.silverRing), 2, 5).setRewardFactor(2.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("saveFromVenom")).setCollectItem(new ItemStack(LOTRMod.pearl), 1, 1).setRewardFactor(20.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("bringReedsRoof")).setCollectItem(new ItemStack(LOTRMod.driedReeds), 10, 20).setRewardFactor(0.5F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("bringReedsRoof")).setCollectItem(new ItemStack(LOTRMod.thatch, 1, 1), 10, 20).setRewardFactor(0.5F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("bringReedsRoof")).setCollectItem(new ItemStack(LOTRMod.slabSingleThatch, 1, 1), 20, 40).setRewardFactor(0.25F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("cedarWood")).setCollectItem(new ItemStack(LOTRMod.wood4, 1, 2), 30, 60).setRewardFactor(0.25F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("cedarWood")).setCollectItem(new ItemStack(LOTRMod.planks2, 1, 2), 60, 120).setRewardFactor(0.125F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("repairWall")).setCollectItem(new ItemStack(Blocks.field_150322_A, 1, 0), 30, 80).setRewardFactor(0.25F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("repairWall")).setCollectItem(new ItemStack(Blocks.field_150348_b, 1, 0), 30, 80).setRewardFactor(0.25F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("repairWall")).setCollectItem(new ItemStack(LOTRMod.brick, 1, 15), 30, 60).setRewardFactor(0.5F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("repairWall")).setCollectItem(new ItemStack(LOTRMod.brick6, 1, 6), 30, 60).setRewardFactor(0.5F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("lionSteak")).setCollectItem(new ItemStack(LOTRMod.lionCooked), 2, 4).setRewardFactor(4.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("defendCorsair")).setCollectItem(new ItemStack(LOTRMod.scimitarNearHarad), 1, 3).setRewardFactor(5.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("defendCorsair")).setCollectItem(new ItemStack(LOTRMod.spearNearHarad), 1, 3).setRewardFactor(5.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("defendCorsair")).setCollectItem(new ItemStack(LOTRMod.maceNearHarad), 1, 3).setRewardFactor(5.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarCraft")).setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 2).setRewardFactor(8.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarCraft")).setCollectItem(new ItemStack(LOTRMod.helmetGondor), 1, 2).setRewardFactor(8.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarCraft")).setCollectItem(new ItemStack(LOTRMod.helmetGondorWinged), 1, 1).setRewardFactor(20.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarCraft")).setCollectItem(new ItemStack(LOTRMod.bodyGondor), 1, 2).setRewardFactor(8.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarCraft")).setCollectItem(new ItemStack(LOTRMod.swordArnor), 1, 1).setRewardFactor(40.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarCraft")).setCollectItem(new ItemStack(LOTRMod.helmetArnor), 1, 1).setRewardFactor(40.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarCraft")).setCollectItem(new ItemStack(Items.field_151042_j), 4, 8).setRewardFactor(2.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarCraft")).setCollectItem(new ItemStack(Items.field_151043_k), 3, 6).setRewardFactor(4.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarCraft")).setCollectItem(new ItemStack(Items.field_151129_at), 2, 4).setRewardFactor(5.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("travelSupplies")).setCollectItem(new ItemStack(LOTRMod.kebab), 4, 8).setRewardFactor(2.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("travelSupplies")).setCollectItem(new ItemStack(Items.field_151083_be), 4, 8).setRewardFactor(2.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("travelSupplies")).setCollectItem(new ItemStack(LOTRMod.muttonCooked), 4, 8).setRewardFactor(2.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("travelSupplies")).setCollectItem(new ItemStack(LOTRMod.mugAraq), 3, 5).setRewardFactor(4.0F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("travelSupplies")).setCollectItem(new ItemStack(LOTRMod.waterskin), 8, 20).setRewardFactor(0.75F));
      UMBAR.addQuest((new LOTRMiniQuestCollect.QFCollect("findOldDagger")).setCollectItem(new ItemStack(LOTRMod.daggerAncientHarad), 1, 2).setRewardFactor(20.0F));
      UMBAR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGondor")).setKillFaction(LOTRFaction.GONDOR, 10, 30));
      UMBAR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("revengeGondor")).setKillFaction(LOTRFaction.GONDOR, 20, 40));
      UMBAR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killForAnadune")).setKillEntity(LOTREntityGondorSoldier.class, 20, 50));
      UMBAR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRangers")).setKillEntity(LOTREntityRangerIthilien.class, 10, 40));
      UMBAR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killSwanKnights")).setKillEntity(LOTREntitySwanKnight.class, 10, 30));
      UMBAR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      CORSAIR.setAchievement(LOTRAchievement.doMiniquestNearHarad);
      CORSAIR.setLore(LOTRLore.LoreCategory.UMBAR);
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("whipMaterial")).setCollectItem(new ItemStack(Items.field_151007_F), 5, 12).setRewardFactor(1.0F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("whipMaterial")).setCollectItem(new ItemStack(LOTRMod.rope), 5, 12).setRewardFactor(1.1F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("whipMaterial")).setCollectItem(new ItemStack(Items.field_151116_aA), 10, 20).setRewardFactor(0.75F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("scurvy")).setCollectItem(new ItemStack(LOTRMod.orange), 6, 12).setRewardFactor(1.75F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("scurvy")).setCollectItem(new ItemStack(LOTRMod.lemon), 6, 12).setRewardFactor(1.75F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("scurvy")).setCollectItem(new ItemStack(LOTRMod.lemon), 6, 12).setRewardFactor(1.75F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugAraq), 4, 10).setRewardFactor(2.5F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugRum), 4, 10).setRewardFactor(2.5F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugCactusLiqueur), 4, 10).setRewardFactor(2.5F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugCarrotWine), 4, 10).setRewardFactor(2.5F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugBananaBeer), 4, 10).setRewardFactor(2.5F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDrink")).setCollectItem(new ItemStack(LOTRMod.mugCornLiquor), 4, 10).setRewardFactor(2.5F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectChests")).setCollectItem(new ItemStack(Blocks.field_150486_ae), 8, 16).setRewardFactor(1.0F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectChests")).setCollectItem(new ItemStack(LOTRMod.chestBasket), 5, 10).setRewardFactor(2.0F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("collectChests")).setCollectItem(new ItemStack(LOTRMod.pouch, 1, 0), 3, 5).setRewardFactor(5.0F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("poisonCaptain")).setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("fixSails")).setCollectItem(new ItemStack(Items.field_151007_F), 5, 12).setRewardFactor(1.0F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("fixSails")).setCollectItem(new ItemStack(LOTRMod.rope), 5, 12).setRewardFactor(1.1F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("fixSails")).setCollectItem(new ItemStack(Blocks.field_150325_L, 1, 15), 6, 15).setRewardFactor(1.0F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("fixSails")).setCollectItem(new ItemStack(Blocks.field_150325_L, 1, 14), 6, 15).setRewardFactor(1.0F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("fixSails")).setCollectItem(new ItemStack(Blocks.field_150325_L, 1, 12), 6, 15).setRewardFactor(1.0F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("fixSails")).setCollectItem(new ItemStack(Blocks.field_150325_L, 1, 0), 6, 15).setRewardFactor(1.0F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("fixShip")).setCollectItem(new ItemStack(LOTRMod.planks2, 1, 2), 60, 120).setRewardFactor(0.2F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("fixShip")).setCollectItem(new ItemStack(LOTRMod.planks3, 1, 3), 60, 120).setRewardFactor(0.2F));
      CORSAIR.addQuest((new LOTRMiniQuestCollect.QFCollect("fixShip")).setCollectItem(new ItemStack(LOTRMod.planks2, 1, 11), 60, 120).setRewardFactor(0.2F));
      CORSAIR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killGondor")).setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
      CORSAIR.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRangers")).setKillEntity(LOTREntityRangerIthilien.class, 10, 20).setRewardFactor(1.5F));
      CORSAIR.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killTaurethrim")).setKillFaction(LOTRFaction.TAURETHRIM, 10, 30));
      CORSAIR.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      GONDOR_RENEGADE.setAchievement(LOTRAchievement.doMiniquestGondorRenegade);
      GONDOR_RENEGADE.setLore(LOTRLore.LoreCategory.UMBAR);
      GONDOR_RENEGADE.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killGondorSoldiers")).setKillEntity(LOTREntityGondorSoldier.class, 3, 8));
      var0 = GONDOR_RENEGADE.questFactories.values().iterator();

      while(var0.hasNext()) {
         qfList = (List)var0.next();
         var2 = qfList.iterator();

         while(var2.hasNext()) {
            qf = (LOTRMiniQuest.QuestFactoryBase)var2.next();
            qf.setRewardFactor(0.0F);
            qf.setHiring(50.0F);
         }
      }

      NOMAD.setAchievement(LOTRAchievement.doMiniquestNearHarad);
      NOMAD.setLore(LOTRLore.LoreCategory.NOMAD);
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDates")).setCollectItem(new ItemStack(LOTRMod.date), 8, 15).setRewardFactor(2.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.lemon), 4, 12).setRewardFactor(2.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.orange), 4, 12).setRewardFactor(2.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.lime), 4, 12).setRewardFactor(2.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFruit")).setCollectItem(new ItemStack(LOTRMod.plum), 4, 12).setRewardFactor(2.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("orangeJuice")).setCollectItem(new ItemStack(LOTRMod.mugOrangeJuice), 2, 6).setRewardFactor(4.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("lemonLiqueur")).setCollectItem(new ItemStack(LOTRMod.mugLemonLiqueur), 2, 6).setRewardFactor(4.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPoison")).setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("camelCarpets")).setCollectItem(new ItemStack(Blocks.field_150404_cg, 14), 8, 15).setRewardFactor(0.75F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("camelCarpets")).setCollectItem(new ItemStack(Blocks.field_150404_cg, 4), 8, 15).setRewardFactor(0.75F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("camelCarpets")).setCollectItem(new ItemStack(Blocks.field_150404_cg, 13), 8, 15).setRewardFactor(0.75F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("camelCarpets")).setCollectItem(new ItemStack(Blocks.field_150404_cg, 11), 8, 15).setRewardFactor(0.75F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("camelCarpets")).setCollectItem(new ItemStack(Blocks.field_150404_cg, 10), 8, 15).setRewardFactor(0.75F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("camelCarpets")).setCollectItem(new ItemStack(Blocks.field_150404_cg, 5), 8, 15).setRewardFactor(0.75F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("camelCarpets")).setCollectItem(new ItemStack(Blocks.field_150404_cg, 4), 8, 15).setRewardFactor(0.75F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("camelCarpets")).setCollectItem(new ItemStack(Blocks.field_150404_cg, 3), 8, 15).setRewardFactor(0.75F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("camelCarpets")).setCollectItem(new ItemStack(Blocks.field_150404_cg, 1), 8, 15).setRewardFactor(0.75F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("waterskins")).setCollectItem(new ItemStack(LOTRMod.waterskin), 8, 16).setRewardFactor(1.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("gulfEquipment")).setCollectItem(new ItemStack(LOTRMod.swordGulfHarad), 1, 2).setRewardFactor(8.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("gulfEquipment")).setCollectItem(new ItemStack(LOTRMod.helmetGulfHarad), 1, 2).setRewardFactor(8.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("gulfEquipment")).setCollectItem(new ItemStack(LOTRMod.bodyGulfHarad), 1, 2).setRewardFactor(8.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarEquipment")).setCollectItem(new ItemStack(LOTRMod.scimitarNearHarad), 1, 2).setRewardFactor(8.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarEquipment")).setCollectItem(new ItemStack(LOTRMod.helmetUmbar), 1, 2).setRewardFactor(8.0F));
      NOMAD.addQuest((new LOTRMiniQuestCollect.QFCollect("umbarEquipment")).setCollectItem(new ItemStack(LOTRMod.bodyUmbar), 1, 2).setRewardFactor(8.0F));
      NOMAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killScorpions")).setKillEntity(LOTREntityDesertScorpion.class, 10, 30));
      NOMAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killManyScorpions")).setKillEntity(LOTREntityDesertScorpion.class, 40, 80));
      NOMAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killBandits")).setKillEntity(LOTREntityBanditHarad.class, 1, 3).setRewardFactor(8.0F));
      NOMAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRangers")).setKillEntity(LOTREntityRangerIthilien.class, 10, 20));
      NOMAD.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      GULF_HARAD.setAchievement(LOTRAchievement.doMiniquestNearHarad);
      GULF_HARAD.setLore(LOTRLore.LoreCategory.GULF);
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDates")).setCollectItem(new ItemStack(LOTRMod.date), 8, 15).setRewardFactor(2.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("orangeJuice")).setCollectItem(new ItemStack(LOTRMod.mugOrangeJuice), 2, 6).setRewardFactor(4.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("lemonLiqueur")).setCollectItem(new ItemStack(LOTRMod.mugLemonLiqueur), 2, 6).setRewardFactor(4.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectPoison")).setCollectItem(new ItemStack(LOTRMod.bottlePoison), 2, 4).setRewardFactor(5.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectIronWeapon")).setCollectItem(new ItemStack(LOTRMod.scimitarNearHarad), 2, 3).setRewardFactor(5.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectIronWeapon")).setCollectItem(new ItemStack(LOTRMod.spearNearHarad), 2, 3).setRewardFactor(4.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectIronWeapon")).setCollectItem(new ItemStack(LOTRMod.poleaxeNearHarad), 2, 3).setRewardFactor(6.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectIronWeapon")).setCollectItem(new ItemStack(LOTRMod.maceNearHarad), 2, 3).setRewardFactor(6.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("findOldDagger")).setCollectItem(new ItemStack(LOTRMod.daggerAncientHarad), 1, 2).setRewardFactor(20.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("morwaithStuff")).setCollectItem(new ItemStack(LOTRMod.helmetMoredain), 1, 1).setRewardFactor(8.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("morwaithStuff")).setCollectItem(new ItemStack(LOTRMod.bodyMoredain), 1, 1).setRewardFactor(8.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("morwaithStuff")).setCollectItem(new ItemStack(LOTRMod.legsMoredain), 1, 1).setRewardFactor(8.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("morwaithStuff")).setCollectItem(new ItemStack(LOTRMod.bootsMoredain), 1, 1).setRewardFactor(8.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("easterlingStuff")).setCollectItem(new ItemStack(LOTRMod.helmetRhunGold), 1, 1).setRewardFactor(20.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("easterlingStuff")).setCollectItem(new ItemStack(LOTRMod.bodyRhunGold), 1, 1).setRewardFactor(20.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("easterlingStuff")).setCollectItem(new ItemStack(LOTRMod.legsRhunGold), 1, 1).setRewardFactor(20.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("easterlingStuff")).setCollectItem(new ItemStack(LOTRMod.bootsRhunGold), 1, 1).setRewardFactor(20.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBones")).setCollectItem(new ItemStack(Items.field_151103_aS), 10, 20).setRewardFactor(1.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("templeBuild")).setCollectItem(new ItemStack(Blocks.field_150322_A, 1, 0), 30, 80).setRewardFactor(0.25F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("templeBuild")).setCollectItem(new ItemStack(LOTRMod.brick, 1, 15), 30, 60).setRewardFactor(0.5F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("templeBuild")).setCollectItem(new ItemStack(LOTRMod.brick3, 1, 13), 30, 60).setRewardFactor(0.5F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("templeBuild")).setCollectItem(new ItemStack(LOTRMod.thatch, 1, 1), 10, 20).setRewardFactor(0.5F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("templeBuild")).setCollectItem(new ItemStack(LOTRMod.wood8, 1, 3), 30, 60).setRewardFactor(0.25F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("templeBuild")).setCollectItem(new ItemStack(LOTRMod.planks3, 1, 3), 60, 120).setRewardFactor(0.125F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("templeBuild")).setCollectItem(new ItemStack(LOTRMod.wood9, 1, 0), 30, 60).setRewardFactor(0.25F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("templeBuild")).setCollectItem(new ItemStack(LOTRMod.planks3, 1, 4), 60, 120).setRewardFactor(0.125F));
      GULF_HARAD.addQuest((new LOTRMiniQuestCollect.QFCollect("templeBuild")).setCollectItem(new ItemStack(LOTRMod.boneBlock, 1, 0), 5, 10).setRewardFactor(2.0F));
      GULF_HARAD.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killTaurethrim")).setKillFaction(LOTRFaction.TAURETHRIM, 10, 30));
      GULF_HARAD.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGondor")).setKillFaction(LOTRFaction.GONDOR, 20, 40));
      GULF_HARAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killScorpions")).setKillEntity(LOTREntityDesertScorpion.class, 10, 30));
      GULF_HARAD.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRangers")).setKillEntity(LOTREntityRangerIthilien.class, 20, 40));
      GULF_HARAD.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      MOREDAIN.setAchievement(LOTRAchievement.doMiniquestMoredain);
      MOREDAIN.setLore(LOTRLore.LoreCategory.FAR_HARAD);
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectLionFur")).setCollectItem(new ItemStack(LOTRMod.lionFur), 3, 6).setRewardFactor(3.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.lionCooked), 4, 6).setRewardFactor(3.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.zebraCooked), 4, 6).setRewardFactor(2.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.rhinoCooked), 4, 6).setRewardFactor(3.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(Items.field_151025_P), 5, 8).setRewardFactor(2.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectFood")).setCollectItem(new ItemStack(LOTRMod.yamRoast), 5, 8).setRewardFactor(2.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectHide")).setCollectItem(new ItemStack(LOTRMod.gemsbokHide), 4, 12).setRewardFactor(2.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBananas")).setCollectItem(new ItemStack(LOTRMod.banana), 4, 6).setRewardFactor(4.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.battleaxeMoredain), 1, 4).setRewardFactor(5.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.daggerMoredain), 1, 4).setRewardFactor(5.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.spearMoredain), 1, 4).setRewardFactor(5.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.swordMoredain), 1, 4).setRewardFactor(5.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.clubMoredain), 1, 4).setRewardFactor(5.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("huntRhino")).setCollectItem(new ItemStack(LOTRMod.rhinoHorn), 1, 3).setRewardFactor(8.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDates")).setCollectItem(new ItemStack(LOTRMod.date), 3, 5).setRewardFactor(4.0F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warResources")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 30, 60).setRewardFactor(0.3F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warResources")).setCollectItem(new ItemStack(Blocks.field_150363_s, 1, 0), 30, 60).setRewardFactor(0.3F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warResources")).setCollectItem(new ItemStack(LOTRMod.gemsbokHide), 6, 15).setRewardFactor(1.5F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warResources")).setCollectItem(new ItemStack(Items.field_151055_y), 80, 200).setRewardFactor(0.05F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("wallMaterials")).setCollectItem(new ItemStack(LOTRMod.brick3, 1, 10), 40, 60).setRewardFactor(0.2F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("wallMaterials")).setCollectItem(new ItemStack(Blocks.field_150405_ch), 20, 30).setRewardFactor(0.5F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("roofMaterials")).setCollectItem(new ItemStack(LOTRMod.thatch), 10, 20).setRewardFactor(0.5F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("jungleWood")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 3), 40, 80).setRewardFactor(0.25F));
      MOREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("jungleWood")).setCollectItem(new ItemStack(LOTRMod.wood6, 1, 0), 40, 80).setRewardFactor(0.25F));
      MOREDAIN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGondor")).setKillFaction(LOTRFaction.GONDOR, 20, 50));
      MOREDAIN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killGondorSoldier")).setKillEntity(LOTREntityGondorSoldier.class, 10, 30));
      MOREDAIN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRanger")).setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
      MOREDAIN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killCrocodile")).setKillEntity(LOTREntityCrocodile.class, 10, 20));
      MOREDAIN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killLion")).setKillEntity(LOTREntityLion.class, 10, 20));
      MOREDAIN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killLion")).setKillEntity(LOTREntityLioness.class, 10, 20));
      MOREDAIN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killTauredain")).setKillFaction(LOTRFaction.TAURETHRIM, 20, 50));
      MOREDAIN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killTauredainBlowgunner")).setKillEntity(LOTREntityTauredainBlowgunner.class, 10, 30));
      MOREDAIN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      TAUREDAIN.setAchievement(LOTRAchievement.doMiniquestTauredain);
      TAUREDAIN.setLore(LOTRLore.LoreCategory.FAR_HARAD_JUNGLE);
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.swordTauredain), 1, 4).setRewardFactor(5.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.daggerTauredain), 1, 4).setRewardFactor(4.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.daggerTauredainPoisoned), 1, 3).setRewardFactor(6.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.axeTauredain), 1, 4).setRewardFactor(5.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.spearTauredain), 1, 4).setRewardFactor(5.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.hammerTauredain), 1, 4).setRewardFactor(5.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.battleaxeTauredain), 1, 4).setRewardFactor(5.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWeapons")).setCollectItem(new ItemStack(LOTRMod.pikeTauredain), 1, 4).setRewardFactor(5.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectObsidian")).setCollectItem(new ItemStack(LOTRMod.obsidianShard), 10, 30).setRewardFactor(0.75F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectCocoa")).setCollectItem(new ItemStack(Items.field_151100_aR, 1, 3), 8, 20).setRewardFactor(1.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warriorFood")).setCollectItem(new ItemStack(Items.field_151025_P), 5, 8).setRewardFactor(2.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warriorFood")).setCollectItem(new ItemStack(LOTRMod.bananaBread), 5, 8).setRewardFactor(2.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warriorFood")).setCollectItem(new ItemStack(LOTRMod.cornBread), 5, 8).setRewardFactor(2.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warriorFood")).setCollectItem(new ItemStack(LOTRMod.banana), 4, 6).setRewardFactor(4.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warriorFood")).setCollectItem(new ItemStack(LOTRMod.mango), 4, 6).setRewardFactor(4.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warriorFood")).setCollectItem(new ItemStack(Items.field_151127_ba), 10, 20).setRewardFactor(0.75F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warriorFood")).setCollectItem(new ItemStack(LOTRMod.melonSoup), 3, 8).setRewardFactor(2.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warriorFood")).setCollectItem(new ItemStack(LOTRMod.corn), 6, 12).setRewardFactor(1.5F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("warriorFood")).setCollectItem(new ItemStack(LOTRMod.cornCooked), 5, 10).setRewardFactor(2.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDarts")).setCollectItem(new ItemStack(LOTRMod.tauredainDart), 20, 40).setRewardFactor(0.5F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectDarts")).setCollectItem(new ItemStack(LOTRMod.tauredainDartPoisoned), 10, 20).setRewardFactor(1.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("collectBanners")).setCollectItem(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.TAUREDAIN.bannerID), 5, 15).setRewardFactor(1.5F));
      TAUREDAIN.addQuest((new LOTRMiniQuestCollect.QFCollect("amulet")).setCollectItem(new ItemStack(LOTRMod.tauredainAmulet), 1, 4).setRewardFactor(20.0F));
      TAUREDAIN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killMoredain")).setKillFaction(LOTRFaction.MORWAITH, 20, 50));
      TAUREDAIN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killMoredainWarrior")).setKillEntity(LOTREntityMoredainWarrior.class, 10, 30));
      TAUREDAIN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killHalfTrolls")).setKillFaction(LOTRFaction.HALF_TROLL, 10, 40));
      TAUREDAIN.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killNearHaradrim")).setKillFaction(LOTRFaction.NEAR_HARAD, 20, 50));
      TAUREDAIN.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killNearHaradWarrior")).setKillEntity(LOTREntityNearHaradrimWarrior.class, 10, 30));
      TAUREDAIN.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
      HALF_TROLL.setAchievement(LOTRAchievement.doMiniquestHalfTroll);
      HALF_TROLL.setLore(LOTRLore.LoreCategory.HALF_TROLL);
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.scimitarHalfTroll), 2, 5).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.maceHalfTroll), 2, 5).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.pikeHalfTroll), 2, 5).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.daggerHalfTroll), 2, 5).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.battleaxeHalfTroll), 2, 5).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.helmetHalfTroll), 1, 4).setRewardFactor(4.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.bodyHalfTroll), 1, 4).setRewardFactor(5.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.legsHalfTroll), 1, 4).setRewardFactor(4.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectEquipment")).setCollectItem(new ItemStack(LOTRMod.bootsHalfTroll), 1, 4).setRewardFactor(4.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("flesh")).setCollectItem(new ItemStack(LOTRMod.lionRaw), 2, 6).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("flesh")).setCollectItem(new ItemStack(LOTRMod.zebraRaw), 2, 6).setRewardFactor(2.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("flesh")).setCollectItem(new ItemStack(LOTRMod.rhinoRaw), 2, 6).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("flesh")).setCollectItem(new ItemStack(Items.field_151078_bh), 3, 8).setRewardFactor(2.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 30, 60).setRewardFactor(0.3F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(Blocks.field_150363_s, 1, 0), 30, 60).setRewardFactor(0.3F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("collectWood")).setCollectItem(new ItemStack(LOTRMod.wood4, 1, 1), 20, 40).setRewardFactor(0.5F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("manTrophy")).setCollectItem(new ItemStack(LOTRMod.swordGondor), 1, 1).setRewardFactor(20.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("manTrophy")).setCollectItem(new ItemStack(LOTRMod.gondorBow), 1, 1).setRewardFactor(20.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("manTrophy")).setCollectItem(new ItemStack(LOTRMod.beacon), 1, 1).setRewardFactor(20.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("resources")).setCollectItem(new ItemStack(Blocks.field_150364_r, 1, 0), 30, 80).setRewardFactor(0.25F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("resources")).setCollectItem(new ItemStack(Items.field_151044_h), 10, 30).setRewardFactor(0.5F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("resources")).setCollectItem(new ItemStack(Blocks.field_150347_e), 30, 80).setRewardFactor(0.25F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("resources")).setCollectItem(new ItemStack(LOTRMod.gemsbokHide), 10, 30).setRewardFactor(0.5F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("huntItems")).setCollectItem(new ItemStack(LOTRMod.lionRaw), 4, 8).setRewardFactor(2.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("huntItems")).setCollectItem(new ItemStack(LOTRMod.rhinoHorn), 3, 6).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("huntItems")).setCollectItem(new ItemStack(LOTRMod.gemsbokHide), 4, 10).setRewardFactor(2.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("huntItems")).setCollectItem(new ItemStack(LOTRMod.gemsbokHorn), 3, 6).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("mordorItems")).setCollectItem(new ItemStack(LOTRMod.orcSteel), 4, 8).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("mordorItems")).setCollectItem(new ItemStack(LOTRMod.scimitarOrc), 3, 5).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("mordorItems")).setCollectItem(new ItemStack(LOTRMod.battleaxeOrc), 3, 5).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("mordorItems")).setCollectItem(new ItemStack(LOTRMod.hammerOrc), 3, 5).setRewardFactor(3.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("tribeItem")).setCollectItem(new ItemStack(LOTRMod.swordTauredain), 1, 1).setRewardFactor(20.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestCollect.QFCollect("tribeItem")).setCollectItem(new ItemStack(LOTRMod.daggerTauredain), 1, 1).setRewardFactor(20.0F));
      HALF_TROLL.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killGondor")).setKillFaction(LOTRFaction.GONDOR, 20, 50));
      HALF_TROLL.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killGondorSoldier")).setKillEntity(LOTREntityGondorSoldier.class, 20, 40));
      HALF_TROLL.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRanger")).setKillEntity(LOTREntityRangerIthilien.class, 10, 30));
      HALF_TROLL.addQuest((new LOTRMiniQuestKillEntity.QFKillEntity("killRohirrim")).setKillEntity(LOTREntityRohirrimWarrior.class, 10, 30));
      HALF_TROLL.addQuest((new LOTRMiniQuestKillFaction.QFKillFaction("killTauredain")).setKillFaction(LOTRFaction.TAURETHRIM, 20, 50));
      HALF_TROLL.addQuest(new LOTRMiniQuestBounty.QFBounty("bounty"));
   }

   public static LOTRMiniQuestFactory forName(String name) {
      LOTRMiniQuestFactory[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRMiniQuestFactory group = var1[var3];
         if (group.getBaseName().equals(name)) {
            return group;
         }
      }

      return null;
   }
}
