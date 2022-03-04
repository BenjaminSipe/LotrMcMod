package lotr.common.item;

import java.util.Iterator;
import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispenseThrowingAxe;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.block.BlockDispenser;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class LOTRItemThrowingAxe extends Item {
   private ToolMaterial axeMaterial;

   public LOTRItemThrowingAxe(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemThrowingAxe(ToolMaterial material) {
      this.axeMaterial = material;
      this.func_77625_d(1);
      this.func_77656_e(material.func_77997_a());
      this.func_77664_n();
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
      BlockDispenser.field_149943_a.func_82595_a(this, new LOTRDispenseThrowingAxe());
   }

   public ToolMaterial getAxeMaterial() {
      return this.axeMaterial;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(world, entityplayer, itemstack.func_77946_l(), 2.0F);
      axe.setIsCritical(true);
      int fireAspect = EnchantmentHelper.func_77506_a(Enchantment.field_77343_v.field_77352_x, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack);
      if (fireAspect > 0) {
         axe.func_70015_d(100);
      }

      Iterator var6 = LOTREnchantment.allEnchantments.iterator();

      while(var6.hasNext()) {
         LOTREnchantment ench = (LOTREnchantment)var6.next();
         if (ench.applyToProjectile() && LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
            LOTREnchantmentHelper.setProjectileEnchantment(axe, ench);
         }
      }

      if (entityplayer.field_71075_bZ.field_75098_d) {
         axe.canBePickedUp = 2;
      }

      world.func_72956_a(entityplayer, "random.bow", 1.0F, 1.0F / (field_77697_d.nextFloat() * 0.4F + 1.2F) + 0.25F);
      if (!world.field_72995_K) {
         world.func_72838_d(axe);
      }

      if (!entityplayer.field_71075_bZ.field_75098_d) {
         --itemstack.field_77994_a;
      }

      return itemstack;
   }

   public boolean func_82789_a(ItemStack itemstack, ItemStack repairItem) {
      return LOTRRecipes.checkItemEquals(this.axeMaterial.getRepairItemStack(), repairItem) ? true : super.func_82789_a(itemstack, repairItem);
   }

   public float getRangedDamageMultiplier(ItemStack itemstack, Entity shooter, Entity hit) {
      float damage = this.axeMaterial.func_78000_c() + 4.0F;
      if (shooter instanceof EntityLivingBase && hit instanceof EntityLivingBase) {
         damage += EnchantmentHelper.func_77512_a((EntityLivingBase)shooter, (EntityLivingBase)hit);
      } else {
         damage += EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED);
      }

      return damage * 0.5F;
   }
}
