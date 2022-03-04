package lotr.client.render.entity;

import java.util.HashMap;
import java.util.Map;
import lotr.client.model.LOTRModelFish;
import lotr.common.entity.animal.LOTREntityFish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderFish extends RenderLiving {
   private static Map fishTypeSkins = new HashMap();

   public LOTRRenderFish() {
      super(new LOTRModelFish(), 0.0F);
   }

   private LOTRRandomSkins getFishSkins(String s) {
      LOTRRandomSkins skins = (LOTRRandomSkins)fishTypeSkins.get(s);
      if (skins == null) {
         skins = LOTRRandomSkins.loadSkinsList("lotr:mob/fish/" + s);
         fishTypeSkins.put(s, skins);
      }

      return skins;
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityFish fish = (LOTREntityFish)entity;
      String type = fish.getFishTextureDir();
      LOTRRandomSkins skins = this.getFishSkins(type);
      return skins.getRandomSkin(fish);
   }

   public void func_77041_b(EntityLivingBase entity, float f) {
      if (!entity.func_70090_H()) {
         GL11.glTranslatef(0.0F, -0.05F, 0.0F);
         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
      }

   }

   protected float func_77044_a(EntityLivingBase entity, float f) {
      return super.func_77044_a(entity, f);
   }
}
