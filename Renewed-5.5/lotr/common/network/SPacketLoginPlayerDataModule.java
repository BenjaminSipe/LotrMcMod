package lotr.common.network;

import io.netty.buffer.Unpooled;
import java.util.function.Supplier;
import lotr.common.LOTRMod;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketLoginPlayerDataModule {
   private final String moduleCode;
   private final byte[] moduleData;

   private SPacketLoginPlayerDataModule(String module, byte[] data) {
      this.moduleCode = module;
      this.moduleData = data;
   }

   public SPacketLoginPlayerDataModule(String module, PacketBuffer data) {
      this.moduleCode = module;
      this.moduleData = new byte[data.readableBytes()];
      data.getBytes(0, this.moduleData);
   }

   public static void encode(SPacketLoginPlayerDataModule packet, PacketBuffer buf) {
      buf.func_180714_a(packet.moduleCode);
      buf.func_179250_a(packet.moduleData);
   }

   public static SPacketLoginPlayerDataModule decode(PacketBuffer buf) {
      String moduleCode = buf.func_218666_n();
      byte[] moduleData = buf.func_179251_a();
      return new SPacketLoginPlayerDataModule(moduleCode, moduleData);
   }

   public static void handle(SPacketLoginPlayerDataModule packet, Supplier context) {
      PlayerEntity player = LOTRMod.PROXY.getClientPlayer();
      LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(player);
      PacketBuffer dataBuffer = new PacketBuffer(Unpooled.copiedBuffer(packet.moduleData));
      pd.receiveLoginData(packet.moduleCode, dataBuffer);
      ((Context)context.get()).setPacketHandled(true);
   }
}
