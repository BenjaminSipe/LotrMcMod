package lotr.client.render.tileentity;

import java.awt.Color;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityEntJar;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class LOTRRenderEntJar extends TileEntitySpecialRenderer {
   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityEntJar jar = (LOTRTileEntityEntJar)tileentity;
      if (jar.drinkAmount > 0) {
         GL11.glPushMatrix();
         GL11.glDisable(2884);
         GL11.glEnable(32826);
         GL11.glTranslatef((float)d + 0.5F, (float)d1, (float)d2 + 0.5F);
         GL11.glScalef(-1.0F, -1.0F, 1.0F);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         float transparency = 0.5F;
         Tessellator tessellator = Tessellator.field_78398_a;
         IIcon icon = null;
         float minU = 0.0F;
         float maxU = 0.0F;
         float minV = 0.0F;
         float maxV = 0.0F;
         int color = 16777215;
         if (jar.drinkMeta >= 0) {
            this.func_147499_a(TextureMap.field_110576_c);
            ItemStack drink = new ItemStack(LOTRMod.entDraught, 1, jar.drinkMeta);
            icon = drink.func_77954_c();
            minU = icon.func_94214_a(7.0D);
            maxU = icon.func_94214_a(8.0D);
            minV = icon.func_94207_b(7.0D);
            maxV = icon.func_94207_b(8.0D);
         } else {
            this.func_147499_a(TextureMap.field_110575_b);
            icon = Blocks.field_150355_j.func_149733_h(1);
            minU = icon.func_94214_a(0.0D);
            maxU = icon.func_94214_a(6.0D);
            minV = icon.func_94207_b(0.0D);
            maxV = icon.func_94207_b(6.0D);
            color = Blocks.field_150355_j.func_149720_d(jar.func_145831_w(), jar.field_145851_c, jar.field_145848_d, jar.field_145849_e);
         }

         double d3 = 0.1875D;
         double d4 = -0.0625D - 0.75D * (double)jar.drinkAmount / (double)LOTRTileEntityEntJar.MAX_CAPACITY;
         tessellator.func_78382_b();
         float[] colors = (new Color(color)).getColorComponents((float[])null);
         tessellator.func_78369_a(colors[0], colors[1], colors[2], transparency);
         tessellator.func_78374_a(-d3, d4, d3, (double)minU, (double)maxV);
         tessellator.func_78374_a(d3, d4, d3, (double)maxU, (double)maxV);
         tessellator.func_78374_a(d3, d4, -d3, (double)maxU, (double)minV);
         tessellator.func_78374_a(-d3, d4, -d3, (double)minU, (double)minV);
         tessellator.func_78381_a();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GL11.glDisable(32826);
         GL11.glEnable(2884);
         GL11.glPopMatrix();
      }
   }
}
