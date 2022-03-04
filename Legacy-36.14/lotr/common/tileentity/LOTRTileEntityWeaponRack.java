package lotr.common.tileentity;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityWeaponRack extends TileEntity {
   private ItemStack weaponItem;
   private EntityLivingBase rackEntity;

   public boolean canAcceptItem(ItemStack itemstack) {
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (LOTRWeaponStats.isMeleeWeapon(itemstack)) {
            return true;
         }

         if (LOTRWeaponStats.isRangedWeapon(itemstack)) {
            return true;
         }

         if (item instanceof ItemHoe) {
            return true;
         }

         if (item instanceof ItemFishingRod) {
            return true;
         }
      }

      return false;
   }

   public ItemStack getWeaponItem() {
      return this.weaponItem;
   }

   public void setWeaponItem(ItemStack item) {
      if (item != null && item.field_77994_a <= 0) {
         item = null;
      }

      this.weaponItem = item;
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
   }

   public EntityLivingBase getEntityForRender() {
      if (this.rackEntity == null) {
         this.rackEntity = new EntityLiving(this.field_145850_b) {
         };
      }

      return this.rackEntity;
   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      nbt.func_74757_a("HasWeapon", this.weaponItem != null);
      if (this.weaponItem != null) {
         nbt.func_74782_a("WeaponItem", this.weaponItem.func_77955_b(new NBTTagCompound()));
      }

   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      boolean hasWeapon = nbt.func_74767_n("HasWeapon");
      if (hasWeapon) {
         this.weaponItem = ItemStack.func_77949_a(nbt.func_74775_l("WeaponItem"));
      } else {
         this.weaponItem = null;
      }

   }

   public Packet func_145844_m() {
      NBTTagCompound data = new NBTTagCompound();
      this.func_145841_b(data);
      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
      NBTTagCompound data = packet.func_148857_g();
      this.func_145839_a(data);
   }
}
