package lotr.client.render.entity;

import lotr.client.LOTRTextures;
import lotr.client.model.LOTRModelSpider;
import lotr.common.entity.npc.LOTREntitySpiderBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class LOTRRenderSpiderBase extends RenderLiving {
   private LOTRModelSpider eyesModel = new LOTRModelSpider(0.55F);

   public LOTRRenderSpiderBase() {
      super(new LOTRModelSpider(0.5F), 1.0F);
      this.func_77042_a(new LOTRModelSpider(0.5F));
   }

   public void func_76986_a(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
      super.func_76986_a(entity, d, d1, d2, f, f1);
      if (Minecraft.func_71382_s() && ((LOTREntitySpiderBase)entity).hiredNPCInfo.getHiringPlayer() == this.field_76990_c.field_78734_h) {
         LOTRNPCRendering.renderHiredIcon(entity, d, d1 + 0.5D, d2);
         LOTRNPCRendering.renderNPCHealthBar(entity, d, d1 + 0.5D, d2);
      }

   }

   protected void func_77036_a(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.func_77036_a(entity, f, f1, f2, f3, f4, f5);
      ResourceLocation eyes1 = LOTRTextures.getEyesTexture(this.func_110775_a(entity), new int[][]{{39, 10}, {42, 11}, {44, 11}, {47, 10}}, 2, 2);
      ResourceLocation eyes2 = LOTRTextures.getEyesTexture(this.func_110775_a(entity), new int[][]{{41, 8}, {42, 9}, {45, 9}, {46, 8}}, 1, 1);
      LOTRGlowingEyes.renderGlowingEyes(entity, eyes1, this.eyesModel, f, f1, f2, f3, f4, f5);
      LOTRGlowingEyes.renderGlowingEyes(entity, eyes2, this.eyesModel, f, f1, f2, f3, f4, f5);
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      float scale = ((LOTREntitySpiderBase)entity).getSpiderScaleAmount();
      GL11.glScalef(scale, scale, scale);
   }

   protected float func_77037_a(EntityLivingBase entity) {
      return 180.0F;
   }
}
