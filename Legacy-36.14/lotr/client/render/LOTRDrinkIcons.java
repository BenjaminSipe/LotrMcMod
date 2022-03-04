package lotr.client.render;

import cpw.mods.fml.common.FMLLog;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class LOTRDrinkIcons {
   private static int maskColor = 16711935;
   private static Map vesselIcons = new HashMap();
   private static Map liquidIcons = new HashMap();

   public static IIcon registerDrinkIcon(IIconRegister iconregister, Item item, String itemName, String vessel) {
      Minecraft mc = Minecraft.func_71410_x();
      IResourceManager resourceManager = mc.func_110442_L();
      TextureMap textureMap = (TextureMap)iconregister;
      String baseIconName = itemName.substring("lotr:".length());

      try {
         BufferedImage vesselIcon = (BufferedImage)vesselIcons.get(vessel);
         if (vesselIcon == null) {
            ResourceLocation res = new ResourceLocation("lotr", "textures/items/drink_" + vessel + ".png");
            vesselIcon = ImageIO.read(resourceManager.func_110536_a(res).func_110527_b());
            vesselIcons.put(vessel, vesselIcon);
         }

         BufferedImage liquidIcon = (BufferedImage)liquidIcons.get(item);
         if (liquidIcon == null) {
            ResourceLocation res = new ResourceLocation("lotr", "textures/items/" + baseIconName + "_liquid.png");
            liquidIcon = ImageIO.read(resourceManager.func_110536_a(res).func_110527_b());
            liquidIcons.put(item, liquidIcon);
         }

         String iconName = "lotr:textures/items/" + baseIconName + "_" + vessel;
         int iconWidth = vesselIcon.getWidth();
         int iconHeight = vesselIcon.getHeight();
         BufferedImage iconImage = new BufferedImage(iconWidth, iconHeight, 2);

         for(int i = 0; i < iconImage.getWidth(); ++i) {
            for(int j = 0; j < iconImage.getHeight(); ++j) {
               int rgb = vesselIcon.getRGB(i, j);
               if ((rgb & 16777215) == maskColor) {
                  rgb = liquidIcon.getRGB(i, j);
               }

               iconImage.setRGB(i, j, rgb);
            }
         }

         LOTRBufferedImageIcon icon = new LOTRBufferedImageIcon(iconName, iconImage);
         icon.func_110966_b(iconImage.getWidth());
         icon.func_110969_c(iconImage.getHeight());
         textureMap.setTextureEntry(iconName, icon);
         return icon;
      } catch (IOException var17) {
         FMLLog.severe("Failed to load mug textures for %s", new Object[]{item.func_77658_a()});
         var17.printStackTrace();
         return Minecraft.func_71410_x().func_147117_R().func_110572_b("");
      }
   }

   public static IIcon registerLiquidIcon(IIconRegister iconregister, Item item, String itemName) {
      return iconregister.func_94245_a(itemName + "_liquid");
   }
}
