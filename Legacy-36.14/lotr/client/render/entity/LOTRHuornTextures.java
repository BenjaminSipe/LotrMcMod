package lotr.client.render.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.HashMap;
import javax.imageio.ImageIO;
import lotr.common.entity.npc.LOTREntityHuornBase;
import lotr.common.entity.npc.LOTREntityTree;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRHuornTextures implements IResourceManagerReloadListener {
   public static LOTRHuornTextures INSTANCE = new LOTRHuornTextures();
   private RenderManager renderManager;
   private HashMap woodTextures;
   private HashMap leafTexturesFast;
   private HashMap leafTexturesFancy;

   public LOTRHuornTextures() {
      this.renderManager = RenderManager.field_78727_a;
      this.woodTextures = new HashMap();
      this.leafTexturesFast = new HashMap();
      this.leafTexturesFancy = new HashMap();
   }

   public void func_110549_a(IResourceManager resourcemanager) {
      this.woodTextures.clear();
      this.leafTexturesFast.clear();
      this.leafTexturesFancy.clear();
   }

   public void bindWoodTexture(LOTREntityHuornBase entity) {
      int treeType = entity.getTreeType();
      Block block = LOTREntityTree.WOOD_BLOCKS[treeType];
      int meta = LOTREntityTree.WOOD_META[treeType];
      ResourceLocation texture = (ResourceLocation)this.woodTextures.get(treeType);
      if (texture == null) {
         texture = this.getDynamicHuornTexture(block, meta);
         this.woodTextures.put(treeType, texture);
      }

      this.renderManager.field_78724_e.func_110577_a(texture);
   }

   public void bindLeafTexture(LOTREntityHuornBase entity) {
      int treeType = entity.getTreeType();
      Block block = LOTREntityTree.LEAF_BLOCKS[treeType];
      int meta = LOTREntityTree.LEAF_META[treeType];
      ResourceLocation texture = (ResourceLocation)this.leafTexturesFast.get(treeType);
      if (Minecraft.func_71375_t()) {
         texture = (ResourceLocation)this.leafTexturesFancy.get(treeType);
      }

      if (texture == null) {
         texture = this.getDynamicHuornTexture(block, meta);
         if (Minecraft.func_71375_t()) {
            this.leafTexturesFancy.put(treeType, texture);
         } else {
            this.leafTexturesFast.put(treeType, texture);
         }
      }

      this.renderManager.field_78724_e.func_110577_a(texture);
      int color = block.func_149741_i(meta);
      if (block == Blocks.field_150362_t && meta == 0) {
         int i = MathHelper.func_76128_c(entity.field_70165_t);
         int j = MathHelper.func_76128_c(entity.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(entity.field_70161_v);
         color = entity.field_70170_p.func_72807_a(i, k).func_150571_c(i, j, k);
      }

      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      GL11.glColor4f(r, g, b, 1.0F);
   }

   private ResourceLocation getDynamicHuornTexture(Block block, int meta) {
      try {
         boolean aF = Minecraft.func_71410_x().field_71474_y.field_151443_J > 1;
         TextureMap terrainAtlas = (TextureMap)Minecraft.func_71410_x().func_110434_K().func_110581_b(TextureMap.field_110575_b);
         BufferedImage[] icons = new BufferedImage[6];
         int width = block.func_149691_a(0, meta).func_94211_a();
         if (aF) {
            width -= 16;
         }

         for(int i = 0; i < 6; ++i) {
            IIcon icon = block.func_149691_a(i, meta);
            int iconWidth = icon.func_94211_a();
            int iconHeight = icon.func_94216_b();
            if (aF) {
               iconWidth -= 16;
               iconHeight -= 16;
            }

            if (iconWidth != width || iconHeight != width) {
               throw new RuntimeException("Error registering Huorn textures: all icons for block " + block.func_149739_a() + " must have the same texture dimensions");
            }

            ResourceLocation iconPath = new ResourceLocation(icon.func_94215_i());
            ResourceLocation r = new ResourceLocation(iconPath.func_110624_b(), "textures/blocks/" + iconPath.func_110623_a() + ".png");
            BufferedImage iconImage = ImageIO.read(Minecraft.func_71410_x().func_110442_L().func_110536_a(r).func_110527_b());
            icons[i] = iconImage.getSubimage(0, 0, width, width);
         }

         BufferedImage image = new BufferedImage(width * 4, width * 2, 2);
         Graphics2D g2d = image.createGraphics();
         g2d.drawImage(icons[1], (BufferedImageOp)null, width, 0);
         g2d.drawImage(icons[0], (BufferedImageOp)null, width * 2, 0);
         g2d.drawImage(icons[4], (BufferedImageOp)null, 0, width);
         g2d.drawImage(icons[2], (BufferedImageOp)null, width, width);
         g2d.drawImage(icons[5], (BufferedImageOp)null, width * 2, width);
         g2d.drawImage(icons[3], (BufferedImageOp)null, width * 3, width);
         g2d.dispose();
         return this.renderManager.field_78724_e.func_110578_a("lotr:huorn", new DynamicTexture(image));
      } catch (Exception var14) {
         throw new RuntimeException(var14);
      }
   }

   static {
      ((IReloadableResourceManager)Minecraft.func_71410_x().func_110442_L()).func_110542_a(INSTANCE);
   }
}
