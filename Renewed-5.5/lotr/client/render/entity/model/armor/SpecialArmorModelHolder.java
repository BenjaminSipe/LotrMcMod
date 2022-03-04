package lotr.client.render.entity.model.armor;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.entity.model.BipedModel;

public class SpecialArmorModelHolder {
   private final Map modelMap = new HashMap();
   private final SpecialArmorModelHolder.SpecialArmorModelFactory modelFactory;

   public SpecialArmorModelHolder(SpecialArmorModelHolder.SpecialArmorModelFactory factory) {
      this.modelFactory = factory;
   }

   public SpecialArmorModel getSpecialModelFromBipedReference(BipedModel referenceBipedModel) {
      Class modelClass = referenceBipedModel.getClass();
      return (SpecialArmorModel)this.modelMap.computeIfAbsent(modelClass, (cls) -> {
         return this.modelFactory.createModel(referenceBipedModel);
      });
   }

   @FunctionalInterface
   public interface SpecialArmorModelFactory {
      SpecialArmorModel createModel(BipedModel var1);
   }
}
