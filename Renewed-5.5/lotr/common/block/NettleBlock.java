package lotr.common.block;

import lotr.common.init.LOTRDamageSources;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NettleBlock extends LOTRGrassBlock {
   public void func_196262_a(BlockState state, World world, BlockPos pos, Entity entity) {
      if (entity instanceof PlayerEntity) {
         PlayerEntity player = (PlayerEntity)entity;
         boolean bootsLegs = player.func_190630_a(EquipmentSlotType.FEET) && player.func_190630_a(EquipmentSlotType.LEGS);
         if (!bootsLegs) {
            player.func_70097_a(LOTRDamageSources.PLANT, 0.25F);
         }
      }

   }
}
