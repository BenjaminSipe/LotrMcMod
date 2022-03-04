package lotr.common.world;

import com.google.common.math.IntMath;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRCloudRenderer;
import lotr.client.render.LOTRSkyRenderer;
import lotr.client.render.LOTRWeatherRenderer;
import lotr.common.LOTRConfig;
import lotr.common.LOTRDate;
import lotr.common.LOTRDimension;
import lotr.common.LOTRMod;
import lotr.common.LOTRTime;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenOcean;
import lotr.common.world.biome.LOTRBiomeGenTundra;
import lotr.compatibility.LOTRModChecker;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.ForgeModContainer;

public abstract class LOTRWorldProvider extends WorldProvider {
   public static int MOON_PHASES = 8;
   @SideOnly(Side.CLIENT)
   private IRenderHandler lotrSkyRenderer;
   @SideOnly(Side.CLIENT)
   private IRenderHandler lotrCloudRenderer;
   @SideOnly(Side.CLIENT)
   private IRenderHandler lotrWeatherRenderer;
   private boolean spawnHostiles = true;
   private boolean spawnPeacefuls = true;
   private double cloudsR;
   private double cloudsG;
   private double cloudsB;
   private double fogR;
   private double fogG;
   private double fogB;

   public abstract LOTRDimension getLOTRDimension();

   public void func_76572_b() {
      this.field_76578_c = new LOTRWorldChunkManager(this.field_76579_a, this.getLOTRDimension());
      this.field_76574_g = this.getLOTRDimension().dimensionID;
   }

   public String getWelcomeMessage() {
      return StatCollector.func_74837_a("lotr.dimension.enter", new Object[]{this.getLOTRDimension().getDimensionName()});
   }

   public String getDepartMessage() {
      return StatCollector.func_74837_a("lotr.dimension.exit", new Object[]{this.getLOTRDimension().getDimensionName()});
   }

   public String getSaveFolder() {
      return this.getLOTRDimension().dimensionName;
   }

   public String func_80007_l() {
      return this.getLOTRDimension().dimensionName;
   }

   public boolean func_76567_e() {
      return true;
   }

   public BiomeGenBase getBiomeGenForCoords(int i, int k) {
      if (this.field_76579_a.func_72899_e(i, 0, k)) {
         Chunk chunk = this.field_76579_a.func_72938_d(i, k);
         if (chunk != null) {
            int chunkX = i & 15;
            int chunkZ = k & 15;
            int biomeID = chunk.func_76605_m()[chunkZ << 4 | chunkX] & 255;
            if (biomeID == 255) {
               BiomeGenBase biomegenbase = this.field_76578_c.func_76935_a((chunk.field_76635_g << 4) + chunkX, (chunk.field_76647_h << 4) + chunkZ);
               biomeID = biomegenbase.field_76756_M;
               chunk.func_76605_m()[chunkZ << 4 | chunkX] = (byte)(biomeID & 255);
            }

            LOTRDimension dim = this.getLOTRDimension();
            return dim.biomeList[biomeID] == null ? dim.biomeList[0] : dim.biomeList[biomeID];
         }
      }

      return this.field_76578_c.func_76935_a(i, k);
   }

   public boolean canBlockFreeze(int i, int j, int k, boolean isBlockUpdate) {
      BiomeGenBase biome = this.field_76579_a.func_72807_a(i, k);
      if (!(biome instanceof LOTRBiomeGenOcean)) {
         return biome instanceof LOTRBiome ? this.field_76579_a.canBlockFreezeBody(i, j, k, isBlockUpdate) : this.field_76579_a.canBlockFreezeBody(i, j, k, isBlockUpdate);
      } else {
         return LOTRBiomeGenOcean.isFrozen(i, k) && this.canFreeze_ignoreTemp(i, j, k, isBlockUpdate);
      }
   }

   public boolean canSnowAt(int i, int j, int k, boolean checkLight) {
      BiomeGenBase biome = this.field_76579_a.func_72807_a(i, k);
      if (biome instanceof LOTRBiomeGenOcean) {
         return LOTRBiomeGenOcean.isFrozen(i, k) && this.canSnow_ignoreTemp(i, j, k, checkLight);
      } else if (biome instanceof LOTRBiomeGenTundra) {
         boolean flag = this.field_76579_a.canSnowAtBody(i, j, k, checkLight);
         return flag && LOTRBiomeGenTundra.isTundraSnowy(i, k);
      } else if (!(biome instanceof LOTRBiome)) {
         return this.field_76579_a.canSnowAtBody(i, j, k, checkLight);
      } else {
         return j >= ((LOTRBiome)biome).getSnowHeight() && this.field_76579_a.canSnowAtBody(i, j, k, checkLight);
      }
   }

