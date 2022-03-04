package lotr.client.render.entity;

import cpw.mods.fml.common.FMLLog;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
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

public class LOTRRenderThrowingAxe extends Render {
   protected ResourceLocation func_110775_a(Entity entity) {
      return TextureMap.field_110576_c;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityThrowingAxe axe = (LOTREntityThrowingAxe)entity;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      if (!axe.inGround) {
         GL11.glTranslatef(0.0F, 0.5F, 0.0F);
      }

      GL11.glRotatef(axe.field_70126_B + (axe.field_70177_z - axe.field_70126_B) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
      if (!axe.inGround) {
         GL11.glRotatef(axe.field_70125_A + (axe.inGround ? 0.0F : 45.0F * f1), 0.0F, 0.0F, -1.0F);
      } else {
         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(0.0F, 0.75F, 0.0F);
      }

      GL11.glEnable(32826);
      float f2 = (float)axe.shake - f1;
      if (f2 > 0.0F) {
         float f3 = -MathHelper.func_76126_a(f2 * 3.0F) * f2;
         GL11.glRotatef(f3, 0.0F, 0.0F, 1.0F);
      }

      GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
      ItemStack axeItem = axe.getProjectileItem();
      IIcon icon = axeItem.func_77954_c();
      if (icon == null) {
         FMLLog.severe("Error rendering throwing axe: no icon for " + axeItem.toString(), new Object[0]);
         GL11.glPopMatrix();
      } else {
         this.func_110777_b(entity);
         Tessellator tessellator = Tessellator.field_78398_a;
         ItemRenderer.func_78439_a(tessellator, icon.func_94212_f(), icon.func_94206_g(), icon.func_94209_e(), icon.func_94210_h(), icon.func_94211_a(), icon.func_94216_b(), 0.0625F);
         GL11.glDisable(32826);
         GL11.glPopMatrix();
      }
   }
}
