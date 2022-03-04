package lotr.client;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.lwjgl.opengl.GL11;

public class LOTRAttackTiming {
   private static Minecraft mc = Minecraft.func_71410_x();
   private static ResourceLocation meterTexture = new ResourceLocation("lotr:gui/attackMeter.png");
   private static RenderItem itemRenderer = new RenderItem();
   public static int attackTime;
   public static int prevAttackTime;
   public static int fullAttackTime;
   private static ItemStack attackItem;
   private static int lastCheckTick = -1;

   public static void doAttackTiming() {
      int currentTick = LOTRTickHandlerClient.clientTick;
      if (lastCheckTick == -1) {
         lastCheckTick = currentTick;
      } else if (lastCheckTick == currentTick) {
         return;
      }

      if (mc.field_71439_g == null) {
         reset();
      } else {
         KeyBinding attackKey = mc.field_71474_y.field_74312_F;
         boolean pressed = attackKey.func_151468_f();
         if (pressed) {
            KeyBinding.func_74507_a(attackKey.func_151463_i());
         }

         if (pressed && mc.field_71476_x != null && mc.field_71476_x.field_72313_a == MovingObjectType.ENTITY && mc.field_71476_x.field_72308_g instanceof EntityLivingBase) {
            if (attackTime > 0) {
               while(attackKey.func_151468_f()) {
               }
            } else {
               ItemStack itemstack = mc.field_71439_g.func_70694_bm();
               fullAttackTime = LOTRWeaponStats.getAttackTimePlayer(itemstack);
               attackTime = fullAttackTime;
               attackItem = itemstack;
            }

            lastCheckTick = currentTick;
         }
      }

   }

   public static void update() {
      prevAttackTime = attackTime;
      if (attackTime > 0) {
         --attackTime;
      } else {
         reset();
      }

   }

   public static void reset() {
      attackTime = 0;
      prevAttackTime = 0;
      fullAttackTime = 0;
      attackItem = null;
   }

   public static void renderAttackMeter(ScaledResolution resolution, float partialTicks) {
      if (fullAttackTime > 0) {
         float attackTimeF = (float)prevAttackTime + (float)(attackTime - prevAttackTime) * partialTicks;
         attackTimeF /= (float)fullAttackTime;
         float meterAmount = 1.0F - attackTimeF;
         int minX = resolution.func_78326_a() / 2 + 120;
         int maxX = resolution.func_78326_a() - 20;
         int maxY = resolution.func_78328_b() - 10;
         int minY = maxY - 10;
         double lerpX = (double)((float)minX + (float)(maxX - minX) * meterAmount);
         Tessellator tessellator = Tessellator.field_78398_a;
         mc.func_110434_K().func_110577_a(meterTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         double minU = 0.0D;
         double maxU = 1.0D;
         double minV = 0.0D;
         double maxV = 0.0625D;
         double lerpU = minU + (maxU - minU) * (double)meterAmount;
         tessellator.func_78382_b();
         tessellator.func_78374_a((double)minX, (double)minY, 0.0D, minU, minV);
         tessellator.func_78374_a((double)minX, (double)maxY, 0.0D, minU, maxV);
         tessellator.func_78374_a((double)maxX, (double)maxY, 0.0D, maxU, maxV);
         tessellator.func_78374_a((double)maxX, (double)minY, 0.0D, maxU, minV);
         tessellator.func_78381_a();
         tessellator.func_78382_b();
         tessellator.func_78374_a(lerpX, (double)minY, 0.0D, lerpU, minV + maxV);
         tessellator.func_78374_a(lerpX, (double)maxY, 0.0D, lerpU, maxV + maxV);
         tessellator.func_78374_a((double)maxX, (double)maxY, 0.0D, maxU, maxV + maxV);
         tessellator.func_78374_a((double)maxX, (double)minY, 0.0D, maxU, minV + maxV);
         tessellator.func_78381_a();
         tessellator.func_78382_b();
         tessellator.func_78374_a((double)minX, (double)minY, 0.0D, minU, minV + maxV * 2.0D);
         tessellator.func_78374_a((double)minX, (double)maxY, 0.0D, minU, maxV + maxV * 2.0D);
         tessellator.func_78374_a((double)maxX, (double)maxY, 0.0D, maxU, maxV + maxV * 2.0D);
         tessellator.func_78374_a((double)maxX, (double)minY, 0.0D, maxU, minV + maxV * 2.0D);
         tessellator.func_78381_a();
         if (attackItem != null) {
            RenderHelper.func_74520_c();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(32826);
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int iconX = (minX + maxX) / 2 - 8;
            int iconY = (minY + maxY) / 2 - 8;
            itemRenderer.func_82406_b(mc.field_71466_p, mc.func_110434_K(), attackItem, iconX, iconY);
            RenderHelper.func_74518_a();
         }
      }

   }
}
