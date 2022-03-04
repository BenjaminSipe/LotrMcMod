package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Map;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import org.apache.commons.lang3.tuple.Pair;

public class LOTRTileEntityDwarvenDoor extends TileEntity {
   private static Map replacementGlowTicks = new HashMap();
   private static int GLOW_RANGE = 12;
   private LOTRDwarvenGlowLogic glowLogic;
   private LOTRBlockGateDwarvenIthildin.DoorSize doorSize;
   private int doorPosX;
   private int doorPosY;
   private int doorBaseX;
   private int doorBaseY;
   private int doorBaseZ;

   public LOTRTileEntityDwarvenDoor() {
      this.glowLogic = (new LOTRDwarvenGlowLogic()).setPlayerRange(GLOW_RANGE);
   }

   public void setDoorSizeAndPos(LOTRBlockGateDwarvenIthildin.DoorSize size, int i, int j) {
      if (size == null) {
         size = LOTRBlockGateDwarvenIthildin.DoorSize._1x1;
      }

      this.doorSize = size;
      this.doorPosX = i;
      this.doorPosY = j;
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
   }

   public LOTRBlockGateDwarvenIthildin.DoorSize getDoorSize() {
      if (this.doorSize == null) {
         this.doorSize = LOTRBlockGateDwarvenIthildin.DoorSize._1x1;
      }

      return this.doorSize;
   }

   public int getDoorPosX() {
      if (this.doorPosX < 0 || this.doorSize != null && this.doorPosX >= this.doorSize.width) {
         this.doorPosX = 0;
      }

      return this.doorPosX;
   }

   public int getDoorPosY() {
      if (this.doorPosY < 0 || this.doorSize != null && this.doorPosY >= this.doorSize.height) {
         this.doorPosY = 0;
      }

      return this.doorPosY;
   }

   public void setDoorBasePos(int i, int j, int k) {
      this.doorBaseX = i;
      this.doorBaseY = j;
      this.doorBaseZ = k;
      this.glowLogic.resetGlowTick();
      this.func_70296_d();
   }

   public boolean isSameDoor(LOTRTileEntityDwarvenDoor other) {
      return this.doorBaseX == other.doorBaseX && this.doorBaseY == other.doorBaseY && this.doorBaseZ == other.doorBaseZ;
   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      this.writeDoorToNBT(nbt);
   }

   private void writeDoorToNBT(NBTTagCompound nbt) {
      if (this.doorSize != null) {
         nbt.func_74778_a("DoorSize", this.doorSize.doorName);
         nbt.func_74774_a("DoorX", (byte)this.doorPosX);
         nbt.func_74774_a("DoorY", (byte)this.doorPosY);
         nbt.func_74768_a("DoorBaseX", this.doorBaseX);
         nbt.func_74768_a("DoorBaseY", this.doorBaseY);
         nbt.func_74768_a("DoorBaseZ", this.doorBaseZ);
      }

   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      this.readDoorFromNBT(nbt);
   }

   private void readDoorFromNBT(NBTTagCompound nbt) {
      if (nbt.func_74764_b("DoorSize")) {
         this.doorSize = LOTRBlockGateDwarvenIthildin.DoorSize.forName(nbt.func_74779_i("DoorSize"));
         this.doorPosX = nbt.func_74771_c("DoorX");
         this.doorPosY = nbt.func_74771_c("DoorY");
         this.doorBaseX = nbt.func_74762_e("DoorBaseX");
         this.doorBaseY = nbt.func_74762_e("DoorBaseY");
         this.doorBaseZ = nbt.func_74762_e("DoorBaseZ");
      }

   }

   public Packet func_145844_m() {
      NBTTagCompound data = new NBTTagCompound();
      this.writeDoorToNBT(data);
      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
      NBTTagCompound data = packet.func_148857_g();
      this.readDoorFromNBT(data);
   }

   public void func_145845_h() {
      super.func_145845_h();
      if (this.doorSize != null) {
         this.glowLogic.update(this.field_145850_b, this.doorBaseX, this.doorBaseY, this.doorBaseZ);
      } else {
         this.glowLogic.update(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
      }

   }

   public void func_145843_s() {
      super.func_145843_s();
      if (this.field_145850_b.field_72995_K) {
         long time = this.field_145850_b.func_82737_E();
         int glow = this.glowLogic.getGlowTick();
         ChunkCoordinates coords = new ChunkCoordinates(this.field_145851_c, this.field_145848_d, this.field_145849_e);
         replacementGlowTicks.put(coords, Pair.of(time, glow));
      }

   }

   public void func_145829_t() {
      super.func_145829_t();
      if (this.field_145850_b.field_72995_K) {
         ChunkCoordinates coords = new ChunkCoordinates(this.field_145851_c, this.field_145848_d, this.field_145849_e);
         long time = this.field_145850_b.func_82737_E();
         if (replacementGlowTicks.containsKey(coords)) {
            Pair stored = (Pair)replacementGlowTicks.get(coords);
            long storedTime = (Long)stored.getLeft();
            int glow = (Integer)stored.getRight();
            if (time == storedTime) {
               this.glowLogic.setGlowTick(glow);
            }

            replacementGlowTicks.remove(coords);
         }
      }

   }

   public float getGlowBrightness(float f) {
      if (this.doorSize != null && this.field_145850_b.func_72899_e(this.doorBaseX, this.doorBaseY, this.doorBaseZ)) {
         TileEntity te = this.field_145850_b.func_147438_o(this.doorPosX, this.doorBaseY, this.doorBaseZ);
         if (te instanceof LOTRTileEntityDwarvenDoor) {
            LOTRTileEntityDwarvenDoor otherDoor = (LOTRTileEntityDwarvenDoor)te;
            return otherDoor.glowLogic.getGlowBrightness(this.field_145850_b, this.doorPosX, this.doorBaseY, this.doorBaseZ, f);
         }
      }

      return this.glowLogic.getGlowBrightness(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, f);
   }

   @SideOnly(Side.CLIENT)
   public double func_145833_n() {
      double range = (double)(GLOW_RANGE + 20);
      return range * range;
   }
}
