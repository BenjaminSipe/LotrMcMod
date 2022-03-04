package lotr.client.gui.map;

import java.text.DecimalFormat;
import lotr.client.util.LocalizableDecimalFormat;
import lotr.common.config.LOTRConfig;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class WaypointDistanceDisplay {
   private static final LocalizableDecimalFormat DECIMAL_FORMAT = new LocalizableDecimalFormat(new DecimalFormat(",##0.#"), "gui.lotr.map.distance.decimal_separator_char", "gui.lotr.map.distance.group_separator_char");
   private static final double METRES_PER_YARD = 0.9144D;
   private static final double YARDS_PER_MILE = 1760.0D;
   private static final double MILES_PER_LEAGUE = 3.0D;
   private static final double LEAGUE_DISPLAY_THRESHOLD = 10.0D;

   public static ITextComponent getDistanceText(double dist) {
      return (Boolean)LOTRConfig.CLIENT.imperialWaypointDistances.get() ? getImperialDistance(dist) : getMetricDistance(dist);
   }

   private static ITextComponent getMetricDistance(double dist) {
      int m = (int)Math.round(dist);
      if (m > 1000) {
         double km = (double)m / 1000.0D;
         return new TranslationTextComponent("gui.lotr.map.distance.km", new Object[]{DECIMAL_FORMAT.format(km)});
      } else {
         return new TranslationTextComponent("gui.lotr.map.distance.m", new Object[]{DECIMAL_FORMAT.format((long)m)});
      }
   }

   private static ITextComponent getImperialDistance(double dist) {
      int yards = (int)Math.round(dist / 0.9144D);
      if ((double)yards > 1760.0D) {
         double miles = (double)yards / 1760.0D;
         double leagues = miles / 3.0D;
         return leagues > 10.0D ? new TranslationTextComponent("gui.lotr.map.distance.leagues", new Object[]{DECIMAL_FORMAT.format(leagues)}) : new TranslationTextComponent("gui.lotr.map.distance.miles", new Object[]{DECIMAL_FORMAT.format(miles)});
      } else {
         return new TranslationTextComponent("gui.lotr.map.distance.yards", new Object[]{DECIMAL_FORMAT.format((long)yards)});
      }
   }
}
