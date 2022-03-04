package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityCorsair;
import lotr.common.entity.npc.LOTREntityGulfHaradWarrior;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.npc.LOTREntityNearHaradrimWarrior;
import lotr.common.entity.npc.LOTREntityNomad;
import lotr.common.entity.npc.LOTREntityUmbarWarrior;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderNearHaradrim extends LOTRRenderBiped {
   private static LOTRRandomSkins haradrimSkinsMale;
   private static LOTRRandomSkins haradrimSkinsFemale;
   private static LOTRRandomSkins warriorSkins;
   private static LOTRRandomSkins harnedorSkinsMale;
   private static LOTRRandomSkins harnedorSkinsFemale;
   private static LOTRRandomSkins harnedorWarriorSkins;
   private static LOTRRandomSkins harnedorOutfits;
   private static LOTRRandomSkins nomadSkinsMale;
   private static LOTRRandomSkins nomadSkinsFemale;
   private static LOTRRandomSkins nomadHats;
   protected ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderNearHaradrim() {
      super(new LOTRModelHuman(), 0.5F);
      this.func_77042_a(this.outfitModel);
      haradrimSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/haradrim_male");
      haradrimSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/haradrim_female");
      warriorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/warrior");
      harnedorSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/harnedor_male");
      harnedorSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/harnedor_female");
      harnedorWarriorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/harnedorWarrior");
      harnedorOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/harnedor_outfit");
      nomadSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/nomad_male");
      nomadSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/nomad_female");
      nomadHats = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/nomad_hat");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityNearHaradrimBase haradrim = (LOTREntityNearHaradrimBase)entity;
      if (!(haradrim instanceof LOTREntityHarnedhrim) && !(haradrim instanceof LOTREntityGulfHaradrim) && !(haradrim instanceof LOTREntityCorsair)) {
         if (haradrim instanceof LOTREntityNomad) {
            return haradrim.familyInfo.isMale() ? nomadSkinsMale.getRandomSkin(haradrim) : nomadSkinsFemale.getRandomSkin(haradrim);
         } else if (!(haradrim instanceof LOTREntityNearHaradrimWarrior) && !(haradrim instanceof LOTREntityUmbarWarrior)) {
            return haradrim.familyInfo.isMale() ? haradrimSkinsMale.getRandomSkin(haradrim) : haradrimSkinsFemale.getRandomSkin(haradrim);
         } else {
            return warriorSkins.getRandomSkin(haradrim);
         }
      } else if (!(haradrim instanceof LOTREntityHarnedorWarrior) && !(haradrim instanceof LOTREntityGulfHaradWarrior)) {
         return haradrim.familyInfo.isMale() ? harnedorSkinsMale.getRandomSkin(haradrim) : harnedorSkinsFemale.getRandomSkin(haradrim);
      } else {
         return harnedorWarriorSkins.getRandomSkin(haradrim);
      }
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityNearHaradrimBase haradrim = (LOTREntityNearHaradrimBase)entity;
      if ((haradrim instanceof LOTREntityHarnedhrim || haradrim instanceof LOTREntityGulfHaradrim) && pass == 1 && haradrim.func_71124_b(3) == null && LOTRRandomSkins.nextInt(haradrim, 2) == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(harnedorOutfits.getRandomSkin(haradrim));
         return 1;
      } else if (haradrim instanceof LOTREntityNomad && pass == 0 && haradrim.func_71124_b(4) == null && LOTRRandomSkins.nextInt(haradrim, 2) == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(nomadHats.getRandomSkin(haradrim));
         return 1;
      } else {
         return super.func_77032_a(haradrim, pass, f);
      }
   }
}
