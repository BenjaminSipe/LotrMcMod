package lotr.common.dispenser;

import lotr.common.entity.item.LOTRBoatEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DispenseLOTRBoat extends DefaultDispenseItemBehavior {
   private final DefaultDispenseItemBehavior dispenseItemBehaviour = new DefaultDispenseItemBehavior();
   private final LOTRBoatEntity.ModBoatType type;

   public DispenseLOTRBoat(LOTRBoatEntity.ModBoatType t) {
      this.type = t;
   }

   public ItemStack func_82487_b(IBlockSource source, ItemStack stack) {
      Direction dir = (Direction)source.func_189992_e().func_177229_b(DispenserBlock.field_176441_a);
      World world = source.func_197524_h();
      double x = source.func_82615_a() + (double)((float)dir.func_82601_c() * 1.125F);
      double y = source.func_82617_b() + (double)((float)dir.func_96559_d() * 1.125F);
      double z = source.func_82616_c() + (double)((float)dir.func_82599_e() * 1.125F);
      BlockPos pos = source.func_180699_d().func_177972_a(dir);
      double yOffset;
      if (world.func_204610_c(pos).func_206884_a(FluidTags.field_206959_a)) {
         yOffset = 1.0D;
      } else {
         if (!world.func_180495_p(pos).func_196958_f() || !world.func_204610_c(pos.func_177977_b()).func_206884_a(FluidTags.field_206959_a)) {
            return this.dispenseItemBehaviour.dispense(source, stack);
         }

         yOffset = 0.0D;
      }

      LOTRBoatEntity boat = new LOTRBoatEntity(world, x, y + yOffset, z);
      boat.setModBoatType(this.type);
      boat.field_70177_z = dir.func_185119_l();
      world.func_217376_c(boat);
      stack.func_190918_g(1);
      return stack;
   }

   protected void func_82485_a(IBlockSource source) {
      source.func_197524_h().func_217379_c(1000, source.func_180699_d(), 0);
   }
}
