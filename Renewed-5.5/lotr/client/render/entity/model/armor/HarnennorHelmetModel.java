package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class HarnennorHelmetModel extends SpecialArmorModel {
   public HarnennorHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public HarnennorHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonHelmetParts();
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
      this.field_78116_c.func_78784_a(0, 5).func_228301_a_(0.0F, -11.0F, -7.0F, 0.0F, 10.0F, 14.0F, 0.0F);
      this.field_78116_c.func_78784_a(16, 19).func_228301_a_(-6.0F, -2.0F, -6.0F, 12.0F, 0.0F, 12.0F, 0.0F);
   }
}
