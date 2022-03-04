package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockUnsmeltery extends LOTRBlockForgeBase {
   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      return this.func_149691_a(side, world.func_72805_g(i, j, k));
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150347_e.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityUnsmeltery();
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getUnsmelteryRenderID();
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if (!world.field_72995_K) {
         entityplayer.openGui(LOTRMod.instance, 38, world, i, j, k);
      }

      return true;
   }

   protected boolean useLargeSmoke() {
      return false;
   }

   public void func_149734_b(World world, int i, int j, int k, Random random) {
      super.func_149734_b(world, i, j, k, random);
      if (isForgeActive(world, i, j, k)) {
         for(int l = 0; l < 3; ++l) {
            float f = (float)i + 0.25F + random.nextFloat() * 0.5F;
            float f1 = (float)j + 0.5F + random.nextFloat() * 0.5F;
            float f2 = (float)k + 0.25F + random.nextFloat() * 0.5F;
            world.func_72869_a("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
         }
      }

   }
}
