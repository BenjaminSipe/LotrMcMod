package lotr.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.util.LOTRClientUtil;
import lotr.common.tileentity.CustomWaypointMarkerTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class CustomWaypointMarkerTileEntityRenderer extends TileEntityRenderer {
   public CustomWaypointMarkerTileEntityRenderer(TileEntityRendererDispatcher disp) {
      super(disp);
   }

   public void render(CustomWaypointMarkerTileEntity marker, float partialTicks, MatrixStack matStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
      String name = marker.getWaypointName();
      if (name != null && this.shouldRenderName(marker)) {
         matStack.func_227860_a_();
         matStack.func_227861_a_(0.5D, 0.5D, 0.5D);
         this.renderName(name, marker.getFacingDirection(), matStack, buffer, combinedLight);
         matStack.func_227865_b_();
      }

   }

   private boolean shouldRenderName(CustomWaypointMarkerTileEntity marker) {
      if (Minecraft.func_71382_s()) {
         BlockPos pos = marker.func_174877_v();
         double dSq = this.field_228858_b_.field_217666_g.func_216773_g().func_70092_e((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D);
         float renderRange = 32.0F;
         return dSq < (double)(renderRange * renderRange);
      } else {
         return false;
      }
   }

   private void renderName(String name, Direction facing, MatrixStack matStack, IRenderTypeBuffer buffer, int packedLight) {
      Minecraft mc = Minecraft.func_71410_x();
      float height = 0.45F;
      float facingOffset = -0.3F;
      float rotationDeg = 180.0F - facing.func_185119_l();
      matStack.func_227860_a_();
      matStack.func_227861_a_((double)((float)facing.func_82601_c() * facingOffset), (double)height, (double)((float)facing.func_82599_e() * facingOffset));
      matStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(rotationDeg));
      float scale = 0.025F;
      matStack.func_227862_a_(-scale, -scale, scale);
      Matrix4f mat = matStack.func_227866_c_().func_227870_a_();
      float alpha = mc.field_71474_y.func_216840_a(0.25F);
      int alphaI = LOTRClientUtil.getAlphaInt(alpha) << 24;
      FontRenderer font = this.field_228858_b_.func_147548_a();
      int xOffset = -font.func_78256_a(name) / 2;
      font.func_228079_a_(name, (float)xOffset, 0.0F, 553648127, false, mat, buffer, true, alphaI, packedLight);
      font.func_228079_a_(name, (float)xOffset, 0.0F, -1, false, mat, buffer, false, 0, packedLight);
      matStack.func_227865_b_();
   }
}
