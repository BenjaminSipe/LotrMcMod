package lotr.client.sound;

import java.util.Random;
import lotr.client.LOTRClientProxy;
import lotr.common.LOTRConfig;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRMusicRegion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRMusicTicker {
   public static LOTRMusicTrack currentTrack;
   private static boolean wasPlayingMenu = true;
   private static final int firstTiming = 100;
   private static int timing = 100;
   private static final int nullTrackResetTiming = 400;

   public static void update(Random rand) {
      Minecraft mc = Minecraft.func_71410_x();
      boolean noMusic = mc.field_71474_y.func_151438_a(SoundCategory.MUSIC) <= 0.0F;
      boolean menu = LOTRMusic.isMenuMusic();
      if (wasPlayingMenu != menu) {
         if (currentTrack != null) {
            mc.func_147118_V().func_147683_b(currentTrack);
            currentTrack = null;
         }

         wasPlayingMenu = menu;
         timing = 100;
      }

      if (currentTrack != null) {
         if (noMusic) {
            mc.func_147118_V().func_147683_b(currentTrack);
         }

         if (!mc.func_147118_V().func_147692_c(currentTrack)) {
            currentTrack = null;
            resetTiming(rand);
         }
      }

      if (!noMusic) {
         boolean update = false;
         if (menu) {
            update = true;
         } else {
            update = LOTRMusic.isLOTRDimension() && !Minecraft.func_71410_x().func_147113_T();
         }

         if (update) {
            --timing;
            if (currentTrack == null && timing <= 0) {
               currentTrack = getNewTrack(mc, rand);
               if (currentTrack != null) {
                  wasPlayingMenu = menu;
                  mc.func_147118_V().func_147682_a(currentTrack);
                  timing = Integer.MAX_VALUE;
               } else {
                  timing = 400;
               }
            }
         }
      }

   }

   private static void resetTiming(Random rand) {
      if (LOTRMusic.isMenuMusic()) {
         timing = MathHelper.func_76136_a(rand, LOTRConfig.musicIntervalMenuMin * 20, LOTRConfig.musicIntervalMenuMax * 20);
      } else {
         timing = MathHelper.func_76136_a(rand, LOTRConfig.musicIntervalMin * 20, LOTRConfig.musicIntervalMax * 20);
      }

   }

   private static LOTRMusicTrack getNewTrack(Minecraft mc, Random rand) {
      LOTRMusicRegion.Sub regionSub = getCurrentRegion(mc, rand);
      LOTRMusicCategory category = getCurrentCategory(mc, rand);
      if (regionSub != null) {
         LOTRMusicRegion region = regionSub.region;
         String sub = regionSub.subregion;
         LOTRTrackSorter.Filter filter;
         if (category != null) {
            filter = LOTRTrackSorter.forRegionAndCategory(region, category);
         } else {
            filter = LOTRTrackSorter.forAny();
         }

         LOTRRegionTrackPool trackPool = LOTRMusic.getTracksForRegion(region, sub);
         return trackPool.getRandomTrack(rand, filter);
      } else {
         return null;
      }
   }

   private static LOTRMusicRegion.Sub getCurrentRegion(Minecraft mc, Random rand) {
      World world = mc.field_71441_e;
      EntityPlayer entityplayer = mc.field_71439_g;
      if (LOTRMusic.isMenuMusic()) {
         return LOTRMusicRegion.MENU.getWithoutSub();
      } else {
         if (LOTRMusic.isLOTRDimension()) {
            int i = MathHelper.func_76128_c(entityplayer.field_70165_t);
            int k = MathHelper.func_76128_c(entityplayer.field_70161_v);
            if (LOTRClientProxy.doesClientChunkExist(world, i, k)) {
               BiomeGenBase biome = world.func_72807_a(i, k);
               if (biome instanceof LOTRBiome) {
                  LOTRBiome lotrbiome = (LOTRBiome)biome;
                  LOTRMusicRegion.Sub region = lotrbiome.getBiomeMusic();
                  return region;
               }
            }
         }

         return null;
      }
   }

   private static LOTRMusicCategory getCurrentCategory(Minecraft mc, Random rand) {
      World world = mc.field_71441_e;
      EntityPlayer entityplayer = mc.field_71439_g;
      if (world != null && entityplayer != null) {
         int i = MathHelper.func_76128_c(entityplayer.field_70165_t);
         int j = MathHelper.func_76128_c(entityplayer.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(entityplayer.field_70161_v);
         if (LOTRMusicCategory.isCave(world, i, j, k)) {
            return LOTRMusicCategory.CAVE;
         } else {
            return LOTRMusicCategory.isDay(world) ? LOTRMusicCategory.DAY : LOTRMusicCategory.NIGHT;
         }
      } else {
         return null;
      }
   }
}
