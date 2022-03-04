package lotr.common.network;

import io.netty.buffer.Unpooled;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.PacketBuffer;

public abstract class ByteArrayPacket {
   private final byte[] byteData;

   protected ByteArrayPacket(byte[] data) {
      this.byteData = data;
   }

   protected ByteArrayPacket(Consumer dataWriter) {
      PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
      dataWriter.accept(buf);
      this.byteData = new byte[buf.readableBytes()];
      buf.getBytes(0, this.byteData);
   }

   protected static void encodeByteData(ByteArrayPacket packet, PacketBuffer buf) {
      buf.func_179250_a(packet.byteData);
   }

   protected static ByteArrayPacket decodeByteData(PacketBuffer buf, Function packetConstructor) {
      byte[] byteData = buf.func_179251_a();
      return (ByteArrayPacket)packetConstructor.apply(byteData);
   }

   protected final PacketBuffer getBufferedByteData() {
      return new PacketBuffer(Unpooled.copiedBuffer(this.byteData));
   }
}
