package lotr.client.render.entity;

import java.awt.Color;
import java.util.Random;
import lotr.client.model.LOTRModelHuman;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderSaruman extends LOTRRenderBiped {
   private static ResourceLocation skin = new ResourceLocation("lotr:mob/char/saruman.png");
   private Random rand = new Random();
   private boolean twitch;

   public LOTRRenderSaruman() {
      super(new LOTRModelHuman(), 0.5F);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      return skin;
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      super.func_77041_b(entity, f);
      if (entity.field_70173_aa % 60 == 0) {
         this.twitch = !this.twitch;
      }

      if (this.twitch) {
         GL11.glRotatef(this.rand.nextFloat() * 40.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(this.rand.nextFloat() * 40.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(this.rand.nextFloat() * 40.0F, 1.0F, 0.0F, 0.0F);
         GL11.glTranslatef(this.rand.nextFloat() * 0.5F, this.rand.nextFloat() * 0.5F, this.rand.nextFloat() * 0.5F);
      }

      int i = entity.field_70173_aa % 360;
      float hue = (float)i / 360.0F;
      Color color = Color.getHSBColor(hue, 1.0F, 1.0F);
      float r = (float)color.getRed() / 255.0F;
      float g = (float)color.getGreen() / 255.0F;
      float b = (float)color.getBlue() / 255.0F;
      GL11.glColor3f(r, g, b);
   }
}
