package lotr.common;

import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class LOTRAchievementRank extends LOTRAchievement {
   private LOTRFactionRank theRank;
   private LOTRFaction theFac;

   public LOTRAchievementRank(LOTRFactionRank rank) {
      super(rank.fac.getAchieveCategory(), rank.fac.getAchieveCategory().getNextRankAchID(), LOTRMod.goldRing, "alignment_" + rank.fac.codeName() + "_" + rank.alignment);
      this.theRank = rank;
      this.theFac = this.theRank.fac;
      this.setRequiresAlly(new LOTRFaction[]{this.theFac});
      this.setSpecial();
   }

   public String getUntranslatedTitle(EntityPlayer entityplayer) {
      return this.theRank.getCodeFullNameWithGender(LOTRLevelData.getData(entityplayer));
   }

   public String getDescription(EntityPlayer entityplayer) {
      String suffix = this.requiresPledge() ? "achieveRankPledge" : "achieveRank";
      return StatCollector.func_74837_a("lotr.faction." + this.theFac.codeName() + "." + suffix, new Object[]{LOTRAlignmentValues.formatAlignForDisplay(this.theRank.alignment)});
   }

   private boolean requiresPledge() {
      return this.theRank.isAbovePledgeRank();
   }

   public boolean canPlayerEarn(EntityPlayer entityplayer) {
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      float align = pd.getAlignment(this.theFac);
      if (align < 0.0F) {
         return false;
      } else {
         return !this.requiresPledge() || pd.isPledgedTo(this.theFac);
      }
   }

   public boolean isPlayerRequiredRank(EntityPlayer entityplayer) {
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      float align = pd.getAlignment(this.theFac);
      float rankAlign = this.theRank.alignment;
      if (this.requiresPledge() && !pd.isPledgedTo(this.theFac)) {
         return false;
      } else {
         return align >= rankAlign;
      }
   }
}
