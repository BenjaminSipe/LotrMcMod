package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;

public class WingedGondorHelmetModel extends GondorHelmetModel {
   public WingedGondorHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public WingedGondorHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.field_78116_c.func_78784_a(32, 8).func_228301_a_(-6.0F - f, -4.0F, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(38, 8).func_228301_a_(-7.0F - f, -13.0F, -0.5F, 3.0F, 9.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(46, 8).func_228301_a_(-5.5F - f, -17.0F, -0.5F, 2.0F, 4.0F, 1.0F, 0.0F);
      this.field_78116_c.field_78809_i = true;
      this.field_78116_c.func_78784_a(32, 8).func_228301_a_(4.0F + f, -4.0F, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(38, 8).func_228301_a_(4.0F + f, -13.0F, -0.5F, 3.0F, 9.0F, 1.0F, 0.0F);
      this.field_78116_c.func_78784_a(46, 8).func_228301_a_(3.5F + f, -17.0F, -0.5F, 2.0F, 4.0F, 1.0F, 0.0F);
   }
}
