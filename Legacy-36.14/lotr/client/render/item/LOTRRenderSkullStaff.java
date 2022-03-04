package lotr.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class LOTRRenderSkullStaff implements IItemRenderer {
   private static ModelBase staffModel = new ModelBase() {
      private ModelRenderer staff = new ModelRenderer(this, 0, 0);

      {
         this.staff.func_78790_a(-0.5F, 8.0F, -6.0F, 1, 1, 28, 0.0F);
         this.staff.func_78790_a(-2.5F, 6.0F, -11.0F, 5, 5, 5, 0.0F);
         this.staff.field_78796_g = (float)Math.toRadians(90.0D);
         this.staff.field_78808_h = (float)Math.toRadians(-20.0D);
      }

      public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.staff.func_78785_a(f5);
      }
   };
   private static ResourceLocation staffTexture = new ResourceLocation("lotr:item/skullStaff.png");

   public boolean handleRenderType(ItemStack itemstack, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      Minecraft.func_71410_x().func_110434_K().func_110577_a(staffTexture);
      if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         GL11.glRotatef(-70.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
      }

      staffModel.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
   }
}
