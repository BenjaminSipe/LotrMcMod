package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityBreeMan;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBreeMan extends LOTRRenderBiped {
   private static LOTRRandomSkins skinsMale;
   private static LOTRRandomSkins skinsFemale;
   private static LOTRRandomSkins headwearFemale;
   protected ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderBreeMan() {
      super(new LOTRModelHuman(), 0.5F);
      skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/bree/bree_male");
      skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/bree/bree_female");
      headwearFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/bree/headwear_female");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityBreeMan man = (LOTREntityBreeMan)entity;
      return man.familyInfo.isMale() ? skinsMale.getRandomSkin(man) : skinsFemale.getRandomSkin(man);
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityBreeMan man = (LOTREntityBreeMan)entity;
      if (pass == 0 && man.func_71124_b(4) == null && !man.familyInfo.isMale() && LOTRRandomSkins.nextInt(man, 4) == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(headwearFemale.getRandomSkin(man));
         return 1;
      } else {
         return super.func_77032_a(man, pass, f);
      }
   }
}
