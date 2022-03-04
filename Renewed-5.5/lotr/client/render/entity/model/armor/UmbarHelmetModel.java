package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class UmbarHelmetModel extends SpecialArmorModel {
   public UmbarHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public UmbarHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonHelmetParts();
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
      this.field_178720_f = new ModelRenderer(this, 32, 0);
      this.field_178720_f.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_178720_f.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f + 0.5F);
      this.field_78116_c.func_78784_a(0, 0);
      this.field_78116_c.func_228301_a_(-0.5F, -11.0F - f, -3.0F, 1.0F, 3.0F, 1.0F, 0.0F);
      this.field_78116_c.func_228301_a_(-0.5F, -10.0F - f, 2.0F, 1.0F, 2.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(0, 16).func_228301_a_(0.0F, -13.0F - f, -6.0F, 0.0F, 4.0F, 12.0F, 0.0F);
   }
}
