package lotr.common.tileentity;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityFlowerPot extends TileEntity {
   public Item item;
   public int meta;

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      nbt.func_74768_a("PlantID", Item.func_150891_b(this.item));
      nbt.func_74768_a("PlantMeta", this.meta);
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      this.item = Item.func_150899_d(nbt.func_74762_e("PlantID"));
      this.meta = nbt.func_74762_e("PlantMeta");
      if (Block.func_149634_a(this.item) == null) {
         this.item = null;
         this.meta = 0;
      }

   }

   public Packet func_145844_m() {
      NBTTagCompound data = new NBTTagCompound();
      this.func_145841_b(data);
      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
      this.func_145839_a(packet.func_148857_g());
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
   }
}
