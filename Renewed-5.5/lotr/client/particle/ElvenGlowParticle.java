package lotr.client.particle;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ElvenGlowParticle extends SpriteTexturedParticle {
   private static IParticleRenderType PARTICLE_SHEET_PROPER_TRANSLUCENT = new IParticleRenderType() {
      public void func_217600_a(BufferBuilder buf, TextureManager texMgr) {
         RenderSystem.depthMask(true);
         texMgr.func_110577_a(AtlasTexture.field_215262_g);
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
         RenderSystem.alphaFunc(516, 0.0F);
         buf.func_181668_a(7, DefaultVertexFormats.field_181704_d);
      }

      public void func_217599_a(Tessellator tess) {
         tess.func_78381_a();
      }

      public String toString() {
         return "PARTICLE_SHEET_PROPER_TRANSLUCENT";
      }
   };

   private ElvenGlowParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      super(world, x, y, z, xSpeed, ySpeed, zSpeed);
      this.field_187129_i = this.field_187129_i * 0.009999999776482582D + xSpeed;
      this.field_187130_j = this.field_187130_j * 0.009999999776482582D + ySpeed;
      this.field_187131_k = this.field_187131_k * 0.009999999776482582D + zSpeed;
      this.field_187126_f += (double)((this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * 0.05F);
      this.field_187127_g += (double)((this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * 0.05F);
      this.field_187128_h += (double)((this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * 0.05F);
      this.field_70547_e = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
      this.field_70547_e *= 3;
   }

   public void func_187110_a(double x, double y, double z) {
      this.func_187108_a(this.func_187116_l().func_72317_d(x, y, z));
      this.func_187118_j();
   }

   public float func_217561_b(float pTick) {
      return 0.25F + 0.002F * MathHelper.func_76126_a(((float)this.field_70546_d + pTick - 1.0F) * 0.25F * 3.1415927F);
   }

   public int func_189214_a(float pTick) {
      float f = ((float)this.field_70547_e - ((float)this.field_70546_d + pTick)) / (float)this.field_70547_e;
      f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
      int worldLight = super.func_189214_a(pTick);
      int lx = worldLight & 255;
      int ly = worldLight >> 16 & 255;
      lx += (int)(f * 15.0F * 16.0F);
      if (lx > 240) {
         lx = 240;
      }

      return lx | ly << 16;
   }

   public void func_225606_a_(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
      super.func_225606_a_(buffer, renderInfo, partialTicks);
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      ++this.field_70546_d;
      this.field_82339_as = 0.9F - ((float)this.field_70546_d - 1.0F) * 0.02F;
      if (this.field_70546_d < this.field_70547_e && this.field_82339_as > 0.1F) {
         this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
         this.field_187129_i *= 0.96D;
         this.field_187130_j *= 0.96D;
         this.field_187131_k *= 0.96D;
         if (this.field_187132_l) {
            this.field_187129_i *= 0.7D;
            this.field_187131_k *= 0.7D;
         }
      } else {
         this.func_187112_i();
      }

   }

   public IParticleRenderType func_217558_b() {
      return PARTICLE_SHEET_PROPER_TRANSLUCENT;
   }

   // $FF: synthetic method
   ElvenGlowParticle(ClientWorld x0, double x1, double x2, double x3, double x4, double x5, double x6, Object x7) {
      this(x0, x1, x2, x3, x4, x5, x6);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite sprites) {
         this.spriteSet = sprites;
      }

      public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         ElvenGlowParticle particle = new ElvenGlowParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
         particle.func_217568_a(this.spriteSet);
         return particle;
      }
   }
}
