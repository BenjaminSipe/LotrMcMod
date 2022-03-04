package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class LOTRTileEntityCommandTable extends TileEntity {
   private int zoomExp;
   private static final int MIN_ZOOM = -2;
   private static final int MAX_ZOOM = 2;

   public int getZoomExp() {
      return this.zoomExp;
   }

   public void setZoomExp(int i) {
      this.zoomExp = MathHelper.func_76125_a(i, -2, 2);
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
   }

   public void toggleZoomExp() {
      int z = this.zoomExp;
      if (z <= -2) {
         z = 2;
      } else {
         --z;
      }

      this.setZoomExp(z);
   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      this.writeTableToNBT(nbt);
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      this.readTableFromNBT(nbt);
   }

   private void writeTableToNBT(NBTTagCompound nbt) {
      nbt.func_74774_a("Zoom", (byte)this.zoomExp);
   }

   private void readTableFromNBT(NBTTagCompound nbt) {
      this.zoomExp = nbt.func_74771_c("Zoom");
   }

   public Packet func_145844_m() {
      NBTTagCompound data = new NBTTagCompound();
      this.writeTableToNBT(data);
      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
      NBTTagCompound data = packet.func_148857_g();
      this.readTableFromNBT(data);
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.func_72330_a((double)(this.field_145851_c - 1), (double)this.field_145848_d, (double)(this.field_145849_e - 1), (double)(this.field_145851_c + 2), (double)(this.field_145848_d + 2), (double)(this.field_145849_e + 2));
   }
}
