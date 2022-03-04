package lotr.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.render.entity.model.CaracalModel;
import lotr.common.entity.animal.CaracalEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class CaracalCollarLayer extends LayerRenderer {
   private static final ResourceLocation CARACAL_COLLAR = new ResourceLocation("lotr", "textures/entity/caracal/caracal_collar.png");
   private final CaracalModel collarModel = new CaracalModel(0.01F);

   public CaracalCollarLayer(IEntityRenderer renderer) {
      super(renderer);
   }

   public void render(MatrixStack matStack, IRenderTypeBuffer buf, int packedLight, CaracalEntity caracal, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (caracal.func_70909_n()) {
         float[] collarRgb = caracal.func_213414_ei().func_193349_f();
         func_229140_a_(this.func_215332_c(), this.collarModel, CARACAL_COLLAR, matStack, buf, packedLight, caracal, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, collarRgb[0], collarRgb[1], collarRgb[2]);
      }

   }
}
