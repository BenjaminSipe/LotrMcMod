package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityDaleLevyman;
import lotr.common.entity.npc.LOTREntityDaleMan;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDaleMan extends LOTRRenderBiped {
   private static LOTRRandomSkins skinsMale;
   private static LOTRRandomSkins skinsFemale;
   private static LOTRRandomSkins skinsSoldier;
   protected ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderDaleMan() {
      super(new LOTRModelHuman(), 0.5F);
      this.func_77042_a(this.outfitModel);
      skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dale/dale_male");
      skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dale/dale_female");
      skinsSoldier = LOTRRandomSkins.loadSkinsList("lotr:mob/dale/dale_soldier");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityDaleMan man = (LOTREntityDaleMan)entity;
      if (man.familyInfo.isMale()) {
         return man instanceof LOTREntityDaleLevyman ? skinsSoldier.getRandomSkin(man) : skinsMale.getRandomSkin(man);
      } else {
         return skinsFemale.getRandomSkin(man);
      }
   }
}
