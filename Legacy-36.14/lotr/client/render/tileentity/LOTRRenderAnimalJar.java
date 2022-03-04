package lotr.client.render.tileentity;

import lotr.client.LOTRTickHandlerClient;
import lotr.client.render.entity.LOTRRenderBird;
import lotr.common.item.LOTRItemAnimalJar;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class LOTRRenderAnimalJar extends TileEntitySpecialRenderer implements IItemRenderer {
   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)tileentity;
      Entity jarEntity = jar.getOrCreateJarEntity();
      if (jarEntity != null) {
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, jar.getEntityBobbing(f), 0.0F);
         if (jarEntity instanceof EntityLivingBase) {
            EntityLivingBase jarLiving = (EntityLivingBase)jarEntity;
            EntityLivingBase viewer = RenderManager.field_78727_a.field_78734_h;
            if (jar.isEntityWatching()) {
               Vec3 viewerPos = viewer.func_70666_h(f);
               Vec3 entityPos = jarLiving.func_70666_h(f);
               double dx = entityPos.field_72450_a - viewerPos.field_72450_a;
               double var10000 = entityPos.field_72448_b - viewerPos.field_72448_b;
               double dz = entityPos.field_72449_c - viewerPos.field_72449_c;
               float lookYaw = (float)Math.toDegrees(Math.atan2(dz, dx));
               lookYaw += 90.0F;
               jarLiving.field_70177_z = jarLiving.field_70126_B = lookYaw;
            }

            jarLiving.field_70761_aq = jarLiving.field_70177_z;
            jarLiving.field_70760_ar = jarLiving.field_70126_B;
         }

         RenderManager.field_78727_a.func_147936_a(jarEntity, f, false);
         GL11.glPopMatrix();
      }

   }

   public boolean handleRenderType(ItemStack itemstack, ItemRenderType type) {
      return true;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper) {
      return true;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         GL11.glTranslatef(0.5F, 0.5F, 0.5F);
      }

      EntityLivingBase viewer = Minecraft.func_71410_x().field_71451_h;
      Entity jarEntity = LOTRItemAnimalJar.getItemJarEntity(itemstack, viewer.field_70170_p);
      if (jarEntity != null) {
         jarEntity.func_70012_b(0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
         jarEntity.field_70173_aa = viewer.field_70173_aa;
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, -0.5F, 0.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPushAttrib(1048575);
         if (type == ItemRenderType.ENTITY) {
            LOTRRenderBird.renderStolenItem = false;
         }

         RenderManager.field_78727_a.func_147940_a(jarEntity, 0.0D, 0.0D, 0.0D, 0.0F, LOTRTickHandlerClient.renderTick);
         LOTRRenderBird.renderStolenItem = true;
         GL11.glPopAttrib();
         GL11.glPopMatrix();
      }

      GL11.glEnable(3008);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glEnable(3042);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      RenderBlocks.getInstance().func_147800_a(Block.func_149634_a(itemstack.func_77973_b()), itemstack.func_77960_j(), 1.0F);
      GL11.glDisable(3042);
   }
}
