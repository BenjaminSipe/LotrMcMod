package lotr.common.quest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionBounties;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringUtils;

public class LOTRMiniQuestBounty extends LOTRMiniQuest {
   private static final int KILL_THRESHOLD = 25;
   private static final float MIN_ALIGNMENT_LOWERING = 100.0F;
   public UUID targetID;
   public String targetName;
   public boolean killed;
   public float alignmentBonus;
   public int coinBonus;
   private boolean bountyClaimedByOther;
   private boolean killedByBounty;

   public LOTRMiniQuestBounty(LOTRPlayerData pd) {
      super(pd);
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      if (this.targetID != null) {
         nbt.func_74778_a("TargetID", this.targetID.toString());
      }

      if (this.targetName != null) {
         nbt.func_74778_a("TargetName", this.targetName);
      }

      nbt.func_74757_a("Killed", this.killed);
      nbt.func_74776_a("AlignF", this.alignmentBonus);
      nbt.func_74768_a("Coins", this.coinBonus);
      nbt.func_74757_a("BountyClaimed", this.bountyClaimedByOther);
      nbt.func_74757_a("KilledBy", this.killedByBounty);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      if (nbt.func_74764_b("TargetID")) {
         String s = nbt.func_74779_i("TargetID");
         this.targetID = UUID.fromString(s);
      }

      if (nbt.func_74764_b("TargetName")) {
         this.targetName = nbt.func_74779_i("TargetName");
      }

      this.killed = nbt.func_74767_n("Killed");
      if (nbt.func_74764_b("Alignment")) {
         this.alignmentBonus = (float)nbt.func_74762_e("Alignment");
      } else {
         this.alignmentBonus = nbt.func_74760_g("AlignF");
      }

      this.coinBonus = nbt.func_74762_e("Coins");
      this.bountyClaimedByOther = nbt.func_74767_n("BountyClaimed");
      this.killedByBounty = nbt.func_74767_n("KilledBy");
   }

   public boolean isValidQuest() {
      return super.isValidQuest() && this.targetID != null;
   }

   public boolean canPlayerAccept(EntityPlayer entityplayer) {
      if (super.canPlayerAccept(entityplayer) && !this.targetID.equals(entityplayer.func_110124_au()) && LOTRLevelData.getData(entityplayer).getAlignment(this.entityFaction) >= 100.0F) {
         LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
         List active = pd.getActiveMiniQuests();
         Iterator var4 = active.iterator();

         LOTRMiniQuest quest;
         do {
            if (!var4.hasNext()) {
               return true;
            }

            quest = (LOTRMiniQuest)var4.next();
         } while(!(quest instanceof LOTRMiniQuestBounty) || !((LOTRMiniQuestBounty)quest).targetID.equals(this.targetID));

         return false;
      } else {
         return false;
      }
   }

   public String getQuestObjective() {
      return StatCollector.func_74837_a("lotr.miniquest.bounty", new Object[]{this.targetName});
   }

   public String getObjectiveInSpeech() {
      return this.targetName;
   }

   public String getProgressedObjectiveInSpeech() {
      return this.targetName;
   }

   public String getQuestProgress() {
      return this.killed ? StatCollector.func_74838_a("lotr.miniquest.bounty.progress.slain") : StatCollector.func_74838_a("lotr.miniquest.bounty.progress.notSlain");
   }

   public String getQuestProgressShorthand() {
      return StatCollector.func_74837_a("lotr.miniquest.progressShort", new Object[]{this.killed ? 1 : 0, 1});
   }

   public float getCompletionFactor() {
      return this.killed ? 1.0F : 0.0F;
   }

   public ItemStack getQuestIcon() {
      return new ItemStack(Items.field_151040_l);
   }

