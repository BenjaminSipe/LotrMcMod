package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.common.entity.projectile.SmokeRingEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SmokeShipModel extends EntityModel {
   private ModelRenderer hull = new ModelRenderer(this);
   private ModelRenderer deck;
   private ModelRenderer mast1;
   private ModelRenderer sail1;
   private ModelRenderer mast2;
   private ModelRenderer sail2;
   private ModelRenderer mast3;
   private ModelRenderer sail3;
   private ModelRenderer bow;
   private ModelRenderer stern;

   public SmokeShipModel() {
      this.hull.func_228300_a_(-3.5F, 1.0F, -8.0F, 7.0F, 5.0F, 16.0F);
      this.hull.func_78793_a(0.0F, 0.0F, 0.0F);
      this.deck = new ModelRenderer(this);
      this.deck.func_228300_a_(-5.0F, -0.01F, -8.0F, 10.0F, 1.0F, 16.0F);
      this.deck.func_78793_a(0.0F, 0.0F, 0.0F);
      this.mast1 = new ModelRenderer(this);
      this.mast1.func_228300_a_(-1.0F, -8.99F, -6.0F, 2.0F, 9.0F, 2.0F);
      this.mast1.func_78793_a(0.0F, 0.0F, 0.0F);
      this.sail1 = new ModelRenderer(this);
      this.sail1.func_228300_a_(-6.0F, -8.0F, -5.5F, 12.0F, 6.0F, 1.0F);
      this.sail1.func_78793_a(0.0F, 0.0F, 0.0F);
      this.mast2 = new ModelRenderer(this);
      this.mast2.func_228300_a_(-1.0F, -11.99F, -1.0F, 2.0F, 12.0F, 2.0F);
      this.mast2.func_78793_a(0.0F, 0.0F, 0.0F);
      this.sail2 = new ModelRenderer(this);
      this.sail2.func_228300_a_(-8.0F, -11.0F, -0.5F, 16.0F, 8.0F, 1.0F);
      this.sail2.func_78793_a(0.0F, 0.0F, 0.0F);
      this.mast3 = new ModelRenderer(this);
      this.mast3.func_228300_a_(-1.0F, -8.99F, 4.0F, 2.0F, 9.0F, 2.0F);
      this.mast3.func_78793_a(0.0F, 0.0F, 0.0F);
      this.sail3 = new ModelRenderer(this);
      this.sail3.func_228300_a_(-6.0F, -8.0F, 4.5F, 12.0F, 6.0F, 1.0F);
      this.sail3.func_78793_a(0.0F, 0.0F, 0.0F);
      this.bow = new ModelRenderer(this);
      this.bow.func_228300_a_(-3.5F, -1.0F, -11.99F, 7.0F, 3.0F, 4.0F);
      this.bow.func_78793_a(0.0F, 0.0F, 0.0F);
      this.stern = new ModelRenderer(this);
      this.stern.func_228300_a_(-3.5F, -1.0F, 7.99F, 7.0F, 3.0F, 4.0F);
      this.stern.func_78793_a(0.0F, 0.0F, 0.0F);
   }

   public void setRotationAngles(SmokeRingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void func_225598_a_(MatrixStack mat, IVertexBuilder buf, int light, int overlay, float r, float g, float b, float a) {
      this.hull.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
      this.deck.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
      this.mast1.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
      this.sail1.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
      this.mast2.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
      this.sail2.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
      this.mast3.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
      this.sail3.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
      this.bow.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
      this.stern.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
   }
}
