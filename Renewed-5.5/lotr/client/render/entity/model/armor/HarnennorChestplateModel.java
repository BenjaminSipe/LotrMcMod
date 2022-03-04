package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class HarnennorChestplateModel extends SpecialArmorModel {
   public HarnennorChestplateModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public HarnennorChestplateModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonChestplateParts();
      this.field_78115_e = new ModelRenderer(this, 16, 16);
      this.field_78115_e.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78115_e.func_228301_a_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, f);
      this.field_178723_h = new ModelRenderer(this, 40, 16);
      this.field_178723_h.func_78793_a(-5.0F, 2.0F, 0.0F);
      this.field_178723_h.func_228301_a_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, f);
      this.field_178723_h.func_78784_a(46, 0);
      this.field_178723_h.func_228301_a_(-4.0F - f, -3.0F - f, -2.0F, 5.0F, 1.0F, 4.0F, 0.0F);
      ModelRenderer rightBarbs1 = new ModelRenderer(this, 29, 0);
      rightBarbs1.func_78793_a(-1.5F, -2.5F - f, -2.0F);
      rightBarbs1.func_228301_a_(-2.5F, 0.0F, -2.0F, 5.0F, 0.0F, 2.0F, 0.0F);
      rightBarbs1.field_78795_f = (float)Math.toRadians(30.0D);
      this.field_178723_h.func_78792_a(rightBarbs1);
      ModelRenderer rightBarbs2 = new ModelRenderer(this, 29, 3);
      rightBarbs2.func_78793_a(-1.5F, -2.5F - f, 2.0F);
      rightBarbs2.func_228301_a_(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, 0.0F);
      rightBarbs2.field_78795_f = (float)Math.toRadians(-30.0D);
      this.field_178723_h.func_78792_a(rightBarbs2);
      this.field_178724_i = new ModelRenderer(this, 40, 16);
      this.field_178724_i.func_78793_a(5.0F, 2.0F, 0.0F);
      this.field_178724_i.field_78809_i = true;
      this.field_178724_i.func_228301_a_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, f);
      this.field_178724_i.func_78784_a(46, 0);
      this.field_178724_i.func_228301_a_(-1.0F + f, -3.0F - f, -2.0F, 5.0F, 1.0F, 4.0F, 0.0F);
      ModelRenderer leftBarbs1 = new ModelRenderer(this, 29, 0);
      leftBarbs1.func_78793_a(1.5F, -2.5F - f, -2.0F);
      leftBarbs1.field_78809_i = true;
      leftBarbs1.func_228301_a_(-2.5F, 0.0F, -2.0F, 5.0F, 0.0F, 2.0F, 0.0F);
      leftBarbs1.field_78795_f = (float)Math.toRadians(30.0D);
      this.field_178724_i.func_78792_a(leftBarbs1);
      ModelRenderer leftBarbs2 = new ModelRenderer(this, 29, 3);
      leftBarbs2.func_78793_a(1.5F, -2.5F - f, 2.0F);
      leftBarbs2.field_78809_i = true;
      leftBarbs2.func_228301_a_(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, 0.0F);
      leftBarbs2.field_78795_f = (float)Math.toRadians(-30.0D);
      this.field_178724_i.func_78792_a(leftBarbs2);
   }
}
