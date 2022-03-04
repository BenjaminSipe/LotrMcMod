package lotr.client.render.entity;

import lotr.client.model.LOTRModelLion;
import lotr.client.model.LOTRModelLionOld;
import lotr.common.entity.animal.LOTREntityLionBase;
import lotr.common.entity.animal.LOTREntityLioness;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderLion extends RenderLiving {
   public static ResourceLocation textureLion = new ResourceLocation("lotr:mob/lion/lion.png");
   public static ResourceLocation textureLioness = new ResourceLocation("lotr:mob/lion/lioness.png");
   private static ResourceLocation textureTicket = new ResourceLocation("lotr:mob/lion/ticketlion.png");
   private static ModelBase lionModel = new LOTRModelLion();
   private static ModelBase lionModelOld = new LOTRModelLionOld();

   public LOTRRenderLion() {
      super(lionModel, 0.5F);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityLionBase lion = (LOTREntityLionBase)entity;
      if (isTicket(lion)) {
         return textureTicket;
      } else {
         return lion instanceof LOTREntityLioness ? textureLioness : textureLion;
      }
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityLionBase lion = (LOTREntityLionBase)entity;
      if (isTicket(lion)) {
         this.field_77045_g = lionModelOld;
      } else {
         this.field_77045_g = lionModel;
      }

      super.func_76986_a(entity, d, d1, d2, f, f1);
   }

   private static boolean isTicket(LOTREntityLionBase lion) {
      return lion.func_94056_bM() && lion.func_94057_bL().equalsIgnoreCase("ticket lion");
   }
}
