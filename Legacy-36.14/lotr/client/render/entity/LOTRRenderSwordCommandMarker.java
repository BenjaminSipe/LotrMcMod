package lotr.client.render.entity;

import lotr.client.fx.LOTREntitySwordCommandMarker;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class LOTRRenderSwordCommandMarker extends Render {
   protected ResourceLocation func_110775_a(Entity entity) {
      return TextureMap.field_110576_c;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntitySwordCommandMarker marker = (LOTREntitySwordCommandMarker)entity;
      GL11.glPushMatrix();
      GL11.glEnable(32826);
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      float rotation = -this.field_76990_c.field_78734_h.field_70177_z;
      GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(135.0F, 0.0F, 0.0F, 1.0F);
      float scale = 1.2F;
      GL11.glTranslatef(-0.75F * scale, 0.0F, 0.03125F * scale);
      GL11.glScalef(scale, scale, scale);
      ItemStack item = new ItemStack(LOTRMod.commandSword);
      GL11.glTranslatef(0.9375F, 0.0625F, 0.0F);
      GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
      this.field_76990_c.field_78721_f.renderItem(this.field_76990_c.field_78734_h, item, 0, ItemRenderType.EQUIPPED);
      GL11.glDisable(32826);
      GL11.glPopMatrix();
   }
}
