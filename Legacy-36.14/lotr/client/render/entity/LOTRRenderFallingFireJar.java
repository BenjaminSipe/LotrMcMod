package lotr.client.render.entity;

import lotr.common.block.LOTRBlockRhunFireJar;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRRenderFallingFireJar extends RenderFallingBlock {
   private static RenderBlocks renderBlocks = new RenderBlocks();

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      EntityFallingBlock falling = (EntityFallingBlock)entity;
      World world = falling.func_145807_e();
      Block block = falling.func_145805_f();
      int i = MathHelper.func_76128_c(falling.field_70165_t);
      int j = MathHelper.func_76128_c(falling.field_70163_u);
      int k = MathHelper.func_76128_c(falling.field_70161_v);
      if (block instanceof LOTRBlockRhunFireJar) {
         if (block != world.func_147439_a(i, j, k)) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d, (float)d1, (float)d2);
            this.func_110777_b(entity);
            GL11.glDisable(2896);
            renderBlocks.field_147845_a = world;
            Tessellator tessellator = Tessellator.field_78398_a;
            tessellator.func_78382_b();
            tessellator.func_78373_b((double)((float)(-i) - 0.5F), (double)((float)(-j) - 0.5F), (double)((float)(-k) - 0.5F));
            renderBlocks.func_147805_b(block, i, j, k);
            tessellator.func_78373_b(0.0D, 0.0D, 0.0D);
            tessellator.func_78381_a();
            GL11.glEnable(2896);
            GL11.glPopMatrix();
         }
      } else {
         super.func_76986_a(entity, d, d1, d2, f, f1);
      }

   }
}
