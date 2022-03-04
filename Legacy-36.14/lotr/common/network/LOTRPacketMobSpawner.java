package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class LOTRPacketMobSpawner implements IMessage {
   private int xCoord;
   private int yCoord;
   private int zCoord;
   private int dimension;
   public int active;
   public boolean spawnsPersistentNPCs;
   public int minSpawnDelay;
   public int maxSpawnDelay;
   public int nearbyMobLimit;
   public int nearbyMobCheckRange;
   public int requiredPlayerRange;
   public int maxSpawnCount;
   public int maxSpawnRange;
   public int maxSpawnRangeVertical;
   public int maxHealth;
   public int navigatorRange;
   public float moveSpeed;
   public float attackDamage;

   public LOTRPacketMobSpawner() {
   }

   public LOTRPacketMobSpawner(int x, int y, int z, int dim) {
      this.xCoord = x;
      this.yCoord = y;
      this.zCoord = z;
      this.dimension = dim;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.xCoord);
      data.writeInt(this.yCoord);
      data.writeInt(this.zCoord);
      data.writeByte(this.dimension);
      data.writeByte(this.active);
      data.writeBoolean(this.spawnsPersistentNPCs);
      data.writeInt(this.minSpawnDelay);
      data.writeInt(this.maxSpawnDelay);
      data.writeByte(this.nearbyMobLimit);
      data.writeByte(this.nearbyMobCheckRange);
      data.writeByte(this.requiredPlayerRange);
      data.writeByte(this.maxSpawnCount);
      data.writeByte(this.maxSpawnRange);
      data.writeByte(this.maxSpawnRangeVertical);
      data.writeShort(this.maxHealth);
      data.writeByte(this.navigatorRange);
      data.writeFloat(this.moveSpeed);
      data.writeFloat(this.attackDamage);
   }

   public void fromBytes(ByteBuf data) {
      this.xCoord = data.readInt();
      this.yCoord = data.readInt();
      this.zCoord = data.readInt();
      this.dimension = data.readByte();
      this.active = data.readByte();
      this.spawnsPersistentNPCs = data.readBoolean();
      this.minSpawnDelay = data.readInt();
      this.maxSpawnDelay = data.readInt();
      this.nearbyMobLimit = data.readByte();
      this.nearbyMobCheckRange = data.readByte();
      this.requiredPlayerRange = data.readByte();
      this.maxSpawnCount = data.readByte();
      this.maxSpawnRange = data.readByte();
      this.maxSpawnRangeVertical = data.readByte();
      this.maxHealth = data.readShort();
      this.navigatorRange = data.readByte();
      this.moveSpeed = data.readFloat();
      this.attackDamage = data.readFloat();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMobSpawner packet, MessageContext context) {
         World world = DimensionManager.getWorld(packet.dimension);
         if (world != null) {
            int i = packet.xCoord;
            int j = packet.yCoord;
            int k = packet.zCoord;
            TileEntity tileentity = world.func_147438_o(i, j, k);
            if (tileentity instanceof LOTRTileEntityMobSpawner) {
               LOTRTileEntityMobSpawner mobSpawner = (LOTRTileEntityMobSpawner)tileentity;
               mobSpawner.active = packet.active;
               mobSpawner.spawnsPersistentNPCs = packet.spawnsPersistentNPCs;
               mobSpawner.minSpawnDelay = packet.minSpawnDelay;
               mobSpawner.maxSpawnDelay = packet.maxSpawnDelay;
               mobSpawner.nearbyMobLimit = packet.nearbyMobLimit;
               mobSpawner.nearbyMobCheckRange = packet.nearbyMobCheckRange;
               mobSpawner.requiredPlayerRange = packet.requiredPlayerRange;
               mobSpawner.maxSpawnCount = packet.maxSpawnCount;
               mobSpawner.maxSpawnRange = packet.maxSpawnRange;
               mobSpawner.maxSpawnRangeVertical = packet.maxSpawnRangeVertical;
               mobSpawner.maxHealth = packet.maxHealth;
               mobSpawner.navigatorRange = packet.navigatorRange;
               mobSpawner.moveSpeed = packet.moveSpeed;
               mobSpawner.attackDamage = packet.attackDamage;
               world.func_147471_g(i, j, k);
               mobSpawner.delay = -1;
               world.func_147452_c(i, j, k, mobSpawner.func_145838_q(), 2, 0);
            }
         }

         return null;
      }
   }
}
