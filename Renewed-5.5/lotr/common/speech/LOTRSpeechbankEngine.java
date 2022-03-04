package lotr.common.speech;

import lotr.common.speech.condition.BiomeSpeechbankCondition;
import lotr.common.speech.condition.NullableResourceLocationSpeechbankCondition;
import lotr.common.speech.condition.PersonalitySpeechbankCondition;
import lotr.common.speech.condition.RankSpeechbankCondition;
import lotr.curuquesta.SpeechbankEngine;
import lotr.curuquesta.condition.BooleanSpeechbankCondition;
import lotr.curuquesta.condition.EnumSpeechbankCondition;
import lotr.curuquesta.condition.FloatRangeSpeechbankCondition;
import lotr.curuquesta.condition.FloatSpeechbankCondition;
import lotr.curuquesta.replaceablevar.ReplaceableSpeechVariable;

public class LOTRSpeechbankEngine {
   public static final SpeechbankEngine INSTANCE = createEngine();
   public static final SpeechbankContextSerializer SERIALIZER;

   private static SpeechbankEngine createEngine() {
      return SpeechbankEngine.createInstance().registerCondition(EnumSpeechbankCondition.enumWithComparableExpressions("day_or_night", SpeechEnums.DayOrNight.values(), (context) -> {
         return context.getWorld().func_72935_r() ? SpeechEnums.DayOrNight.DAY : SpeechEnums.DayOrNight.NIGHT;
      })).registerCondition(new EnumSpeechbankCondition("daytime", SpeechEnums.Daytime.values(), (context) -> {
         return SpeechEnums.Daytime.getDaytime(context.getWorld());
      })).registerCondition(new FloatRangeSpeechbankCondition("daytime_phase", (context) -> {
         return SpeechEnums.Daytime.getDaytimePhase(context.getWorld());
      }, 0.0F, 1.0F)).registerCondition(new FloatRangeSpeechbankCondition("hour", (context) -> {
         return SpeechEnums.Daytime.getHour(context.getWorld());
      }, 0.0F, 24.0F)).registerCondition(new EnumSpeechbankCondition("weather", SpeechEnums.Weather.values(), (context) -> {
         return SpeechEnums.Weather.getWeather(context.getWorld(), context.getNPCBiome(), context.getNPCPosition());
      })).registerCondition(new BooleanSpeechbankCondition("thunder", (context) -> {
         return SpeechEnums.Weather.isThundering(context.getWorld());
      })).registerCondition(EnumSpeechbankCondition.enumWithComparableExpressions("moon_phase", SpeechEnums.MoonPhase.values(), (context) -> {
         return SpeechEnums.MoonPhase.getPhase(context.getWorld());
      })).registerCondition(new BooleanSpeechbankCondition("lunar_eclipse", (context) -> {
         return context.isLunarEclipse();
      })).registerCondition(new FloatSpeechbankCondition("align", (context) -> {
         return context.getPlayerAlignmentWithNPCFaction();
      })).registerCondition(new RankSpeechbankCondition("rank", (context) -> {
         return context.getPlayerRankWithNPCFaction();
      })).registerCondition(new EnumSpeechbankCondition("relation", SpeechEnums.Relation.values(), (context) -> {
         return context.getNPC().isFriendlyAndAligned(context.getPlayer()) ? SpeechEnums.Relation.FRIENDLY : SpeechEnums.Relation.HOSTILE;
      })).registerCondition(new NullableResourceLocationSpeechbankCondition("pledge", (context) -> {
         return context.getPledgeFactionName();
      })).registerCondition(new EnumSpeechbankCondition("pledge_relation", SpeechEnums.PledgeRelation.values(), (context) -> {
         return context.getPledgeFactionRelation();
      })).registerCondition(new EnumSpeechbankCondition("player_gender_pref", SpeechEnums.PreferredGender.values(), (context) -> {
         return SpeechEnums.PreferredGender.fromRankGender(context.getPreferredRankGender());
      })).registerCondition(new EnumSpeechbankCondition("gender", SpeechEnums.PreferredGender.values(), (context) -> {
         return SpeechEnums.PreferredGender.fromNPCGender(context.getNPC());
      })).registerCondition(new BooleanSpeechbankCondition("can_trade", (context) -> {
         return context.getNPC().canTrade(context.getPlayer());
      })).registerCondition(new BooleanSpeechbankCondition("in_combat", (context) -> {
         return context.getNPC().func_70638_az() != null;
      })).registerCondition(new EnumSpeechbankCondition("in_conversation", SpeechEnums.InConversation.values(), (context) -> {
         return SpeechEnums.InConversation.getInConversationType(context.getNPC(), context.getPlayer());
      })).registerCondition(new BooleanSpeechbankCondition("stargazing", (context) -> {
         return context.getNPC().isStargazing();
      })).registerCondition(new BooleanSpeechbankCondition("sun_admiring", (context) -> {
         return context.getNPC().isWatchingSunriseOrSunset();
      })).registerCondition(new BooleanSpeechbankCondition("fleeing", (context) -> {
         return context.getNPC().isFleeing();
      })).registerCondition(new BiomeSpeechbankCondition("biome", (context) -> {
         return context.getNPCBiomeWithTags();
      })).registerCondition(new BooleanSpeechbankCondition("underground", (context) -> {
         return context.isUnderground();
      })).registerCondition(new BooleanSpeechbankCondition("drunk", (context) -> {
         return context.isNPCDrunk();
      })).registerCondition(new BooleanSpeechbankCondition("player_drunk", (context) -> {
         return context.isPlayerDrunk();
      })).registerCondition(new EnumSpeechbankCondition("hired", SpeechEnums.Hired.values(), (context) -> {
         return SpeechEnums.Hired.NONE;
      })).registerCondition(new BooleanSpeechbankCondition("mounted", (context) -> {
         return context.getNPC().func_184187_bx() != null;
      })).registerCondition(new BooleanSpeechbankCondition("player_mounted", (context) -> {
         return context.getPlayer().func_184187_bx() != null;
      })).registerCondition(EnumSpeechbankCondition.enumWithComparableExpressions("health", SpeechEnums.Health.values(), (context) -> {
         return SpeechEnums.Health.getHealth(context.getNPC());
      })).registerCondition(EnumSpeechbankCondition.enumWithComparableExpressions("player_health", SpeechEnums.Health.values(), (context) -> {
         return SpeechEnums.Health.getHealth(context.getPlayer());
      })).registerCondition(EnumSpeechbankCondition.enumWithComparableExpressions("player_hunger", SpeechEnums.Health.values(), (context) -> {
         return SpeechEnums.Health.getHealth(context.getPlayerHungerLevel());
      })).registerCondition(new PersonalitySpeechbankCondition("personality", (context) -> {
         return context.getNPC().getPersonalInfo().getPersonalityTraits();
      })).registerReplaceableVariable(new ReplaceableSpeechVariable("p", "player", (context) -> {
         return context.getPlayer().func_200200_C_().getString();
      }));
   }

   static {
      SERIALIZER = new SpeechbankContextSerializer(INSTANCE);
   }
}
