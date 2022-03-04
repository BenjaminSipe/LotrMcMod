package lotr.client.render.entity;

import lotr.client.LOTRSpeechClient;
import lotr.client.model.LOTRModelGollum;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderGollum extends RenderLiving {
   private static ResourceLocation skin = new ResourceLocation("lotr:mob/char/gollum.png");

   public LOTRRenderGollum() {
      super(new LOTRModelGollum(), 0.5F);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return skin;
   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      float scale = 0.85F;
      GL11.glScalef(scale, scale, scale);
   }

   public void func_76986_a(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityGollum gollum = (LOTREntityGollum)entity;
      super.func_76986_a(gollum, d, d1, d2, f, f1);
      if (Minecraft.func_71382_s()) {
         if (!LOTRSpeechClient.hasSpeech(gollum)) {
            this.func_147906_a(gollum, gollum.func_70005_c_(), d, d1 + 0.5D, d2, 64);
         }

         if (gollum.getGollumOwner() == Minecraft.func_71410_x().field_71439_g) {
            LOTRNPCRendering.renderNPCHealthBar(entity, d, d1 + 0.5D, d2);
         }
      }

   }

   protected void func_77029_c(EntityLivingBase entity, float f) {
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      ItemStack heldItem = entity.func_70694_bm();
      if (heldItem != null && heldItem.func_77973_b() == Items.field_151115_aP) {
         GL11.glPushMatrix();
         ((LOTRModelGollum)this.field_77045_g).head.func_78794_c(0.0625F);
         GL11.glTranslatef(0.21875F, 0.03125F, -0.375F);
         float f1 = 0.375F;
         GL11.glScalef(f1, f1, f1);
         GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-50.0F, 1.0F, 0.0F, 0.0F);
         this.field_76990_c.field_78721_f.func_78443_a(entity, heldItem, 0);
         GL11.glPopMatrix();
      }

   }
}
