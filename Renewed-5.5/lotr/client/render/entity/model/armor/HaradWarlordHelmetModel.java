package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class HaradWarlordHelmetModel extends SpecialArmorModel {
   private ModelRenderer stickRight;
   private ModelRenderer stickCentre;
   private ModelRenderer stickLeft;

   public HaradWarlordHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public HaradWarlordHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonHelmetParts();
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78784_a(6, 24).func_228301_a_(-2.5F, -3.0F, 4.1F, 5.0F, 3.0F, 2.0F, 0.0F);
      this.field_78116_c.func_78784_a(0, 16).func_228301_a_(-9.0F, -16.0F, 5.5F, 18.0F, 8.0F, 0.0F, 0.0F);
      this.stickRight = new ModelRenderer(this, 36, 0);
      this.stickRight.func_228301_a_(-0.5F, -19.0F, 5.0F, 1.0F, 18.0F, 1.0F, 0.0F);
      this.stickRight.func_78784_a(0, 24).func_228301_a_(-1.5F, -24.0F, 5.5F, 3.0F, 5.0F, 0.0F, 0.0F);
      this.stickRight.field_78808_h = (float)Math.toRadians(-28.0D);
      this.field_78116_c.func_78792_a(this.stickRight);
      this.stickCentre = new ModelRenderer(this, 36, 0);
      this.stickCentre.func_228301_a_(-0.5F, -19.0F, 5.0F, 1.0F, 18.0F, 1.0F, 0.0F);
      this.stickCentre.func_78784_a(0, 24).func_228301_a_(-1.5F, -24.0F, 5.5F, 3.0F, 5.0F, 0.0F, 0.0F);
      this.stickCentre.field_78808_h = (float)Math.toRadians(0.0D);
      this.field_78116_c.func_78792_a(this.stickCentre);
      this.stickLeft = new ModelRenderer(this, 36, 0);
      this.stickLeft.func_228301_a_(-0.5F, -19.0F, 5.0F, 1.0F, 18.0F, 1.0F, 0.0F);
      this.stickLeft.func_78784_a(0, 24).func_228301_a_(-1.5F, -24.0F, 5.5F, 3.0F, 5.0F, 0.0F, 0.0F);
      this.stickLeft.field_78808_h = (float)Math.toRadians(28.0D);
      this.field_78116_c.func_78792_a(this.stickLeft);
   }
}
