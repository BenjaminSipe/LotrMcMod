package lotr.client.render.item;

import lotr.client.LOTRClientProxy;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class LOTRRenderCrossbow implements IItemRenderer {
   public boolean handleRenderType(ItemStack itemstack, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      LOTRRenderCrossbow.RotationMode rotationMode = null;
      EntityLivingBase holder = (EntityLivingBase)data[1];
      boolean loaded = LOTRItemCrossbow.isLoaded(itemstack);
      boolean using = false;
      if (holder instanceof EntityPlayer) {
         using = ((EntityPlayer)holder).func_71011_bu() == itemstack;
      } else if (holder instanceof EntityLiving) {
         using = ((EntityLiving)holder).func_70694_bm() == itemstack;
         if (using && holder instanceof LOTREntityNPC) {
            using = ((LOTREntityNPC)holder).clientCombatStance;
         }
      }

      if (LOTRRenderBow.renderingWeaponRack) {
         rotationMode = LOTRRenderCrossbow.RotationMode.FIRST_PERSON_HOLDING;
      } else if (holder == Minecraft.func_71410_x().field_71451_h && Minecraft.func_71410_x().field_71474_y.field_74320_O == 0) {
         if (!using && !loaded) {
            rotationMode = LOTRRenderCrossbow.RotationMode.FIRST_PERSON_HOLDING;
         } else {
            rotationMode = LOTRRenderCrossbow.RotationMode.FIRST_PERSON_LOADED;
         }
      } else {
         if (!using && !loaded) {
            rotationMode = LOTRRenderCrossbow.RotationMode.ENTITY_HOLDING;
         } else {
            rotationMode = LOTRRenderCrossbow.RotationMode.ENTITY_LOADED;
         }

         GL11.glTranslatef(0.9375F, 0.0625F, 0.0F);
         GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(0.6666667F, 0.6666667F, 0.6666667F);
         GL11.glTranslatef(0.0F, 0.3F, 0.0F);
         GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
         GL11.glScalef(2.6666667F, 2.6666667F, 2.6666667F);
         GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);
      }

      if (rotationMode == LOTRRenderCrossbow.RotationMode.FIRST_PERSON_LOADED) {
         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(-60.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-25.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(0.0F, 0.0F, -0.5F);
      } else if (rotationMode == LOTRRenderCrossbow.RotationMode.ENTITY_HOLDING) {
         GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
         GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(0.625F, -0.625F, 0.625F);
         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(0.0F, -0.3F, 0.0F);
         GL11.glScalef(1.625F, 1.625F, 1.625F);
         GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
      } else if (rotationMode == LOTRRenderCrossbow.RotationMode.ENTITY_LOADED) {
         GL11.glRotatef(50.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(0.0F, 0.0F, -0.15F);
         GL11.glTranslatef(0.0F, -0.5F, 0.0F);
      }

      IIcon icon = ((EntityLivingBase)data[1]).func_70620_b(itemstack, 0);
      if (icon == null) {
         GL11.glPopMatrix();
      } else {
         TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
         Tessellator tessellator = Tessellator.field_78398_a;
         float f = icon.func_94209_e();
         float f1 = icon.func_94212_f();
         float f2 = icon.func_94206_g();
         float f3 = icon.func_94210_h();
         ItemRenderer.func_78439_a(tessellator, f1, f2, f, f3, icon.func_94211_a(), icon.func_94216_b(), 0.0625F);
         if (itemstack != null && itemstack.hasEffect(0)) {
            LOTRClientProxy.renderEnchantmentEffect();
         }

         GL11.glDisable(32826);
      }
   }

   private static enum RotationMode {
      FIRST_PERSON_HOLDING,
      FIRST_PERSON_LOADED,
      ENTITY_HOLDING,
      ENTITY_LOADED;
   }
}
