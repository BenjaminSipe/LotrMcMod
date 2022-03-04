package lotr.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityEntJar extends TileEntity {
   public int drinkMeta = -1;
   public int drinkAmount;
   public static int MAX_CAPACITY = 6;

   public void func_145845_h() {
      if (!this.field_145850_b.field_72995_K && (this.field_145850_b.func_72951_B(this.field_145851_c, this.field_145848_d, this.field_145849_e) || this.field_145850_b.func_72951_B(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e)) && this.field_145850_b.field_73012_v.nextInt(2500) == 0) {
         this.fillWithWater();
      }

   }

   public boolean fillFromBowl(ItemStack itemstack) {
      if (this.drinkMeta == -1 && this.drinkAmount == 0) {
         this.drinkMeta = itemstack.func_77960_j();
         ++this.drinkAmount;
         this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
         this.func_70296_d();
         return true;
      } else if (this.drinkMeta == itemstack.func_77960_j() && this.drinkAmount < MAX_CAPACITY) {
         ++this.drinkAmount;
         this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
         this.func_70296_d();
         return true;
      } else {
         return false;
      }
   }

   public void fillWithWater() {
      if (this.drinkMeta == -1 && this.drinkAmount < MAX_CAPACITY) {
         ++this.drinkAmount;
      }

      this.drinkAmount = Math.min(this.drinkAmount, MAX_CAPACITY);
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
   }

   public void consume() {
      --this.drinkAmount;
      if (this.drinkAmount <= 0) {
         this.drinkMeta = -1;
      }

      this.drinkAmount = Math.max(this.drinkAmount, 0);
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      nbt.func_74768_a("DrinkMeta", this.drinkMeta);
      nbt.func_74768_a("DrinkAmount", this.drinkAmount);
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      this.drinkMeta = nbt.func_74762_e("DrinkMeta");
      this.drinkAmount = nbt.func_74762_e("DrinkAmount");
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
