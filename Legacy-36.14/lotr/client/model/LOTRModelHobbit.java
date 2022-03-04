package lotr.client.model;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelHobbit extends LOTRModelBiped {
   public ModelRenderer bipedChest;
   private static final float F_10_12 = 0.8333333F;

   public LOTRModelHobbit() {
      this(0.0F);
   }

   public LOTRModelHobbit(float f) {
      this(f, 64, f == 0.0F ? 64 : 32);
   }

   public LOTRModelHobbit(float f, int width, int height) {
      super(f, 0.0F, width, height);
      boolean isArmor = height == 32;
      this.bipedChest = new ModelRenderer(this, 24, 0);
      this.bipedChest.func_78790_a(-3.0F, 2.0F, -4.0F, 6, 3, 2, f);
      this.bipedChest.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78115_e.func_78792_a(this.bipedChest);
      if (!isArmor) {
         this.field_78114_d = new ModelRenderer(this, 0, 32);
         this.field_78114_d.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 12, 8, 0.5F + f);
         this.field_78114_d.func_78793_a(0.0F, 2.0F, 0.0F);
      }

      if (!isArmor) {
         ModelRenderer rightFoot = new ModelRenderer(this, 40, 32);
         rightFoot.func_78790_a(-2.0F, 10.0F, -5.0F, 4, 2, 3, f);
         rightFoot.field_78796_g = (float)Math.toRadians(10.0D);
         this.field_78123_h.func_78792_a(rightFoot);
         ModelRenderer leftFoot = new ModelRenderer(this, 40, 32);
         leftFoot.func_78790_a(-2.0F, 10.0F, -5.0F, 4, 2, 3, f);
         leftFoot.field_78796_g = (float)Math.toRadians(-10.0D);
         this.field_78124_i.func_78792_a(leftFoot);
      }

      ModelRenderer var10000 = this.field_78116_c;
      var10000.field_78797_d += 4.0F;
      var10000 = this.field_78114_d;
      var10000.field_78797_d += 4.0F;
      var10000 = this.field_78115_e;
      var10000.field_78797_d += 4.8F;
      var10000 = this.field_78112_f;
      var10000.field_78797_d += 4.8F;
      var10000 = this.field_78113_g;
      var10000.field_78797_d += 4.8F;
      var10000 = this.field_78123_h;
      var10000.field_78797_d += 4.8F;
      var10000 = this.field_78124_i;
      var10000.field_78797_d += 4.8F;
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.bipedChest.field_78806_j = entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).shouldRenderNPCChest();
      float f6 = 2.0F;
      if (this.field_78091_s) {
         GL11.glPushMatrix();
         GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
         GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
         GL11.glTranslatef(0.0F, -1.0F * f5, 0.0F);
      } else {
         GL11.glPushMatrix();
      }

      this.field_78116_c.func_78785_a(f5);
      this.field_78114_d.func_78785_a(f5);
      if (this.field_78091_s) {
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
         GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
      }

      GL11.glPushMatrix();
      GL11.glScalef(1.0F, 0.8333333F, 1.0F);
      this.field_78115_e.func_78785_a(f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(1.0F, 0.8333333F, 1.0F);
      this.field_78112_f.func_78785_a(f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(1.0F, 0.8333333F, 1.0F);
      this.field_78113_g.func_78785_a(f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(1.0F, 0.8333333F, 1.0F);
      this.field_78123_h.func_78785_a(f5);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(1.0F, 0.8333333F, 1.0F);
      this.field_78124_i.func_78785_a(f5);
      GL11.glPopMatrix();
      if (this.field_78091_s) {
         GL11.glPopMatrix();
      } else {
         GL11.glPopMatrix();
      }

   }
}
