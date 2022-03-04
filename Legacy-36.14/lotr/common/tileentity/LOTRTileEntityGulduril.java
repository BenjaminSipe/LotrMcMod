package lotr.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRTileEntityGulduril extends TileEntity {
   public int ticksExisted;

   public void func_145834_a(World world) {
      super.func_145834_a(world);
      this.ticksExisted = world.field_73012_v.nextInt(200);
   }

   public void func_145845_h() {
      ++this.ticksExisted;
   }
}
