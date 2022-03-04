package lotr.common.quest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.client.LOTRKeyHandler;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRGreyWandererTracker;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityGandalf;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRSpeech;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class LOTRMiniQuestWelcome extends LOTRMiniQuest {
   private static final String SPEECHBANK = "char/gandalf/quest";
   public int stage;
   public static final int STAGE_GET_ITEMS = 1;
   public static final int STAGE_READ_BOOK = 2;
   public static final int STAGE_EXPLAIN_BOOK = 3;
   public static final int STAGE_EXPLAIN_MAP = 4;
   public static final int STAGE_OPEN_MAP = 5;
   public static final int STAGE_EXPLAIN_FACTIONS = 6;
   public static final int STAGE_EXPLAIN_ALIGNMENT = 7;
   public static final int STAGE_CYCLE_ALIGNMENT = 8;
   public static final int STAGE_CYCLE_REGIONS = 9;
   public static final int STAGE_EXPLAIN_FACTION_GUIDE = 10;
   public static final int STAGE_OPEN_FACTIONS = 11;
   public static final int STAGE_TALK_ADVENTURES = 12;
   public static final int STAGE_GET_POUCHES = 13;
   public static final int STAGE_TALK_FINAL = 14;
   public static final int STAGE_COMPLETE = 15;
   public static final int NUM_STAGES = 15;
   private boolean movedOn;

   public LOTRMiniQuestWelcome(LOTRPlayerData pd) {
      super(pd);
      this.stage = 0;
   }

   public LOTRMiniQuestWelcome(LOTRPlayerData pd, LOTREntityGandalf gandalf) {
      this(pd);
      this.setNPCInfo(gandalf);
      this.speechBankStart = "";
      this.speechBankProgress = "";
      this.speechBankComplete = "";
      this.speechBankTooMany = "";
      this.quoteStart = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 2);
      this.quoteComplete = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 12);
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.func_74774_a("WStage", (byte)this.stage);
      nbt.func_74757_a("WMovedOn", this.movedOn);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.stage = nbt.func_74771_c("WStage");
      this.movedOn = nbt.func_74767_n("WMovedOn");
   }

   public String getFactionSubtitle() {
      return "";
   }

   public String getQuestObjective() {
      if (this.stage == 2) {
         return StatCollector.func_74838_a("lotr.miniquest.welcome.book");
      } else {
         KeyBinding keyMenu;
         if (this.stage == 5) {
            keyMenu = LOTRKeyHandler.keyBindingMenu;
            return StatCollector.func_74837_a("lotr.miniquest.welcome.map", new Object[]{GameSettings.func_74298_c(keyMenu.func_151463_i())});
         } else {
            KeyBinding keyDown;
            if (this.stage == 8) {
               keyMenu = LOTRKeyHandler.keyBindingAlignmentCycleLeft;
               keyDown = LOTRKeyHandler.keyBindingAlignmentCycleRight;
               return StatCollector.func_74837_a("lotr.miniquest.welcome.align", new Object[]{GameSettings.func_74298_c(keyMenu.func_151463_i()), GameSettings.func_74298_c(keyDown.func_151463_i())});
            } else if (this.stage == 9) {
               keyMenu = LOTRKeyHandler.keyBindingAlignmentGroupPrev;
               keyDown = LOTRKeyHandler.keyBindingAlignmentGroupNext;
               return StatCollector.func_74837_a("lotr.miniquest.welcome.alignRegions", new Object[]{GameSettings.func_74298_c(keyMenu.func_151463_i()), GameSettings.func_74298_c(keyDown.func_151463_i())});
            } else if (this.stage == 11) {
               keyMenu = LOTRKeyHandler.keyBindingMenu;
               return StatCollector.func_74837_a("lotr.miniquest.welcome.factions", new Object[]{GameSettings.func_74298_c(keyMenu.func_151463_i())});
            } else {
               return StatCollector.func_74838_a("lotr.miniquest.welcome.speak");
            }
         }
      }
   }

   public String getObjectiveInSpeech() {
      return "OBJECTIVE_SPEECH";
   }

   public String getProgressedObjectiveInSpeech() {
      return "OBJECTIVE_SPEECH_PROGRESSED";
   }

   public String getQuestProgress() {
      return this.getQuestProgressShorthand();
   }

   public String getQuestProgressShorthand() {
      return StatCollector.func_74837_a("lotr.miniquest.progressShort", new Object[]{this.stage, 15});
   }

   public float getCompletionFactor() {
      return (float)this.stage / 15.0F;
   }

   public ItemStack getQuestIcon() {
      return new ItemStack(LOTRMod.redBook);
   }

   public boolean canPlayerAccept(EntityPlayer entityplayer) {
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      return !pd.hasAnyGWQuest();
   }

   private void updateGreyWanderer() {
      LOTRGreyWandererTracker.setWandererActive(this.entityUUID);
   }

   public void start(EntityPlayer entityplayer, LOTREntityNPC npc) {
      super.start(entityplayer, npc);
      String line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 3);
      this.sendQuoteSpeech(entityplayer, npc, line);
      this.quotesStages.add(line);
      this.stage = 1;
      this.updateQuest();
      this.updateGreyWanderer();
   }

   public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
      this.updateGreyWanderer();
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      ArrayList dropItems;
      String line;
      if (this.stage == 1) {
         dropItems = new ArrayList();
         dropItems.add(new ItemStack(LOTRMod.redBook));
         npc.dropItemList(dropItems);
         line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 4);
         this.sendQuoteSpeech(entityplayer, npc, line);
         this.quotesStages.add(line);
         this.stage = 2;
         this.updateQuest();
      } else {
         String line;
         if (this.stage == 2) {
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 4);
            this.sendQuoteSpeech(entityplayer, npc, line);
         } else if (this.stage == 3) {
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 5);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 4;
            this.updateQuest();
         } else if (this.stage == 4) {
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 6);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 5;
            this.updateQuest();
         } else if (this.stage == 5) {
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 6);
            this.sendQuoteSpeech(entityplayer, npc, line);
         } else if (this.stage == 6) {
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 7);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 7;
            this.updateQuest();
         } else if (this.stage == 7) {
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 8);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 8;
            this.updateQuest();
         } else if (this.stage == 8) {
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 8);
            this.sendQuoteSpeech(entityplayer, npc, line);
         } else if (this.stage == 9) {
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 8);
            this.sendQuoteSpeech(entityplayer, npc, line);
         } else if (this.stage == 10) {
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 9);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 11;
            this.updateQuest();
         } else if (this.stage == 11) {
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 9);
            this.sendQuoteSpeech(entityplayer, npc, line);
         } else if (this.stage == 12) {
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 10);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 13;
            this.updateQuest();
         } else if (this.stage == 13) {
            dropItems = new ArrayList();
            if (!pd.getQuestData().getGivenFirstPouches()) {
               dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
               dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
               dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
               pd.getQuestData().setGivenFirstPouches(true);
            }

            npc.dropItemList(dropItems);
            line = LOTRSpeech.getSpeechAtLine("char/gandalf/quest", 11);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 14;
            this.updateQuest();
         } else if (this.stage == 14) {
            this.stage = 15;
            this.updateQuest();
            this.complete(entityplayer, npc);
         }
      }

   }

   public void handleEvent(LOTRMiniQuestEvent event) {
      if (event instanceof LOTRMiniQuestEvent.OpenRedBook && this.stage == 2) {
         this.stage = 3;
         this.updateQuest();
         this.updateGreyWanderer();
      }

      if (event instanceof LOTRMiniQuestEvent.ViewMap && this.stage == 5) {
         this.stage = 6;
         this.updateQuest();
         this.updateGreyWanderer();
      }

      if (event instanceof LOTRMiniQuestEvent.CycleAlignment && this.stage == 8) {
         this.stage = 9;
         this.updateQuest();
         this.updateGreyWanderer();
      }

      if (event instanceof LOTRMiniQuestEvent.CycleAlignmentRegion && this.stage == 9) {
         this.stage = 10;
         this.updateQuest();
         this.updateGreyWanderer();
      }

      if (event instanceof LOTRMiniQuestEvent.ViewFactions && this.stage == 11) {
         this.stage = 12;
         this.updateQuest();
         this.updateGreyWanderer();
      }

   }

   protected void complete(EntityPlayer entityplayer, LOTREntityNPC npc) {
      super.complete(entityplayer, npc);
      this.updateGreyWanderer();
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.doGreyQuest);
   }

   public void onPlayerTick(EntityPlayer entityplayer) {
      if (!LOTRGreyWandererTracker.isWandererActive(this.entityUUID)) {
         this.movedOn = true;
         this.updateQuest();
      }

   }

   public boolean isFailed() {
      return super.isFailed() || this.movedOn;
   }

   public String getQuestFailure() {
      return this.movedOn ? StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.movedOn", new Object[]{this.entityName}) : super.getQuestFailure();
   }

   public String getQuestFailureShorthand() {
      return this.movedOn ? StatCollector.func_74838_a("lotr.gui.redBook.mq.movedOn") : super.getQuestFailureShorthand();
   }

   public float getAlignmentBonus() {
      return 0.0F;
   }

   public int getCoinBonus() {
      return 0;
   }

   protected boolean canRewardVariousExtraItems() {
      return false;
   }

   public static boolean[] forceMenu_Map_Factions(EntityPlayer entityplayer) {
      boolean[] flags = new boolean[]{false, false};
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      List activeQuests = pd.getActiveMiniQuests();
      Iterator var4 = activeQuests.iterator();

      while(var4.hasNext()) {
         LOTRMiniQuest quest = (LOTRMiniQuest)var4.next();
         if (quest instanceof LOTRMiniQuestWelcome) {
            LOTRMiniQuestWelcome qw = (LOTRMiniQuestWelcome)quest;
            if (qw.stage == 5) {
               flags[0] = true;
               break;
            }

            if (qw.stage == 11) {
               flags[1] = true;
               break;
            }
         }
      }

      return flags;
   }
}
