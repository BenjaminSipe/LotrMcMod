package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderAngmarHillman extends LOTRRenderBiped {
   private static LOTRRandomSkins hillmanSkinsMale;
   private static LOTRRandomSkins hillmanSkinsFemale;
   private static LOTRRandomSkins hillmanOutfits;
   private ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);
   private boolean useOutfits;

   public LOTRRenderAngmarHillman(boolean outfit) {
      super(new LOTRModelHuman(), 0.5F);
      this.useOutfits = outfit;
      this.func_77042_a(this.outfitModel);
      hillmanSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/hillman_male");
      hillmanSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/hillman_female");
      hillmanOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/outfit");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityAngmarHillman hillman = (LOTREntityAngmarHillman)entity;
      return hillman.familyInfo.isMale() ? hillmanSkinsMale.getRandomSkin(hillman) : hillmanSkinsFemale.getRandomSkin(hillman);
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityAngmarHillman hillman = (LOTREntityAngmarHillman)entity;
      if (this.useOutfits && pass == 1 && hillman.func_71124_b(3) == null) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(hillmanOutfits.getRandomSkin(hillman));
         return 1;
      } else {
         return super.func_77032_a(hillman, pass, f);
      }
   }
}
