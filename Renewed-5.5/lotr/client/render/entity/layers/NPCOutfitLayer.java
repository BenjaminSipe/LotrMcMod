package lotr.client.render.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.function.Predicate;
import lotr.client.render.RandomTextureVariants;
import lotr.client.render.entity.ArmsStyleModelProvider;
import lotr.client.render.entity.model.LOTRBipedModel;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class NPCOutfitLayer extends LayerRenderer {
   private final LOTRBipedModel standardArmsOutfitModel;
   private final LOTRBipedModel smallArmsOutfitModel;
   private final RandomTextureVariants outfitSkins;
   private final EquipmentSlotType requiredEmptySlot;
   private final float proportionWithOutfit;
   private final Predicate genderCheck;

   public NPCOutfitLayer(IEntityRenderer renderer, ArmsStyleModelProvider armsStyleModelProvider, RandomTextureVariants skins, EquipmentSlotType slot, float prop, Predicate gender) {
      super(renderer);
      this.standardArmsOutfitModel = armsStyleModelProvider.getModelForArmsStyle(false);
      this.smallArmsOutfitModel = armsStyleModelProvider.getModelForArmsStyle(true);
      this.outfitSkins = skins;
      this.requiredEmptySlot = slot;
      this.proportionWithOutfit = prop;
      this.genderCheck = gender;
   }

   public NPCOutfitLayer(IEntityRenderer renderer, ArmsStyleModelProvider armsStyleModelProvider, RandomTextureVariants skins, EquipmentSlotType slot) {
      this(renderer, armsStyleModelProvider, skins, slot, 1.0F);
   }

   public NPCOutfitLayer(IEntityRenderer renderer, ArmsStyleModelProvider armsStyleModelProvider, RandomTextureVariants skins, EquipmentSlotType slot, float prop) {
      this(renderer, armsStyleModelProvider, skins, slot, prop, NPCOutfitLayer::anyGender);
   }

   public static boolean anyGender(NPCEntity entity) {
      return true;
   }

   public static boolean maleOnly(NPCEntity entity) {
      return entity.getPersonalInfo().isMale();
   }

   public static boolean femaleOnly(NPCEntity entity) {
      return entity.getPersonalInfo().isFemale();
   }

   public void render(MatrixStack matStack, IRenderTypeBuffer buf, int packedLight, NPCEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (this.genderCheck.test(entity) && entity.func_184582_a(this.requiredEmptySlot).func_190926_b() && RandomTextureVariants.nextFloat(entity) < this.proportionWithOutfit) {
         LOTRBipedModel outfitModel = entity.useSmallArmsModel() ? this.smallArmsOutfitModel : this.standardArmsOutfitModel;
         func_229140_a_(this.func_215332_c(), outfitModel, this.outfitSkins.getRandomSkin(entity), matStack, buf, packedLight, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
      }

   }
}
