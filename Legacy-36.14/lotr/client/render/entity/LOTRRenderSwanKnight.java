package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderSwanKnight extends LOTRRenderBiped {
   private static LOTRRandomSkins dolAmrothSkins;

   public LOTRRenderSwanKnight() {
      super(new LOTRModelHuman(), 0.5F);
      dolAmrothSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/swanKnight");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      return dolAmrothSkins.getRandomSkin((LOTREntityNPC)entity);
   }
}
