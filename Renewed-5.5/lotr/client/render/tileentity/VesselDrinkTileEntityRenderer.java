package lotr.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.matrix.MatrixStack.Entry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.client.render.model.vessel.VesselDrinkModel;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.item.VesselType;
import lotr.common.tileentity.VesselDrinkTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class VesselDrinkTileEntityRenderer extends TileEntityRenderer {
   private static final float BLOCK_MODEL_SCALE = 0.0625F;

   public VesselDrinkTileEntityRenderer(TileEntityRendererDispatcher disp) {
      super(disp);
   }

   public void render(VesselDrinkTileEntity vessel, float partialTicks, MatrixStack matStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
      if (!vessel.isEmpty()) {
         ItemStack vesselItem = vessel.getVesselItem();
         VesselType vesselType = vessel.getVesselType();
         RenderSystem.enableRescaleNormal();
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         matStack.func_227860_a_();
         matStack.func_227861_a_(0.5D, 0.0D, 0.5D);
         Direction rotateDir = this.getRotationToMatchBlockstateJson((Direction)vessel.func_195044_w().func_177229_b(VesselDrinkBlock.FACING));
         matStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(rotateDir.func_185119_l()));
         matStack.func_227861_a_(-0.5D, 0.0D, -0.5D);
         matStack.func_227862_a_(0.0625F, 0.0625F, 0.0625F);
         TextureAtlasSprite liquidIcon = VesselDrinkModel.getLiquidIconFor(vesselItem);
         RenderType renderType = RenderType.func_228634_a_(liquidIcon.func_229241_m_().func_229223_g_());
         IVertexBuilder vb = buffer.getBuffer(renderType);
         if (vesselType != VesselType.WOODEN_MUG && vesselType != VesselType.CERAMIC_MUG) {
            if (vesselType != VesselType.GOLDEN_GOBLET && vesselType != VesselType.SILVER_GOBLET && vesselType != VesselType.COPPER_GOBLET && vesselType != VesselType.WOODEN_CUP) {
               if (vesselType == VesselType.ALE_HORN || vesselType == VesselType.GOLDEN_ALE_HORN) {
                  this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 9, 9.5F, 6.875F, 12.5F, 9.125F, 6.375F, combinedLight, combinedOverlay);
               }
            } else {
               this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 9, 2.25F, 6.0F, combinedLight, combinedOverlay);
            }
         } else {
            this.renderLiquidSurface(matStack, vb, liquidIcon, 6, 10, 3.0F, 5.25F, combinedLight, combinedOverlay);
         }

         matStack.func_227865_b_();
         RenderSystem.disableBlend();
         RenderSystem.enableCull();
         RenderSystem.disableRescaleNormal();
      }

   }

   private Direction getRotationToMatchBlockstateJson(Direction stateFacing) {
      return stateFacing != Direction.NORTH && stateFacing != Direction.SOUTH ? stateFacing : stateFacing.func_176734_d();
   }

   private void renderLiquidSurface(MatrixStack matStack, IVertexBuilder vb, TextureAtlasSprite icon, int uvMin, int uvMax, float width, float y, int light, int overlay) {
      float halfWidth = width / 2.0F;
      float x0 = 8.0F - halfWidth;
      float z0 = 8.0F - halfWidth;
      float x1 = 8.0F + halfWidth;
      float z1 = 8.0F + halfWidth;
      this.renderLiquidSurface(matStack, vb, icon, uvMin, uvMax, x0, z0, x1, z1, y, light, overlay);
   }

   private void renderLiquidSurface(MatrixStack matStack, IVertexBuilder vb, TextureAtlasSprite icon, int uvMin, int uvMax, float x0, float z0, float x1, float z1, float y, int light, int overlay) {
      float actualUVMin = (float)uvMin / 2.0F;
      float actualUVMax = (float)uvMax / 2.0F;
      float minU = icon.func_94214_a((double)actualUVMin);
      float maxU = icon.func_94214_a((double)actualUVMax);
      float minV = icon.func_94207_b((double)actualUVMin);
      float maxV = icon.func_94207_b((double)actualUVMax);
      Entry last = matStack.func_227866_c_();
      Matrix4f mat = last.func_227870_a_();
      Matrix3f normal = last.func_227872_b_();
      vb.func_227888_a_(mat, x0, y, z1).func_227885_a_(1.0F, 1.0F, 1.0F, 1.0F).func_225583_a_(minU, maxV).func_227891_b_(overlay).func_227886_a_(light).func_227887_a_(normal, 0.0F, 1.0F, 0.0F).func_181675_d();
      vb.func_227888_a_(mat, x1, y, z1).func_227885_a_(1.0F, 1.0F, 1.0F, 1.0F).func_225583_a_(maxU, maxV).func_227891_b_(overlay).func_227886_a_(light).func_227887_a_(normal, 0.0F, 1.0F, 0.0F).func_181675_d();
      vb.func_227888_a_(mat, x1, y, z0).func_227885_a_(1.0F, 1.0F, 1.0F, 1.0F).func_225583_a_(maxU, minV).func_227891_b_(overlay).func_227886_a_(light).func_227887_a_(normal, 0.0F, 1.0F, 0.0F).func_181675_d();
      vb.func_227888_a_(mat, x0, y, z0).func_227885_a_(1.0F, 1.0F, 1.0F, 1.0F).func_225583_a_(minU, minV).func_227891_b_(overlay).func_227886_a_(light).func_227887_a_(normal, 0.0F, 1.0F, 0.0F).func_181675_d();
   }
}
