package lotr.client.render.entity;

import java.util.HashMap;
import java.util.Map;
import lotr.client.model.LOTRModelButterfly;
import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderButterfly extends RenderLiving {
   private static Map textures = new HashMap();

   public LOTRRenderButterfly() {
      super(new LOTRModelButterfly(), 0.2F);
      LOTREntityButterfly.ButterflyType[] var1 = LOTREntityButterfly.ButterflyType.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTREntityButterfly.ButterflyType t = var1[var3];
         textures.put(t, LOTRRandomSkins.loadSkinsList("lotr:mob/butterfly/" + t.textureDir));
      }

   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityButterfly butterfly = (LOTREntityButterfly)entity;
      if (butterfly.getButterflyType() == LOTREntityButterfly.ButterflyType.LORIEN) {
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(2896);
      }

      super.func_76986_a(entity, d, d1, d2, f, f1);
      GL11.glEnable(2896);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityButterfly butterfly = (LOTREntityButterfly)entity;
      LOTRRandomSkins skins = (LOTRRandomSkins)textures.get(butterfly.getButterflyType());
      return skins.getRandomSkin(butterfly);
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      GL11.glScalef(0.3F, 0.3F, 0.3F);
   }

   protected float func_77044_a(EntityLivingBase entity, float f) {
      LOTREntityButterfly butterfly = (LOTREntityButterfly)entity;
      return butterfly.isButterflyStill() && butterfly.flapTime > 0 ? (float)butterfly.flapTime - f : super.func_77044_a(entity, f);
   }
}
