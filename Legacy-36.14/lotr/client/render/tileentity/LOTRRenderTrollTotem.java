package lotr.client.render.tileentity;

import lotr.client.model.LOTRModelTrollTotem;
import lotr.common.tileentity.LOTRTileEntityTrollTotem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderTrollTotem extends TileEntitySpecialRenderer {
   private LOTRModelTrollTotem totemModel = new LOTRModelTrollTotem();
   private ResourceLocation totemTexture = new ResourceLocation("lotr:item/trollTotem.png");

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityTrollTotem trollTotem = (LOTRTileEntityTrollTotem)tileentity;
      int i = trollTotem.func_145832_p();
      int meta = i & 3;
      float rotation = 0.0F;
      switch((i & 12) >> 2) {
      case 0:
         rotation = 180.0F;
         break;
      case 1:
         rotation = 270.0F;
         break;
      case 2:
         rotation = 0.0F;
         break;
      case 3:
         rotation = 90.0F;
      }

      this.renderTrollTotem(d, d1, d2, meta, rotation, trollTotem.getJawRotation(f));
   }

   private void renderTrollTotem(double d, double d1, double d2, int meta, float rotation, float jawRotation) {
      GL11.glPushMatrix();
      GL11.glEnable(32826);
      GL11.glDisable(2884);
      GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
      GL11.glScalef(1.0F, -1.0F, -1.0F);
      GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
      this.func_147499_a(this.totemTexture);
      switch(meta) {
      case 0:
         this.totemModel.renderHead(0.0625F, jawRotation);
         break;
      case 1:
         this.totemModel.renderBody(0.0625F);
         break;
      case 2:
         this.totemModel.renderBase(0.0625F);
      }

      GL11.glPopMatrix();
   }

   public void renderInvTrollTotem(int meta) {
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      this.renderTrollTotem(0.0D, 0.0D, 0.0D, meta, 0.0F, 0.0F);
      GL11.glEnable(32826);
   }
}
