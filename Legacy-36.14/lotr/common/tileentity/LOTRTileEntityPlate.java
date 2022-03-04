package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.entity.LOTRPlateFallingInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class LOTRTileEntityPlate extends TileEntity {
   private ItemStack foodItem;
   public LOTRPlateFallingInfo plateFallInfo;

   public static boolean isValidFoodItem(ItemStack itemstack) {
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (item instanceof ItemFood) {
            if (item instanceof ItemSoup) {
               return false;
            }

            if (item.hasContainerItem(itemstack)) {
               return false;
            }

            return true;
         }
      }

      return false;
   }

   public ItemStack getFoodItem() {
      return this.foodItem;
   }

   public void setFoodItem(ItemStack item) {
      if (item != null && item.field_77994_a <= 0) {
         item = null;
      }

      this.foodItem = item;
      if (this.field_145850_b != null) {
         this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      }

      this.func_70296_d();
   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      nbt.func_74757_a("PlateEmpty", this.foodItem == null);
      if (this.foodItem != null) {
         nbt.func_74782_a("FoodItem", this.foodItem.func_77955_b(new NBTTagCompound()));
      }

   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      if (nbt.func_74764_b("FoodID")) {
         Item item = Item.func_150899_d(nbt.func_74762_e("FoodID"));
         if (item != null) {
            int damage = nbt.func_74762_e("FoodDamage");
            this.foodItem = new ItemStack(item, 1, damage);
         }
      } else {
         boolean empty = nbt.func_74767_n("PlateEmpty");
         if (empty) {
            this.foodItem = null;
         } else {
            this.foodItem = ItemStack.func_77949_a(nbt.func_74775_l("FoodItem"));
         }
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

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      AxisAlignedBB bb = super.getRenderBoundingBox();
      if (this.foodItem != null) {
         bb = bb.func_72321_a(0.0D, (double)((float)this.foodItem.field_77994_a * 0.03125F), 0.0D);
      }

      return bb;
   }
}
