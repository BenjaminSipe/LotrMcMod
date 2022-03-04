package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelSmokeShip extends ModelBase {
   private ModelRenderer hull = new ModelRenderer(this);
   private ModelRenderer deck;
   private ModelRenderer mast1;
   private ModelRenderer sail1;
   private ModelRenderer mast2;
   private ModelRenderer sail2;
   private ModelRenderer mast3;
   private ModelRenderer sail3;
   private ModelRenderer bow;
   private ModelRenderer stern;

   public LOTRModelSmokeShip() {
      this.hull.func_78789_a(-3.5F, 1.0F, -8.0F, 7, 5, 16);
      this.hull.func_78793_a(0.0F, 0.0F, 0.0F);
      this.deck = new ModelRenderer(this);
      this.deck.func_78789_a(-5.0F, 0.0F, -8.0F, 10, 1, 16);
      this.deck.func_78793_a(0.0F, 0.0F, 0.0F);
      this.mast1 = new ModelRenderer(this);
      this.mast1.func_78789_a(-1.0F, -9.0F, -6.0F, 2, 9, 2);
      this.mast1.func_78793_a(0.0F, 0.0F, 0.0F);
      this.sail1 = new ModelRenderer(this);
      this.sail1.func_78789_a(-6.0F, -8.0F, -5.5F, 12, 6, 1);
      this.sail1.func_78793_a(0.0F, 0.0F, 0.0F);
      this.mast2 = new ModelRenderer(this);
      this.mast2.func_78789_a(-1.0F, -12.0F, -1.0F, 2, 12, 2);
      this.mast2.func_78793_a(0.0F, 0.0F, 0.0F);
      this.sail2 = new ModelRenderer(this);
      this.sail2.func_78789_a(-8.0F, -11.0F, -0.5F, 16, 8, 1);
      this.sail2.func_78793_a(0.0F, 0.0F, 0.0F);
      this.mast3 = new ModelRenderer(this);
      this.mast3.func_78789_a(-1.0F, -9.0F, 4.0F, 2, 9, 2);
      this.mast3.func_78793_a(0.0F, 0.0F, 0.0F);
      this.sail3 = new ModelRenderer(this);
      this.sail3.func_78789_a(-6.0F, -8.0F, 4.5F, 12, 6, 1);
      this.sail3.func_78793_a(0.0F, 0.0F, 0.0F);
      this.bow = new ModelRenderer(this);
      this.bow.func_78789_a(-3.5F, -1.0F, -12.0F, 7, 3, 4);
      this.bow.func_78793_a(0.0F, 0.0F, 0.0F);
      this.stern = new ModelRenderer(this);
      this.stern.func_78789_a(-3.5F, -1.0F, 8.0F, 7, 3, 4);
      this.stern.func_78793_a(0.0F, 0.0F, 0.0F);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.hull.func_78785_a(f5);
      this.deck.func_78785_a(f5);
      this.mast1.func_78785_a(f5);
      this.sail1.func_78785_a(f5);
      this.mast2.func_78785_a(f5);
      this.sail2.func_78785_a(f5);
      this.mast3.func_78785_a(f5);
      this.sail3.func_78785_a(f5);
      this.bow.func_78785_a(f5);
      this.stern.func_78785_a(f5);
   }
}
