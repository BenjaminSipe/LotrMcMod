package lotr.client.sound;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.client.LOTRTickHandlerClient;
import lotr.client.render.LOTRWeatherRenderer;
import lotr.common.LOTRConfig;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiomeGenBarrowDowns;
import lotr.common.world.biome.LOTRBiomeGenBeach;
import lotr.common.world.biome.LOTRBiomeGenDeadMarshes;
import lotr.common.world.biome.LOTRBiomeGenDolGuldur;
import lotr.common.world.biome.LOTRBiomeGenFarHaradCoast;
import lotr.common.world.biome.LOTRBiomeGenLindonCoast;
import lotr.common.world.biome.LOTRBiomeGenMirkwoodCorrupted;
import lotr.common.world.biome.LOTRBiomeGenOcean;
import lotr.common.world.biome.LOTRBiomeGenOldForest;
import lotr.common.world.biome.LOTRBiomeGenUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.ISound.AttenuationType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;

public class LOTRAmbience {
   private int tallGrassCount;
   private int leafCount;
   private int ticksSinceWight;
   private List playingWindSounds = new ArrayList();
   private static final int maxWindSounds = 4;
   private List playingSeaSounds = new ArrayList();
   private static final int maxSeaSounds = 3;
   private ISound playingJazzMusic;
   private int jazzPlayerID;
   private static final ResourceLocation jazzMusicPath = new ResourceLocation("lotr:music.jazzelf");
   private static final String jazzMusicTitle = "The Galadhon Groovers - Funky Villagers";

