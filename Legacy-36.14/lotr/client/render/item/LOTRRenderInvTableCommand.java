package lotr.client.render.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class LOTRRenderInvTableCommand implements IItemRenderer {
   public boolean handleRenderType(ItemStack itemstack, ItemRenderType type) {
      return type == ItemRenderType.INVENTORY;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper) {
      return true;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      Block block = Block.func_149634_a(itemstack.func_77973_b());
      int meta = itemstack.func_77960_j();
      RenderBlocks rb = (RenderBlocks)data[0];
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, -0.18F, 0.0F);
      float scale = 0.6F;
      GL11.glScalef(scale, scale, scale);
      rb.func_147800_a(block, meta, 1.0F);
      GL11.glPopMatrix();
   }
}
