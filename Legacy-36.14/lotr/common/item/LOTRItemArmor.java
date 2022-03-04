package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;

public class LOTRItemArmor extends ItemArmor {
   private LOTRMaterial lotrMaterial;
   private String extraName;

   public LOTRItemArmor(LOTRMaterial material, int slotType) {
      this(material, slotType, "");
   }

   public LOTRItemArmor(LOTRMaterial material, int slotType, String s) {
      super(material.toArmorMaterial(), 0, slotType);
      this.lotrMaterial = material;
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
      this.extraName = s;
   }

   public LOTRMaterial getLOTRArmorMaterial() {
      return this.lotrMaterial;
   }

   public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type) {
      String path = "lotr:armor/";
      if (entity instanceof LOTREntityHalfTroll) {
         path = "lotr:mob/halfTroll/";
      }

      String armorName = this.getArmorName();
      String texture = path + armorName;
      if (type != null) {
         texture = texture + "_" + type;
      }

      return texture + ".png";
   }

   private String getArmorName() {
      String prefix = this.func_82812_d().name().substring("lotr".length() + 1).toLowerCase();
      String suffix = this.field_77881_a == 2 ? "2" : "1";
      if (!StringUtils.func_151246_b(this.extraName)) {
         suffix = this.extraName;
      }

      return prefix + "_" + suffix;
   }

   public boolean func_77645_m() {
      return this.lotrMaterial.isDamageable();
   }
}
