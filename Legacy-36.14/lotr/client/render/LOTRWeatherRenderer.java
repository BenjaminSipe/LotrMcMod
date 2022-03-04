package lotr.client.render;

import java.util.Random;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRConfig;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenDagorlad;
import lotr.common.world.biome.LOTRBiomeGenFarHaradVolcano;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import lotr.common.world.biome.LOTRBiomeGenMorgulVale;
import lotr.common.world.biome.LOTRBiomeGenNurn;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public class LOTRWeatherRenderer extends IRenderHandler {
   private static final ResourceLocation rainTexture = new ResourceLocation("lotr:weather/rain.png");
   private static final ResourceLocation snowTexture = new ResourceLocation("lotr:weather/snow.png");
   private static final ResourceLocation ashTexture = new ResourceLocation("lotr:weather/ash.png");
   private static final ResourceLocation sandstormTexture = new ResourceLocation("lotr:weather/sandstorm.png");
   private static final ResourceLocation rainTexture_def = new ResourceLocation("textures/environment/rain.png");
   private static final ResourceLocation snowTexture_def = new ResourceLocation("textures/environment/snow.png");
   private Random rand = new Random();
   private float[] rainXCoords;
   private float[] rainYCoords;

   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      EntityRenderer er = mc.field_71460_t;
      int rendererUpdateCount = LOTRTickHandlerClient.clientTick;
      float rainStrength = world.func_72867_j(partialTicks);
      if (rainStrength > 0.0F) {
         er.func_78463_b((double)partialTicks);
         int k2;
         if (this.rainXCoords == null) {
            this.rainXCoords = new float[1024];
            this.rainYCoords = new float[1024];

            for(int i = 0; i < 32; ++i) {
               for(k2 = 0; k2 < 32; ++k2) {
                  float f2 = (float)(k2 - 16);
                  float f3 = (float)(i - 16);
                  float f4 = MathHelper.func_76129_c(f2 * f2 + f3 * f3);
                  this.rainXCoords[i << 5 | k2] = -f3 / f4;
                  this.rainYCoords[i << 5 | k2] = f2 / f4;
               }
            }
         }

         EntityLivingBase entitylivingbase = mc.field_71451_h;
         k2 = MathHelper.func_76128_c(entitylivingbase.field_70165_t);
         int l2 = MathHelper.func_76128_c(entitylivingbase.field_70163_u);
         int i3 = MathHelper.func_76128_c(entitylivingbase.field_70161_v);
         Tessellator tessellator = Tessellator.field_78398_a;
         GL11.glDisable(2884);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glEnable(3042);
         OpenGlHelper.func_148821_a(770, 771, 1, 0);
         GL11.glAlphaFunc(516, 0.1F);
         double d0 = entitylivingbase.field_70142_S + (entitylivingbase.field_70165_t - entitylivingbase.field_70142_S) * (double)partialTicks;
         double d1 = entitylivingbase.field_70137_T + (entitylivingbase.field_70163_u - entitylivingbase.field_70137_T) * (double)partialTicks;
         double d2 = entitylivingbase.field_70136_U + (entitylivingbase.field_70161_v - entitylivingbase.field_70136_U) * (double)partialTicks;
         int k = MathHelper.func_76128_c(d1);
         byte b0 = 5;
         if (mc.field_71474_y.field_74347_j) {
            b0 = 10;
         }

         boolean flag = false;
         byte b1 = -1;
         float f5 = (float)rendererUpdateCount + partialTicks;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         flag = false;
         boolean isChristmas = LOTRMod.isChristmas();

         for(int l = i3 - b0; l <= i3 + b0; ++l) {
            for(int i1 = k2 - b0; i1 <= k2 + b0; ++i1) {
               int j1 = (l - i3 + 16) * 32 + i1 - k2 + 16;
               float f6 = this.rainXCoords[j1] * 0.5F;
               float f7 = this.rainYCoords[j1] * 0.5F;
               BiomeGenBase biomegenbase = world.func_72807_a(i1, l);
               boolean rainy = biomegenbase.func_76738_d();
               boolean snowy = biomegenbase.func_76746_c();
               boolean ashy = biomegenbase instanceof LOTRBiomeGenMordor && !(biomegenbase instanceof LOTRBiomeGenNurn) && !(biomegenbase instanceof LOTRBiomeGenMorgulVale) || biomegenbase instanceof LOTRBiomeGenFarHaradVolcano || biomegenbase instanceof LOTRBiomeGenDagorlad;
               boolean sandy = isSandstormBiome(biomegenbase);
               if (isChristmas) {
                  ashy = false;
                  sandy = false;
               }

               if (rainy || snowy || ashy || sandy) {
                  int k1 = world.func_72874_g(i1, l);
                  int l1 = l2 - b0;
                  int i2 = l2 + b0;
                  if (l1 < k1) {
                     l1 = k1;
                  }

                  if (i2 < k1) {
                     i2 = k1;
                  }

                  float f8 = 1.0F;
                  int j2 = k1;
                  if (k1 < k) {
                     j2 = k;
                  }

                  if (l1 != i2) {
                     this.rand.setSeed((long)(i1 * i1 * 3121 + i1 * 45238971 ^ l * l * 418711 + l * 13761));
                     float f9 = biomegenbase.func_150564_a(i1, l1, l);
                     float f10;
                     double d4;
                     float f16;
                     float f11;
                     double d5;
                     float f14;
                     float f15;
                     if (ashy) {
                        if (b1 != 1) {
                           if (b1 >= 0) {
                              tessellator.func_78381_a();
                           }

                           b1 = 1;
                           mc.func_110434_K().func_110577_a(ashTexture);
                           tessellator.func_78382_b();
                        }

                        f10 = ((float)(rendererUpdateCount & 511) + partialTicks) / 512.0F;
                        f16 = this.rand.nextFloat() * 0.3F + f5 * 0.003F * (float)this.rand.nextGaussian();
                        f11 = this.rand.nextFloat() + f5 * (float)this.rand.nextGaussian() * 0.001F;
                        d4 = (double)((float)i1 + 0.5F) - entitylivingbase.field_70165_t;
                        d5 = (double)((float)l + 0.5F) - entitylivingbase.field_70161_v;
                        f14 = MathHelper.func_76133_a(d4 * d4 + d5 * d5) / (float)b0;
                        f15 = 1.0F;
                        tessellator.func_78380_c((world.func_72802_i(i1, j2, l, 0) * 3 + 15728880) / 4);
                        tessellator.func_78369_a(f15, f15, f15, ((1.0F - f14 * f14) * 0.3F + 0.5F) * rainStrength);
                        tessellator.func_78373_b(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                        tessellator.func_78374_a((double)((float)i1 - f6) + 0.5D, (double)l1, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8 + f16), (double)((float)l1 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78374_a((double)((float)i1 + f6) + 0.5D, (double)l1, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8 + f16), (double)((float)l1 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78374_a((double)((float)i1 + f6) + 0.5D, (double)i2, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8 + f16), (double)((float)i2 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78374_a((double)((float)i1 - f6) + 0.5D, (double)i2, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8 + f16), (double)((float)i2 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78373_b(0.0D, 0.0D, 0.0D);
                     } else if (sandy) {
                        if (b1 != 1) {
                           if (b1 >= 0) {
                              tessellator.func_78381_a();
                           }

                           b1 = 1;
                           mc.func_110434_K().func_110577_a(sandstormTexture);
                           tessellator.func_78382_b();
                        }

                        f10 = ((float)(rendererUpdateCount & 511) + partialTicks) / 512.0F;
                        f16 = f5 * (0.07F + (float)this.rand.nextGaussian() * 0.01F);
                        f11 = this.rand.nextFloat() + f5 * (float)this.rand.nextGaussian() * 0.001F;
                        d4 = (double)((float)i1 + 0.5F) - entitylivingbase.field_70165_t;
                        d5 = (double)((float)l + 0.5F) - entitylivingbase.field_70161_v;
                        f14 = MathHelper.func_76133_a(d4 * d4 + d5 * d5) / (float)b0;
                        f15 = 1.0F;
                        tessellator.func_78380_c((world.func_72802_i(i1, j2, l, 0) * 3 + 15728880) / 4);
                        tessellator.func_78369_a(f15, f15, f15, ((1.0F - f14 * f14) * 0.3F + 0.5F) * rainStrength);
                        tessellator.func_78373_b(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                        tessellator.func_78374_a((double)((float)i1 - f6) + 0.5D, (double)l1, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8 + f16), (double)((float)l1 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78374_a((double)((float)i1 + f6) + 0.5D, (double)l1, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8 + f16), (double)((float)l1 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78374_a((double)((float)i1 + f6) + 0.5D, (double)i2, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8 + f16), (double)((float)i2 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78374_a((double)((float)i1 - f6) + 0.5D, (double)i2, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8 + f16), (double)((float)i2 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78373_b(0.0D, 0.0D, 0.0D);
                     } else if (world.func_72959_q().func_76939_a(f9, k1) >= 0.15F) {
                        if (b1 != 0) {
                           if (b1 >= 0) {
                              tessellator.func_78381_a();
                           }

                           b1 = 0;
                           if (LOTRConfig.newWeather) {
                              mc.func_110434_K().func_110577_a(rainTexture);
                           } else {
                              mc.func_110434_K().func_110577_a(rainTexture_def);
                           }

                           tessellator.func_78382_b();
                        }

                        f10 = ((float)(rendererUpdateCount + i1 * i1 * 3121 + i1 * 45238971 + l * l * 418711 + l * 13761 & 31) + partialTicks) / 32.0F * (3.0F + this.rand.nextFloat());
                        double d3 = (double)((float)i1 + 0.5F) - entitylivingbase.field_70165_t;
                        d4 = (double)((float)l + 0.5F) - entitylivingbase.field_70161_v;
                        float f12 = MathHelper.func_76133_a(d3 * d3 + d4 * d4) / (float)b0;
                        float f13 = 1.0F;
                        tessellator.func_78380_c(world.func_72802_i(i1, j2, l, 0));
                        tessellator.func_78369_a(f13, f13, f13, ((1.0F - f12 * f12) * 0.5F + 0.5F) * rainStrength);
                        tessellator.func_78373_b(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                        tessellator.func_78374_a((double)((float)i1 - f6) + 0.5D, (double)l1, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8), (double)((float)l1 * f8 / 4.0F + f10 * f8));
                        tessellator.func_78374_a((double)((float)i1 + f6) + 0.5D, (double)l1, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8), (double)((float)l1 * f8 / 4.0F + f10 * f8));
                        tessellator.func_78374_a((double)((float)i1 + f6) + 0.5D, (double)i2, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8), (double)((float)i2 * f8 / 4.0F + f10 * f8));
                        tessellator.func_78374_a((double)((float)i1 - f6) + 0.5D, (double)i2, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8), (double)((float)i2 * f8 / 4.0F + f10 * f8));
                        tessellator.func_78373_b(0.0D, 0.0D, 0.0D);
                     } else {
                        if (b1 != 1) {
                           if (b1 >= 0) {
                              tessellator.func_78381_a();
                           }

                           b1 = 1;
                           if (LOTRConfig.newWeather) {
                              mc.func_110434_K().func_110577_a(snowTexture);
                           } else {
                              mc.func_110434_K().func_110577_a(snowTexture_def);
                           }

                           tessellator.func_78382_b();
                        }

                        f10 = ((float)(rendererUpdateCount & 511) + partialTicks) / 512.0F;
                        f16 = this.rand.nextFloat() + f5 * 0.01F * (float)this.rand.nextGaussian();
                        f11 = this.rand.nextFloat() + f5 * (float)this.rand.nextGaussian() * 0.001F;
                        d4 = (double)((float)i1 + 0.5F) - entitylivingbase.field_70165_t;
                        d5 = (double)((float)l + 0.5F) - entitylivingbase.field_70161_v;
                        f14 = MathHelper.func_76133_a(d4 * d4 + d5 * d5) / (float)b0;
                        f15 = 1.0F;
                        tessellator.func_78380_c((world.func_72802_i(i1, j2, l, 0) * 3 + 15728880) / 4);
                        tessellator.func_78369_a(f15, f15, f15, ((1.0F - f14 * f14) * 0.3F + 0.5F) * rainStrength);
                        tessellator.func_78373_b(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                        tessellator.func_78374_a((double)((float)i1 - f6) + 0.5D, (double)l1, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8 + f16), (double)((float)l1 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78374_a((double)((float)i1 + f6) + 0.5D, (double)l1, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8 + f16), (double)((float)l1 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78374_a((double)((float)i1 + f6) + 0.5D, (double)i2, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8 + f16), (double)((float)i2 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78374_a((double)((float)i1 - f6) + 0.5D, (double)i2, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8 + f16), (double)((float)i2 * f8 / 4.0F + f10 * f8 + f11));
                        tessellator.func_78373_b(0.0D, 0.0D, 0.0D);
                     }
                  }
               }
            }
         }

         if (b1 >= 0) {
            tessellator.func_78381_a();
         }

         GL11.glEnable(2884);
         GL11.glDisable(3042);
         GL11.glAlphaFunc(516, 0.1F);
         er.func_78483_a((double)partialTicks);
      }

   }

   public static boolean isSandstormBiome(BiomeGenBase biome) {
      return !biome.func_76738_d() && biome.field_76752_A.func_149688_o() == Material.field_151595_p;
   }
}
