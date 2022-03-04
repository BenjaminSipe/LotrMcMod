package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DorwinionElvenHelmetModel extends SpecialArmorModel {
   public DorwinionElvenHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public DorwinionElvenHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonHelmetParts();
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
      this.field_78116_c.func_78784_a(20, 16).func_228301_a_(0.0F, -10.0F, 4.0F, 0.0F, 10.0F, 4.0F, 0.0F);
      this.field_78116_c.func_78784_a(32, 0).func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f + 0.5F);
      ModelRenderer crest = new ModelRenderer(this, 0, 16);
      crest.func_78793_a(0.0F, -f, 0.0F);
      crest.func_228301_a_(-1.0F, -11.0F, -6.0F, 2.0F, 5.0F, 8.0F, 0.0F);
      crest.field_78795_f = (float)Math.toRadians(-15.0D);
      this.field_78116_c.func_78792_a(crest);
   }
}
