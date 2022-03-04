package lotr.client.render;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;

public class LOTRBufferedImageIcon extends TextureAtlasSprite {
   private final String iconName;
   private final BufferedImage imageRGB;
   private static Set loadedResources = new HashSet();

   public LOTRBufferedImageIcon(String s, BufferedImage rgb) {
      super(s);
      this.iconName = s;
      this.imageRGB = rgb;
      if (!loadedResources.contains(s)) {
         TextureManager texManager = Minecraft.func_71410_x().func_110434_K();
         ResourceLocation r = new ResourceLocation(this.iconName);
         ResourceLocation r1 = new ResourceLocation(r.func_110624_b(), String.format("%s%s", r.func_110623_a(), ".png"));
         texManager.func_147645_c(r1);
         texManager.func_110579_a(r1, new DynamicTexture(this.imageRGB));
         loadedResources.add(s);
      }

   }

   public boolean load(IResourceManager resourceManager, ResourceLocation resourceLocation) {
      BufferedImage[] imageArray = new BufferedImage[1 + Minecraft.func_71410_x().field_71474_y.field_151442_I];
      imageArray[0] = this.imageRGB;
      this.func_147964_a(imageArray, (AnimationMetadataSection)null, (float)Minecraft.func_71410_x().field_71474_y.field_151443_J > 1.0F);
      return false;
   }

   public boolean hasCustomLoader(IResourceManager resourceManager, ResourceLocation resourceLocation) {
      return true;
   }
}
