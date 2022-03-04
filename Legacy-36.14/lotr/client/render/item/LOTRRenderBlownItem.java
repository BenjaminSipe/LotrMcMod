package lotr.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBlownItem implements IItemRenderer {
   public boolean handleRenderType(ItemStack itemstack, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      EntityLivingBase equipper = (EntityLivingBase)data[1];
      Item item = itemstack.func_77973_b();
      Tessellator tessellator = Tessellator.field_78398_a;
      if (Minecraft.func_71410_x().field_71474_y.field_74320_O != 0 || equipper != Minecraft.func_71410_x().field_71439_g) {
         GL11.glScalef(-1.0F, 1.0F, 1.0F);
         GL11.glTranslatef(-1.35F, 0.0F, 0.0F);
         GL11.glRotatef(-45.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(0.0F, 0.3F, 0.0F);
         GL11.glTranslatef(0.0F, 0.0F, 0.15F);
      }

      int passes = item.getRenderPasses(itemstack.func_77960_j());

      for(int pass = 0; pass < passes; ++pass) {
         int color = item.func_82790_a(itemstack, pass);
         float r = (float)(color >> 16 & 255) / 255.0F;
         float g = (float)(color >> 8 & 255) / 255.0F;
         float b = (float)(color & 255) / 255.0F;
         GL11.glColor3f(r, g, b);
         IIcon icon = equipper.func_70620_b(itemstack, pass);
         float f = icon.func_94209_e();
         float f1 = icon.func_94212_f();
         float f2 = icon.func_94206_g();
         float f3 = icon.func_94210_h();
         ItemRenderer.func_78439_a(tessellator, f1, f2, f, f3, icon.func_94211_a(), icon.func_94216_b(), 0.0625F);
      }

      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GL11.glDisable(32826);
   }
}
