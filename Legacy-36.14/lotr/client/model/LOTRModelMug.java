package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelMug extends ModelBase {
   private ModelRenderer[] mugParts = new ModelRenderer[5];
   private ModelRenderer[] handleParts = new ModelRenderer[3];

   public LOTRModelMug() {
      this.mugParts[0] = new ModelRenderer(this, 0, 0);
      this.mugParts[0].func_78789_a(-3.0F, -8.0F, -2.0F, 1, 8, 4);
      this.mugParts[1] = new ModelRenderer(this, 10, 3);
      this.mugParts[1].func_78789_a(-3.0F, -8.0F, -3.0F, 6, 8, 1);
      this.mugParts[2] = new ModelRenderer(this, 24, 0);
      this.mugParts[2].func_78789_a(2.0F, -8.0F, -2.0F, 1, 8, 4);
      this.mugParts[3] = new ModelRenderer(this, 34, 3);
      this.mugParts[3].func_78789_a(-3.0F, -8.0F, 2.0F, 6, 8, 1);
      this.mugParts[4] = new ModelRenderer(this, 0, 12);
      this.mugParts[4].func_78789_a(-2.0F, -1.0F, -2.0F, 4, 1, 4);
      this.handleParts[0] = new ModelRenderer(this, 0, 17);
      this.handleParts[0].func_78789_a(3.0F, -7.0F, -0.5F, 2, 1, 1);
      this.handleParts[1] = new ModelRenderer(this, 0, 19);
      this.handleParts[1].func_78789_a(4.0F, -6.0F, -0.5F, 1, 4, 1);
      this.handleParts[2] = new ModelRenderer(this, 0, 24);
      this.handleParts[2].func_78789_a(3.0F, -2.0F, -0.5F, 2, 1, 1);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      ModelRenderer[] var8 = this.mugParts;
      int var9 = var8.length;

      int var10;
      ModelRenderer part;
      for(var10 = 0; var10 < var9; ++var10) {
         part = var8[var10];
         part.func_78785_a(f5);
      }

      var8 = this.handleParts;
      var9 = var8.length;

      for(var10 = 0; var10 < var9; ++var10) {
         part = var8[var10];
         part.func_78785_a(f5);
      }

   }
}
