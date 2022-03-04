package lotr.client.model;

import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class LOTRModelLeatherHat extends LOTRModelBiped {
   private static ItemStack feather;
   private ItemStack hatItem;

   public LOTRModelLeatherHat() {
      this(0.0F);
   }

   public LOTRModelLeatherHat(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-6.0F, -9.0F, -6.0F, 12, 2, 12, f);
      this.field_78116_c.func_78784_a(0, 14).func_78790_a(-4.0F, -13.0F, -4.0F, 8, 4, 8, f);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }

   public void setHatItem(ItemStack itemstack) {
      this.hatItem = itemstack;
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GL11.glPushMatrix();
      int hatColor = LOTRItemLeatherHat.getHatColor(this.hatItem);
      float r = (float)(hatColor >> 16 & 255) / 255.0F;
      float g = (float)(hatColor >> 8 & 255) / 255.0F;
      float b = (float)(hatColor & 255) / 255.0F;
      GL11.glColor3f(r, g, b);
      this.field_78116_c.func_78785_a(f5);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      if (LOTRItemLeatherHat.hasFeather(this.hatItem)) {
         this.field_78116_c.func_78794_c(f5);
         GL11.glScalef(0.375F, 0.375F, 0.375F);
         GL11.glRotatef(130.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(0.25F, 1.5F, 0.75F);
         GL11.glRotatef(-45.0F, 0.0F, 0.0F, 1.0F);
         int featherColor = LOTRItemLeatherHat.getFeatherColor(this.hatItem);
         r = (float)(featherColor >> 16 & 255) / 255.0F;
         g = (float)(featherColor >> 8 & 255) / 255.0F;
         b = (float)(featherColor & 255) / 255.0F;
         GL11.glColor3f(r, g, b);
         if (entity instanceof EntityLivingBase) {
            RenderManager.field_78727_a.field_78721_f.func_78443_a((EntityLivingBase)entity, feather, 0);
         } else {
            RenderManager.field_78727_a.field_78721_f.func_78443_a(Minecraft.func_71410_x().field_71439_g, feather, 0);
         }

         GL11.glColor3f(1.0F, 1.0F, 1.0F);
      }

      GL11.glPopMatrix();
   }

   static {
      feather = new ItemStack(Items.field_151008_G);
   }
}
