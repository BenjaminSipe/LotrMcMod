package lotr.client.render.entity;

import lotr.common.entity.projectile.LOTREntityDart;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderDart extends Render {
   protected ResourceLocation func_110775_a(Entity entity) {
      return TextureMap.field_110576_c;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityDart dart = (LOTREntityDart)entity;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glRotatef(dart.field_70126_B + (dart.field_70177_z - dart.field_70126_B) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(dart.field_70127_C + (dart.field_70125_A - dart.field_70127_C) * f1, 0.0F, 0.0F, 1.0F);
      GL11.glEnable(32826);
      float f2 = (float)dart.shake - f1;
      float scale;
      if (f2 > 0.0F) {
         scale = -MathHelper.func_76126_a(f2 * 3.0F) * f2;
         GL11.glRotatef(scale, 0.0F, 0.0F, 1.0F);
      }

      GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(0.0F, -1.0F, 0.0F);
      scale = 0.6F;
      GL11.glScalef(scale, scale, scale);
      GL11.glTranslatef(0.0F, 0.8F, 0.0F);
      ItemStack itemstack = dart.getProjectileItem();
      IIcon icon = itemstack.func_77954_c();
      Tessellator tessellator = Tessellator.field_78398_a;
      float minU = icon.func_94209_e();
      float maxU = icon.func_94212_f();
      float minV = icon.func_94206_g();
      float maxV = icon.func_94210_h();
      int width = icon.func_94211_a();
      int height = icon.func_94211_a();
      this.func_110776_a(this.func_110775_a(dart));
      ItemRenderer.func_78439_a(tessellator, maxU, minV, minU, maxV, width, height, 0.0625F);
      GL11.glDisable(32826);
      GL11.glPopMatrix();
   }
}
