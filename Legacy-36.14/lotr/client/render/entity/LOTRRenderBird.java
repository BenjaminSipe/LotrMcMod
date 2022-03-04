package lotr.client.render.entity;

import java.util.HashMap;
import java.util.Map;
import lotr.client.model.LOTRModelBird;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityCrebain;
import lotr.common.entity.animal.LOTREntityGorcrow;
import lotr.common.entity.animal.LOTREntitySeagull;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBird extends RenderLiving {
   private static Map birdTypeSkins = new HashMap();
   public static boolean renderStolenItem = true;

   public LOTRRenderBird() {
      super(new LOTRModelBird(), 0.2F);
   }

   private LOTRRandomSkins getBirdSkins(String s) {
      LOTRRandomSkins skins = (LOTRRandomSkins)birdTypeSkins.get(s);
      if (skins == null) {
         skins = LOTRRandomSkins.loadSkinsList("lotr:mob/bird/" + s);
         birdTypeSkins.put(s, skins);
      }

      return skins;
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityBird bird = (LOTREntityBird)entity;
      String type = bird.getBirdTextureDir();
      LOTRRandomSkins skins = this.getBirdSkins(type);
      return skins.getRandomSkin(bird);
   }

   public void func_77041_b(EntityLivingBase entity, float f) {
      float scale;
      if (entity instanceof LOTREntityCrebain) {
         scale = LOTREntityCrebain.CREBAIN_SCALE;
         GL11.glScalef(scale, scale, scale);
      } else if (entity instanceof LOTREntityGorcrow) {
         scale = LOTREntityGorcrow.GORCROW_SCALE;
         GL11.glScalef(scale, scale, scale);
      } else if (entity instanceof LOTREntitySeagull) {
         scale = LOTREntitySeagull.SEAGULL_SCALE;
         GL11.glScalef(scale, scale, scale);
      }

   }

   protected float func_77044_a(EntityLivingBase entity, float f) {
      LOTREntityBird bird = (LOTREntityBird)entity;
      return bird.isBirdStill() && bird.flapTime > 0 ? (float)bird.flapTime - f : super.func_77044_a(entity, f);
   }

   protected void func_77029_c(EntityLivingBase entity, float f) {
      LOTREntityBird bird = (LOTREntityBird)entity;
      if (renderStolenItem) {
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         ItemStack stolenItem = bird.getStolenItem();
         if (stolenItem != null) {
            GL11.glPushMatrix();
            ((LOTRModelBird)this.field_77045_g).head.func_78794_c(0.0625F);
            GL11.glTranslatef(0.05F, 1.4F, -0.1F);
            float scale = 0.25F;
            GL11.glScalef(scale, scale, scale);
            this.field_76990_c.field_78721_f.func_78443_a(entity, stolenItem, 0);
            GL11.glPopMatrix();
         }
      }

   }
}
