package lotr.client.render;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderShield {
   private static int SHIELD_WIDTH = 32;
   private static int SHIELD_HEIGHT = 32;
   private static float MODELSCALE = 0.0625F;

   public static void renderShield(LOTRShields shield, EntityLivingBase entity, ModelBiped model) {
      Minecraft mc = Minecraft.func_71410_x();
      ResourceLocation shieldTexture = shield.shieldTexture;
      ItemStack held = entity == null ? null : entity.func_70694_bm();
      ItemStack heldLeft = entity instanceof LOTREntityNPC ? ((LOTREntityNPC)entity).getHeldItemLeft() : null;
      ItemStack inUse = entity instanceof EntityPlayer ? ((EntityPlayer)entity).func_71011_bu() : null;
      boolean holdingSword = entity == null ? true : held != null && (held.func_77973_b() instanceof ItemSword || held.func_77973_b() instanceof ItemTool) && (inUse == null || inUse.func_77975_n() != EnumAction.bow);
      boolean blocking = holdingSword && inUse != null && inUse.func_77975_n() == EnumAction.block;
      if (heldLeft != null && entity instanceof LOTREntityNPC) {
         LOTREntityNPC npc = (LOTREntityNPC)entity;
         if (npc.npcCape != null) {
            return;
         }
      }

      ItemStack chestplate = entity == null ? null : entity.func_71124_b(3);
      boolean wearingChestplate = chestplate != null && chestplate.func_77973_b().isValidArmor(chestplate, ((LOTRItemArmor)LOTRMod.bodyMithril).field_77881_a, entity);
      boolean renderOnBack = !holdingSword || holdingSword && heldLeft != null;
      GL11.glPushMatrix();
      if (renderOnBack) {
         model.field_78115_e.func_78794_c(MODELSCALE);
      } else {
         model.field_78113_g.func_78794_c(MODELSCALE);
      }

      GL11.glScalef(-1.5F, -1.5F, 1.5F);
      if (renderOnBack) {
         GL11.glTranslatef(0.5F, -0.8F, 0.0F);
         if (wearingChestplate) {
            GL11.glTranslatef(0.0F, 0.0F, 0.24F);
         } else {
            GL11.glTranslatef(0.0F, 0.0F, 0.16F);
         }

         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      } else if (blocking) {
         GL11.glRotatef(10.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(-0.4F, -0.9F, -0.15F);
      } else {
         GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(-0.5F, -0.75F, 0.0F);
         if (wearingChestplate) {
            GL11.glTranslatef(0.0F, 0.0F, -0.24F);
         } else {
            GL11.glTranslatef(0.0F, 0.0F, -0.16F);
         }

         GL11.glRotatef(-15.0F, 0.0F, 0.0F, 1.0F);
      }

      mc.func_110434_K().func_110577_a(shieldTexture);
      GL11.glEnable(3008);
      doRenderShield(0.0F);
      GL11.glTranslatef(1.0F, 0.0F, 0.0F);
      GL11.glScalef(-1.0F, 1.0F, 1.0F);
      doRenderShield(0.5F);
      GL11.glPopMatrix();
   }

   private static void doRenderShield(float f) {
      float minU = 0.0F + f;
      float maxU = 0.5F + f;
      float minV = 0.0F;
      float maxV = 1.0F;
      int width = SHIELD_WIDTH;
      int height = SHIELD_HEIGHT;
      double depth1 = (double)(MODELSCALE * 0.5F * f);
      double depth2 = (double)(MODELSCALE * 0.5F * (0.5F + f));
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78375_b(0.0F, 0.0F, 1.0F);
      tessellator.func_78374_a(0.0D, 0.0D, depth1, (double)maxU, (double)maxV);
      tessellator.func_78374_a(1.0D, 0.0D, depth1, (double)minU, (double)maxV);
      tessellator.func_78374_a(1.0D, 1.0D, depth1, (double)minU, (double)minV);
      tessellator.func_78374_a(0.0D, 1.0D, depth1, (double)maxU, (double)minV);
      tessellator.func_78381_a();
      tessellator.func_78382_b();
      tessellator.func_78375_b(0.0F, 0.0F, -1.0F);
      tessellator.func_78374_a(0.0D, 1.0D, depth2, (double)maxU, (double)minV);
      tessellator.func_78374_a(1.0D, 1.0D, depth2, (double)minU, (double)minV);
      tessellator.func_78374_a(1.0D, 0.0D, depth2, (double)minU, (double)maxV);
      tessellator.func_78374_a(0.0D, 0.0D, depth2, (double)maxU, (double)maxV);
      tessellator.func_78381_a();
      float f5 = 0.5F * (maxU - minU) / (float)width;
      float f6 = 0.5F * (maxV - minV) / (float)height;
      tessellator.func_78382_b();
      tessellator.func_78375_b(-1.0F, 0.0F, 0.0F);

      int k;
      float f7;
      float f8;
      for(k = 0; k < width; ++k) {
         f7 = (float)k / (float)width;
         f8 = maxU + (minU - maxU) * f7 - f5;
         tessellator.func_78374_a((double)f7, 0.0D, depth2, (double)f8, (double)maxV);
         tessellator.func_78374_a((double)f7, 0.0D, depth1, (double)f8, (double)maxV);
         tessellator.func_78374_a((double)f7, 1.0D, depth1, (double)f8, (double)minV);
         tessellator.func_78374_a((double)f7, 1.0D, depth2, (double)f8, (double)minV);
      }

      tessellator.func_78381_a();
      tessellator.func_78382_b();
      tessellator.func_78375_b(1.0F, 0.0F, 0.0F);

      float f9;
      for(k = 0; k < width; ++k) {
         f7 = (float)k / (float)width;
         f8 = maxU + (minU - maxU) * f7 - f5;
         f9 = f7 + 1.0F / (float)width;
         tessellator.func_78374_a((double)f9, 1.0D, depth2, (double)f8, (double)minV);
         tessellator.func_78374_a((double)f9, 1.0D, depth1, (double)f8, (double)minV);
         tessellator.func_78374_a((double)f9, 0.0D, depth1, (double)f8, (double)maxV);
         tessellator.func_78374_a((double)f9, 0.0D, depth2, (double)f8, (double)maxV);
      }

      tessellator.func_78381_a();
      tessellator.func_78382_b();
      tessellator.func_78375_b(0.0F, 1.0F, 0.0F);

      for(k = 0; k < height; ++k) {
         f7 = (float)k / (float)height;
         f8 = maxV + (minV - maxV) * f7 - f6;
         f9 = f7 + 1.0F / (float)height;
         tessellator.func_78374_a(0.0D, (double)f9, depth1, (double)maxU, (double)f8);
         tessellator.func_78374_a(1.0D, (double)f9, depth1, (double)minU, (double)f8);
         tessellator.func_78374_a(1.0D, (double)f9, depth2, (double)minU, (double)f8);
         tessellator.func_78374_a(0.0D, (double)f9, depth2, (double)maxU, (double)f8);
      }

      tessellator.func_78381_a();
      tessellator.func_78382_b();
      tessellator.func_78375_b(0.0F, -1.0F, 0.0F);

      for(k = 0; k < height; ++k) {
         f7 = (float)k / (float)height;
         f8 = maxV + (minV - maxV) * f7 - f6;
         tessellator.func_78374_a(1.0D, (double)f7, depth1, (double)minU, (double)f8);
         tessellator.func_78374_a(0.0D, (double)f7, depth1, (double)maxU, (double)f8);
         tessellator.func_78374_a(0.0D, (double)f7, depth2, (double)maxU, (double)f8);
         tessellator.func_78374_a(1.0D, (double)f7, depth2, (double)minU, (double)f8);
      }

      tessellator.func_78381_a();
   }
}
