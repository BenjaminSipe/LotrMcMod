package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityMoredain;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMoredain extends LOTRRenderBiped {
   private static LOTRRandomSkins skinsMale;
   private static LOTRRandomSkins skinsFemale;
   private static LOTRRandomSkins outfits;
   private ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderMoredain() {
      super(new LOTRModelHuman(), 0.5F);
      skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/moredain/moredain_male");
      skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/moredain/moredain_female");
      outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/moredain/outfit");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityMoredain moredain = (LOTREntityMoredain)entity;
      return moredain.familyInfo.isMale() ? skinsMale.getRandomSkin(moredain) : skinsFemale.getRandomSkin(moredain);
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityMoredain moredain = (LOTREntityMoredain)entity;
      if (pass == 1 && moredain.func_71124_b(3) == null && LOTRRandomSkins.nextInt(moredain, 3) == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(outfits.getRandomSkin(moredain));
         return 1;
      } else {
         return super.func_77032_a(moredain, pass, f);
      }
   }
}
