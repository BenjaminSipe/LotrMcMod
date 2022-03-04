package lotr.client.render.entity;

import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public abstract class LOTRRenderRugBase extends Render {
   private ModelBase rugModel;

   public LOTRRenderRugBase(ModelBase m) {
      this.rugModel = m;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityRugBase rug = (LOTREntityRugBase)entity;
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      this.func_110777_b(rug);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      GL11.glRotatef(180.0F - rug.field_70177_z, 0.0F, 1.0F, 0.0F);
      this.preRenderCallback();
      this.rugModel.func_78088_a(rug, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
   }

   protected void preRenderCallback() {
   }
}
