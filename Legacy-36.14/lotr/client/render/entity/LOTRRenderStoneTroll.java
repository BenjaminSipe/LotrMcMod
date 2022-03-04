package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.item.LOTREntityStoneTroll;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderStoneTroll extends Render {
   private static ResourceLocation texture = new ResourceLocation("lotr:mob/troll/stone.png");
   private static LOTRModelTroll model = new LOTRModelTroll();
   private static LOTRModelTroll shirtModel = new LOTRModelTroll(1.0F, 0);
   private static LOTRModelTroll trousersModel = new LOTRModelTroll(0.75F, 1);

   protected ResourceLocation func_110775_a(Entity entity) {
      return texture;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glTranslatef((float)d, (float)d1 + 1.5F, (float)d2);
      this.func_110777_b(entity);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      GL11.glRotatef(180.0F - entity.field_70177_z, 0.0F, 1.0F, 0.0F);
      model.func_78088_a(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      this.func_110776_a(LOTRRenderTroll.trollOutfits[((LOTREntityStoneTroll)entity).getTrollOutfit()]);
      shirtModel.func_78088_a(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      trousersModel.func_78088_a(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
   }
}
