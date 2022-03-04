package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRTileEntityArmorStand extends TileEntity implements IInventory {
   private ItemStack[] inventory = new ItemStack[4];
   public int ticksExisted;

   public void func_145834_a(World world) {
      super.func_145834_a(world);
      this.ticksExisted = world.field_73012_v.nextInt(100);
   }

   public void func_145845_h() {
      ++this.ticksExisted;
   }

   public int func_70302_i_() {
      return this.inventory.length;
   }

   public ItemStack func_70301_a(int i) {
      return this.inventory[i];
   }

   public ItemStack func_70298_a(int i, int j) {
      if (this.inventory[i] != null) {
         ItemStack itemstack;
         if (this.inventory[i].field_77994_a <= j) {
            itemstack = this.inventory[i];
            this.inventory[i] = null;
            this.func_70296_d();
            return itemstack;
         } else {
            itemstack = this.inventory[i].func_77979_a(j);
            if (this.inventory[i].field_77994_a == 0) {
               this.inventory[i] = null;
            }

            this.func_70296_d();
            return itemstack;
         }
      } else {
         return null;
      }
   }

   public ItemStack func_70304_b(int i) {
      if (this.inventory[i] != null) {
         ItemStack itemstack = this.inventory[i];
         this.inventory[i] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void func_70299_a(int i, ItemStack itemstack) {
      this.inventory[i] = itemstack;
      if (itemstack != null && itemstack.field_77994_a > this.func_70297_j_()) {
         itemstack.field_77994_a = this.func_70297_j_();
      }

      this.func_70296_d();
   }

   public void func_70296_d() {
      super.func_70296_d();
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
   }

   public String func_145825_b() {
      return StatCollector.func_74838_a("container.lotr.armorStand");
   }

   public boolean func_145818_k_() {
      return false;
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      this.readArmorStandFromNBT(nbt);
   }

   private void readArmorStandFromNBT(NBTTagCompound nbt) {
      NBTTagList items = nbt.func_150295_c("Items", 10);
      this.inventory = new ItemStack[this.func_70302_i_()];

      for(int i = 0; i < items.func_74745_c(); ++i) {
         NBTTagCompound itemData = items.func_150305_b(i);
         byte byte0 = itemData.func_74771_c("Slot");
         if (byte0 >= 0 && byte0 < this.inventory.length) {
            this.inventory[byte0] = ItemStack.func_77949_a(itemData);
         }
      }

   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      this.writeArmorStandToNBT(nbt);
   }

   private void writeArmorStandToNBT(NBTTagCompound nbt) {
      NBTTagList items = new NBTTagList();

      for(int i = 0; i < this.inventory.length; ++i) {
         if (this.inventory[i] != null) {
            NBTTagCompound itemData = new NBTTagCompound();
            itemData.func_74774_a("Slot", (byte)i);
            this.inventory[i].func_77955_b(itemData);
            items.func_74742_a(itemData);
         }
      }

      nbt.func_74782_a("Items", items);
   }

   public int func_70297_j_() {
      return 64;
   }

   public boolean func_70300_a(EntityPlayer entityplayer) {
      return this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && entityplayer.func_70092_e((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D) <= 64.0D;
   }

   public void func_70295_k_() {
   }

   public void func_70305_f() {
   }

   public boolean func_94041_b(int slot, ItemStack itemstack) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.func_72330_a((double)(this.field_145851_c - 1), (double)this.field_145848_d, (double)(this.field_145849_e - 1), (double)(this.field_145851_c + 1), (double)(this.field_145848_d + 2), (double)(this.field_145849_e + 1));
   }

   public Packet func_145844_m() {
      NBTTagCompound data = new NBTTagCompound();
      this.writeArmorStandToNBT(data);
      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
      this.readArmorStandFromNBT(packet.func_148857_g());
   }
}
