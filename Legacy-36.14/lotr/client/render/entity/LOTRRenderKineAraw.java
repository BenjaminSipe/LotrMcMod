package lotr.client.render.entity;

import lotr.common.entity.animal.LOTREntityKineAraw;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderKineAraw extends LOTRRenderAurochs {
   private static LOTRRandomSkins kineSkins;

   public LOTRRenderKineAraw() {
      kineSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/kineAraw");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityKineAraw kine = (LOTREntityKineAraw)entity;
      ResourceLocation skin = kineSkins.getRandomSkin(kine);
      return skin;
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      float scale = LOTREntityKineAraw.KINE_SCALE;
      GL11.glScalef(scale, scale, scale);
   }
}