   private boolean canFreeze_ignoreTemp(int i, int j, int k, boolean isBlockUpdate) {
      if (j >= 0 && j < this.field_76579_a.func_72800_K() && this.field_76579_a.func_72972_b(EnumSkyBlock.Block, i, j, k) < 10) {
         Block block = this.field_76579_a.func_147439_a(i, j, k);
         if ((block == Blocks.field_150355_j || block == Blocks.field_150358_i) && this.field_76579_a.func_72805_g(i, j, k) == 0) {
            if (!isBlockUpdate) {
               return true;
            }

            boolean surroundWater = true;
            if (surroundWater && this.field_76579_a.func_147439_a(i - 1, j, k).func_149688_o() != Material.field_151586_h) {
               surroundWater = false;
            }

            if (surroundWater && this.field_76579_a.func_147439_a(i + 1, j, k).func_149688_o() != Material.field_151586_h) {
               surroundWater = false;
            }

            if (surroundWater && this.field_76579_a.func_147439_a(i, j, k - 1).func_149688_o() != Material.field_151586_h) {
               surroundWater = false;
            }

            if (surroundWater && this.field_76579_a.func_147439_a(i, j, k + 1).func_149688_o() != Material.field_151586_h) {
               surroundWater = false;
            }

            if (!surroundWater) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean canSnow_ignoreTemp(int i, int j, int k, boolean checkLight) {
      if (!checkLight) {
         return true;
      } else {
         if (j >= 0 && j < this.field_76579_a.func_72800_K() && this.field_76579_a.func_72972_b(EnumSkyBlock.Block, i, j, k) < 10) {
            Block block = this.field_76579_a.func_147439_a(i, j, k);
            if (block.func_149688_o() == Material.field_151579_a && Blocks.field_150431_aC.func_149742_c(this.field_76579_a, i, j, k)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean shouldMapSpin(String entity, double x, double y, double z) {
      return false;
   }

   public void resetRainAndThunder() {
      super.resetRainAndThunder();
      if (LOTRMod.doDayCycle(this.field_76579_a)) {
         LOTRTime.advanceToMorning();
      }

   }

   public float func_76563_a(long time, float partialTick) {
      float daytime = ((float)((int)(time % (long)LOTRTime.DAY_LENGTH)) + partialTick) / (float)LOTRTime.DAY_LENGTH - 0.25F;
      if (daytime < 0.0F) {
         ++daytime;
      }

      if (daytime > 1.0F) {
         --daytime;
      }

      float angle = 1.0F - (float)((Math.cos((double)daytime * 3.141592653589793D) + 1.0D) / 2.0D);
      angle = daytime + (angle - daytime) / 3.0F;
      return angle;
   }

   public int func_76559_b(long time) {
      return getLOTRMoonPhase();
   }

   public static int getLOTRMoonPhase() {
      int day = LOTRDate.ShireReckoning.currentDay;
      return IntMath.mod(day, MOON_PHASES);
   }

   public static boolean isLunarEclipse() {
      int day = LOTRDate.ShireReckoning.currentDay;
      return getLOTRMoonPhase() == 0 && IntMath.mod(day / MOON_PHASES, 4) == 3;
   }

   @SideOnly(Side.CLIENT)
   public IRenderHandler getSkyRenderer() {
      if (!LOTRModChecker.hasShaders() && LOTRConfig.enableLOTRSky) {
         if (this.lotrSkyRenderer == null) {
            this.lotrSkyRenderer = new LOTRSkyRenderer(this);
         }

         return this.lotrSkyRenderer;
      } else {
         return super.getSkyRenderer();
      }
   }

   @SideOnly(Side.CLIENT)
   public IRenderHandler getWeatherRenderer() {
      if (this.lotrWeatherRenderer == null) {
         this.lotrWeatherRenderer = new LOTRWeatherRenderer();
      }

      return this.lotrWeatherRenderer;
   }

   @SideOnly(Side.CLIENT)
   public IRenderHandler getCloudRenderer() {
      if (!LOTRModChecker.hasShaders() && LOTRConfig.cloudRange > 0) {
         if (this.lotrCloudRenderer == null) {
            this.lotrCloudRenderer = new LOTRCloudRenderer();
         }

         return this.lotrCloudRenderer;
      } else {
         return super.getCloudRenderer();
      }
   }

   @SideOnly(Side.CLIENT)
   public float func_76571_f() {
      return 192.0F;
   }

   @SideOnly(Side.CLIENT)
   public Vec3 drawClouds(float f) {
      Minecraft mc = Minecraft.func_71410_x();
      int i = (int)mc.field_71451_h.field_70165_t;
      int k = (int)mc.field_71451_h.field_70161_v;
      Vec3 clouds = super.drawClouds(f);
      this.cloudsR = this.cloudsG = this.cloudsB = 0.0D;
      GameSettings settings = mc.field_71474_y;
      int[] ranges = ForgeModContainer.blendRanges;
      int distance = 0;
      if (settings.field_74347_j && settings.field_151451_c >= 0 && settings.field_151451_c < ranges.length) {
         distance = ranges[settings.field_151451_c];
      }

      int l = 0;

      for(int i1 = -distance; i1 <= distance; ++i1) {
         for(int k1 = -distance; k1 <= distance; ++k1) {
            Vec3 tempClouds = Vec3.func_72443_a(clouds.field_72450_a, clouds.field_72448_b, clouds.field_72449_c);
            BiomeGenBase biome = this.field_76579_a.func_72807_a(i + i1, k + k1);
            if (biome instanceof LOTRBiome) {
               ((LOTRBiome)biome).getCloudColor(tempClouds);
            }

            this.cloudsR += tempClouds.field_72450_a;
            this.cloudsG += tempClouds.field_72448_b;
            this.cloudsB += tempClouds.field_72449_c;
            ++l;
         }
      }

      this.cloudsR /= (double)l;
      this.cloudsG /= (double)l;
      this.cloudsB /= (double)l;
      return Vec3.func_72443_a(this.cloudsR, this.cloudsG, this.cloudsB);
   }

   @SideOnly(Side.CLIENT)
   public Vec3 func_76562_b(float f, float f1) {
      Minecraft mc = Minecraft.func_71410_x();
      int i = (int)mc.field_71451_h.field_70165_t;
      int k = (int)mc.field_71451_h.field_70161_v;
      Vec3 fog = super.func_76562_b(f, f1);
      this.fogR = this.fogG = this.fogB = 0.0D;
      GameSettings settings = mc.field_71474_y;
      int[] ranges = ForgeModContainer.blendRanges;
      int distance = 0;
      if (settings.field_74347_j && settings.field_151451_c >= 0 && settings.field_151451_c < ranges.length) {
         distance = ranges[settings.field_151451_c];
      }

      int l = 0;

      for(int i1 = -distance; i1 <= distance; ++i1) {
         for(int k1 = -distance; k1 <= distance; ++k1) {
            Vec3 tempFog = Vec3.func_72443_a(fog.field_72450_a, fog.field_72448_b, fog.field_72449_c);
            BiomeGenBase biome = this.field_76579_a.func_72807_a(i + i1, k + k1);
            if (biome instanceof LOTRBiome) {
               ((LOTRBiome)biome).getFogColor(tempFog);
            }

            this.fogR += tempFog.field_72450_a;
            this.fogG += tempFog.field_72448_b;
            this.fogB += tempFog.field_72449_c;
            ++l;
         }
      }

      this.fogR /= (double)l;
      this.fogG /= (double)l;
      this.fogB /= (double)l;
      return Vec3.func_72443_a(this.fogR, this.fogG, this.fogB);
   }

   public float[] modifyFogIntensity(float farPlane, int fogMode) {
      Minecraft mc = Minecraft.func_71410_x();
      int i = (int)mc.field_71451_h.field_70165_t;
      int k = (int)mc.field_71451_h.field_70161_v;
      float fogStart = 0.0F;
      float fogEnd = 0.0F;
      GameSettings settings = mc.field_71474_y;
      int[] ranges = ForgeModContainer.blendRanges;
      int distance = 0;
      if (settings.field_74347_j && settings.field_151451_c >= 0 && settings.field_151451_c < ranges.length) {
         distance = ranges[settings.field_151451_c];
      }

      int l = 0;

      for(int i1 = -distance; i1 <= distance; ++i1) {
         for(int k1 = -distance; k1 <= distance; ++k1) {
            float thisFogStart = 0.0F;
            float thisFogEnd = 0.0F;
            boolean foggy = this.func_76568_b(i + i1, k + k1);
            if (foggy) {
               thisFogStart = farPlane * 0.05F;
               thisFogEnd = Math.min(farPlane, 192.0F) * 0.5F;
            } else if (fogMode < 0) {
               thisFogStart = 0.0F;
               thisFogEnd = farPlane;
            } else {
               thisFogStart = farPlane * 0.75F;
               thisFogEnd = farPlane;
            }

            fogStart += thisFogStart;
            fogEnd += thisFogEnd;
            ++l;
         }
      }

      fogStart /= (float)l;
      fogEnd /= (float)l;
      return new float[]{fogStart, fogEnd};
   }

   public float[] handleFinalFogColors(EntityLivingBase viewer, double tick, float[] rgb) {
      return rgb;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_76568_b(int i, int k) {
      BiomeGenBase biome = this.field_76579_a.func_72807_a(i, k);
      return biome instanceof LOTRBiome ? ((LOTRBiome)biome).hasFog() : super.func_76568_b(i, k);
   }
}
