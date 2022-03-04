package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GondorHelmetModel extends SpecialArmorModel {
   public GondorHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public GondorHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonHelmetParts();
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
      this.field_78116_c.func_78784_a(0, 16).func_228301_a_(-1.5F, -9.0F, -3.5F, 3.0F, 1.0F, 7.0F, f);
      this.field_78116_c.func_78784_a(20, 16).func_228301_a_(-0.5F, -10.0F, -3.5F, 1.0F, 1.0F, 7.0F, f);
      this.field_78116_c.func_78784_a(24, 0).func_228301_a_(-1.5F, -10.5F - f, -4.5F - f, 3.0F, 4.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(24, 5).func_228301_a_(-0.5F, -11.5F - f, -4.5F - f, 1.0F, 1.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(28, 5).func_228301_a_(-0.5F, -6.5F - f, -4.5F - f, 1.0F, 1.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(32, 0).func_228301_a_(-1.5F, -9.5F - f, 3.5F + f, 3.0F, 3.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(32, 4).func_228301_a_(-0.5F, -10.5F - f, 3.5F + f, 1.0F, 1.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(36, 4).func_228301_a_(-0.5F, -6.5F - f, 3.5F + f, 1.0F, 1.0F, 1.0F, 0.0F);
   }
}
