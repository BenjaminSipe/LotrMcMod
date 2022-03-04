package lotr.client.render.tileentity;

import lotr.client.model.LOTRModelUnsmeltery;
import lotr.common.block.LOTRBlockForgeBase;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderUnsmeltery extends TileEntitySpecialRenderer {
   private ModelBase unsmelteryModel = new LOTRModelUnsmeltery();
   private ResourceLocation idleTexture = new ResourceLocation("lotr:item/unsmeltery/idle.png");
   private ResourceLocation activeTexture = new ResourceLocation("lotr:item/unsmeltery/active.png");

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityUnsmeltery unsmeltery = (LOTRTileEntityUnsmeltery)tileentity;
      GL11.glPushMatrix();
      GL11.glEnable(32826);
      GL11.glDisable(2884);
      GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
      GL11.glScalef(1.0F, -1.0F, -1.0F);
      float rotation = 0.0F;
      float rocking = 0.0F;
      if (unsmeltery != null) {
         switch(unsmeltery.func_145832_p() & 7) {
         case 2:
            rotation = 180.0F;
            break;
         case 3:
            rotation = 0.0F;
            break;
         case 4:
            rotation = 90.0F;
            break;
         case 5:
            rotation = 270.0F;
         }

         rocking = unsmeltery.getRockingAmount(f);
      }

      GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
      boolean useActiveTexture = unsmeltery != null && LOTRBlockForgeBase.isForgeActive(unsmeltery.func_145831_w(), unsmeltery.field_145851_c, unsmeltery.field_145848_d, unsmeltery.field_145849_e);
      if (useActiveTexture) {
         this.func_147499_a(this.activeTexture);
      } else {
         this.func_147499_a(this.idleTexture);
      }

      this.unsmelteryModel.func_78088_a((Entity)null, rocking, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public void renderInvUnsmeltery() {
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      this.func_147500_a((TileEntity)null, 0.0D, 0.0D, 0.0D, 0.0F);
      GL11.glEnable(32826);
   }
}
