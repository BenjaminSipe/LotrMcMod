package lotr.client.render;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import lotr.client.LOTRClientProxy;
import lotr.client.LOTRTickHandlerClient;
import lotr.client.render.entity.LOTRNPCRendering;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRShields;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderPlayerEvent.Post;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

public class LOTRRenderPlayer {
   private Minecraft mc = Minecraft.func_71410_x();
   private RenderManager renderManager;

   public LOTRRenderPlayer() {
      this.renderManager = RenderManager.field_78727_a;
      FMLCommonHandler.instance().bus().register(this);
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void preRenderSpecials(Pre event) {
      EntityPlayer entityplayer = event.entityPlayer;
      float tick = event.partialRenderTick;
      LOTRShields shield = LOTRLevelData.getData(entityplayer).getShield();
      if (shield != null) {
         if (!entityplayer.func_82150_aj()) {
            LOTRRenderShield.renderShield(shield, entityplayer, event.renderer.field_77109_a);
         } else if (!entityplayer.func_98034_c(this.mc.field_71439_g)) {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
            GL11.glDepthMask(false);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glAlphaFunc(516, 0.003921569F);
            LOTRRenderShield.renderShield(shield, entityplayer, event.renderer.field_77109_a);
            GL11.glDisable(3042);
            GL11.glAlphaFunc(516, 0.1F);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
         }
      }

   }

   @SubscribeEvent
   public void postRender(Post event) {
      EntityPlayer entityplayer = event.entityPlayer;
      float tick = event.partialRenderTick;
      double d0 = RenderManager.field_78725_b;
      double d1 = RenderManager.field_78726_c;
      double d2 = RenderManager.field_78723_d;
      float f0 = (float)entityplayer.field_70142_S + (float)(entityplayer.field_70165_t - entityplayer.field_70142_S) * tick;
      float f1 = (float)entityplayer.field_70137_T + (float)(entityplayer.field_70163_u - entityplayer.field_70137_T) * tick;
      float f2 = (float)entityplayer.field_70136_U + (float)(entityplayer.field_70161_v - entityplayer.field_70136_U) * tick;
      float fr0 = f0 - (float)d0;
      float fr1 = f1 - (float)d1;
      float fr2 = f2 - (float)d2;
      float yOffset = entityplayer.func_70608_bn() ? -1.5F : 0.0F;
      if (this.shouldRenderAlignment(entityplayer) && (this.mc.field_71441_e.field_73011_w instanceof LOTRWorldProvider || LOTRConfig.alwaysShowAlignment)) {
         LOTRPlayerData clientPD = LOTRLevelData.getData((EntityPlayer)this.mc.field_71439_g);
         LOTRPlayerData otherPD = LOTRLevelData.getData(entityplayer);
         float alignment = otherPD.getAlignment(clientPD.getViewingFaction());
         double dist = entityplayer.func_70068_e(this.renderManager.field_78734_h);
         float range = RendererLivingEntity.NAME_TAG_RANGE;
         if (dist < (double)(range * range)) {
            FontRenderer fr = Minecraft.func_71410_x().field_71466_p;
            GL11.glPushMatrix();
            GL11.glTranslatef(fr0, fr1, fr2);
            GL11.glTranslatef(0.0F, entityplayer.field_70131_O + 0.6F + yOffset, 0.0F);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-this.renderManager.field_78735_i, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.renderManager.field_78732_j, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            float scale = 0.025F;
            GL11.glScalef(scale, scale, scale);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            String sAlign = LOTRAlignmentValues.formatAlignForDisplay(alignment);
            this.mc.func_110434_K().func_110577_a(LOTRClientProxy.alignmentTexture);
            LOTRTickHandlerClient.drawTexturedModalRect((double)(-MathHelper.func_76128_c((double)(fr.func_78256_a(sAlign) + 18) / 2.0D)), -19.0D, 0, 36, 16, 16);
            LOTRTickHandlerClient.drawAlignmentText(fr, 18 - MathHelper.func_76128_c((double)(fr.func_78256_a(sAlign) + 18) / 2.0D), -12, sAlign, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glEnable(2896);
            GL11.glDisable(32826);
            GL11.glPopMatrix();
         }
      }

      if (this.shouldRenderFellowPlayerHealth(entityplayer)) {
         LOTRNPCRendering.renderHealthBar(entityplayer, (double)fr0, (double)fr1, (double)fr2, new int[]{16375808, 12006707}, (int[])null);
      }

   }

   private boolean shouldRenderPlayerHUD(EntityPlayer entityplayer) {
      if (!Minecraft.func_71382_s()) {
         return false;
      } else {
         return entityplayer != this.renderManager.field_78734_h && !entityplayer.func_70093_af() && !entityplayer.func_98034_c(this.mc.field_71439_g);
      }
   }

   private boolean shouldRenderAlignment(EntityPlayer entityplayer) {
      if (LOTRConfig.displayAlignmentAboveHead && this.shouldRenderPlayerHUD(entityplayer)) {
         if (LOTRLevelData.getData(entityplayer).getHideAlignment()) {
            UUID playerUuid = entityplayer.func_110124_au();
            List fellowships = LOTRLevelData.getData((EntityPlayer)this.mc.field_71439_g).getClientFellowships();
            Iterator var4 = fellowships.iterator();

            LOTRFellowshipClient fs;
            do {
               if (!var4.hasNext()) {
                  return false;
               }

               fs = (LOTRFellowshipClient)var4.next();
            } while(!fs.containsPlayer(playerUuid));

            return true;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private boolean shouldRenderFellowPlayerHealth(EntityPlayer entityplayer) {
      if (LOTRConfig.fellowPlayerHealthBars && this.shouldRenderPlayerHUD(entityplayer)) {
         List fellowships = LOTRLevelData.getData((EntityPlayer)this.mc.field_71439_g).getClientFellowships();
         Iterator var3 = fellowships.iterator();

         while(var3.hasNext()) {
            LOTRFellowshipClient fs = (LOTRFellowshipClient)var3.next();
            if (fs.containsPlayer(entityplayer.func_110124_au())) {
               return true;
            }
         }
      }

      return false;
   }
}
