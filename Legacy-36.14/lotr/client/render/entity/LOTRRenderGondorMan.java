package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.entity.npc.LOTREntityGondorSoldier;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGondorMan extends LOTRRenderBiped {
   private static LOTRRandomSkins skinsMale;
   private static LOTRRandomSkins skinsFemale;
   private static LOTRRandomSkins skinsSoldier;
   private static LOTRRandomSkins outfits;
   private static LOTRRandomSkins headwearFemale;
   protected ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderGondorMan() {
      super(new LOTRModelHuman(), 0.5F);
      this.func_77042_a(this.outfitModel);
      skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/gondor_male");
      skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/gondor_female");
      skinsSoldier = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/gondorSoldier");
      outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/outfit");
      headwearFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/headwear_female");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityGondorMan gondorian = (LOTREntityGondorMan)entity;
      if (gondorian.familyInfo.isMale()) {
         return gondorian instanceof LOTREntityGondorSoldier ? skinsSoldier.getRandomSkin(gondorian) : skinsMale.getRandomSkin(gondorian);
      } else {
         return skinsFemale.getRandomSkin(gondorian);
      }
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityGondorMan gondorian = (LOTREntityGondorMan)entity;
      if (pass == 1 && gondorian.func_71124_b(3) == null && LOTRRandomSkins.nextInt(gondorian, 4) == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(outfits.getRandomSkin(gondorian));
         return 1;
      } else if (pass == 0 && gondorian.func_71124_b(4) == null && !gondorian.familyInfo.isMale() && LOTRRandomSkins.nextInt(gondorian, 4) == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(headwearFemale.getRandomSkin(gondorian));
         return 1;
      } else {
         return super.func_77032_a(gondorian, pass, f);
      }
   }
}
