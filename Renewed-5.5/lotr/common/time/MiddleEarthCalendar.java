package lotr.common.time;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.text.ITextComponent;

public abstract class MiddleEarthCalendar {
   public static final int SECOND_AGE_LENGTH = 3441;
   public static final int THIRD_AGE_LENGTH = 3021;
   public static final int SHIRE_RECKONING_OFFSET_FROM_THIRD_AGE = -1600;
   public static final int THIRD_AGE_CURRENT;
   public static int currentDay;
   private Map cachedDates = new HashMap();

   public final MiddleEarthCalendar.AbstractDate getCurrentDate() {
      return this.getDate(currentDay);
   }

   public final MiddleEarthCalendar.AbstractDate getDate(int day) {
      MiddleEarthCalendar.AbstractDate date = (MiddleEarthCalendar.AbstractDate)this.cachedDates.get(day);
      if (date == null) {
         date = this.computeDateForCache(day);
         this.cachedDates.put(day, date);
      }

      return date;
   }

   protected abstract MiddleEarthCalendar.AbstractDate computeDateForCache(int var1);

   public final ITextComponent getCurrentDateAndYearLongform() {
      return this.getCurrentDate().getDateAndYearName(false);
   }

   public final ITextComponent getCurrentDateAndYearShortform() {
      return this.getCurrentDate().getDateAndYearName(true);
   }

   static {
      THIRD_AGE_CURRENT = ShireReckoning.START_DATE.year - -1600;
      currentDay = 0;
   }

   public abstract static class AbstractDate {
      protected abstract ITextComponent getDateAndYearName(boolean var1);

      protected abstract ITextComponent getDateName(boolean var1);

      protected abstract ITextComponent getYearName(boolean var1);
   }
}
