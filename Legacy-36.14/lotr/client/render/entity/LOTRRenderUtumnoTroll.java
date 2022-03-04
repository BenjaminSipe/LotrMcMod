package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderUtumnoTroll extends LOTRRenderTroll {
   private static LOTRRandomSkins utumnoTrollSkins;

   public LOTRRenderUtumnoTroll() {
      utumnoTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/utumno");
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return utumnoTrollSkins.getRandomSkin((LOTREntityTroll)entity);
   }

   protected void renderTrollWeapon(EntityLivingBase entity, float f) {
      ((LOTRModelTroll)this.field_77045_g).renderWoodenClubWithSpikes(0.0625F);
   }
}
