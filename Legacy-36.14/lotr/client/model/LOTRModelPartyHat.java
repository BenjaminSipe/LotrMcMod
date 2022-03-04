package lotr.client.model;

import lotr.common.item.LOTRItemPartyHat;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class LOTRModelPartyHat extends LOTRModelBiped {
   private ItemStack hatItem;

   public LOTRModelPartyHat() {
      this(0.0F);
   }

   public LOTRModelPartyHat(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -14.0F, -4.0F, 8, 8, 8, f);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }

   public void setHatItem(ItemStack itemstack) {
      this.hatItem = itemstack;
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GL11.glPushMatrix();
      int hatColor = LOTRItemPartyHat.getHatColor(this.hatItem);
      float r = (float)(hatColor >> 16 & 255) / 255.0F;
      float g = (float)(hatColor >> 8 & 255) / 255.0F;
      float b = (float)(hatColor & 255) / 255.0F;
      GL11.glColor3f(r, g, b);
      this.field_78116_c.func_78785_a(f5);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }
}
