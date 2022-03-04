package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelBanner extends ModelBase {
   private ModelRenderer stand;
   private ModelRenderer post;
   private ModelRenderer lowerPost;
   private ModelRenderer bannerFront;
   private ModelRenderer bannerBack;

   public LOTRModelBanner() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.stand = new ModelRenderer(this, 0, 0);
      this.stand.func_78793_a(0.0F, 24.0F, 0.0F);
      this.stand.func_78789_a(-6.0F, -2.0F, -6.0F, 12, 2, 12);
      this.post = new ModelRenderer(this, 0, 14);
      this.post.func_78793_a(0.0F, 24.0F, 0.0F);
      this.post.func_78789_a(-0.5F, -48.0F, -0.5F, 1, 47, 1);
      this.post.func_78784_a(4, 14).func_78789_a(-8.0F, -43.0F, -1.5F, 16, 1, 3);
      this.lowerPost = new ModelRenderer(this, 0, 14);
      this.lowerPost.func_78793_a(0.0F, 24.0F, 0.0F);
      this.lowerPost.func_78789_a(-0.5F, -1.0F, -0.5F, 1, 24, 1);
      this.bannerFront = new ModelRenderer(this, 0, 0);
      this.bannerFront.func_78793_a(0.0F, -18.0F, 0.0F);
      this.bannerFront.func_78789_a(-8.0F, 0.0F, -1.0F, 16, 32, 0);
      this.bannerBack = new ModelRenderer(this, 0, 0);
      this.bannerBack.func_78793_a(0.0F, -18.0F, 0.0F);
      this.bannerBack.func_78789_a(-8.0F, 0.0F, -1.0F, 16, 32, 0);
      this.bannerBack.field_78796_g = 3.1415927F;
   }

   public void renderStand(float f) {
      this.stand.func_78785_a(f);
   }

   public void renderPost(float f) {
      this.post.func_78785_a(f);
   }

   public void renderLowerPost(float f) {
      this.lowerPost.func_78785_a(f);
   }

   public void renderBanner(float f) {
      this.bannerFront.func_78785_a(f);
      this.bannerBack.func_78785_a(f);
   }
}
