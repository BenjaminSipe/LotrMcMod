package lotr.client.render.tileentity;

import java.util.Random;
import lotr.common.entity.LOTRPlateFallingInfo;
import lotr.common.tileentity.LOTRTileEntityPlate;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class LOTRRenderPlateFood extends TileEntitySpecialRenderer {
   private Random rand = new Random();

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
      ItemStack plateItem = plate.getFoodItem();
      LOTRPlateFallingInfo fallInfo = plate.plateFallInfo;
      if (fallInfo == null) {
         float var10000 = 0.0F;
      } else {
         fallInfo.getPlateOffsetY(f);
      }

      if (plateItem != null) {
         GL11.glPushMatrix();
         GL11.glDisable(2884);
         GL11.glEnable(32826);
         GL11.glTranslatef((float)d + 0.5F, (float)d1, (float)d2 + 0.5F);
         this.func_147499_a(TextureMap.field_110576_c);
         IIcon icon = plateItem.func_77954_c();
         Tessellator tessellator = Tessellator.field_78398_a;
         float f4 = icon.func_94209_e();
         float f1 = icon.func_94212_f();
         float f2 = icon.func_94206_g();
         float f3 = icon.func_94210_h();
         int foods = plateItem.field_77994_a;
         float lowerOffset = 0.125F;

         for(int l = 0; l < foods; ++l) {
            GL11.glPushMatrix();
            float offset = 0.0F;
            if (fallInfo != null) {
               offset = fallInfo.getFoodOffsetY(l, f);
            }

            offset = Math.max(offset, lowerOffset);
            GL11.glTranslatef(0.0F, offset, 0.0F);
            lowerOffset = offset + 0.03125F;
            this.rand.setSeed((long)(plate.field_145851_c * 3129871) ^ (long)plate.field_145849_e * 116129781L ^ (long)plate.field_145848_d + (long)l * 5930563L);
            float rotation = this.rand.nextFloat() * 360.0F;
            GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(-0.25F, -0.25F, 0.0F);
            GL11.glScalef(0.5625F, 0.5625F, 0.5625F);
            ItemRenderer.func_78439_a(tessellator, f1, f2, f4, f3, icon.func_94211_a(), icon.func_94216_b(), 0.0625F);
            GL11.glPopMatrix();
         }

         GL11.glDisable(32826);
         GL11.glEnable(2884);
         GL11.glPopMatrix();
      }

   }
}
