package lotr.client.render.entity;

import lotr.client.LOTRTextures;
import lotr.client.model.LOTRModelOrc;
import lotr.common.entity.npc.LOTREntityBlackUruk;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.entity.npc.LOTREntityUrukHai;
import lotr.common.entity.npc.LOTREntityUrukHaiBerserker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderOrc extends LOTRRenderBiped {
   private static LOTRRandomSkins orcSkins;
   private static LOTRRandomSkins urukSkins;
   private static LOTRRandomSkins blackUrukSkins;
   private LOTRModelOrc eyesModel = new LOTRModelOrc(0.05F);

   public LOTRRenderOrc() {
      super(new LOTRModelOrc(), 0.5F);
      orcSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/orc");
      urukSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/urukHai");
      blackUrukSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/orc/blackUruk");
   }

   protected void func_82421_b() {
      this.field_82423_g = new LOTRModelOrc(1.0F);
      this.field_82425_h = new LOTRModelOrc(0.5F);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityOrc orc = (LOTREntityOrc)entity;
      if (orc instanceof LOTREntityUrukHai) {
         return urukSkins.getRandomSkin(orc);
      } else {
         return orc instanceof LOTREntityBlackUruk ? blackUrukSkins.getRandomSkin(orc) : orcSkins.getRandomSkin(orc);
      }
   }

   protected void func_77036_a(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.func_77036_a(entity, f, f1, f2, f3, f4, f5);
      ResourceLocation eyes = LOTRTextures.getEyesTexture(this.func_110775_a(entity), new int[][]{{9, 11}, {13, 11}}, 2, 1);
      LOTRGlowingEyes.renderGlowingEyes(entity, eyes, this.eyesModel, f, f1, f2, f3, f4, f5);
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      super.func_77041_b(entity, f);
      LOTREntityOrc orc = (LOTREntityOrc)entity;
      if (orc.isWeakOrc) {
         GL11.glScalef(0.85F, 0.85F, 0.85F);
      } else if (orc instanceof LOTREntityUrukHaiBerserker) {
         float scale = LOTREntityUrukHaiBerserker.BERSERKER_SCALE;
         GL11.glScalef(scale, scale, scale);
      }

   }
}
