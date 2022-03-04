package lotr.client.render.entity;

import java.util.HashMap;
import java.util.Map;
import lotr.client.model.LOTRModelBear;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBear extends RenderLiving {
   private static Map bearSkins = new HashMap();

   public LOTRRenderBear() {
      super(new LOTRModelBear(), 0.5F);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityBear bear = (LOTREntityBear)entity;
      return getBearSkin(bear.getBearType());
   }

   public static ResourceLocation getBearSkin(LOTREntityBear.BearType type) {
      String s = type.textureName();
      ResourceLocation skin = (ResourceLocation)bearSkins.get(s);
      if (skin == null) {
         skin = new ResourceLocation("lotr:mob/bear/" + s + ".png");
         bearSkins.put(s, skin);
      }

      return skin;
   }

   public void func_77041_b(EntityLivingBase entity, float f) {
      scaleBearModel();
   }

   public static void scaleBearModel() {
      float scale = 1.2F;
      GL11.glScalef(scale, scale, scale);
   }
}
