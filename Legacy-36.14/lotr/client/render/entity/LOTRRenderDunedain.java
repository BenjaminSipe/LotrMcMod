package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityDunedain;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDunedain extends LOTRRenderBiped {
   private static LOTRRandomSkins skinsMale;
   private static LOTRRandomSkins skinsFemale;
   protected ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderDunedain() {
      super(new LOTRModelHuman(), 0.5F);
      this.func_77042_a(this.outfitModel);
      skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/ranger/ranger_male");
      skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/ranger/ranger_female");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityDunedain dunedain = (LOTREntityDunedain)entity;
      return dunedain.familyInfo.isMale() ? skinsMale.getRandomSkin(dunedain) : skinsFemale.getRandomSkin(dunedain);
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityDunedain dunedain = (LOTREntityDunedain)entity;
      return super.func_77032_a(dunedain, pass, f);
   }
}
