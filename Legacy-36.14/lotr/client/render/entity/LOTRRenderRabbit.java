package lotr.client.render.entity;

import lotr.client.model.LOTRModelRabbit;
import lotr.common.entity.animal.LOTREntityRabbit;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderRabbit extends RenderLiving {
   private static LOTRRandomSkins rabbitSkins;

   public LOTRRenderRabbit() {
      super(new LOTRModelRabbit(), 0.3F);
      rabbitSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/rabbit");
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityRabbit rabbit = (LOTREntityRabbit)entity;
      return rabbitSkins.getRandomSkin(rabbit);
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      GL11.glScalef(0.75F, 0.75F, 0.75F);
   }
}
