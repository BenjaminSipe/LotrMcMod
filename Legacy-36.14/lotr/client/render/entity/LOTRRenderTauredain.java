package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityTauredain;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderTauredain extends LOTRRenderBiped {
   private static LOTRRandomSkins skinsMale;
   private static LOTRRandomSkins skinsFemale;
   private static LOTRRandomSkins outfits;
   protected ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderTauredain() {
      super(new LOTRModelHuman(), 0.5F);
      skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/tauredain_male");
      skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/tauredain_female");
      outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/outfit");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityTauredain tauredain = (LOTREntityTauredain)entity;
      return tauredain.familyInfo.isMale() ? skinsMale.getRandomSkin(tauredain) : skinsFemale.getRandomSkin(tauredain);
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityTauredain tauredain = (LOTREntityTauredain)entity;
      if (pass == 1 && tauredain.func_71124_b(3) == null && LOTRRandomSkins.nextInt(tauredain, 3) == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(outfits.getRandomSkin(tauredain));
         return 1;
      } else {
         return super.func_77032_a(tauredain, pass, f);
      }
   }
}
