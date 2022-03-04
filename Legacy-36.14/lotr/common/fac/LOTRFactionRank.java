package lotr.common.fac;

import lotr.common.LOTRAchievementRank;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRTitle;
import net.minecraft.util.StatCollector;

public class LOTRFactionRank implements Comparable {
   public static final LOTRFactionRank RANK_NEUTRAL = new LOTRFactionRank.Dummy("lotr.faction.rank.neutral");
   public static final LOTRFactionRank RANK_ENEMY = new LOTRFactionRank.Dummy("lotr.faction.rank.enemy");
   public final LOTRFaction fac;
   public final float alignment;
   public final String name;
   private LOTRAchievementRank rankAchievement;
   private boolean isGendered;
   private LOTRTitle rankTitle;
   private LOTRTitle rankTitleMasc;
   private LOTRTitle rankTitleFem;

   public LOTRFactionRank(LOTRFaction f, float al, String s, boolean gend) {
      this.fac = f;
      this.alignment = al;
      this.name = s;
      this.isGendered = gend;
   }

   public String getCodeName() {
      return "lotr.faction." + this.fac.codeName() + ".rank." + this.name;
   }

   public String getCodeNameFem() {
      return this.getCodeName() + "_fm";
   }

   public String getCodeFullName() {
      return this.getCodeName() + ".f";
   }

   public String getCodeFullNameFem() {
      return this.getCodeNameFem() + ".f";
   }

   public String getCodeFullNameWithGender(LOTRPlayerData pd) {
      return this.isGendered() && pd.useFeminineRanks() ? this.getCodeFullNameFem() : this.getCodeFullName();
   }

   public String getDisplayName() {
      return StatCollector.func_74838_a(this.getCodeName());
   }

   public String getDisplayNameFem() {
      return StatCollector.func_74838_a(this.getCodeNameFem());
   }

   public String getDisplayFullName() {
      return StatCollector.func_74838_a(this.getCodeFullName());
   }

   public String getDisplayFullNameFem() {
      return StatCollector.func_74838_a(this.getCodeFullNameFem());
   }

   public String getShortNameWithGender(LOTRPlayerData pd) {
      return this.isGendered() && pd.useFeminineRanks() ? this.getDisplayNameFem() : this.getDisplayName();
   }

   public String getFullNameWithGender(LOTRPlayerData pd) {
      return this.isGendered() && pd.useFeminineRanks() ? this.getDisplayFullNameFem() : this.getDisplayFullName();
   }

   public boolean isGendered() {
      return this.isGendered;
   }

   public boolean isDummyRank() {
      return false;
   }

   public LOTRFactionRank makeTitle() {
      if (this.isGendered) {
         this.rankTitleMasc = new LOTRTitle(this, false);
         this.rankTitleFem = new LOTRTitle(this, true);
         return this;
      } else {
         this.rankTitle = new LOTRTitle(this, false);
         return this;
      }
   }

   public LOTRFactionRank makeAchievement() {
      this.rankAchievement = new LOTRAchievementRank(this);
      return this;
   }

   public LOTRAchievementRank getRankAchievement() {
      return this.rankAchievement;
   }

   public LOTRFactionRank setPledgeRank() {
      this.fac.setPledgeRank(this);
      return this;
   }

   public boolean isPledgeRank() {
      return this == this.fac.getPledgeRank();
   }

   public boolean isAbovePledgeRank() {
      return this.alignment > this.fac.getPledgeAlignment();
   }

   public int compareTo(LOTRFactionRank other) {
      if (this.fac != other.fac) {
         throw new IllegalArgumentException("Cannot compare two ranks from different factions!");
      } else {
         float al1 = this.alignment;
         float al2 = other.alignment;
         if (al1 == al2) {
            throw new IllegalArgumentException("Two ranks cannot have the same alignment value!");
         } else {
            return -Float.valueOf(al1).compareTo(al2);
         }
      }
   }

   public static final class Dummy extends LOTRFactionRank {
      public Dummy(String s) {
         super((LOTRFaction)null, 0.0F, s, false);
      }

      public String getCodeName() {
         return this.name;
      }

      public String getDisplayName() {
         return StatCollector.func_74838_a(this.getCodeName());
      }

      public String getDisplayFullName() {
         return this.getDisplayName();
      }

      public boolean isDummyRank() {
         return true;
      }
   }
}
