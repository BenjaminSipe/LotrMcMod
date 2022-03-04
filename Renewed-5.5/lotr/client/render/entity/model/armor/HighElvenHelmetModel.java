package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class HighElvenHelmetModel extends SpecialArmorModel {
   private ModelRenderer crest;

   public HighElvenHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public HighElvenHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonHelmetParts();
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_228301_a_(-0.5F, -11.0F, -2.0F, 1.0F, 3.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(0, 4).func_228301_a_(-0.5F, -10.0F, 2.0F, 1.0F, 2.0F, 1.0F, 0.0F);
      this.crest = new ModelRenderer(this, 32, 0);
      this.crest.func_228301_a_(-1.0F, -11.0F, -8.0F, 2.0F, 1.0F, 11.0F, 0.0F);
      this.crest.func_78784_a(32, 12).func_228301_a_(-1.0F, -10.0F, -8.0F, 2.0F, 1.0F, 1.0F, 0.0F);
      this.crest.field_78795_f = (float)Math.toRadians(-16.0D);
      this.field_78116_c.func_78792_a(this.crest);
   }
}
