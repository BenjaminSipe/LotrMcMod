package lotr.client.render.entity;

import java.util.Iterator;
import lotr.client.LOTRClientProxy;
import lotr.client.LOTRTickHandlerClient;
import lotr.client.fx.LOTREntityAlignmentBonus;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderAlignmentBonus extends Render {
   public LOTRRenderAlignmentBonus() {
      this.field_76989_e = 0.0F;
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return LOTRClientProxy.alignmentTexture;
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      EntityPlayer entityplayer = Minecraft.func_71410_x().field_71439_g;
      LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
      LOTRFaction viewingFaction = playerData.getViewingFaction();
      LOTREntityAlignmentBonus bonusParticle = (LOTREntityAlignmentBonus)entity;
      LOTRFaction mainFaction = bonusParticle.mainFaction;
      LOTRAlignmentBonusMap factionBonusMap = bonusParticle.factionBonusMap;
      LOTRFaction renderFaction = null;
      boolean showConquest = false;
      float bonus;
      if (bonusParticle.conquestBonus > 0.0F && playerData.isPledgedTo(viewingFaction)) {
         renderFaction = viewingFaction;
         showConquest = true;
      } else if (bonusParticle.conquestBonus >= 0.0F || viewingFaction != mainFaction && !playerData.isPledgedTo(viewingFaction)) {
         if (!factionBonusMap.isEmpty()) {
            if (factionBonusMap.containsKey(viewingFaction)) {
               renderFaction = viewingFaction;
            } else if (factionBonusMap.size() == 1 && mainFaction.isPlayableAlignmentFaction()) {
               renderFaction = mainFaction;
            } else if (mainFaction.isPlayableAlignmentFaction() && bonusParticle.prevMainAlignment >= 0.0F && (Float)factionBonusMap.get(mainFaction) < 0.0F) {
               renderFaction = mainFaction;
            } else {
               Iterator var18 = factionBonusMap.keySet().iterator();

               label127:
               while(true) {
                  LOTRFaction faction;
                  float alignment;
                  do {
                     do {
                        do {
                           if (!var18.hasNext()) {
                              if (renderFaction == null) {
                                 if (!mainFaction.isPlayableAlignmentFaction() || (Float)factionBonusMap.get(mainFaction) >= 0.0F) {
                                    var18 = factionBonusMap.keySet().iterator();

                                    while(true) {
                                       do {
                                          do {
                                             do {
                                                if (!var18.hasNext()) {
                                                   break label127;
                                                }

                                                faction = (LOTRFaction)var18.next();
                                             } while(!faction.isPlayableAlignmentFaction());

                                             bonus = (Float)factionBonusMap.get(faction);
                                          } while(bonus >= 0.0F);

                                          alignment = playerData.getAlignment(faction);
                                       } while(renderFaction != null && alignment <= playerData.getAlignment(renderFaction));

                                       renderFaction = faction;
                                    }
                                 } else {
                                    renderFaction = mainFaction;
                                 }
                              }
                              break label127;
                           }

                           faction = (LOTRFaction)var18.next();
                        } while(!faction.isPlayableAlignmentFaction());

                        bonus = (Float)factionBonusMap.get(faction);
                     } while(bonus <= 0.0F);

                     alignment = playerData.getAlignment(faction);
                  } while(renderFaction != null && alignment <= playerData.getAlignment(renderFaction));

                  renderFaction = faction;
               }
            }
         }
      } else {
         renderFaction = viewingFaction;
         showConquest = true;
      }

      if (renderFaction != null) {
         float alignBonus = factionBonusMap.containsKey(renderFaction) ? (Float)factionBonusMap.get(renderFaction) : 0.0F;
         boolean showAlign = alignBonus != 0.0F;
         bonus = bonusParticle.conquestBonus;
         if (showAlign || showConquest) {
            String title = bonusParticle.name;
            boolean isViewingFaction = renderFaction == viewingFaction;
            boolean showTitle = showAlign || showConquest && !bonusParticle.isHiredKill;
            float particleHealth = (float)bonusParticle.particleAge / (float)bonusParticle.particleMaxAge;
            float alpha = particleHealth < 0.75F ? 1.0F : (1.0F - particleHealth) / 0.25F;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d, (float)d1, (float)d2);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-0.025F, -0.025F, 0.025F);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            this.renderBonusText(isViewingFaction, renderFaction, title, showTitle, alignBonus, showAlign, bonus, showConquest, alpha);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glEnable(2896);
            GL11.glPopMatrix();
         }
      }

   }

   private void renderBonusText(boolean isViewingFaction, LOTRFaction renderFaction, String title, boolean showTitle, float align, boolean showAlign, float conq, boolean showConq, float alpha) {
      Minecraft mc = Minecraft.func_71410_x();
      FontRenderer fr = mc.field_71466_p;
      String strAlign = LOTRAlignmentValues.formatAlignForDisplay(align);
      String strConq = LOTRAlignmentValues.formatConqForDisplay(conq, true);
      boolean negativeConq = conq < 0.0F;
      GL11.glPushMatrix();
      if (!isViewingFaction) {
         float scale = 0.5F;
         GL11.glScalef(scale, scale, 1.0F);
         strAlign = strAlign + " (" + renderFaction.factionName() + "...)";
      }

      int x = -MathHelper.func_76128_c((double)(fr.func_78256_a(strAlign) + 18) / 2.0D);
      int y = -16;
      if (showAlign) {
         this.func_110776_a(LOTRClientProxy.alignmentTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
         LOTRTickHandlerClient.drawTexturedModalRect((double)x, (double)(y - 5), 0, 36, 16, 16);
         LOTRTickHandlerClient.drawAlignmentText(fr, x + 18, y, strAlign, alpha);
         y += 14;
      }

      if (showTitle) {
         x = -MathHelper.func_76128_c((double)fr.func_78256_a(title) / 2.0D);
         if (showAlign) {
            LOTRTickHandlerClient.drawAlignmentText(fr, x, y, title, alpha);
         } else {
            LOTRTickHandlerClient.drawConquestText(fr, x, y, title, negativeConq, alpha);
         }

         y += 16;
      }

      if (showConq) {
         x = -MathHelper.func_76128_c((double)(fr.func_78256_a(strConq) + 18) / 2.0D);
         this.func_110776_a(LOTRClientProxy.alignmentTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
         LOTRTickHandlerClient.drawTexturedModalRect((double)x, (double)(y - 5), negativeConq ? 16 : 0, 228, 16, 16);
         LOTRTickHandlerClient.drawConquestText(fr, x + 18, y, strConq, negativeConq, alpha);
      }

      GL11.glPopMatrix();
   }
}
