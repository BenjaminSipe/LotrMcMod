package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelElf extends LOTRModelBiped implements LOTRGlowingEyes.Model {
   private ModelRenderer earRight;
   private ModelRenderer earLeft;
   public ModelRenderer bipedChest;

   public LOTRModelElf() {
      this(0.0F);
   }

   public LOTRModelElf(float f) {
      this(f, 64, f == 0.0F ? 64 : 32);
   }

   public LOTRModelElf(float f, int width, int height) {
      super(f, 0.0F, width, height);
      this.earRight = new ModelRenderer(this, 0, 0);
      this.earRight.func_78789_a(-4.0F, -6.5F, -1.0F, 1, 4, 2);
      this.earRight.func_78793_a(0.0F, 0.0F, 0.0F);
      this.earRight.field_78808_h = -0.2617994F;
      this.earLeft = new ModelRenderer(this, 0, 0);
      this.earLeft.field_78809_i = true;
      this.earLeft.func_78789_a(3.0F, -6.5F, -1.0F, 1, 4, 2);
      this.earLeft.func_78793_a(0.0F, 0.0F, 0.0F);
      this.earLeft.field_78808_h = 0.2617994F;
      this.field_78116_c.func_78792_a(this.earRight);
      this.field_78116_c.func_78792_a(this.earLeft);
      this.bipedChest = new ModelRenderer(this, 24, 0);
      this.bipedChest.func_78790_a(-3.0F, 2.0F, -4.0F, 6, 3, 2, f);
      this.bipedChest.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78115_e.func_78792_a(this.bipedChest);
      if (height == 64) {
         this.field_78114_d = new ModelRenderer(this, 0, 32);
         this.field_78114_d.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 16, 8, 0.5F + f);
         this.field_78114_d.func_78793_a(0.0F, 0.0F, 0.0F);
      }

   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.bipedChest.field_78806_j = entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).shouldRenderNPCChest();
      if (this.field_78091_s) {
         float f6 = 2.0F;
         GL11.glPushMatrix();
         GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
         GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
         this.field_78116_c.func_78785_a(f5);
         this.field_78114_d.func_78785_a(f5);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
         GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
         this.field_78115_e.func_78785_a(f5);
         this.field_78112_f.func_78785_a(f5);
         this.field_78113_g.func_78785_a(f5);
         this.field_78123_h.func_78785_a(f5);
         this.field_78124_i.func_78785_a(f5);
         GL11.glPopMatrix();
      } else {
         this.field_78116_c.func_78785_a(f5);
         this.field_78114_d.func_78785_a(f5);
         this.field_78115_e.func_78785_a(f5);
         this.field_78112_f.func_78785_a(f5);
         this.field_78113_g.func_78785_a(f5);
         this.field_78123_h.func_78785_a(f5);
         this.field_78124_i.func_78785_a(f5);
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      if (entity instanceof LOTREntityElf) {
         LOTREntityElf elf = (LOTREntityElf)entity;
         if (elf.isJazz() && elf.isSolo()) {
            this.field_78112_f.field_78796_g = (float)Math.toRadians(-45.0D);
            this.field_78113_g.field_78796_g = -this.field_78112_f.field_78796_g;
            this.field_78112_f.field_78795_f = (float)Math.toRadians(-50.0D);
            this.field_78113_g.field_78795_f = this.field_78112_f.field_78795_f;
         }
      }

   }

   public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.field_78116_c.func_78785_a(f5);
   }
}
