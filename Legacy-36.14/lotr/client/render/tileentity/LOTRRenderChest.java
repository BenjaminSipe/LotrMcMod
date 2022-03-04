package lotr.client.render.tileentity;

import cpw.mods.fml.common.FMLLog;
import java.util.HashMap;
import java.util.Map;
import lotr.common.block.LOTRBlockChest;
import lotr.common.block.LOTRBlockSpawnerChest;
import lotr.common.tileentity.LOTRTileEntityChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderChest extends TileEntitySpecialRenderer {
   private static Map chestTextures = new HashMap();
   private static ModelChest chestModel = new ModelChest();
   private LOTRTileEntityChest itemEntity = new LOTRTileEntityChest();

   public static ResourceLocation getChestTexture(String s) {
      ResourceLocation r = (ResourceLocation)chestTextures.get(s);
      if (r == null) {
         r = new ResourceLocation("lotr:item/chest/" + s + ".png");
         chestTextures.put(s, r);
      }

      return r;
   }

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityChest chest = (LOTRTileEntityChest)tileentity;
      int meta;
      if (!chest.func_145830_o()) {
         meta = 0;
      } else {
         Block block = tileentity.func_145838_q();
         meta = tileentity.func_145832_p();
         if (block instanceof BlockChest && meta == 0) {
            try {
               ((BlockChest)block).func_149954_e(tileentity.func_145831_w(), tileentity.field_145851_c, tileentity.field_145848_d, tileentity.field_145849_e);
            } catch (ClassCastException var13) {
               FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest", new Object[]{tileentity.field_145851_c, tileentity.field_145848_d, tileentity.field_145849_e});
            }

            meta = tileentity.func_145832_p();
         }
      }

      GL11.glPushMatrix();
      GL11.glEnable(32826);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glTranslatef((float)d, (float)d1 + 1.0F, (float)d2 + 1.0F);
      GL11.glScalef(1.0F, -1.0F, -1.0F);
      GL11.glTranslatef(0.5F, 0.5F, 0.5F);
      float rot = 0.0F;
      if (meta == 2) {
         rot = 180.0F;
      }

      if (meta == 3) {
         rot = 0.0F;
      }

      if (meta == 4) {
         rot = 90.0F;
      }

      if (meta == 5) {
         rot = -90.0F;
      }

      GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      float lid = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * f;
      lid = 1.0F - lid;
      lid = 1.0F - lid * lid * lid;
      chestModel.field_78234_a.field_78795_f = -(lid * 3.1415927F / 2.0F);
      this.func_147499_a(getChestTexture(chest.textureName));
      chestModel.func_78231_a();
      GL11.glDisable(32826);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void renderInvChest(Block block, int meta) {
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      this.itemEntity.textureName = "";
      if (block instanceof LOTRBlockChest) {
         this.itemEntity.textureName = ((LOTRBlockChest)block).getChestTextureName();
      } else if (block instanceof LOTRBlockSpawnerChest) {
         Block c = ((LOTRBlockSpawnerChest)block).chestModel;
         if (c instanceof LOTRBlockChest) {
            this.itemEntity.textureName = ((LOTRBlockChest)c).getChestTextureName();
         }
      }

      this.func_147500_a(this.itemEntity, 0.0D, 0.0D, 0.0D, 0.0F);
      GL11.glEnable(32826);
   }
}
