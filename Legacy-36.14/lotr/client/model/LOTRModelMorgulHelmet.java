package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelMorgulHelmet extends LOTRModelBiped {
   private ModelRenderer[] spikes;

   public LOTRModelMorgulHelmet() {
      this(0.0F);
   }

   public LOTRModelMorgulHelmet(float f) {
      super(f);
      this.spikes = new ModelRenderer[8];
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 12, 8, f);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78784_a(0, 20).func_78790_a(-3.5F, -18.0F, -3.5F, 7, 10, 1, f);

      int i;
      for(i = 0; i < this.spikes.length; ++i) {
         this.spikes[i] = new ModelRenderer(this, 16, 20);
         this.spikes[i].func_78793_a(0.0F, 0.0F, 0.0F);
      }

      this.spikes[0].func_78789_a(-1.0F, -5.5F, -10.0F, 1, 1, 4);
      this.spikes[0].field_78795_f = (float)Math.toRadians(-20.0D);
      this.spikes[0].field_78796_g = (float)Math.toRadians(20.0D);
      this.spikes[1].func_78789_a(0.0F, -5.5F, -10.0F, 1, 1, 4);
      this.spikes[1].field_78795_f = (float)Math.toRadians(-20.0D);
      this.spikes[1].field_78796_g = (float)Math.toRadians(-20.0D);
      this.spikes[2].func_78789_a(6.0F, -5.5F, -1.0F, 4, 1, 1);
      this.spikes[2].field_78808_h = (float)Math.toRadians(-20.0D);
      this.spikes[2].field_78796_g = (float)Math.toRadians(20.0D);
      this.spikes[3].func_78789_a(6.0F, -5.5F, 0.0F, 4, 1, 1);
      this.spikes[3].field_78808_h = (float)Math.toRadians(-20.0D);
      this.spikes[3].field_78796_g = (float)Math.toRadians(-20.0D);
      this.spikes[4].func_78789_a(0.0F, -5.5F, 6.0F, 1, 1, 4);
      this.spikes[4].field_78795_f = (float)Math.toRadians(20.0D);
      this.spikes[4].field_78796_g = (float)Math.toRadians(20.0D);
      this.spikes[5].func_78789_a(-1.0F, -5.5F, 6.0F, 1, 1, 4);
      this.spikes[5].field_78795_f = (float)Math.toRadians(20.0D);
      this.spikes[5].field_78796_g = (float)Math.toRadians(-20.0D);
      this.spikes[6].func_78789_a(-10.0F, -5.5F, 0.0F, 4, 1, 1);
      this.spikes[6].field_78808_h = (float)Math.toRadians(20.0D);
      this.spikes[6].field_78796_g = (float)Math.toRadians(20.0D);
      this.spikes[7].func_78789_a(-10.0F, -5.5F, -1.0F, 4, 1, 1);
      this.spikes[7].field_78808_h = (float)Math.toRadians(20.0D);
      this.spikes[7].field_78796_g = (float)Math.toRadians(-20.0D);

      for(i = 0; i < this.spikes.length; ++i) {
         this.field_78116_c.func_78792_a(this.spikes[i]);
      }

      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
