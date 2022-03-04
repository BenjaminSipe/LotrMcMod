package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Item.Properties;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SaltItem extends Item {
   public SaltItem(Properties properties) {
      super(properties);
   }

   public ActionResultType func_195939_a(ItemUseContext context) {
      World world = context.func_195991_k();
      BlockPos usePos = context.func_195995_a();
      ItemStack itemstack = context.func_195996_i();
      if (!world.field_72995_K) {
         boolean usedAny = false;
         int salted = false;
         int range = 1 + world.field_73012_v.nextInt(2);
         int yRange = range / 2;

         for(int i = -range; i <= range; ++i) {
            for(int j = -yRange; j <= yRange; ++j) {
               for(int k = -range; k <= range; ++k) {
                  BlockPos nearPos = usePos.func_177982_a(i, j, k);
                  if (nearPos.func_218141_a(usePos, (double)range)) {
                  }

                  Block block = world.func_180495_p(nearPos).func_177230_c();
                  Block newBlock = null;
                  if (block != Blocks.field_196658_i && block != Blocks.field_150346_d && block != Blocks.field_150458_ak) {
                     if (block == Blocks.field_150433_aE) {
                        newBlock = Blocks.field_150350_a;
                     }
                  } else {
                     newBlock = Blocks.field_196660_k;
                  }

                  if (newBlock != null) {
                     if (nearPos.equals(usePos) || world.field_73012_v.nextInt(3) != 0) {
                        world.func_175656_a(nearPos, newBlock.func_176223_P());
                        if (nearPos.equals(usePos) || world.field_73012_v.nextInt(3) == 0) {
                           ((ServerWorld)world).func_195598_a(ParticleTypes.field_197613_f, (double)nearPos.func_177958_n() + 0.5D, (double)nearPos.func_177956_o() + 1.0D, (double)nearPos.func_177952_p() + 0.5D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                        }
                     }

                     usedAny = true;
                  }
               }
            }
         }

         if (usedAny) {
            itemstack.func_190918_g(1);
            return ActionResultType.SUCCESS;
         }
      }

      return ActionResultType.PASS;
   }
}
