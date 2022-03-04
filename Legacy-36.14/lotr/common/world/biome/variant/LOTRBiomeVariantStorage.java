package lotr.common.world.biome.variant;

import cpw.mods.fml.common.FMLLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lotr.common.LOTRDimension;
import lotr.common.network.LOTRPacketBiomeVariantsUnwatch;
import lotr.common.network.LOTRPacketBiomeVariantsWatch;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class LOTRBiomeVariantStorage {
   private static Map chunkVariantMap = new HashMap();
   private static Map chunkVariantMapClient = new HashMap();

   private static Map getDimensionChunkMap(World world) {
      Map sourcemap;
      if (!world.field_72995_K) {
         sourcemap = chunkVariantMap;
      } else {
         sourcemap = chunkVariantMapClient;
      }

      LOTRDimension dim = LOTRDimension.getCurrentDimensionWithFallback(world);
      Map map = (Map)sourcemap.get(dim);
      if (map == null) {
         map = new HashMap();
         sourcemap.put(dim, map);
      }

      return (Map)map;
   }

   private static ChunkCoordIntPair getChunkKey(Chunk chunk) {
      return new ChunkCoordIntPair(chunk.field_76635_g, chunk.field_76647_h);
   }

   public static byte[] getChunkBiomeVariants(World world, Chunk chunk) {
      return getChunkBiomeVariants(world, getChunkKey(chunk));
   }

   public static byte[] getChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
      return (byte[])getDimensionChunkMap(world).get(chunk);
   }

   public static void setChunkBiomeVariants(World world, Chunk chunk, byte[] variants) {
      setChunkBiomeVariants(world, getChunkKey(chunk), variants);
   }

   public static void setChunkBiomeVariants(World world, ChunkCoordIntPair chunk, byte[] variants) {
      getDimensionChunkMap(world).put(chunk, variants);
   }

   public static void clearChunkBiomeVariants(World world, Chunk chunk) {
      clearChunkBiomeVariants(world, getChunkKey(chunk));
   }

   public static void clearChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
      getDimensionChunkMap(world).remove(chunk);
   }

   public static void loadChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
      if (getChunkBiomeVariants(world, chunk) == null) {
         byte[] variants;
         if (data.func_74764_b("LOTRBiomeVariants")) {
            variants = data.func_74770_j("LOTRBiomeVariants");
         } else {
            variants = new byte[256];
         }

         setChunkBiomeVariants(world, chunk, variants);
      }

   }

   public static void saveChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
      byte[] variants = getChunkBiomeVariants(world, chunk);
      if (variants != null) {
         data.func_74773_a("LOTRBiomeVariants", variants);
      }

   }

   public static void clearAllVariants(World world) {
      getDimensionChunkMap(world).clear();
      FMLLog.info("Unloading LOTR biome variants in %s", new Object[]{LOTRDimension.getCurrentDimensionWithFallback(world).dimensionName});
   }

   public static void performCleanup(WorldServer world) {
      Map dimensionMap = getDimensionChunkMap(world);
      int loaded = dimensionMap.size();
      long l = System.nanoTime();
      List removalChunks = new ArrayList();
      Iterator var6 = dimensionMap.keySet().iterator();

      while(var6.hasNext()) {
         ChunkCoordIntPair chunk = (ChunkCoordIntPair)var6.next();
         if (!world.field_73059_b.func_73149_a(chunk.field_77276_a, chunk.field_77275_b)) {
            removalChunks.add(chunk);
         }
      }

      int removed = 0;

      for(Iterator var10 = removalChunks.iterator(); var10.hasNext(); ++removed) {
         ChunkCoordIntPair chunk = (ChunkCoordIntPair)var10.next();
         dimensionMap.remove(chunk);
      }

   }

   public static void sendChunkVariantsToPlayer(World world, Chunk chunk, EntityPlayerMP entityplayer) {
      byte[] variants = getChunkBiomeVariants(world, chunk);
      if (variants != null) {
         LOTRPacketBiomeVariantsWatch packet = new LOTRPacketBiomeVariantsWatch(chunk.field_76635_g, chunk.field_76647_h, variants);
         LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
      } else {
         String dimName = world.field_73011_w.func_80007_l();
         int posX = chunk.field_76635_g << 4;
         int posZ = chunk.field_76647_h << 4;
         String playerName = entityplayer.func_70005_c_();
         FMLLog.severe("Could not find LOTR biome variants for %s chunk at %d, %d; requested by %s", new Object[]{dimName, posX, posZ, playerName});
      }

   }

   public static void sendUnwatchToPlayer(World world, Chunk chunk, EntityPlayerMP entityplayer) {
      LOTRPacketBiomeVariantsUnwatch packet = new LOTRPacketBiomeVariantsUnwatch(chunk.field_76635_g, chunk.field_76647_h);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   public static int getSize(World world) {
      Map map = getDimensionChunkMap(world);
      return map.size();
   }
}
