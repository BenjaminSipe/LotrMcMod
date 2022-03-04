package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRReflection;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.entity.animal.LOTREntityGiraffe;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.entity.animal.LOTREntityWildBoar;
import lotr.common.entity.npc.LOTREntityWarg;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.IIcon;

public class LOTRItemMountArmor extends Item {
   private ArmorMaterial armorMaterial;
   private LOTRItemMountArmor.Mount mountType;
   private int damageReduceAmount;
   private Item templateItem;

   public LOTRItemMountArmor(LOTRMaterial material, LOTRItemMountArmor.Mount mount) {
      this(material.toArmorMaterial(), mount);
   }

   public LOTRItemMountArmor(ArmorMaterial material, LOTRItemMountArmor.Mount mount) {
      this.armorMaterial = material;
      this.damageReduceAmount = material.func_78044_b(1) + material.func_78044_b(2);
      this.mountType = mount;
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
   }

   public LOTRItemMountArmor setTemplateItem(Item item) {
      this.templateItem = item;
      return this;
   }

   public String func_77653_i(ItemStack itemstack) {
      return this.templateItem != null ? this.templateItem.func_77653_i(this.createTemplateItemStack(itemstack)) : super.func_77653_i(itemstack);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77650_f(ItemStack itemstack) {
      return this.templateItem != null ? this.templateItem.func_77650_f(this.createTemplateItemStack(itemstack)) : super.func_77650_f(itemstack);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      return this.templateItem != null ? this.templateItem.func_77617_a(i) : super.func_77617_a(i);
   }

   private ItemStack createTemplateItemStack(ItemStack source) {
      ItemStack template = new ItemStack(this.templateItem);
      template.field_77994_a = source.field_77994_a;
      template.func_77964_b(source.func_77960_j());
      if (source.func_77978_p() != null) {
         template.func_77982_d(source.func_77978_p());
      }

      return template;
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      if (this.templateItem == null) {
         super.func_94581_a(iconregister);
      }

   }

   public boolean isValid(LOTRNPCMount mount) {
      if (mount instanceof LOTREntityElk) {
         return this.mountType == LOTRItemMountArmor.Mount.ELK;
      } else if (mount instanceof LOTREntityWildBoar) {
         return this.mountType == LOTRItemMountArmor.Mount.BOAR;
      } else if (mount instanceof LOTREntityCamel) {
         return this.mountType == LOTRItemMountArmor.Mount.CAMEL;
      } else if (mount instanceof LOTREntityWarg) {
         return this.mountType == LOTRItemMountArmor.Mount.WARG;
      } else if (mount instanceof LOTREntityGiraffe) {
         return this.mountType == LOTRItemMountArmor.Mount.GIRAFFE;
      } else if (mount instanceof LOTREntityRhino) {
         return this.mountType == LOTRItemMountArmor.Mount.RHINO;
      } else {
         return this.mountType == LOTRItemMountArmor.Mount.HORSE;
      }
   }

   public int getDamageReduceAmount() {
      return this.damageReduceAmount;
   }

   public int func_77619_b() {
      return 0;
   }

   public ArmorMaterial getMountArmorMaterial() {
      return this.armorMaterial;
   }

   public boolean func_82789_a(ItemStack itemstack, ItemStack repairItem) {
      return this.armorMaterial.func_151685_b() == repairItem.func_77973_b() ? true : super.func_82789_a(itemstack, repairItem);
   }

   public String getArmorTexture() {
      String path = null;
      if (this.templateItem != null) {
         int index = 0;
         if (this.templateItem == Items.field_151138_bX) {
            index = 1;
         }

         if (this.templateItem == Items.field_151136_bY) {
            index = 2;
         }

         if (this.templateItem == Items.field_151125_bZ) {
            index = 3;
         }

         path = LOTRReflection.getHorseArmorTextures()[index];
      } else {
         String mountName = this.mountType.textureName;
         String materialName = this.armorMaterial.name().toLowerCase();
         if (materialName.startsWith("lotr_")) {
            materialName = materialName.substring("lotr_".length());
         }

         path = "lotr:armor/mount/" + mountName + "_" + materialName + ".png";
      }

      return path;
   }

   public static enum Mount {
      HORSE("horse"),
      ELK("elk"),
      BOAR("boar"),
      CAMEL("camel"),
      WARG("warg"),
      GIRAFFE("giraffe"),
      RHINO("rhino");

      public final String textureName;

      private Mount(String s) {
         this.textureName = s;
      }
   }
}
