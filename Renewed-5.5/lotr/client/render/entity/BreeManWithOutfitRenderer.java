package lotr.client.render.entity;

import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.layers.ManOutfitLayer;
import lotr.client.render.entity.layers.NPCOutfitLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.inventory.EquipmentSlotType;

public class BreeManWithOutfitRenderer extends BreeManRenderer {
   private static final RandomTextureVariants WOMAN_HEADWEAR = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/bree/headwear_female");

   public BreeManWithOutfitRenderer(EntityRendererManager mgr) {
      super(mgr);
      this.func_177094_a(new ManOutfitLayer(this, WOMAN_HEADWEAR, EquipmentSlotType.HEAD, 0.25F, NPCOutfitLayer::femaleOnly));
   }
}
