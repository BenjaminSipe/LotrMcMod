package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityDunlending;
import lotr.common.entity.npc.LOTREntityDunlendingBartender;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDunlending extends LOTRRenderDunlendingBase {
   private static LOTRRandomSkins dunlendingOutfits;
   private static ResourceLocation outfitApron = new ResourceLocation("lotr:mob/dunland/bartender_apron.png");
   private ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderDunlending() {
      this.func_77042_a(this.outfitModel);
      dunlendingOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/outfit");
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityDunlending dunlending = (LOTREntityDunlending)entity;
      if (pass == 1 && dunlending.func_71124_b(3) == null) {
         this.func_77042_a(this.outfitModel);
         if (dunlending instanceof LOTREntityDunlendingBartender) {
            this.func_110776_a(outfitApron);
         } else {
            this.func_110776_a(dunlendingOutfits.getRandomSkin(dunlending));
         }

         return 1;
      } else {
         return super.func_77032_a(dunlending, pass, f);
      }
   }
}
