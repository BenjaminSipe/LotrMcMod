package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.projectile.LOTREntityGandalfFireball;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketWeaponFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemGandalfStaffWhite extends LOTRItemSword implements LOTRStoryItem {
   public LOTRItemGandalfStaffWhite() {
      super(LOTRMaterial.HIGH_ELVEN);
      this.func_77656_e(1500);
      this.func_77637_a(LOTRCreativeTabs.tabStory);
      this.lotrWeaponDamage = 8.0F;
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      entityplayer.func_71038_i();
      itemstack.func_77972_a(2, entityplayer);
      world.func_72956_a(entityplayer, "mob.ghast.fireball", 2.0F, (field_77697_d.nextFloat() - field_77697_d.nextFloat()) * 0.2F + 1.0F);
      if (!world.field_72995_K) {
         world.func_72838_d(new LOTREntityGandalfFireball(world, entityplayer));
         LOTRPacketWeaponFX packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.STAFF_GANDALF_WHITE, entityplayer);
         LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(entityplayer, 64.0D));
      }

      return itemstack;
   }

   public int func_77626_a(ItemStack itemstack) {
      return 40;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.bow;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      return itemstack;
   }
}
