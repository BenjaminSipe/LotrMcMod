package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockUtumnoReturnLight extends Block {
   public LOTRBlockUtumnoReturnLight() {
      super(Material.field_151594_q);
      this.func_149715_a(1.0F);
   }

   public boolean isReplaceable(IBlockAccess world, int i, int j, int k) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   public Item func_149650_a(int i, Random random, int j) {
      return null;
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return -1;
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      return null;
   }

   public boolean func_149703_v() {
      return false;
   }
}
