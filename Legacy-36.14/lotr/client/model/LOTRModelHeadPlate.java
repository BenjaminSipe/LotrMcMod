package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import lotr.client.render.LOTRRenderBlocks;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTRPlateFallingInfo;
import lotr.common.item.LOTRItemPlate;
import lotr.common.tileentity.LOTRTileEntityPlate;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRModelHeadPlate extends LOTRModelHuman {
   private RenderBlocks blockRenderer = new RenderBlocks();
   private LOTRTileEntityPlate plateTE = new LOTRTileEntityPlate();
   private Block plateBlock;

   public void setPlateItem(ItemStack itemstack) {
      if (itemstack.func_77973_b() instanceof LOTRItemPlate) {
         this.plateBlock = ((LOTRItemPlate)itemstack.func_77973_b()).plateBlock;
      } else {
         this.plateBlock = LOTRMod.plateBlock;
      }

   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float tick = LOTRTickHandlerClient.renderTick;
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      LOTRPlateFallingInfo fallingInfo = entity == null ? null : LOTRPlateFallingInfo.getOrCreateFor(entity, false);
      float fallOffset = fallingInfo == null ? 0.0F : fallingInfo.getPlateOffsetY(tick);
      GL11.glEnable(32826);
      GL11.glPushMatrix();
      GL11.glScalef(1.0F, -1.0F, 1.0F);
      GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.0F, 1.0F - this.field_78116_c.field_78797_d / 16.0F, 0.0F);
      GL11.glTranslatef(0.0F, fallOffset * 0.5F, 0.0F);
      Minecraft.func_71410_x().func_110434_K().func_110577_a(TextureMap.field_110575_b);
      World world = entity == null ? LOTRMod.proxy.getClientWorld() : entity.field_70170_p;
      LOTRRenderBlocks.renderEntityPlate(world, 0, 0, 0, this.plateBlock, this.blockRenderer);
      if (entity instanceof EntityLivingBase) {
         EntityLivingBase living = (EntityLivingBase)entity;
         ItemStack heldItem = living.func_70694_bm();
         if (heldItem != null && LOTRTileEntityPlate.isValidFoodItem(heldItem)) {
            ItemStack heldItemMinusOne = heldItem.func_77946_l();
            --heldItemMinusOne.field_77994_a;
            if (heldItemMinusOne.field_77994_a > 0) {
               this.plateTE.setFoodItem(heldItemMinusOne);
               this.plateTE.plateFallInfo = fallingInfo;
               TileEntityRendererDispatcher.field_147556_a.func_147549_a(this.plateTE, -0.5D, -0.5D, -0.5D, tick);
               this.plateTE.plateFallInfo = null;
               GL11.glDisable(2884);
            }
         }
      }

      GL11.glPopMatrix();
      GL11.glDisable(32826);
   }
}
