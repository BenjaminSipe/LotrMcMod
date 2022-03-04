package lotr.client.render.item;

import lotr.client.model.LOTRModelBanner;
import lotr.client.render.entity.LOTRRenderBanner;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBannerItem implements IItemRenderer {
   private static LOTRModelBanner model = new LOTRModelBanner();

   public boolean handleRenderType(ItemStack itemstack, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      GL11.glDisable(2884);
      Entity holder = (Entity)data[1];
      boolean isFirstPerson = holder == Minecraft.func_71410_x().field_71439_g && Minecraft.func_71410_x().field_71474_y.field_74320_O == 0;
      boolean renderStand = false;
      TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
      if (isFirstPerson) {
         GL11.glTranslatef(1.0F, 1.0F, 0.0F);
         GL11.glScalef(-1.0F, 1.0F, 1.0F);
      } else {
         GL11.glTranslatef(-1.5F, 0.85F, -0.1F);
         GL11.glRotatef(75.0F, 0.0F, 0.0F, 1.0F);
      }

      GL11.glScalef(1.0F, -1.0F, 1.0F);
      LOTRItemBanner.BannerType bannerType = LOTRItemBanner.getBannerType(itemstack);
      textureManager.func_110577_a(LOTRRenderBanner.getStandTexture(bannerType));
      if (renderStand) {
         model.renderStand(0.0625F);
      }

      model.renderPost(0.0625F);
      model.renderLowerPost(0.0625F);
      textureManager.func_110577_a(LOTRRenderBanner.getBannerTexture(bannerType));
      model.renderBanner(0.0625F);
   }
}
