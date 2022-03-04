package lotr.common.tileentity;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import lotr.common.item.LOTRPoisonedDrinks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityMug extends TileEntity {
   private ItemStack mugItem;
   private LOTRItemMug.Vessel mugVessel;

   public ItemStack getMugItem() {
      if (this.mugItem == null) {
         return this.getVessel().getEmptyVessel();
      } else {
         ItemStack copy = this.mugItem.func_77946_l();
         if (LOTRItemMug.isItemFullDrink(copy)) {
            LOTRItemMug.setVessel(copy, this.getVessel(), true);
         }

         return copy;
      }
   }

   public void setMugItem(ItemStack itemstack) {
      if (itemstack != null && itemstack.field_77994_a <= 0) {
         itemstack = null;
      }

      this.mugItem = itemstack;
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
   }

   public ItemStack getMugItemForRender() {
      return LOTRItemMug.getEquivalentDrink(this.getMugItem());
   }

   public void setEmpty() {
      this.setMugItem((ItemStack)null);
   }

   public boolean isEmpty() {
      return !LOTRItemMug.isItemFullDrink(this.getMugItem());
   }

   public LOTRItemMug.Vessel getVessel() {
      if (this.mugVessel == null) {
         LOTRItemMug.Vessel[] var1 = LOTRItemMug.Vessel.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRItemMug.Vessel v = var1[var3];
            if (v.canPlace && v.getBlock() == this.func_145838_q()) {
               return v;
            }
         }

         return LOTRItemMug.Vessel.MUG;
      } else {
         return this.mugVessel;
      }
   }

   public void setVessel(LOTRItemMug.Vessel v) {
      this.mugVessel = v;
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
   }

   public boolean canPoisonMug() {
      ItemStack itemstack = this.getMugItem();
      if (itemstack == null) {
         return false;
      } else {
         return LOTRPoisonedDrinks.canPoison(itemstack) && !LOTRPoisonedDrinks.isDrinkPoisoned(itemstack);
      }
   }

   public void poisonMug(EntityPlayer entityplayer) {
      ItemStack itemstack = this.getMugItem();
      LOTRPoisonedDrinks.setDrinkPoisoned(itemstack, true);
      LOTRPoisonedDrinks.setPoisonerPlayer(itemstack, entityplayer);
      this.setMugItem(itemstack);
   }

   public void func_145845_h() {
      if (!this.field_145850_b.field_72995_K && this.isEmpty() && this.field_145850_b.func_72951_B(this.field_145851_c, this.field_145848_d, this.field_145849_e) && this.field_145850_b.field_73012_v.nextInt(6000) == 0) {
         ItemStack waterItem = new ItemStack(LOTRMod.mugWater);
         LOTRItemMug.setVessel(waterItem, this.getVessel(), false);
         this.setMugItem(waterItem);
         this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
         this.func_70296_d();
      }

   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      nbt.func_74757_a("HasMugItem", this.mugItem != null);
      if (this.mugItem != null) {
         nbt.func_74782_a("MugItem", this.mugItem.func_77955_b(new NBTTagCompound()));
      }

      if (this.mugVessel != null) {
         nbt.func_74774_a("Vessel", (byte)this.mugVessel.id);
      }

   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      if (nbt.func_74764_b("ItemID")) {
         Item item = Item.func_150899_d(nbt.func_74762_e("ItemID"));
         if (item != null) {
            int damage = nbt.func_74762_e("ItemDamage");
            this.mugItem = new ItemStack(item, 1, damage);
         }
      } else {
         boolean hasItem = nbt.func_74767_n("HasMugItem");
         if (!hasItem) {
            this.mugItem = null;
         } else {
            this.mugItem = ItemStack.func_77949_a(nbt.func_74775_l("MugItem"));
         }
      }

      if (nbt.func_74764_b("Vessel")) {
         this.mugVessel = LOTRItemMug.Vessel.forMeta(nbt.func_74771_c("Vessel"));
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
