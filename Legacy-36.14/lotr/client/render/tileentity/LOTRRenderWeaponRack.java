package lotr.client.render.tileentity;

import lotr.client.model.LOTRModelWeaponRack;
import lotr.client.render.item.LOTRRenderBow;
import lotr.common.tileentity.LOTRTileEntityWeaponRack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class LOTRRenderWeaponRack extends TileEntitySpecialRenderer {
   private static ResourceLocation rackTexture = new ResourceLocation("lotr:item/weaponRack.png");
   private static LOTRModelWeaponRack rackModel = new LOTRModelWeaponRack();

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityWeaponRack weaponRack = (LOTRTileEntityWeaponRack)tileentity;
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glEnable(32826);
      GL11.glEnable(3008);
      GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
      int meta = weaponRack.func_145832_p();
      int dir = meta & 3;
      boolean wall = (meta & 4) != 0;
      switch(dir) {
      case 0:
         GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 1:
         GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 2:
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 3:
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      }

      if (wall) {
         GL11.glTranslatef(0.0F, 0.375F, -0.5F);
      }

      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      float scale = 0.0625F;
      this.func_147499_a(rackTexture);
      rackModel.onWall = wall;
      rackModel.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);
      ItemStack weaponItem = weaponRack.getWeaponItem();
      if (weaponItem != null) {
         float weaponScale = 0.625F;
         GL11.glScalef(weaponScale, weaponScale, weaponScale);
         GL11.glScalef(-1.0F, 1.0F, 1.0F);
         GL11.glTranslatef(0.0F, 0.52F, 0.0F);
         if (wall) {
            GL11.glTranslatef(0.0F, 1.1F, 0.51F);
         }

         GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(0.9375F, 0.0625F, 0.0F);
         GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(0.6666667F, 0.6666667F, 0.6666667F);
         GL11.glTranslatef(0.0F, 0.3F, 0.0F);
         RenderManager renderManager = RenderManager.field_78727_a;
         int passes = 1;
         if (weaponItem.func_77973_b().func_77623_v()) {
            passes = weaponItem.func_77973_b().getRenderPasses(weaponItem.func_77960_j());
         }

         LOTRRenderBow.renderingWeaponRack = true;

         for(int pass = 0; pass < passes; ++pass) {
            int color = weaponItem.func_77973_b().func_82790_a(weaponItem, pass);
            float r = (float)(color >> 16 & 255) / 255.0F;
            float g = (float)(color >> 8 & 255) / 255.0F;
            float b = (float)(color & 255) / 255.0F;
            GL11.glColor4f(r, g, b, 1.0F);
            renderManager.field_78721_f.renderItem(weaponRack.getEntityForRender(), weaponItem, 0, ItemRenderType.EQUIPPED);
         }

         LOTRRenderBow.renderingWeaponRack = false;
      }

      GL11.glEnable(2884);
      GL11.glDisable(32826);
      GL11.glPopMatrix();
      this.renderWeaponName(weaponRack, d + 0.5D, d1 + 0.75D, d2 + 0.5D);
   }

   private void renderWeaponName(LOTRTileEntityWeaponRack rack, double d, double d1, double d2) {
      MovingObjectPosition mop = Minecraft.func_71410_x().field_71476_x;
      if (mop != null && mop.field_72313_a == MovingObjectType.BLOCK && mop.field_72311_b == rack.field_145851_c && mop.field_72312_c == rack.field_145848_d && mop.field_72309_d == rack.field_145849_e) {
         ItemStack weaponItem = rack.getWeaponItem();
         if (Minecraft.func_71382_s() && weaponItem != null && weaponItem.func_82837_s()) {
            RenderManager renderManager = RenderManager.field_78727_a;
            FontRenderer fontRenderer = this.func_147498_b();
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            double dSq = renderManager.field_78734_h.func_70092_e((double)rack.field_145851_c + 0.5D, (double)rack.field_145848_d + 0.5D, (double)rack.field_145849_e);
            float f2 = 64.0F;
            if (dSq < (double)(f2 * f2)) {
               String name = weaponItem.func_82833_r();
               GL11.glPushMatrix();
               GL11.glTranslatef((float)d, (float)d1 + 0.5F, (float)d2);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-renderManager.field_78735_i, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(renderManager.field_78732_j, 1.0F, 0.0F, 0.0F);
               GL11.glScalef(-f1, -f1, f1);
               GL11.glDisable(2896);
               GL11.glDepthMask(false);
               GL11.glDisable(2929);
               GL11.glEnable(3042);
               OpenGlHelper.func_148821_a(770, 771, 1, 0);
               Tessellator tessellator = Tessellator.field_78398_a;
               byte b0 = 0;
               GL11.glDisable(3553);
               tessellator.func_78382_b();
               int j = fontRenderer.func_78256_a(name) / 2;
               tessellator.func_78369_a(0.0F, 0.0F, 0.0F, 0.25F);
               tessellator.func_78377_a((double)(-j - 1), (double)(-1 + b0), 0.0D);
               tessellator.func_78377_a((double)(-j - 1), (double)(8 + b0), 0.0D);
               tessellator.func_78377_a((double)(j + 1), (double)(8 + b0), 0.0D);
               tessellator.func_78377_a((double)(j + 1), (double)(-1 + b0), 0.0D);
               tessellator.func_78381_a();
               GL11.glEnable(3553);
               fontRenderer.func_78276_b(name, -fontRenderer.func_78256_a(name) / 2, b0, 553648127);
               GL11.glEnable(2929);
               GL11.glDepthMask(true);
               fontRenderer.func_78276_b(name, -fontRenderer.func_78256_a(name) / 2, b0, -1);
               GL11.glEnable(2896);
               GL11.glDisable(3042);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glPopMatrix();
            }
         }
      }

   }
}
