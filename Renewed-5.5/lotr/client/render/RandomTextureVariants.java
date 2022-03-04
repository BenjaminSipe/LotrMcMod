package lotr.client.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import lotr.common.LOTRLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.entity.Entity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

public class RandomTextureVariants implements ISelectiveResourceReloadListener {
   private static final Random RAND = new Random();
   private static final Minecraft MC = Minecraft.func_71410_x();
   private static final Map ALL_RANDOM_SKINS = new HashMap();
   private final ResourceLocation skinPath;
   private final List skins = new ArrayList();

   public static RandomTextureVariants loadSkinsList(String namespace, String path) {
      return loadSkinsList(new ResourceLocation(namespace, path));
   }

   public static RandomTextureVariants loadSkinsList(ResourceLocation path) {
      return (RandomTextureVariants)ALL_RANDOM_SKINS.computeIfAbsent(path, RandomTextureVariants::new);
   }

   private RandomTextureVariants(ResourceLocation path) {
      this.skinPath = path;
      IReloadableResourceManager resMgr = (IReloadableResourceManager)MC.func_195551_G();
      resMgr.func_219534_a(this);
      this.loadAllRandomSkins(resMgr);
   }

   private void loadAllRandomSkins(IResourceManager resMgr) {
      this.skins.clear();
      int skinCount = 0;
      int skips = 0;
      int maxSkips = 10;
      boolean foundAfterSkip = false;

      while(true) {
         ResourceLocation skin = new ResourceLocation(this.skinPath.func_110624_b(), this.skinPath.func_110623_a() + "/" + skinCount + ".png");
         boolean noFile = false;

         try {
            if (resMgr.func_199002_a(skin) == null) {
               noFile = true;
            }
         } catch (Exception var9) {
            noFile = true;
         }

         if (noFile) {
            ++skips;
            if (skips >= maxSkips) {
               if (this.skins.isEmpty()) {
                  LOTRLog.warn("No random skins for %s", this.skinPath);
               }

               if (foundAfterSkip) {
                  LOTRLog.warn("Random skins %s skipped a number. This is bad for performance - please number your skins from 0 up, with no gaps!", this.skinPath);
               }

               return;
            }

            ++skinCount;
         } else {
            this.skins.add(skin);
            ++skinCount;
            if (skips > 0) {
               foundAfterSkip = true;
            }
         }
      }
   }

   public ResourceLocation getRandomSkin(Entity entity) {
      if (this.skins.isEmpty()) {
         return MissingTextureSprite.func_195675_b();
      } else {
         int i = nextInt(entity, this.skins.size());
         return (ResourceLocation)this.skins.get(i);
      }
   }

   public ResourceLocation getRandomSkin() {
      if (this.skins.isEmpty()) {
         return MissingTextureSprite.func_195675_b();
      } else {
         int i = RAND.nextInt(this.skins.size());
         return (ResourceLocation)this.skins.get(i);
      }
   }

   public static int nextInt(Entity entity, int n) {
      setRandSeedFromEntity(entity);
      return RAND.nextInt(n);
   }

   public static float nextFloat(Entity entity) {
      setRandSeedFromEntity(entity);
      return RAND.nextFloat();
   }

   private static void setRandSeedFromEntity(Entity entity) {
      UUID entityUuid = entity.func_110124_au();
      long l = entityUuid.getLeastSignificantBits();
      l = l * 29506206L * (l ^ entityUuid.getMostSignificantBits()) + 25859L;
      l = l * l * 426430295004L + 25925025L * l;
      RAND.setSeed(l);
   }

   public List getAllSkins() {
      return this.skins;
   }

   public void onResourceManagerReload(IResourceManager resMgr, Predicate resPredicate) {
      if (resPredicate.test(VanillaResourceType.TEXTURES)) {
         this.loadAllRandomSkins(resMgr);
      }

   }
}
