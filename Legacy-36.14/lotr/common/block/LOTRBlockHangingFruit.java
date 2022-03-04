package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class LOTRBlockHangingFruit extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon[] fruitIcons;
   private String[] fruitSides = new String[]{"top", "side", "bottom"};

   public LOTRBlockHangingFruit() {
      super(Material.field_151585_k);
      this.func_149675_a(true);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 0) {
         return this.fruitIcons[2];
      } else {
         return i == 1 ? this.fruitIcons[0] : this.fruitIcons[1];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.fruitIcons = new IIcon[3];

      for(int i = 0; i < 3; ++i) {
         this.fruitIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.fruitSides[i]);
      }

   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      if (!this.func_149718_j(world, i, j, k)) {
         this.func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
         world.func_147468_f(i, j, k);
      }

   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         this.func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
         world.func_147468_f(i, j, k);
      }

   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      int l = world.func_72805_g(i, j, k);
      ForgeDirection dir = ForgeDirection.getOrientation(l + 2);
      Block block = world.func_147439_a(i + dir.offsetX, j, k + dir.offsetZ);
      return block.isWood(world, i + dir.offsetX, j, k + dir.offsetZ);
   }

   public boolean func_149686_d() {
      return false;
   }

   public boolean func_149662_c() {
      return false;
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      this.func_149719_a(world, i, j, k);
      return super.func_149668_a(world, i, j, k);
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB func_149633_g(World world, int i, int j, int k) {
      this.func_149719_a(world, i, j, k);
      return super.func_149633_g(world, i, j, k);
   }
}
