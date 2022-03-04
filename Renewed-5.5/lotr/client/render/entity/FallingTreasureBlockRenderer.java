package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Iterator;
import java.util.Random;
import lotr.common.entity.item.FallingTreasureBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;

public class FallingTreasureBlockRenderer extends EntityRenderer {
   public FallingTreasureBlockRenderer(EntityRendererManager mgr) {
      super(mgr);
      this.field_76989_e = 0.5F;
   }

   public void render(FallingTreasureBlockEntity entity, float yaw, float tick, MatrixStack matStack, IRenderTypeBuffer buf, int packedLight) {
      BlockState state = entity.getFallTile();
      if (state.func_185901_i() == BlockRenderType.MODEL) {
         World world = entity.func_130014_f_();
         if (state != world.func_180495_p(entity.func_233580_cy_()) && state.func_185901_i() != BlockRenderType.INVISIBLE) {
            matStack.func_227860_a_();
            BlockPos pos = new BlockPos(entity.func_226277_ct_(), entity.func_174813_aQ().field_72337_e, entity.func_226281_cx_());
            matStack.func_227861_a_(-0.5D, 0.0D, -0.5D);
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.func_71410_x().func_175602_ab();
            Iterator var11 = RenderType.func_228661_n_().iterator();

            while(var11.hasNext()) {
               RenderType type = (RenderType)var11.next();
               if (RenderTypeLookup.canRenderInLayer(state, type)) {
                  ForgeHooksClient.setRenderLayer(type);
                  blockrendererdispatcher.func_175019_b().func_228802_a_(world, blockrendererdispatcher.func_184389_a(state), state, pos, matStack, buf.getBuffer(type), false, new Random(), state.func_209533_a(entity.getOrigin()), OverlayTexture.field_229196_a_);
               }
            }

            ForgeHooksClient.setRenderLayer((RenderType)null);
            matStack.func_227865_b_();
            super.func_225623_a_(entity, yaw, tick, matStack, buf, packedLight);
         }
      }

   }

   public ResourceLocation getEntityTexture(FallingTreasureBlockEntity entity) {
      return AtlasTexture.field_110575_b;
   }
}
