package lotr.client.render.entity;

import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityBarrel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderEntityBarrel extends Render {
   private ItemStack barrelItem;

   public LOTRRenderEntityBarrel() {
      this.barrelItem = new ItemStack(LOTRMod.barrel);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return TextureMap.field_110575_b;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityBarrel barrel = (LOTREntityBarrel)entity;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1 + 0.5F, (float)d2);
      GL11.glRotatef(180.0F - f, 0.0F, 1.0F, 0.0F);
      float f2 = (float)barrel.getTimeSinceHit() - f1;
      float f3 = barrel.getDamageTaken() - f1;
      if (f3 < 0.0F) {
         f3 = 0.0F;
      }

      if (f2 > 0.0F) {
         GL11.glRotatef(MathHelper.func_76126_a(f2) * f2 * f3 / 10.0F * (float)barrel.getForwardDirection(), 1.0F, 0.0F, 0.0F);
      }

      this.func_110777_b(barrel);
      GL11.glScalef(1.5F, 1.5F, 1.5F);
      this.field_76990_c.field_78721_f.func_78443_a(this.field_76990_c.field_78734_h, this.barrelItem, 0);
      GL11.glPopMatrix();
   }
}
