package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelBannerWall extends ModelBase {
   private ModelRenderer post;
   private ModelRenderer banner;

   public LOTRModelBannerWall() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.post = new ModelRenderer(this, 4, 18);
      this.post.func_78793_a(0.0F, -8.0F, 0.0F);
      this.post.func_78789_a(-8.0F, 0.0F, -0.5F, 16, 1, 1);
      this.banner = new ModelRenderer(this, 0, 0);
      this.banner.func_78793_a(0.0F, -7.0F, 0.0F);
      this.banner.func_78789_a(-8.0F, 0.0F, 0.0F, 16, 32, 0);
   }

   public void renderPost(float f) {
      this.post.func_78785_a(f);
   }

   public void renderBanner(float f) {
      this.banner.func_78785_a(f);
   }
}
