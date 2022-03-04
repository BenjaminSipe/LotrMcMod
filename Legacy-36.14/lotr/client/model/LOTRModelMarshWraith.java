package lotr.client.model;

import lotr.common.entity.npc.LOTREntityMarshWraith;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelMarshWraith extends ModelBase {
   private ModelRenderer head;
   private ModelRenderer headwear;
   private ModelRenderer body;
   private ModelRenderer rightArm;
   private ModelRenderer leftArm;
   private ModelRenderer cape;

   public LOTRModelMarshWraith() {
      this.field_78089_u = 64;
      this.head = new ModelRenderer(this, 0, 0);
      this.head.func_78789_a(-4.0F, -8.0F, -4.0F, 8, 8, 8);
      this.head.func_78793_a(0.0F, 0.0F, 0.0F);
      this.headwear = new ModelRenderer(this, 32, 0);
      this.headwear.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
      this.headwear.func_78793_a(0.0F, 0.0F, 0.0F);
      this.body = new ModelRenderer(this, 0, 16);
      this.body.func_78789_a(-4.0F, 0.0F, -2.0F, 8, 24, 4);
      this.body.func_78793_a(0.0F, 0.0F, 0.0F);
      this.rightArm = new ModelRenderer(this, 46, 16);
      this.rightArm.func_78789_a(-3.0F, -2.0F, -2.0F, 4, 12, 4);
      this.rightArm.func_78793_a(-5.0F, 2.0F, 0.0F);
      this.leftArm = new ModelRenderer(this, 46, 16);
      this.leftArm.field_78809_i = true;
      this.leftArm.func_78789_a(-1.0F, -2.0F, -2.0F, 4, 12, 4);
      this.leftArm.func_78793_a(5.0F, 2.0F, 0.0F);
      this.cape = new ModelRenderer(this, 24, 16);
      this.cape.func_78789_a(-5.0F, 1.0F, 3.0F, 10, 16, 1);
      this.cape.func_78793_a(0.0F, 0.0F, 0.0F);
      this.cape.field_78795_f = 0.1F;
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.head.func_78785_a(f5);
      this.headwear.func_78785_a(f5);
      this.body.func_78785_a(f5);
      this.rightArm.func_78785_a(f5);
      this.leftArm.func_78785_a(f5);
      this.cape.func_78785_a(f5);
      if (entity instanceof LOTREntityMarshWraith) {
         GL11.glDisable(3042);
         GL11.glDisable(32826);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78796_g = f3 / 57.295776F;
      this.head.field_78795_f = f4 / 57.295776F;
      this.headwear.field_78796_g = this.head.field_78796_g;
      this.headwear.field_78795_f = this.head.field_78795_f;
   }
}
