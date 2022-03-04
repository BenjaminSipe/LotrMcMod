package lotr.common.speech.condition;

import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import lotr.common.LOTRLog;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionRank;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import lotr.curuquesta.condition.SpeechbankCondition;
import lotr.curuquesta.condition.predicate.ComplexPredicateParsers;

public class RankSpeechbankCondition extends SpeechbankCondition {
   public RankSpeechbankCondition(String conditionName, Function valueFromContext) {
      super(conditionName, valueFromContext, ComplexPredicateParsers.logicalExpressionOfComparableSubpredicates(RankSpeechbankCondition::parseRank, OptionallyUnderspecifiedFactionRank.asymmetricComparator()));
   }

   private static OptionallyUnderspecifiedFactionRank parseRank(String s) {
      return OptionallyUnderspecifiedFactionRank.underspecified(s);
   }

   private static FactionSettings currentFactions() {
      return FactionSettingsManager.clientInstance().getCurrentLoadedFactions();
   }

   public boolean isValidValue(OptionallyUnderspecifiedFactionRank rank) {
      return rank != null && rank.getRankName() != null;
   }

   protected void writeValue(OptionallyUnderspecifiedFactionRank rank, ByteBuf buf) {
      buf.writeInt(rank.getFaction().getAssignedId());
      buf.writeInt(rank.resolveRank().getAssignedId());
   }

   protected OptionallyUnderspecifiedFactionRank readValue(ByteBuf buf) {
      int facId = buf.readInt();
      int rankId = buf.readInt();
      Faction faction = currentFactions().getFactionByID(facId);
      if (faction == null) {
         LOTRLog.warn("Received faction with ID %d as part of a speechbank context, but no such faction exists!", facId);
         return null;
      } else {
         FactionRank rank = faction.getRankByID(rankId);
         if (rank == null) {
            LOTRLog.warn("Received rank with ID %d in faction %s as part of a speechbank context, but no such rank exists!", rankId, faction.getName());
            return null;
         } else {
            return OptionallyUnderspecifiedFactionRank.fullySpecified(rank);
         }
      }
   }
}
