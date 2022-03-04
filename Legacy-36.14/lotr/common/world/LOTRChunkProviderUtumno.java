package lotr.common.world;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.mapgen.LOTRMapGenCaves;
import lotr.common.world.mapgen.LOTRMapGenCavesUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class LOTRChunkProviderUtumno implements IChunkProvider {
   private World worldObj;
   private Random rand;
   private BiomeGenBase[] biomesForGeneration;
   private LOTRBiomeVariant[] variantsForGeneration;
   private LOTRMapGenCaves caveGenerator = new LOTRMapGenCavesUtumno();

   public LOTRChunkProviderUtumno(World world, long l) {
      this.worldObj = world;
      this.rand = new Random(l);
      LOTRUtumnoLevel.setupLevels();
   }

   private void generateTerrain(int chunkX, int chunkZ, Block[] blocks, byte[] metadata) {
      Arrays.fill(blocks, Blocks.field_150350_a);
      LOTRUtumnoLevel.generateTerrain(this.worldObj, this.rand, chunkX, chunkZ, blocks, metadata);
   }

   public Chunk func_73158_c(int i, int j) {
      return this.func_73154_d(i, j);
   }

   public Chunk func_73154_d(int i, int k) {
      this.rand.setSeed((long)i * 341873128712L + (long)k * 132897987541L);
      LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)this.worldObj.func_72959_q();
      Block[] blocks = new Block[65536];
      byte[] metadata = new byte[65536];
      this.generateTerrain(i, k, blocks, metadata);
      this.biomesForGeneration = this.worldObj.func_72959_q().func_76933_b(this.biomesForGeneration, i * 16, k * 16, 16, 16);
      this.variantsForGeneration = chunkManager.getBiomeVariants(this.variantsForGeneration, i * 16, k * 16, 16, 16);
      this.caveGenerator.func_151539_a(this, this.worldObj, i, k, blocks);
      Chunk chunk = new Chunk(this.worldObj, i, k);
      ExtendedBlockStorage[] blockStorage = chunk.func_76587_i();

      int k1;
      int j1;
      for(int i1 = 0; i1 < 16; ++i1) {
         for(k1 = 0; k1 < 16; ++k1) {
            for(j1 = 0; j1 < 256; ++j1) {
               int blockIndex = i1 << 12 | k1 << 8 | j1;
               Block block = blocks[blockIndex];
               if (block != null && block != Blocks.field_150350_a) {
                  byte meta = metadata[blockIndex];
                  int j2 = j1 >> 4;
                  if (blockStorage[j2] == null) {
                     blockStorage[j2] = new ExtendedBlockStorage(j2 << 4, true);
                  }

                  blockStorage[j2].func_150818_a(i1, j1 & 15, k1, block);
                  blockStorage[j2].func_76654_b(i1, j1 & 15, k1, meta & 15);
               }
            }
         }
      }

      byte[] biomes = chunk.func_76605_m();

      for(k1 = 0; k1 < biomes.length; ++k1) {
         biomes[k1] = (byte)this.biomesForGeneration[k1].field_76756_M;
      }

      byte[] variants = new byte[256];

      for(j1 = 0; j1 < variants.length; ++j1) {
         variants[j1] = (byte)this.variantsForGeneration[j1].variantID;
      }

      LOTRBiomeVariantStorage.setChunkBiomeVariants(this.worldObj, chunk, variants);
      chunk.func_76613_n();
      return chunk;
   }

   public boolean func_73149_a(int i, int j) {
      return true;
   }

   public void func_73153_a(IChunkProvider ichunkprovider, int chunkX, int chunkZ) {
      BlockSand.field_149832_M = true;
      int i = chunkX * 16;
      int k = chunkZ * 16;
      BiomeGenBase biomegenbase = this.worldObj.func_72807_a(i + 16, k + 16);
      if (biomegenbase instanceof LOTRBiome) {
         LOTRBiome biome = (LOTRBiome)biomegenbase;
         this.rand.setSeed(this.worldObj.func_72905_C());
         long l1 = this.rand.nextLong() / 2L * 2L + 1L;
         long l2 = this.rand.nextLong() / 2L * 2L + 1L;
         this.rand.setSeed((long)chunkX * l1 + (long)chunkZ * l2 ^ this.worldObj.func_72905_C());
         biome.func_76728_a(this.worldObj, this.rand, i, k);
         BlockSand.field_149832_M = false;
      }
   }

   public boolean func_73151_a(boolean flag, IProgressUpdate update) {
      return true;
   }

   public void func_104112_b() {
   }

   public boolean func_73156_b() {
      return false;
   }

   public boolean func_73157_c() {
      return true;
   }

   public String func_73148_d() {
      return "UtumnoLevelSource";
   }

   public List func_73155_a(EnumCreatureType creatureType, int i, int j, int k) {
      BiomeGenBase biome = this.worldObj.func_72807_a(i, k);
      return biome == null ? null : biome.func_76747_a(creatureType);
   }

   public ChunkPosition func_147416_a(World world, String type, int i, int j, int k) {
      return null;
   }

   public int func_73152_e() {
      return 0;
   }

   public void func_82695_e(int i, int j) {
   }
}
