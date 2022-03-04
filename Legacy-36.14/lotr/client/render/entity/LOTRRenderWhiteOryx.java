package lotr.client.render.entity;

import lotr.common.entity.animal.LOTREntityWhiteOryx;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderWhiteOryx extends LOTRRenderGemsbok {
   private LOTRRandomSkins oryxSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/whiteOryx");

   public ResourceLocation func_110775_a(Entity entity) {
      return this.oryxSkins.getRandomSkin((LOTREntityWhiteOryx)entity);
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      float scale = 0.9F;
      GL11.glScalef(scale, scale, scale);
   }
}
