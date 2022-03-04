package lotr.client.model;

import lotr.common.item.LOTRItemHaradTurban;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelHaradTurban extends LOTRModelHaradRobes {
   private ModelRenderer ornament;

   public LOTRModelHaradTurban() {
      this(0.0F);
   }

   public LOTRModelHaradTurban(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-5.0F, -10.0F, -5.0F, 10, 5, 10, 0.0F);
      ModelRenderer shawl = new ModelRenderer(this, 0, 15);
      shawl.func_78790_a(-4.5F, -5.0F, 1.5F, 9, 6, 4, 0.25F);
      shawl.field_78795_f = (float)Math.toRadians(13.0D);
      this.field_78116_c.func_78792_a(shawl);
      this.ornament = new ModelRenderer(this, 0, 0);
      this.ornament.func_78790_a(-1.0F, -9.0F, -6.0F, 2, 2, 1, 0.0F);
      this.field_78116_c.func_78792_a(this.ornament);
      this.field_78114_d.field_78804_l.clear();
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.ornament.field_78806_j = false;
      super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      LOTRArmorModels.INSTANCE.copyBoxRotations(this.ornament, this.field_78116_c);
      this.ornament.field_78806_j = this.field_78116_c.field_78806_j && LOTRItemHaradTurban.hasOrnament(this.robeItem);
      this.ornament.func_78785_a(f5);
   }
}
