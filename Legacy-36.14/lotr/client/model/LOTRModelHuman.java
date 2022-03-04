package lotr.client.model;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelHuman extends LOTRModelBiped {
   public ModelRenderer bipedChest;

   public LOTRModelHuman() {
      this(0.0F, false);
   }

   public LOTRModelHuman(float f, boolean armor) {
      super(f, 0.0F, 64, armor ? 32 : 64);
      this.bipedChest = new ModelRenderer(this, 24, 0);
      this.bipedChest.func_78790_a(-3.0F, 2.0F, -4.0F, 6, 3, 2, f);
      this.bipedChest.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78115_e.func_78792_a(this.bipedChest);
      if (!armor) {
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
}