   public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
      if (this.killed) {
         this.complete(entityplayer, npc);
      } else {
         this.sendProgressSpeechbank(entityplayer, npc);
      }

   }

   public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
      if (!this.killed && !this.isFailed() && entity instanceof EntityPlayer && ((EntityPlayer)entity).func_110124_au().equals(this.targetID)) {
         EntityPlayer slainPlayer = (EntityPlayer)entity;
         LOTRPlayerData slainPlayerData = LOTRLevelData.getData(slainPlayer);
         this.killed = true;
         LOTRFactionBounties.forFaction(this.entityFaction).forPlayer(slainPlayer).recordBountyKilled();
         this.updateQuest();
         LOTRFaction highestFaction = this.getPledgeOrHighestAlignmentFaction(slainPlayer, 100.0F);
         if (highestFaction != null) {
            float curAlignment = slainPlayerData.getAlignment(highestFaction);
            float alignmentLoss = this.getKilledAlignmentPenalty();
            if (curAlignment + alignmentLoss < 100.0F) {
               alignmentLoss = -(curAlignment - 100.0F);
            }

            LOTRAlignmentValues.AlignmentBonus source = new LOTRAlignmentValues.AlignmentBonus(alignmentLoss, "lotr.alignment.bountyKilled");
            slainPlayerData.addAlignment(slainPlayer, source, highestFaction, entityplayer);
            IChatComponent slainMsg1 = new ChatComponentTranslation("chat.lotr.bountyKilled1", new Object[]{entityplayer.func_70005_c_(), this.entityFaction.factionName()});
            IChatComponent slainMsg2 = new ChatComponentTranslation("chat.lotr.bountyKilled2", new Object[]{highestFaction.factionName()});
            slainMsg1.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
            slainMsg2.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
            slainPlayer.func_145747_a(slainMsg1);
            slainPlayer.func_145747_a(slainMsg2);
         }

         IChatComponent announceMsg = new ChatComponentTranslation("chat.lotr.bountyKill", new Object[]{entityplayer.func_70005_c_(), slainPlayer.func_70005_c_(), this.entityFaction.factionName()});
         announceMsg.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
         Iterator var12 = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b.iterator();

         while(var12.hasNext()) {
            Object obj = var12.next();
            EntityPlayer otherPlayer = (EntityPlayer)obj;
            if (otherPlayer != slainPlayer) {
               otherPlayer.func_145747_a(announceMsg);
            }
         }
      }

   }

   public void onKilledByPlayer(EntityPlayer entityplayer, EntityPlayer killer) {
      if (!this.killed && !this.isFailed() && killer.func_110124_au().equals(this.targetID)) {
         LOTRPlayerData killerData = LOTRLevelData.getData(killer);
         LOTRFaction killerHighestFaction = this.getPledgeOrHighestAlignmentFaction(killer, 0.0F);
         if (killerHighestFaction != null) {
            float killerBonus = this.getAlignmentBonus();
            LOTRAlignmentValues.AlignmentBonus source = new LOTRAlignmentValues.AlignmentBonus(killerBonus, "lotr.alignment.killedHunter");
            killerData.addAlignment(killer, source, killerHighestFaction, entityplayer);
         }

         LOTRPlayerData pd = this.getPlayerData();
         float curAlignment = pd.getAlignment(this.entityFaction);
         if (curAlignment > 100.0F) {
            float alignmentLoss = this.getKilledAlignmentPenalty();
            if (curAlignment + alignmentLoss < 100.0F) {
               alignmentLoss = -(curAlignment - 100.0F);
            }

            LOTRAlignmentValues.AlignmentBonus source = new LOTRAlignmentValues.AlignmentBonus(alignmentLoss, "lotr.alignment.killedByBounty");
            pd.addAlignment(entityplayer, source, this.entityFaction, killer);
            IChatComponent slainMsg1 = new ChatComponentTranslation("chat.lotr.killedByBounty1", new Object[]{killer.func_70005_c_()});
            IChatComponent slainMsg2 = new ChatComponentTranslation("chat.lotr.killedByBounty2", new Object[]{this.entityFaction.factionName()});
            slainMsg1.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
            slainMsg2.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
            entityplayer.func_145747_a(slainMsg1);
            entityplayer.func_145747_a(slainMsg2);
         }

         this.killedByBounty = true;
         this.updateQuest();
         killerData.addAchievement(LOTRAchievement.killHuntingPlayer);
         IChatComponent announceMsg = new ChatComponentTranslation("chat.lotr.killedByBounty", new Object[]{entityplayer.func_70005_c_(), killer.func_70005_c_()});
         announceMsg.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
         Iterator var14 = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b.iterator();

         while(var14.hasNext()) {
            Object obj = var14.next();
            EntityPlayer otherPlayer = (EntityPlayer)obj;
            if (otherPlayer != entityplayer) {
               otherPlayer.func_145747_a(announceMsg);
            }
         }
      }

   }

   private LOTRFaction getPledgeOrHighestAlignmentFaction(EntityPlayer entityplayer, float min) {
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      if (pd.getPledgeFaction() != null) {
         return pd.getPledgeFaction();
      } else {
         List highestFactions = new ArrayList();
         float highestAlignment = min;
         Iterator var6 = LOTRFaction.getPlayableAlignmentFactions().iterator();

         LOTRFaction f;
         while(var6.hasNext()) {
            f = (LOTRFaction)var6.next();
            float alignment = pd.getAlignment(f);
            if (alignment > min) {
               if (alignment > highestAlignment) {
                  highestFactions.clear();
                  highestFactions.add(f);
                  highestAlignment = alignment;
               } else if (alignment == highestAlignment) {
                  highestFactions.add(f);
               }
            }
         }

         if (!highestFactions.isEmpty()) {
            Random rand = entityplayer.func_70681_au();
            f = (LOTRFaction)highestFactions.get(rand.nextInt(highestFactions.size()));
            return f;
         } else {
            return null;
         }
      }
   }

   public void onPlayerTick(EntityPlayer entityplayer) {
      super.onPlayerTick(entityplayer);
      if (this.isActive() && !this.killed && !this.bountyClaimedByOther && LOTRFactionBounties.forFaction(this.entityFaction).forPlayer(this.targetID).recentlyBountyKilled()) {
         this.bountyClaimedByOther = true;
         this.updateQuest();
      }

   }

   public boolean isFailed() {
      return super.isFailed() || this.bountyClaimedByOther || this.killedByBounty;
   }

   public String getQuestFailure() {
      if (this.killedByBounty) {
         return StatCollector.func_74837_a("lotr.miniquest.bounty.killedBy", new Object[]{this.targetName});
      } else {
         return this.bountyClaimedByOther ? StatCollector.func_74837_a("lotr.miniquest.bounty.claimed", new Object[]{this.targetName}) : super.getQuestFailure();
      }
   }

   public String getQuestFailureShorthand() {
      if (this.killedByBounty) {
         return StatCollector.func_74838_a("lotr.miniquest.bounty.killedBy.short");
      } else {
         return this.bountyClaimedByOther ? StatCollector.func_74838_a("lotr.miniquest.bounty.claimed.short") : super.getQuestFailureShorthand();
      }
   }

   public void start(EntityPlayer entityplayer, LOTREntityNPC npc) {
      super.start(entityplayer, npc);
      LOTRLevelData.getData(this.targetID).placeBountyFor(npc.getFaction());
   }

   protected void complete(EntityPlayer entityplayer, LOTREntityNPC npc) {
      LOTRPlayerData pd = this.getPlayerData();
      pd.addCompletedBountyQuest();
      int bComplete = pd.getCompletedBountyQuests();
      boolean specialReward = bComplete > 0 && bComplete % 5 == 0;
      if (specialReward) {
         ItemStack trophy = new ItemStack(LOTRMod.bountyTrophy);
         this.rewardItemTable.add(trophy);
      }

      super.complete(entityplayer, npc);
      pd.addAchievement(LOTRAchievement.doMiniquestHunter);
      if (specialReward) {
         pd.addAchievement(LOTRAchievement.doMiniquestHunter5);
      }

   }

   public float getAlignmentBonus() {
      return this.alignmentBonus;
   }

   public float getKilledAlignmentPenalty() {
      return -this.getAlignmentBonus() * 2.0F;
   }

   public int getCoinBonus() {
      return this.coinBonus;
   }

   protected boolean shouldRandomiseCoinReward() {
      return false;
   }

   public static enum BountyHelp {
      BIOME("biome"),
      WAYPOINT("wp");

      public final String speechName;

      private BountyHelp(String s) {
         this.speechName = s;
      }

      public static LOTRMiniQuestBounty.BountyHelp getRandomHelpType(Random random) {
         return values()[random.nextInt(values().length)];
      }
   }

   public static class QFBounty extends LOTRMiniQuest.QuestFactoryBase {
      public QFBounty(String name) {
         super(name);
      }

      public Class getQuestClass() {
         return LOTRMiniQuestBounty.class;
      }

      public LOTRMiniQuestBounty createQuest(LOTREntityNPC npc, Random rand) {
         if (!LOTRConfig.allowBountyQuests) {
            return null;
         } else {
            LOTRMiniQuestBounty quest = (LOTRMiniQuestBounty)super.createQuest(npc, rand);
            LOTRFaction faction = npc.getFaction();
            LOTRFactionBounties bounties = LOTRFactionBounties.forFaction(faction);
            List players = bounties.findBountyTargets(25);
            if (players.isEmpty()) {
               return null;
            } else {
               LOTRFactionBounties.PlayerData targetData = (LOTRFactionBounties.PlayerData)players.get(rand.nextInt(players.size()));
               int kills = targetData.getNumKills();
               float f = (float)kills;
               int alignment = (int)f;
               alignment = MathHelper.func_76125_a(alignment, 1, 50);
               int coins = (int)(f * 10.0F * MathHelper.func_151240_a(rand, 0.75F, 1.25F));
               coins = MathHelper.func_76125_a(coins, 1, 1000);
               quest.targetID = targetData.playerID;
               String username = targetData.findUsername();
               if (StringUtils.isBlank(username)) {
                  username = quest.targetID.toString();
               }

               quest.targetName = username;
               quest.alignmentBonus = (float)alignment;
               quest.coinBonus = coins;
               return quest;
            }
         }
      }
   }
}
