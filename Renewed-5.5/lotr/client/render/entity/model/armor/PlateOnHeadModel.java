package lotr.client.render.entity.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.client.event.LOTRTickHandlerClient;
import lotr.common.entity.capabilities.PlateFallingDataProvider;
import lotr.common.init.LOTRBlocks;
import lotr.common.item.PlateItem;
import lotr.common.tileentity.PlateTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.util.LazyOptional;

public class PlateOnHeadModel extends SpecialArmorModel implements ItemStackDependentModel, WearerDependentArmorModel {
   private Block plateBlock;
   private LazyOptional fallingData = LazyOptional.empty();
   private ItemStack currentHeldItem;
   private final PlateTileEntity fakePlateTE;

   public PlateOnHeadModel(BipedModel referenceBipedModel) {
      super(referenceBipedModel, 0.0F);
      this.currentHeldItem = ItemStack.field_190927_a;
      this.fakePlateTE = new PlateTileEntity();
   }

   public void setModelItem(ItemStack stack) {
      if (stack.func_77973_b() instanceof PlateItem) {
         this.plateBlock = ((PlateItem)stack.func_77973_b()).getBlock();
      } else {
         this.plateBlock = (Block)LOTRBlocks.FINE_PLATE.get();
      }

   }

   public void acceptWearingEntity(LivingEntity entity) {
      this.fallingData = entity.getCapability(PlateFallingDataProvider.CAPABILITY);
      this.currentHeldItem = entity.func_184614_ca();
   }

   public void func_225598_a_(MatrixStack matStack, IVertexBuilder buf, int packedLight, int packedOverlay, float r, float g, float b, float a) {
      float partialTick = LOTRTickHandlerClient.renderPartialTick;
      float fallingOffset = (Float)this.fallingData.map((d) -> {
         return d.getPlateOffsetY(partialTick);
      }).orElse(0.0F);
      IRenderTypeBuffer renderTypeBuf = Minecraft.func_71410_x().func_228019_au_().func_228487_b_();
      RenderSystem.disableCull();
      matStack.func_227860_a_();
      this.field_78116_c.func_228307_a_(matStack);
      matStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
      matStack.func_227861_a_(-0.5D, 0.45D + (double)fallingOffset * 0.5D, -0.5D);
      this.renderPlateBlockModel(matStack, renderTypeBuf, packedLight, packedOverlay);
      if (PlateTileEntity.isValidFoodItem(this.currentHeldItem)) {
         ItemStack heldItemMinusOne = this.currentHeldItem.func_77946_l();
         heldItemMinusOne.func_190918_g(1);
         if (!heldItemMinusOne.func_190926_b()) {
            this.fakePlateTE.setFoodItem(heldItemMinusOne);
            this.fakePlateTE.setFallingDataForRender(this.fallingData);
            TileEntityRendererDispatcher.field_147556_a.func_147547_b(this.fakePlateTE).func_225616_a_(this.fakePlateTE, partialTick, matStack, renderTypeBuf, packedLight, packedOverlay);
            this.fakePlateTE.setFoodItem((ItemStack)null);
         }
      }

      matStack.func_227865_b_();
      RenderSystem.enableCull();
      this.fakePlateTE.setFallingDataForRender(this.fallingData = LazyOptional.empty());
   }

   private void renderPlateBlockModel(MatrixStack matStack, IRenderTypeBuffer renderTypeBuf, int light, int overlay) {
      BlockState state = this.plateBlock.func_176223_P();
      Minecraft.func_71410_x().func_175602_ab().renderBlock(state, matStack, renderTypeBuf, light, overlay, EmptyModelData.INSTANCE);
   }
}
