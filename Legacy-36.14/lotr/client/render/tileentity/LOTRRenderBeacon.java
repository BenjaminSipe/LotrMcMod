package lotr.client.render.tileentity;

import lotr.client.model.LOTRModelBeacon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBeacon extends TileEntitySpecialRenderer {
   private ModelBase beaconModel = new LOTRModelBeacon();
   private ResourceLocation beaconTexture = new ResourceLocation("lotr:item/beacon.png");

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      this.func_147499_a(this.beaconTexture);
      GL11.glPushMatrix();
      GL11.glEnable(32826);
      GL11.glDisable(2884);
      GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
      GL11.glScalef(1.0F, -1.0F, 1.0F);
      this.beaconModel.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public void renderInvBeacon() {
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      this.func_147500_a((TileEntity)null, 0.0D, 0.0D, 0.0D, 0.0F);
      GL11.glEnable(32826);
   }
}
