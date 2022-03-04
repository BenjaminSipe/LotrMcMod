package lotr.curuquesta.util;

import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;

public class StringSerializer {
   public static void write(String value, ByteBuf buf) {
      buf.writeInt(value.length());
      buf.writeBytes(value.getBytes(StandardCharsets.UTF_8));
   }

   public static String read(ByteBuf buf) {
      int length = buf.readInt();
      ByteBuf bytes = buf.readBytes(length);
      return bytes.toString(StandardCharsets.UTF_8);
   }
}
