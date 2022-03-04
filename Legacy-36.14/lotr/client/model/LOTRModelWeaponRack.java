package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelWeaponRack extends ModelBase {
   private ModelRenderer base = new ModelRenderer(this, 0, 0);
   private ModelRenderer stand;
   private ModelRenderer holder;
   private ModelRenderer holderUpperParts;
   public boolean onWall = false;

   public LOTRModelWeaponRack() {
      this.base.func_78793_a(0.0F, 24.0F, 0.0F);
      this.base.func_78789_a(-7.0F, -2.0F, -3.0F, 14, 2, 6);
      this.stand = new ModelRenderer(this, 34, 0);
      this.stand.func_78793_a(0.0F, -2.0F, 0.0F);
      this.stand.func_78789_a(-4.0F, -4.0F, -0.5F, 8, 1, 1);
      this.stand.func_78784_a(52, 0);
      this.stand.func_78789_a(-6.0F, -6.0F, -1.0F, 2, 6, 2);
      this.stand.field_78809_i = true;
      this.stand.func_78789_a(4.0F, -6.0F, -1.0F, 2, 6, 2);
      this.holder = new ModelRenderer(this, 0, 8);
      this.holder.func_78793_a(0.0F, -8.0F, 0.0F);
      this.holder.func_78789_a(-7.0F, -1.0F, -2.0F, 14, 1, 4);
      this.holder.func_78784_a(6, 13);
      this.holder.func_78789_a(-6.0F, -2.0F, -1.5F, 2, 1, 3);
      this.holder.field_78809_i = true;
      this.holder.func_78789_a(4.0F, -2.0F, -1.5F, 2, 1, 3);
      this.holder.field_78809_i = false;
      this.holder.func_78784_a(0, 13);
      this.holder.func_78789_a(-6.0F, -3.0F, 0.5F, 2, 1, 1);
      this.holder.field_78809_i = true;
      this.holder.func_78789_a(4.0F, -3.0F, 0.5F, 2, 1, 1);
      this.holderUpperParts = new ModelRenderer(this, 0, 13);
      this.holderUpperParts.func_78793_a(0.0F, 0.0F, 0.0F);
      this.holderUpperParts.func_78789_a(-6.0F, -3.0F, -1.5F, 2, 1, 1);
      this.holderUpperParts.field_78809_i = true;
      this.holderUpperParts.func_78789_a(4.0F, -3.0F, -1.5F, 2, 1, 1);
      this.base.func_78792_a(this.stand);
      this.base.func_78792_a(this.holder);
      this.holder.func_78792_a(this.holderUpperParts);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (this.onWall) {
         this.base.field_78795_f = (float)Math.toRadians(-90.0D);
         this.stand.field_78807_k = true;
         this.holder.field_78795_f = 0.0F;
         this.holder.func_78793_a(0.0F, -2.0F, 0.0F);
         this.holderUpperParts.field_78806_j = false;
      } else {
         this.base.field_78795_f = 0.0F;
         this.stand.field_78807_k = false;
         this.holder.field_78795_f = 0.0F;
         this.holder.func_78793_a(0.0F, -8.0F, 0.0F);
         this.holderUpperParts.field_78806_j = true;
      }

      this.base.func_78785_a(f5);
   }
}
