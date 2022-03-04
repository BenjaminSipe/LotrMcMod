package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public class LOTRPacketBiomeVariantsUnwatch implements IMessage {
   private int chunkX;
   private int chunkZ;

   public LOTRPacketBiomeVariantsUnwatch() {
   }

   public LOTRPacketBiomeVariantsUnwatch(int x, int z) {
      this.chunkX = x;
      this.chunkZ = z;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.chunkX);
      data.writeInt(this.chunkZ);
   }

   public void fromBytes(ByteBuf data) {
      this.chunkX = data.readInt();
      this.chunkZ = data.readInt();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketBiomeVariantsUnwatch packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         int chunkX = packet.chunkX;
         int chunkZ = packet.chunkZ;
         if (world.func_72899_e(chunkX << 4, 0, chunkZ << 4)) {
            LOTRBiomeVariantStorage.clearChunkBiomeVariants(world, new ChunkCoordIntPair(chunkX, chunkZ));
         } else {
            FMLLog.severe("Client received LOTR biome variant unwatch packet for nonexistent chunk at %d, %d", new Object[]{chunkX << 4, chunkZ << 4});
         }

         return null;
      }
   }
}
