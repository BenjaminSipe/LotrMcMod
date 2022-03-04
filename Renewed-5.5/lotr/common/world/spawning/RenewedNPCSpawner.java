package lotr.common.world.spawning;

import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import lotr.common.LOTRLog;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.LOTRDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;

public class RenewedNPCSpawner {
   private static final Map PER_WORLD_SPAWNERS = new HashMap();
   private final RegistryKey dimension;
   private long lastCycleTime;
   private static final long SPAWN_CYCLE_INTERVAL = 20L;
   private final List spawnableChunks = new ArrayList();
   private static final int SPAWNABLE_CHUNK_RADIUS = 7;
   private static final double AVG_NPCS_PER_CHUNK = 0.51D;

   private RenewedNPCSpawner(RegistryKey dimension) {
      this.dimension = dimension;
   }

   public static RenewedNPCSpawner getForWorld(ServerWorld world) {
      return (RenewedNPCSpawner)PER_WORLD_SPAWNERS.computeIfAbsent(world.func_234923_W_(), RenewedNPCSpawner::new);
   }

   public void runSpawning(ServerWorld world) {
      if (world.func_82736_K().func_223586_b(GameRules.field_223601_d)) {
         world.func_217381_Z().func_76320_a("lotrNpcSpawning");
         long worldTime = world.func_82737_E();
         this.lastCycleTime = Math.min(this.lastCycleTime, worldTime);
         if (worldTime - this.lastCycleTime >= 20L) {
            this.runSpawnCycle(world);
            this.lastCycleTime = worldTime;
         }

         world.func_217381_Z().func_76319_b();
      }
   }

   private void runSpawnCycle(ServerWorld world) {
      List chunks = this.gatherSpawnableChunks(world);
      double sumDensity = (Double)chunks.stream().collect(Collectors.summingDouble((chunk) -> {
         return this.getNPCDensityCapForChunk(world, chunk);
      }));
      int cap = (int)(sumDensity * 0.51D);
      double count = (Double)Streams.stream(world.func_241136_z_()).filter(this::includeInNPCCount).map((e) -> {
         return (NPCEntity)e;
      }).collect(Collectors.summingDouble(NPCEntity::getSpawnCountWeight));
      if (count < (double)cap) {
         Collections.shuffle(chunks);
         Random rand = world.field_73012_v;
         Iterator var9 = chunks.iterator();

         while(var9.hasNext()) {
            ChunkPos chunkPos = (ChunkPos)var9.next();
            count += this.performSpawningInChunk(world, chunkPos, rand);
            if (count >= (double)cap) {
               break;
            }
         }
      }

   }

   private boolean includeInNPCCount(Entity e) {
      return e.func_70089_S() && e instanceof NPCEntity;
   }

   private List gatherSpawnableChunks(ServerWorld world) {
      this.spawnableChunks.clear();
      ServerChunkProvider scp = world.func_72863_F();
      Iterator var3 = world.func_217369_A().iterator();

      while(var3.hasNext()) {
         ServerPlayerEntity player = (ServerPlayerEntity)var3.next();
         int chunkX = MathHelper.func_76128_c(player.func_226277_ct_() / 16.0D);
         int chunkZ = MathHelper.func_76128_c(player.func_226281_cx_() / 16.0D);

         for(int dx = -7; dx <= 7; ++dx) {
            for(int dz = -7; dz <= 7; ++dz) {
               ChunkPos chunkPos = new ChunkPos(chunkX + dx, chunkZ + dz);
               if (!this.spawnableChunks.contains(chunkPos) && scp.func_222865_a(chunkPos)) {
                  this.spawnableChunks.add(chunkPos);
               }
            }
         }
      }

      return this.spawnableChunks;
   }

   private double getNPCDensityCapForChunk(ServerWorld world, ChunkPos chunkPos) {
      Random rand = world.field_73012_v;
      int x = MathHelper.func_76136_a(rand, chunkPos.func_180334_c(), chunkPos.func_180332_e());
      int z = MathHelper.func_76136_a(rand, chunkPos.func_180333_d(), chunkPos.func_180330_f());
      int y = world.func_181545_F();
      return this.getNPCDensityForBiome(world, world.func_226691_t_(new BlockPos(x, y, z)));
   }

   private double getNPCDensityForBiome(ServerWorld world, Biome biome) {
      return NPCSpawnSettingsManager.getSpawnsForBiome(biome, world).getNPCDensity();
   }

