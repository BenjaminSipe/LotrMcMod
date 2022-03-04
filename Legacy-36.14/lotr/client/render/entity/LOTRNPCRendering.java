package lotr.client.render.entity;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import lotr.client.LOTRSpeechClient;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.item.LOTRItemRedBook;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRNPCRendering {
   private static RenderItem itemRenderer = new RenderItem();
   private static final int SPEECH_WIDTH = 150;
   private static final float SPEECH_SCALE = 0.015F;

   public static void renderAllNPCSpeeches(Minecraft mc, World world, float f) {
      GL11.glPushMatrix();
      RenderHelper.func_74519_b();
      GL11.glAlphaFunc(516, 0.01F);
      double d0 = RenderManager.field_78725_b;
      double d1 = RenderManager.field_78726_c;
      double d2 = RenderManager.field_78723_d;
      Iterator var9 = world.field_72996_f.iterator();

      while(var9.hasNext()) {
         Object obj = var9.next();
         Entity entity = (Entity)obj;
         boolean inRange = entity.func_145770_h(d0, d1, d2);
         if (entity instanceof LOTREntityNPC && inRange) {
            LOTREntityNPC npc = (LOTREntityNPC)entity;
            if (!npc.func_70089_S()) {
               LOTRSpeechClient.removeSpeech(npc);
            } else {
               LOTRSpeechClient.TimedSpeech timedSpeech = LOTRSpeechClient.getSpeechFor(npc);
               if (timedSpeech != null) {
                  double d3 = npc.field_70142_S + (npc.field_70165_t - npc.field_70142_S) * (double)f;
                  double d4 = npc.field_70137_T + (npc.field_70163_u - npc.field_70137_T) * (double)f;
                  double d5 = npc.field_70136_U + (npc.field_70161_v - npc.field_70136_U) * (double)f;
                  renderSpeech(npc, timedSpeech.getSpeech(), timedSpeech.getAge(), d3 - d0, d4 - d1, d5 - d2);
               }
            }
         }
      }

      GL11.glAlphaFunc(516, 0.1F);
      RenderHelper.func_74518_a();
      mc.field_71460_t.func_78483_a((double)f);
      GL11.glPopMatrix();
   }

   public static void renderSpeech(EntityLivingBase entity, String speech, float speechAge, double d, double d1, double d2) {
      Minecraft mc = Minecraft.func_71410_x();
      World world = mc.field_71441_e;
      world.field_72984_F.func_76320_a("renderNPCSpeech");
      TextureManager textureManager = mc.func_110434_K();
      RenderManager renderManager = RenderManager.field_78727_a;
      FontRenderer fr = mc.field_71466_p;
      double distance = (double)RendererLivingEntity.NAME_TAG_RANGE;
      double distanceSq = entity.func_70068_e(renderManager.field_78734_h);
      if (distanceSq <= distance * distance) {
         String name = EnumChatFormatting.YELLOW + entity.func_70005_c_();
         int fontHeight = fr.field_78288_b;
         List speechLines = fr.func_78271_c(speech, 150);
         float alpha = 0.8F;
         if (speechAge < 0.1F) {
            alpha *= speechAge / 0.1F;
         }

         int intAlpha = (int)(alpha * 255.0F);
         GL11.glPushMatrix();
         GL11.glTranslatef((float)d, (float)d1 + entity.field_70131_O + 0.3F, (float)d2);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-renderManager.field_78735_i, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(renderManager.field_78732_j, 1.0F, 0.0F, 0.0F);
         GL11.glDisable(2896);
         GL11.glDepthMask(false);
         GL11.glDisable(2929);
         GL11.glEnable(3042);
         OpenGlHelper.func_148821_a(770, 771, 1, 0);
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         Tessellator tessellator = Tessellator.field_78398_a;
         float scale = 0.015F;
         GL11.glScalef(-scale, -scale, scale);
         GL11.glTranslatef(0.0F, (float)(-fontHeight * (3 + speechLines.size())), 0.0F);
         GL11.glDisable(3553);
         tessellator.func_78382_b();
         tessellator.func_78369_a(0.0F, 0.0F, 0.0F, 0.25F * alpha);
         int halfNameW = fr.func_78256_a(name) / 2;
         tessellator.func_78377_a((double)(-halfNameW - 1), 0.0D, 0.0D);
         tessellator.func_78377_a((double)(-halfNameW - 1), (double)fontHeight, 0.0D);
         tessellator.func_78377_a((double)(halfNameW + 1), (double)fontHeight, 0.0D);
         tessellator.func_78377_a((double)(halfNameW + 1), 0.0D, 0.0D);
         tessellator.func_78381_a();
         GL11.glEnable(3553);
         fr.func_78276_b(name, -halfNameW, 0, intAlpha << 24 | 16777215);
         GL11.glTranslatef(0.0F, (float)fontHeight, 0.0F);
         Iterator var26 = speechLines.iterator();

         while(var26.hasNext()) {
            String line = (String)var26.next();
            GL11.glTranslatef(0.0F, (float)fontHeight, 0.0F);
            GL11.glDisable(3553);
            tessellator.func_78382_b();
            tessellator.func_78369_a(0.0F, 0.0F, 0.0F, 0.25F * alpha);
            int halfLineW = fr.func_78256_a(line) / 2;
            tessellator.func_78377_a((double)(-halfLineW - 1), 0.0D, 0.0D);
            tessellator.func_78377_a((double)(-halfLineW - 1), (double)fontHeight, 0.0D);
            tessellator.func_78377_a((double)(halfLineW + 1), (double)fontHeight, 0.0D);
            tessellator.func_78377_a((double)(halfLineW + 1), 0.0D, 0.0D);
            tessellator.func_78381_a();
            GL11.glEnable(3553);
            fr.func_78276_b(line, -halfLineW, 0, intAlpha << 24 | 16777215);
         }

         GL11.glDisable(3042);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glEnable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }

      world.field_72984_F.func_76319_b();
   }

   private static float calcSpeechDisplacement(LOTREntityNPC npc) {
      LOTRSpeechClient.TimedSpeech timedSpeech = LOTRSpeechClient.getSpeechFor(npc);
      if (timedSpeech != null) {
         FontRenderer fr = Minecraft.func_71410_x().field_71466_p;
         int fontHeight = fr.field_78288_b;
         int numLines = fr.func_78271_c(timedSpeech.getSpeech(), 150).size();
         float f = (float)(fontHeight * (3 + numLines));
         return f * 0.015F;
      } else {
         return 0.0F;
      }
   }

   public static void renderHiredIcon(EntityLivingBase entity, double d, double d1, double d2) {
      if (LOTRConfig.hiredUnitIcons) {
         if (!(entity.field_70153_n instanceof LOTREntityNPC)) {
            if (entity instanceof LOTREntityNPC && LOTRSpeechClient.hasSpeech((LOTREntityNPC)entity)) {
               d1 += (double)calcSpeechDisplacement((LOTREntityNPC)entity);
            }

            Minecraft mc = Minecraft.func_71410_x();
            TextureManager textureManager = mc.func_110434_K();
            FontRenderer fr = mc.field_71466_p;
            RenderManager renderManager = RenderManager.field_78727_a;
            EntityLivingBase viewer = renderManager.field_78734_h;
            World world = mc.field_71441_e;
            world.field_72984_F.func_76320_a("renderHiredIcon");
            double distance = (double)RendererLivingEntity.NAME_TAG_RANGE;
            double distanceSq = entity.func_70068_e(viewer);
            if (distanceSq <= distance * distance) {
               ItemStack hiredIcon = entity.func_70694_bm();
               int displayLevel = -1;
               String squadron = null;
               if (entity instanceof LOTREntityNPC) {
                  LOTREntityNPC npc = (LOTREntityNPC)entity;
                  LOTRHiredNPCInfo hiredInfo = npc.hiredNPCInfo;
                  if (hiredInfo.getTask().displayXpLevel) {
                     displayLevel = hiredInfo.xpLevel;
                  }

                  String sq = hiredInfo.getSquadron();
                  if (!StringUtils.func_151246_b(sq)) {
                     squadron = sq;
                  }
               }

               GL11.glPushMatrix();
               GL11.glTranslatef((float)d, (float)d1 + entity.field_70131_O, (float)d2);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-renderManager.field_78735_i, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(renderManager.field_78732_j, 1.0F, 0.0F, 0.0F);
               GL11.glDisable(2896);
               GL11.glDepthMask(false);
               GL11.glDisable(2929);
               GL11.glEnable(3042);
               OpenGlHelper.func_148821_a(770, 771, 1, 0);
               OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               if (squadron != null) {
                  GL11.glTranslatef(0.0F, 0.3F, 0.0F);
                  GL11.glPushMatrix();
                  Tessellator tessellator = Tessellator.field_78398_a;
                  int halfWidth = fr.func_78256_a(squadron) / 2;
                  float boxScale = 0.015F;
                  GL11.glScalef(-boxScale, -boxScale, boxScale);
                  GL11.glDisable(3553);
                  tessellator.func_78382_b();
                  tessellator.func_78369_a(0.0F, 0.0F, 0.0F, 0.25F);
                  tessellator.func_78377_a((double)(-halfWidth - 1), -9.0D, 0.0D);
                  tessellator.func_78377_a((double)(-halfWidth - 1), 0.0D, 0.0D);
                  tessellator.func_78377_a((double)(halfWidth + 1), 0.0D, 0.0D);
                  tessellator.func_78377_a((double)(halfWidth + 1), -9.0D, 0.0D);
                  tessellator.func_78381_a();
                  GL11.glEnable(3553);
                  fr.func_78276_b(squadron, -halfWidth, -8, 553648127);
                  GL11.glEnable(2929);
                  GL11.glDepthMask(true);
                  fr.func_78276_b(squadron, -halfWidth, -8, -1);
                  GL11.glDisable(2929);
                  GL11.glDepthMask(false);
                  GL11.glPopMatrix();
               }

               float itemScale;
               if (viewer.func_70093_af() && displayLevel >= 0) {
                  GL11.glPushMatrix();
                  GL11.glTranslatef(0.0F, 0.5F, 0.0F);
                  GL11.glScalef(-1.0F, -1.0F, 1.0F);
                  itemScale = 0.03F;
                  GL11.glScalef(itemScale, itemScale, itemScale);
                  String s = String.valueOf(displayLevel);
                  LOTRTickHandlerClient.drawBorderedText(fr, -fr.func_78256_a(s) / 2, 0, s, 16733440, 1.0F);
                  GL11.glPopMatrix();
               } else if (hiredIcon != null) {
                  GL11.glPushMatrix();
                  GL11.glTranslatef(0.0F, 0.5F, 0.0F);
                  GL11.glScalef(-1.0F, -1.0F, 1.0F);
                  itemScale = 0.03F;
                  GL11.glScalef(itemScale, itemScale, itemScale);
                  textureManager.func_110577_a(TextureMap.field_110576_c);
                  itemRenderer.func_94149_a(-8, -8, hiredIcon.func_77954_c(), 16, 16);
                  GL11.glPopMatrix();
               }

               GL11.glDisable(3042);
               GL11.glEnable(2929);
               GL11.glDepthMask(true);
               GL11.glEnable(2896);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glPopMatrix();
            }

            world.field_72984_F.func_76319_b();
         }
      }
   }

   public static void renderNPCHealthBar(EntityLivingBase entity, double d, double d1, double d2) {
      if (LOTRConfig.hiredUnitHealthBars) {
         if (!(entity.field_70153_n instanceof LOTREntityNPC)) {
            if (entity instanceof LOTREntityNPC && LOTRSpeechClient.hasSpeech((LOTREntityNPC)entity)) {
               d1 += (double)calcSpeechDisplacement((LOTREntityNPC)entity);
            }

            renderHealthBar(entity, d, d1, d2, new int[]{5888860, 12006707}, new int[]{6079225, 12006707});
         }
      }
   }

   public static void renderHealthBar(EntityLivingBase entity, double d, double d1, double d2, int[] colors, int[] mountColors) {
      Minecraft mc = Minecraft.func_71410_x();
      World world = mc.field_71441_e;
      world.field_72984_F.func_76320_a("renderHealthBar");
      RenderManager renderManager = RenderManager.field_78727_a;
      double distance = (double)RendererLivingEntity.NAME_TAG_RANGE;
      double distanceSq = entity.func_70068_e(renderManager.field_78734_h);
      if (distanceSq <= distance * distance) {
         float f1 = 1.6F;
         float f2 = 0.016666666F * f1;
         GL11.glPushMatrix();
         GL11.glTranslatef((float)d, (float)d1 + entity.field_70131_O + 0.7F, (float)d2);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-renderManager.field_78735_i, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(renderManager.field_78732_j, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(-f2, -f2, f2);
         GL11.glDisable(2896);
         GL11.glDepthMask(false);
         GL11.glDisable(2929);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         Tessellator tessellator = Tessellator.field_78398_a;
         GL11.glDisable(3553);
         int colorHealth = colors[0];
         int colorBase = colors[1];
         tessellator.func_78382_b();
         tessellator.func_78378_d(0);
         tessellator.func_78377_a(-19.5D, 18.5D, 0.0D);
         tessellator.func_78377_a(-19.5D, 21.0D, 0.0D);
         tessellator.func_78377_a(19.5D, 21.0D, 0.0D);
         tessellator.func_78377_a(19.5D, 18.5D, 0.0D);
         tessellator.func_78381_a();
         tessellator.func_78382_b();
         tessellator.func_78378_d(colorBase);
         tessellator.func_78377_a(-19.0D, 19.0D, 0.0D);
         tessellator.func_78377_a(-19.0D, 20.5D, 0.0D);
         tessellator.func_78377_a(19.0D, 20.5D, 0.0D);
         tessellator.func_78377_a(19.0D, 19.0D, 0.0D);
         tessellator.func_78381_a();
         double healthRemaining = (double)(entity.func_110143_aJ() / entity.func_110138_aP());
         if (healthRemaining < 0.0D) {
            healthRemaining = 0.0D;
         }

         tessellator.func_78382_b();
         tessellator.func_78378_d(colorHealth);
         tessellator.func_78377_a(-19.0D, 19.0D, 0.0D);
         tessellator.func_78377_a(-19.0D, 20.5D, 0.0D);
         tessellator.func_78377_a(-19.0D + 38.0D * healthRemaining, 20.5D, 0.0D);
         tessellator.func_78377_a(-19.0D + 38.0D * healthRemaining, 19.0D, 0.0D);
         tessellator.func_78381_a();
         if (mountColors != null && entity.field_70154_o instanceof EntityLivingBase) {
            EntityLivingBase mount = (EntityLivingBase)entity.field_70154_o;
            int mountColorHealth = mountColors[0];
            int mountColorBase = mountColors[1];
            tessellator.func_78382_b();
            tessellator.func_78378_d(0);
            tessellator.func_78377_a(-19.5D, 23.5D, 0.0D);
            tessellator.func_78377_a(-19.5D, 26.0D, 0.0D);
            tessellator.func_78377_a(19.5D, 26.0D, 0.0D);
            tessellator.func_78377_a(19.5D, 23.5D, 0.0D);
            tessellator.func_78381_a();
            tessellator.func_78382_b();
            tessellator.func_78378_d(mountColorBase);
            tessellator.func_78377_a(-19.0D, 24.0D, 0.0D);
            tessellator.func_78377_a(-19.0D, 25.5D, 0.0D);
            tessellator.func_78377_a(19.0D, 25.5D, 0.0D);
            tessellator.func_78377_a(19.0D, 24.0D, 0.0D);
            tessellator.func_78381_a();
            double mountHealthRemaining = (double)(mount.func_110143_aJ() / mount.func_110138_aP());
            if (mountHealthRemaining < 0.0D) {
               mountHealthRemaining = 0.0D;
            }

            tessellator.func_78382_b();
            tessellator.func_78378_d(mountColorHealth);
            tessellator.func_78377_a(-19.0D, 24.0D, 0.0D);
            tessellator.func_78377_a(-19.0D, 25.5D, 0.0D);
            tessellator.func_78377_a(-19.0D + 38.0D * mountHealthRemaining, 25.5D, 0.0D);
            tessellator.func_78377_a(-19.0D + 38.0D * mountHealthRemaining, 24.0D, 0.0D);
            tessellator.func_78381_a();
         }

         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glEnable(2896);
         GL11.glDisable(3042);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }

      world.field_72984_F.func_76319_b();
   }

   public static void renderQuestBook(LOTREntityNPC npc, double d, double d1, double d2) {
      Minecraft mc = Minecraft.func_71410_x();
      World world = mc.field_71441_e;
      world.field_72984_F.func_76320_a("renderMiniquestBook");
      float distance = mc.field_71451_h.func_70032_d(npc);
      boolean aboveHead = (double)distance <= LOTRMiniQuest.RENDER_HEAD_DISTANCE;
      TextureManager textureManager = mc.func_110434_K();
      RenderManager renderManager = RenderManager.field_78727_a;
      EntityPlayer entityplayer = mc.field_71439_g;
      if (!LOTRLevelData.getData((EntityPlayer)entityplayer).getMiniQuestsForEntity(npc, true).isEmpty() && !LOTRSpeechClient.hasSpeech(npc)) {
         ItemStack questBook = new ItemStack(LOTRMod.redBook);
         IIcon icon = questBook.func_77954_c();
         if (icon == null) {
            icon = ((TextureMap)textureManager.func_110581_b(TextureMap.field_110576_c)).func_110572_b("missingno");
         }

         Tessellator tessellator = Tessellator.field_78398_a;
         float minU = ((IIcon)icon).func_94209_e();
         float maxU = ((IIcon)icon).func_94212_f();
         float minV = ((IIcon)icon).func_94206_g();
         float maxV = ((IIcon)icon).func_94210_h();
         float age;
         float rotation;
         float scale;
         if (aboveHead) {
            age = (float)npc.field_70173_aa + LOTRTickHandlerClient.renderTick;
            rotation = age % 360.0F;
            rotation *= 6.0F;
            GL11.glPushMatrix();
            GL11.glEnable(32826);
            GL11.glDisable(2896);
            GL11.glTranslatef((float)d, (float)d1 + npc.field_70131_O + 1.3F, (float)d2);
            scale = 1.0F;
            GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F * scale, -0.5F * scale, 0.03125F * scale);
            GL11.glScalef(scale, scale, scale);
            textureManager.func_110577_a(TextureMap.field_110576_c);
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ItemRenderer.func_78439_a(tessellator, maxU, minV, minU, maxV, ((IIcon)icon).func_94211_a(), ((IIcon)icon).func_94216_b(), 0.0625F);
            GL11.glEnable(2896);
            GL11.glDisable(32826);
            GL11.glPopMatrix();
         } else {
            age = distance / (float)LOTRMiniQuest.RENDER_HEAD_DISTANCE;
            age = (float)Math.pow((double)age, 1.1D);
            rotation = (float)Math.pow((double)age, -0.4D);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d, (float)d1 + npc.field_70131_O + 1.3F, (float)d2);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-renderManager.field_78735_i, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(renderManager.field_78732_j, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(age, age, age);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            OpenGlHelper.func_148821_a(770, 771, 1, 0);
            textureManager.func_110577_a(TextureMap.field_110576_c);
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, rotation);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, rotation);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            scale = 0.0625F;
            GL11.glScalef(scale, scale, scale);
            textureManager.func_110577_a(TextureMap.field_110576_c);
            itemRenderer.func_94149_a(-8, -8, (IIcon)icon, 16, 16);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glEnable(2896);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }

      world.field_72984_F.func_76319_b();
   }

   public static void renderQuestOffer(LOTREntityNPC npc, double d, double d1, double d2) {
      Minecraft mc = Minecraft.func_71410_x();
      World world = mc.field_71441_e;
      world.field_72984_F.func_76320_a("renderMiniquestoffer");
      if (npc.func_70089_S() && npc.questInfo.clientIsOffering && !LOTRSpeechClient.hasSpeech(npc)) {
         EntityPlayer entityplayer = mc.field_71439_g;
         float distance = mc.field_71451_h.func_70032_d(npc);
         if ((double)distance <= 16.0D && LOTRLevelData.getData((EntityPlayer)entityplayer).getMiniQuestsForEntity(npc, true).isEmpty()) {
            TextureManager textureManager = mc.func_110434_K();
            RenderManager renderManager = RenderManager.field_78727_a;
            IIcon icon = LOTRItemRedBook.questOfferIcon;
            Tessellator tessellator = Tessellator.field_78398_a;
            float minU = icon.func_94209_e();
            float maxU = icon.func_94212_f();
            float minV = icon.func_94206_g();
            float maxV = icon.func_94210_h();
            float scale = 0.75F;
            float alpha = 1.0F;
            int questColor = npc.questInfo.clientOfferColor;
            float[] questRGB = (new Color(questColor)).getColorComponents((float[])null);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d, (float)d1 + npc.field_70131_O + 1.0F, (float)d2);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-renderManager.field_78735_i, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(renderManager.field_78732_j, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(scale, scale, scale);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            OpenGlHelper.func_148821_a(770, 771, 1, 0);
            textureManager.func_110577_a(TextureMap.field_110576_c);
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            float itemScale = 0.0625F;
            GL11.glScalef(itemScale, itemScale, itemScale);
            textureManager.func_110577_a(TextureMap.field_110576_c);
            GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], alpha);
            itemRenderer.func_94149_a(-8, -8, icon, 16, 16);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }

      world.field_72984_F.func_76319_b();
   }
}
