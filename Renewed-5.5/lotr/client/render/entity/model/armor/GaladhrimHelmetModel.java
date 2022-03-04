package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GaladhrimHelmetModel extends SpecialArmorModel {
   public GaladhrimHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public GaladhrimHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonHelmetParts();
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
      ModelRenderer horn = new ModelRenderer(this, 32, 0);
      horn.func_228301_a_(-0.5F, -9.0F - f, 2.0F - f, 1.0F, 3.0F, 3.0F, 0.0F);
      horn.func_78784_a(32, 6).func_228301_a_(-0.5F, -10.0F - f, 3.5F - f, 1.0F, 1.0F, 3.0F, 0.0F);
      horn.func_78784_a(32, 10).func_228301_a_(-0.5F, -11.0F - f, 5.5F - f, 1.0F, 1.0F, 4.0F, 0.0F);
      horn.field_78795_f = (float)Math.toRadians(45.0D);
      this.field_78116_c.func_78792_a(horn);
   }
}
