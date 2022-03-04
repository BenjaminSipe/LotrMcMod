package lotr.common;

import com.google.common.math.IntMath;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lotr.common.network.LOTRPacketDate;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.world.LOTRWorldInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRDate {
   private static int ticksInDay;
   private static long prevWorldTime;
   public static int SECOND_AGE_LENGTH;
   public static int THIRD_AGE_LENGTH;
   public static int SR_TO_TA;
   public static int THIRD_AGE_CURRENT;

   public static void saveDates(NBTTagCompound levelData) {
      NBTTagCompound nbt = new NBTTagCompound();
      nbt.func_74768_a("ShireDate", LOTRDate.ShireReckoning.currentDay);
      levelData.func_74782_a("Dates", nbt);
   }

   public static void loadDates(NBTTagCompound levelData) {
      if (levelData.func_74764_b("Dates")) {
         NBTTagCompound nbt = levelData.func_74775_l("Dates");
         LOTRDate.ShireReckoning.currentDay = nbt.func_74762_e("ShireDate");
      } else {
         LOTRDate.ShireReckoning.currentDay = 0;
      }

   }

   public static void resetWorldTimeInMenu() {
      prevWorldTime = -1L;
   }

   public static void update(World world) {
      if (world.func_72912_H() instanceof LOTRWorldInfo) {
         long worldTime = world.func_72820_D();
         if (prevWorldTime == -1L) {
            prevWorldTime = worldTime;
         }

         long prevDay = prevWorldTime / (long)ticksInDay;
         long day = worldTime / (long)ticksInDay;
         if (day != prevDay) {
            setDate(LOTRDate.ShireReckoning.currentDay + 1);
         }

         prevWorldTime = worldTime;
      }
   }

   public static void setDate(int date) {
      LOTRDate.ShireReckoning.currentDay = date;
      LOTRLevelData.markDirty();
      Iterator var1 = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b.iterator();

      while(var1.hasNext()) {
         Object obj = var1.next();
         EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
         sendUpdatePacket(entityplayer, true);
      }

   }

   public static void sendUpdatePacket(EntityPlayerMP entityplayer, boolean update) {
      NBTTagCompound nbt = new NBTTagCompound();
      saveDates(nbt);
      LOTRPacketDate packet = new LOTRPacketDate(nbt, update);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   static {
      ticksInDay = LOTRTime.DAY_LENGTH;
      prevWorldTime = -1L;
      SECOND_AGE_LENGTH = 3441;
      THIRD_AGE_LENGTH = 3021;
      SR_TO_TA = 1600;
      THIRD_AGE_CURRENT = LOTRDate.ShireReckoning.startDate.year + SR_TO_TA;
   }

   public static class ShireReckoning {
      public static LOTRDate.ShireReckoning.Date startDate;
      public static int currentDay;
      private static Map cachedDates;

      public static boolean isLeapYear(int year) {
         return year % 4 == 0 && year % 100 != 0;
      }

      public static LOTRDate.ShireReckoning.Date getShireDate() {
         return getShireDate(currentDay);
      }

      public static LOTRDate.ShireReckoning.Date getShireDate(int day) {
         LOTRDate.ShireReckoning.Date date = (LOTRDate.ShireReckoning.Date)cachedDates.get(day);
         if (date == null) {
            date = startDate.copy();
            int i;
            if (day < 0) {
               for(i = 0; i < -day; ++i) {
                  date = date.decrement();
               }
            } else {
               for(i = 0; i < day; ++i) {
                  date = date.increment();
               }
            }

            cachedDates.put(day, date);
         }

         return date;
      }

      public static LOTRDate.Season getSeason() {
         return getShireDate().month.season;
      }

      static {
         startDate = new LOTRDate.ShireReckoning.Date(1401, LOTRDate.ShireReckoning.Month.HALIMATH, 22);
         currentDay = 0;
         cachedDates = new HashMap();
      }

      public static class Date {
         public final int year;
         public final LOTRDate.ShireReckoning.Month month;
         public final int monthDate;
         private LOTRDate.ShireReckoning.Day day;

         public Date(int y, LOTRDate.ShireReckoning.Month m, int d) {
            this.year = y;
            this.month = m;
            this.monthDate = d;
         }

         public String getDateName(boolean longName) {
            String[] dayYear = this.getDayAndYearNames(longName);
            return dayYear[0] + ", " + dayYear[1];
         }

         public String[] getDayAndYearNames(boolean longName) {
            StringBuilder builder = new StringBuilder();
            if (this.month.hasWeekdayName) {
               builder.append(this.getDay().getDayName());
            }

            builder.append(" ");
            if (!this.month.isSingleDay()) {
               builder.append(this.monthDate);
               builder.append(" ");
            }

            builder.append(this.month.getMonthName());
            String dateName = builder.toString();
            builder = new StringBuilder();
            if (longName) {
               builder.append(StatCollector.func_74838_a("lotr.date.shire.long"));
            } else {
               builder.append(StatCollector.func_74838_a("lotr.date.shire"));
            }

            builder.append(" ");
            builder.append(this.year);
            String yearName = builder.toString();
            return new String[]{dateName, yearName};
         }

         public LOTRDate.ShireReckoning.Day getDay() {
            if (!this.month.hasWeekdayName) {
               return null;
            } else {
               if (this.day == null) {
                  int yearDay = 0;
                  int monthID = this.month.ordinal();

                  int dayID;
                  for(dayID = 0; dayID < monthID; ++dayID) {
                     LOTRDate.ShireReckoning.Month m = LOTRDate.ShireReckoning.Month.values()[dayID];
                     if (m.hasWeekdayName) {
                        yearDay += m.days;
                     }
                  }

                  yearDay += this.monthDate;
                  dayID = IntMath.mod(yearDay - 1, LOTRDate.ShireReckoning.Day.values().length);
                  this.day = LOTRDate.ShireReckoning.Day.values()[dayID];
               }

               return this.day;
            }
         }

         public LOTRDate.ShireReckoning.Date copy() {
            return new LOTRDate.ShireReckoning.Date(this.year, this.month, this.monthDate);
         }

         public LOTRDate.ShireReckoning.Date increment() {
            int newYear = this.year;
            LOTRDate.ShireReckoning.Month newMonth = this.month;
            int newDate = this.monthDate;
            ++newDate;
            if (newDate > newMonth.days) {
               newDate = 1;
               int monthID = newMonth.ordinal();
               ++monthID;
               if (monthID >= LOTRDate.ShireReckoning.Month.values().length) {
                  monthID = 0;
                  ++newYear;
               }

               newMonth = LOTRDate.ShireReckoning.Month.values()[monthID];
               if (newMonth.isLeapYear && !LOTRDate.ShireReckoning.isLeapYear(newYear)) {
                  ++monthID;
                  newMonth = LOTRDate.ShireReckoning.Month.values()[monthID];
               }
            }

            return new LOTRDate.ShireReckoning.Date(newYear, newMonth, newDate);
         }

         public LOTRDate.ShireReckoning.Date decrement() {
            int newYear = this.year;
            LOTRDate.ShireReckoning.Month newMonth = this.month;
            int newDate = this.monthDate;
            --newDate;
            if (newDate < 0) {
               int monthID = newMonth.ordinal();
               --monthID;
               if (monthID < 0) {
                  monthID = LOTRDate.ShireReckoning.Month.values().length - 1;
                  --newYear;
               }

               newMonth = LOTRDate.ShireReckoning.Month.values()[monthID];
               if (newMonth.isLeapYear && !LOTRDate.ShireReckoning.isLeapYear(newYear)) {
                  --monthID;
                  newMonth = LOTRDate.ShireReckoning.Month.values()[monthID];
               }

               newDate = newMonth.days;
            }

            return new LOTRDate.ShireReckoning.Date(newYear, newMonth, newDate);
         }
      }

      public static enum Month {
         YULE_2("yule2", 1, LOTRDate.Season.WINTER),
         AFTERYULE("afteryule", 30, LOTRDate.Season.WINTER),
         SOLMATH("solmath", 30, LOTRDate.Season.WINTER),
         RETHE("rethe", 30, LOTRDate.Season.WINTER),
         ASTRON("astron", 30, LOTRDate.Season.SPRING),
         THRIMIDGE("thrimidge", 30, LOTRDate.Season.SPRING),
         FORELITHE("forelithe", 30, LOTRDate.Season.SPRING),
         LITHE_1("lithe1", 1, LOTRDate.Season.SPRING),
         MIDYEARSDAY("midyearsday", 1, LOTRDate.Season.SUMMER, false, false),
         OVERLITHE("overlithe", 1, LOTRDate.Season.SUMMER, false, true),
         LITHE_2("lithe2", 1, LOTRDate.Season.SUMMER),
         AFTERLITHE("afterlithe", 30, LOTRDate.Season.SUMMER),
         WEDMATH("wedmath", 30, LOTRDate.Season.SUMMER),
         HALIMATH("halimath", 30, LOTRDate.Season.SUMMER),
         WINTERFILTH("winterfilth", 30, LOTRDate.Season.AUTUMN),
         BLOTMATH("blotmath", 30, LOTRDate.Season.AUTUMN),
         FOREYULE("foreyule", 30, LOTRDate.Season.AUTUMN),
         YULE_1("yule1", 1, LOTRDate.Season.AUTUMN);

         private String name;
         public int days;
         public boolean hasWeekdayName;
         public boolean isLeapYear;
         public LOTRDate.Season season;

         private Month(String s, int i, LOTRDate.Season se) {
            this(s, i, se, true, false);
         }

         private Month(String s, int i, LOTRDate.Season se, boolean flag, boolean flag1) {
            this.name = s;
            this.days = i;
            this.hasWeekdayName = flag;
            this.isLeapYear = flag1;
            this.season = se;
         }

         public String getMonthName() {
            return StatCollector.func_74838_a("lotr.date.shire.month." + this.name);
         }

         public boolean isSingleDay() {
            return this.days == 1;
         }
      }

      public static enum Day {
         STERDAY("sterday"),
         SUNDAY("sunday"),
         MONDAY("monday"),
         TREWSDAY("trewsday"),
         HEVENSDAY("hevensday"),
         MERSDAY("mersday"),
         HIGHDAY("highday");

         private String name;

         private Day(String s) {
            this.name = s;
         }

         public String getDayName() {
            return StatCollector.func_74838_a("lotr.date.shire.day." + this.name);
         }
      }
   }

   public static enum Season {
      SPRING("spring", 0, new float[]{1.0F, 1.0F, 1.0F}),
      SUMMER("summer", 1, new float[]{1.15F, 1.15F, 0.9F}),
      AUTUMN("autumn", 2, new float[]{1.2F, 1.0F, 0.7F}),
      WINTER("winter", 3, new float[]{1.0F, 0.8F, 0.8F});

      public static LOTRDate.Season[] allSeasons = new LOTRDate.Season[]{SPRING, SUMMER, AUTUMN, WINTER};
      private final String name;
      public final int seasonID;
      private final float[] grassRGB;

      private Season(String s, int i, float[] f) {
         this.name = s;
         this.seasonID = i;
         this.grassRGB = f;
      }

      public String codeName() {
         return this.name;
      }

      public int transformColor(int color) {
         float[] rgb = (new Color(color)).getRGBColorComponents((float[])null);
         float r = rgb[0];
         float g = rgb[1];
         float b = rgb[2];
         r = Math.min(r * this.grassRGB[0], 1.0F);
         g = Math.min(g * this.grassRGB[1], 1.0F);
         b = Math.min(b * this.grassRGB[2], 1.0F);
         return (new Color(r, g, b)).getRGB();
      }
   }
}
