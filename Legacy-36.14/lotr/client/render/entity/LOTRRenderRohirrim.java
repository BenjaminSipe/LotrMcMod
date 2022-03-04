package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.npc.LOTREntityRohanShieldmaiden;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderRohirrim extends LOTRRenderBiped {
   private static LOTRRandomSkins skinsMale;
   private static LOTRRandomSkins skinsFemale;
   private static LOTRRandomSkins skinsSoldier;
   private static LOTRRandomSkins skinsShieldmaiden;
   protected ModelBiped outfitModel = new LOTRModelHuman(0.6F, false);

   public LOTRRenderRohirrim() {
      super(new LOTRModelHuman(), 0.5F);
      this.func_77042_a(this.outfitModel);
      skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/rohan_male");
      skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/rohan_female");
      skinsSoldier = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/warrior");
      skinsShieldmaiden = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/shieldmaiden");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityRohanMan rohirrim = (LOTREntityRohanMan)entity;
      if (rohirrim.familyInfo.isMale()) {
         return rohirrim instanceof LOTREntityRohirrimWarrior ? skinsSoldier.getRandomSkin(rohirrim) : skinsMale.getRandomSkin(rohirrim);
      } else {
         return rohirrim instanceof LOTREntityRohanShieldmaiden ? skinsShieldmaiden.getRandomSkin(rohirrim) : skinsFemale.getRandomSkin(rohirrim);
      }
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityRohanMan rohirrim = (LOTREntityRohanMan)entity;
      return super.func_77032_a(rohirrim, pass, f);
   }
}
