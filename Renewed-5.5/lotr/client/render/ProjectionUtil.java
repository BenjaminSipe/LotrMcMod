package lotr.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class ProjectionUtil {
   public static Matrix4f getProjection(Minecraft mc, float partialTicks, float farField) {
      GameRenderer gr = mc.field_71460_t;
      MatrixStack projectStack = new MatrixStack();
      projectStack.func_227866_c_().func_227870_a_().func_226591_a_();
      double fov = gr.func_215311_a(gr.func_215316_n(), partialTicks, true);
      float nearField = 0.05F;
      projectStack.func_227866_c_().func_227870_a_().func_226595_a_(Matrix4f.func_195876_a(fov, (float)mc.func_228018_at_().func_198109_k() / (float)mc.func_228018_at_().func_198091_l(), nearField, farField));
      gr.func_228380_a_(projectStack, partialTicks);
      if (mc.field_71474_y.field_74336_f) {
         gr.func_228383_b_(projectStack, partialTicks);
      }

      float f = MathHelper.func_219799_g(partialTicks, mc.field_71439_g.field_71080_cy, mc.field_71439_g.field_71086_bY);
      if (f > 0.0F) {
         int i = 20;
         if (mc.field_71439_g.func_70644_a(Effects.field_76431_k)) {
            i = 7;
         }

         int updateCount = gr.field_78529_t;
         float f1 = 5.0F / (f * f + 5.0F) - f * 0.04F;
         f1 *= f1;
         Vector3f axis = new Vector3f(0.0F, MathHelper.field_180189_a / 2.0F, MathHelper.field_180189_a / 2.0F);
         projectStack.func_227863_a_(axis.func_229187_a_(((float)updateCount + partialTicks) * (float)i));
         projectStack.func_227862_a_(1.0F / f1, 1.0F, 1.0F);
         float f2 = -((float)updateCount + partialTicks) * (float)i;
         projectStack.func_227863_a_(axis.func_229187_a_(f2));
      }

      return projectStack.func_227866_c_().func_227870_a_();
   }
}
