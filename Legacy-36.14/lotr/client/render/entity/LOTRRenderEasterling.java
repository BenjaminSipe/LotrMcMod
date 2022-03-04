package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderEasterling extends LOTRRenderBiped {
   private static LOTRRandomSkins easterlingSkinsMale;
   private static LOTRRandomSkins easterlingSkinsFemale;
   protected ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderEasterling() {
      super(new LOTRModelHuman(), 0.5F);
      this.func_77042_a(this.outfitModel);
      easterlingSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/rhun/easterling_male");
      easterlingSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/rhun/easterling_female");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityEasterling easterling = (LOTREntityEasterling)entity;
      return easterling.familyInfo.isMale() ? easterlingSkinsMale.getRandomSkin(easterling) : easterlingSkinsFemale.getRandomSkin(easterling);
   }
}
