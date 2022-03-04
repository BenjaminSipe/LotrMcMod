package lotr.client.render.entity;

import java.util.HashMap;
import java.util.Map;
import lotr.client.LOTRTextures;
import lotr.client.model.LOTRModelWarg;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.entity.npc.LOTREntityWarg;
import lotr.common.entity.npc.LOTREntityWargBombardier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderWarg extends RenderLiving {
   private static Map wargSkins = new HashMap();
   private static ResourceLocation wargSaddle = new ResourceLocation("lotr:mob/warg/saddle.png");
   private LOTRModelWarg saddleModel = new LOTRModelWarg(0.5F);
   private LOTRModelWarg eyesModel = new LOTRModelWarg(0.05F);

   public LOTRRenderWarg() {
      super(new LOTRModelWarg(), 0.5F);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityWarg warg = (LOTREntityWarg)entity;
      ResourceLocation skin = getWargSkin(warg.getWargType());
      return LOTRRenderHorse.getLayeredMountTexture(warg, skin);
   }

   public static ResourceLocation getWargSkin(LOTREntityWarg.WargType type) {
      String s = type.textureName();
      ResourceLocation wargSkin = (ResourceLocation)wargSkins.get(s);
      if (wargSkin == null) {
         wargSkin = new ResourceLocation("lotr:mob/warg/" + s + ".png");
         wargSkins.put(s, wargSkin);
      }

      return wargSkin;
   }

   public void func_76986_a(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
      if (entity instanceof LOTREntityWargBombardier) {
         GL11.glEnable(32826);
         GL11.glPushMatrix();
         GL11.glTranslatef((float)d, (float)d1 + 1.7F, (float)d2);
         GL11.glRotatef(-f, 0.0F, 1.0F, 0.0F);
         int i = entity.func_70070_b(f1);
         int j = i % 65536;
         int k = i / 65536;
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j / 1.0F, (float)k / 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         LOTRRenderOrcBomb bombRenderer = (LOTRRenderOrcBomb)RenderManager.field_78727_a.func_78715_a(LOTREntityOrcBomb.class);
         bombRenderer.renderBomb(entity, 0.0D, 0.0D, 0.0D, f1, ((LOTREntityWargBombardier)entity).getBombFuse(), ((LOTREntityWargBombardier)entity).getBombStrengthLevel(), 0.75F, 1.0F);
         GL11.glPopMatrix();
         GL11.glDisable(32826);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      super.func_76986_a(entity, d, d1, d2, f, f1);
      if (Minecraft.func_71382_s() && ((LOTREntityWarg)entity).hiredNPCInfo.getHiringPlayer() == this.field_76990_c.field_78734_h) {
         LOTRNPCRendering.renderHiredIcon(entity, d, d1 + 0.5D, d2);
         LOTRNPCRendering.renderNPCHealthBar(entity, d, d1 + 0.5D, d2);
      }

   }

   protected void func_77036_a(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.func_77036_a(entity, f, f1, f2, f3, f4, f5);
      LOTREntityWarg warg = (LOTREntityWarg)entity;
      ResourceLocation eyes = LOTRTextures.getEyesTexture(getWargSkin(warg.getWargType()), new int[][]{{100, 12}, {108, 12}}, 2, 1);
      LOTRGlowingEyes.renderGlowingEyes(entity, eyes, this.eyesModel, f, f1, f2, f3, f4, f5);
   }

   protected int func_77032_a(EntityLivingBase entity, int pass, float f) {
      LOTREntityWarg warg = (LOTREntityWarg)entity;
      if (pass == 0 && warg.isMountSaddled()) {
         this.func_110776_a(wargSaddle);
         this.func_77042_a(this.saddleModel);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      if (LOTRMod.isAprilFools()) {
         GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
      }

   }
}