   public LOTRAmbience() {
      FMLCommonHandler.instance().bus().register(this);
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onPlaySound(PlaySoundEvent17 event) {
      String name = event.name;
      ISound sound = event.sound;
      if (LOTRConfig.newWeather && sound instanceof PositionedSound) {
         PositionedSound ps = (PositionedSound)sound;
         World world = Minecraft.func_71410_x().field_71441_e;
         if (world != null && world.field_73011_w instanceof LOTRWorldProvider) {
            if (name.equals("ambient.weather.rain")) {
               event.result = new PositionedSoundRecord(new ResourceLocation("lotr:ambient.weather.rain"), ps.func_147653_e(), ps.func_147655_f(), ps.func_147649_g(), ps.func_147654_h(), ps.func_147651_i());
            } else if (name.equals("ambient.weather.thunder")) {
               event.result = new PositionedSoundRecord(new ResourceLocation("lotr:ambient.weather.thunder"), ps.func_147653_e(), ps.func_147655_f(), ps.func_147649_g(), ps.func_147654_h(), ps.func_147651_i());
            }
         }
      }

      if (this.playingJazzMusic != null && event.category == SoundCategory.MUSIC) {
         event.result = null;
      }
   }

   public void updateAmbience(World world, EntityPlayer entityplayer) {
      world.field_72984_F.func_76320_a("lotrAmbience");
      Minecraft mc = Minecraft.func_71410_x();
      boolean enableAmbience = LOTRConfig.enableAmbience;
      boolean enableWeather = LOTRConfig.newWeather;
      double x = entityplayer.field_70165_t;
      double y = entityplayer.field_70121_D.field_72338_b;
      double z = entityplayer.field_70161_v;
      int i = MathHelper.func_76128_c(x);
      int j = MathHelper.func_76128_c(y);
      int k = MathHelper.func_76128_c(z);
      BiomeGenBase biome = world.func_72807_a(i, k);
      Random rand = world.field_73012_v;
      if (enableAmbience) {
         boolean spookyBiomeNoise;
         if (this.ticksSinceWight > 0) {
            --this.ticksSinceWight;
         } else {
            spookyBiomeNoise = LOTRTickHandlerClient.anyWightsViewed && rand.nextInt(20) == 0 || biome instanceof LOTRBiomeGenBarrowDowns && rand.nextInt(3000) == 0;
            if (spookyBiomeNoise) {
               world.func_72980_b(x, y, z, "lotr:wight.ambience", 1.0F, 0.8F + rand.nextFloat() * 0.4F, false);
               this.ticksSinceWight = 300;
            }
         }

         spookyBiomeNoise = false;
         float spookyPitch = 1.0F;
         if (biome instanceof LOTRBiomeGenDolGuldur) {
            spookyBiomeNoise = rand.nextInt(1000) == 0;
            spookyPitch = 0.85F;
         } else if (biome instanceof LOTRBiomeGenDeadMarshes) {
            spookyBiomeNoise = rand.nextInt(2400) == 0;
         } else if (biome instanceof LOTRBiomeGenMirkwoodCorrupted) {
            spookyBiomeNoise = rand.nextInt(3000) == 0;
         } else if (biome instanceof LOTRBiomeGenOldForest) {
            spookyBiomeNoise = rand.nextInt(6000) == 0;
         } else if (biome instanceof LOTRBiomeGenUtumno) {
            spookyBiomeNoise = rand.nextInt(1000) == 0;
            spookyPitch = 0.75F;
         }

         if (spookyBiomeNoise) {
            world.func_72980_b(x, y, z, "lotr:wight.ambience", 1.0F, (0.8F + rand.nextFloat() * 0.4F) * spookyPitch, false);
         }

         if (biome instanceof LOTRBiomeGenUtumno && world.field_73012_v.nextInt(500) == 0) {
            world.func_72980_b(x, y, z, "ambient.cave.cave", 1.0F, 0.8F + rand.nextFloat() * 0.2F, false);
         }
      }

      int l;
      int i1;
      int range;
      float x1;
      byte xzRange;
      HashSet removes;
      ISound sea;
      Iterator var71;
      if (enableWeather && world.field_73011_w instanceof LOTRWorldProvider) {
         if (this.playingWindSounds.size() < 4) {
            xzRange = 16;
            int minWindHeight = 100;
            int fullWindHeight = 180;
            if (rand.nextInt(20) == 0) {
               minWindHeight -= 10;
               if (rand.nextInt(10) == 0) {
                  minWindHeight -= 10;
               }
            }

            if (world.func_72896_J()) {
               minWindHeight = 80;
               fullWindHeight = 120;
               if (rand.nextInt(20) == 0) {
                  minWindHeight -= 20;
               }

               if (LOTRWeatherRenderer.isSandstormBiome(biome)) {
                  minWindHeight = 60;
                  fullWindHeight = 80;
               }
            }

            for(l = 0; l < 2; ++l) {
               i1 = i + MathHelper.func_76136_a(rand, -xzRange, xzRange);
               int k1 = k + MathHelper.func_76136_a(rand, -xzRange, xzRange);
               range = j + MathHelper.func_76136_a(rand, -16, 16);
               if (range >= minWindHeight && world.func_72937_j(i1, range, k1)) {
                  float windiness = (float)(range - minWindHeight) / (float)(fullWindHeight - minWindHeight);
                  windiness = MathHelper.func_76131_a(windiness, 0.0F, 1.0F);
                  if (windiness >= rand.nextFloat()) {
                     float x1 = (float)i1 + 0.5F;
                     float y1 = (float)range + 0.5F;
                     float z1 = (float)k1 + 0.5F;
                     float vol = 1.0F * Math.max(0.25F, windiness);
                     x1 = 0.8F + rand.nextFloat() * 0.4F;
                     ISound wind = (new LOTRAmbience.AmbientSoundNoAttentuation(new ResourceLocation("lotr:ambient.weather.wind"), vol, x1, x1, y1, z1)).calcAmbientVolume(entityplayer, xzRange);
                     mc.func_147118_V().func_147682_a(wind);
                     this.playingWindSounds.add(wind);
                     break;
                  }
               }
            }
         } else {
            removes = new HashSet();
            var71 = this.playingWindSounds.iterator();

            while(var71.hasNext()) {
               sea = (ISound)var71.next();
               if (!mc.func_147118_V().func_147692_c(sea)) {
                  removes.add(sea);
               }
            }

            this.playingWindSounds.removeAll(removes);
         }
      }

      if (enableAmbience) {
         if (this.playingSeaSounds.size() < 3) {
            if (biome instanceof LOTRBiomeGenOcean || biome instanceof LOTRBiomeGenBeach || biome instanceof LOTRBiomeGenLindonCoast || biome instanceof LOTRBiomeGenFarHaradCoast) {
               xzRange = 64;
               float[] rangeChecks = new float[]{0.25F, 0.5F, 0.75F, 1.0F};
               float[] var66 = rangeChecks;
               l = rangeChecks.length;

               label193:
               for(i1 = 0; i1 < l; ++i1) {
                  float fr = var66[i1];
                  range = (int)((float)xzRange * fr);

                  for(int l = 0; l < 8; ++l) {
                     int i1 = i + MathHelper.func_76136_a(rand, -range, range);
                     int k1 = k + MathHelper.func_76136_a(rand, -range, range);
                     int j1 = j + MathHelper.func_76136_a(rand, -16, 8);
                     Block block = world.func_147439_a(i1, j1, k1);
                     if (block.func_149688_o() == Material.field_151586_h && j1 >= world.func_72825_h(i1, k1)) {
                        x1 = (float)i1 + 0.5F;
                        float y1 = (float)j1 + 0.5F;
                        float z1 = (float)k1 + 0.5F;
                        float vol = 1.0F;
                        float pitch = 0.8F + rand.nextFloat() * 0.4F;
                        ISound sea = (new LOTRAmbience.AmbientSoundNoAttentuation(new ResourceLocation("lotr:ambient.terrain.sea"), vol, pitch, x1, y1, z1)).calcAmbientVolume(entityplayer, xzRange);
                        mc.func_147118_V().func_147682_a(sea);
                        this.playingSeaSounds.add(sea);
                        int j2 = world.func_72976_f(i1, k1) - 1;
                        if (world.func_147439_a(i1, j2, k1).func_149688_o() == Material.field_151586_h) {
                           double dx = (double)i1 + 0.5D - entityplayer.field_70165_t;
                           double dz = (double)k1 + 0.5D - entityplayer.field_70161_v;
                           float angle = (float)Math.atan2(dz, dx);
                           float cos = MathHelper.func_76134_b(angle);
                           float sin = MathHelper.func_76126_a(angle);
                           float angle90 = angle + (float)Math.toRadians(-90.0D);
                           float cos90 = MathHelper.func_76134_b(angle90);
                           float sin90 = MathHelper.func_76126_a(angle90);
                           float waveSpeed = MathHelper.func_151240_a(rand, 0.3F, 0.5F);
                           int waveR = 40 + rand.nextInt(100);

                           for(int w = -waveR; w <= waveR; ++w) {
                              float f = (float)w / 8.0F;
                              double d0 = (double)i1 + 0.5D;
                              double d1 = (double)j2 + 1.0D + (double)MathHelper.func_151240_a(rand, 0.02F, 0.1F);
                              double d2 = (double)k1 + 0.5D;
                              d0 += (double)(f * cos90);
                              d2 += (double)(f * sin90);
                              if (world.func_147439_a(MathHelper.func_76128_c(d0), MathHelper.func_76128_c(d1) - 1, MathHelper.func_76128_c(d2)).func_149688_o() == Material.field_151586_h) {
                                 double d3 = (double)(waveSpeed * -cos);
                                 double d4 = 0.0D;
                                 double d5 = (double)(waveSpeed * -sin);
                                 LOTRMod.proxy.spawnParticle("wave", d0, d1, d2, d3, d4, d5);
                              }
                           }
                        }
                        break label193;
                     }
                  }
               }
            }
         } else {
            removes = new HashSet();
            var71 = this.playingSeaSounds.iterator();

            while(var71.hasNext()) {
               sea = (ISound)var71.next();
               if (!mc.func_147118_V().func_147692_c(sea)) {
                  removes.add(sea);
               }
            }

            this.playingSeaSounds.removeAll(removes);
         }
      }

      if (this.playingJazzMusic == null) {
         if (entityplayer.field_70173_aa % 20 == 0) {
            double range = 16.0D;
            List elves = world.func_72872_a(LOTREntityElf.class, entityplayer.field_70121_D.func_72314_b(range, range, range));
            LOTREntityElf playingElf = null;
            Iterator var73 = elves.iterator();

            while(var73.hasNext()) {
               Object obj = var73.next();
               LOTREntityElf elf = (LOTREntityElf)obj;
               if (elf.func_70089_S() && elf.isJazz() && elf.isSolo()) {
                  playingElf = elf;
                  break;
               }
            }

            if (playingElf != null) {
               mc.func_147118_V().func_147690_c();
               this.jazzPlayerID = playingElf.func_145782_y();
               ISound music = this.getJazzMusic(playingElf);
               mc.func_147118_V().func_147682_a(music);
               this.playingJazzMusic = music;
               mc.field_71456_v.func_73833_a("The Galadhon Groovers - Funky Villagers");
            }
         }
      } else {
         Entity player = world.func_73045_a(this.jazzPlayerID);
         if (player == null || !player.func_70089_S()) {
            mc.func_147118_V().func_147683_b(this.playingJazzMusic);
            this.playingJazzMusic = null;
         }

         if (!mc.func_147118_V().func_147692_c(this.playingJazzMusic)) {
            this.playingJazzMusic = null;
         }
      }

      world.field_72984_F.func_76319_b();
   }

   private ISound getJazzMusic(Entity entity) {
      return PositionedSoundRecord.func_147675_a(jazzMusicPath, (float)entity.field_70165_t, (float)entity.field_70163_u, (float)entity.field_70161_v);
   }

   private static class AmbientSoundNoAttentuation extends PositionedSoundRecord {
      public AmbientSoundNoAttentuation(ResourceLocation sound, float vol, float pitch, float x, float y, float z) {
         super(sound, vol, pitch, x, y, z);
         this.field_147666_i = AttenuationType.NONE;
      }

      public LOTRAmbience.AmbientSoundNoAttentuation calcAmbientVolume(EntityPlayer entityplayer, int maxRange) {
         float distFr = (float)entityplayer.func_70011_f((double)this.field_147660_d, (double)this.field_147661_e, (double)this.field_147658_f);
         distFr /= (float)maxRange;
         distFr = Math.min(distFr, 1.0F);
         distFr = 1.0F - distFr;
         distFr *= 1.5F;
         distFr = MathHelper.func_76131_a(distFr, 0.1F, 1.0F);
         this.field_147662_b *= distFr;
         return this;
      }
   }
}
