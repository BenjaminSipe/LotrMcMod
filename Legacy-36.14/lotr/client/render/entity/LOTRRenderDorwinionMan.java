package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityDorwinionMan;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDorwinionMan extends LOTRRenderBiped {
   private static LOTRRandomSkins skinsMale;
   private static LOTRRandomSkins skinsFemale;
   private static LOTRRandomSkins outfits;
   private ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderDorwinionMan() {
      super(new LOTRModelHuman(), 0.5F);
      skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dorwinion/dorwinion_male");
      skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dorwinion/dorwinion_female");
      outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/dorwinion/outfit");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityDorwinionMan man = (LOTREntityDorwinionMan)entity;
      return man.familyInfo.isMale() ? skinsMale.getRandomSkin(man) : skinsFemale.getRandomSkin(man);
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityDorwinionMan man = (LOTREntityDorwinionMan)entity;
      if (pass == 1 && man.func_71124_b(3) == null && LOTRRandomSkins.nextInt(man, 2) == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(outfits.getRandomSkin(man));
         return 1;
      } else {
         return super.func_77032_a(man, pass, f);
      }
   }
}
