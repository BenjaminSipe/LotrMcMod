package lotr.client.render.entity;

import lotr.client.LOTRTickHandlerClient;
import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class LOTRRenderScrapTrader extends LOTRRenderBiped {
   private static LOTRRandomSkins traderSkins;

   public LOTRRenderScrapTrader() {
      super(new LOTRModelHuman(), 0.5F);
      traderSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/scrapTrader");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      return traderSkins.getRandomSkin((LOTREntityScrapTrader)entity);
   }

   public void func_76986_a(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
      if (!Keyboard.isKeyDown(Minecraft.func_71410_x().field_71474_y.field_151447_Z.func_151463_i())) {
         if (LOTRTickHandlerClient.scrapTraderMisbehaveTick <= 0) {
            super.func_76986_a(entity, d, d1, d2, f, f1);
         } else {
            int r = 3;

            for(int i = -r; i <= r; ++i) {
               for(int k = -r; k <= r; ++k) {
                  if (Math.abs(i) + Math.abs(k) > 2) {
                     GL11.glPushMatrix();
                     GL11.glScalef(1.0F, 3.0F, 1.0F);
                     double g = 6.0D;
                     super.func_76986_a(entity, (double)i * g, 0.0D, (double)k * g, f, f1);
                     GL11.glPopMatrix();
                  }
               }
            }

            GL11.glPushMatrix();
            float s = 6.0F;
            GL11.glScalef(1.0F, s, 1.0F);
            GL11.glColor3f(0.0F, 0.0F, 0.0F);
            d1 /= (double)s;
            super.func_76986_a(entity, d, d1, d2, f, f1);
            GL11.glPopMatrix();
         }
      }
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      super.func_77041_b(entity, f);
      float fadeout = ((LOTREntityScrapTrader)entity).getFadeoutProgress(f);
      if (fadeout > 0.0F) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3008);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - fadeout);
      }

   }
}
