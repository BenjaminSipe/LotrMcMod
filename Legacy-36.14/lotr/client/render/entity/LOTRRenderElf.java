package lotr.client.render.entity;

import java.awt.Color;
import lotr.client.LOTRTextures;
import lotr.client.model.LOTRModelElf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDorwinionElf;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityHighElf;
import lotr.common.entity.npc.LOTREntityRivendellElf;
import lotr.common.entity.npc.LOTREntityTormentedElf;
import lotr.common.entity.npc.LOTREntityWoodElf;
import lotr.common.item.LOTRItemRing;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderElf extends LOTRRenderBiped {
   private static LOTRRandomSkins galadhrimSkinsMale;
   private static LOTRRandomSkins galadhrimSkinsFemale;
   private static LOTRRandomSkins woodElfSkinsMale;
   private static LOTRRandomSkins woodElfSkinsFemale;
   private static LOTRRandomSkins highElfSkinsMale;
   private static LOTRRandomSkins highElfSkinsFemale;
   private static LOTRRandomSkins dorwinionSkinsMale;
   private static LOTRRandomSkins dorwinionSkinsFemale;
   private static LOTRRandomSkins tormentedElfSkins;
   private static LOTRRandomSkins jazzSkinsMale;
   private static LOTRRandomSkins jazzSkinsFemale;
   private static LOTRRandomSkins jazzOutfits;
   private LOTRModelElf eyesModel = new LOTRModelElf(0.05F, 64, 64);
   private LOTRModelElf outfitModel = new LOTRModelElf(0.6F, 64, 64);

   public LOTRRenderElf() {
      super(new LOTRModelElf(), 0.5F);
      galadhrimSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/galadhrim_male");
      galadhrimSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/galadhrim_female");
      woodElfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/woodElf_male");
      woodElfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/woodElf_female");
      highElfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/highElf_male");
      highElfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/highElf_female");
      dorwinionSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/dorwinion_male");
      dorwinionSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/dorwinion_female");
      tormentedElfSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/tormented");
      jazzSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/jazz_male");
      jazzSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/jazz_female");
      jazzOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/jazz_outfit");
   }

   protected void func_82421_b() {
      this.field_82423_g = new LOTRModelElf(1.0F);
      this.field_82425_h = new LOTRModelElf(0.5F);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityElf elf = (LOTREntityElf)entity;
      boolean male = elf.familyInfo.isMale();
      if (elf.isJazz()) {
         return male ? jazzSkinsMale.getRandomSkin(elf) : jazzSkinsFemale.getRandomSkin(elf);
      } else if (elf instanceof LOTREntityTormentedElf) {
         return tormentedElfSkins.getRandomSkin(elf);
      } else if (elf instanceof LOTREntityDorwinionElf) {
         return male ? dorwinionSkinsMale.getRandomSkin(elf) : dorwinionSkinsFemale.getRandomSkin(elf);
      } else if (!(elf instanceof LOTREntityHighElf) && !(elf instanceof LOTREntityRivendellElf)) {
         if (elf instanceof LOTREntityWoodElf) {
            return male ? woodElfSkinsMale.getRandomSkin(elf) : woodElfSkinsFemale.getRandomSkin(elf);
         } else {
            return male ? galadhrimSkinsMale.getRandomSkin(elf) : galadhrimSkinsFemale.getRandomSkin(elf);
         }
      } else {
         return male ? highElfSkinsMale.getRandomSkin(elf) : highElfSkinsFemale.getRandomSkin(elf);
      }
   }

   protected void func_77036_a(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.func_77036_a(entity, f, f1, f2, f3, f4, f5);
      if (entity instanceof LOTREntityTormentedElf) {
         ResourceLocation eyes = LOTRTextures.getEyesTexture(this.func_110775_a(entity), new int[][]{{9, 12}, {13, 12}}, 2, 1);
         LOTRGlowingEyes.renderGlowingEyes(entity, eyes, this.eyesModel, f, f1, f2, f3, f4, f5);
      }

   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityElf elf = (LOTREntityElf)entity;
      if (elf.isJazz() && pass == 0 && elf.func_71124_b(4) == null && LOTRRandomSkins.nextInt(elf, 2) == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(jazzOutfits.getRandomSkin(elf));
         return 1;
      } else {
         return super.func_77032_a(elf, pass, f);
      }
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      super.func_77041_b(entity, f);
      LOTREntityElf elf = (LOTREntityElf)entity;
      if (LOTRMod.isAprilFools()) {
         GL11.glScalef(0.25F, 0.25F, 0.25F);
      }

      if (elf.isJazz() && elf.isSolo()) {
         float hue = ((float)elf.field_70173_aa + f) / 20.0F;
         hue %= 360.0F;
         float sat = 0.5F;
         Color color = Color.getHSBColor(hue, sat, 1.0F);
         float r = (float)color.getRed() / 255.0F;
         float g = (float)color.getGreen() / 255.0F;
         float b = (float)color.getBlue() / 255.0F;
         GL11.glColor3f(r, g, b);
         float soloSpin = elf.getSoloSpin(f);
         GL11.glRotatef(soloSpin, 0.0F, 1.0F, 0.0F);
      }

   }

   protected void func_77029_c(EntityLivingBase entity, float f) {
      super.func_77029_c(entity, f);
      LOTREntityElf elf = (LOTREntityElf)entity;
      if (elf.isJazz() && elf.isSolo()) {
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 0.75F, 0.1F);
         GL11.glScalef(1.0F, -1.0F, 1.0F);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
         TextureManager texturemanager = this.field_76990_c.field_78724_e;
         texturemanager.func_110577_a(TextureMap.field_110576_c);
         TextureUtil.func_152777_a(false, false, 1.0F);
         Tessellator tessellator = Tessellator.field_78398_a;
         IIcon icon = LOTRItemRing.saxIcon;
         float minU = icon.func_94209_e();
         float maxU = icon.func_94212_f();
         float minV = icon.func_94206_g();
         float maxV = icon.func_94210_h();
         GL11.glEnable(32826);
         ItemRenderer.func_78439_a(tessellator, maxU, minV, minU, maxV, icon.func_94211_a(), icon.func_94216_b(), 0.0625F);
         GL11.glDisable(32826);
         texturemanager.func_110577_a(TextureMap.field_110576_c);
         TextureUtil.func_147945_b();
         GL11.glPopMatrix();
      }

   }
}
