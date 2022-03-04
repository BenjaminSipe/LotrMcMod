package lotr.client.fx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.client.LOTRClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTREffectRenderer {
   private Minecraft mc;
   private World worldObj;
   private List[] particleLayers = new List[0];

   public LOTREffectRenderer(Minecraft minecraft) {
      this.mc = minecraft;
   }

   public void addEffect(EntityFX entityfx) {
      int layer = entityfx.func_70537_b();
      if (layer >= this.particleLayers.length) {
         List[] newLayers = new List[layer + 1];

         for(int l = 0; l < newLayers.length; ++l) {
            if (l < this.particleLayers.length) {
               newLayers[l] = this.particleLayers[l];
            } else {
               newLayers[l] = new ArrayList();
            }
         }

         this.particleLayers = newLayers;
      }

      List layerList = this.particleLayers[layer];
      if (layerList.size() >= 4000) {
         layerList.remove(0);
      }

      layerList.add(entityfx);
   }

   public void updateEffects() {
      List[] var1 = this.particleLayers;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         List layer = var1[var3];

         for(int i = 0; i < layer.size(); ++i) {
            EntityFX particle = (EntityFX)layer.get(i);
            if (particle != null) {
               particle.func_70071_h_();
            }

            if (particle == null || particle.field_70128_L) {
               layer.remove(i--);
            }
         }
      }

   }

   public void renderParticles(Entity entity, float f) {
      float f1 = ActiveRenderInfo.field_74588_d;
      float f2 = ActiveRenderInfo.field_74586_f;
      float f3 = ActiveRenderInfo.field_74587_g;
      float f4 = ActiveRenderInfo.field_74596_h;
      float f5 = ActiveRenderInfo.field_74589_e;
      EntityFX.field_70556_an = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)f;
      EntityFX.field_70554_ao = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)f;
      EntityFX.field_70555_ap = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)f;

      for(int l = 0; l < this.particleLayers.length; ++l) {
         List layer = this.particleLayers[l];
         if (!layer.isEmpty()) {
            if (l == 0) {
               this.mc.func_110434_K().func_110577_a(LOTRClientProxy.particlesTexture);
            } else if (l == 1) {
               this.mc.func_110434_K().func_110577_a(LOTRClientProxy.particles2Texture);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDepthMask(false);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glAlphaFunc(516, 0.003921569F);
            Tessellator tessellator = Tessellator.field_78398_a;
            tessellator.func_78382_b();
            Iterator var11 = layer.iterator();

            while(var11.hasNext()) {
               EntityFX particle = (EntityFX)var11.next();
               if (particle != null) {
                  tessellator.func_78380_c(particle.func_70070_b(f));
                  particle.func_70539_a(tessellator, f, f1, f5, f2, f3, f4);
               }
            }

            tessellator.func_78381_a();
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glAlphaFunc(516, 0.1F);
         }
      }

   }

   public void clearEffectsAndSetWorld(World world) {
      this.worldObj = world;
      List[] var2 = this.particleLayers;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         List layer = var2[var4];
         layer.clear();
      }

   }
}
