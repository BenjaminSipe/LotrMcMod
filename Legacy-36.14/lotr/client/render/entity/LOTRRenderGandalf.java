package lotr.client.render.entity;

import lotr.client.LOTRSpeechClient;
import lotr.client.model.LOTRModelHuman;
import lotr.client.model.LOTRModelWizardHat;
import lotr.common.LOTRCapes;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGandalf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGandalf extends LOTRRenderBiped {
   private static ResourceLocation skin = new ResourceLocation("lotr:mob/char/gandalf.png");
   private static ResourceLocation hat = new ResourceLocation("lotr:mob/char/gandalf_hat.png");
   private static ResourceLocation cloak = new ResourceLocation("lotr:mob/char/gandalf_cloak.png");
   private static ResourceLocation skin_santa = new ResourceLocation("lotr:mob/char/santa.png");
   private static ResourceLocation hat_santa = new ResourceLocation("lotr:mob/char/santa_hat.png");
   private static ResourceLocation cloak_santa = new ResourceLocation("lotr:mob/char/santa_cloak.png");
   private ModelBiped hatModel = new LOTRModelWizardHat(1.0F);
   private ModelBiped cloakModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderGandalf() {
      super(new LOTRModelHuman(), 0.5F);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      return LOTRMod.isChristmas() ? skin_santa : skin;
   }

   public void func_76986_a(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
      LOTREntityGandalf gandalf = (LOTREntityGandalf)entity;
      super.func_76986_a(gandalf, d, d1, d2, f, f1);
      if (Minecraft.func_71382_s() && !LOTRSpeechClient.hasSpeech(gandalf)) {
         this.func_147906_a(gandalf, gandalf.func_70005_c_(), d, d1 + 0.75D, d2, 64);
      }

   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityGandalf gandalf = (LOTREntityGandalf)entity;
      if (pass == 0 && gandalf.func_71124_b(4) == null) {
         this.func_77042_a(this.hatModel);
         if (LOTRMod.isChristmas()) {
            this.func_110776_a(hat_santa);
         } else {
            this.func_110776_a(hat);
         }

         return 1;
      } else if (pass == 1 && gandalf.func_71124_b(3) == null) {
         this.func_77042_a(this.cloakModel);
         if (LOTRMod.isChristmas()) {
            this.func_110776_a(cloak_santa);
         } else {
            this.func_110776_a(cloak);
         }

         return 1;
      } else {
         return super.func_77032_a(gandalf, pass, f);
      }
   }

   protected ResourceLocation getCapeToRender(LOTREntityNPC entity) {
      return LOTRMod.isChristmas() ? LOTRCapes.GANDALF_SANTA : super.getCapeToRender(entity);
   }
}
