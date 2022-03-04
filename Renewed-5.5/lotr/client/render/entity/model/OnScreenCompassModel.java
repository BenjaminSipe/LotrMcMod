package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class OnScreenCompassModel extends Model {
   private ModelRenderer compass;

   public OnScreenCompassModel() {
      super(RenderType::func_228640_c_);
      this.field_78090_t = 32;
      this.field_78089_u = 32;
      this.compass = new ModelRenderer(this, 0, 0);
      this.compass.func_78793_a(0.0F, 0.0F, 0.0F);
      this.compass.func_228300_a_(-16.0F, 0.0F, -16.0F, 32.0F, 0.0F, 32.0F);
   }

   public void func_225598_a_(MatrixStack mat, IVertexBuilder buf, int light, int overlay, float r, float g, float b, float a) {
      this.compass.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
   }
}
