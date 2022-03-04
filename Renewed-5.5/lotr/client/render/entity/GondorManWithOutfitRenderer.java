package lotr.client.render.entity;

import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.layers.ManOutfitLayer;
import lotr.client.render.entity.layers.NPCOutfitLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.inventory.EquipmentSlotType;

public class GondorManWithOutfitRenderer extends GondorManRenderer {
   private static final RandomTextureVariants OUTFITS = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/gondor/outfit");
   private static final RandomTextureVariants WOMAN_HEADWEAR = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/gondor/headwear_female");

   public GondorManWithOutfitRenderer(EntityRendererManager mgr) {
      super(mgr);
      this.func_177094_a(new ManOutfitLayer(this, OUTFITS, EquipmentSlotType.CHEST, 0.25F));
      this.func_177094_a(new ManOutfitLayer(this, WOMAN_HEADWEAR, EquipmentSlotType.HEAD, 0.25F, NPCOutfitLayer::femaleOnly));
   }
}
