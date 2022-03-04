package lotr.common.fac;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;

public class MapSquare {
   public final int mapX;
   public final int mapZ;
   public final int radius;

   public MapSquare(int x, int z, int r) {
      this.mapX = x;
      this.mapZ = z;
      this.radius = r;
   }

   public static MapSquare read(JsonObject json) {
      int mapX = json.get("x").getAsInt();
      int mapZ = json.get("z").getAsInt();
      int radius = json.get("radius").getAsInt();
      return new MapSquare(mapX, mapZ, radius);
   }

   public static MapSquare read(PacketBuffer buf) {
      int mapX = buf.func_150792_a();
      int mapZ = buf.func_150792_a();
      int radius = buf.func_150792_a();
      return new MapSquare(mapX, mapZ, radius);
   }

   public void write(PacketBuffer buf) {
      buf.func_150787_b(this.mapX);
      buf.func_150787_b(this.mapZ);
      buf.func_150787_b(this.radius);
   }
}
