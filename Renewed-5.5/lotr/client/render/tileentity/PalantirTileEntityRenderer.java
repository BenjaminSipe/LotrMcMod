package lotr.client.render.tileentity;

import com.google.common.math.IntMath;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.client.render.tileentity.model.PalantirModel;
import lotr.common.tileentity.PalantirTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class PalantirTileEntityRenderer extends TileEntityRenderer {
   private static final ResourceLocation TEXTURE = new ResourceLocation("lotr", "textures/entity/palantir/palantir.png");
   private static final int FRAMES = 24;
   private static final int FRAME_LENGTH = 3;
   private static final ResourceLocation[] ANIMATED_INNER_TEXTURE = (ResourceLocation[])Util.func_200696_a(new ResourceLocation[24], (arr) -> {
      for(int i = 0; i < arr.length; ++i) {
         arr[i] = new ResourceLocation("lotr", String.format("textures/entity/palantir/inner_%d.png", i));
      }

   });
   private final PalantirModel innerOrbModel = new PalantirModel(true);
   private final PalantirModel othersModel = new PalantirModel(false);

   public PalantirTileEntityRenderer(TileEntityRendererDispatcher disp) {
      super(disp);
   }

   public void render(PalantirTileEntity palantir, float partialTicks, MatrixStack matStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
      int animationTick = palantir.getAnimationTick();
      int frame = IntMath.mod(animationTick / 3, 24);
      ResourceLocation innerTexture = ANIMATED_INNER_TEXTURE[frame];
      matStack.func_227860_a_();
      matStack.func_227861_a_(0.5D, 0.5D, 0.5D);
      matStack.func_227862_a_(-1.0F, -1.0F, 1.0F);
      IVertexBuilder vb = buffer.getBuffer(RenderType.func_228640_c_(innerTexture));
      this.innerOrbModel.func_225598_a_(matStack, vb, this.getInnerOrbLight(combinedLight), combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
      vb = buffer.getBuffer(RenderType.func_228644_e_(TEXTURE));
      this.othersModel.func_225598_a_(matStack, vb, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
      matStack.func_227865_b_();
   }

   private int getInnerOrbLight(int combinedLight) {
      int blockLight = LightTexture.func_228450_a_(combinedLight);
      int skyLight = LightTexture.func_228454_b_(combinedLight);
      return LightTexture.func_228451_a_(Math.max(blockLight, 15), skyLight);
   }
}
