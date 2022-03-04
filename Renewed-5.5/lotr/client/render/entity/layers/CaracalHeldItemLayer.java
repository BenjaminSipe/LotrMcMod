package lotr.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.render.entity.model.CaracalModel;
import lotr.common.entity.animal.CaracalEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class CaracalHeldItemLayer extends LayerRenderer {
   public CaracalHeldItemLayer(IEntityRenderer renderer) {
      super(renderer);
   }

   public void render(MatrixStack matStack, IRenderTypeBuffer buf, int packedLight, CaracalEntity caracal, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      boolean child = caracal.func_70631_g_();
      matStack.func_227860_a_();
      if (child) {
         float f = 0.75F;
         matStack.func_227862_a_(0.75F, 0.75F, 0.75F);
         matStack.func_227861_a_(0.0D, 0.5D, 0.209375D);
      }

      ModelRenderer head = ((CaracalModel)this.func_215332_c()).getHead();
      matStack.func_227861_a_((double)(head.field_78800_c / 16.0F), (double)(head.field_78797_d / 16.0F), (double)(head.field_78798_e / 16.0F));
      matStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(netHeadYaw));
      matStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(headPitch));
      if (child) {
         matStack.func_227861_a_(0.06D, 0.11D, -0.4D);
      } else {
         matStack.func_227861_a_(0.06D, 0.02D, -0.4D);
      }

      matStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
      ItemStack heldItem = caracal.func_184582_a(EquipmentSlotType.MAINHAND);
      Minecraft.func_71410_x().func_175597_ag().func_228397_a_(caracal, heldItem, TransformType.GROUND, false, matStack, buf, packedLight);
      matStack.func_227865_b_();
   }
}
