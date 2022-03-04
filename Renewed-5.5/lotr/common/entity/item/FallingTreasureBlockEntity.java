package lotr.common.entity.item;

import java.util.Optional;
import lotr.common.block.TreasurePileBlock;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class FallingTreasureBlockEntity extends Entity {
   private int fallTime;
   private boolean shouldDropItem;
   private boolean dontSetBlock;
   private static final DataParameter ORIGIN;
   private static final DataParameter FALL_TILE;

   public FallingTreasureBlockEntity(EntityType type, World world) {
      super(type, world);
      this.shouldDropItem = true;
   }

   public FallingTreasureBlockEntity(World world, double x, double y, double z, BlockState fallingBlockState) {
      this((EntityType)LOTREntities.FALLING_TREASURE_BLOCK.get(), world);
      this.field_70156_m = true;
      this.func_70107_b(x, y + (double)((1.0F - this.func_213302_cg()) / 2.0F), z);
      this.func_213317_d(Vector3d.field_186680_a);
      this.field_70169_q = x;
      this.field_70167_r = y;
      this.field_70166_s = z;
      this.setOrigin(this.func_233580_cy_());
      this.setFallTile(fallingBlockState);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(ORIGIN, BlockPos.field_177992_a);
      this.field_70180_af.func_187214_a(FALL_TILE, Optional.empty());
   }

   public void setOrigin(BlockPos pos) {
      this.field_70180_af.func_187227_b(ORIGIN, pos);
   }

   public BlockPos getOrigin() {
      return (BlockPos)this.field_70180_af.func_187225_a(ORIGIN);
   }

   public void setFallTile(BlockState state) {
      this.field_70180_af.func_187227_b(FALL_TILE, Optional.of(state));
   }

   public BlockState getFallTile() {
      return (BlockState)((Optional)this.field_70180_af.func_187225_a(FALL_TILE)).orElse(Blocks.field_150350_a.func_176223_P());
   }

   public boolean func_70075_an() {
      return false;
   }

   protected boolean func_225502_at_() {
      return false;
   }

   public boolean func_70067_L() {
      return this.func_70089_S();
   }

   private boolean isValidFallTile() {
      BlockState fallTile = this.getFallTile();
      return !fallTile.func_196958_f() && fallTile.func_177230_c() instanceof TreasurePileBlock;
   }

   public void func_70071_h_() {
      if (!this.isValidFallTile()) {
         this.func_70106_y();
      } else {
         BlockState fallTile = this.getFallTile();
         Block block = fallTile.func_177230_c();
         BlockPos thisPos;
         if (this.fallTime++ == 0) {
            thisPos = this.func_233580_cy_();
            if (this.field_70170_p.func_180495_p(thisPos).func_177230_c() == block) {
               this.field_70170_p.func_217377_a(thisPos, false);
            } else if (!this.field_70170_p.field_72995_K) {
               this.func_70106_y();
               return;
            }
         }

         if (!this.func_189652_ae()) {
            this.func_213317_d(this.func_213322_ci().func_72441_c(0.0D, -0.04D, 0.0D));
         }

         this.func_213315_a(MoverType.SELF, this.func_213322_ci());
         if (!this.field_70170_p.field_72995_K) {
            thisPos = this.func_233580_cy_();
            boolean flag1 = false;
            if (!this.field_70122_E && !flag1) {
               if (!this.field_70170_p.field_72995_K && (this.fallTime > 100 && (thisPos.func_177956_o() < 1 || thisPos.func_177956_o() > 256) || this.fallTime > 600)) {
                  if (this.shouldDropItem && this.field_70170_p.func_82736_K().func_223586_b(GameRules.field_223604_g)) {
                     this.dropTreasureItems();
                  }

                  this.func_70106_y();
               }
            } else {
               BlockState stateAtPos = this.field_70170_p.func_180495_p(thisPos);
               this.func_213317_d(this.func_213322_ci().func_216372_d(0.7D, -0.5D, 0.7D));
               if (stateAtPos.func_177230_c() != Blocks.field_196603_bb) {
                  this.func_70106_y();
                  if (this.dontSetBlock) {
                     if (block instanceof FallingBlock) {
                     }
                  } else {
                     SoundType treasureSoundType = fallTile.getSoundType(this.field_70170_p, thisPos, this);
                     boolean placedAnyTreasure = false;
                     boolean placedAllTreasure = false;
                     if (stateAtPos.func_177230_c() == fallTile.func_177230_c()) {
                        int belowPileLevel = (Integer)stateAtPos.func_177229_b(TreasurePileBlock.PILE_LEVEL);
                        if (belowPileLevel < 8) {
                           int fallingPileLevel;
                           for(fallingPileLevel = (Integer)fallTile.func_177229_b(TreasurePileBlock.PILE_LEVEL); fallingPileLevel > 0 && belowPileLevel < 8; ++belowPileLevel) {
                              --fallingPileLevel;
                           }

                           this.field_70170_p.func_180501_a(thisPos, (BlockState)stateAtPos.func_206870_a(TreasurePileBlock.PILE_LEVEL, belowPileLevel), 3);
                           thisPos = thisPos.func_177984_a();
                           stateAtPos = this.field_70170_p.func_180495_p(thisPos);
                           placedAnyTreasure = true;
                           if (fallingPileLevel <= 0) {
                              placedAllTreasure = true;
                              fallTile = Blocks.field_150350_a.func_176223_P();
                           } else {
                              fallTile = (BlockState)fallTile.func_206870_a(TreasurePileBlock.PILE_LEVEL, fallingPileLevel);
                           }

                           this.setFallTile(fallTile);
                        }
                     }

                     if (!placedAllTreasure) {
                        boolean replaceable = stateAtPos.func_196953_a(new DirectionalPlaceContext(this.field_70170_p, thisPos, Direction.DOWN, ItemStack.field_190927_a, Direction.UP));
                        boolean canFallThrough = FallingBlock.func_185759_i(this.field_70170_p.func_180495_p(thisPos.func_177977_b()));
                        boolean placeAt = fallTile.func_196955_c(this.field_70170_p, thisPos) && !canFallThrough;
                        if (replaceable && placeAt) {
                           if (fallTile.func_235901_b_(BlockStateProperties.field_208198_y) && this.field_70170_p.func_204610_c(thisPos).func_206886_c() == Fluids.field_204546_a) {
                              fallTile = (BlockState)fallTile.func_206870_a(BlockStateProperties.field_208198_y, true);
                           }

                           if (this.field_70170_p.func_180501_a(thisPos, fallTile, 3)) {
                              if (block instanceof TreasurePileBlock) {
                                 ((TreasurePileBlock)block).onEndFallingTreasure(this.field_70170_p, thisPos, fallTile, stateAtPos);
                              }

                              placedAnyTreasure = true;
                           } else if (this.shouldDropItem && this.field_70170_p.func_82736_K().func_223586_b(GameRules.field_223604_g)) {
                              this.dropTreasureItems();
                           }
                        } else if (this.shouldDropItem && this.field_70170_p.func_82736_K().func_223586_b(GameRules.field_223604_g)) {
                           this.dropTreasureItems();
                        }
                     }

                     if (placedAnyTreasure) {
                        this.field_70170_p.func_184133_a((PlayerEntity)null, thisPos, treasureSoundType.func_185841_e(), SoundCategory.BLOCKS, (treasureSoundType.func_185843_a() + 1.0F) / 2.0F, treasureSoundType.func_185847_b() * 0.8F);
                     }
                  }
               }
            }
         }

         this.func_213317_d(this.func_213322_ci().func_186678_a(0.98D));
      }

   }

   private void dropTreasureItems() {
      Block.func_220075_c(this.getFallTile(), this.field_70170_p, this.func_233580_cy_());
   }

   protected void func_213281_b(CompoundNBT nbt) {
      nbt.func_218657_a("BlockState", NBTUtil.func_190009_a(this.getFallTile()));
      nbt.func_74768_a("Time", this.fallTime);
      nbt.func_74757_a("DropItem", this.shouldDropItem);
   }

   protected void func_70037_a(CompoundNBT nbt) {
      this.setFallTile(NBTUtil.func_190008_d(nbt.func_74775_l("BlockState")));
      this.fallTime = nbt.func_74762_e("Time");
      if (nbt.func_150297_b("DropItem", 99)) {
         this.shouldDropItem = nbt.func_74767_n("DropItem");
      }

      if (!this.isValidFallTile()) {
         this.setFallTile(((Block)LOTRBlocks.GOLD_TREASURE_PILE.get()).func_176223_P());
      }

   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_90999_ad() {
      return false;
   }

   public void func_85029_a(CrashReportCategory category) {
      super.func_85029_a(category);
      category.func_71507_a("Immitating BlockState", this.getFallTile().toString());
   }

   public boolean func_184213_bq() {
      return true;
   }

   public IPacket func_213297_N() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   static {
      ORIGIN = EntityDataManager.func_187226_a(FallingTreasureBlockEntity.class, DataSerializers.field_187200_j);
      FALL_TILE = EntityDataManager.func_187226_a(FallingTreasureBlockEntity.class, DataSerializers.field_187197_g);
   }
}
