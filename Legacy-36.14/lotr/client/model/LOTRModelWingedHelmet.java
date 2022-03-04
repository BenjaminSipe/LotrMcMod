package lotr.client.model;

public class LOTRModelWingedHelmet extends LOTRModelGondorHelmet {
   public LOTRModelWingedHelmet() {
      this(0.0F);
   }

   public LOTRModelWingedHelmet(float f) {
      super(f);
      this.field_78116_c.func_78784_a(32, 8).func_78790_a(-6.0F - f, -4.0F, -0.5F, 2, 2, 1, 0.0F);
      this.field_78116_c.func_78784_a(38, 8).func_78790_a(-7.0F - f, -13.0F, -0.5F, 3, 9, 1, 0.0F);
      this.field_78116_c.func_78784_a(46, 8).func_78790_a(-5.5F - f, -17.0F, -0.5F, 2, 4, 1, 0.0F);
      this.field_78116_c.field_78809_i = true;
      this.field_78116_c.func_78784_a(32, 8).func_78790_a(4.0F + f, -4.0F, -0.5F, 2, 2, 1, 0.0F);
      this.field_78116_c.func_78784_a(38, 8).func_78790_a(4.0F + f, -13.0F, -0.5F, 3, 9, 1, 0.0F);
      this.field_78116_c.func_78784_a(46, 8).func_78790_a(3.5F + f, -17.0F, -0.5F, 2, 4, 1, 0.0F);
   }
}
