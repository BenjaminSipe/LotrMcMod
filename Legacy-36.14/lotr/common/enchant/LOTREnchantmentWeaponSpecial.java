package lotr.common.enchant;

import lotr.common.LOTRDamage;
import lotr.common.item.LOTRItemBalrogWhip;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketWeaponFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentWeaponSpecial extends LOTREnchantment {
   private boolean compatibleBane = true;
   private boolean compatibleOtherSpecial = false;

   public LOTREnchantmentWeaponSpecial(String s) {
      super(s, new LOTREnchantmentType[]{LOTREnchantmentType.MELEE, LOTREnchantmentType.THROWING_AXE, LOTREnchantmentType.RANGED_LAUNCHER});
      this.setValueModifier(3.0F);
      this.setBypassAnvilLimit();
   }

   public LOTREnchantmentWeaponSpecial setIncompatibleBane() {
      this.compatibleBane = false;
      return this;
   }

   public LOTREnchantmentWeaponSpecial setCompatibleOtherSpecial() {
      this.compatibleOtherSpecial = true;
      return this;
   }

   public String getDescription(ItemStack itemstack) {
      return LOTRWeaponStats.isMeleeWeapon(itemstack) ? StatCollector.func_74837_a("lotr.enchant." + this.enchantName + ".desc.melee", new Object[0]) : StatCollector.func_74837_a("lotr.enchant." + this.enchantName + ".desc.ranged", new Object[0]);
   }

   public boolean isBeneficial() {
      return true;
   }

   public boolean canApply(ItemStack itemstack, boolean considering) {
      if (!super.canApply(itemstack, considering)) {
         return false;
      } else {
         Item item = itemstack.func_77973_b();
         return !(item instanceof LOTRItemBalrogWhip) || this != LOTREnchantment.fire && this != LOTREnchantment.chill;
      }
   }

   public boolean isCompatibleWith(LOTREnchantment other) {
      if (!this.compatibleBane && other instanceof LOTREnchantmentBane) {
         return false;
      } else {
         return this.compatibleOtherSpecial || !(other instanceof LOTREnchantmentWeaponSpecial) || ((LOTREnchantmentWeaponSpecial)other).compatibleOtherSpecial;
      }
   }

   public static int getFireAmount() {
      return 2;
   }

   public static void doChillAttack(EntityLivingBase entity) {
      if (entity instanceof EntityPlayerMP) {
         LOTRDamage.doFrostDamage((EntityPlayerMP)entity);
      }

      int duration = 5;
      entity.func_70690_d(new PotionEffect(Potion.field_76421_d.field_76415_H, duration * 20, 1));
      LOTRPacketWeaponFX packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.CHILLING, entity);
      LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(entity, 64.0D));
   }
}
