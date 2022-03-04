package lotr.client.render.entity;

import cpw.mods.fml.common.FMLLog;
import lotr.client.model.LOTRModelBoar;
import lotr.common.LOTRMod;
import lotr.common.entity.projectile.LOTREntitySpear;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class LOTRRenderSpear extends Render {
   private static ModelBase boarModel = new LOTRModelBoar();

   protected ResourceLocation func_110775_a(Entity entity) {
      return TextureMap.field_110576_c;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntitySpear spear = (LOTREntitySpear)entity;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      GL11.glRotatef(spear.field_70126_B + (spear.field_70177_z - spear.field_70126_B) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(spear.field_70127_C + (spear.field_70125_A - spear.field_70127_C) * f1, 0.0F, 0.0F, 1.0F);
      GL11.glEnable(32826);
      float f2 = (float)spear.shake - f1;
      if (f2 > 0.0F) {
         float f3 = -MathHelper.func_76126_a(f2 * 3.0F) * f2;
         GL11.glRotatef(f3, 0.0F, 0.0F, 1.0F);
      }

      GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(0.0F, -1.0F, 0.0F);
      if (LOTRMod.isAprilFools()) {
         this.func_110776_a(LOTRRenderWildBoar.boarSkin);
         GL11.glTranslatef(0.0F, -2.5F, 0.0F);
         GL11.glScalef(0.25F, 0.25F, 0.25F);
         boarModel.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.625F);
      } else {
         ItemStack itemstack = spear.getProjectileItem();
         IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, ItemRenderType.EQUIPPED);
         if (customRenderer != null) {
            customRenderer.renderItem(ItemRenderType.EQUIPPED, itemstack, new Object[2]);
         } else {
            FMLLog.severe("Error rendering spear: no custom renderer for " + itemstack.toString(), new Object[0]);
         }
      }

      GL11.glDisable(32826);
      GL11.glPopMatrix();
   }

   static {
      boarModel.field_78091_s = false;
   }
}
