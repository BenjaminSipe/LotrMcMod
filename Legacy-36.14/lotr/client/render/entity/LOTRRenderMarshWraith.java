package lotr.client.render.entity;

import lotr.client.model.LOTRModelMarshWraith;
import lotr.common.entity.npc.LOTREntityMarshWraith;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderMarshWraith extends RenderLiving {
   private static ResourceLocation skin = new ResourceLocation("lotr:mob/wraith/marshWraith.png");

   public LOTRRenderMarshWraith() {
      super(new LOTRModelMarshWraith(), 0.5F);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return skin;
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      super.func_77041_b(entity, f);
      float f1 = 0.9375F;
      GL11.glScalef(f1, f1, f1);
      LOTREntityMarshWraith wraith = (LOTREntityMarshWraith)entity;
      if (wraith.getSpawnFadeTime() < 30) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3008);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, (float)wraith.getSpawnFadeTime() / 30.0F);
      } else if (wraith.getDeathFadeTime() > 0) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3008);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, (float)wraith.getDeathFadeTime() / 30.0F);
      }

   }

   protected float func_77037_a(EntityLivingBase entity) {
      return 0.0F;
   }
}
