package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityMirkTroll;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMirkTroll extends LOTRRenderTroll {
   private static LOTRRandomSkins mirkSkins;
   private static LOTRRandomSkins mirkArmorSkins;

   public LOTRRenderMirkTroll() {
      mirkSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mirkTroll");
      mirkArmorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mirkTroll_armor");
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return mirkSkins.getRandomSkin((LOTREntityMirkTroll)entity);
   }

   protected void renderTrollWeapon(EntityLivingBase entity, float f) {
      ((LOTRModelTroll)this.field_77045_g).renderBattleaxe(0.0625F);
   }

   protected void bindTrollOutfitTexture(EntityLivingBase entity) {
      this.func_110776_a(mirkArmorSkins.getRandomSkin((LOTREntityMirkTroll)entity));
   }
}
