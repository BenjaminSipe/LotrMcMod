package lotr.common.tileentity;

import java.util.Iterator;
import java.util.Random;
import lotr.common.LOTRGameRules;
import lotr.common.block.GondorBeaconBlock;
import lotr.common.init.LOTRTileEntities;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class GondorBeaconTileEntity extends TileEntity implements ITickableTileEntity {
   private int ticksExisted;
   private boolean isBurning;
   private int litCounter;
   private int unlitCounter;
   private long lastManualStateChangeTime = -1L;

   public GondorBeaconTileEntity() {
      super((TileEntityType)LOTRTileEntities.GONDOR_BEACON.get());
   }

   public boolean isBurning() {
      return this.isBurning;
   }

   public void beginBurning() {
      if (!this.isBurning) {
         this.setBurning(true);
      }

   }

   public void extinguish() {
      if (this.isBurning) {
         this.updateFullyLit(false);
         this.setBurning(false);
      }

   }

   private void setBurning(boolean flag) {
      boolean wasBurning = this.isBurning;
      if (wasBurning != flag) {
         this.isBurning = flag;
         if (!this.isBurning) {
            this.litCounter = 0;
         } else {
            this.unlitCounter = 0;
         }

         this.lastManualStateChangeTime = this.field_145850_b.func_82737_E();
         this.func_70296_d();
         this.func_145831_w().func_184138_a(this.func_174877_v(), this.func_195044_w(), this.func_195044_w(), 3);
         if (wasBurning) {
         }
      }

   }

   private boolean isFullyLit() {
      return (Boolean)this.func_195044_w().func_177229_b(GondorBeaconBlock.FULLY_LIT);
   }

   private void updateFullyLit(boolean flag) {
      this.field_145850_b.func_175656_a(this.field_174879_c, (BlockState)this.field_145850_b.func_180495_p(this.field_174879_c).func_206870_a(GondorBeaconBlock.FULLY_LIT, flag));
      this.func_70296_d();
   }

   private int getSpreadRange() {
      return this.field_145850_b.func_82736_K().func_223592_c(LOTRGameRules.GONDOR_BEACON_RANGE);
   }

   private int getLightingTime() {
      return this.field_145850_b.func_82736_K().func_223592_c(LOTRGameRules.GONDOR_BEACON_LIGHTING_TIME);
   }

   private boolean shouldSpreadBurning() {
      return this.isBurning && this.litCounter >= this.getLightingTime();
   }

   private boolean shouldSpreadExtinguishing() {
      return !this.isBurning && this.unlitCounter >= this.getLightingTime();
   }

   public void func_73660_a() {
      ++this.ticksExisted;
      if (!this.field_145850_b.field_72995_K) {
         if (this.isBurning && this.litCounter < this.getLightingTime()) {
            ++this.litCounter;
            if (this.litCounter >= this.getLightingTime()) {
               this.updateFullyLit(true);
            }
         } else if (!this.isBurning && this.unlitCounter < this.getLightingTime()) {
            ++this.unlitCounter;
            if (this.unlitCounter >= this.getLightingTime()) {
               this.updateFullyLit(false);
            }
         }

         if (this.ticksExisted % 10 == 0 && (this.shouldSpreadBurning() || this.shouldSpreadExtinguishing())) {
            int range = this.getSpreadRange();
            int rangeSq = range * range;
            int chunkSearchRange = (range >> 4) + 1;
            int chunkX = this.func_174877_v().func_177958_n() >> 4;
            int chunkZ = this.func_174877_v().func_177952_p() >> 4;

            for(int i = -chunkSearchRange; i <= chunkSearchRange; ++i) {
               for(int k = -chunkSearchRange; k <= chunkSearchRange; ++k) {
                  int aChunkX = chunkX + i;
                  int aChunkZ = chunkZ + k;
                  if (this.field_145850_b.func_217354_b(aChunkX, aChunkZ)) {
                     Chunk chunk = this.field_145850_b.func_212866_a_(aChunkX, aChunkZ);
                     if (chunk != null) {
                        Iterator var11 = chunk.func_177434_r().values().iterator();

                        while(var11.hasNext()) {
                           TileEntity te = (TileEntity)var11.next();
                           if (!te.func_145837_r() && te instanceof GondorBeaconTileEntity) {
                              GondorBeaconTileEntity beacon = (GondorBeaconTileEntity)te;
                              if (this.func_174877_v().func_177951_i(beacon.func_174877_v()) <= (double)rangeSq) {
                                 this.spreadStateTo(beacon);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } else if (this.isFullyLit()) {
         this.addCampfireParticles();
      }

   }

   private void spreadStateTo(GondorBeaconTileEntity other) {
      if (this.lastManualStateChangeTime > other.lastManualStateChangeTime) {
         if (this.shouldSpreadBurning() && !other.isBurning()) {
            other.setBurning(true);
         } else if (this.shouldSpreadExtinguishing() && other.isBurning()) {
            other.setBurning(false);
         }
      }

   }

   private void addCampfireParticles() {
      World world = this.func_145831_w();
      if (world != null) {
         BlockPos pos = this.func_174877_v();
         if (!this.isTopCovered(pos)) {
            Random rand = world.field_73012_v;
            if (rand.nextFloat() < 0.11F) {
               int numSmoke = rand.nextInt(2) + 2;

               for(int i = 0; i < numSmoke; ++i) {
                  boolean isSignalFire = true;
                  boolean spawnExtraSmoke = false;
                  CampfireBlock.func_220098_a(world, pos, isSignalFire, spawnExtraSmoke);
               }
            }
         }
      }

   }

   private boolean isTopCovered(BlockPos beaconPos) {
      BlockPos abovePos = this.field_174879_c.func_177984_a();
      return LOTRUtil.hasSolidSide(this.field_145850_b, beaconPos.func_177984_a(), Direction.DOWN) || LOTRUtil.hasSolidSide(this.field_145850_b, beaconPos.func_177984_a(), Direction.UP);
   }

   public CompoundNBT func_189515_b(CompoundNBT nbt) {
      super.func_189515_b(nbt);
      this.writeBurning(nbt);
      nbt.func_74774_a("LitCounter", (byte)this.litCounter);
      nbt.func_74774_a("UnlitCounter", (byte)this.unlitCounter);
      nbt.func_74772_a("StateChangeTime", this.lastManualStateChangeTime);
      return nbt;
   }

   private void writeBurning(CompoundNBT nbt) {
      nbt.func_74757_a("IsBurning", this.isBurning);
   }

   public void func_230337_a_(BlockState state, CompoundNBT nbt) {
      super.func_230337_a_(state, nbt);
      this.readBurning(nbt);
      this.litCounter = nbt.func_74771_c("LitCounter");
      this.unlitCounter = nbt.func_74771_c("UnlitCounter");
      this.lastManualStateChangeTime = nbt.func_74763_f("StateChangeTime");
   }

   private void readBurning(CompoundNBT nbt) {
      this.isBurning = nbt.func_74767_n("IsBurning");
   }

   public CompoundNBT func_189517_E_() {
      CompoundNBT nbt = super.func_189517_E_();
      this.writeBurning(nbt);
      return nbt;
   }

   public SUpdateTileEntityPacket func_189518_D_() {
      CompoundNBT nbt = new CompoundNBT();
      this.writeBurning(nbt);
      return new SUpdateTileEntityPacket(this.field_174879_c, 0, nbt);
   }

   public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
      this.readBurning(pkt.func_148857_g());
   }
}
