package lotr.client.render.entity;

import lotr.client.model.LOTRModelSauron;
import lotr.common.entity.npc.LOTREntitySauron;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderSauron extends RenderBiped {
   private static ResourceLocation skin = new ResourceLocation("lotr:mob/char/sauron.png");

   public LOTRRenderSauron() {
      super(new LOTRModelSauron(), 0.5F);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return skin;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntitySauron sauron = (LOTREntitySauron)entity;
      if (sauron.getIsUsingMace()) {
         this.field_82423_g.field_78120_m = this.field_82425_h.field_78120_m = this.field_77071_a.field_78120_m = 3;
         this.field_82423_g.field_78118_o = this.field_82425_h.field_78118_o = this.field_77071_a.field_78118_o = true;
      }

      super.func_76986_a(entity, d, d1, d2, f, f1);
   }

   protected void func_82422_c() {
      GL11.glTranslatef(0.0F, 0.4375F, 0.0F);
   }
}
