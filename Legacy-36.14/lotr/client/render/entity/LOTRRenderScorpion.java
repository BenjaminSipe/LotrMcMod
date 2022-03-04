package lotr.client.render.entity;

import lotr.client.model.LOTRModelScorpion;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import lotr.common.entity.animal.LOTREntityScorpion;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderScorpion extends RenderLiving {
   private static ResourceLocation jungleTexture = new ResourceLocation("lotr:mob/scorpion/jungle.png");
   private static ResourceLocation desertTexture = new ResourceLocation("lotr:mob/scorpion/desert.png");

   public LOTRRenderScorpion() {
      super(new LOTRModelScorpion(), 1.0F);
      this.func_77042_a(new LOTRModelScorpion());
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return entity instanceof LOTREntityDesertScorpion ? desertTexture : jungleTexture;
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      float scale = ((LOTREntityScorpion)entity).getScorpionScaleAmount();
      GL11.glScalef(scale, scale, scale);
   }

   protected float func_77037_a(EntityLivingBase entity) {
      return 180.0F;
   }

   public float func_77044_a(EntityLivingBase entity, float f) {
      float strikeTime = (float)((LOTREntityScorpion)entity).getStrikeTime();
      if (strikeTime > 0.0F) {
         strikeTime -= f;
      }

      return strikeTime / 20.0F;
   }
}
