package lotr.client.render.entity;

import lotr.common.entity.LOTREntityInvasionSpawner;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class LOTRRenderInvasionSpawner extends Render {
   protected ResourceLocation func_110775_a(Entity entity) {
      return TextureMap.field_110576_c;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityInvasionSpawner spawner = (LOTREntityInvasionSpawner)entity;
      GL11.glPushMatrix();
      GL11.glEnable(32826);
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      float rotation = this.interpolateRotation(spawner.prevSpawnerSpin, spawner.spawnerSpin, f1);
      float scale = 1.5F;
      GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
      GL11.glScalef(scale, scale, scale);
      ItemStack item = spawner.getInvasionItem();
      this.field_76990_c.field_78721_f.renderItem(this.field_76990_c.field_78734_h, item, 0, ItemRenderType.EQUIPPED);
      GL11.glDisable(32826);
      GL11.glPopMatrix();
   }

   private float interpolateRotation(float prevRotation, float newRotation, float tick) {
      float interval;
      for(interval = newRotation - prevRotation; interval < -180.0F; interval += 360.0F) {
      }

      while(interval >= 180.0F) {
         interval -= 360.0F;
      }

      return prevRotation + tick * interval;
   }
}
