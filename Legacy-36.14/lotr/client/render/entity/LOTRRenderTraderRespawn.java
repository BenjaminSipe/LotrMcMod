package lotr.client.render.entity;

import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityTraderRespawn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderTraderRespawn extends Render {
   private ItemStack theItemStack;

   protected ResourceLocation func_110775_a(Entity entity) {
      return TextureMap.field_110576_c;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      if (this.theItemStack == null) {
         this.theItemStack = new ItemStack(LOTRMod.silverCoin);
      }

      LOTREntityTraderRespawn traderRespawn = (LOTREntityTraderRespawn)entity;
      this.func_110777_b(traderRespawn);
      GL11.glPushMatrix();
      GL11.glEnable(32826);
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      float rotation = this.interpolateRotation(traderRespawn.prevSpawnerSpin, traderRespawn.spawnerSpin, f1);
      float scale = traderRespawn.getScaleFloat(f1);
      GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F * scale, traderRespawn.getBobbingOffset(f1), 0.03125F * scale);
      GL11.glScalef(scale, scale, scale);
      IIcon icon = this.theItemStack.func_77954_c();
      if (icon == null) {
         icon = ((TextureMap)Minecraft.func_71410_x().func_110434_K().func_110581_b(TextureMap.field_110576_c)).func_110572_b("missingno");
      }

      Tessellator tessellator = Tessellator.field_78398_a;
      float f2 = ((IIcon)icon).func_94209_e();
      float f3 = ((IIcon)icon).func_94212_f();
      float f4 = ((IIcon)icon).func_94206_g();
      float f5 = ((IIcon)icon).func_94210_h();
      ItemRenderer.func_78439_a(tessellator, f3, f4, f2, f5, ((IIcon)icon).func_94211_a(), ((IIcon)icon).func_94216_b(), 0.0625F);
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
