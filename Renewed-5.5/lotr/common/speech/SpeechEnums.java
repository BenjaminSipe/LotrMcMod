package lotr.common.speech;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.fac.RankGender;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.biome.ExtendedWeatherType;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.LOTRBiomeWrapper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IDayTimeReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class SpeechEnums {
   public static enum Health implements Comparable {
      DANGER,
      LOW,
      MEDIUM,
      HIGH,
      FULL;

      public static SpeechEnums.Health getHealth(LivingEntity e) {
         float healthF = e.func_110143_aJ() / e.func_110138_aP();
         return getHealth(healthF);
      }

      public static SpeechEnums.Health getHealth(float frac) {
         frac = MathHelper.func_76131_a(frac, 0.0F, 1.0F);
         if (frac == 1.0F) {
            return FULL;
         } else if (frac > 0.75F) {
            return HIGH;
         } else if (frac > 0.5F) {
            return MEDIUM;
         } else {
            return frac > 0.25F ? LOW : DANGER;
         }
      }
   }

   public static enum Hired {
      OWN,
      OTHER,
      NONE;
   }

   public static enum InConversation {
      NONE,
      SAME,
      OTHER;

      public static SpeechEnums.InConversation getInConversationType(NPCEntity npc, PlayerEntity player) {
         LivingEntity currentTalkingTo = npc.getTalkingToEntity();
         if (currentTalkingTo == null) {
            return NONE;
         } else {
            return currentTalkingTo == player ? SAME : OTHER;
         }
      }
   }

   public static enum PreferredGender {
      M,
      F;

      public static SpeechEnums.PreferredGender fromRankGender(RankGender rankGender) {
         return rankGender == RankGender.FEMININE ? F : M;
      }

      public static SpeechEnums.PreferredGender fromNPCGender(NPCEntity npc) {
         return npc.getPersonalInfo().isMale() ? M : F;
      }
   }

   public static enum PledgeRelation {
      THIS,
      GOOD,
      NEUTRAL,
      BAD,
      NONE;
   }

   public static enum Relation {
      FRIENDLY,
      HOSTILE;
   }

   public static enum MoonPhase {
      FULL,
      WANING_GIBBOUS,
      THRID_QUARTER,
      WANING_CRESCENT,
      NEW,
      WAXING_CRESCENT,
      FIRST_QUARTER,
      WAXING_GIBBOUS;

      public static SpeechEnums.MoonPhase getPhase(IDayTimeReader world) {
         int phase = world.func_230315_m_().func_236035_c_(world.func_241851_ab());
         return values()[MathHelper.func_76125_a(phase, 0, values().length - 1)];
      }
   }

   public static enum Weather {
      CLEAR,
      RAIN,
      SNOW,
      ASH,
      SANDSTORM;

      public static SpeechEnums.Weather getWeather(World world, Biome biome, BlockPos pos) {
         if (world.func_72896_J()) {
            LOTRBiomeWrapper biomeWrapper = LOTRBiomes.getWrapperFor(biome, world);
            ExtendedWeatherType extendedWeather = biomeWrapper.getExtendedWeatherVisually();
            if (extendedWeather == ExtendedWeatherType.ASHFALL) {
               return ASH;
            } else if (extendedWeather == ExtendedWeatherType.SANDSTORM) {
               return SANDSTORM;
            } else {
               return LOTRBiomeBase.isSnowingVisually(biomeWrapper, world, pos) ? SNOW : RAIN;
            }
         } else {
            return CLEAR;
         }
      }

      public static boolean isThundering(World world) {
         return world.func_72911_I();
      }
   }

   public static enum Daytime {
      DAWN,
      MORNING,
      AFTERNOON,
      EVENING,
      DUSK,
      NIGHT;

      public static SpeechEnums.Daytime getDaytime(World world) {
         float phase = getDaytimePhase(world);
         if (phase > 0.225F && phase < 0.265F) {
            return DAWN;
         } else if (phase >= 0.265F && phase < 0.5F) {
            return MORNING;
         } else if (phase >= 0.5F && phase < 0.68F) {
            return AFTERNOON;
         } else if (phase >= 0.68F && phase < 0.735F) {
            return EVENING;
         } else {
            return phase >= 0.735F && phase < 0.775F ? DUSK : NIGHT;
         }
      }

      public static float getDaytimePhase(World world) {
         float sunCycle = world.func_242415_f(1.0F);
         return (sunCycle + 0.5F) % 1.0F;
      }

      public static float getHour(World world) {
         return getDaytimePhase(world) * 24.0F;
      }
   }

   public static enum DayOrNight {
      DAY,
      NIGHT;
   }
}
