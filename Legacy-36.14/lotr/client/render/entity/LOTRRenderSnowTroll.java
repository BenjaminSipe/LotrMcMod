package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntitySnowTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderSnowTroll extends LOTRRenderTroll {
   private static LOTRRandomSkins snowTrollSkins;

   public LOTRRenderSnowTroll() {
      snowTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/snowTroll");
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return snowTrollSkins.getRandomSkin((LOTREntityTroll)entity);
   }

   protected void bindTrollOutfitTexture(EntityLivingBase entity) {
   }

   protected void renderTrollWeapon(EntityLivingBase entity, float f) {
      LOTREntitySnowTroll troll = (LOTREntitySnowTroll)entity;
      if (!troll.isThrowingSnow()) {
         ((LOTRModelTroll)this.field_77045_g).renderWoodenClubWithSpikes(0.0625F);
      }

   }
}
