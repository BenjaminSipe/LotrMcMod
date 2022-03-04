package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityOlogHai;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderOlogHai extends LOTRRenderTroll {
   private static LOTRRandomSkins ologSkins;
   private static LOTRRandomSkins ologArmorSkins;

   public LOTRRenderOlogHai() {
      ologSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/ologHai");
      ologArmorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/ologHai_armor");
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return ologSkins.getRandomSkin((LOTREntityOlogHai)entity);
   }

   protected void renderTrollWeapon(EntityLivingBase entity, float f) {
      ((LOTRModelTroll)this.field_77045_g).renderWarhammer(0.0625F);
   }

   protected void bindTrollOutfitTexture(EntityLivingBase entity) {
      this.func_110776_a(ologArmorSkins.getRandomSkin((LOTREntityOlogHai)entity));
   }
}
