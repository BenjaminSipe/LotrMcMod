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

public class LOTRPacketBiomeVariantsWatch implements IMessage {
   private int chunkX;
   private int chunkZ;
   private byte[] variants;

   public LOTRPacketBiomeVariantsWatch() {
   }

   public LOTRPacketBiomeVariantsWatch(int x, int z, byte[] v) {
      this.chunkX = x;
      this.chunkZ = z;
      this.variants = v;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.chunkX);
      data.writeInt(this.chunkZ);
      data.writeInt(this.variants.length);
      data.writeBytes(this.variants);
   }

   public void fromBytes(ByteBuf data) {
      this.chunkX = data.readInt();
      this.chunkZ = data.readInt();
      int length = data.readInt();
      this.variants = data.readBytes(length).array();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketBiomeVariantsWatch packet, MessageContext context) {
         World world = LOTRMod.proxy.getClientWorld();
         int chunkX = packet.chunkX;
         int chunkZ = packet.chunkZ;
         if (world.func_72899_e(chunkX << 4, 0, chunkZ << 4)) {
            LOTRBiomeVariantStorage.setChunkBiomeVariants(world, new ChunkCoordIntPair(chunkX, chunkZ), packet.variants);
         } else {
            FMLLog.severe("Client received LOTR biome variant data for nonexistent chunk at %d, %d", new Object[]{chunkX << 4, chunkZ << 4});
         }

         return null;
      }
   }
}
