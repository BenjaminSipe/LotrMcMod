package lotr.client.render.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

public class LOTRRenderHorse extends RenderHorse {
   private static Map layeredMountTextures = new HashMap();

   public LOTRRenderHorse() {
      super(new ModelHorse(), 0.75F);
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityHorse horse = (LOTREntityHorse)entity;
      boolean fools = LOTRMod.isAprilFools();
      int horseType = horse.func_110265_bP();
      if (fools) {
         horse.func_110214_p(1);
      }

      super.func_76986_a(entity, d, d1, d2, f, f1);
      if (fools) {
         horse.func_110214_p(horseType);
      }

   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityHorse horse = (LOTREntityHorse)entity;
      ResourceLocation horseSkin = super.func_110775_a(entity);
      return getLayeredMountTexture(horse, horseSkin);
   }

   public static ResourceLocation getLayeredMountTexture(LOTRNPCMount mount, ResourceLocation mountSkin) {
      String skinPath = mountSkin.toString();
      String armorPath = mount.getMountArmorTexture();
      if (armorPath != null && !StringUtils.isBlank(armorPath)) {
         Minecraft mc = Minecraft.func_71410_x();
         String path = "lotr_" + skinPath + "_" + armorPath;
         ResourceLocation texture = (ResourceLocation)layeredMountTextures.get(path);
         if (texture == null) {
            texture = new ResourceLocation(path);
            List layers = new ArrayList();
            ITextureObject skinTexture = mc.func_110434_K().func_110581_b(mountSkin);
            if (skinTexture instanceof LayeredTexture) {
               LayeredTexture skinTextureLayered = (LayeredTexture)skinTexture;
               layers.addAll(skinTextureLayered.field_110567_b);
            } else {
               layers.add(skinPath);
            }

            layers.add(armorPath);
            mc.func_110434_K().func_110579_a(texture, new LayeredTexture((String[])layers.toArray(new String[0])));
            layeredMountTextures.put(path, texture);
         }

         return texture;
      } else {
         return mountSkin;
      }
   }
}
