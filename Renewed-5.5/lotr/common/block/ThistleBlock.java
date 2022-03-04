package lotr.common.block;

import lotr.common.init.LOTRDamageSources;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

public class ThistleBlock extends LOTRFlowerBlock {
   private static final VoxelShape THISTLE_SHAPE = Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

   public ThistleBlock(Effect effect, int effectDuration) {
      super(effect, effectDuration);
      this.flowerShape = THISTLE_SHAPE;
   }

   public void func_196262_a(BlockState state, World world, BlockPos pos, Entity entity) {
      if (entity instanceof LivingEntity && entity.func_70051_ag()) {
         LivingEntity living = (LivingEntity)entity;
         boolean bootsLegs = living.func_190630_a(EquipmentSlotType.FEET) && living.func_190630_a(EquipmentSlotType.LEGS);
         if (!bootsLegs) {
            living.func_70097_a(LOTRDamageSources.PLANT, 0.25F);
         }
      }

   }
}
