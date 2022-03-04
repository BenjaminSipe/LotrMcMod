package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockThatchFloor extends Block {
   public LOTRBlockThatchFloor() {
      super(Material.field_151593_r);
      this.func_149711_c(0.2F);
      this.func_149672_a(Block.field_149779_h);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      return null;
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getThatchFloorRenderID();
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return this.func_149718_j(world, i, j, k);
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         this.func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
         world.func_147468_f(i, j, k);
      }

   }
}
