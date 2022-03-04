package lotr.common.item;

import javax.annotation.Nullable;
import lotr.client.render.entity.model.armor.LOTRArmorModels;
import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRMaterial;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LOTRArmorItem extends ArmorItem {
   private final String specialTextureName;
   private final boolean isUndamageable;

   private static Properties defaultArmorProperties() {
      return (new Properties()).func_200916_a(LOTRItemGroups.COMBAT);
   }

   public LOTRArmorItem(IArmorMaterial material, EquipmentSlotType slot) {
      this(material, slot, defaultArmorProperties(), (String)null);
   }

   public LOTRArmorItem(IArmorMaterial material, EquipmentSlotType slot, String specialTex) {
      this(material, slot, defaultArmorProperties(), specialTex);
   }

   public LOTRArmorItem(IArmorMaterial material, EquipmentSlotType slot, Properties properties) {
      this(material, slot, properties, (String)null);
   }

   public LOTRArmorItem(IArmorMaterial material, EquipmentSlotType slot, Properties properties, String specialTex) {
      super(material, slot, properties);
      this.specialTextureName = specialTex;
      this.isUndamageable = (Boolean)LOTRMaterial.ifLOTRArmorMaterial(material).map(LOTRMaterial.AsArmor::isUndamageable).orElse(false);
   }

   public LOTRArmorItem(LOTRMaterial material, EquipmentSlotType slot) {
      this((IArmorMaterial)material.asArmor(), slot);
   }

   public LOTRArmorItem(LOTRMaterial material, EquipmentSlotType slot, String specialTex) {
      this((IArmorMaterial)material.asArmor(), slot, (String)specialTex);
   }

   public LOTRArmorItem(LOTRMaterial material, EquipmentSlotType slot, Properties properties) {
      this(material.asArmor(), slot, properties, (String)null);
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
      if (this.specialTextureName == null) {
         return super.getArmorTexture(stack, entity, slot, type);
      } else {
         ArmorItem item = (ArmorItem)stack.func_77973_b();
         String materialName = item.func_200880_d().func_200897_d();
         String domain = "minecraft";
         int idx = materialName.indexOf(58);
         if (idx != -1) {
            domain = materialName.substring(0, idx);
            materialName = materialName.substring(idx + 1);
         }

         String texturePath = String.format("%s:textures/models/armor/%s_%s%s.png", domain, materialName, this.specialTextureName, type == null ? "" : String.format("_%s", type));
         return texturePath;
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Nullable
   public BipedModel getArmorModel(LivingEntity entity, ItemStack itemstack, EquipmentSlotType slot, BipedModel _default) {
      return LOTRArmorModels.getArmorModel(entity, itemstack, slot, _default);
   }

   public boolean func_77645_m() {
      return super.func_77645_m() && !this.isUndamageable;
   }
}
