package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRDimension;
import lotr.common.tileentity.LOTRTileEntityUtumnoPortal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockUtumnoPortal extends BlockContainer {
   public LOTRBlockUtumnoPortal() {
      super(Material.field_151567_E);
      this.func_149711_c(-1.0F);
      this.func_149752_b(Float.MAX_VALUE);
      this.func_149672_a(Block.field_149769_e);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityUtumnoPortal();
   }

   public void func_149743_a(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity) {
   }

   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
      entity.func_70110_aj();
      TileEntity te = world.func_147438_o(i, j, k);
      if (te instanceof LOTRTileEntityUtumnoPortal) {
         ((LOTRTileEntityUtumnoPortal)te).transferEntity(entity);
      }

   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149745_a(Random par1Random) {
      return 0;
   }

   public int func_149645_b() {
      return -1;
   }

   public void func_149726_b(World world, int i, int j, int k) {
      if (world.field_73011_w.field_76574_g != LOTRDimension.MIDDLE_EARTH.dimensionID) {
         world.func_147468_f(i, j, k);
      }

   }

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return Item.func_150899_d(0);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150427_aO.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }
}
