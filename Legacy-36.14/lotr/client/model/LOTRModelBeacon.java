package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelBeacon extends ModelBase {
   private ModelRenderer base = new ModelRenderer(this, 0, 0);
   private ModelRenderer[][] logs = new ModelRenderer[3][4];

   public LOTRModelBeacon() {
      this.base.func_78789_a(-8.0F, -8.0F, -2.0F, 16, 16, 4);
      this.base.func_78793_a(0.0F, 22.0F, 0.0F);
      this.base.field_78795_f = 1.5707964F;

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 4; ++j) {
            this.logs[i][j] = new ModelRenderer(this, 30, 15);
            this.logs[i][j].func_78789_a(-1.5F, 0.0F, -7.0F, 3, 3, 14);
            this.logs[i][j].func_78793_a(i != 1 ? -6.0F + (float)j * 4.0F : 0.0F, 17.0F - (float)i * 3.0F, i == 1 ? -6.0F + (float)j * 4.0F : 0.0F);
            if (i == 1) {
               this.logs[i][j].field_78796_g = 1.5707964F;
            }
         }
      }

   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.base.func_78785_a(f5);

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 4; ++j) {
            this.logs[i][j].func_78785_a(f5);
         }
      }

   }
}
