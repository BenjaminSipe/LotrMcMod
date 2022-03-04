package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ArnorHelmetModel extends SpecialArmorModel {
   public ArnorHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public ArnorHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonHelmetParts();
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
      this.field_78116_c.func_78784_a(32, 0).func_228301_a_(-4.5F - f, -13.0F - f, -1.0F, 1.0F, 8.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(36, 0).func_228301_a_(-4.5F - f, -12.0F - f, 0.0F, 1.0F, 7.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(40, 0).func_228301_a_(-4.5F - f, -11.0F - f, 1.0F, 1.0F, 5.0F, 1.0F, 0.0F);
      this.field_78116_c.field_78809_i = true;
      this.field_78116_c.func_78784_a(32, 0).func_228301_a_(3.5F + f, -13.0F - f, -1.0F, 1.0F, 8.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(36, 0).func_228301_a_(3.5F + f, -12.0F - f, 0.0F, 1.0F, 7.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(40, 0).func_228301_a_(3.5F + f, -11.0F - f, 1.0F, 1.0F, 5.0F, 1.0F, 0.0F);
   }
}
