package lotr.client.render.tileentity;

import java.util.HashMap;
import java.util.Map;
import lotr.client.model.LOTRModelKebabStand;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class LOTRRenderKebabStand extends TileEntitySpecialRenderer {
   private static LOTRModelKebabStand standModel = new LOTRModelKebabStand();
   private static Map standTextures = new HashMap();
   private static ResourceLocation rawTexture = new ResourceLocation("lotr:item/kebab/raw.png");
   private static ResourceLocation cookedTexture = new ResourceLocation("lotr:item/kebab/cooked.png");

   private static ResourceLocation getStandTexture(LOTRTileEntityKebabStand kebabStand) {
      String s = kebabStand.getStandTextureName();
      if (!StringUtils.func_151246_b(s)) {
         s = "_" + s;
      }

      s = "stand" + s;
      ResourceLocation r = (ResourceLocation)standTextures.get(s);
      if (r == null) {
         r = new ResourceLocation("lotr:item/kebab/" + s + ".png");
         standTextures.put(s, r);
      }

      return r;
   }

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand)tileentity;
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glEnable(32826);
      GL11.glEnable(3008);
      GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
      int meta = kebabStand.func_145832_p();
      switch(meta) {
      case 2:
         GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 3:
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 4:
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 5:
         GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
      }

      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      float scale = 0.0625F;
      this.func_147499_a(getStandTexture(kebabStand));
      standModel.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);
      int meatAmount = kebabStand.getMeatAmount();
      if (meatAmount > 0) {
         boolean cooked = kebabStand.isCooked();
         if (cooked) {
            this.func_147499_a(cookedTexture);
         } else {
            this.func_147499_a(rawTexture);
         }

         float spin = kebabStand.getKebabSpin(f);
         standModel.renderKebab(scale, meatAmount, spin);
      }

      GL11.glEnable(2884);
      GL11.glDisable(32826);
      GL11.glPopMatrix();
   }
}
