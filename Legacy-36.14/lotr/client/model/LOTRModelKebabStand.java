package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelKebabStand extends ModelBase {
   private ModelRenderer stand;
   private ModelRenderer[] kebab;

   public LOTRModelKebabStand() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.stand = new ModelRenderer(this, 0, 0);
      this.stand.func_78793_a(0.0F, 24.0F, 0.0F);
      this.stand.func_78789_a(-7.0F, -1.0F, -7.0F, 14, 1, 14);
      this.stand.func_78784_a(0, 15).func_78789_a(-4.0F, -16.0F, 6.0F, 8, 15, 1);
      this.stand.func_78784_a(0, 31).func_78789_a(-4.0F, -16.0F, -2.0F, 8, 1, 8);
      this.stand.func_78784_a(0, 40).func_78789_a(-0.5F, -15.0F, -0.5F, 1, 14, 1);
      ModelRenderer panelRight = new ModelRenderer(this, 18, 15);
      panelRight.func_78793_a(-4.0F, 0.0F, 6.0F);
      panelRight.func_78789_a(-4.0F, -16.0F, 0.0F, 4, 15, 1);
      panelRight.field_78796_g = (float)Math.toRadians(-45.0D);
      this.stand.func_78792_a(panelRight);
      ModelRenderer panelLeft = new ModelRenderer(this, 18, 15);
      panelLeft.func_78793_a(4.0F, 0.0F, 6.0F);
      panelLeft.func_78789_a(0.0F, -16.0F, 0.0F, 4, 15, 1);
      panelLeft.field_78796_g = (float)Math.toRadians(45.0D);
      this.stand.func_78792_a(panelLeft);
      this.field_78090_t = 32;
      this.field_78089_u = 32;
      this.kebab = new ModelRenderer[9];

      for(int i = 0; i < this.kebab.length; ++i) {
         ModelRenderer kb = new ModelRenderer(this, 0, 0);
         kb.func_78793_a(0.0F, 10.0F, 0.0F);
         if (i > 0) {
            int width = i + 1;
            kb.func_78789_a(-((float)width) / 2.0F, 0.0F, -((float)width) / 2.0F, width, 11, width);
         }

         this.kebab[i] = kb;
      }

   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.stand.func_78785_a(f5);
   }

   public void renderKebab(float scale, int size, float spin) {
      if (size < 0 || size >= this.kebab.length) {
         size = 0;
      }

      this.kebab[size].field_78796_g = (float)Math.toRadians((double)spin);
      this.kebab[size].func_78785_a(scale);
   }
}
