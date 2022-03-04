package lotr.common.speech.condition;

import java.util.OptionalInt;
import lotr.common.LOTRLog;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionRank;
import lotr.curuquesta.condition.predicate.AsymmetricComparator;

public class OptionallyUnderspecifiedFactionRank {
   private final Faction faction;
   private final String rankName;

   private OptionallyUnderspecifiedFactionRank(Faction faction, String rankName) {
      this.faction = faction;
      this.rankName = rankName;
   }

   public static OptionallyUnderspecifiedFactionRank fullySpecified(FactionRank rank) {
      return new OptionallyUnderspecifiedFactionRank(rank.getFaction(), rank.getBaseName());
   }

   public static OptionallyUnderspecifiedFactionRank fullySpecified(Faction faction, String rankName) {
      if (faction == null) {
         throw new IllegalArgumentException("OptionallyUnderspecifiedFactionRank error - constructing a fully specified one, but faction is null!");
      } else if (rankName == null) {
         throw new IllegalArgumentException("OptionallyUnderspecifiedFactionRank error - constructing a fully specified one, but rankName is null!");
      } else {
         return new OptionallyUnderspecifiedFactionRank(faction, rankName);
      }
   }

   public static OptionallyUnderspecifiedFactionRank underspecified(String rankName) {
      if (rankName == null) {
         throw new IllegalArgumentException("OptionallyUnderspecifiedFactionRank error - constructing an underspecified one, but the rankName at least must be specified!");
      } else {
         return new OptionallyUnderspecifiedFactionRank((Faction)null, rankName);
      }
   }

   public Faction getFaction() {
      return this.faction;
   }

   public String getRankName() {
      return this.rankName;
   }

   public boolean isUnderspecified() {
      return this.faction == null;
   }

   public FactionRank resolveRank() {
      if (this.isUnderspecified()) {
         throw new IllegalStateException("Cannot resolve the rank when it's underspecified! Development error.");
      } else {
         FactionRank rank = this.faction.getRankByName(this.rankName);
         if (rank == null) {
            throw new IllegalArgumentException("Could not resolve rank name " + this.rankName + " in faction " + this.faction.getName() + " - no such rank!");
         } else {
            return rank;
         }
      }
   }

   public static AsymmetricComparator asymmetricComparator() {
      return (rankInContext, rankInPredicate) -> {
         if (rankInContext.isUnderspecified()) {
            throw new IllegalArgumentException("The rank-in-context must be fully specified for this to work! Development error.");
         } else {
            Faction factionInContext = rankInContext.getFaction();
            String rankInPredicateName = rankInPredicate.rankName;
            if (factionInContext.getRankByName(rankInPredicateName) == null) {
               LOTRLog.debug("Speechbank entry refers to a rank '%s', but there is no such rank in the faction-in-context (%s). SpeechbankOverride is probably responsible here. This speechbank entry will be skipped.", rankInPredicateName, factionInContext.getName());
               return OptionalInt.empty();
            } else {
               OptionallyUnderspecifiedFactionRank fullySpecifiedRankInPredicate = fullySpecified(factionInContext, rankInPredicateName);
               int rankComparison = rankInContext.resolveRank().compareTo(fullySpecifiedRankInPredicate.resolveRank());
               return OptionalInt.of(rankComparison);
            }
         }
      };
   }

   public String toString() {
      return this.isUnderspecified() ? String.format("%s (faction unspecified)", this.rankName) : this.resolveRank().toString();
   }
}
