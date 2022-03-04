package lotr.client.render.entity;

import lotr.common.block.LOTRBlockTreasurePile;
import lotr.common.entity.item.LOTREntityFallingTreasure;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRRenderFallingCoinPile extends Render {
   private static final RenderBlocks blockRenderer = new RenderBlocks();

   public LOTRRenderFallingCoinPile() {
      this.field_76989_e = 0.5F;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityFallingTreasure fallingCoin = (LOTREntityFallingTreasure)entity;
      World world = fallingCoin.field_70170_p;
      Block block = fallingCoin.theBlock;
      int meta = fallingCoin.theBlockMeta;
      int i = MathHelper.func_76128_c(fallingCoin.field_70165_t);
      int j = MathHelper.func_76128_c(fallingCoin.field_70163_u);
      int k = MathHelper.func_76128_c(fallingCoin.field_70161_v);
      if (block != null && block != world.func_147439_a(i, j, k)) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)d, (float)d1, (float)d2);
         this.func_110777_b(fallingCoin);
         GL11.glDisable(2896);
         LOTRBlockTreasurePile.setTreasureBlockBounds(block, meta);
         blockRenderer.func_147775_a(block);
         blockRenderer.func_147749_a(block, world, i, j, k, meta);
         GL11.glEnable(2896);
         GL11.glPopMatrix();
      }

   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return TextureMap.field_110575_b;
   }
}
