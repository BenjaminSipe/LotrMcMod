package lotr.common.time;

import lotr.common.LOTRLog;
import lotr.common.data.LOTRLevelData;
import lotr.common.dim.LOTRDimensionType;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketDate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class LOTRDate {
   private static final long TICKS_IN_DAY = 48000L;
   private static long prevWorldTime = -1L;

   public static void saveDates(CompoundNBT levelData) {
      CompoundNBT dateData = new CompoundNBT();
      dateData.func_74768_a("CurrentDay", MiddleEarthCalendar.currentDay);
      levelData.func_218657_a("Dates", dateData);
   }

   public static void loadDates(CompoundNBT levelData) {
      if (levelData.func_150297_b("Dates", 10)) {
         CompoundNBT dateData = levelData.func_74775_l("Dates");
         int currentDay = 0;
         if (dateData.func_74764_b("CurrentDay")) {
            currentDay = dateData.func_74762_e("CurrentDay");
         } else if (dateData.func_74764_b("ShireDate")) {
            currentDay = dateData.func_74762_e("ShireDate");
         }

         MiddleEarthCalendar.currentDay = currentDay;
      } else {
         MiddleEarthCalendar.currentDay = 0;
      }

   }

   public static void resetWorldTimeInMenu() {
      prevWorldTime = -1L;
   }

   public static void updateDate(ServerWorld world) {
      if (world.func_230315_m_() instanceof LOTRDimensionType) {
         long worldTime = world.func_72820_D();
         if (prevWorldTime == -1L) {
            prevWorldTime = worldTime;
         }

         long prevDay = prevWorldTime / 48000L;
         long day = worldTime / 48000L;
         if (day != prevDay) {
            setDate(world, MiddleEarthCalendar.currentDay + 1);
         }

         prevWorldTime = worldTime;
      }
   }

   public static void setDate(ServerWorld world, int date) {
      MiddleEarthCalendar.currentDay = date;
      LOTRLevelData.sidedInstance((IWorldReader)world).markDirty();
      LOTRLog.info("Updating LOTR day: " + ((ShireReckoning.ShireDate)ShireReckoning.INSTANCE.getCurrentDate()).getDateName(false).getString());
      LOTRPacketHandler.sendToAll(createDatePacket(true));
   }

   public static void sendLoginPacket(ServerPlayerEntity player) {
      LOTRPacketHandler.sendTo(createDatePacket(false), player);
   }

   public static void sendDisplayPacket(ServerPlayerEntity player) {
      LOTRPacketHandler.sendTo(createDatePacket(true), player);
   }

   private static SPacketDate createDatePacket(boolean displayNewDate) {
      CompoundNBT dateData = new CompoundNBT();
      saveDates(dateData);
      return new SPacketDate(dateData, displayNewDate);
   }
}