   private double performSpawningInChunk(ServerWorld world, ChunkPos chunkPos, Random rand) {
      double addedSpawnCountWeight = 0.0D;
      BlockPos pos = getRandomSpawnPositionInChunk(world, chunkPos, rand);
      if (pos.func_177956_o() >= 1) {
         int y = pos.func_177956_o();
         BlockState state = world.func_180495_p(pos);
         if (!state.func_215686_e(world, pos)) {
            Mutable movingPos = new Mutable();
            int j = false;
            int groups = 3;

            for(int l = 0; l < groups; ++l) {
               int x = pos.func_177958_n();
               int z = pos.func_177952_p();
               int range = true;
               int yRange = false;
               int rangeP1 = true;
               int yRangeP1 = true;
               NPCSpawnEntry.EntryInContext spawnEntryInstance = getRandomSpawnListEntry(world, pos);
               if (spawnEntryInstance != null) {
                  ILivingEntityData entityGroupData = null;
                  boolean isConquestSpawn = spawnEntryInstance.isConquestSpawn();
                  int groupSize = spawnEntryInstance.getRandomGroupSize(rand);
                  int spawnedInGroup = 0;
                  int attempts = groupSize * 8;

                  for(int a = 0; a < attempts; ++a) {
                     x += rand.nextInt(6) - rand.nextInt(6);
                     z += rand.nextInt(6) - rand.nextInt(6);
                     y += rand.nextInt(1) - rand.nextInt(1);
                     movingPos.func_181079_c(x, y, z);
                     double xd = (double)x + 0.5D;
                     double zd = (double)z + 0.5D;
                     PlayerEntity closestPlayer = world.func_217366_a(xd, (double)y, zd, -1.0D, false);
                     if (closestPlayer != null) {
                        double distSqToPlayer = closestPlayer.func_70092_e(xd, (double)y, zd);
                        if (isSuitableSpawnLocation(world, chunkPos, movingPos, distSqToPlayer)) {
                           EntityType typeToSpawn = spawnEntryInstance.getTypeToSpawn(rand);
                           if (canNPCSpawnAtLocation(world, typeToSpawn, movingPos, distSqToPlayer)) {
                              NPCEntity entity = tryCreateNPC(world, typeToSpawn);
                              if (entity != null) {
                                 entity.func_70012_b(xd, (double)y, zd, rand.nextFloat() * 360.0F, 0.0F);
                                 int canSpawn = ForgeHooks.canEntitySpawn(entity, world, xd, (double)y, zd, (AbstractSpawner)null, SpawnReason.NATURAL);
                                 if (canSpawn != -1 && (canSpawn == 1 || canNPCSpawnNormally(world, entity, distSqToPlayer))) {
                                    if (!ForgeEventFactory.doSpecialSpawn(entity, world, (float)xd, (float)y, (float)zd, (AbstractSpawner)null, SpawnReason.NATURAL)) {
                                       entityGroupData = entity.func_213386_a(world, world.func_175649_E(entity.func_233580_cy_()), SpawnReason.NATURAL, entityGroupData, (CompoundNBT)null);
                                    }

                                    world.func_242417_l(entity);
                                    addedSpawnCountWeight += entity.getSpawnCountWeight();
                                    ++spawnedInGroup;
                                    if (spawnedInGroup >= groupSize || spawnedInGroup >= ForgeEventFactory.getMaxSpawnPackSize(entity) || entity.func_204209_c(spawnedInGroup)) {
                                       break;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return addedSpawnCountWeight;
   }

   private static BlockPos getRandomSpawnPositionInChunk(ServerWorld world, ChunkPos chunkPos, Random rand) {
      int x = MathHelper.func_76136_a(rand, chunkPos.func_180334_c(), chunkPos.func_180332_e());
      int z = MathHelper.func_76136_a(rand, chunkPos.func_180333_d(), chunkPos.func_180330_f());
      int topY = world.func_201676_a(Type.WORLD_SURFACE, x, z) + 1;
      int y = rand.nextInt(topY + 1);
      return new BlockPos(x, y, z);
   }

   private static NPCSpawnEntry.EntryInContext getRandomSpawnListEntry(World world, BlockPos pos) {
      Random rand = world.field_73012_v;
      Biome biome = world.func_226691_t_(pos);
      BiomeNPCSpawnList spawnList = NPCSpawnSettingsManager.getSpawnsForBiome(biome, world);
      return spawnList.getRandomSpawnEntry(rand, world, pos);
   }

   private static boolean isSuitableSpawnLocation(ServerWorld world, ChunkPos mainChunkPos, Mutable pos, double distSqToPlayer) {
      double playerAndSpawnExclusionRange = 24.0D;
      if (distSqToPlayer <= 576.0D) {
         return false;
      } else if (LOTRDimensions.getDimensionSpawnPoint(world).func_218137_a(Vector3d.func_237492_c_(pos), 24.0D)) {
         return false;
      } else {
         ChunkPos chunkPosAtBlock = new ChunkPos(pos);
         return Objects.equals(chunkPosAtBlock, mainChunkPos) || world.func_72863_F().func_222865_a(chunkPosAtBlock);
      }
   }

   private static boolean canNPCSpawnAtLocation(ServerWorld world, EntityType type, Mutable pos, double distSqToPlayer) {
      double despawnDist = (double)type.func_220339_d().func_233671_f_();
      if (!type.func_225437_d() && distSqToPlayer > despawnDist * despawnDist) {
         return false;
      } else if (type.func_200720_b()) {
         PlacementType placementType = EntitySpawnPlacementRegistry.func_209344_a(type);
         if (!WorldEntitySpawner.func_209382_a(placementType, world, pos, type)) {
            return false;
         } else {
            return !EntitySpawnPlacementRegistry.func_223515_a(type, world, SpawnReason.NATURAL, pos, world.field_73012_v) ? false : world.func_226664_a_(type.func_220328_a((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o(), (double)pos.func_177952_p() + 0.5D));
         }
      } else {
         return false;
      }
   }

   private static NPCEntity tryCreateNPC(ServerWorld world, EntityType type) {
      try {
         Entity entity = type.func_200721_a(world);
         if (!(entity instanceof NPCEntity)) {
            throw new IllegalStateException("LOTR mob spawner trying to spawn a non-NPC: " + Registry.field_212629_r.func_177774_c(type));
         } else {
            return (NPCEntity)entity;
         }
      } catch (Exception var3) {
         var3.printStackTrace();
         LOTRLog.warn("Failed to create spawned NPC", var3);
         return null;
      }
   }

   private static boolean canNPCSpawnNormally(ServerWorld world, NPCEntity entity, double distSqToPlayer) {
      double despawnDist = (double)entity.func_200600_R().func_220339_d().func_233671_f_();
      if (distSqToPlayer > despawnDist * despawnDist && entity.func_213397_c(distSqToPlayer)) {
         return false;
      } else {
         return entity.func_213380_a(world, SpawnReason.NATURAL) && entity.func_205019_a(world);
      }
   }
}
