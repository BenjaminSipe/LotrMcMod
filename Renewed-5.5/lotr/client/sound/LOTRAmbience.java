package lotr.client.sound;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lotr.common.LOTRLog;
import lotr.common.config.LOTRConfig;
import lotr.common.dim.LOTRDimensionType;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRSoundEvents;
import lotr.common.world.biome.ExtendedWeatherType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.LocatableSound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class LOTRAmbience {
   private List playingWindSounds = new ArrayList();
   private static final int MAX_WIND_SOUNDS = 4;

   public LOTRAmbience() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onPlaySound(PlaySoundEvent event) {
      ISound sound = event.getSound();
      if (sound instanceof SimpleSound) {
         SimpleSound simpleSound = (SimpleSound)sound;
         ResourceLocation name = simpleSound.func_147650_b();
         Minecraft mc = Minecraft.func_71410_x();
         World world = mc.field_71441_e;
         if (world != null && world.func_230315_m_() instanceof LOTRDimensionType && name.equals(SoundEvents.field_187754_de.func_187503_a())) {
            event.setResultSound(this.copySoundPropertiesToNew(simpleSound, LOTRSoundEvents.NEW_THUNDER));
         }
      }

   }

   private SimpleSound copySoundPropertiesToNew(SimpleSound srcSound, SoundEvent newSoundLocation) {
      float actualVolume = 1.0F;
      float actualPitch = 1.0F;

      try {
         actualVolume = ObfuscationReflectionHelper.findField(LocatableSound.class, "field_147662_b").getFloat(srcSound);
         actualPitch = ObfuscationReflectionHelper.findField(LocatableSound.class, "field_147663_c").getFloat(srcSound);
      } catch (IllegalAccessException | IllegalArgumentException var6) {
         LOTRLog.error("Failed to retrieve sound properties for replacement sound");
         var6.printStackTrace();
      }

      return new SimpleSound(newSoundLocation.func_187503_a(), srcSound.func_184365_d(), actualVolume, actualPitch, srcSound.func_147657_c(), srcSound.func_147652_d(), srcSound.func_147656_j(), srcSound.func_147649_g(), srcSound.func_147654_h(), srcSound.func_147651_i(), srcSound.func_217861_m());
   }

   public void updateAmbience(Minecraft mc, World world, PlayerEntity player) {
      IProfiler profiler = world.func_217381_Z();
      profiler.func_76320_a("lotrAmbience");
      SoundHandler soundHandler = mc.func_147118_V();
      Random rand = world.field_73012_v;
      double xD = player.func_226277_ct_();
      double yD = player.func_226278_cu_();
      double zD = player.func_226281_cx_();
      BlockPos pos = player.func_233580_cy_();
      int x = pos.func_177958_n();
      int y = pos.func_177956_o();
      int z = pos.func_177952_p();
      boolean isLOTRDimension = world.func_230315_m_() instanceof LOTRDimensionType;
      if (isLOTRDimension && (Boolean)LOTRConfig.CLIENT.windAmbience.get()) {
         profiler.func_76320_a("wind");
         this.doWindAmbience(soundHandler, world, rand, player, pos);
         profiler.func_76319_b();
      }

      profiler.func_76319_b();
   }

   private void doWindAmbience(SoundHandler soundHandler, World world, Random rand, PlayerEntity player, BlockPos pos) {
      if (this.playingWindSounds.size() < 4) {
         int xzRange = 16;
         int yRange = 16;
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

            Biome biome = world.func_226691_t_(pos);
            if (LOTRBiomes.getWrapperFor(biome, world).getExtendedWeatherVisually() == ExtendedWeatherType.SANDSTORM) {
               minWindHeight = 60;
               fullWindHeight = 80;
            }
         }

         Mutable movingPos = new Mutable();

         for(int l = 0; l < 2; ++l) {
            int dx = MathHelper.func_76136_a(rand, -xzRange, xzRange);
            int dz = MathHelper.func_76136_a(rand, -xzRange, xzRange);
            int dy = MathHelper.func_76136_a(rand, -yRange, yRange);
            movingPos.func_189533_g(pos).func_196234_d(dx, dy, dz);
            int windY = movingPos.func_177956_o();
            if (windY >= minWindHeight && world.func_175710_j(movingPos)) {
               float windHeightLerp = (float)(windY - minWindHeight) / (float)(fullWindHeight - minWindHeight);
               windHeightLerp = MathHelper.func_76131_a(windHeightLerp, 0.0F, 1.0F);
               if (windHeightLerp >= rand.nextFloat()) {
                  float vol = 1.0F * Math.max(0.25F, windHeightLerp);
                  float pitch = 0.8F + rand.nextFloat() * 0.4F;
                  ISound wind = (new AmbientSoundNoAttenuation(LOTRSoundEvents.AMBIENCE_WIND, SoundCategory.AMBIENT, vol, pitch, movingPos)).modifyAmbientVolume(player, xzRange);
                  soundHandler.func_147682_a(wind);
                  this.playingWindSounds.add(wind);
                  break;
               }
            }
         }
      } else {
         Set removes = new HashSet();
         Iterator var21 = this.playingWindSounds.iterator();

         while(var21.hasNext()) {
            ISound wind = (ISound)var21.next();
            if (!soundHandler.func_215294_c(wind)) {
               removes.add(wind);
            }
         }

         this.playingWindSounds.removeAll(removes);
      }

   }
}
