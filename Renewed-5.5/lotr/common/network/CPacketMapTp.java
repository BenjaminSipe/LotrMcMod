package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.util.LOTRUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class CPacketMapTp {
   private final int xCoord;
   private final int zCoord;

   public CPacketMapTp(int x, int z) {
      this.xCoord = x;
      this.zCoord = z;
   }

   public static void encode(CPacketMapTp packet, PacketBuffer buf) {
      buf.writeInt(packet.xCoord);
      buf.writeInt(packet.zCoord);
   }

   public static CPacketMapTp decode(PacketBuffer buf) {
      int xCoord = buf.readInt();
      int zCoord = buf.readInt();
      return new CPacketMapTp(xCoord, zCoord);
   }

   public static void handle(CPacketMapTp packet, Supplier context) {
      ServerPlayerEntity player = ((Context)context.get()).getSender();
      if (CPacketIsOpRequest.isOpCanTeleport(player)) {
         World world = player.field_70170_p;
         int x = packet.xCoord;
         int z = packet.zCoord;
         int y = LOTRUtil.forceLoadChunkAndGetTopBlock(world, x, z);
         if (player.field_71075_bZ.field_75098_d && player.field_71075_bZ.field_75100_b) {
            BlockPos currentPos = player.func_233580_cy_();
            int currentHeightAboveTop = currentPos.func_177956_o() - world.func_205770_a(Type.MOTION_BLOCKING_NO_LEAVES, currentPos).func_177956_o();
            if (currentHeightAboveTop > 0) {
               y += currentHeightAboveTop;
            }
         }

         String command = String.format("/tp %d %d %d", x, y, z);
         player.func_184102_h().func_195571_aL().func_197059_a(player.func_195051_bN(), command);
      }

      ((Context)context.get()).setPacketHandled(true);
   }
}
