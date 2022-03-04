package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class UrukHelmetModel extends SpecialArmorModel {
   private ModelRenderer crest;
   private ModelRenderer jaw;

   public UrukHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public UrukHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonHelmetParts();
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
      this.crest = new ModelRenderer(this, 0, 22);
      this.crest.func_228301_a_(-10.0F, -16.0F, -1.0F, 20.0F, 10.0F, 0.0F, 0.0F);
      this.crest.field_78795_f = (float)Math.toRadians(-10.0D);
      this.field_78116_c.func_78792_a(this.crest);
      this.jaw = new ModelRenderer(this, 0, 16);
      this.jaw.func_228301_a_(-6.0F, 2.0F, -4.0F, 12.0F, 6.0F, 0.0F, 0.0F);
      this.jaw.field_78795_f = (float)Math.toRadians(-60.0D);
      this.field_78116_c.func_78792_a(this.jaw);
   }
}
