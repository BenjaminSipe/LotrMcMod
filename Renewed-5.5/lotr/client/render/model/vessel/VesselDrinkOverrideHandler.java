package lotr.client.render.model.vessel;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class VesselDrinkOverrideHandler extends ItemOverrideList {
   private Map vesselModels = new HashMap();

   public void putOverride(VesselType vessel, IBakedModel model) {
      this.vesselModels.put(vessel, model);
   }

   public IBakedModel func_239290_a_(IBakedModel model, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity livingEntity) {
      VesselType vessel = VesselDrinkItem.getVessel(stack);
      return (IBakedModel)this.vesselModels.get(vessel);
   }
}
