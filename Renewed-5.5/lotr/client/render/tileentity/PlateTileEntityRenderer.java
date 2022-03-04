package lotr.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Random;
import lotr.client.render.model.PlateFoodModels;
import lotr.common.tileentity.PlateTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.util.LazyOptional;

public class PlateTileEntityRenderer extends TileEntityRenderer {
   private Random rand = new Random(42984194L);

   public PlateTileEntityRenderer(TileEntityRendererDispatcher disp) {
      super(disp);
   }

   public void render(PlateTileEntity plate, float partialTicks, MatrixStack matStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
      ItemStack plateItem = plate.getFoodItem();
      LazyOptional fallingData = plate.getFallingDataForRender();
      float plateFallOffset = (Float)fallingData.map((d) -> {
         return d.getPlateOffsetY(partialTicks);
      }).orElse(0.0F);
      if (!plateItem.func_190926_b()) {
         matStack.func_227860_a_();
         RenderSystem.disableCull();
         RenderSystem.enableRescaleNormal();
         matStack.func_227861_a_(0.5D, 0.0D, 0.5D);
         ItemRenderer itemRenderer = Minecraft.func_71410_x().func_175599_af();
         PlateFoodModels.ModelAndHeight specialItemModel = PlateFoodModels.INSTANCE.getSpecialModel(plateItem.func_77973_b());
         float itemHeight = getItemHeight(plateItem, specialItemModel, true);
         int foods = plateItem.func_190916_E();
         float lowerOffset = 0.125F;

         for(int foodSlot = 0; foodSlot < foods; ++foodSlot) {
            matStack.func_227860_a_();
            float offset = (Float)fallingData.map((d) -> {
               return d.getFoodOffsetY(foodSlot, partialTicks);
            }).orElse(0.0F);
            offset = Math.max(offset, lowerOffset);
            matStack.func_227861_a_(0.0D, (double)offset, 0.0D);
            lowerOffset = offset + itemHeight;
            this.rand.setSeed((long)(plate.func_174877_v().func_177958_n() * 3129871) ^ (long)plate.func_174877_v().func_177952_p() * 116129781L ^ (long)plate.func_174877_v().func_177956_o() + (long)foodSlot * 5930563L);
            matStack.func_227861_a_(0.0D, 0.03125D, 0.0D);
            float rotation = this.rand.nextFloat() * 360.0F;
            matStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(rotation));
            matStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
            matStack.func_227862_a_(0.5625F, 0.5625F, 0.5625F);
            if (specialItemModel == null) {
               itemRenderer.func_229110_a_(plateItem, TransformType.FIXED, combinedLight, combinedOverlay, matStack, buffer);
            } else {
               IBakedModel model = itemRenderer.func_175037_a().func_178083_a().getModel(specialItemModel.modelRes);
               matStack.func_227860_a_();
               model = ForgeHooksClient.handleCameraTransforms(matStack, model, TransformType.FIXED, false);
               matStack.func_227861_a_(-0.5D, -0.5D, -0.5D);
               matStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-90.0F));
               matStack.func_227861_a_(0.0D, -0.55D, 0.0D);
               RenderType renderType = RenderType.func_228643_e_();
               IVertexBuilder vertBuilder = ItemRenderer.func_229113_a_(buffer, renderType, true, plateItem.func_77962_s());
               Random itemRenderRand = new Random();
               long seed = 42L;
               Direction[] var25 = Direction.values();
               int var26 = var25.length;

               for(int var27 = 0; var27 < var26; ++var27) {
                  Direction direction = var25[var27];
                  itemRenderRand.setSeed(seed);
                  itemRenderer.func_229112_a_(matStack, vertBuilder, model.getQuads((BlockState)null, direction, itemRenderRand, (IModelData)null), plateItem, combinedLight, combinedOverlay);
               }

               itemRenderRand.setSeed(seed);
               itemRenderer.func_229112_a_(matStack, vertBuilder, model.getQuads((BlockState)null, (Direction)null, itemRenderRand, (IModelData)null), plateItem, combinedLight, combinedOverlay);
               matStack.func_227865_b_();
            }

            matStack.func_227865_b_();
         }

         RenderSystem.disableRescaleNormal();
         RenderSystem.enableCull();
         matStack.func_227865_b_();
      }

   }

   public static float getItemHeight(ItemStack plateItem) {
      return getItemHeight(plateItem, (PlateFoodModels.ModelAndHeight)null, false);
   }

   private static float getItemHeight(ItemStack plateItem, PlateFoodModels.ModelAndHeight suppliedModel, boolean useSupplied) {
      if (!useSupplied) {
         suppliedModel = PlateFoodModels.INSTANCE.getSpecialModel(plateItem.func_77973_b());
      }

      float itemHeight = suppliedModel != null ? suppliedModel.height : 0.03125F;
      return itemHeight;
   }
}
