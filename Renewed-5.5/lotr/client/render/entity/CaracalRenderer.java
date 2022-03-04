package lotr.client.render.entity;

import lotr.client.render.entity.layers.CaracalCollarLayer;
import lotr.client.render.entity.layers.CaracalHeldItemLayer;
import lotr.client.render.entity.model.CaracalModel;
import lotr.common.entity.animal.CaracalEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class CaracalRenderer extends MobRenderer {
   private static final ResourceLocation CARACAL_TEXTURE = new ResourceLocation("lotr", "textures/entity/caracal/caracal.png");

   public CaracalRenderer(EntityRendererManager mgr) {
      super(mgr, new CaracalModel(0.0F), 0.4F);
      this.func_177094_a(new CaracalCollarLayer(this));
      this.func_177094_a(new CaracalHeldItemLayer(this));
   }

   public ResourceLocation getEntityTexture(CaracalEntity caracal) {
      return CARACAL_TEXTURE;
   }
}
