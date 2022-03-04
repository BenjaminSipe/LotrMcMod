package lotr.client.render.tileentity;

import lotr.client.render.LOTRRenderBlocks;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import lotr.common.tileentity.LOTRTileEntityDwarvenDoor;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRRenderDwarvenDoor extends TileEntitySpecialRenderer {
   private RenderBlocks renderBlocks;

   public void func_147496_a(World world) {
      this.renderBlocks = new RenderBlocks(world);
   }

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      if (this.renderBlocks == null) {
         this.renderBlocks = new RenderBlocks(tileentity.func_145831_w());
      }

      LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor)tileentity;
      GL11.glPushMatrix();
      GL11.glEnable(32826);
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      float alphaFunc = LOTRRenderDwarvenGlow.setupGlow(door.getGlowBrightness(f));
      this.func_147499_a(TextureMap.field_110575_b);
      Block block = door.func_145838_q();
      if (block instanceof LOTRBlockGateDwarvenIthildin) {
         LOTRRenderBlocks.renderDwarvenDoorGlow((LOTRBlockGateDwarvenIthildin)block, this.renderBlocks, door.field_145851_c, door.field_145848_d, door.field_145849_e);
      }

      LOTRRenderDwarvenGlow.endGlow(alphaFunc);
      GL11.glDisable(32826);
      GL11.glPopMatrix();
   }
}
